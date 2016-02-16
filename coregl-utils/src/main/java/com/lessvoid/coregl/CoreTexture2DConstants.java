package com.lessvoid.coregl;

import java.util.Hashtable;
import java.util.Map;

import com.lessvoid.coregl.spi.CoreGL;

/**
 * @author Brian Groenke
 */
class CoreTexture2DConstants {

  static class ResizeFilterInfo {
    /**
     * The value of the minimizing filter.
     */
    int minFilter;

    /**
     * The value of the magnifying filter.
     */
    int magFilter;

    ResizeFilterInfo(final int minFilter, final int magFilter) {
      this.minFilter = minFilter;
      this.magFilter = magFilter;
    }
  }

  static class ColorFormatInfo {
    /**
     * The pixel data format.
     */
    final int format;

    /**
     * The texture format.
     */
    final int internalFormat;

    /**
     * The compressed kind of the texture format.
     */
    final int compressedInternalFormat;

    /**
     * Number of components per pixels
     */
    final int componentsPerPixel;

    /**
     * GL11 Default constructor.
     *
     * @param newFormat
     *          the pixel data format
     * @param newInternalFormat
     *          the internal format
     * @param newCompressedInternalFormat
     *          the compressed internal format
     * @param newComponentsPerPixel
     *          the number of components (usually bytes) per pixel
     */
    ColorFormatInfo(final int newFormat, final int newInternalFormat, final int newCompressedInternalFormat,
        final int newComponentsPerPixel) {
      format = newFormat;
      internalFormat = newInternalFormat;
      compressedInternalFormat = newCompressedInternalFormat;
      componentsPerPixel = newComponentsPerPixel;
    }
  }

  static class TypeInfo {
    int internalType;

    TypeInfo(final int internalType) {
      this.internalType = internalType;
    }
  }

  private final Map<ResizeFilter, ResizeFilterInfo> resizeFilterMap = new Hashtable<ResizeFilter, ResizeFilterInfo>();
  private final Map<ColorFormat, ColorFormatInfo> formatMap = new Hashtable<ColorFormat, ColorFormatInfo>();
  private final Map<Type, TypeInfo> typeMap = new Hashtable<Type, TypeInfo>();

  CoreTexture2DConstants(final CoreGL gl) {
    initResizeFilterMap(gl);
    initColorFormatMap(gl);
    initTypeMap(gl);
  }

  private void initResizeFilterMap(final CoreGL gl) {
    resizeFilterMap.put(ResizeFilter.Nearest, filterInfo(gl.GL_NEAREST(), gl.GL_NEAREST()));
    resizeFilterMap.put(ResizeFilter.Linear, filterInfo(gl.GL_LINEAR(), gl.GL_LINEAR()));
    resizeFilterMap.put(ResizeFilter.NearestMipMapNearest, filterInfo(gl.GL_NEAREST_MIPMAP_NEAREST(), gl.GL_NEAREST()));
    resizeFilterMap.put(ResizeFilter.NearestMipMapLinear, filterInfo(gl.GL_NEAREST_MIPMAP_LINEAR(), gl.GL_LINEAR()));
    resizeFilterMap.put(ResizeFilter.LinearMipMapNearest, filterInfo(gl.GL_LINEAR_MIPMAP_NEAREST(), gl.GL_LINEAR()));
    resizeFilterMap.put(ResizeFilter.LinearMipMapLinear, filterInfo(gl.GL_LINEAR_MIPMAP_LINEAR(), gl.GL_LINEAR()));
  }

  private void initColorFormatMap(final CoreGL gl) {
    formatMap.put(ColorFormat.Red, colorInfo(gl.GL_RED(), gl.GL_RGB(), gl.GL_COMPRESSED_RGB(), 1));
    formatMap.put(ColorFormat.Green, colorInfo(gl.GL_GREEN(), gl.GL_RGB(), gl.GL_COMPRESSED_RGB(), 1));
    formatMap.put(ColorFormat.Blue, colorInfo(gl.GL_BLUE(), gl.GL_RGB(), gl.GL_COMPRESSED_RGB(), 1));
    formatMap.put(ColorFormat.Alpha, colorInfo(gl.GL_ALPHA(), gl.GL_ALPHA(), gl.GL_COMPRESSED_ALPHA(), 1));
    formatMap.put(ColorFormat.RGB, colorInfo(gl.GL_RGB(), gl.GL_RGB(), gl.GL_COMPRESSED_RGB(), 3));
    formatMap.put(ColorFormat.BGR, colorInfo(gl.GL_BGR(), gl.GL_RGB(), gl.GL_COMPRESSED_RGB(), 3));
    formatMap.put(ColorFormat.RGBA, colorInfo(gl.GL_RGBA(), gl.GL_RGBA(), gl.GL_COMPRESSED_RGBA(), 4));
    formatMap.put(ColorFormat.BGRA, colorInfo(gl.GL_BGRA(), gl.GL_RGBA(), gl.GL_COMPRESSED_RGBA(), 4));
    formatMap.put(ColorFormat.Luminance,
                  colorInfo(gl.GL_LUMINANCE(), gl.GL_LUMINANCE(), gl.GL_COMPRESSED_LUMINANCE(), 1));
    formatMap.put(ColorFormat.LuminanceAlpha,
                  colorInfo(gl.GL_LUMINANCE_ALPHA(), gl.GL_LUMINANCE_ALPHA(), gl.GL_COMPRESSED_LUMINANCE_ALPHA(), 2));
  }

  private void initTypeMap(final CoreGL gl) {
    typeMap.put(Type.UNSIGNED_BYTE, typeInfo(gl.GL_UNSIGNED_BYTE()));
    typeMap.put(Type.BYTE, typeInfo(gl.GL_BYTE()));
    typeMap.put(Type.UNSIGNED_SHORT, typeInfo(gl.GL_UNSIGNED_SHORT()));
    typeMap.put(Type.SHORT, typeInfo(gl.GL_SHORT()));
    typeMap.put(Type.UNSIGNED_INT, typeInfo(gl.GL_SHORT()));
    typeMap.put(Type.INT, typeInfo(gl.GL_INT()));
    typeMap.put(Type.FLOAT, typeInfo(gl.GL_FLOAT()));
    typeMap.put(Type.UNSIGNED_BYTE_3_3_2, typeInfo(gl.GL_UNSIGNED_BYTE_3_3_2()));
    typeMap.put(Type.UNSIGNED_BYTE_2_3_3_REV, typeInfo(gl.GL_UNSIGNED_BYTE_2_3_3_REV()));
    typeMap.put(Type.UNSIGNED_SHORT_5_6_5, typeInfo(gl.GL_UNSIGNED_SHORT_5_6_5()));
    typeMap.put(Type.UNSIGNED_SHORT_5_6_5_REV, typeInfo(gl.GL_UNSIGNED_SHORT_5_6_5_REV()));
    typeMap.put(Type.UNSIGNED_SHORT_4_4_4_4, typeInfo(gl.GL_UNSIGNED_SHORT_4_4_4_4()));
    typeMap.put(Type.UNSIGNED_SHORT_4_4_4_4_REV, typeInfo(gl.GL_UNSIGNED_SHORT_4_4_4_4_REV()));
    typeMap.put(Type.UNSIGNED_SHORT_5_5_5_1, typeInfo(gl.GL_UNSIGNED_SHORT_5_5_5_1()));
    typeMap.put(Type.UNSIGNED_SHORT_1_5_5_5_REV, typeInfo(gl.GL_UNSIGNED_SHORT_1_5_5_5_REV()));
    typeMap.put(Type.UNSIGNED_INT_8_8_8_8, typeInfo(gl.GL_UNSIGNED_INT_8_8_8_8()));
    typeMap.put(Type.UNSIGNED_INT_8_8_8_8_REV, typeInfo(gl.GL_UNSIGNED_INT_8_8_8_8_REV()));
    typeMap.put(Type.UNSIGNED_INT_10_10_10_2, typeInfo(gl.GL_UNSIGNED_INT_10_10_10_2()));
    typeMap.put(Type.UNSIGNED_INT_2_10_10_10_REV, typeInfo(gl.GL_UNSIGNED_INT_2_10_10_10_REV()));
  }

  private ResizeFilterInfo filterInfo(final int min, final int mag) {
    return new ResizeFilterInfo(min, mag);
  }

  private ColorFormatInfo colorInfo(final int format, final int internal, final int compressed, final int comp) {
    return new ColorFormatInfo(format, internal, compressed, comp);
  }

  private TypeInfo typeInfo(final int value) {
    return new TypeInfo(value);
  }

  ResizeFilterInfo getResizeFilter(final ResizeFilter filter) {
    return resizeFilterMap.get(filter);
  }

  ColorFormatInfo getColorInfo(final ColorFormat format) {
    return formatMap.get(format);
  }

  TypeInfo getTypeInfo(final Type type) {
    return typeMap.get(type);
  }
}
