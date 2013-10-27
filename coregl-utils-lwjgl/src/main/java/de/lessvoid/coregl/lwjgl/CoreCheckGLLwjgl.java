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
package de.lessvoid.coregl.lwjgl;


import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.glGetError;

import java.util.logging.Logger;

import org.lwjgl.util.glu.GLU;

import de.lessvoid.coregl.CoreCheckGL;
import de.lessvoid.coregl.CoreGLException;

public class CoreCheckGLLwjgl implements CoreCheckGL {
  // The logger of this class.
  private static Logger log = Logger.getLogger(CoreCheckGLLwjgl.class.getName());

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreCheckGL#checkGLError()
   */
  @Override
  public void checkGLError() {
    checkGLError("");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreCheckGL#checkGLError(java.lang.String)
   */
  @Override
  public void checkGLError(final String message) {
    checkGLError(message, false);
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreCheckGL#checkGLError(java.lang.String, boolean)
   */
  @Override
  public void checkGLError(final String message, final boolean throwException) {
    int error = glGetError();
    boolean hasError = false;
    while (error != GL_NO_ERROR) {
      hasError = true;
      String glerrmsg = GLU.gluErrorString(error);
      StringBuilder stacktrace = new StringBuilder();
      for (StackTraceElement strackTraceElement : Thread.currentThread().getStackTrace()) {
        stacktrace.append(strackTraceElement.toString());
        stacktrace.append("\n");
      }
      log.warning("OpenGL Error: (" + error + ") " + glerrmsg + " {" + message + "} " + stacktrace.toString());
      error = glGetError();
    }

    if (hasError && throwException) {
      throw new CoreGLException("OpenGL Error occurred:" + message);
    }
  }
}
