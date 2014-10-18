package de.lessvoid.coregl;

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