package de.lessvoid.coregl;

import java.util.logging.Level;
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

  /* These arrays are used to transfer the arguments between the logging functions without requiring new objects. */
  private static final Object[] transferArray1 = new Object[1];
  private static final Object[] transferArray2 = new Object[2];
  private static final Object[] transferArray3 = new Object[3];

  public static CoreLogger getCoreLogger(final String name) {
    return new CoreLogger(name);
  }

  public void info(final String message) {
    if (log.isLoggable(Level.INFO)) {
      log.info(prepare(message));
    }
  }

  public void info(final String message, final Object arg1) {
    Object[] transferArray = transferArray1;
    transferArray[0] = arg1;

    info(message, transferArray);
  }

  public void info(final String message, final Object arg1, final Object arg2) {
    Object[] transferArray = transferArray2;
    transferArray[0] = arg1;
    transferArray[1] = arg2;

    info(message, transferArray);
  }

  public void info(final String message, final Object arg1, final Object arg2, final Object arg3) {
    Object[] transferArray = transferArray3;
    transferArray[0] = arg1;
    transferArray[1] = arg2;
    transferArray[2] = arg3;

    info(message, transferArray);
  }

  public void info(final String message, final Object... args) {
    if (log.isLoggable(Level.INFO)) {
      log.info(prepareWithArgs(message, args));
    }
  }

  public void warn(final String message) {
    if (log.isLoggable(Level.WARNING)) {
      log.warning(prepare(message));
    }
  }

  public void warn(final String message, final Object arg1) {
    Object[] transferArray = transferArray1;
    transferArray[0] = arg1;

    warn(message, transferArray);
  }

  public void warn(final String message, final Object arg1, final Object arg2) {
    Object[] transferArray = transferArray2;
    transferArray[0] = arg1;
    transferArray[1] = arg2;

    warn(message, transferArray);
  }

  public void warn(final String message, final Object arg1, final Object arg2, final Object arg3) {
    Object[] transferArray = transferArray3;
    transferArray[0] = arg1;
    transferArray[1] = arg2;
    transferArray[2] = arg3;

    warn(message, transferArray);
  }

  public void warn(final String message, final Object... args) {
    if (log.isLoggable(Level.WARNING)) {
      log.warning(prepareWithArgs(message, args));
    }
  }

  public void severe(final String message) {
    if (log.isLoggable(Level.SEVERE)) {
      log.severe(prepare(message));
    }
  }

  public void severe(final String message, final Object arg1) {
    Object[] transferArray = transferArray1;
    transferArray[0] = arg1;

    severe(message, transferArray);
  }

  public void severe(final String message, final Object arg1, final Object arg2) {
    Object[] transferArray = transferArray2;
    transferArray[0] = arg1;
    transferArray[1] = arg2;

    severe(message, transferArray);
  }

  public void severe(final String message, final Object arg1, final Object arg2, final Object arg3) {
    Object[] transferArray = transferArray3;
    transferArray[0] = arg1;
    transferArray[1] = arg2;
    transferArray[2] = arg3;

    severe(message, transferArray);
  }

  public void severe(final String message, final Object... args) {
    if (log.isLoggable(Level.SEVERE)) {
      log.severe(prepareWithArgs(message, args));
    }
  }

  public void fine(final String message) {
    if (log.isLoggable(Level.FINE)) {
      log.fine(prepare(message));
    }
  }

  public void fine(final String message, final Object arg1) {
    Object[] transferArray = transferArray1;
    transferArray[0] = arg1;

    fine(message, transferArray);
  }

  public void fine(final String message, final Object arg1, final Object arg2) {
    Object[] transferArray = transferArray2;
    transferArray[0] = arg1;
    transferArray[1] = arg2;

    fine(message, transferArray);
  }

  public void fine(final String message, final Object arg1, final Object arg2, final Object arg3) {
    Object[] transferArray = transferArray3;
    transferArray[0] = arg1;
    transferArray[1] = arg2;
    transferArray[2] = arg3;

    fine(message, transferArray);
  }

  public void fine(final String message, final Object... args) {
    if (log.isLoggable(Level.FINE)) {
      log.fine(prepareWithArgs(message, args));
    }
  }

  public void setLoggingPrefix(final String prefix) {
    if (prefix == null) throw new IllegalArgumentException("prefix argument cannot be null");
    this.prefix = prefix;
  }

  public String getLoggingPrefix() {
    return prefix;
  }

  public void checkGLError(final CoreGL gl, final String message, final Object arg1) {
    Object[] transferArray = transferArray1;
    transferArray[0] = arg1;

    checkGLError(gl, message, transferArray);
  }

  public void checkGLError(final CoreGL gl, final String message, final Object arg1, final Object arg2) {
    Object[] transferArray = transferArray2;
    transferArray[0] = arg1;
    transferArray[1] = arg2;

    checkGLError(gl, message, transferArray);
  }

  public void checkGLError(final CoreGL gl,
                           final String message,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3) {
    Object[] transferArray = transferArray3;
    transferArray[0] = arg1;
    transferArray[1] = arg2;
    transferArray[2] = arg3;

    checkGLError(gl, message, transferArray);
  }

  public void checkGLError(final CoreGL gl, final String message, final Object...args) {
    checkGLError(gl, false, message, args);
  }

  public void checkGLError(final CoreGL gl, final boolean throwException, final String message, final Object arg1) {
    Object[] transferArray = transferArray1;
    transferArray[0] = arg1;

    checkGLError(gl, throwException, message, transferArray);
  }

  public void checkGLError(final CoreGL gl,
                           final boolean throwException,
                           final String message,
                           final Object arg1,
                           final Object arg2) {
    Object[] transferArray = transferArray2;
    transferArray[0] = arg1;
    transferArray[1] = arg2;

    checkGLError(gl, throwException, message, transferArray);
  }

  public void checkGLError(final CoreGL gl,
                           final boolean throwException,
                           final String message,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3) {
    Object[] transferArray = transferArray3;
    transferArray[0] = arg1;
    transferArray[1] = arg2;
    transferArray[2] = arg3;

    checkGLError(gl, throwException, message, transferArray);
  }

  public void checkGLError(final CoreGL gl, final boolean throwException, final String message, final Object...args) {
    checkGLErrorInternal(gl, throwException, message, args);
  }

  public void checkGLError(final CoreGL gl, final String message) {
    checkGLError(gl, false, message);
  }

  public void checkGLError(final CoreGL gl, final boolean throwException, final String message) {
    checkGLErrorInternal(gl, throwException, message, null);
  }

  private void checkGLErrorInternal(final CoreGL gl,
                                    final boolean throwException,
                                    final String message,
                                    final Object[] args) {
    int error = gl.glGetError();
    int noError = gl.GL_NO_ERROR();
    boolean hasError = false;
    String preparedMessage = null;
    String stackTrace = null;
    boolean warningEnabled = log.isLoggable(Level.WARNING);

    while (error != noError) {
      hasError = true;
      if ((warningEnabled || throwException) && (preparedMessage == null)) {
        if (args != null) {
          preparedMessage = prepareWithArgs(message, args);
        } else {
          preparedMessage = prepare(message);
        }
      }

      if (warningEnabled) {
        String glerrmsg = gl.getUtil().gluErrorString(error);
        if (stackTrace == null) {
          clearBuffer();
          for (final StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            concatBuffer.append(stackTraceElement.toString());
            concatBuffer.append("\n");
          }
          stackTrace = concatBuffer.toString();
        }
        warn("OpenGL Error [{} | {}]: {} | Trace: {}", error, glerrmsg, preparedMessage, stackTrace);
      }
      error = gl.glGetError();
    }

    if (hasError && throwException) {
      throw new CoreGLException("OpenGL Error occurred: " + preparedMessage);
    }
  }

  private String prepareWithArgs(final String message, final Object... args) {
    clearBuffer();
    concatBuffer.append(prefix);
    concatBuffer.append(message);
    for (final Object arg : args) {
      final int index = concatBuffer.indexOf(ARG_STR);
      if (index == -1) {
        severe("Error preparing logging message. Expected {} parameters in message: \"{}\"", args.length, message);
        break;
      }
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
