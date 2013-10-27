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
 * This is not really OpenGL core profile specific. It's just a helper class to save the content of
 * the color or stencil buffer to a file. I always wanted to have a class like that ... and now I have one ;D
 * @author void
 */
public interface CoreScreenshot {

  /**
   * Save the color buffer to a disk file. Since this is really meant for development purpose it will catch
   * any IOExceptions and throws a RuntimeException if it fails for easy drop-in use.
   * @param filename target filename to save
   * @param width the width of the image to save
   * @param height the height of the image to save
   */
  void color(String filename, int width, int height);

  /**
   * Save the stencil buffer to a disk file. Since this is really meant for development purpose it will catch
   * any IOExceptions and throws a RuntimeException if it fails for easy drop-in use.
   * @param filename target filename to save
   * @param width the width of the image to save
   * @param height the height of the image to save
   */
  void stencil(String filename, int width, int height);
}
