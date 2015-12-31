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
  private String prefix = "";

  public static CoreLogger getCoreLogger(final String name) {
    return new CoreLogger(name);
  }

  public void info(final String message) {
    log.info(prepare(message));
  }

  public void info(final String message, final Object... args) {
    log.info(prepareWithArgs(message, args));
  }

  public void warn(final String message) {
    log.warning(prepare(message));
  }

  public void warn(final String message, final Object... args) {
    log.warning(prepareWithArgs(message, args));
  }

  public void severe(final String message) {
    log.severe(prepare(message));
  }

  public void severe(final String message, final Object... args) {
    log.severe(prepareWithArgs(message, args));
  }

  public void fine(final String message) {
    log.fine(prepare(message));
  }

  public void fine(final String message, final Object... args) {
    log.fine(prepareWithArgs(message, args));
  }

  public void setLoggingPrefix(final String prefix) {
    if (prefix == null) throw new IllegalArgumentException("prefix argument cannot be null");
    this.prefix = prefix;
  }

  public String getLoggingPrefix() {
    return prefix;
  }

  public void checkGLError(final CoreGL gl, final String message, final Object...args) {
    checkGLError(gl, false, message, args);
  }

  public void checkGLError(final CoreGL gl, final boolean throwException, final String message, final Object...args) {
    // pre-process message args and then pass to normal checkGLError
    checkGLError(gl, throwException, prepareWithArgs(message, args));
  }

  public void checkGLError(final CoreGL gl, final String message) {
    checkGLError(gl, false, message);
  }

  public void checkGLError(final CoreGL gl, final boolean throwException, final String message) {
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

  private String prepareWithArgs(final String message, final Object... args) {
    clearBuffer();
    concatBuffer.append(prefix);
    concatBuffer.append(message);
    for (final Object arg : args) {
      final int index = concatBuffer.indexOf(ARG_STR);
      concatBuffer.replace(index, index + 2, arg.toString());
    }
    return concatBuffer.toString();
  }

  // adds prefix to message without using varargs for argument processing
  private String prepare(final String message) {
    clearBuffer();
    concatBuffer.append(prefix);
    concatBuffer.append(message);
    return concatBuffer.toString();
  }

  private void clearBuffer () {
    concatBuffer.delete(0, concatBuffer.length());
  }

  private CoreLogger(final String name) {
    log = Logger.getLogger(name);
  }
}
