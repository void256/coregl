package de.lessvoid.coregl;

import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL31.glDrawArraysInstanced;

/**
 * Simple helper methods to render vertex arrays.
 * @author void
 */
public class CoreRender {

  /**
   * Render the currently active VAO using triangle strips with the given
   * number of vertices.
   *
   * @param count number of vertices to render as triangle strips
   */
  public static void renderTriangleStrip(final int count) {
    glDrawArrays(GL_TRIANGLE_STRIP, 0, count);
    CoreCheckGL.checkGLError("glDrawArrays");
  }

  /**
   * Render the currently active VAO using triangle strips with the given
   * number of vertices AND do that primCount times.
   *
   * @param count number of vertices to render as triangle strips per primitve
   * @param primCount number of primitives to render
   */
  public static void renderTriangleStripInstances(final int count, int primCount) {
    glDrawArraysInstanced(GL_TRIANGLE_STRIP, 0, count, primCount);
    CoreCheckGL.checkGLError("glDrawArraysInstanced(GL_TRIANGLE_STRIP)");
  }

  /**
   * Render the currently active VAO using points with the given
   * number of vertices AND do that primCount times.
   *
   * @param count number of vertices to render as points per primitive
   * @param primCount number of primitives to render
   */
  public static void renderPointsInstances(final int count, int primCount) {
    glDrawArraysInstanced(GL_POINTS, 0, count, primCount);
    CoreCheckGL.checkGLError("glDrawArraysInstanced(GL_POINTS)");
  }
}
