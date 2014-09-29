package de.lessvoid.coregl;

import java.util.*;

class CoreTexture2DConstants {
	/**
	 * Image resizing mode. This enumerator is used in simple and defines the used filter for the magnifying and
	 * minimizing automatically.
	 */
	enum ResizeFilter {
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
	enum ColorFormat {
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

	enum Type {
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
	
	static class ResizeFilterInfo {
		/**
		 * The value of the minimizing filter.
		 */
		private int minFilter;

		/**
		 * The value of the magnifying filter.
		 */
		private int magFilter;

		ResizeFilterInfo(final int minFilter, final int magFilter) {
			this.minFilter = minFilter;
			this.magFilter = magFilter;
		}
	}

	static class ColorFormatInfo {
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

		/**GL11
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

	static class TypeInfo {
		int internalType;

		TypeInfo(final int internalType) {
			this.internalType = internalType;
		}
	}

	private Map<ResizeFilter, ResizeFilterInfo> resizeFilterMap = new Hashtable<ResizeFilter, ResizeFilterInfo>();
	private Map<ColorFormat, ColorFormatInfo> formatMap = new Hashtable<ColorFormat, ColorFormatInfo>();
	private Map<Type, TypeInfo> typeMap = new Hashtable<Type, TypeInfo>();
	
	CoreTexture2DConstants(final CoreGL gl) {
		initResizeFilterMap(gl);
		initColorFormatMap(gl);
		initTypeMap(gl);
	}
	
	private void initResizeFilterMap(CoreGL gl) {
		resizeFilterMap.put(ResizeFilter.Nearest, filterInfo(gl.GL_NEAREST(), gl.GL_NEAREST()));
		resizeFilterMap.put(ResizeFilter.Linear, filterInfo(gl.GL_LINEAR(), gl.GL_LINEAR()));
		resizeFilterMap.put(ResizeFilter.NearestMipMapNearest, filterInfo(gl.GL_NEAREST_MIPMAP_NEAREST(), gl.GL_NEAREST()));
		resizeFilterMap.put(ResizeFilter.NearestMipMapLinear, filterInfo(gl.GL_NEAREST_MIPMAP_LINEAR(), gl.GL_LINEAR()));
		resizeFilterMap.put(ResizeFilter.LinearMipMapNearest, filterInfo(gl.GL_LINEAR_MIPMAP_NEAREST(), gl.GL_LINEAR()));
		resizeFilterMap.put(ResizeFilter.LinearMipMapLinear, filterInfo(gl.GL_LINEAR_MIPMAP_LINEAR(), gl.GL_LINEAR()));
	}

	private void initColorFormatMap(CoreGL gl) {
		formatMap.put(ColorFormat.Red, colorInfo(gl.GL_RED(), gl.GL_RGB(), gl.GL_COMPRESSED_RGB(), 1));
		formatMap.put(ColorFormat.Green, colorInfo(gl.GL_GREEN(), gl.GL_RGB(), gl.GL_COMPRESSED_RGB(), 1));
		formatMap.put(ColorFormat.Blue, colorInfo(gl.GL_BLUE(), gl.GL_RGB(), gl.GL_COMPRESSED_RGB(), 1));
		formatMap.put(ColorFormat.Alpha, colorInfo(gl.GL_ALPHA(), gl.GL_ALPHA(), gl.GL_COMPRESSED_ALPHA(), 1));
		formatMap.put(ColorFormat.RGB, colorInfo(gl.GL_RGB(), gl.GL_RGB(), gl.GL_COMPRESSED_RGB(), 3));
		formatMap.put(ColorFormat.BGR, colorInfo(gl.GL_BGR(), gl.GL_RGB(), gl.GL_COMPRESSED_RGB(), 3));
		formatMap.put(ColorFormat.RGBA, colorInfo(gl.GL_RGBA(), gl.GL_RGBA(), gl.GL_COMPRESSED_RGBA(), 4));
		formatMap.put(ColorFormat.BGRA, colorInfo(gl.GL_BGRA(), gl.GL_RGBA(), gl.GL_COMPRESSED_RGBA(), 4));
		formatMap.put(ColorFormat.Luminance, colorInfo(gl.GL_LUMINANCE(), gl.GL_LUMINANCE(), gl.GL_COMPRESSED_LUMINANCE(), 1));
		formatMap.put(ColorFormat.LuminanceAlpha, colorInfo(gl.GL_LUMINANCE_ALPHA(), gl.GL_LUMINANCE_ALPHA(), gl.GL_COMPRESSED_LUMINANCE_ALPHA(), 2));
	}

	private void initTypeMap(CoreGL gl) {
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
	
	private TypeInfo typeInfo(int value) {
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
