package com.lessvoid.coregl;

/**
 * Image resizing mode. This enumerator is used in simple and defines the used
 * filter for the magnifying and minimizing automatically.
 */
public enum ResizeFilter {
  /**
   * Nearest filter. This filter applies the nearest filter to both the
   * magnifying and the minimizing filter.
   */
  Nearest,

  /**
   * Linear filter. This applies the linear filter to ot the magnifying and the
   * minimizing filter.
   */
  Linear,

  /**
   * This filter applies the linear nearest to the magnifying filter and the
   * nearest mipmap nearest filter to the minimizing filter. This filter is the
   * fastest mipmap based filtering
   */
  NearestMipMapNearest,

  /**
   * This filter applies the linear linear to the magnifying filter and the
   * linear mipmap nearest filter to the minimizing filter. This filter is
   * slightly slower then {@link #NearestMipMapNearest} but creates a better
   * quality.
   */
  NearestMipMapLinear,

  /**
   * This filter applies the linear linear to the magnifying filter and the
   * linear mipmap nearest linear to the minimizing filter. This filter is
   * slightly slower then {@link #NearestMipMapLinear} but creates a better
   * quality.
   */
  LinearMipMapNearest,

  /**
   * This filter applies the linear linear to the magnifying filter and the
   * linear mipmap linear linear to the minimizing filter. This filter creates
   * the best quality but is also the slowest filter method.
   */
  LinearMipMapLinear;
}