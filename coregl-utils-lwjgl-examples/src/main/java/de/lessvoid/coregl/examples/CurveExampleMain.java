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
package de.lessvoid.coregl.examples;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import de.lessvoid.coregl.CoreFactory;
import de.lessvoid.coregl.CoreVAO.FloatType;
import de.lessvoid.coregl.CoreVBO.DataType;
import de.lessvoid.coregl.CoreVBO.UsageType;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreVAO;
import de.lessvoid.coregl.lwjgl.CoreFactoryLwjgl;
import de.lessvoid.coregl.spi.CoreSetup;
import de.lessvoid.coregl.spi.CoreSetup.RenderLoopCallback;

public class CurveExampleMain implements RenderLoopCallback {
  private CoreShader shader;
  private float r = 0.f;
  private CoreFactory factory;

  public CurveExampleMain(final CoreFactory factory) {
    this.factory = factory;
    shader = factory.createShaderWithVertexAttributes("vVertex", "vTexture");
    shader.vertexShader("curve/curve.vs");
    shader.fragmentShader("curve/curve.fs");
    shader.link();

    CoreVAO vao = factory.createVAO();
    vao.bind();

    float aspect = 4.f/3.f;
    factory.createVBO(DataType.FLOAT, UsageType.STATIC_DRAW, new Float[] {
        -0.4f, -0.4f * aspect,    0.0f, 0.0f, 
         0.4f, -0.4f * aspect,    1.0f, 0.0f, 
        -0.4f,  0.4f * aspect,    0.0f, 1.0f, 
         0.4f,  0.4f * aspect,    1.0f, 1.0f, 
    });

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
  public boolean render(final float deltaTime) {
    glClearColor(.1f, .1f, .3f, 0.f);
    glClear(GL_COLOR_BUFFER_BIT);

    shader.setUniformf("r", r);
    r += deltaTime / 10000.f;
    System.out.println(r);

    // render all the data in the currently active vao using triangle strips
    factory.getCoreRender().renderTriangleStrip(4);
    return false;
  }

  public static void main(final String[] args) throws Exception {
    CoreFactory factory = CoreFactoryLwjgl.create();
    CoreSetup setup = factory.createSetup();
    setup.initializeLogging(); // optional to get jdk14 to better format the log
    setup.initialize("Hello Lwjgl Core GL", 1024, 768);
    setup.renderLoop(new CurveExampleMain(factory));
  }
}
