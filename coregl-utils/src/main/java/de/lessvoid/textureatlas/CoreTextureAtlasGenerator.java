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
package de.lessvoid.textureatlas;

import java.nio.FloatBuffer;

import de.lessvoid.coregl.CoreFBO;
import de.lessvoid.coregl.CoreFactory;
import de.lessvoid.coregl.CoreRender;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreTexture2D;
import de.lessvoid.coregl.CoreTexture2D.ColorFormat;
import de.lessvoid.coregl.CoreTexture2D.ResizeFilter;
import de.lessvoid.coregl.CoreTexture2D.Type;
import de.lessvoid.coregl.CoreVAO;
import de.lessvoid.coregl.CoreVAO.FloatType;
import de.lessvoid.coregl.CoreVBO;
import de.lessvoid.coregl.CoreVBO.DataType;
import de.lessvoid.coregl.CoreVBO.UsageType;
import de.lessvoid.math.MatrixFactory;
import de.lessvoid.textureatlas.TextureAtlasGenerator.Result;

/**
 * This uses de.lessvoid.textureatlas.TextureAtlasGenerator while rendering individual CoreTextures into a
 * CoreRenderToTexture target.
 *
 * @author void
 */
public class CoreTextureAtlasGenerator {
  private CoreRender coreRender;
  private CoreFBO renderToTexture;
  private CoreVAO vao;
  private CoreVBO<FloatBuffer> vbo;
  private TextureAtlasGenerator generator;
  private CoreShader shader;
  private CoreTexture2D texture;

  /**
   * Prepare a RenderToTexture target of the given width x height that will be used as the rendering target for the
   * texture atlas algorithm.
   * 
   * @param width width of the texture
   * @param height height of the texture
   */
  public CoreTextureAtlasGenerator(final CoreFactory coreFactory, final int width, final int height) {
    coreRender = coreFactory.getCoreRender();

    renderToTexture = coreFactory.createCoreFBO();
    renderToTexture.bindFramebuffer();

    texture = coreFactory.createEmptyTexture(ColorFormat.RGBA, Type.UNSIGNED_BYTE, width, height, ResizeFilter.Linear);
    renderToTexture.attachTexture(texture.getTextureId(), 0);

    shader = coreFactory.newShaderWithVertexAttributes("aVertex", "aUV");
    shader.vertexShader("de/lessvoid/coregl/plain-texture.vs");
    shader.fragmentShader("de/lessvoid/coregl/plain-texture.fs");
    shader.link();
    shader.activate();
    shader.setUniformi("uTexture", 0);

    vao = coreFactory.createVAO();
    vao.bind();

    vbo = coreFactory.createVBO(DataType.FLOAT, UsageType.STREAM_DRAW, 4*4);
    vbo.bind();

    vao.enableVertexAttribute(0);
    vao.vertexAttribPointer(0, 2, FloatType.FLOAT, 4, 0);
    vao.enableVertexAttribute(1);
    vao.vertexAttribPointer(1, 2, FloatType.FLOAT, 4, 2);
    
    renderToTexture.bindFramebuffer();

    coreRender.clearColor(0.f, 0.f, 0.f, 0.f);
    coreRender.clearColorBuffer();

    renderToTexture.disableAndResetViewport();

    vao.unbind();

    generator = new TextureAtlasGenerator(width, height);
  }

  /**
   * Add a single CoreTexture2D to the atlas and return informations about the position in the texture atlas.
   *
   * @param texture the texture
   * @param name the name used to identify this texture
   * @param padding padding value around the texture when being placed into the atlas
   * @return the Result
   */
  public Result addImage(final CoreTexture2D texture, final String name, final int padding) {
    try {
      Result result = generator.addImage(texture.getWidth(), texture.getHeight(), name, padding);
      put(texture, result.getX(), result.getY());
      return result;
    } catch (TextureAtlasGeneratorException e) {
      return null;
    }
  }

  /**
   * The target texture allocated for the texture atlas. If you want to later render using the texture atlas you'll
   * need to call this and call bind() on it.
   * @return the CoreRenderToTexture allocated for the texture altas
   */
  public CoreTexture2D getTargetTexture() {
    return texture;
  }

  /**
   * Width of the texture atlas used.
   * @return width of the texture atlas
   */
  public int getWidth() {
    return texture.getWidth();
  }

  /**
   * Height of the texture atlas used.
   * @return height of the texture atlas
   */
  public int getHeight() {
    return texture.getHeight();
  }

  private void put(final CoreTexture2D source, final int x, final int y) {
    shader.activate();
    shader.setUniformMatrix4f("uMvp", MatrixFactory.createOrtho(0, texture.getWidth(), 0, texture.getHeight()).toBuffer());

    vao.bind();
    renderToTexture.bindFramebuffer();

    FloatBuffer buffer = vbo.getBuffer();
    buffer.put(x);
    buffer.put(y);
    buffer.put(0.0f);
    buffer.put(0.0f);

    buffer.put(x);
    buffer.put(y + source.getHeight());
    buffer.put(0.0f);
    buffer.put(1.0f);

    buffer.put(x + source.getWidth());
    buffer.put(y);
    buffer.put(1.0f);
    buffer.put(0.0f);

    buffer.put(x + source.getWidth());
    buffer.put(y + source.getHeight());
    buffer.put(1.0f);
    buffer.put(1.0f);
    buffer.rewind();
    vbo.bind();
    vbo.send();

    coreRender.renderTriangleStrip(4);
    vao.unbind();
    renderToTexture.disableAndResetViewport();
  }
}
