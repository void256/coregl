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
package de.lessvoid.coregl;

import java.nio.Buffer;


/**
 * The CoreTexture2D represents a 2D texture in OpenGL space.
 * <p />
 * This class takes care for loading the texture to OpenGL and for generating mipmaps as needed.
 * <p />
 * This class does <b>not</b> handle proxy textures.
 *
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
public interface CoreTexture2D {
  /**
   * Image resizing mode. This enumerator is used in simple and defines the used filter for the magnifying and
   * minimizing automatically.
   */
  public enum ResizeFilter {
    /**
     * Nearest filter. This filter applies the nearest filter to both the magnifying and the minimizing filter.
     */
    Nearest,

    /**
     * Linear filter. This applies the linear filter to ot the magnifying and the minimizing filter.
     */
    Linear,

    /**
     * This filter applies the linear nearest to the magnifying filter and the nearest mipmap nearest filter to the
     * minimizing filter. This filter is the fastest mipmap based filtering
     */
    NearestMipMapNearest,

    /**
     * This filter applies the linear linear to the magnifying filter and the linear mipmap nearest filter to the
     * minimizing filter. This filter is slightly slower then {@link #NearestMipMapNearest} but creates a better
     * quality.
     */
    NearestMipMapLinear,

    /**
     * This filter applies the linear linear to the magnifying filter and the linear mipmap nearest linear to the
     * minimizing filter. This filter is slightly slower then {@link #NearestMipMapLinear} but creates a better
     * quality.
     */
    LinearMipMapNearest,

    /**
     * This filter applies the linear linear to the magnifying filter and the linear mipmap linear linear to the
     * minimizing filter. This filter creates the best quality but is also the slowest filter method.
     */
    LinearMipMapLinear;
  }

  /**
   * This enumerator contains the default settings for different color values the texture could be stored as.
   */
  public enum ColorFormat {
    /**
     * In case this format is used the pixel data is expected to contain only one color channel. This color channel is
     * used as the <b>red</b> color of the newly created RGB texture.
     */
    Red,

    /**
     * In case this format is used the pixel data is expected to contain only one color channel. This color channel is
     * used as the <b>green</b> color of the newly created RGB texture.
     */
    Green,

    /**
     * In case this format is used the pixel data is expected to contain only one color channel. This color channel is
     * used as the <b>blue</b> color of the newly created RGB texture.
     */
    Blue,

    /**
     * In case this format is used the pixel data is expected to contain only one color channel. This color channel is
     * used as the <b>alpha</b> color of the newly created alpha texture.
     */
    Alpha,

    /**
     * In case this format is used the pixel data is expected to contain three color channels. The colors are
     * <b>red</b>, <b>green</b> and <b>blue</b> in this order. The created texture will be a RGB texture.
     */
    RGB,

    /**
     * In case this format is used the pixel data is expected to contain three color channels. The colors are
     * <b>blue</b>, <b>green</b> and <b>red</b> in this order. The created texture will be a RGB texture.
     */
    BGR,

    /**
     * In case this format is used the pixel data is expected to contain four color channels. The colors are
     * <b>red</b>, <b>green</b>, <b>blue</b> and <b>alpha</b> in this order. The created texture will be a RGBA texture.
     */
    RGBA,

    /**
     * In case this format is used the pixel data is expected to contain four color channels. The colors are
     * <b>blue</b>, <b>green</b>, <b>red</b> and <b>alpha</b> in this order. The created texture will be a RGBA texture.
     */
    BGRA,

    /**
     * In case this format is used the pixel data is expected to contain only one color channels. The color is used
     * as luminance level, so the created texture will be a gray-scale texture.
     */
    Luminance,

    /**
     * In case this format is used the pixel data is expected to contain only two color channels. The channels are
     * expected to be the <b>luminance</b> and the <b>alpha</b> channel. The created texture will be gray-scale texture
     * with transparency.
     */
    LuminanceAlpha;
  }

  public enum Type {
    UNSIGNED_BYTE,
    BYTE,
    UNSIGNED_SHORT,
    SHORT,
    UNSIGNED_INT,
    INT,
    FLOAT,
    UNSIGNED_BYTE_3_3_2,
    UNSIGNED_BYTE_2_3_3_REV,
    UNSIGNED_SHORT_5_6_5,
    UNSIGNED_SHORT_5_6_5_REV,
    UNSIGNED_SHORT_4_4_4_4,
    UNSIGNED_SHORT_4_4_4_4_REV,
    UNSIGNED_SHORT_5_5_5_1,
    UNSIGNED_SHORT_1_5_5_5_REV,
    UNSIGNED_INT_8_8_8_8,
    UNSIGNED_INT_8_8_8_8_REV,
    UNSIGNED_INT_10_10_10_2,
    UNSIGNED_INT_2_10_10_10_REV;
  }
  
  /**
   * Fetch the maximal allowed size of a texture.
   *
   * @return the maximal size of a texture in pixels
   * @throws CoreGLException in case reading the maximal texture size from OpenGL failed
   */
  int getMaxTextureSize();

  /**
   * Check if non-power-of-two textures are supported hardware-accelerated.
   *
   * @return {@code true} in case non-power-of-two are supported hardware-accelerated
   */
  boolean isNPOTHardwareSupported();

  /**
   * Check if non-power-of-two textures are supported. The support is maybe done in software and so very slow.
   *
   * @return {@code true} in case this OpenGL context is able to handle non-power-of-two textures
   * @see #isNPOTHardwareSupported()
   */
  boolean isNPOTSupported();

  /**
   * Calling this functions causes the error checks of this function to be disabled. No matter the nonsense you send
   * into this class, everything will reach OpenGL. This should be done in case you are worries about the extra overhead
   * caused by the error checks. Also in case you just don't ever do mistakes you can safely disables those checks.
   */
  void disableErrorChecking();

  /**
   * Calling this function causes the error checks to be enabled again. By default the error checks are active. It is
   * only needed to call this in case you disabled the checks sometime before.
   */
  void enableErrorChecking();

  /**
   * Bind the texture to the current context.
   *
   * @throws CoreGLException in case binding the texture fails
   */
  void bind();

  /**
   * Dispose this texture.
   *
   * @throws CoreGLException in case disposing the texture fails
   */
  void dispose();

  /**
   * Return the texture id of the texture.
   * @return the textureId
   */
  int getTextureId();

  /**
   * Get the height of this texture.
   *
   * @return the height of the texture
   */
  int getHeight();

  /**
   * Get the width of this texture.
   *
   * @return the width of the texture
   */
  int getWidth();

  /**
   * Update the texture data with the data from the given buffer.
   * @param pixels the new pixels
   */
  void updateTextureData(Buffer pixels);

  /**
   * Update the texture data with the data from the given buffer.
   * @param pixels the new pixels
   */
  void updateTextureData3D(Buffer pixels);

}
