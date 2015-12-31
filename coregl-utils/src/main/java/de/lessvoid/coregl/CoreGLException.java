/**
 * Copyright (c) 2013, Jens Hohmuth
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package de.lessvoid.coregl;

/**
 * This is the exception thrown by the classes of the CoreGL library in case
 * something goes wrong badly.
 *
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
public class CoreGLException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  /**
   * Constructs a new CoreGL exception with {@code null} as its detail message.
   * The cause is not initialized, and may subsequently be initialized by a call
   * to {@link #initCause}.
   */
  public CoreGLException() {
    super();
  }

  /**
   * Constructs a new CoreGL exception with the specified detail message. The
   * cause is not initialized, and may subsequently be initialized by a call to
   * {@link #initCause}.
   *
   * @param message
   *          the detail message. The detail message is saved for later
   *          retrieval by the {@link #getMessage()} method.
   */
  public CoreGLException(final String message) {
    super(message);
  }

  /**
   * Constructs a new CoreGL exception with the specified detail message and
   * cause.
   * <p />
   * Note that the detail message associated with {@code cause} is <i>not</i>
   * automatically incorporated in this CoreGL exception's detail message.
   *
   * @param message
   *          the detail message (which is saved for later retrieval by the
   *          {@link #getMessage()} method).
   * @param cause
   *          the cause (which is saved for later retrieval by the
   *          {@link #getCause()} method). (A <tt>null</tt> value is permitted,
   *          and indicates that the cause is nonexistent or unknown.)
   */
  public CoreGLException(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new CoreGL exception with the specified cause and a detail
   * message of <tt>(cause==null ? null : cause.toString())</tt> (which
   * typically contains the class and detail message of <tt>cause</tt>). This
   * constructor is useful for CoreGL exceptions that are little more than
   * wrappers for other throwables.
   *
   * @param cause
   *          the cause (which is saved for later retrieval by the
   *          {@link #getCause()} method). (A <tt>null</tt> value is permitted,
   *          and indicates that the cause is nonexistent or unknown.)
   */
  public CoreGLException(final Throwable cause) {
    super(cause);
  }
}
