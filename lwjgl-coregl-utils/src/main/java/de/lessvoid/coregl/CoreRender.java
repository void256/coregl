package de.lessvoid.coregl;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL31.glDrawArraysInstanced;

public class CoreRender {
  public static void renderTriangleStrip() {
    glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
    CoreCheckGL.checkGLError("glDrawArrays");
  }

  public static void renderTriangleStripInstances(final int count) {
    glDrawArraysInstanced(GL_TRIANGLE_STRIP, 0, 4, count);
    CoreCheckGL.checkGLError("glDrawArraysInstanced");
  }
}
