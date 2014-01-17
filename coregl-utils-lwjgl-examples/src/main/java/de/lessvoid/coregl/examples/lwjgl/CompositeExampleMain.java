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

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DST_ALPHA;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_DST_ALPHA;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_ZERO;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import de.lessvoid.coregl.CoreFactory;
import de.lessvoid.coregl.CoreSetup;
import de.lessvoid.coregl.CoreSetup.RenderLoopCallback;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreVAO;
import de.lessvoid.coregl.CoreVAO.FloatType;
import de.lessvoid.coregl.CoreVBO.DataType;
import de.lessvoid.coregl.CoreVBO.UsageType;
import de.lessvoid.coregl.lwjgl.CoreFactoryLwjgl;
import de.lessvoid.math.MatrixFactory;

public class CompositeExampleMain implements RenderLoopCallback {
  private final CoreShader shader;
  private final CoreVAO src;
  private final CoreVAO dst;
  private final CoreVAO white;
  private CoreFactory factory;

  public CompositeExampleMain(final CoreFactory factory) {
    this.factory = factory;
    shader = factory.newShaderWithVertexAttributes("aVertex", "aColor");
    shader.vertexShader("plain-color.vs");
    shader.fragmentShader("plain-color.fs");
    shader.link();

    src = factory.createVAO();
    src.bind();

    factory.createVBO(DataType.FLOAT, UsageType.STATIC_DRAW, new Float[] {
          0.f,   0.f,    1.f, 1.f, 0.f, 1.f,
        100.f,   0.f,    1.f, 1.f, 0.f, 1.f,
          0.f, 100.f,    1.f, 1.f, 0.f, 1.f,
        100.f,   0.f,    0.f, 0.f, 0.f, 0.f,
          0.f, 100.f,    0.f, 0.f, 0.f, 0.f,
        100.f, 100.f,    0.f, 0.f, 0.f, 0.f,
    });

    src.enableVertexAttribute(0);
    src.vertexAttribPointer(0, 2, FloatType.FLOAT, 6, 0);
    src.enableVertexAttribute(1);
    src.vertexAttribPointer(1, 4, FloatType.FLOAT, 6, 2);

    dst = factory.createVAO();
    dst.bind();

    factory.createVBO(DataType.FLOAT, UsageType.STATIC_DRAW, new Float[] {
          0.f,   0.f,    0.f, 0.f, 1.f, 1.f,
        100.f,   0.f,    0.f, 0.f, 1.f, 1.f,
        100.f, 100.f,    0.f, 0.f, 1.f, 1.f,
          0.f,   0.f,    0.f, 0.f, 0.f, 0.f,
          0.f, 100.f,    0.f, 0.f, 0.f, 0.f,
        100.f, 100.f,    0.f, 0.f, 0.f, 0.f,
    });

    dst.enableVertexAttribute(0);
    dst.vertexAttribPointer(0, 2, FloatType.FLOAT, 6, 0);
    dst.enableVertexAttribute(1);
    dst.vertexAttribPointer(1, 4, FloatType.FLOAT, 6, 2);

    white = factory.createVAO();
    white.bind();

    factory.createVBO(DataType.FLOAT, UsageType.STATIC_DRAW, new Float[] {
          0.f,   0.f,    1.f, 1.f, 1.f, 1.f,
        100.f,   0.f,    1.f, 1.f, 1.f, 1.f,
        100.f, 100.f,    1.f, 1.f, 1.f, 1.f,
          0.f,   0.f,    1.f, 1.f, 1.f, 1.f,
          0.f, 100.f,    1.f, 1.f, 1.f, 1.f,
        100.f, 100.f,    1.f, 1.f, 1.f, 1.f,
    });

    white.enableVertexAttribute(0);
    white.vertexAttribPointer(0, 2, FloatType.FLOAT, 6, 0);
    white.enableVertexAttribute(1);
    white.vertexAttribPointer(1, 4, FloatType.FLOAT, 6, 2);

    shader.activate();
    shader.setUniformMatrix4f("uMvp", MatrixFactory.createOrtho(0, 1024.f, 768.f, 0).toBuffer());

    glEnable(GL_BLEND);
  }

  @Override
  public boolean render(final float deltaTime) {
    glClearColor(1.f, 1.f, 1.f, 1.f);
    glClear(GL_COLOR_BUFFER_BIT);

    float x = 100.f;
    float y = 100.f;

    // first row
    renderDst(x, y);
    glBlendFunc(GL_ZERO, GL_ZERO);
    renderSrc(x, y);
    renderWhite(x, y);
    x += 150.f;

    renderDst(x, y);
    glBlendFunc(GL_ONE, GL_ZERO);
    renderSrc(x, y);
    renderWhite(x, y);
    x += 150.f;

    renderDst(x, y);
    glBlendFunc(GL_ZERO, GL_ONE);
    renderSrc(x, y);
    renderWhite(x, y);
    x += 150.f;

    renderDst(x, y);
    glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
    renderSrc(x, y);
    renderWhite(x, y);
    x += 150.f;

    renderDst(x, y);
    glBlendFunc(GL_ONE_MINUS_DST_ALPHA, GL_ONE);
    renderSrc(x, y);
    renderWhite(x, y);
    x += 150.f;

    renderDst(x, y);
    glBlendFunc(GL_DST_ALPHA, GL_ZERO);
    renderSrc(x, y);
    renderWhite(x, y);

    x  = 100.f;
    y += 150.f;

    // second row
    renderDst(x, y);
    glBlendFunc(GL_ZERO, GL_SRC_ALPHA);
    renderSrc(x, y);
    renderWhite(x, y);
    x += 150.f;

    renderDst(x, y);
    glBlendFunc(GL_ONE_MINUS_DST_ALPHA, GL_ZERO);
    renderSrc(x, y);
    renderWhite(x, y);
    x += 150.f;

    renderDst(x, y);
    glBlendFunc(GL_ZERO, GL_ONE_MINUS_SRC_ALPHA);
    renderSrc(x, y);
    renderWhite(x, y);
    x += 150.f;

    renderDst(x, y);
    glBlendFunc(GL_DST_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    renderSrc(x, y);
    renderWhite(x, y);
    x += 150.f;

    renderDst(x, y);
    glBlendFunc(GL_ONE_MINUS_DST_ALPHA, GL_SRC_ALPHA);
    renderSrc(x, y);
    renderWhite(x, y);
    x += 150.f;

    renderDst(x, y);
    glBlendFunc(GL_ONE_MINUS_DST_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    renderSrc(x, y);
    renderWhite(x, y);

    return false;
  }

  private void renderDst(final float x, final float y) {
    shader.setUniformf("uOffset", x, y);
    glDisable(GL_BLEND);
    dst.bind();
    factory.getCoreRender().renderTriangles(6);
  }

  private void renderWhite(final float x, final float y) {
    shader.setUniformf("uOffset", x, y);
    glBlendFunc(GL_ONE_MINUS_DST_ALPHA, GL_ONE);
    white.bind();
    factory.getCoreRender().renderTriangles(6);
  }

  private void renderSrc(final float x, final float y) {
    shader.setUniformf("uOffset", x, y);

    glEnable(GL_BLEND);
    src.bind();
    factory.getCoreRender().renderTriangles(6);
  }

  public static void main(final String[] args) throws Exception {
    CoreFactory factory = CoreFactoryLwjgl.create();
    CoreSetup setup = factory.createSetup();
    setup.initializeLogging(); // optional to get jdk14 to better format the log
    setup.initialize("Hello Compositing", 1024, 768);
    setup.renderLoop(new CompositeExampleMain(factory));
  }
}