package de.lessvoid.coregl;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL31.glDrawArraysInstanced;

public class Render {
  public static void render() {
    glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
    CheckGL.checkGLError("glDrawArrays");
  }

  public static void renderInstances(final int count) {
    glDrawArraysInstanced(GL_TRIANGLE_STRIP, 0, 4, count);
    CheckGL.checkGLError("glDrawArraysInstanced");
  }
}
