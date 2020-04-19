/**
 * Copyright (c) 2013, Jens Hohmuth
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.lessvoid.coregl.examples;

import com.lessvoid.coregl.ColorFormat;
import com.lessvoid.coregl.CoreBuffer;
import com.lessvoid.coregl.CoreFBO;
import com.lessvoid.coregl.CoreRender;
import com.lessvoid.coregl.CoreShader;
import com.lessvoid.coregl.CoreTexture2D;
import com.lessvoid.coregl.CoreVAO;
import com.lessvoid.coregl.CoreVAO.FloatType;
import com.lessvoid.coregl.ResizeFilter;
import com.lessvoid.coregl.Type;
import com.lessvoid.coregl.examples.runner.CoreExampleMain;
import com.lessvoid.coregl.examples.runner.CoreExampleRenderLoop;
import com.lessvoid.coregl.examples.util.ImageLoaderImageIO;
import com.lessvoid.coregl.spi.CoreGL;

import java.io.IOException;
import java.nio.ByteBuffer;

import static com.lessvoid.coregl.CoreBufferTargetType.ARRAY_BUFFER;
import static com.lessvoid.coregl.CoreBufferUsageType.STATIC_DRAW;

/**
 * The DistanceFieldExampleMain generates a signed distance field from a high resolution
 * bitmap and then renders it.
 *
 * @author void
 */
public class DistanceFieldExampleMain implements CoreExampleRenderLoop {
  private CoreRender coreRender;
  private ImageLoaderImageIO loader = new ImageLoaderImageIO();
  private CoreTexture2D texture;
  private CoreFBO fbo;
  private CoreTexture2D fboTexture;
  private CoreShader textureShader;
  private float t;

  @Override
  public boolean render(final CoreGL gl, final float d) {
    float deltaTime = 16.6666f;

    coreRender.clearColor(.1f, .1f, .3f, 0.f);
    coreRender.clearColorBuffer();
    t += deltaTime;
    textureShader.setUniformf("scale", (float)Math.sin(t/750.f) + 1.5f);
    coreRender.renderTriangleStrip(4);
    return true;
  }

  @Override
  public boolean endLoop(final CoreGL gl) {
    return false;
  }

  @Override
  public void sizeChanged(final CoreGL gl, final int width, final int height) {
    textureShader.setUniformf("resolution", width, height);
    gl.glViewport(0, 0, width, height);
  }

  @Override
  public void init(final CoreGL gl, final int framebufferWidth, final int framebufferHeight) {
    try {
      ByteBuffer data = loader.loadAsByteBufferRGBA(gl, DistanceFieldExampleMain.class.getResourceAsStream("nifty-logo-150x150.png"));
      texture = CoreTexture2D.createCoreTexture(
              gl,
              ColorFormat.RGBA,
              loader.getImageWidth(),
              loader.getImageHeight(),
              data,
              ResizeFilter.Nearest);
      texture.bind();
    } catch (IOException e) {
      e.printStackTrace();
    }

    coreRender = CoreRender.createCoreRender(gl);

    final CoreShader edtShader = CoreShader.createShaderWithVertexAttributes(gl, "vVertex", "vUV");
    edtShader.vertexShader("distancefield/edt.vs", DistanceFieldExampleMain.class.getResourceAsStream("distancefield/edt.vs"));
    edtShader.fragmentShader("distancefield/edt.fs", DistanceFieldExampleMain.class.getResourceAsStream("distancefield/edt.fs"));
    edtShader.link();
    edtShader.activate();
    edtShader.setUniformi("uTexture", 0);

    textureShader = CoreShader.createShaderWithVertexAttributes(gl, "vVertex", "vUV");
    textureShader.vertexShader("distancefield/texture.vs", DistanceFieldExampleMain.class.getResourceAsStream("distancefield/texture.vs"));
    textureShader.fragmentShader("distancefield/texture.fs", DistanceFieldExampleMain.class.getResourceAsStream("distancefield/texture.fs"));
    textureShader.link();
    textureShader.activate();
    textureShader.setUniformi("uTexture", 0);

    final CoreVAO vao = CoreVAO.createCoreVAO(gl);
    vao.bind();

    CoreBuffer.createCoreBufferObject(
        gl,
        ARRAY_BUFFER,
        STATIC_DRAW,
        new float[] {
            -1.0f, -1.0f,    0.0f, .0f,
            -1.0f,  1.0f,    0.0f, 2048.f,
             1.0f, -1.0f, 2048.0f, .0f,
             1.0f,  1.0f, 2048.0f, 2048.f, });

    // parameters are: index, size, stride, offset
    // this will use the currently active VBO to store the VBO in the VAO
    vao.enableVertexAttribute(0);
    vao.vertexAttribPointer(0, 2, FloatType.FLOAT, 4, 0);
    vao.enableVertexAttribute(1);
    vao.vertexAttribPointer(1, 2, FloatType.FLOAT, 4, 2);

    edtShader.activate();
    vao.bind();

    fboTexture = CoreTexture2D.createEmptyTexture(gl, ColorFormat.RGBA, Type.UNSIGNED_BYTE, 128, 128, ResizeFilter.LinearMipMapLinear);
    fbo = CoreFBO.createCoreFBO(gl);
    fbo.bindFramebuffer();
    fbo.attachTexture(fboTexture.getTextureId(), 0);

    gl.glViewport(0, 0, 128, 128);
    texture.bind();
    coreRender.renderTriangleStrip(4);
    fbo.disable();

    gl.glViewport(0, 0, 1024, 768);

    vao.bind();
    CoreBuffer.createCoreBufferObject(
            gl,
            ARRAY_BUFFER,
            STATIC_DRAW,
            new float[] {
                    -0.5f, -0.5f, 0.0f, 1.0f,
                    -0.5f,  0.5f, 0.0f, 0.0f,
                     0.5f, -0.5f, 1.0f, 1.0f,
                     0.5f,  0.5f, 1.0f, 0.0f, });
    vao.vertexAttribPointer(0, 2, FloatType.FLOAT, 4, 0);
    vao.vertexAttribPointer(1, 2, FloatType.FLOAT, 4, 2);

    fboTexture.bind();
    textureShader.activate();
  }

  public static void main(final String[] args) throws Exception {
    CoreExampleMain.runExample(args, new DistanceFieldExampleMain());
  }
}
