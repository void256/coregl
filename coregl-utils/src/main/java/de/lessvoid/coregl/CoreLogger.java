package de.lessvoid.coregl;

import java.util.logging.Logger;

import de.lessvoid.coregl.spi.CoreGL;

/**
 * Basic logging functionality for coregl-utils library classes; wraps a
 * java.util.logging.Logger and provides basic support for high-performance,
 * SLF4J-like string concatenation. Note: this class is not synchronized for
 * performance reasons and should not be used simultaneously across multiple
 * threads. Concurrent threads should each create their own CoreLogger instance,
 * if possible.
 *
 * @author Brian Groenke
 */
public final class CoreLogger {

  private static final int DEFAULT_BUFFER_CAPACITY = 1024;
  private static final String ARG_STR = "{}";
  private final Logger log;
  private final StringBuilder concatBuffer = new StringBuilder(DEFAULT_BUFFER_CAPACITY);

  public static CoreLogger getCoreLogger(final String name) {
    return new CoreLogger(name);
  }

  public void info(final String message) {
    log.info(message);
  }

  public void info(final String message, final Object... args) {
    info(concat(message, args));
  }

  public void warn(final String message) {
    log.warning(message);
  }

  public void warn(final String message, final Object... args) {
    warn(concat(message, args));
  }

  public void severe(final String message) {
    log.severe(message);
  }

  public void severe(final String message, final Object... args) {
    severe(concat(message, args));
  }

  public void fine(final String message) {
    log.fine(message);
  }

  public void fine(final String message, final Object... args) {
    fine(concat(message, args));
  }

  public void checkGLError(final CoreGL gl, final boolean throwException, final String message, final Object... args) {
    int error = gl.glGetError();
    boolean hasError = false;
    while (error != gl.GL_NO_ERROR()) {
      hasError = true;
      final String glerrmsg = gl.getUtil().gluErrorString(error);
      final StringBuilder stacktrace = new StringBuilder();
      for (final StackTraceElement strackTraceElement : Thread.currentThread().getStackTrace()) {
        stacktrace.append(strackTraceElement.toString());
        stacktrace.append("\n");
      }
      warn("OpenGL Error [{} | {}]: {} | Trace: {}", error, glerrmsg, message, stacktrace.toString());
      error = gl.glGetError();
    }

    if (hasError && throwException) {
      throw new CoreGLException("OpenGL Error occurred: " + message);
    }
  }

  public void checkGLError(final CoreGL gl, final String message, final Object...args) {
    checkGLError(gl, false, message, args);
  }

  private String concat(final String message, final Object... args) {
    concatBuffer.delete(0, concatBuffer.length()); // clear buffer
    concatBuffer.append(message);
    for (final Object arg : args) {
      final int index = concatBuffer.indexOf(ARG_STR);
      concatBuffer.replace(index, index + 2, arg.toString());
    }
    return concatBuffer.toString();
  }

  private CoreLogger(final String name) {
    log = Logger.getLogger(name);
  }
}
