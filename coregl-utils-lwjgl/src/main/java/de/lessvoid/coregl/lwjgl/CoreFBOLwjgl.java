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

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glDrawBuffer;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_UNDEFINED;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_UNSUPPORTED;
import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL30.GL_STENCIL_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_STENCIL_INDEX8;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glBindRenderbuffer;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;
import static org.lwjgl.opengl.GL30.glFramebufferRenderbuffer;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL30.glFramebufferTextureLayer;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.opengl.GL30.glGenRenderbuffers;
import static org.lwjgl.opengl.GL30.glRenderbufferStorage;

import org.lwjgl.opengl.Display;

import de.lessvoid.coregl.CoreCheckGL;
import de.lessvoid.coregl.CoreFBO;
import de.lessvoid.coregl.CoreGLException;

/*
 * @see CoreFBO
 */
public class CoreFBOLwjgl implements CoreFBO {
  private static final CoreCheckGL checkGL = new CoreCheckGLLwjgl();
  private int fbo;

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreFBO#bindFramebuffer()
   */
  @Override
  public void bindFramebuffer() {
    glBindFramebuffer(GL_FRAMEBUFFER, fbo);
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreFBO#disable()
   */
  @Override
  public void disable() {
    glBindFramebuffer(GL_FRAMEBUFFER, 0);
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreFBO#disableAndResetViewport()
   */
  @Override
  public void disableAndResetViewport() {
    glBindFramebuffer(GL_FRAMEBUFFER, 0);
    glViewport(0, 0, Display.getWidth(), Display.getHeight());
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreFBO#delete()
   */
  @Override
  public void delete() {
    glDeleteFramebuffers(fbo);
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreFBO#attachTexture(int, int)
   */
  @Override
  public void attachTexture(final int textureId, final int colorAttachmentIdx) {
    glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + colorAttachmentIdx, GL_TEXTURE_2D, textureId, 0);
    checkGL.checkGLError("glFramebufferTexture2D");

    glDrawBuffer(GL_COLOR_ATTACHMENT0 + colorAttachmentIdx);
    checkGL.checkGLError("glDrawBuffer");

    checkFramebufferStatus();
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreFBO#attachTexture(int, int, int)
   */
  @Override
  public void attachTexture(final int textureId, final int colorAttachmentIdx, final int layer) {
    glFramebufferTextureLayer(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + colorAttachmentIdx, textureId, 0, layer);
    checkGL.checkGLError("glFramebufferTextureLayer");

    glDrawBuffer(GL_COLOR_ATTACHMENT0 + colorAttachmentIdx);
    checkGL.checkGLError("glDrawBuffer");

    checkFramebufferStatus();
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreFBO#attachStencil(int, int)
   */
  @Override
  public void attachStencil(final int width, final int height) {
    int renderBuffer = glGenRenderbuffers();
    glBindRenderbuffer(GL_RENDERBUFFER, renderBuffer);
    glRenderbufferStorage(GL_RENDERBUFFER, GL_STENCIL_INDEX8, width, height);
    glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_STENCIL_ATTACHMENT, GL_RENDERBUFFER, renderBuffer);
    checkFramebufferStatus();
  }

  CoreFBOLwjgl() {
    initialize();
  }

  private void initialize() {
    fbo = glGenFramebuffers();
    checkGL.checkGLError("glGenFramebuffers");
  }

  private void checkFramebufferStatus() {
    int fboStatus = glCheckFramebufferStatus(GL_FRAMEBUFFER);
    if (fboStatus != GL_FRAMEBUFFER_COMPLETE) {
      throw new CoreGLException(translateErrorState(fboStatus));
    }
  }

  private String translateErrorState(final int fboStatus) {
    switch (fboStatus) {
      case GL_FRAMEBUFFER_UNDEFINED:
        return "GL_FRAMEBUFFER_UNDEFINED is returned if target is the default framebuffer, but the default framebuffer does not exist";
      case GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT:
        return "GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT is returned if any of the framebuffer attachment points are framebuffer incomplete.";
      case GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT:
        return "GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT is returned if the framebuffer does not have at least one image attached to it.";
      case GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER:
        return "GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER is returned if the value of GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE is GL_NONE for any color attachment point(s) named by GL_DRAWBUFFERi.";
      case GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER:
        return "GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER is returned if GL_READ_BUFFER is not GL_NONE and the value of GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE is GL_NONE for the color attachment point named by GL_READ_BUFFER.";
      case GL_FRAMEBUFFER_UNSUPPORTED:
        return "GL_FRAMEBUFFER_UNSUPPORTED is returned if the combination of internal formats of the attached images violates an implementation-dependent set of restrictions.";
      case GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE:
        return "GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE is returned if the value of GL_RENDERBUFFER_SAMPLES is not the same for all attached renderbuffers; if the value of GL_TEXTURE_SAMPLES is the not same for all attached textures; or, if the attached images are a mix of renderbuffers and textures, the value of GL_RENDERBUFFER_SAMPLES does not match the value of GL_TEXTURE_SAMPLES. + GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE is also returned if the value of GL_TEXTURE_FIXED_SAMPLE_LOCATIONS is not the same for all attached textures; or, if the attached images are a mix of renderbuffers and textures, the value of GL_TEXTURE_FIXED_SAMPLE_LOCATIONS is not GL_TRUE for all attached textures.";
//      case GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS:
//        return "GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS is returned if any framebuffer attachment is layered, and any populated attachment is not layered, or if all populated color attachments are not from textures of the same target.";
      default:
        return "Unknown Framebuffer Status: [" + fboStatus + "]";
    }
  }
}
