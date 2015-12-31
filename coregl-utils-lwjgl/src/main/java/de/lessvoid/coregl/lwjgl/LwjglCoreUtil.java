package de.lessvoid.coregl.lwjgl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;

import de.lessvoid.coregl.CoreVersion;
import de.lessvoid.coregl.CoreVersion.GLSLVersion;
import de.lessvoid.coregl.CoreVersion.GLVersion;
import de.lessvoid.coregl.spi.CoreUtil;

/**
 * @author Brian Groenke
 */
class LwjglCoreUtil implements CoreUtil {

  @Override
  public int gluBuild2DMipmaps(final int target,
                               final int internalFormat,
                               final int width,
                               final int height,
                               final int format,
                               final int type,
                               final ByteBuffer data) {
    return GLU.gluBuild2DMipmaps(target, internalFormat, width, height, format, type, data);
  }

  @Override
  public ByteBuffer createByteBuffer(final byte[] data) {
    final ByteBuffer buffer = BufferUtils.createByteBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  @Override
  public ByteBuffer createByteBuffer(final ByteBuffer data) {
    final ByteBuffer buffer = BufferUtils.createByteBuffer(data.limit());
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  @Override
  public IntBuffer createIntBuffer(final int[] data) {
    final IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  @Override
  public IntBuffer createIntBuffer(final IntBuffer data) {
    final IntBuffer buffer = BufferUtils.createIntBuffer(data.limit());
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  @Override
  public ShortBuffer createShortBuffer(final short[] data) {
    final ShortBuffer buffer = BufferUtils.createShortBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  @Override
  public ShortBuffer createShortBuffer(final ShortBuffer data) {
    final ShortBuffer buffer = BufferUtils.createShortBuffer(data.limit());
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  @Override
  public FloatBuffer createFloatBuffer(final float[] data) {
    final FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  @Override
  public FloatBuffer createFloatBuffer(final FloatBuffer data) {
    final FloatBuffer buffer = BufferUtils.createFloatBuffer(data.limit());
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  @Override
  public DoubleBuffer createDoubleBuffer(final double[] data) {
    final DoubleBuffer buffer = BufferUtils.createDoubleBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  @Override
  public DoubleBuffer createDoubleBuffer(final DoubleBuffer data) {
    final DoubleBuffer buffer = BufferUtils.createDoubleBuffer(data.limit());
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  @Override
  public IntBuffer createIntBuffer(final int size) {
    return BufferUtils.createIntBuffer(size);
  }

  @Override
  public FloatBuffer createFloatBuffer(final int size) {
    return BufferUtils.createFloatBuffer(size);
  }

  @Override
  public ByteBuffer createByteBuffer(final int size) {
    return BufferUtils.createByteBuffer(size);
  }

  @Override
  public ShortBuffer createShortBuffer(final int size) {
    return BufferUtils.createShortBuffer(size);
  }

  @Override
  public DoubleBuffer createDoubleBuffer(final int size) {
    return BufferUtils.createDoubleBuffer(size);
  }

  @Override
  public boolean isNPOTSupported() {
    return getGLVersion().checkAgainst(GLVersion.GL20) || isNPOTHardwareSupported();
  }

  @Override
  public boolean isNPOTHardwareSupported() {
    return GLContext.getCapabilities().GL_ARB_texture_non_power_of_two;
  }

  @Override
  public GLVersion getGLVersion() {
    final String glVersionString = GL11.glGetString(GL11.GL_VERSION);
    return CoreVersion.getGLVersionFromString(glVersionString);
  }

  @Override
  public GLSLVersion getGLSLVersion() {
    final String glslVersionString = GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION);
    return CoreVersion.getGLSLVersionFromString(glslVersionString);
  }
}
