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
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import de.lessvoid.coregl.CoreFactory;
import de.lessvoid.coregl.CoreSetup;
import de.lessvoid.coregl.CoreSetup.RenderLoopCallback;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreTexture2D;
import de.lessvoid.coregl.CoreTexture2D.ColorFormat;
import de.lessvoid.coregl.CoreTexture2D.ResizeFilter;
import de.lessvoid.coregl.CoreVAO;
import de.lessvoid.coregl.CoreVBO;
import de.lessvoid.coregl.lwjgl.CoreFactoryLwjgl;
import de.lessvoid.math.MatrixFactory;
import de.lessvoid.math.Mat4;
import de.lessvoid.math.Vec4;
import de.lessvoid.simpleimageloader.ImageData;
import de.lessvoid.simpleimageloader.SimpleImageLoader;

/**
 * The GeometryShaderExampleMain demonstrates the lwjgl-core-utils API to use an geometry shader. It will render a
 * quad from a single GL_POINT.
 *
 * @author void
 */
public class GeometryShaderExampleMain implements RenderLoopCallback {
  int vertexCount = 1000;
  int dataPerVertex = 28;
  CoreVBO vbo;
  private CoreFactory factory;

  public GeometryShaderExampleMain(final CoreFactory factory) throws Exception {
    this.factory = factory;
    CoreShader shader = factory.newShader();
    shader.vertexShader("geometry-shader/vertex.vs");
    shader.fragmentShader("geometry-shader/fragment.fs");
    shader.geometryShader("geometry-shader/geometry.gs");
    shader.link();

    CoreVAO vao = factory.createVAO();
    vao.bind();

    float[] vertexData = new float[vertexCount*dataPerVertex];
    int index = 0;
    for (int i=0; i<vertexCount; i++) {
      vertexData[index++] = (float)Math.random() * (1024.f - 50.f);
      vertexData[index++] = (float)Math.random() * (768.f - 50.f);
      vertexData[index++] = (float)Math.random() * 50.f + 50.f;
      vertexData[index++] = (float)Math.random() * 50.f + 50.f;

      vertexData[index++] = (float)Math.random();
      vertexData[index++] = (float)Math.random();
      vertexData[index++] = (float)Math.random();
      vertexData[index++] = (float)Math.random();

      vertexData[index++] = (float)Math.random();
      vertexData[index++] = (float)Math.random();
      vertexData[index++] = (float)Math.random();
      vertexData[index++] = (float)Math.random();

      vertexData[index++] = (float)Math.random();
      vertexData[index++] = (float)Math.random();
      vertexData[index++] = (float)Math.random();
      vertexData[index++] = (float)Math.random();

      vertexData[index++] = (float)Math.random();
      vertexData[index++] = (float)Math.random();
      vertexData[index++] = (float)Math.random();
      vertexData[index++] = (float)Math.random();

      vertexData[index++] = 0.5f;
      vertexData[index++] = 0.5f;

      vertexData[index++] = 0.0001f;//1.0f;
      vertexData[index++] = 0.5f;

      vertexData[index++] = 0.5f;
      vertexData[index++] = 0.0001f;//1.0f;
      
      vertexData[index++] = 0.0001f;//1.0f;
      vertexData[index++] = 0.0001f;//1.0f;
  };
    
    vbo = factory.createDynamic(vertexData);
    vbo.send();

    // parameters are: index, size, stride, offset
    // this will use the currently active VBO to store the VBO in the VAO
    vao.enableVertexAttributef(shader.getAttribLocation("aVertex"), 4, dataPerVertex, 0);
    vao.enableVertexAttributef(shader.getAttribLocation("aColor1"), 4, dataPerVertex, 4);
    vao.enableVertexAttributef(shader.getAttribLocation("aColor2"), 4, dataPerVertex, 8);
    vao.enableVertexAttributef(shader.getAttribLocation("aColor3"), 4, dataPerVertex, 12);
    vao.enableVertexAttributef(shader.getAttribLocation("aColor4"), 4, dataPerVertex, 16);
    vao.enableVertexAttributef(shader.getAttribLocation("aUV1"), 4, dataPerVertex, 20);
    vao.enableVertexAttributef(shader.getAttribLocation("aUV2"), 4, dataPerVertex, 24);

    // load texture
    SimpleImageLoader loader = new SimpleImageLoader();
    ImageData imageData = loader.load("demo.png", GeometryShaderExampleMain.class.getResourceAsStream("/nifty-logo-150x150.png"));

    CoreTexture2D texture = factory.createTexture(ColorFormat.RGBA, imageData.getWidth(), imageData.getHeight(), imageData.getData(), ResizeFilter.Linear);
    texture.bind();

    // we only use a single shader and a single vao so we can activate both her
    // and let them stay active the whole time.
    shader.activate();
    shader.setUniformMatrix4f("uMvp", MatrixFactory.createOrtho(0, 1024.f, 768.f, 0).toBuffer());
    shader.setUniformf("uWidth", 1024.f);
    shader.setUniformf("uHeight", 768.f);
    shader.setUniformi("uTex", 0);

    System.out.println(Mat4.transform(MatrixFactory.createOrtho(0, 1024.f, 768.f, 0), new Vec4(10.0f, 758.0f, 0.0f, 1.0f), null));
    vao.bind();
  }

  @Override
  public boolean render(final float deltaTime) {
    glClearColor(.1f, .1f, .3f, 0.f);
    glClear(GL_COLOR_BUFFER_BIT);

    // render all the data in the currently active vao using triangle strips
    factory.getCoreRender().renderPoints(vertexCount);
    return false;
  }

  public static void main(final String[] args) throws Exception {
    CoreFactory factory = new CoreFactoryLwjgl();
    CoreSetup setup = factory.createSetup();
    setup.initializeLogging(); // optional to get jdk14 to better format the log
    setup.initialize("Hello Lwjgl Core GL", 1024, 768);
    setup.renderLoop(new GeometryShaderExampleMain(factory));
  }
}
