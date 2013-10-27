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
 * Helper method to check for GL errors. This will call glGetError() and as long as the call returns not GL_NO_ERROR it
 * will log the error and the stacktrace of the caller using jdk14 logging.
 *
 * @author void
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
public interface CoreCheckGL {
  /**
   * Check for GL error and log any errors found. You should probably call this once a frame.
   */
  void checkGLError();

  /**
   * Check for GL error and log any errors found. You should probably call this once a frame.
   *
   * @param message a message to log
   *        (can be used to log additional information for instance what call was executed before)
   */
  void checkGLError(String message);

  /**
   * Check for GL error and log any errors found. You should probably call this once a frame.
   *
   * @param message a message to log
   *        (can be used to log additional information for instance what call was executed before)
   * @param throwException in case this value is set {@code true} and OpenGL reports a error a exception will be thrown
   * @throws CoreGLException in case the {@code throwException} is set {@code true} and OpenGL reports an error
   */
  void checkGLError(String message, boolean throwException);
}
