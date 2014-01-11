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
package de.lessvoid.coregl.examples.lwjgl;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPointSize;

import java.nio.FloatBuffer;

import de.lessvoid.coregl.CoreFactory;
import de.lessvoid.coregl.CoreRender;
import de.lessvoid.coregl.CoreSetup;
import de.lessvoid.coregl.CoreSetup.RenderLoopCallback;
import de.lessvoid.coregl.CoreVAO.FloatType;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreVAO;
import de.lessvoid.coregl.CoreVBO;
import de.lessvoid.coregl.CoreVBO.DataType;
import de.lessvoid.coregl.CoreVBO.UsageType;
import de.lessvoid.coregl.lwjgl.CoreFactoryLwjgl;
import de.lessvoid.math.MatrixFactory;
import de.lessvoid.math.Mat4;

public class StarfieldMain implements RenderLoopCallback {
  private static final int STAR_COUNT = 20000;
  private CoreRender coreRender;
  private CoreShader shader;
  private float z = 5.0f;
  private float angleX = 0.f;
  private float angleY = 0.f;

  public StarfieldMain(final CoreFactory factory) {
    coreRender = factory.getCoreRender();

    shader = factory.newShader();
    shader.vertexShader("starfield/star.vs");
    shader.fragmentShader("starfield/star.fs");
    shader.link();

    CoreVAO vao = factory.createVAO();
    vao.bind();

    factory.createVBO(DataType.FLOAT, UsageType.STATIC_DRAW, new Float[] {
        -0.025f, -0.025f,
        -0.025f,  0.025f,
        0.025f, -0.025f,
        0.025f,  0.025f,
    });
    vao.vertexAttribPointer(shader.getAttribLocation("aVertex"), 2, FloatType.FLOAT, 0, 0);

    CoreVBO<FloatBuffer> starPosBuffer = factory.createVBO(DataType.FLOAT, UsageType.STATIC_DRAW, STAR_COUNT * 3);
    FloatBuffer buffer = starPosBuffer.getBuffer();
    float size = 20.f;

    for (int i=0; i<STAR_COUNT; i++) {
      buffer.put(i*3 + 0, (float) Math.random() * size - size / 2.f);
      buffer.put(i*3 + 1, (float) Math.random() * size - size / 2.f);
      buffer.put(i*3 + 2, (float) Math.random() * size - size / 2.f);
    }

    buffer.rewind();
    starPosBuffer.bind();
    starPosBuffer.send();

    vao.enableVertexAttribute(0);
    vao.enableVertexAttributeDivisorf(shader.getAttribLocation("aStarPos"), 3, FloatType.FLOAT, 0, 0, 1);

    shader.activate();
    vao.bind();
    glPointSize(2);

    glEnable(GL_DEPTH_TEST);
  }

  @Override
  public boolean render(final float deltaTime) {
    glClearColor(.1f, .1f, .3f, 0.f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    // render all the data in the currently active vao using triangle strips
    coreRender.renderTriangleStripInstances(4, STAR_COUNT);

    Mat4 translate = Mat4.createTranslate(0.f, 0.f, z);
    Mat4 rotateX = Mat4.createRotate(angleX, 1.f, 0.f, 0.f);
    Mat4 rotateY = Mat4.createRotate(angleY, 0.f, 1.f, 0.f);
    Mat4 projection = MatrixFactory.createProjection(65.f, 1024.f/768.f, 1f, 1000.f);

    z -= deltaTime / 1000.f;
    angleX += deltaTime / 1000.f;
    angleY += deltaTime / 2000.f;

    Mat4 modelViewProjection = Mat4.mul(projection, Mat4.mul(translate, Mat4.mul(rotateX, rotateY, null), null), null);
    shader.setUniformMatrix4f("uModelViewProjection", modelViewProjection.toBuffer());

    return false;
  }

  public static void main(final String[] args) throws Exception {
    CoreFactory factory = CoreFactoryLwjgl.create();
    CoreSetup setup = factory.createSetup();
    setup.initializeLogging(); // optional to get jdk14 to better format the log
    setup.initialize("Hello Lwjgl Core GL", 1024, 768);
    setup.renderLoop(new StarfieldMain(factory));
  }
}
