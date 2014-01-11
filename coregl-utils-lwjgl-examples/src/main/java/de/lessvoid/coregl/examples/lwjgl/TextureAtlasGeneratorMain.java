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

import java.io.File;
import java.io.FilenameFilter;
import java.nio.FloatBuffer;

import de.lessvoid.coregl.CoreFactory;
import de.lessvoid.coregl.CoreSetup;
import de.lessvoid.coregl.CoreSetup.RenderLoopCallback;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreTexture2D;
import de.lessvoid.coregl.CoreTexture2D.ColorFormat;
import de.lessvoid.coregl.CoreTexture2D.ResizeFilter;
import de.lessvoid.coregl.CoreVAO;
import de.lessvoid.coregl.CoreVAO.FloatType;
import de.lessvoid.coregl.CoreVBO;
import de.lessvoid.coregl.CoreVBO.DataType;
import de.lessvoid.coregl.CoreVBO.UsageType;
import de.lessvoid.coregl.lwjgl.CoreFactoryLwjgl;
import de.lessvoid.math.MatrixFactory;
import de.lessvoid.simpleimageloader.ImageData;
import de.lessvoid.simpleimageloader.SimpleImageLoader;
import de.lessvoid.simpleimageloader.SimpleImageLoaderConfig;
import de.lessvoid.textureatlas.CoreTextureAtlasGenerator;

public class TextureAtlasGeneratorMain implements RenderLoopCallback {
  private SimpleImageLoader loader = new SimpleImageLoader();

  private CoreVAO vao;
  private CoreVBO<FloatBuffer> vbo;
  private CoreShader shader;
  private CoreTexture2D textureAtlas;
  private CoreFactory factory;

  public TextureAtlasGeneratorMain(final CoreFactory factory) throws Exception {
    this.factory = factory;

    shader = factory.newShaderWithVertexAttributes("aVertex", "aUV");
    shader.vertexShader("texture-atlas/texture.vs");
    shader.fragmentShader("texture-atlas/texture.fs");
    shader.link();
    shader.activate();
    shader.setUniformi("uTexture", 0);

    vao = factory.createVAO();
    vao.bind();

    vbo = factory.createVBO(DataType.FLOAT, UsageType.STATIC_DRAW, 4*4);
    vbo.bind();

    vao.vertexAttribPointer(0, 2, FloatType.FLOAT, 4, 0);
    vao.vertexAttribPointer(1, 2, FloatType.FLOAT, 4, 2);
    vao.enableVertexAttribute(0);
    vao.enableVertexAttribute(1);

    CoreTextureAtlasGenerator generator = new CoreTextureAtlasGenerator(factory, 1024, 1024);
    File base = new File("src/main/resources/texture-atlas");
    for (String f : base.list(new PNGFileFilter())) {
      String filename = "/texture-atlas/" + f;
      ImageData imageData = loader.load(filename, GeometryShaderExampleMain.class.getResourceAsStream(filename), new SimpleImageLoaderConfig().forceAlpha());
      CoreTexture2D texture = factory.createTexture(ColorFormat.RGBA, imageData.getWidth(), imageData.getHeight(), imageData.getData(),  ResizeFilter.Linear);
      if (null == generator.addImage(texture, filename, 5)) {
        System.out.println("failed to add image: " + filename);
      }
    }
    textureAtlas = generator.getTargetTexture();
  }

  @Override
  public boolean render(final float deltaTime) {
    glClearColor(.1f, .1f, .3f, 0.f);
    glClear(GL_COLOR_BUFFER_BIT);

    textureAtlas.bind();

    FloatBuffer buffer = vbo.getBuffer();
    buffer.put(0.f);
    buffer.put(0.f);
    buffer.put(0.0f);
    buffer.put(0.0f);

    buffer.put(0.f);
    buffer.put(0.f + 768);
    buffer.put(0.0f);
    buffer.put(1.0f);
    
    buffer.put(0.f + 1024);
    buffer.put(0.f);
    buffer.put(1.0f);
    buffer.put(0.0f);
    
    buffer.put(0.f + 1024);
    buffer.put(0.f + 768);
    buffer.put(1.0f);
    buffer.put(1.0f);
    buffer.rewind();

    vbo.send();
    vao.bind();

    shader.setUniformMatrix4f("uMvp", MatrixFactory.createOrtho(0, 1024.f, 768.f, 0).toBuffer());
    factory.getCoreRender().renderTriangleStrip(4);
    return false;
  }

  public static void main(final String[] args) throws Exception {
    CoreFactory factory = CoreFactoryLwjgl.create();
    CoreSetup setup = factory.createSetup();
    setup.initializeLogging(); // optional to get jdk14 to better format the log
    setup.initialize("Texture Atlas Generator", 1024, 768);
    setup.renderLoop(new TextureAtlasGeneratorMain(factory));
  }

  private static class PNGFileFilter implements FilenameFilter {
    @Override
    public boolean accept(File dir, String name) {
      return name.endsWith(".png");
    }
  }
}
