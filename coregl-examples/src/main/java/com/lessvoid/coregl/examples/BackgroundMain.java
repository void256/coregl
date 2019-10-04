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

import com.lessvoid.coregl.CoreBuffer;
import com.lessvoid.coregl.CoreRender;
import com.lessvoid.coregl.CoreShader;
import com.lessvoid.coregl.CoreVAO;
import com.lessvoid.coregl.CoreVAO.FloatType;
import com.lessvoid.coregl.examples.runner.CoreExampleMain;
import com.lessvoid.coregl.examples.runner.CoreExampleRenderLoop;
import com.lessvoid.coregl.spi.CoreGL;

import static com.lessvoid.coregl.CoreBufferTargetType.ARRAY_BUFFER;
import static com.lessvoid.coregl.CoreBufferUsageType.STATIC_DRAW;

public class BackgroundMain implements CoreExampleRenderLoop {

  private CoreShader shader;
  private final long startTime = System.currentTimeMillis();
  private CoreRender coreRender;

  @Override
  public void init(final CoreGL gl, final int framebufferWidth, final int framebufferHeight) {
    coreRender = CoreRender.createCoreRender(gl);
    shader = CoreShader.createShaderWithVertexAttributes(gl, "vVertex");
    shader.vertexShader("background/background.vs");
    shader.fragmentShader("background/background.fs");
    shader.link();

    final CoreVAO vao = CoreVAO.createCoreVAO(gl);
    vao.bind();

    CoreBuffer.createCoreBufferObject(
        gl,
        ARRAY_BUFFER,
        STATIC_DRAW,
        new float[] { -1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f });

    // parameters are: index, size, stride, offset
    // this will use the currently active VBO to store the VBO in the VAO
    vao.enableVertexAttribute(0);
    vao.vertexAttribPointer(0, 2, FloatType.FLOAT, 2, 0);

    // we only use a single shader and a single vao so we can activate both here
    // and let them stay active the whole time.
    shader.activate();
    vao.bind();
  }

  @Override
  public boolean render(final CoreGL gl, final float delaTime) {
    gl.glClearColor(.1f, .1f, .3f, 0.f);
    gl.glClear(gl.GL_COLOR_BUFFER_BIT());

    final float time = (System.currentTimeMillis() - startTime) / 1000.f;
    shader.setUniformf("time", time);

    // render all the data in the currently active vao using triangle strips
    coreRender.renderTriangleStrip(4);
    return true;
  }

  @Override
  public boolean endLoop(final CoreGL gl) {
    return false;
  }

  @Override
  public void sizeChanged(final CoreGL gl, final int width, final int height) {
    shader.setUniformf("resolution", width, height);
    gl.glViewport(0, 0, width, height);
    System.out.println(width + ":" + height);
  }

  public static void main(final String[] args) throws Exception {
    CoreExampleMain.runExample(args, new BackgroundMain());
  }
}