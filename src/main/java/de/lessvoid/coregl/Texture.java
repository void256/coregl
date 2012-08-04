package de.lessvoid.coregl;

import static org.lwjgl.opengl.GL11.glGenTextures;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.logging.Logger;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Texture {
  private Logger log = Logger.getLogger(Texture.class.getName());

  private final int textureId;
  private final int width;
  private final int height;
  private final int textureWidth;
  private final int textureHeight;

  public Texture(
      final boolean filterParam,
      final int width,
      final int height,
      final int textureWidth,
      final int textureHeight,
      final int depth,
      final ByteBuffer imageData) {
    imageData.rewind();
    this.textureId = createTextureID();
    this.width = width;
    this.height = height;
    this.textureWidth = textureWidth;
    this.textureHeight = textureHeight;
    createTexture(imageData, textureWidth, textureHeight, filterParam, depth == 32 ? GL11.GL_RGBA : GL11.GL_RGB);
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int getTextureWidth() {
    return textureWidth;
  }

  public int getTextureHeight() {
    return textureHeight;
  }

  public void dispose() {
    GL11.glDeleteTextures(textureId);
    CheckGL.checkGLError("dispose");
  }

  private void createTexture(final ByteBuffer textureBuffer, final int width, final int height, final boolean filter, final int srcPixelFormat) {
    int minFilter = GL11.GL_NEAREST;
    int magFilter = GL11.GL_NEAREST;
    if (filter) {
      minFilter = GL11.GL_LINEAR;
      magFilter = GL11.GL_LINEAR;
    }
    bind();

    IntBuffer temp = BufferUtils.createIntBuffer(16);
    GL11.glGetInteger(GL11.GL_MAX_TEXTURE_SIZE, temp);
    CheckGL.checkGLError("glGetInteger");

    int max = temp.get(0);
    if ((width > max) || (height > max)) {
      throw new RuntimeException("Attempt to allocate a texture to big for the current hardware");
    }
    if (width < 0) {
      log.warning("Attempt to allocate a texture with negative width");
      return;
    }
    if (height < 0) {
      log.warning("Attempt to allocate a texture with negative height");
      return;
    }

    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, minFilter); 
    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, magFilter); 
    CheckGL.checkGLError("glTexParameteri");

    if (minFilter == GL11.GL_LINEAR_MIPMAP_NEAREST) {
      GLU.gluBuild2DMipmaps(
          GL11.GL_TEXTURE_2D,
          1,
          width,
          height,
          srcPixelFormat, 
          GL11.GL_UNSIGNED_BYTE,
          textureBuffer);
    } else {
      GL11.glTexImage2D(
          GL11.GL_TEXTURE_2D, 
          0,
          GL11.GL_RGBA8, 
          width, 
          height, 
          0, 
          srcPixelFormat, 
          GL11.GL_UNSIGNED_BYTE, 
          textureBuffer);
    }
    CheckGL.checkGLError("glTexImage2D");
  }

  private int createTextureID() {
    IntBuffer textureId = BufferUtils.createIntBuffer(1);
    textureId.rewind();
    glGenTextures(textureId);
    CheckGL.checkGLError("glGenTextures");
    textureId.rewind();
    return textureId.get();
  }

  private IntBuffer createIntBuffer(final int size) {
    ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
    temp.order(ByteOrder.nativeOrder());
    return temp.asIntBuffer();
  }    

  public void bind() {
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
    CheckGL.checkGLError("glBindTexture");
  }
}
