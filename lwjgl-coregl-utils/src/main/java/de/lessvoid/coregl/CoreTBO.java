package de.lessvoid.coregl;

import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL30.GL_R32F;
import static org.lwjgl.opengl.GL31.GL_TEXTURE_BUFFER;
import static org.lwjgl.opengl.GL31.glTexBuffer;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class CoreTBO {
  private int tbo;
  private int texture;

  public CoreTBO(final float[] data) {
    init(data);
  }

  public void bind() {
    glBindTexture(GL_TEXTURE_BUFFER, texture);
  }

  public void dispose() {
    glBindBuffer(GL_TEXTURE_BUFFER, tbo);
    CoreCheckGL.checkGLError("glBindBuffer");

    glDeleteBuffers(tbo);
    CoreCheckGL.checkGLError("glDeleteBuffers");

    glDeleteTextures(texture);
    CoreCheckGL.checkGLError("glDeleteTextures");
  }

  private void init(final float[] data) {
    tbo = glGenBuffers();
    CoreCheckGL.checkGLError("glGenBuffers");

    glBindBuffer(GL_TEXTURE_BUFFER, tbo);
    CoreCheckGL.checkGLError("glBindBuffer");

    FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    glBufferData(GL_TEXTURE_BUFFER, buffer, GL_STATIC_DRAW); 

    texture = glGenTextures();
    CoreCheckGL.checkGLError("glGenTextures");

    glBindTexture(GL_TEXTURE_BUFFER, texture);
    CoreCheckGL.checkGLError("glBindTexture");

    glTexBuffer(GL_TEXTURE_BUFFER, GL_R32F, tbo);
    CoreCheckGL.checkGLError("glTexBuffer");
  }
}
