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
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL14.GL_FUNC_ADD;
import static org.lwjgl.opengl.GL14.GL_MAX;
import static org.lwjgl.opengl.GL20.glBlendEquationSeparate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.charset.Charset;

import org.lwjgl.opengl.Display;

import de.lessvoid.coregl.CoreFBO;
import de.lessvoid.coregl.CoreFactory;
import de.lessvoid.coregl.CoreSetup;
import de.lessvoid.coregl.CoreSetup.RenderLoopCallback;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreTexture2D;
import de.lessvoid.coregl.CoreTexture2D.ColorFormat;
import de.lessvoid.coregl.CoreTexture2D.ResizeFilter;
import de.lessvoid.coregl.CoreTexture2D.Type;
import de.lessvoid.coregl.CoreVAO;
import de.lessvoid.coregl.CoreVAO.FloatType;
import de.lessvoid.coregl.CoreVBO;
import de.lessvoid.coregl.lwjgl.CoreFactoryLwjgl;
import de.lessvoid.math.Mat4;
import de.lessvoid.math.MatrixFactory;

public class LineMain implements RenderLoopCallback {
  private static final Mat4 ORTHO = MatrixFactory.createOrtho(0, 1024.f, 768.f, 0);
  private final CoreShader texture;
  private final CoreShader lineShader1;
  private final CoreShader lineShader2;
  private final CoreVAO src;
  private final CoreVBO vbo;
  private final CoreVBO vboQuad;
  private final CoreVBO vboBackground;
  private final CoreFBO fbo;
  private float totalTime;
  private final CoreTexture2D fboTexture;
  private final CoreShader backgroundShader;
  private float time;
  private CoreFactory factory;

  public LineMain(final CoreFactory factory) throws Exception {
    this.factory = factory;

    texture = factory.newShaderWithVertexAttributes("aVertex", "aUV");
    texture.vertexShader("line/line-pass2.vs");
    texture.fragmentShader("line/line-pass2.fs");
    texture.link();

    lineShader1 = factory.newShaderWithVertexAttributes("aVertex");
    lineShader1.vertexShader("line/line.vs");
    lineShader1.geometryShader("line/line.gs", stream("#version 150 core\n#define CAP_ROUND\n#define JOIN_NONE\n"), resource("line/line.gs"));
    lineShader1.fragmentShader("line/line.fs", stream("#version 150 core\n#define CAP_ROUND\n#define JOIN_NONE\n"), resource("line/line.fs"));
    lineShader1.link();

    lineShader2 = factory.newShaderWithVertexAttributes("aVertex");
    lineShader2.vertexShader("line/line.vs");
    lineShader2.geometryShader("line/line.gs", stream("#version 150 core\n#define CAP_BUTT\n#define JOIN_NONE\n"), resource("line/line.gs"));
    lineShader2.fragmentShader("line/line.fs", stream("#version 150 core\n#define CAP_BUTT\n#define JOIN_NONE\n"), resource("line/line.fs"));
    lineShader2.link();

    src = factory.createVAO();
    src.bind();

    vbo = factory.createVBODynamic(new float[2*5]);
    src.vertexAttribPointer(0, 2, FloatType.FLOAT, 2, 0);
    totalTime = 0;

    vboQuad = factory.createVBODynamic(new float[4*4]);
    vboBackground = factory.createVBODynamic(new float[5*4]);

    fbo = factory.createCoreFBO();
    fbo.bindFramebuffer();

    fboTexture = factory.createEmptyTexture(ColorFormat.Red, Type.UNSIGNED_BYTE, Display.getWidth(), Display.getHeight(), ResizeFilter.Linear);
    fbo.attachTexture(fboTexture.getTextureId(), 0);

    backgroundShader = factory.newShaderWithVertexAttributes("aVertex", "aColor");
    backgroundShader.vertexShader("background-gradient.vs");
    backgroundShader.fragmentShader("background-gradient.fs");
    backgroundShader.link();
  }

  private InputStream resource(final String name) {
    return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
  }

  private InputStream stream(final String data) {
    return new ByteArrayInputStream(data.getBytes(Charset.forName("ISO-8859-1")));
  }

  @Override
  public boolean render(final float deltaTime) {
    time += deltaTime;

    fbo.bindFramebuffer();
    glViewport(0, 0, Display.getWidth(), Display.getHeight());

    glClearColor(0.f, 0.f, 0.f, 0.f);
    glClear(GL_COLOR_BUFFER_BIT);
//    glClearStencil(0);
//    glStencilFuncSeparate(GL_FRONT_AND_BACK, GL_EQUAL, 0, 0xff);
//    glStencilOpSeparate(GL_FRONT_AND_BACK, GL_KEEP, GL_KEEP, GL_INCR);
//    glStencilMask(0xFF);
//    glEnable(GL_STENCIL_TEST);
    
    glEnable(GL_BLEND);
    glBlendEquationSeparate(GL_MAX, GL_MAX);

    totalTime += deltaTime;
    float w = 100.f;
    float r = 2.f;

    lineShader1.activate();
    lineShader1.setUniformMatrix4f("uMvp", MatrixFactory.createOrtho(0, 1024.f, 0, 768.f).toBuffer());
    lineShader1.setUniformf("lineColor", 1.f, 1.f, 1.f, (float)((Math.sin(time/1700.f) + 1.0) / 2.0));
    lineShader1.setUniformf("lineParameters", (2*r + w), (2*r + w) / 2.f, (2*r + w) / 2.f - 2 * r, (2*r));

    FloatBuffer buffer = vbo.getFloatBuffer();
    buffer.put(100.f);
    buffer.put(100.f);
    buffer.put(100.f);
    buffer.put(100.f);
    buffer.put(600.f);
    buffer.put(300.f);
    buffer.put(600.f + (float)Math.cos(totalTime/1500.f)*200.f);
    buffer.put(300.f + (float)Math.sin(totalTime/1500.f)*200.f);
    buffer.put(600.f + (float)Math.cos(totalTime/1500.f)*200.f);
    buffer.put(300.f + (float)Math.sin(totalTime/1500.f)*200.f);
    buffer.rewind();
    src.vertexAttribPointer(0, 2, FloatType.FLOAT, 2, 0);
    src.disableVertexAttribute(1);
    vbo.send();
    factory.getCoreRender().renderLinesAdjacent(5);
    fbo.disable();

    // Render background
    glDisable(GL_BLEND);

    FloatBuffer background = vboBackground.getFloatBuffer();
    background.put(0.f);
    background.put(0.f);
    background.put(1.f);
    background.put(0.f);
    background.put(0.f);

    background.put(0.f);
    background.put(0.f + 768);
    background.put(0.0f);
    background.put(1.0f);
    background.put(0.0f);

    background.put(0.f + 1024);
    background.put(0.f);
    background.put(0.0f);
    background.put(0.0f);
    background.put(1.0f);

    background.put(0.f + 1024);
    background.put(0.f + 768);
    background.put(1.0f);
    background.put(1.0f);
    background.put(1.0f);
    background.rewind();

    vboBackground.bind();
    src.vertexAttribPointer(0, 2, FloatType.FLOAT, 5, 0);
    src.vertexAttribPointer(1, 3, FloatType.FLOAT, 5, 2);
    vboBackground.send();

    glViewport(0, 0, Display.getWidth(), Display.getHeight());
    backgroundShader.activate();
    backgroundShader.setUniformMatrix4f("uMvp", ORTHO.toBuffer());
    factory.getCoreRender().renderTriangleStrip(4);

    // Render lines fbo

    glEnable(GL_BLEND);
    glBlendFunc (GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glBlendEquationSeparate(GL_FUNC_ADD, GL_FUNC_ADD);

    FloatBuffer quad = vboQuad.getFloatBuffer();
    quad.put(0.f);
    quad.put(0.f);
    quad.put(0.f);
    quad.put(0.f);

    quad.put(0.f);
    quad.put(0.f + 768);
    quad.put(0.0f);
    quad.put(1.0f);

    quad.put(0.f + 1024);
    quad.put(0.f);
    quad.put(1.0f);
    quad.put(0.0f);

    quad.put(0.f + 1024);
    quad.put(0.f + 768);
    quad.put(1.0f);
    quad.put(1.0f);
    quad.rewind();

    vboQuad.bind();
    src.vertexAttribPointer(0, 2, FloatType.FLOAT, 4, 0);
    src.vertexAttribPointer(1, 2, FloatType.FLOAT, 4, 2);
    vboQuad.send();

    fboTexture.bind();
    texture.activate();
    texture.setUniformMatrix4f("uMvp", ORTHO.toBuffer());
    texture.setUniformi("uTexture", 0);
    texture.setUniformf("lineColor", 1.f, 1.f, 1.f, 1.f);
    factory.getCoreRender().renderTriangleStrip(4);
    return false;
  }

  public static void main(final String[] args) throws Exception {
    CoreFactory factory = CoreFactoryLwjgl.create();
    CoreSetup setup = factory.createSetup();
    setup.initializeLogging();
    setup.initialize("Hello line caps", 1024, 768);
    setup.renderLoop(new LineMain(factory));
  }
}
