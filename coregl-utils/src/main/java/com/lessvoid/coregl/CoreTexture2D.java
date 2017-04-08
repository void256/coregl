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
package com.lessvoid.coregl;

import com.lessvoid.coregl.spi.CoreGL;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * The CoreTexture2D represents a 2D texture in OpenGL space.
 * <p />
 * This class takes care for loading the texture to OpenGL and for generating
 * mipmaps as needed.
 * <p />
 * This class does <b>not</b> handle proxy textures.
 *
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
public class CoreTexture2D {

  /**
   * This constant can be used as the data type to enable the automated
   * selection of the data type. Also his value is used for the texture ID in
   * case the class is supposed to acquire the texture ID automatically.
   */
  private static final int AUTO = -1;

  /**
   * The logger used to spam your logfile.
   */
  private static final Logger LOG = Logger.getLogger(CoreTexture2D.class.getName());

  /**
   * The buffered value of the maximal texture size.
   */
  private static int maxTextureSize = -1;

  /**
   * As long as this variable is set {@code true} the class will perform checks
   * for errors.
   */
  private static boolean errorChecks = true;

  /**
   * This flag is switched {@code true} once the texture is disposed. It is
   * afterwards used for two things. First to throw a exception in case a
   * disposed texture is bound again, secondly to show a warning in case the
   * java garbage collector consumes this class while the assigned texture was
   * not disposed.
   */
  private boolean isDisposed;

  /**
   * The instance of CoreGL that this CoreTexture2D uses to access OpenGL
   * functions.
   */
  private final CoreGL gl;

  /**
   * The target type of the texture.
   */
  private final int textureTarget;

  /**
   * The ID of the texture.
   */
  private final int textureId;

  /**
   * The width of the texture.
   */
  private final int width;

  /**
   * The height of the texture.
   */
  private final int height;

  /**
   * We remember the parameters used for the glTexture2D call so we can easily
   * update the texture if we need later.
   */
  private boolean textureCanBeUpdated;
  private int texImageTarget;
  private int texImageLevel;
  private int texImageInternalFormat;
  private int texImageWidth;
  private int texImageHeight;
  private int texBorder;
  private int texFormat;
  private int texType;
  private int texDepth;

  /**
   * Create an empty texture.
   *
   * @param format
   *          the ColorFormat for the texture
   * @param dataType
   *          the data type you'll later send the pixel data
   * @param width
   *          the width of the texture
   * @param height
   *          the height of the texture
   * @param filter
   *          the ResizeFilter to use
   * @return the new allocated CoreTexture2D
   */
  public static CoreTexture2D createEmptyTexture(final CoreGL gl,
                                                 final ColorFormat format,
                                                 final Type dataType,
                                                 final int width,
                                                 final int height,
                                                 final ResizeFilter filter) {
    final CoreTexture2DConstants texMapConsts = new CoreTexture2DConstants(gl);

    final ByteBuffer buffer = CoreBufferUtil
        .createByteBuffer(width * height * texMapConsts.getColorInfo(format).componentsPerPixel);
    final byte[] data = new byte[width * height * texMapConsts.getColorInfo(format).componentsPerPixel];
    buffer.put(data);
    buffer.flip();

    return new CoreTexture2D(gl,
                             texMapConsts,
                             AUTO,
                             gl.GL_TEXTURE_2D(),
                             0,
                             texMapConsts.getColorInfo(format).internalFormat,
                             width,
                             height,
                             0,
                             texMapConsts.getColorInfo(format).format,
                             texMapConsts.getTypeInfo(dataType).internalType,
                             buffer,
                             texMapConsts.getResizeFilter(filter).magFilter,
                             texMapConsts.getResizeFilter(filter).minFilter);
  }

  /**
   * Create an empty texture.
   *
   * @param format
   *          the ColorFormat for the texture
   * @param dataType
   *          the data type you'll later send the pixel data
   * @param width
   *          the width of the texture
   * @param height
   *          the height of the texture
   * @param filter
   *          the ResizeFilter to use
   * @return the new allocated CoreTexture2D
   */
  public static CoreTexture2D createEmptyTextureArray(final CoreGL gl,
                                                      final ColorFormat format,
                                                      final Type dataType,
                                                      final int width,
                                                      final int height,
                                                      final int num,
                                                      final ResizeFilter filter) {
    // FIXME works only for ColorFormat.RGBA at the moment! add other formats
    final ByteBuffer buffer = CoreBufferUtil.createByteBuffer(width * height * 4 * num);
    final byte[] data = new byte[width * height * 4 * num];
    buffer.put(data);
    buffer.flip();

    final CoreTexture2DConstants texMaps = new CoreTexture2DConstants(gl);

    return new CoreTexture2D(gl,
                             texMaps,
                             AUTO,
                             gl.GL_TEXTURE_2D_ARRAY(),
                             0,
                             texMaps.getColorInfo(format).internalFormat,
                             width,
                             height,
                             0,
                             texMaps.getColorInfo(format).format,
                             dataType,
                             buffer,
                             texMaps.getResizeFilter(filter).magFilter,
                             texMaps.getResizeFilter(filter).minFilter,
                             true,
                             num);
  }

  public static CoreTexture2D createCoreTexture(final CoreGL gl,
                                                final ColorFormat rgba,
                                                final int width,
                                                final int height,
                                                final ByteBuffer data,
                                                final ResizeFilter linear) {
    return new CoreTexture2D(gl, new CoreTexture2DConstants(gl), rgba, width, height, data, linear);
  }

  /**
   * This is one of the simple constructors that only allow very limited
   * possibilities for settings. How ever they use settings that should fit the
   * need on most cases.
   *
   * @param format
   *          the texture format
   * @param width
   *          the width of the texture
   * @param height
   *          the height of the texture
   * @param data
   *          the pixel data
   * @param filter
   *          the used filter
   * @throws CoreGLException
   *           in case the creation of the texture fails for any reason
   */
  private CoreTexture2D(final CoreGL gl, final CoreTexture2DConstants texMaps, final ColorFormat format,
      final int width, final int height, final Buffer data, final ResizeFilter filter) {
    this(gl, texMaps, format, false, width, height, data, filter);
  }

  /**
   * This is the constructor is a slightly reduced version that defines some
   * common options automatically.
   *
   * @param internalFormat
   *          the internal format of the texture
   * @param width
   *          the width of the texture in pixels
   * @param height
   *          the height of the texture in pixels
   * @param format
   *          the format of the pixel data
   * @param data
   *          the pixel data
   * @param filter
   *          the used filter
   * @throws CoreGLException
   *           in case the creation of the texture fails for any reason
   */
  private CoreTexture2D(final CoreGL gl, final CoreTexture2DConstants texMaps, final int internalFormat,
      final int width, final int height, final int format, final Buffer data, final ResizeFilter filter) {
    this(gl, texMaps, AUTO, gl.GL_TEXTURE_2D(), 0, internalFormat, width, height, 0, format, AUTO, data,
        texMaps.getResizeFilter(filter).magFilter, texMaps.getResizeFilter(filter).minFilter);
  }

  /**
   * This is one of the simple constructors that only allow very limited
   * possibilities for settings. How ever they use settings that should fit the
   * need on most cases.
   *
   * @param format
   *          the texture format
   * @param compressed
   *          {@code true} in case the internal texture data is supposed to be
   *          compressed if possible
   * @param width
   *          the width of the texture
   * @param height
   *          the height of the texture
   * @param data
   *          the pixel data
   * @param filter
   *          the used filter
   * @throws CoreGLException
   *           in case the creation of the texture fails for any reason
   */
  private CoreTexture2D(final CoreGL gl, final CoreTexture2DConstants texMaps, final ColorFormat format,
      final boolean compressed, final int width, final int height, final Buffer data, final ResizeFilter filter) {
    this(gl, texMaps, texMaps.getColorInfo(format).internalFormat, width, height,
        (compressed ? texMaps.getColorInfo(format).compressedInternalFormat : texMaps.getColorInfo(format).format),
        data, filter);
  }

  /**
   * This is the constructor is a slightly reduced version that defines some
   * common options automatically.
   *
   * @param internalFormat
   *          the internal format of the texture
   * @param width
   *          the width of the texture in pixels
   * @param height
   *          the height of the texture in pixels
   * @param format
   *          the format of the pixel data
   * @param data
   *          the pixel data
   * @param magFilter
   *          the magnifying filter
   * @param minFilter
   *          the minimizing filter
   * @throws CoreGLException
   *           in case the creation of the texture fails for any reason
   */
  private CoreTexture2D(final CoreGL gl, final CoreTexture2DConstants texMaps, final ColorFormat internalFormat,
      final int width, final int height, final ColorFormat format, final Buffer data, final int magFilter,
      final int minFilter) {
    this(gl, texMaps, gl.GL_TEXTURE_2D(), internalFormat, width, height, format, data, magFilter, minFilter);
  }

  /**
   * This is the constructor is a slightly reduced version that defines some
   * common options automatically.
   *
   * @param internalFormat
   *          the internal format of the texture
   * @param width
   *          the width of the texture in pixels
   * @param height
   *          the height of the texture in pixels
   * @param format
   *          the format of the pixel data
   * @param data
   *          the pixel data
   * @param magFilter
   *          the magnifying filter
   * @param minFilter
   *          the minimizing filter
   * @throws CoreGLException
   *           in case the creation of the texture fails for any reason
   */
  private CoreTexture2D(final CoreGL gl, final CoreTexture2DConstants texMaps, final int internalFormat,
      final int width, final int height, final ColorFormat format, final Buffer data, final int magFilter,
      final int minFilter) {
    this(gl, texMaps, AUTO, gl.GL_TEXTURE_2D(), 0, internalFormat, width, height, 0,
        texMaps.getColorInfo(format).format, AUTO, data, magFilter, minFilter);
  }

  /**
   * This is the constructor is a slightly reduced version that defines some
   * common options automatically.
   *
   * @param target
   *          the target type of the texture operations, has to be a valid 2D
   *          texture target
   * @param internalFormat
   *          the internal format of the texture
   * @param width
   *          the width of the texture in pixels
   * @param height
   *          the height of the texture in pixels
   * @param format
   *          the format of the pixel data
   * @param data
   *          the pixel data
   * @param magFilter
   *          the magnifying filter
   * @param minFilter
   *          the minimizing filter
   * @throws CoreGLException
   *           in case the creation of the texture fails for any reason
   */
  private CoreTexture2D(final CoreGL gl, final CoreTexture2DConstants texMaps, final int target,
      final ColorFormat internalFormat, final int width, final int height, final ColorFormat format, final Buffer data,
      final int magFilter, final int minFilter) {
    this(gl, texMaps, AUTO, target, 0, texMaps.getColorInfo(internalFormat).internalFormat, width, height, 0,
        texMaps.getColorInfo(format).format, AUTO, data, magFilter, minFilter);
  }

  /**
   * This is the constructor that allows to define all the settings required to
   * create a texture. Using this causes the class to disable all assumptions
   * and do exactly what you want.
   *
   * @param textureId
   *          the ID that is supposed to be used with the texture, it has to be
   *          a valid texture ID for the selected target. Use {@link #AUTO} to
   *          tell the class to fetch a texture ID in its own.
   * @param target
   *          the target type of the texture operations, has to be a valid 2D
   *          texture target
   * @param level
   *          the mipmap level of the texture, in case you want the automated
   *          mipmap generation to kick in leave this value on {@code 0} and
   *          selected a fitting minimizing filter
   * @param internalFormat
   *          the internal format of the texture
   * @param width
   *          the width of the texture in pixels
   * @param height
   *          the height of the texture in pixels
   * @param border
   *          the width of the border of the texture
   * @param format
   *          the format of the pixel data
   * @param type
   *          the data type of the pixel data
   * @param data
   *          the pixel data
   * @param magFilter
   *          the magnifying filter
   * @param minFilter
   *          the minimizing filter
   * @throws CoreGLException
   *           in case the creation of the texture fails for any reason
   */
  private CoreTexture2D(final CoreGL gl, final CoreTexture2DConstants texMaps, final int textureId, final int target,
      final int level, final ColorFormat internalFormat, final int width, final int height, final int border,
      final ColorFormat format, final Type type, final Buffer data, final int magFilter, final int minFilter) {
    this(gl, texMaps, textureId, target, level, texMaps.getColorInfo(internalFormat).internalFormat, width, height,
        border, texMaps.getColorInfo(format).format, type, data, magFilter, minFilter, false, 0);
  }

  /**
   * This is the constructor that allows to define all the settings required to
   * create a texture. Using this causes the class to disable all assumptions
   * and do exactly what you want.
   *
   * @param textureId
   *          the ID that is supposed to be used with the texture, it has to be
   *          a valid texture ID for the selected target. Use {@link #AUTO} to
   *          tell the class to fetch a texture ID in its own.
   * @param target
   *          the target type of the texture operations, has to be a valid 2D
   *          texture target
   * @param level
   *          the mipmap level of the texture, in case you want the automated
   *          mipmap generation to kick in leave this value on {@code 0} and
   *          selected a fitting minimizing filter
   * @param internalFormat
   *          the internal format of the texture
   * @param width
   *          the width of the texture in pixels
   * @param height
   *          the height of the texture in pixels
   * @param border
   *          the width of the border of the texture
   * @param format
   *          the format of the pixel data
   * @param type
   *          the data type of the pixel data
   * @param data
   *          the pixel data
   * @param magFilter
   *          the magnifying filter
   * @param minFilter
   *          the minimizing filter
   * @throws CoreGLException
   *           in case the creation of the texture fails for any reason
   */
  private CoreTexture2D(final CoreGL gl, final CoreTexture2DConstants texMaps, final int textureId, final int target,
      final int level, final int internalFormat, final int width, final int height, final int border, final int format,
      final int type, final Buffer data, final int magFilter, final int minFilter) {
    this(gl, texMaps, textureId, target, level, internalFormat, width, height, border, format, type, data, magFilter,
        minFilter, false, 0);
  }

  private CoreTexture2D(final CoreGL gl, final CoreTexture2DConstants texMaps, final int textureId, final int target,
      final int level, final int internalFormat, final int width, final int height, final int border, final int format,
      final Type type, final Buffer data, final int magFilter, final int minFilter, final boolean textureArray,
      final int depth) {
    this.gl = gl;
    this.textureId = createTexture(textureId,
                                   target,
                                   level,
                                   internalFormat,
                                   width,
                                   height,
                                   border,
                                   format,
                                   texMaps.getTypeInfo(type).internalType,
                                   data,
                                   magFilter,
                                   minFilter,
                                   textureArray,
                                   depth);
    textureTarget = target;
    this.width = width;
    this.height = height;
  }

  private CoreTexture2D(final CoreGL gl, final CoreTexture2DConstants texMaps, final int textureId, final int target,
      final int level, final int internalFormat, final int width, final int height, final int border, final int format,
      final int type, final Buffer data, final int magFilter, final int minFilter, final boolean textureArray,
      final int depth) {
    this.gl = gl;
    this.textureId = createTexture(textureId,
                                   target,
                                   level,
                                   internalFormat,
                                   width,
                                   height,
                                   border,
                                   format,
                                   type,
                                   data,
                                   magFilter,
                                   minFilter,
                                   textureArray,
                                   depth);
    textureTarget = target;
    this.width = width;
    this.height = height;
  }

  public int getMaxTextureSize() {
    if (maxTextureSize == -1) {
      maxTextureSize = gl.glGetInteger(gl.GL_MAX_TEXTURE_SIZE());
      checkGLError("glGetInteger", true);
    }
    return maxTextureSize;
  }

  public boolean isNPOTHardwareSupported() {
    return gl.isNPOTHardwareSupported();
  }

  public boolean isNPOTSupported() {
    return gl.isNPOTSupported();
  }

  public void disableErrorChecking() {
    errorChecks = false;
  }

  public void enableErrorChecking() {
    errorChecks = true;
  }

  public void bind() {
    if (isDisposed) {
      throw new CoreGLException("This texture was disposed. You can't bind it anymore. Its gone for good.");
    }
    gl.glBindTexture(textureTarget, textureId);
    checkGLError("glBindTexture", true);
  }

  public void dispose() {
    gl.glDeleteTextures(1, CoreBufferUtil.createIntBuffer(new int[] { textureId }));
    checkGLError("dispose", true);
    isDisposed = true;
  }

  public int getTextureId() {
    return textureId;
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append(CoreTexture2D.class.getName()).append('(');
    builder.append("id:").append(textureId).append(", ");
    builder.append("w:").append(width).append(", ");
    builder.append("h:").append(height).append(')');
    return builder.toString();
  }

  @Override
  protected void finalize() throws Throwable {
    if (!isDisposed) {
      LOG.warning("Memory Leak: Texture " + Integer.toString(textureId)
          + " is getting finalized by the Java GC without being disposed.");
    }
    super.finalize();
  }

  /**
   * Apply the filter values for the texture scaling.
   *
   * @param target
   *          the texture target
   * @param minFilter
   *          the minimize filter
   * @param magFilter
   *          the maximize filter
   * @throws CoreGLException
   *           in case setting the filter parameter fails
   */
  private void applyFilters(final int target, final int minFilter, final int magFilter) {
    gl.glTexParameteri(target, gl.GL_TEXTURE_MIN_FILTER(), minFilter);
    checkGLError("glTexParameteri", true);
    gl.glTexParameteri(target, gl.GL_TEXTURE_MAG_FILTER(), magFilter);
    checkGLError("glTexParameteri", true);
  }

  /**
   * Generate and bind a texture ID.
   *
   * @param target
   *          the target type of the texture
   * @param textureId
   *          the ID of the texture, in case this is {@code -1} a new texture ID
   *          will be generated.
   * @return the texture ID that was actually bound
   * @throws CoreGLException
   *           in case creating or binding the texture fails
   */
  private int applyTextureId(final int target, final int textureId) {
    final int usedTextureId;
    if (textureId == AUTO) {
      usedTextureId = createTextureID();
    } else {
      usedTextureId = textureId;
    }

    gl.glBindTexture(target, usedTextureId);
    checkGLError("glBindTexture", true);
    return usedTextureId;
  }

  /**
   * Check if the border parameter is valid.
   *
   * @param border
   *          the border parameter
   * @throws CoreGLException
   *           in case the border parameter is not valid
   */
  private static void checkBorder(final int border) {
    if (border != 0 && border != 1) {
      throw new CoreGLException("Border has illegal value: 0x" + Integer.toHexString(border));
    }
  }

  /**
   * Check the format, the size and the data type value.
   *
   * @param format
   *          the selected format
   * @param type
   *          the selected data type
   * @throws CoreGLException
   *           in case the parameters don't work together
   */
  private static void checkFormatSizeData(final CoreGL gl, final int format, final int type, final Buffer data) {
    if (data == null) {
      throw new CoreGLException("Pixeldata must not be NULL");
    }

    if (type == gl.GL_BITMAP() && format != gl.GL_COLOR_INDEX()) {
      throw new CoreGLException("GL_BITMAP requires the format to be GL_COLOR_INDEX");
    }

    if (checkEqualsInts(type,
                        gl.GL_UNSIGNED_BYTE(),
                        gl.GL_BYTE(),
                        gl.GL_BITMAP(),
                        gl.GL_UNSIGNED_BYTE_3_3_2(),
                        gl.GL_UNSIGNED_BYTE_2_3_3_REV())) {
      if (!(data instanceof ByteBuffer)) {
        throw new CoreGLException("The selected type requires its data as byte values.");
      }
    } else if (checkEqualsInts(type,
                               gl.GL_UNSIGNED_SHORT(),
                               gl.GL_SHORT(),
                               gl.GL_UNSIGNED_SHORT_5_6_5(),
                               gl.GL_UNSIGNED_SHORT_5_6_5_REV(),
                               gl.GL_UNSIGNED_SHORT_4_4_4_4(),
                               gl.GL_UNSIGNED_SHORT_4_4_4_4_REV(),
                               gl.GL_UNSIGNED_SHORT_5_5_5_1(),
                               gl.GL_UNSIGNED_SHORT_1_5_5_5_REV())) {
      if (!(data instanceof ShortBuffer)) {
        throw new CoreGLException("The selected type requires its data as short values.");
      }
    } else if (checkEqualsInts(type,
                               gl.GL_UNSIGNED_INT(),
                               gl.GL_INT(),
                               gl.GL_UNSIGNED_INT_8_8_8_8(),
                               gl.GL_UNSIGNED_INT_8_8_8_8_REV(),
                               gl.GL_UNSIGNED_INT_10_10_10_2(),
                               gl.GL_UNSIGNED_INT_2_10_10_10_REV())) {
      if (!(data instanceof IntBuffer)) {
        throw new CoreGLException("The selected type requires its data as integer values.");
      }
    } else if (type == gl.GL_FLOAT()) {
      if (!(data instanceof FloatBuffer) && !(data instanceof DoubleBuffer)) {
        throw new CoreGLException("The selected type requires its data as floating-point values.");
      }
    } else {
      throw new CoreGLException("Unknown type value.");
    }
  }

  /**
   * This functions causes the OpenGL errors to be checked in case error
   * checking is enabled.
   *
   * @param message
   *          the message in case a error is detected
   * @param throwException
   *          {@code true} in case an exception is supposed to be thrown in case
   *          a error is detected
   */
  private void checkGLError(final String message, final boolean throwException) {
    if (errorChecks) {
      gl.checkGLError(throwException, message);
    }
  }

  /**
   * This function is used to check if the size value fit the capabilities of
   * OpenGL.
   *
   * @param width
   *          the width of the new texture
   * @param height
   *          the height of the new texture
   * @throws CoreGLException
   *           in case the texture dimensions are too large or negative
   */
  private void checkSize(final int width, final int height) {
    final int maxSize = getMaxTextureSize();

    if ((width > maxSize) || (height > maxSize)) {
      throw new CoreGLException("Attempt to allocate a texture to big for the current hardware");
    }
    if (width < 0) {
      throw new CoreGLException("Attempt to allocate a texture with a width value below 0.");
    }
    if (height < 0) {
      throw new CoreGLException("Attempt to allocate a texture with a height value below 0.");
    }

    if (!isPowerOfTwo(height) || !isPowerOfTwo(width)) {
      if (!isNPOTSupported()) {
        throw new CoreGLException("Non-power-of-two textures are not supported.");
      }
      if (!isNPOTHardwareSupported()) {
        LOG.warning("Non-pwer-of-two textures are supported, but software emulated.");
      }
    }
  }

  /**
   * Check if the target ID is valid to be used with this class.
   *
   * @param target
   *          the target ID
   * @throws CoreGLException
   *           in case the {@code target} parameter contains a illegal value
   */
  private void checkTarget(final int target) {
    if (!checkEqualsInts(target,
                         gl.GL_TEXTURE_2D(),
                         gl.GL_TEXTURE_2D_ARRAY(),
                         gl.GL_TEXTURE_CUBE_MAP_POSITIVE_X(),
                         gl.GL_TEXTURE_CUBE_MAP_NEGATIVE_X(),
                         gl.GL_TEXTURE_CUBE_MAP_POSITIVE_Y(),
                         gl.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y(),
                         gl.GL_TEXTURE_CUBE_MAP_POSITIVE_Z(),
                         gl.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z())) {
      throw new CoreGLException("Illegal target ID: 0x" + Integer.toHexString(target));
    }
  }

  /**
   * Create a new texture and transfer the data into the OpenGL space. Also
   * generate mipmaps as needed.
   *
   * @param textureId
   *          the ID of the texture to use, in case this is {@code -1} a new
   *          texture ID is generated
   * @param target
   *          the target of the create operation, has to be a valid 2D texture
   *          target
   * @param level
   *          the mipmap level, automatic mipmap generation is disabled in case
   *          this valid is not equal to 0
   * @param internalFormat
   *          the internal texture format
   * @param width
   *          the width of the texture
   * @param height
   *          the height of the texture
   * @param border
   *          the border width of the texture
   * @param format
   *          the format of the pixel data
   * @param type
   *          the data type of the pixel data
   * @param data
   *          the pixel data
   * @param magFilter
   *          the magnifying filter
   * @param minFilter
   *          the minimizing filter, in case a filter that uses mipmaps is
   *          selected, those mipmaps are generated automatically
   * @return the texture ID of the newly created texture
   * @throws CoreGLException
   *           in case anything goes wrong
   */
  private int createTexture(final int textureId,
                            final int target,
                            final int level,
                            final int internalFormat,
                            final int width,
                            final int height,
                            final int border,
                            final int format,
                            final int type,
                            final Buffer data,
                            final int magFilter,
                            final int minFilter,
                            final boolean textureArray,
                            final int depth) {
    final int usedType = getType(type, data);

    if (errorChecks) {
      checkTarget(target);
      checkBorder(border);
      checkSize(width - 2 * border, height - 2 * border);
      checkFormatSizeData(gl, format, usedType, data);
    }

    final int usedTextureId = applyTextureId(target, textureId);
    try {
      applyFilters(target, minFilter, magFilter);

      if (textureArray) {
        glTexImage3D(target, level, internalFormat, width, height, depth, border, format, usedType, data);
      } else if (isCreatingMipMaps(gl, level, minFilter)) {
        if (CoreVersion.checkCurrentGLVersion(gl, CoreVersion.GLVersion.GL30)) {
          glTexImage2D(target, 0, internalFormat, width, height, border, format, usedType, data);
          gl.glGenerateMipmap(target);
          checkGLError("glGenerateMipmap", true);
          /*
           * } else if (//FIXME Check for: 'EXT_framebuffer_object' ) {
           * glTexImage2D(target, 0, internalFormat, width, height, border,
           * format, usedType, data); //FIXME JOGL impl needed for:
           * EXTFramebufferObject.glGenerateMipmapEXT(target);
           * checkGLError("glGenerateMipmapEXT", true); } else if (//FIXME Check
           * for: GL_SGIS_generate_mipmap && ((isPowerOfTwo(height) &&
           * isPowerOfTwo(width)) || isNPOTHardwareSupported())) { //FIXME JOGL
           * impl needed for: gl.glTexParameteri(target,
           * SGISGenerateMipmap.GL_GENERATE_MIPMAP_SGIS, GL11.GL_TRUE);
           * checkGLError("glTexParameteri", true); glTexImage2D(target, 0,
           * internalFormat, width, height, border, format, usedType, data);
           */
        } else {
          gluBuild2DMipmaps(target, internalFormat, width, height, format, usedType, data);
        }
      } else {
        glTexImage2D(target, level, internalFormat, width, height, border, format, usedType, data);
      }
    } catch (final CoreGLException ex) {
      if (textureId == -1) {
        gl.glDeleteTextures(1, CoreBufferUtil.createIntBuffer(new int[] { textureId }));
        checkGLError("glDeleteTextures", false);
      }
      throw ex;
    }

    return usedTextureId;
  }

  /**
   * Create a new texture ID.
   *
   * @return the newly generated texture ID
   * @throws CoreGLException
   *           in case generation a new texture ID fails
   */
  private int createTextureID() {
    final IntBuffer idbuff = CoreBufferUtil.createIntBuffer(1);
    gl.glGenTextures(1, idbuff);
    checkGLError("glGenTextures", true);
    return idbuff.get(0);
  }

  /**
   * Automatically select a type in case its requested. This function only
   * determines the best fitting type automatically in case the {@code type}
   * parameter is set to {@link #AUTO}.
   *
   * @param type
   *          the selected type of the pixel data
   * @param data
   *          the pixel data
   * @return the value of {@code type} or a automatically determined type
   * @throws CoreGLException
   *           in case the automated detection of the type fails
   */
  private int getType(final int type, final Buffer data) {
    if (type == AUTO) {
      if (data instanceof ByteBuffer) {
        return gl.GL_UNSIGNED_BYTE();
      } else if (data instanceof ShortBuffer) {
        return gl.GL_UNSIGNED_SHORT();
      } else if (data instanceof IntBuffer) {
        return gl.GL_UNSIGNED_INT();
      } else if (data instanceof FloatBuffer) {
        return gl.GL_FLOAT();
      } else if (data instanceof DoubleBuffer) {
        return gl.GL_FLOAT();
      } else {
        throw new CoreGLException("Unknown buffer type; " + data.getClass().toString());
      }
    } else {
      return type;
    }
  }

  /**
   * This is a wrapper function for the actual call to {@code glTexImage2D}. It
   * uses the generic {@link Buffer} and internally casts it as needed to fit
   * the different implementations of the {@code glTexImage2D} function.
   *
   * @param target
   *          the target of the texture creation operation
   * @param level
   *          the level of this texture
   * @param internalformat
   *          the internal format
   * @param width
   *          the width of the texture
   * @param height
   *          the height of the texture
   * @param border
   *          the border width of the texture
   * @param format
   *          the format of the pixel data
   * @param type
   *          the data type of the pixel data
   * @param pixels
   *          the pixel data
   * @throws CoreGLException
   *           in case OpenGL reports a error or in case the type of the buffer
   *           is unknown
   */
  private void glTexImage2D(final int target,
                            final int level,
                            final int internalformat,
                            final int width,
                            final int height,
                            final int border,
                            final int format,
                            final int type,
                            final Buffer pixels) {
    texImageTarget = target;
    texImageLevel = level;
    texImageInternalFormat = format;
    texImageWidth = width;
    texImageHeight = height;
    texBorder = border;
    texFormat = format;
    texType = type;
    textureCanBeUpdated = true;

    updateTextureData(pixels);
  }

  /**
   * This is a wrapper function for the actual call to {@code glTexImage3D}. It
   * uses the generic {@link Buffer} and internally casts it as needed to fit
   * the different implementations of the {@code glTexImage3D} function.
   *
   * @param target
   *          the target of the texture creation operation
   * @param level
   *          the level of this texture
   * @param internalformat
   *          the internal format
   * @param width
   *          the width of the texture
   * @param height
   *          the height of the texture
   * @param depth
   *          the depth of the texture
   * @param border
   *          the border width of the texture
   * @param format
   *          the format of the pixel data
   * @param type
   *          the data type of the pixel data
   * @param pixels
   *          the pixel data
   * @throws CoreGLException
   *           in case OpenGL reports a error or in case the type of the buffer
   *           is unknown
   */
  private void glTexImage3D(final int target,
                            final int level,
                            final int internalformat,
                            final int width,
                            final int height,
                            final int depth,
                            final int border,
                            final int format,
                            final int type,
                            final Buffer pixels) {
    texImageTarget = target;
    texImageLevel = level;
    texImageInternalFormat = format;
    texImageWidth = width;
    texImageHeight = height;
    texBorder = border;
    texFormat = format;
    texType = type;
    textureCanBeUpdated = true;
    texDepth = depth;

    updateTextureData3D(pixels);
  }

  public void updateTextureData(final Buffer pixels) {
    if (!textureCanBeUpdated) {
      throw new CoreGLException("updateTextureData() call can only be used to update texture data");
    }

    if (pixels instanceof ByteBuffer) {
      gl.glTexImage2D(texImageTarget,
                      texImageLevel,
                      texImageInternalFormat,
                      texImageWidth,
                      texImageHeight,
                      texBorder,
                      texFormat,
                      texType,
                      (ByteBuffer) pixels);
    } else if (pixels instanceof ShortBuffer) {
      gl.glTexImage2D(texImageTarget,
                      texImageLevel,
                      texImageInternalFormat,
                      texImageWidth,
                      texImageHeight,
                      texBorder,
                      texFormat,
                      texType,
                      (ShortBuffer) pixels);
    } else if (pixels instanceof IntBuffer) {
      gl.glTexImage2D(texImageTarget,
                      texImageLevel,
                      texImageInternalFormat,
                      texImageWidth,
                      texImageHeight,
                      texBorder,
                      texFormat,
                      texType,
                      (IntBuffer) pixels);
    } else if (pixels instanceof FloatBuffer) {
      gl.glTexImage2D(texImageTarget,
                      texImageLevel,
                      texImageInternalFormat,
                      texImageWidth,
                      texImageHeight,
                      texBorder,
                      texFormat,
                      texType,
                      (FloatBuffer) pixels);
    } else if (pixels instanceof DoubleBuffer) {
      gl.glTexImage2D(texImageTarget,
                      texImageLevel,
                      texImageInternalFormat,
                      texImageWidth,
                      texImageHeight,
                      texBorder,
                      texFormat,
                      texType,
                      (DoubleBuffer) pixels);
    } else {
      throw new CoreGLException("Unknown buffer type; " + pixels.getClass().toString());
    }
    checkGLError("glTexImage2D", true);
  }

  public void updateTextureData3D(final Buffer pixels) {
    if (!textureCanBeUpdated) {
      throw new CoreGLException("updateTextureData() call can only be used to update texture data");
    }

    if (pixels instanceof ByteBuffer) {
      gl.glTexImage3D(texImageTarget,
                      texImageLevel,
                      texImageInternalFormat,
                      texImageWidth,
                      texImageHeight,
                      texDepth,
                      texBorder,
                      texFormat,
                      texType,
                      (ByteBuffer) pixels);
    } else if (pixels instanceof ShortBuffer) {
      gl.glTexImage3D(texImageTarget,
                      texImageLevel,
                      texImageInternalFormat,
                      texImageWidth,
                      texImageHeight,
                      texDepth,
                      texBorder,
                      texFormat,
                      texType,
                      (ShortBuffer) pixels);
    } else if (pixels instanceof IntBuffer) {
      gl.glTexImage3D(texImageTarget,
                      texImageLevel,
                      texImageInternalFormat,
                      texImageWidth,
                      texImageHeight,
                      texDepth,
                      texBorder,
                      texFormat,
                      texType,
                      (IntBuffer) pixels);
    } else if (pixels instanceof FloatBuffer) {
      gl.glTexImage3D(texImageTarget,
                      texImageLevel,
                      texImageInternalFormat,
                      texImageWidth,
                      texImageHeight,
                      texDepth,
                      texBorder,
                      texFormat,
                      texType,
                      (FloatBuffer) pixels);
    } else if (pixels instanceof DoubleBuffer) {
      gl.glTexImage3D(texImageTarget,
                      texImageLevel,
                      texImageInternalFormat,
                      texImageWidth,
                      texImageHeight,
                      texDepth,
                      texBorder,
                      texFormat,
                      texType,
                      (DoubleBuffer) pixels);
    } else {
      throw new CoreGLException("Unknown buffer type; " + pixels.getClass().toString());
    }
    checkGLError("glTexImage3D", true);
  }

  /**
   * Save the texture as a png file with the given filename.
   * 
   * @param filename
   */
  public void saveAsPNG(final String filename) {
    try {
      final BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
      final ByteBuffer pixels = CoreBufferUtil.createByteBuffer(width * height * 4);
      gl.glGetTexImage(gl.GL_TEXTURE_2D(), 0, gl.GL_RGBA(), gl.GL_UNSIGNED_BYTE(), pixels);
      gl.checkGLError("glGetTexImage");

      final WritableRaster raster = bi.getRaster();
      final int[] buffer = new int[width * height * 4];
      for (int i = 0; i < width * height * 4; i++) {
        buffer[i] = pixels.get(i);
      }
      raster.setPixels(0, 0, width, height, buffer);

      final File outputfile = new File(filename);
      ImageIO.write(bi, "png", outputfile);
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * This functions wraps the last resort function for the mipmap generation.
   * This function only works in base {@code data} is a {@link ByteBuffer}. It
   * will create textures and its mipmaps.
   *
   * @param target
   *          the target of the texture creation operation
   * @param components
   *          the internal texture format
   * @param width
   *          the width of the texture
   * @param height
   *          the height of the texture
   * @param format
   *          the format of the pixel data
   * @param type
   *          the type of the pixel data
   * @param data
   *          the pixel data
   * @throws CoreGLException
   *           in case the creation of the mipmap fails
   */
  private void gluBuild2DMipmaps(final int target,
                                 final int components,
                                 final int width,
                                 final int height,
                                 final int format,
                                 final int type,
                                 final Buffer data) {
    if (data instanceof ByteBuffer) {
      gl.gluBuild2DMipmaps(target, components, width, height, format, type, (ByteBuffer) data);
      checkGLError("gluBuild2DMipmaps", true);
    } else {
      throw new CoreGLException("MipMap creation not supported on this platform for non-byte buffers.");
    }
  }

  /**
   * Check if mipmaps are supposed to be generated.
   *
   * @param level
   *          the level settings
   * @param minFilter
   *          the minimizing filter
   * @return {@code true} in case mipmaps are supposed to be generated
   */
  private static boolean isCreatingMipMaps(final CoreGL gl, final int level, final int minFilter) {
    if (level > 0) {
      return false;
    }

    if (checkEqualsInts(minFilter,
                        gl.GL_NEAREST_MIPMAP_NEAREST(),
                        gl.GL_LINEAR_MIPMAP_NEAREST(),
                        gl.GL_NEAREST_MIPMAP_LINEAR(),
                        gl.GL_LINEAR_MIPMAP_LINEAR())) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Check if a value is power of two.
   * 
   * @param n
   *          the value to check
   * @return {@code true} in case the value is power of two
   */
  private static boolean isPowerOfTwo(final int n) {
    return ((n != 0) && (n & (n - 1)) == 0);
  }

  private static boolean checkEqualsInts(final int val, final int... ints) {
    Arrays.sort(ints);
    final int ind = Arrays.binarySearch(ints, val);
    return ind >= 0;
  }
}
