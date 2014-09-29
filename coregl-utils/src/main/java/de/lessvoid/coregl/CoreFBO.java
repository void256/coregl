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

import java.nio.IntBuffer;

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
public class CoreFBO {

	private final CoreGL gl;

	private int fbo;

	CoreFBO(final CoreGL gl) {
		this.gl = gl;
		initialize();
	}
	/**
	 * Enable this FBO (glBindFramebuffer)
	 */
	public void bindFramebuffer() {
		gl.glBindFramebuffer(gl.GL_FRAMEBUFFER(), fbo);
	}

	/**
	 * Disable rendering to this FBO.
	 */
	public void disable() {
		gl.glBindFramebuffer(gl.GL_FRAMEBUFFER(), 0);
	}

	/**
	 * Disable rendering to this FBO and reset the Viewport back to screen size.
	 */
	public void disableAndResetViewport() {
		gl.glBindFramebuffer(gl.GL_FRAMEBUFFER(), 0);
		gl.glViewport(0, 0, gl.getDisplayWidth(), gl.getDisplayHeight());
	}

	/**
	 * Delete the FBO.
	 */
	public void delete() {
		gl.glDeleteFramebuffers(1, gl.getUtil().createIntBuffer(new int[] {fbo}));
	}

	/**
	 * Attach a texture object to this FBO.
	 *
	 * @param textureId the texture to attach
	 * @param colorAttachmentIdx the color attachment to use (0=GL_COLOR_ATTACHMENT0, 1=GL_COLOR_ATTACHMENT1 and so on)
	 */
	public void attachTexture(int textureId, int colorAttachmentIdx) {
		gl.glFramebufferTexture2D(gl.GL_FRAMEBUFFER(), gl.GL_COLOR_ATTACHMENT0() + colorAttachmentIdx, gl.GL_TEXTURE_2D(), textureId, 0);
		gl.checkGLError("glFramebufferTexture2D");

		gl.glDrawBuffer(gl.GL_COLOR_ATTACHMENT0() + colorAttachmentIdx);
		gl.checkGLError("glDrawBuffer");

		checkFramebufferStatus();
	}

	/**
	 * Attach a texture object from a texture array to this FBO.
	 *
	 * @param textureId the texture to attach
	 * @param colorAttachmentIdx the color attachment to use (0=GL_COLOR_ATTACHMENT0, 1=GL_COLOR_ATTACHMENT1 and so on)
	 * @param layer the texture index inside the texture array to attach
	 */
	public void attachTexture(int textureId, int colorAttachmentIdx, int layer) {
		gl.glFramebufferTextureLayer(gl.GL_FRAMEBUFFER(), gl.GL_COLOR_ATTACHMENT0() + colorAttachmentIdx, textureId, 0, layer);
		gl.checkGLError("glFramebufferTextureLayer");

		gl.glDrawBuffer(gl.GL_COLOR_ATTACHMENT0() + colorAttachmentIdx);
		gl.checkGLError("glDrawBuffer");

		checkFramebufferStatus();
	}

	/**
	 * Attach a stencil buffer to this FBO.
	 *
	 * @param width the width of the stencil buffer
	 * @param height the height of the stenicl buffer
	 */
	public void attachStencil(int width, int height) {
		IntBuffer buff = gl.getUtil().createIntBuffer(1);
		gl.glGenRenderBuffers(1, buff);
	    int renderBuffer = buff.get(0);
	    gl.glBindRenderbuffer(gl.GL_RENDERBUFFER(), renderBuffer);
	    gl.glRenderbufferStorage(gl.GL_RENDERBUFFER(), gl.GL_STENCIL_INDEX8(), width, height);
	    gl.glFramebufferRenderbuffer(gl.GL_FRAMEBUFFER(), gl.GL_STENCIL_ATTACHMENT(), gl.GL_RENDERBUFFER(), renderBuffer);
	    checkFramebufferStatus();
	}

	private void initialize() {
		IntBuffer buffStore = gl.getUtil().createIntBuffer(1);
		gl.glGenFramebuffers(1, buffStore);
		fbo = buffStore.get(0);
		gl.checkGLError("glGenFramebuffers");
	}

	private void checkFramebufferStatus() {
		int fboStatus = gl.glCheckFramebufferStatus(gl.GL_FRAMEBUFFER());
		if (fboStatus != gl.GL_FRAMEBUFFER_COMPLETE()) {
			throw new CoreGLException(translateErrorState(fboStatus));
		}
	}

	private String translateErrorState(final int fboStatus) {
		if (fboStatus == gl.GL_FRAMEBUFFER_UNDEFINED()) {
			return "GL_FRAMEBUFFER_UNDEFINED is returned if target is the default framebuffer, but the default framebuffer does not exist";
		} else if (fboStatus == gl.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT()) {
			return "GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT is returned if any of the framebuffer attachment points are framebuffer incomplete.";
		} else if (fboStatus == gl.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT()) {
			return "GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT is returned if the framebuffer does not have at least one image attached to it.";
		} else if (fboStatus == gl.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER()) {
			return "GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER is returned if the value of GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE is GL_NONE for any color attachment point(s) named by GL_DRAWBUFFERi.";
		} else if (fboStatus == gl.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER()) {
			return "GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER is returned if GL_READ_BUFFER is not GL_NONE and the value of GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE is GL_NONE for the color attachment point named by GL_READ_BUFFER.";
		} else if (fboStatus == gl.GL_FRAMEBUFFER_UNSUPPORTED()) {
			return "GL_FRAMEBUFFER_UNSUPPORTED is returned if the combination of internal formats of the attached images violates an implementation-dependent set of restrictions.";
		} else if (fboStatus == gl.GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE()) {
			return "GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE is returned if the value of GL_RENDERBUFFER_SAMPLES is not the same for all attached renderbuffers; if the value of GL_TEXTURE_SAMPLES is the not same for all attached textures; or, if the attached images are a mix of renderbuffers and textures, the value of GL_RENDERBUFFER_SAMPLES does not match the value of GL_TEXTURE_SAMPLES. + GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE is also returned if the value of GL_TEXTURE_FIXED_SAMPLE_LOCATIONS is not the same for all attached textures; or, if the attached images are a mix of renderbuffers and textures, the value of GL_TEXTURE_FIXED_SAMPLE_LOCATIONS is not GL_TRUE for all attached textures.";
			//		      case GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS:
			//		        return "GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS is returned if any framebuffer attachment is layered, and any populated attachment is not layered, or if all populated color attachments are not from textures of the same target.";
		} else {
			return "Unknown Framebuffer Status: [" + fboStatus + "]";
		}
	}
}
