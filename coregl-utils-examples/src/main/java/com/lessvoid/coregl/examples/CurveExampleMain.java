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
import com.lessvoid.coregl.spi.CoreGL;
import com.lessvoid.coregl.spi.CoreGLSetup.RenderLoopCallback;

import java.io.IOException;

import static com.lessvoid.coregl.CoreBufferTargetType.ARRAY_BUFFER;
import static com.lessvoid.coregl.CoreBufferUsageType.STATIC_DRAW;

public class CurveExampleMain implements RenderLoopCallback {

  private CoreRender coreRender;
  private CoreShader shader;
  private float r = 0.f;

  @Override
  public void init(final CoreGL gl) {
    coreRender = CoreRender.createCoreRender(gl);
    shader = CoreShader.createShaderWithVertexAttributes(gl, "vVertex", "vTexture");
    try {
      shader.vertexShader("vertex", CurveExampleMain.class.getModule().getResourceAsStream("curve/curve.vs"));
      shader.fragmentShader("fragmen", CurveExampleMain.class.getModule().getResourceAsStream("curve/curve.fs"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    shader.link();

    final CoreVAO vao = CoreVAO.createCoreVAO(gl);
    vao.bind();

    final float aspect = 4.f / 3.f;
    CoreBuffer.createCoreBufferObject(
        gl,
        ARRAY_BUFFER,
        STATIC_DRAW,
        new float[] {
            -0.4f, -0.4f * aspect, 0.0f, 0.0f, 0.4f, -0.4f * aspect, 1.0f, 0.0f, -0.4f,
             0.4f * aspect, 0.0f, 1.0f, 0.4f, 0.4f * aspect, 1.0f, 1.0f, });

    // parameters are: index, size, stride, offset
    // this will use the currently active VBO to store the VBO in the VAO
    vao.enableVertexAttribute(0);
    vao.vertexAttribPointer(0, 2, FloatType.FLOAT, 4, 0);
    vao.enableVertexAttribute(1);
    vao.vertexAttribPointer(1, 2, FloatType.FLOAT, 4, 2);

    // we only use a single shader and a single vao so we can activate both here
    // and let them stay active the whole time.
    shader.activate();
    vao.bind();
  }

  @Override
  public boolean render(final CoreGL gl, final float deltaTime) {
    gl.glClearColor(.1f, .1f, .3f, 0.f);
    gl.glClear(gl.GL_COLOR_BUFFER_BIT());

    shader.setUniformf("r", r);
    r += deltaTime / 10000.f;

    // render all the data in the currently active vao using triangle strips
    coreRender.renderTriangleStrip(4);
    return true;
  }

  @Override
  public boolean endLoop() {
    return false;
  }

  public static void main(final String[] args) throws Exception {
    final RenderLoopCallback curveEample = new CurveExampleMain();
    CoreExampleMain.runExample(curveEample);
  }
}
