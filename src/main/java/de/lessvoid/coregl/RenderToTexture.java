package de.lessvoid.coregl;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glDrawBuffer;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.Display;

public class RenderToTexture {
  private int fbo;
  private int texture;
  private int width;
  private int height;

  public RenderToTexture(final int width, final int height) {
    this.width = width;
    this.height = height;
    initialize();
  }
  
  private void initialize() {
    fbo = glGenFramebuffers();
    CheckGL.checkGLError("glGenFramebuffers");

    glBindFramebuffer(GL_DRAW_FRAMEBUFFER, fbo);
    CheckGL.checkGLError("glBindFramebuffer");

    texture = glGenTextures();
    CheckGL.checkGLError("glGenTextures");

    glBindTexture(GL_TEXTURE_2D, texture);
    CheckGL.checkGLError("glBindTexture");

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST); 
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
    CheckGL.checkGLError("glTexImage2D");

    glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture, 0);
    CheckGL.checkGLError("glFramebufferTexture");

    glDrawBuffer(GL_COLOR_ATTACHMENT0);
    CheckGL.checkGLError("glDrawBuffer");

    glBindTexture(GL_TEXTURE_2D, 0);
    checkFramebufferStatus();

    off();
  }

  private void checkFramebufferStatus() {
    int fboStatus = glCheckFramebufferStatus(GL_DRAW_FRAMEBUFFER);
    if (fboStatus != GL_FRAMEBUFFER_COMPLETE) {
      System.out.println("FBO STATUS CHECK FAILED WITH: " + fboStatus);
    }
  }

  public void on() {
    glBindFramebuffer(GL_DRAW_FRAMEBUFFER, fbo);
    glViewport(0, 0, width, height);
  }

  public void off() {
    glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
    glViewport(0, 0, Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight());
  }

  public void bindTexture() {
    glBindTexture(GL_TEXTURE_2D, texture);
  }

  public void destroy() {
    glDeleteTextures(texture);
    glDeleteFramebuffers(fbo);
  }
}
