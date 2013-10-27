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
 * Helper class to use a frame buffer object. This will take care of all the necessary setup. Currently this class only
 * supports texture and not yet render buffer objects.
 *
 * You're supposed to provide the actual textures an attach the textures to this FBO through the methods provided on
 * this class.
 *
 * You can enable rendering to the FBO with a call to enable() and disable it again with a call to disable(). When you
 * later want to use the textures you've attached earlier you can simply bind() the original texture.
 *
 * @author void
 */
public interface CoreFBO {
  /**
   * Enable this FBO (glBindFramebuffer)
   */
  void bindFramebuffer();

  /**
   * Disable rendering to this FBO.
   */
  void disable();

  /**
   * Disable rendering to this FBO and reset the Viewport back to screen size.
   */
  void disableAndResetViewport();

  /**
   * Delete the FBO.
   */
  void delete();

  /**
   * Attach a texture object to this FBO.
   *
   * @param textureId the texture to attach
   * @param colorAttachmentIdx the color attachment to use (0=GL_COLOR_ATTACHMENT0, 1=GL_COLOR_ATTACHMENT1 and so on)
   */
  void attachTexture(int textureId, int colorAttachmentIdx);

  /**
   * Attach a texture object from a texture array to this FBO.
   *
   * @param textureId the texture to attach
   * @param colorAttachmentIdx the color attachment to use (0=GL_COLOR_ATTACHMENT0, 1=GL_COLOR_ATTACHMENT1 and so on)
   * @param layer the texture index inside the texture array to attach
   */
  void attachTexture(int textureId, int colorAttachmentIdx, int layer);

  /**
   * Attach a stencil buffer to this FBO.
   *
   * @param width the width of the stencil buffer
   * @param height the height of the stenicl buffer
   */
  void attachStencil(int width, int height);
}
