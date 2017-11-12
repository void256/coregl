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
package com.lessvoid.textureatlas;

import com.lessvoid.coregl.ColorFormat;
import com.lessvoid.coregl.CoreBuffer;
import com.lessvoid.coregl.CoreBufferDataType;
import com.lessvoid.coregl.CoreBufferUsageType;
import com.lessvoid.coregl.CoreFBO;
import com.lessvoid.coregl.CoreRender;
import com.lessvoid.coregl.CoreShader;
import com.lessvoid.coregl.CoreTexture2D;
import com.lessvoid.coregl.CoreVAO;
import com.lessvoid.coregl.CoreVAO.FloatType;
import com.lessvoid.coregl.ResizeFilter;
import com.lessvoid.coregl.Type;
import com.lessvoid.coregl.spi.CoreGL;
import com.lessvoid.math.MatrixFactory;

import java.nio.FloatBuffer;

import static com.lessvoid.coregl.CoreBufferTargetType.ARRAY_BUFFER;

/**
 * This uses TextureAtlasGenerator while rendering
 * individual CoreTextures into a CoreRenderToTexture target.
 *
 * @author void
 */
public class CoreTextureAtlasGenerator {
  private final CoreRender coreRender;
  private final CoreFBO renderToTexture;
  private final CoreVAO vao;
  private final CoreBuffer<FloatBuffer> vbo;
  private final TextureAtlasGenerator generator;
  private final CoreShader shader;
  private final CoreTexture2D texture;

  /**
   * Prepare a RenderToTexture target of the given width x height that will be
   * used as the rendering target for the texture atlas algorithm.
   *
   * @param width
   *          width of the texture
   * @param height
   *          height of the texture
   */
  public CoreTextureAtlasGenerator(final CoreGL gl, final int width, final int height) {
    coreRender = CoreRender.createCoreRender(gl);

    renderToTexture = CoreFBO.createCoreFBO(gl);
    renderToTexture.bindFramebuffer();

    texture = CoreTexture2D
        .createEmptyTexture(gl, ColorFormat.RGBA, Type.UNSIGNED_BYTE, width, height, ResizeFilter.Linear);
    renderToTexture.attachTexture(texture.getTextureId(), 0);

    shader = CoreShader.createShaderWithVertexAttributes(gl, "aVertex", "aUV");
    shader.vertexShader("com/lessvoid/coregl/plain-texture.vs");
    shader.fragmentShader("com/lessvoid/coregl/plain-texture.fs");
    shader.link();
    shader.activate();
    shader.setUniformi("uTexture", 0);

    vao = CoreVAO.createCoreVAO(gl);
    vao.bind();

    vbo = CoreBuffer.createCoreBufferObject(gl, CoreBufferDataType.FLOAT, CoreBufferUsageType.STREAM_DRAW, 4 * 4);
    vbo.bind(ARRAY_BUFFER);

    vao.enableVertexAttribute(0);
    vao.vertexAttribPointer(0, 2, FloatType.FLOAT, 4, 0);
    vao.enableVertexAttribute(1);
    vao.vertexAttribPointer(1, 2, FloatType.FLOAT, 4, 2);

    renderToTexture.bindFramebuffer();

    coreRender.clearColor(0.f, 0.f, 0.f, 0.f);
    coreRender.clearColorBuffer();

    renderToTexture.disableAndResetViewport(width, height);

    vao.unbind();

    generator = new TextureAtlasGenerator(width, height);
  }

  /**
   * Add a single CoreTexture2D to the atlas and return informations about the
   * position in the texture atlas.
   *
   * @param texture
   *          the texture
   * @param name
   *          the name used to identify this texture
   * @param padding
   *          padding value around the texture when being placed into the atlas
   * @return the Result
   */
  public TextureAtlasGenerator.Result addImage(final CoreTexture2D texture, final String name, final int padding) {
    try {
      final TextureAtlasGenerator.Result result = generator.addImage(texture.getWidth(), texture.getHeight(), name, padding);
      put(texture, result.getX(), result.getY());
      return result;
    } catch (final TextureAtlasGeneratorException e) {
      return null;
    }
  }

  /**
   * The target texture allocated for the texture atlas. If you want to later
   * render using the texture atlas you'll need to call this and call bind() on
   * it.
   * 
   * @return the CoreRenderToTexture allocated for the texture altas
   */
  public CoreTexture2D getTargetTexture() {
    return texture;
  }

  /**
   * Width of the texture atlas used.
   * 
   * @return width of the texture atlas
   */
  public int getWidth() {
    return texture.getWidth();
  }

  /**
   * Height of the texture atlas used.
   * 
   * @return height of the texture atlas
   */
  public int getHeight() {
    return texture.getHeight();
  }

  private void put(final CoreTexture2D source, final int x, final int y) {
    shader.activate();
    shader.setUniformMatrix("uMvp",
                            4,
                            MatrixFactory.createOrtho(0, texture.getWidth(), 0, texture.getHeight()).toBuffer());

    vao.bind();
    renderToTexture.bindFramebuffer();

    final FloatBuffer buffer = vbo.getBuffer();
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
    vbo.bind(ARRAY_BUFFER);
    vbo.send(ARRAY_BUFFER);

    coreRender.renderTriangleStrip(4);
    vao.unbind();
    renderToTexture.disableAndResetViewport(source.getWidth(), source.getHeight());
  }
}
