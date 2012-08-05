package de.lessvoid.coregl;


import java.util.logging.Logger;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;

/**
 * Helper method to check for GL errors. This will call glGetError() and as long as the call returns something else then
 * GL_NO_ERROR it will log the error and the stacktrace of the caller.
 *
 * @author void
 */
public class CheckGL {
  private static Logger log = Logger.getLogger(CheckGL.class.getName());

  /**
   * Check for GL error and log any errors found. You should probably call this once a frame.
   * @param message a message to log (can be used to log additional informations for instance what call was executed
   *        before)
   */
  public static void checkGLError(final String message) {
    int error = glGetError();
    while (error != GL_NO_ERROR) {
      String glerrmsg = GLU.gluErrorString(error);
      StringBuffer stacktrace = new StringBuffer();
      for (StackTraceElement e : Thread.currentThread().getStackTrace()) {
        stacktrace.append(e.toString());
        stacktrace.append("\n");
      }
      log.warning("OpenGL Error: (" + error + ") " + glerrmsg + " {" + message + "} " + stacktrace.toString());
      error = glGetError();
    }
  }
}
