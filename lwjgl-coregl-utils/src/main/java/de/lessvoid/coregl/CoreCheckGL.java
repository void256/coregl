package de.lessvoid.coregl;


import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.glGetError;

import java.util.logging.Logger;

import org.lwjgl.util.glu.GLU;

/**
 * Helper method to check for GL errors. This will call glGetError() and as long as the call returns not GL_NO_ERROR it
 * will log the error and the stacktrace of the caller using jdk14 logging.
 *
 * @author void
 */
public class CoreCheckGL {
  private static Logger log = Logger.getLogger(CoreCheckGL.class.getName());

  /**
   * Check for GL error and log any errors found. You should probably call this once a frame.
   */
  public static void checkGLError() {
    checkGLError("");
  }

  /**
   * Check for GL error and log any errors found. You should probably call this once a frame.
   * @param message a message to log
   *        (can be used to log additional informations for instance what call was executed before)
   */
  public static void checkGLError(final String message) {
    int error = glGetError();
    while (error != GL_NO_ERROR) {
      String glerrmsg = GLU.gluErrorString(error);
      StringBuilder stacktrace = new StringBuilder();
      for (StackTraceElement strackTraceElement : Thread.currentThread().getStackTrace()) {
        stacktrace.append(strackTraceElement.toString());
        stacktrace.append("\n");
      }
      log.warning("OpenGL Error: (" + error + ") " + glerrmsg + " {" + message + "} " + stacktrace.toString());
      error = glGetError();
    }
  }
}
