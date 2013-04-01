package de.lessvoid.coregl;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glDrawBuffer;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_UNDEFINED;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_UNSUPPORTED;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;

import org.lwjgl.opengl.Display;

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
  private int fbo;

  /**
   * Generate a new FBO.
   */
  public CoreFBO() {
    initialize();
  }

  /**
   * Enable this FBO (glBindFramebuffer)
   */
  public void bindFramebuffer() {
    glBindFramebuffer(GL_DRAW_FRAMEBUFFER, fbo);
  }

  /**
   * Enable this FBO and use the dimensions provided to setup the glViewport().
   *
   * @param width the width of the viewport to use
   * @param height the height of the viewport to use
   */
  public void bindFramebuffer(final int width, final int height) {
    bindFramebuffer();
    glViewport(0, 0, width, height);
  }

  /**
   * Disable rendering to this FBO.
   */
  public void disable() {
    glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
  }

  /**
   * Disable rendering to this FBO and reset the viewport to the Display.getDisplayMode() dimensions.
   */
  public void disableAndResetViewport() {
    disable();
    glViewport(0, 0, Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight());
  }

  /**
   * Delete the FBO.
   */
  public void delete() {
    glDeleteFramebuffers(fbo);
  }

  /**
   * Attach a texture object to this FBO.
   *
   * @param textureId the texture to attach
   * @param colorAttachmentIdx the color attachment to use (0=GL_COLOR_ATTACHMENT0, 1=GL_COLOR_ATTACHMENT1 and so on)
   */
  public void attachTexture(final int textureId, final int colorAttachmentIdx) {
    glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + colorAttachmentIdx, GL_TEXTURE_2D, textureId, 0);
    CoreCheckGL.checkGLError("glFramebufferTexture2D");

    glDrawBuffer(GL_COLOR_ATTACHMENT0 + colorAttachmentIdx);
    CoreCheckGL.checkGLError("glDrawBuffer");

    checkFramebufferStatus();
  }

  /**
   * Attach a texture object from a texture array to this FBO.
   *
   * @param textureId the texture to attach
   * @param colorAttachmentIdx the color attachment to use (0=GL_COLOR_ATTACHMENT0, 1=GL_COLOR_ATTACHMENT1 and so on)
   * @param layer the texture index inside the texture array to attach
   */
  public void attachTexture(final int textureId, final int colorAttachmentIdx, final int layer) {
    glFramebufferTextureLayer(GL_DRAW_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + colorAttachmentIdx, textureId, 0, layer);
    CoreCheckGL.checkGLError("glFramebufferTextureLayer");

    glDrawBuffer(GL_COLOR_ATTACHMENT0 + colorAttachmentIdx);
    CoreCheckGL.checkGLError("glDrawBuffer");

    checkFramebufferStatus();
  }

  private void initialize() {
    fbo = glGenFramebuffers();
    CoreCheckGL.checkGLError("glGenFramebuffers");
  }

  private void checkFramebufferStatus() {
    int fboStatus = glCheckFramebufferStatus(GL_DRAW_FRAMEBUFFER);
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
