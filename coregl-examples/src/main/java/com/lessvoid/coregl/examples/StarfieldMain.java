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
import com.lessvoid.math.Mat4;
import com.lessvoid.math.MatrixFactory;

import java.nio.FloatBuffer;

import static com.lessvoid.coregl.CoreBufferDataType.FLOAT;
import static com.lessvoid.coregl.CoreBufferTargetType.ARRAY_BUFFER;
import static com.lessvoid.coregl.CoreBufferUsageType.STATIC_DRAW;

public class StarfieldMain implements CoreExampleRenderLoop {

  private static final int STAR_COUNT = 20000;
  private CoreRender coreRender;
  private CoreShader shader;
  private float z = 5.0f;
  private float angleX = 0.f;
  private float angleY = 0.f;

  @Override
  public void init(final CoreGL gl, final int framebufferWidth, final int framebufferHeight) {
    coreRender = CoreRender.createCoreRender(gl);

    shader = CoreShader.createShader(gl);
    shader.vertexShader("starfield/star.vs", StarfieldMain.class.getResourceAsStream("starfield/star.vs"));
    shader.fragmentShader("starfield/star.fs", StarfieldMain.class.getResourceAsStream("starfield/star.fs"));
    shader.link();

    final CoreVAO vao = CoreVAO.createCoreVAO(gl);
    vao.bind();

    CoreBuffer.createCoreBufferObject(
        gl,
        ARRAY_BUFFER,
        STATIC_DRAW,
        new float[] { -0.025f, -0.025f, -0.025f, 0.025f, 0.025f, -0.025f, 0.025f, 0.025f, });
    vao.vertexAttribPointer(shader.getAttribLocation("aVertex"), 2, FloatType.FLOAT, 0, 0);

    final CoreBuffer<FloatBuffer> starPosBuffer = CoreBuffer
        .createCoreBufferObject(gl, FLOAT, STATIC_DRAW, STAR_COUNT * 3);
    final FloatBuffer buffer = starPosBuffer.getBuffer();
    final float size = 20.f;

    for (int i = 0; i < STAR_COUNT; i++) {
      buffer.put(i * 3 + 0, (float) Math.random() * size - size / 2.f);
      buffer.put(i * 3 + 1, (float) Math.random() * size - size / 2.f);
      buffer.put(i * 3 + 2, (float) Math.random() * size - size / 2.f);
    }

    buffer.rewind();
    starPosBuffer.bind(ARRAY_BUFFER);
    starPosBuffer.send(ARRAY_BUFFER);

    vao.enableVertexAttribute(0);
    vao.enableVertexAttributeDivisorf(shader.getAttribLocation("aStarPos"), 3, FloatType.FLOAT, 0, 0, 1);

    shader.activate();
    vao.bind();
    gl.glPointSize(2);

    gl.glEnable(gl.GL_DEPTH_TEST());
  }

  @Override
  public boolean render(final CoreGL gl, final float d) {
    gl.glClearColor(.1f, .1f, .3f, 0.f);
    gl.glClear(gl.GL_COLOR_BUFFER_BIT() | gl.GL_DEPTH_BUFFER_BIT());

    // render all the data in the currently active vao using triangle strips
    coreRender.renderTriangleStripInstances(4, STAR_COUNT);

    final Mat4 translate = Mat4.createTranslate(0.f, 0.f, z);
    final Mat4 rotateX = Mat4.createRotate(angleX, 1.f, 0.f, 0.f);
    final Mat4 rotateY = Mat4.createRotate(angleY, 0.f, 1.f, 0.f);
    final Mat4 projection = MatrixFactory.createProjection(65.f, 1024.f / 768.f, 1f, 1000.f);

    float deltaTime = 16.6666f;
    z -= deltaTime / 1000.f;
    angleX += deltaTime / 1000.f;
    angleY += deltaTime / 2000.f;

    final Mat4 modelViewProjection = Mat4.mul(projection,
                                              Mat4.mul(translate, Mat4.mul(rotateX, rotateY, null), null),
                                              null);
    shader.setUniformMatrix("uModelViewProjection", 4, modelViewProjection.toBuffer());

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
  }

  public static void main(final String[] args) throws Exception {
    CoreExampleMain.runExample(args, new StarfieldMain());
  }
}
