package de.lessvoid.coregl;


import org.lwjgl.util.vector.Matrix4f;

public class MatrixFactory {
  public static Matrix4f createProjection(final float width, final float height) {
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

}
