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
package de.lessvoid.coregl.lwjgl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Logger;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.SGISGenerateMipmap;
import org.lwjgl.util.glu.GLU;

import de.lessvoid.coregl.CoreCheckGL;
import de.lessvoid.coregl.CoreGLException;
import de.lessvoid.coregl.CoreTexture2D;

public class CoreTexture2DLwjgl implements CoreTexture2D {
  private static final CoreCheckGL checkGL = new CoreCheckGLLwjgl();

  private static Map<ResizeFilter, ResizeFilterInfo> resizeFilterMap = new Hashtable<ResizeFilter, ResizeFilterInfo>();
  private static Map<ColorFormat, ColorFormatInfo> formatMap = new Hashtable<ColorFormat, ColorFormatInfo>();
  private static Map<Type, TypeInfo> typeMap = new Hashtable<Type, TypeInfo>();

  static {
    initResizeFilterMap();
    initColorFormatMap();
    initTypeMap();
  }

  /**
   * This constant can be used as the data type to enable the automated selection of the data type. Also his value is
   * used for the texture ID in case the class is supposed to acquire the texture ID automatically.
   */
  private static final int AUTO = -1;

  /**
   * The logger used to spam your logfile.
   */
  private static final Logger LOG = Logger.getLogger(CoreTexture2DLwjgl.class.getName());

  /**
   * The buffered value of the maximal texture size.
   */
  private static int maxTextureSize = -1;

  /**
   * As long as this variable is set {@code true} the class will perform checks for errors.
   */
  private static boolean errorChecks = true;

  /**
   * This flag is switched {@code true} once the texture is disposed. It is afterwards used for two things. First to
   * throw a exception in case a disposed texture is bound again, secondly to show a warning in case the java garbage
   * collector consumes this class while the assigned texture was not disposed.
   */
  private boolean isDisposed;

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
   * We remember the parameters used for the glTexture2D call so we can easily update the texture if we need later. 
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
   * @param format the ColorFormat for the texture
   * @param dataType the data type you'll later send the pixel data
   * @param width the width of the texture
   * @param height the height of the texture
   * @param filter the ResizeFilter to use
   * @return the new allocated CoreTexture2D
   */
  static CoreTexture2DLwjgl createEmptyTexture(
      final ColorFormat format,
      final Type dataType,
      final int width,
      final int height,
      final ResizeFilter filter) {
    ByteBuffer buffer = BufferUtils.createByteBuffer(width*height*formatMap.get(format).componentsPerPixel);
    byte[] data = new byte[width*height*formatMap.get(format).componentsPerPixel];
    buffer.put(data);
    buffer.flip();

    return new CoreTexture2DLwjgl(
        AUTO,
        GL11.GL_TEXTURE_2D,
        0,
        formatMap.get(format).internalFormat,
        width,
        height,
        0,
        formatMap.get(format).format,
        typeMap.get(dataType).internalType,
        buffer,
        resizeFilterMap.get(filter).magFilter,
        resizeFilterMap.get(filter).minFilter);
  }

  private static void initResizeFilterMap() {
    resizeFilterMap.put(ResizeFilter.Nearest, filterInfo(GL11.GL_NEAREST, GL11.GL_NEAREST));
    resizeFilterMap.put(ResizeFilter.Linear, filterInfo(GL11.GL_LINEAR, GL11.GL_LINEAR));
    resizeFilterMap.put(ResizeFilter.NearestMipMapNearest, filterInfo(GL11.GL_NEAREST_MIPMAP_NEAREST, GL11.GL_NEAREST));
    resizeFilterMap.put(ResizeFilter.NearestMipMapLinear, filterInfo(GL11.GL_NEAREST_MIPMAP_LINEAR, GL11.GL_LINEAR));
    resizeFilterMap.put(ResizeFilter.LinearMipMapNearest, filterInfo(GL11.GL_LINEAR_MIPMAP_NEAREST, GL11.GL_LINEAR));
    resizeFilterMap.put(ResizeFilter.LinearMipMapLinear, filterInfo(GL11.GL_LINEAR_MIPMAP_LINEAR, GL11.GL_LINEAR));
  }

  private static void initColorFormatMap() {
    formatMap.put(ColorFormat.Red, colorInfo(GL11.GL_RED, GL11.GL_RGB, GL13.GL_COMPRESSED_RGB, 1));
    formatMap.put(ColorFormat.Green, colorInfo(GL11.GL_GREEN, GL11.GL_RGB, GL13.GL_COMPRESSED_RGB, 1));
    formatMap.put(ColorFormat.Blue, colorInfo(GL11.GL_BLUE, GL11.GL_RGB, GL13.GL_COMPRESSED_RGB, 1));
    formatMap.put(ColorFormat.Alpha, colorInfo(GL11.GL_ALPHA, GL11.GL_ALPHA, GL13.GL_COMPRESSED_ALPHA, 1));
    formatMap.put(ColorFormat.RGB, colorInfo(GL11.GL_RGB, GL11.GL_RGB, GL13.GL_COMPRESSED_RGB, 3));
    formatMap.put(ColorFormat.BGR, colorInfo(GL12.GL_BGR, GL11.GL_RGB, GL13.GL_COMPRESSED_RGB, 3));
    formatMap.put(ColorFormat.RGBA, colorInfo(GL11.GL_RGBA, GL11.GL_RGBA, GL13.GL_COMPRESSED_RGBA, 4));
    formatMap.put(ColorFormat.BGRA, colorInfo(GL12.GL_BGRA, GL11.GL_RGBA, GL13.GL_COMPRESSED_RGBA, 4));
    formatMap.put(ColorFormat.Luminance, colorInfo(GL11.GL_LUMINANCE, GL11.GL_LUMINANCE, GL13.GL_COMPRESSED_LUMINANCE, 1));
    formatMap.put(ColorFormat.LuminanceAlpha, colorInfo(GL11.GL_LUMINANCE_ALPHA, GL11.GL_LUMINANCE_ALPHA, GL13.GL_COMPRESSED_LUMINANCE_ALPHA, 2));
  }

  private static void initTypeMap() {
    typeMap.put(Type.UNSIGNED_BYTE, typeInfo(GL11.GL_UNSIGNED_BYTE));
    typeMap.put(Type.BYTE, typeInfo(GL11.GL_BYTE));
    typeMap.put(Type.UNSIGNED_SHORT, typeInfo(GL11.GL_UNSIGNED_SHORT));
    typeMap.put(Type.SHORT, typeInfo(GL11.GL_SHORT));
    typeMap.put(Type.UNSIGNED_INT, typeInfo(GL11.GL_SHORT));
    typeMap.put(Type.INT, typeInfo(GL11.GL_INT));
    typeMap.put(Type.FLOAT, typeInfo(GL11.GL_FLOAT));
    typeMap.put(Type.UNSIGNED_BYTE_3_3_2, typeInfo(GL12.GL_UNSIGNED_BYTE_3_3_2));
    typeMap.put(Type.UNSIGNED_BYTE_2_3_3_REV, typeInfo(GL12.GL_UNSIGNED_BYTE_2_3_3_REV));
    typeMap.put(Type.UNSIGNED_SHORT_5_6_5, typeInfo(GL12.GL_UNSIGNED_SHORT_5_6_5));
    typeMap.put(Type.UNSIGNED_SHORT_5_6_5_REV, typeInfo(GL12.GL_UNSIGNED_SHORT_5_6_5_REV));
    typeMap.put(Type.UNSIGNED_SHORT_4_4_4_4, typeInfo(GL12.GL_UNSIGNED_SHORT_4_4_4_4));
    typeMap.put(Type.UNSIGNED_SHORT_4_4_4_4_REV, typeInfo(GL12.GL_UNSIGNED_SHORT_4_4_4_4_REV));
    typeMap.put(Type.UNSIGNED_SHORT_5_5_5_1, typeInfo(GL12.GL_UNSIGNED_SHORT_5_5_5_1));
    typeMap.put(Type.UNSIGNED_SHORT_1_5_5_5_REV, typeInfo(GL12.GL_UNSIGNED_SHORT_1_5_5_5_REV));
    typeMap.put(Type.UNSIGNED_INT_8_8_8_8, typeInfo(GL12.GL_UNSIGNED_INT_8_8_8_8));
    typeMap.put(Type.UNSIGNED_INT_8_8_8_8_REV, typeInfo(GL12.GL_UNSIGNED_INT_8_8_8_8_REV));
    typeMap.put(Type.UNSIGNED_INT_10_10_10_2, typeInfo(GL12.GL_UNSIGNED_INT_10_10_10_2));
    typeMap.put(Type.UNSIGNED_INT_2_10_10_10_REV, typeInfo(GL12.GL_UNSIGNED_INT_2_10_10_10_REV));
  }

  /**
   * Create an empty texture.
   *
   * @param format the ColorFormat for the texture
   * @param dataType the data type you'll later send the pixel data
   * @param width the width of the texture
   * @param height the height of the texture
   * @param filter the ResizeFilter to use
   * @return the new allocated CoreTexture2D
   */
  static CoreTexture2DLwjgl createEmptyTextureArray(
      final ColorFormat format,
      final Type dataType,
      final int width,
      final int height,
      final int num,
      final ResizeFilter filter) {
    // FIXME works only for ColorFormat.RGBA at the moment! add other formats
    ByteBuffer buffer = BufferUtils.createByteBuffer(width*height*4*num);
    byte[] data = new byte[width*height*4*num];
    buffer.put(data);
    buffer.flip();

    return new CoreTexture2DLwjgl(
        AUTO,
        GL30.GL_TEXTURE_2D_ARRAY,
        0,
        formatMap.get(format).internalFormat,
        width,
        height,
        0,
        formatMap.get(format).format,
        dataType,
        buffer,
        resizeFilterMap.get(filter).magFilter,
        resizeFilterMap.get(filter).minFilter,
        true,
        num);    
  }

  /**
   * This is one of the simple constructors that only allow very limited possibilities for settings. How ever they use
   * settings that should fit the need on most cases.
   *
   * @param format the texture format
   * @param width the width of the texture
   * @param height the height of the texture
   * @param data the pixel data
   * @param filter the used filter
   * @throws CoreGLException in case the creation of the texture fails for any reason
   */
  // PUBLIC
  CoreTexture2DLwjgl(
      final ColorFormat format,
      final int width,
      final int height,
      final Buffer data,
      final ResizeFilter filter) {
    this(format, false, width, height, data, filter);
  }

  /**
   * This is the constructor is a slightly reduced version that defines some common options automatically.
   *
   * @param internalFormat the internal format of the texture
   * @param width the width of the texture in pixels
   * @param height the height of the texture in pixels
   * @param format the format of the pixel data
   * @param data the pixel data
   * @param filter the used filter
   * @throws CoreGLException in case the creation of the texture fails for any reason
   */
  CoreTexture2DLwjgl(
      final int internalFormat,
      final int width,
      final int height,
      final int format,
      final Buffer data,
      final ResizeFilter filter) {
    this(
        AUTO,
        GL11.GL_TEXTURE_2D,
        0,
        internalFormat,
        width,
        height,
        0,
        format,
        AUTO,
        data,
        resizeFilterMap.get(filter).magFilter,
        resizeFilterMap.get(filter).minFilter);
  }

  /**
   * This is one of the simple constructors that only allow very limited possibilities for settings. How ever they use
   * settings that should fit the need on most cases.
   *
   * @param format the texture format
   * @param compressed {@code true} in case the internal texture data is supposed to be compressed if possible
   * @param width the width of the texture
   * @param height the height of the texture
   * @param data the pixel data
   * @param filter the used filter
   * @throws CoreGLException in case the creation of the texture fails for any reason
   */
  CoreTexture2DLwjgl(
      final ColorFormat format,
      final boolean compressed,
      final int width,
      final int height,
      final Buffer data,
      final ResizeFilter filter) {
    this(
        formatMap.get(format).internalFormat,
        width,
        height,
        (compressed ? formatMap.get(format).compressedInternalFormat : formatMap.get(format).format),
        data,
        filter);
  }

  /**
   * This is the constructor is a slightly reduced version that defines some common options automatically.
   *
   * @param internalFormat the internal format of the texture
   * @param width the width of the texture in pixels
   * @param height the height of the texture in pixels
   * @param format the format of the pixel data
   * @param data the pixel data
   * @param magFilter the magnifying filter
   * @param minFilter the minimizing filter
   * @throws CoreGLException in case the creation of the texture fails for any reason
   */
  // PUBLIC
  CoreTexture2DLwjgl(
      final ColorFormat internalFormat,
      final int width,
      final int height,
      final ColorFormat format,
      final Buffer data,
      final int magFilter,
      final int minFilter) {
    this(GL11.GL_TEXTURE_2D, internalFormat, width, height, format, data, magFilter, minFilter);
  }

  /**
   * This is the constructor is a slightly reduced version that defines some common options automatically.
   *
   * @param internalFormat the internal format of the texture
   * @param width the width of the texture in pixels
   * @param height the height of the texture in pixels
   * @param format the format of the pixel data
   * @param data the pixel data
   * @param magFilter the magnifying filter
   * @param minFilter the minimizing filter
   * @throws CoreGLException in case the creation of the texture fails for any reason
   */
  CoreTexture2DLwjgl(
      final int internalFormat,
      final int width,
      final int height,
      final ColorFormat format,
      final Buffer data,
      final int magFilter,
      final int minFilter) {
    this(
        AUTO,
        GL11.GL_TEXTURE_2D,
        0,
        internalFormat,
        width,
        height,
        0,
        formatMap.get(format).format,
        AUTO,
        data,
        magFilter,
        minFilter);
  }

  /**
   * This is the constructor is a slightly reduced version that defines some common options automatically.
   *
   * @param target the target type of the texture operations, has to be a valid 2D texture target
   * @param internalFormat the internal format of the texture
   * @param width the width of the texture in pixels
   * @param height the height of the texture in pixels
   * @param format the format of the pixel data
   * @param data the pixel data
   * @param magFilter the magnifying filter
   * @param minFilter the minimizing filter
   * @throws CoreGLException in case the creation of the texture fails for any reason
   */
  // PUBLIC
  CoreTexture2DLwjgl(
      final int target,
      final ColorFormat internalFormat,
      final int width,
      final int height,
      final ColorFormat format,
      final Buffer data,
      final int magFilter,
      final int minFilter) {
    this(
        AUTO,
        target,
        0,
        formatMap.get(internalFormat).internalFormat,
        width,
        height,
        0,
        formatMap.get(format).format,
        AUTO,
        data,
        magFilter,
        minFilter);
  }

  /**
   * This is the constructor that allows to define all the settings required to create a texture. Using this causes the
   * class to disable all assumptions and do exactly what you want.
   *
   * @param textureId the ID that is supposed to be used with the texture, it has to be a valid texture ID for the
   *                  selected target. Use {@link #AUTO} to tell the class to fetch a texture ID in its own.
   * @param target the target type of the texture operations, has to be a valid 2D texture target
   * @param level the mipmap level of the texture, in case you want the automated mipmap generation to kick in leave
   *              this value on {@code 0} and selected a fitting minimizing filter
   * @param internalFormat the internal format of the texture
   * @param width the width of the texture in pixels
   * @param height the height of the texture in pixels
   * @param border the width of the border of the texture
   * @param format the format of the pixel data
   * @param type the data type of the pixel data
   * @param data the pixel data
   * @param magFilter the magnifying filter
   * @param minFilter the minimizing filter
   * @throws CoreGLException in case the creation of the texture fails for any reason
   */
  // PUBLIC
  CoreTexture2DLwjgl(
      final int textureId,
      final int target,
      final int level,
      final ColorFormat internalFormat,
      final int width,
      final int height,
      final int border,
      final ColorFormat format,
      final Type type,
      final Buffer data,
      final int magFilter,
      final int minFilter) {
    this(
        textureId,
        target,
        level,
        formatMap.get(internalFormat).internalFormat,
        width,
        height,
        border,
        formatMap.get(format).format,
        type,
        data,
        magFilter,
        minFilter,
        false,
        0);
  }

  /**
   * This is the constructor that allows to define all the settings required to create a texture. Using this causes the
   * class to disable all assumptions and do exactly what you want.
   *
   * @param textureId the ID that is supposed to be used with the texture, it has to be a valid texture ID for the
   *                  selected target. Use {@link #AUTO} to tell the class to fetch a texture ID in its own.
   * @param target the target type of the texture operations, has to be a valid 2D texture target
   * @param level the mipmap level of the texture, in case you want the automated mipmap generation to kick in leave
   *              this value on {@code 0} and selected a fitting minimizing filter
   * @param internalFormat the internal format of the texture
   * @param width the width of the texture in pixels
   * @param height the height of the texture in pixels
   * @param border the width of the border of the texture
   * @param format the format of the pixel data
   * @param type the data type of the pixel data
   * @param data the pixel data
   * @param magFilter the magnifying filter
   * @param minFilter the minimizing filter
   * @throws CoreGLException in case the creation of the texture fails for any reason
   */
  CoreTexture2DLwjgl(
      final int textureId,
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
      final int minFilter) {
    this(
        textureId,
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
        false,
        0);
  }

  private CoreTexture2DLwjgl(
      final int textureId,
      final int target,
      final int level,
      final int internalFormat,
      final int width,
      final int height,
      final int border,
      final int format,
      final Type type,
      final Buffer data,
      final int magFilter,
      final int minFilter,
      final boolean textureArray,
      final int depth) {
    this.textureId = createTexture(
        textureId,
        target,
        level,
        internalFormat,
        width,
        height,
        border,
        format,
        typeMap.get(type).internalType,
        data,
        magFilter,
        minFilter,
        textureArray,
        depth);
    this.textureTarget = target;
    this.width = width;
    this.height = height;
  }

  private CoreTexture2DLwjgl(
      final int textureId,
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
    this.textureId = createTexture(
        textureId,
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
    this.textureTarget = target;
    this.width = width;
    this.height = height;
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreTexture2D#getMaxTextureSize()
   */
  @Override
  public int getMaxTextureSize() {
    if (maxTextureSize == -1) {
      maxTextureSize = GL11.glGetInteger(GL11.GL_MAX_TEXTURE_SIZE);
      checkGLError("glGetInteger", true);
    }
    return maxTextureSize;
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreTexture2D#isNPOTHardwareSupported()
   */
  @Override
  public boolean isNPOTHardwareSupported() {
    return GLContext.getCapabilities().GL_ARB_texture_non_power_of_two;
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreTexture2D#isNPOTSupported()
   */
  @Override
  public boolean isNPOTSupported() {
    return GLContext.getCapabilities().OpenGL20 || isNPOTHardwareSupported();
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreTexture2D#disableErrorChecking()
   */
  @Override
  public void disableErrorChecking() {
    errorChecks = false;
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreTexture2D#enableErrorChecking()
   */
  @Override
  public void enableErrorChecking() {
    errorChecks = true;
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreTexture2D#bind()
   */
  @Override
  public void bind() {
    if (isDisposed) {
      throw new CoreGLException("This texture was disposed. You can't bind it anymore. Its gone for good.");
    }
    GL11.glBindTexture(textureTarget, textureId);
    checkGLError("glBindTexture", true);
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreTexture2D#dispose()
   */
  @Override
  public void dispose() {
    GL11.glDeleteTextures(textureId);
    checkGLError("dispose", true);
    isDisposed = true;
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreTexture2D#getTextureId()
   */
  @Override
  public int getTextureId() {
    return textureId;
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreTexture2D#getHeight()
   */
  @Override
  public int getHeight() {
    return height;
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreTexture2D#getWidth()
   */
  @Override
  public int getWidth() {
    return width;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append(CoreTexture2DLwjgl.class.getName()).append('(');
    builder.append("id:").append(textureId).append(", ");
    builder.append("w:").append(width).append(", ");
    builder.append("h:").append(height).append(')');
    return builder.toString();
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#finalize()
   */
  @Override
  protected void finalize() throws Throwable {
    if (!isDisposed) {
      LOG.warning("Memory Leak: Texture " + Integer.toString(textureId) +
          " is getting finalized by the Java GC without being disposed.");
    }
    super.finalize();
  }

  /**
   * Apply the filter values for the texture scaling.
   *
   * @param target the texture target
   * @param minFilter the minimize filter
   * @param magFilter the maximize filter
   * @throws CoreGLException in case setting the filter parameter fails
   */
  private void applyFilters(final int target, final int minFilter, final int magFilter) {
    GL11.glTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER, minFilter);
    checkGLError("glTexParameteri", true);
    GL11.glTexParameteri(target, GL11.GL_TEXTURE_MAG_FILTER, magFilter);
    checkGLError("glTexParameteri", true);
  }

  /**
   * Generate and bind a texture ID.
   *
   * @param target the target type of the texture
   * @param textureId the ID of the texture, in case this is {@code -1} a new texture ID will be generated.
   * @return the texture ID that was actually bound
   * @throws CoreGLException in case creating or binding the texture fails
   */
  private int applyTextureId(final int target, final int textureId) {
    final int usedTextureId;
    if (textureId == AUTO) {
      usedTextureId = createTextureID();
    } else {
      usedTextureId = textureId;
    }

    GL11.glBindTexture(target, usedTextureId);
    checkGLError("glBindTexture", true);
    return usedTextureId;
  }

  /**
   * Check if the border parameter is valid.
   *
   * @param border the border parameter
   * @throws CoreGLException in case the border parameter is not valid
   */
  private static void checkBorder(final int border) {
    if (border != 0 && border != 1) {
      throw new CoreGLException("Border has illegal value: 0x" + Integer.toHexString(border));
    }
  }

  /**
   * Check the format, the size and the data type value.
   *
   * @param format the selected format
   * @param type the selected data type
   * @throws CoreGLException in case the parameters don't work together
   */
  private static void checkFormatSizeData(final int format, final int type, final Buffer data) {
    if (data == null) {
      throw new CoreGLException("Pixeldata must not be NULL");
    }

    if (type == GL11.GL_BITMAP && format != GL11.GL_COLOR_INDEX) {
      throw new CoreGLException("GL_BITMAP requires the format to be GL_COLOR_INDEX");
    }

    switch (type) {
      case GL11.GL_UNSIGNED_BYTE:
      case GL11.GL_BYTE:
      case GL11.GL_BITMAP:
      case GL12.GL_UNSIGNED_BYTE_3_3_2:
      case GL12.GL_UNSIGNED_BYTE_2_3_3_REV:
        if (!(data instanceof ByteBuffer)) {
          throw new CoreGLException("The selected type requires its data as byte values.");
        }
        break;

      case GL11.GL_UNSIGNED_SHORT:
      case GL11.GL_SHORT:
      case GL12.GL_UNSIGNED_SHORT_5_6_5:
      case GL12.GL_UNSIGNED_SHORT_5_6_5_REV:
      case GL12.GL_UNSIGNED_SHORT_4_4_4_4:
      case GL12.GL_UNSIGNED_SHORT_4_4_4_4_REV:
      case GL12.GL_UNSIGNED_SHORT_5_5_5_1:
      case GL12.GL_UNSIGNED_SHORT_1_5_5_5_REV:
        if (!(data instanceof ShortBuffer)) {
          throw new CoreGLException("The selected type requires its data as short values.");
        }
        break;

      case GL11.GL_UNSIGNED_INT:
      case GL11.GL_INT:
      case GL12.GL_UNSIGNED_INT_8_8_8_8:
      case GL12.GL_UNSIGNED_INT_8_8_8_8_REV:
      case GL12.GL_UNSIGNED_INT_10_10_10_2:
      case GL12.GL_UNSIGNED_INT_2_10_10_10_REV:
        if (!(data instanceof IntBuffer)) {
          throw new CoreGLException("The selected type requires its data as integer values.");
        }
        break;

      case GL11.GL_FLOAT:
        if (!(data instanceof FloatBuffer) && !(data instanceof DoubleBuffer)) {
          throw new CoreGLException("The selected type requires its data as floating-point values.");
        }
        break;

      default:
        throw new CoreGLException("Unknown type value.");
    }
  }

  /**
   * This functions causes the OpenGL errors to be checked in case error checking is enabled.
   *
   * @param message the message in case a error is detected
   * @param throwException {@code true} in case an exception is supposed to be thrown in case a error is detected
   */
  private void checkGLError(final String message, final boolean throwException) {
    if (errorChecks) {
      checkGL.checkGLError(message, throwException);
    }
  }

  /**
   * This function is used to check if the size value fit the capabilities of OpenGL.
   *
   * @param width the width of the new texture
   * @param height the height of the new texture
   * @throws CoreGLException in case the texture dimensions are too large or negative
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
   * @param target the target ID
   * @throws CoreGLException in case the {@code target} parameter contains a illegal value
   */
  private void checkTarget(final int target) {
    switch (target) {
      case GL11.GL_TEXTURE_2D:
      case GL30.GL_TEXTURE_2D_ARRAY:
      case GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X:
      case GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_X:
      case GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Y:
      case GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y:
      case GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Z:
      case GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z:
        break;

      default:
        throw new CoreGLException("Illegal target ID: 0x" + Integer.toHexString(target));
    }
  }

  /**
   * Create a new texture and transfer the data into the OpenGL space. Also generate mipmaps as needed.
   *
   * @param textureId the ID of the texture to use, in case this is {@code -1} a new texture ID is generated
   * @param target the target of the create operation, has to be a valid 2D texture target
   * @param level the mipmap level, automatic mipmap generation is disabled in case this valid is not equal to 0
   * @param internalFormat the internal texture format
   * @param width the width of the texture
   * @param height the height of the texture
   * @param border the border width of the texture
   * @param format the format of the pixel data
   * @param type the data type of the pixel data
   * @param data the pixel data
   * @param magFilter the magnifying filter
   * @param minFilter the minimizing filter, in case a filter that uses mipmaps is selected, those mipmaps are generated
   *                  automatically
   * @return the texture ID of the newly created texture
   * @throws CoreGLException in case anything goes wrong
   */
  private int createTexture(
      final int textureId,
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
      checkFormatSizeData(format, usedType, data);
    }

    final int usedTextureId = applyTextureId(target, textureId);
    try {
      applyFilters(target, minFilter, magFilter);

      if (textureArray) {
        glTexImage3D(target, level, internalFormat, width, height, depth, border, format, usedType, data);
      } else if (isCreatingMipMaps(level, minFilter)) {
        if (GLContext.getCapabilities().OpenGL30) {
          glTexImage2D(target, 0, internalFormat, width, height, border, format, usedType, data);
          GL30.glGenerateMipmap(target);
          checkGLError("glGenerateMipmap", true);
        } else if (GLContext.getCapabilities().GL_EXT_framebuffer_object) {
          glTexImage2D(target, 0, internalFormat, width, height, border, format, usedType, data);
          EXTFramebufferObject.glGenerateMipmapEXT(target);
          checkGLError("glGenerateMipmapEXT", true);
        } else if (GLContext.getCapabilities().GL_SGIS_generate_mipmap &&
            ((isPowerOfTwo(height) && isPowerOfTwo(width)) || isNPOTHardwareSupported())) {
          GL11.glTexParameteri(target, SGISGenerateMipmap.GL_GENERATE_MIPMAP_SGIS, GL11.GL_TRUE);
          checkGLError("glTexParameteri", true);
          glTexImage2D(target, 0, internalFormat, width, height, border, format, usedType, data);
        } else {
          gluBuild2DMipmaps(target, internalFormat, width, height, format, usedType, data);
        }
      } else {
        glTexImage2D(target, level, internalFormat, width, height, border, format, usedType, data);
      }
    } catch (final CoreGLException ex) {
      if (textureId == -1) {
        GL11.glDeleteTextures(usedTextureId);
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
   * @throws CoreGLException in case generation a new texture ID fails
   */
  private int createTextureID() {
    final int id = GL11.glGenTextures();
    checkGLError("glGenTextures", true);
    return id;
  }

  /**
   * Automatically select a type in case its requested. This function only determines the best fitting type
   * automatically in case the {@code type} parameter is set to {@link #AUTO}.
   *
   * @param type the selected type of the pixel data
   * @param data the pixel data
   * @return the value of {@code type} or a automatically determined type
   * @throws CoreGLException in case the automated detection of the type fails
   */
  private int getType(final int type, final Buffer data) {
    if (type == AUTO) {
      if (data instanceof ByteBuffer) {
        return GL11.GL_UNSIGNED_BYTE;
      } else if (data instanceof ShortBuffer) {
        return GL11.GL_UNSIGNED_SHORT;
      } else if (data instanceof IntBuffer) {
        return GL11.GL_UNSIGNED_INT;
      } else if (data instanceof FloatBuffer) {
        return GL11.GL_FLOAT;
      } else if (data instanceof DoubleBuffer) {
        return GL11.GL_FLOAT;
      } else {
        throw new CoreGLException("Unknown buffer type; " + data.getClass().toString());
      }
    } else {
      return type;
    }
  }

  /**
   * This is a wrapper function for the actual call to {@code glTexImage2D}. It uses the generic {@link Buffer} and
   * internally casts it as needed to fit the different implementations of the {@code glTexImage2D} function.
   *
   * @param target the target of the texture creation operation
   * @param level the level of this texture
   * @param internalformat the internal format
   * @param width the width of the texture
   * @param height the height of the texture
   * @param border the border width of the texture
   * @param format the format of the pixel data
   * @param type the data type of the pixel data
   * @param pixels the pixel data
   * @throws CoreGLException in case OpenGL reports a error or in case the type of the buffer is unknown
   */
  private void glTexImage2D(
      final int target,
      final int level,
      final int internalformat,
      final int width,
      final int height,
      final int border,
      final int format,
      final int type,
      final Buffer pixels) {
    this.texImageTarget = target;
    this.texImageLevel = level;
    this.texImageInternalFormat = format;
    this.texImageWidth = width;
    this.texImageHeight = height;
    this.texBorder = border;
    this.texFormat = format;
    this.texType = type;
    this.textureCanBeUpdated = true;

    updateTextureData(pixels);
  }

  /**
   * This is a wrapper function for the actual call to {@code glTexImage3D}. It uses the generic {@link Buffer} and
   * internally casts it as needed to fit the different implementations of the {@code glTexImage3D} function.
   *
   * @param target the target of the texture creation operation
   * @param level the level of this texture
   * @param internalformat the internal format
   * @param width the width of the texture
   * @param height the height of the texture
   * @param depth the depth of the texture
   * @param border the border width of the texture
   * @param format the format of the pixel data
   * @param type the data type of the pixel data
   * @param pixels the pixel data
   * @throws CoreGLException in case OpenGL reports a error or in case the type of the buffer is unknown
   */
  private void glTexImage3D(
      final int target,
      final int level,
      final int internalformat,
      final int width,
      final int height,
      final int depth,
      final int border,
      final int format,
      final int type,
      final Buffer pixels) {
    this.texImageTarget = target;
    this.texImageLevel = level;
    this.texImageInternalFormat = format;
    this.texImageWidth = width;
    this.texImageHeight = height;
    this.texBorder = border;
    this.texFormat = format;
    this.texType = type;
    this.textureCanBeUpdated = true;
    this.texDepth = depth;

    updateTextureData3D(pixels);
  }

  @Override
  public void updateTextureData(final Buffer pixels) {
    if (!textureCanBeUpdated) {
      throw new CoreGLException("updateTextureData() call can only be used to update texture data");
    }

    if (pixels instanceof ByteBuffer) {
      GL11.glTexImage2D(texImageTarget, texImageLevel, texImageInternalFormat, texImageWidth, texImageHeight, texBorder, texFormat, texType, (ByteBuffer) pixels);
    } else if (pixels instanceof ShortBuffer) {
      GL11.glTexImage2D(texImageTarget, texImageLevel, texImageInternalFormat, texImageWidth, texImageHeight, texBorder, texFormat, texType, (ShortBuffer) pixels);
    } else if (pixels instanceof IntBuffer) {
      GL11.glTexImage2D(texImageTarget, texImageLevel, texImageInternalFormat, texImageWidth, texImageHeight, texBorder, texFormat, texType, (IntBuffer) pixels);
    } else if (pixels instanceof FloatBuffer) {
      GL11.glTexImage2D(texImageTarget, texImageLevel, texImageInternalFormat, texImageWidth, texImageHeight, texBorder, texFormat, texType, (FloatBuffer) pixels);
    } else if (pixels instanceof DoubleBuffer) {
      GL11.glTexImage2D(texImageTarget, texImageLevel, texImageInternalFormat, texImageWidth, texImageHeight, texBorder, texFormat, texType, (DoubleBuffer) pixels);
    } else {
      throw new CoreGLException("Unknown buffer type; " + pixels.getClass().toString());
    }
    checkGLError("glTexImage2D", true);
  }

  @Override
  public void updateTextureData3D(final Buffer pixels) {
    if (!textureCanBeUpdated) {
      throw new CoreGLException("updateTextureData() call can only be used to update texture data");
    }

    if (pixels instanceof ByteBuffer) {
      GL12.glTexImage3D(texImageTarget, texImageLevel, texImageInternalFormat, texImageWidth, texImageHeight, texDepth, texBorder, texFormat, texType, (ByteBuffer) pixels);
    } else if (pixels instanceof ShortBuffer) {
      GL12.glTexImage3D(texImageTarget, texImageLevel, texImageInternalFormat, texImageWidth, texImageHeight, texDepth, texBorder, texFormat, texType, (ShortBuffer) pixels);
    } else if (pixels instanceof IntBuffer) {
      GL12.glTexImage3D(texImageTarget, texImageLevel, texImageInternalFormat, texImageWidth, texImageHeight, texDepth, texBorder, texFormat, texType, (IntBuffer) pixels);
    } else if (pixels instanceof FloatBuffer) {
      GL12.glTexImage3D(texImageTarget, texImageLevel, texImageInternalFormat, texImageWidth, texImageHeight, texDepth, texBorder, texFormat, texType, (FloatBuffer) pixels);
    } else if (pixels instanceof DoubleBuffer) {
      GL12.glTexImage3D(texImageTarget, texImageLevel, texImageInternalFormat, texImageWidth, texImageHeight, texDepth, texBorder, texFormat, texType, (DoubleBuffer) pixels);
    } else {
      throw new CoreGLException("Unknown buffer type; " + pixels.getClass().toString());
    }
    checkGLError("glTexImage3D", true);
  }

  /**
   * This functions wraps the last resort function for the mipmap generation. This function only works in base
   * {@code data} is a {@link ByteBuffer}. It will create textures and its mipmaps.
   *
   * @param target the target of the texture creation operation
   * @param components the internal texture format
   * @param width the width of the texture
   * @param height the height of the texture
   * @param format the format of the pixel data
   * @param type the type of the pixel data
   * @param data the pixel data
   * @throws CoreGLException in case the creation of the mipmap fails
   */
  private void gluBuild2DMipmaps(final int target, final int components, final int width, final int height,
                                 final int format, final int type, final Buffer data) {
    if (data instanceof ByteBuffer) {
      GLU.gluBuild2DMipmaps(target, components, width, height, format, type, (ByteBuffer) data);
      checkGLError("gluBuild2DMipmaps", true);
    } else {
      throw new CoreGLException("MipMap creation not supported on this platform for non-byte buffers.");
    }
  }

  /**
   * Check if mipmaps are supposed to be generated.
   *
   * @param level the level settings
   * @param minFilter the minimizing filter
   * @return {@code true} in case mipmaps are supposed to be generated
   */
  private static boolean isCreatingMipMaps(final int level, final int minFilter) {
    if (level > 0) {
      return false;
    }

    switch (minFilter) {
      case GL11.GL_NEAREST_MIPMAP_NEAREST:
      case GL11.GL_LINEAR_MIPMAP_NEAREST:
      case GL11.GL_NEAREST_MIPMAP_LINEAR:
      case GL11.GL_LINEAR_MIPMAP_LINEAR:
        return true;

      default:
        return false;
    }
  }

  /**
   * Check if a value is power of two.
   * @param n the value to check
   * @return {@code true} in case the value is power of two
   */
  private static boolean isPowerOfTwo(final int n) {
    return ((n != 0) && (n & (n - 1)) == 0);
  }

  private static ResizeFilterInfo filterInfo(final int min, final int mag) {
    return new ResizeFilterInfo(min, mag);
  }

  private static ColorFormatInfo colorInfo(final int format, final int internal, final int compressed, final int comp) {
    return new ColorFormatInfo(format, internal, compressed, comp);
  }

  private static class ResizeFilterInfo {
    /**
     * The value of the minimizing filter.
     */
    private int minFilter;

    /**
     * The value of the magnifying filter.
     */
    private int magFilter;

    private ResizeFilterInfo(final int minFilter, final int magFilter) {
      this.minFilter = minFilter;
      this.magFilter = magFilter;
    }
  }

  private static class ColorFormatInfo {
    /**
     * The pixel data format.
     */
    private final int format;
  
    /**
     * The texture format.
     */
    private final int internalFormat;
  
    /**
     * The compressed kind of the texture format.
     */
    private final int compressedInternalFormat;
  
    /**
     * Number of components per pixels
     */
    private final int componentsPerPixel;
  
    /**
     * Default constructor.
     *
     * @param newFormat the pixel data format
     * @param newInternalFormat the internal format
     * @param newCompressedInternalFormat the compressed internal format
     * @param newComponentsPerPixel the number of components (usually bytes) per pixel
     */
    ColorFormatInfo(
        final int newFormat,
        final int newInternalFormat,
        final int newCompressedInternalFormat,
        final int newComponentsPerPixel) {
      format = newFormat;
      internalFormat = newInternalFormat;
      compressedInternalFormat = newCompressedInternalFormat;
      componentsPerPixel = newComponentsPerPixel;
    }
  }

  private static TypeInfo typeInfo(int value) {
    return new TypeInfo(value);
  }

  private static class TypeInfo {
    private int internalType;

    private TypeInfo(final int internalType) {
      this.internalType = internalType;
    }
  }
}
