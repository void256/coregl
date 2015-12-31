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

import de.lessvoid.coregl.CoreRender;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreVAO;
import de.lessvoid.coregl.CoreVAO.FloatType;
import de.lessvoid.coregl.CoreVBO;
import de.lessvoid.coregl.CoreVBO.DataType;
import de.lessvoid.coregl.CoreVBO.UsageType;
import de.lessvoid.coregl.spi.CoreGL;
import de.lessvoid.coregl.spi.CoreSetup.RenderLoopCallback;
import de.lessvoid.math.MatrixFactory;

public class CompositeExampleMain implements RenderLoopCallback {

  private CoreRender coreRender;
  private CoreShader shader;
  private CoreVAO src;
  private CoreVAO dst;
  private CoreVAO white;

  @Override
  public void init(final CoreGL gl) {
    coreRender = CoreRender.createCoreRender(gl);
    shader = CoreShader.createShaderWithVertexAttributes(gl, "aVertex", "aColor");
    shader.vertexShader("plain-color.vs");
    shader.fragmentShader("plain-color.fs");
    shader.link();

    src = CoreVAO.createCoreVAO(gl);
    src.bind();

    CoreVBO.createCoreVBO(gl,
                          DataType.FLOAT,
                          UsageType.STATIC_DRAW,
                          new Float[] { 0.f, 0.f, 1.f, 1.f, 0.f, 1.f, 100.f, 0.f, 1.f, 1.f, 0.f, 1.f, 0.f, 100.f, 1.f,
                              1.f, 0.f, 1.f, 100.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 100.f, 0.f, 0.f, 0.f, 0.f, 100.f,
                              100.f, 0.f, 0.f, 0.f, 0.f, });

    src.enableVertexAttribute(0);
    src.vertexAttribPointer(0, 2, FloatType.FLOAT, 6, 0);
    src.enableVertexAttribute(1);
    src.vertexAttribPointer(1, 4, FloatType.FLOAT, 6, 2);

    dst = CoreVAO.createCoreVAO(gl);
    dst.bind();

    CoreVBO.createCoreVBO(gl,
                          DataType.FLOAT,
                          UsageType.STATIC_DRAW,
                          new Float[] { 0.f, 0.f, 0.f, 0.f, 1.f, 1.f, 100.f, 0.f, 0.f, 0.f, 1.f, 1.f, 100.f, 100.f, 0.f,
                              0.f, 1.f, 1.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 100.f, 0.f, 0.f, 0.f, 0.f, 100.f, 100.f,
                              0.f, 0.f, 0.f, 0.f, });

    dst.enableVertexAttribute(0);
    dst.vertexAttribPointer(0, 2, FloatType.FLOAT, 6, 0);
    dst.enableVertexAttribute(1);
    dst.vertexAttribPointer(1, 4, FloatType.FLOAT, 6, 2);

    white = CoreVAO.createCoreVAO(gl);
    white.bind();

    CoreVBO.createCoreVBO(gl,
                          DataType.FLOAT,
                          UsageType.STATIC_DRAW,
                          new Float[] { 0.f, 0.f, 1.f, 1.f, 1.f, 1.f, 100.f, 0.f, 1.f, 1.f, 1.f, 1.f, 100.f, 100.f, 1.f,
                              1.f, 1.f, 1.f, 0.f, 0.f, 1.f, 1.f, 1.f, 1.f, 0.f, 100.f, 1.f, 1.f, 1.f, 1.f, 100.f, 100.f,
                              1.f, 1.f, 1.f, 1.f, });

    white.enableVertexAttribute(0);
    white.vertexAttribPointer(0, 2, FloatType.FLOAT, 6, 0);
    white.enableVertexAttribute(1);
    white.vertexAttribPointer(1, 4, FloatType.FLOAT, 6, 2);

    shader.activate();
    shader.setUniformMatrix("uMvp", 4, MatrixFactory.createOrtho(0, 1024.f, 768.f, 0).toBuffer());

    gl.glEnable(gl.GL_BLEND());
  }

  @Override
  public boolean render(final CoreGL gl, final float deltaTime) {
    gl.glClearColor(1.f, 1.f, 1.f, 1.f);
    gl.glClear(gl.GL_COLOR_BUFFER_BIT());

    float x = 100.f;
    float y = 100.f;

    // first row
    renderDst(gl, x, y);
    gl.glBlendFunc(gl.GL_ZERO(), gl.GL_ZERO());
    renderSrc(gl, x, y);
    renderWhite(gl, x, y);
    x += 150.f;

    renderDst(gl, x, y);
    gl.glBlendFunc(gl.GL_ONE(), gl.GL_ZERO());
    renderSrc(gl, x, y);
    renderWhite(gl, x, y);
    x += 150.f;

    renderDst(gl, x, y);
    gl.glBlendFunc(gl.GL_ZERO(), gl.GL_ONE());
    renderSrc(gl, x, y);
    renderWhite(gl, x, y);
    x += 150.f;

    renderDst(gl, x, y);
    gl.glBlendFunc(gl.GL_ONE(), gl.GL_ONE_MINUS_SRC_ALPHA());
    renderSrc(gl, x, y);
    renderWhite(gl, x, y);
    x += 150.f;

    renderDst(gl, x, y);
    gl.glBlendFunc(gl.GL_ONE_MINUS_DST_ALPHA(), gl.GL_ONE());
    renderSrc(gl, x, y);
    renderWhite(gl, x, y);
    x += 150.f;

    renderDst(gl, x, y);
    gl.glBlendFunc(gl.GL_DST_ALPHA(), gl.GL_ZERO());
    renderSrc(gl, x, y);
    renderWhite(gl, x, y);

    x = 100.f;
    y += 150.f;

    // second row
    renderDst(gl, x, y);
    gl.glBlendFunc(gl.GL_ZERO(), gl.GL_SRC_ALPHA());
    renderSrc(gl, x, y);
    renderWhite(gl, x, y);
    x += 150.f;

    renderDst(gl, x, y);
    gl.glBlendFunc(gl.GL_ONE_MINUS_DST_ALPHA(), gl.GL_ZERO());
    renderSrc(gl, x, y);
    renderWhite(gl, x, y);
    x += 150.f;

    renderDst(gl, x, y);
    gl.glBlendFunc(gl.GL_ZERO(), gl.GL_ONE_MINUS_SRC_ALPHA());
    renderSrc(gl, x, y);
    renderWhite(gl, x, y);
    x += 150.f;

    renderDst(gl, x, y);
    gl.glBlendFunc(gl.GL_DST_ALPHA(), gl.GL_ONE_MINUS_SRC_ALPHA());
    renderSrc(gl, x, y);
    renderWhite(gl, x, y);
    x += 150.f;

    renderDst(gl, x, y);
    gl.glBlendFunc(gl.GL_ONE_MINUS_DST_ALPHA(), gl.GL_SRC_ALPHA());
    renderSrc(gl, x, y);
    renderWhite(gl, x, y);
    x += 150.f;

    renderDst(gl, x, y);
    gl.glBlendFunc(gl.GL_ONE_MINUS_DST_ALPHA(), gl.GL_ONE_MINUS_SRC_ALPHA());
    renderSrc(gl, x, y);
    renderWhite(gl, x, y);

    return true;
  }

  @Override
  public boolean endLoop() {
    return false;
  }

  private void renderDst(final CoreGL gl, final float x, final float y) {
    shader.setUniformf("uOffset", x, y);
    gl.glDisable(gl.GL_BLEND());
    dst.bind();
    coreRender.renderTriangles(6);
  }

  private void renderWhite(final CoreGL gl, final float x, final float y) {
    shader.setUniformf("uOffset", x, y);
    gl.glBlendFunc(gl.GL_ONE_MINUS_DST_ALPHA(), gl.GL_ONE());
    white.bind();
    coreRender.renderTriangles(6);
  }

  private void renderSrc(final CoreGL gl, final float x, final float y) {
    shader.setUniformf("uOffset", x, y);

    gl.glEnable(gl.GL_BLEND());
    src.bind();
    coreRender.renderTriangles(6);
  }

  public static void main(final String[] args) throws Exception {
    final RenderLoopCallback compositeExample = new CompositeExampleMain();
    CoreExampleMain.runExample(compositeExample, args);
  }
}
