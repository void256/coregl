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

public class CoreTextureBuffer {
  private int tbo;
  private int texture;


  public CoreTextureBuffer(final byte[] data) {
    // GL_R8Ibyte 1 NO R 0 0 1
    // GL_RG8Ibyte 2 NO R G 0 1
    // GL_RGBA8Ibyte 4 NO R G B A

    // GL_R8UI ubyte 1 NO R 0 0 1
    // GL_RG8UIubyte 2 NO R G 0 1
    // GL_RGBA8UIubyte 4 NO R G B A
    // GL_R8 ubyte 1 YES R 0 0 1
    // GL_RG8ubyte 2 YES R G 0 1

  }

  public CoreTextureBuffer(final short[] data) {
    // GL_R16I short 1 NO R 0 0 1
    // GL_RG16I short 2 NO R G 0 1
    // GL_RGBA16I short 4 NO R G B A
    // GL_R16UI ushort 1 NO R 0 0 1
    // GL_RG16UI ushort 2 NO R G 0 1
    // GL_RGBA16UI ushort 4 NO R G B A
    // GL_R16 ushort 1 YES R 0 0 1
    // GL_RG16 ushort 2 YES R G 0 1
    // GL_RGBA16 short 4 YES R G B A

  }

  public CoreTextureBuffer(final int[] data) {
    // GL_R32I int 1 NO R 0 0 1
    // GL_RG32I int 2 NO R G 0 1
    // GL_RGB32I int 3 NO R G B 1
    // GL_RGBA32I int 4 NO R G B A

    // GL_R32UI uint 1 NO R 0 0 1
    // GL_RG32UI uint 2 NO R G 0 1
    // GL_RGB32UI uint 3 NO R G B 1
    // GL_RGBA32UI uint 4 NO R G B A
    // GL_RGBA8 uint 4 YES R G B A

  }

  public CoreTextureBuffer(final float[] data) {
    // GL_R16F half 1 NO R 0 0 1
    // GL_RG16F half 2 NO R G 0 1
    // GL_RGBA16F half 4 NO R G B A
    // GL_R32F float 1 NO R 0 0 1
    // GL_RG32F float 2 NO R G 0 1
    // GL_RGB32Ffloat 3 NO R G B 1
    // GL_RGBA32Ffloat 4 NO R G B A

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
