package de.lessvoid.coregl;


import org.lwjgl.util.vector.Matrix4f;

/**
 * Creates LWJGL Matrix4j instances for projection- and orthographic  projection.
 * @author void
 */
public class CoreMatrixFactory {

  /**
   * Create orthographic projection Matrix4f.
   * @param width width
   * @param height height
   * @return new Matrix4j
   */
  public static Matrix4f createOrtho(final float width, final float height) {
    float left = 0;
    float right = width;
    float top = 0;
    float bottom = height;
    float zNear = -9999;
    float zFar = 9999;

    Matrix4f projection = new Matrix4f();
    projection.m00 = 2 / (right-left);
    projection.m30 = - (right+left) / (right-left);
    projection.m11 = 2 / (top-bottom);
    projection.m31 = - (top+bottom) / (top-bottom);
    projection.m22 = -2 / (zFar-zNear);
    projection.m32 = - (zFar+zNear) / (zFar-zNear);
    projection.m33 = 1;
    return projection;
  }

  /**
   * Create perspective projection Matrix4f.
   * @param width width
   * @param height height
   * @param zNear z near value (for example 1)
   * @param zFar z far value (for example 10000)
   * @return new perspective projection matrix
   */
  public static Matrix4f createProjection(final float width, final float height, final float zNear, final float zFar) {
    float left = 0;
    float right = width;
    float top = 0;
    float bottom = height;

    Matrix4f projection = new Matrix4f();
    projection.m00 = 2 * zNear / (right-left);
    projection.m20 = (right+left) / (right-left);
    projection.m11 = 2 * zNear / (top-bottom);
    projection.m21 = (top+bottom) / (top-bottom);
    projection.m22 = -(zFar + zNear) / (zFar-zNear);
    projection.m32 = - 2 * zFar * zNear / (zFar-zNear);
    projection.m23 = -1;
    return projection;
  }
}
