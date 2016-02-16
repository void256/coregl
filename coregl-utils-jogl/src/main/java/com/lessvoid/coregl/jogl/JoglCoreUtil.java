package com.lessvoid.coregl.jogl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import com.jogamp.common.nio.Buffers;
import com.jogamp.common.util.VersionNumber;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLExtensions;
import com.jogamp.opengl.glu.GLU;

import com.lessvoid.coregl.spi.CoreUtil;
import com.lessvoid.coregl.CoreVersion;
import com.lessvoid.coregl.CoreVersion.GLSLVersion;
import com.lessvoid.coregl.CoreVersion.GLVersion;

/**
 * @author Brian Groenke
 */
class JoglCoreUtil implements CoreUtil {

  @Override
  public int gluBuild2DMipmaps(final int target,
                               final int internalFormat,
                               final int width,
                               final int height,
                               final int format,
                               final int type,
                               final ByteBuffer data) {
    return GLU.createGLU().gluBuild2DMipmaps(target, internalFormat, width, height, format, type, data);
  }

  @Override
  public String gluErrorString (final int glError) {
    return GLU.createGLU().gluErrorString(glError);
  }

  @Override
  public ByteBuffer createByteBuffer(final byte[] data) {
    return Buffers.newDirectByteBuffer(data);
  }

  @Override
  public IntBuffer createIntBuffer(final int[] data) {
    return Buffers.newDirectIntBuffer(data);
  }

  @Override
  public ShortBuffer createShortBuffer(final short[] data) {
    return Buffers.newDirectShortBuffer(data);
  }

  @Override
  public FloatBuffer createFloatBuffer(final float[] data) {
    return Buffers.newDirectFloatBuffer(data);
  }

  @Override
  public DoubleBuffer createDoubleBuffer(final double[] data) {
    return Buffers.newDirectDoubleBuffer(data);
  }

  @Override
  public ByteBuffer createByteBuffer(final ByteBuffer data) {
    final int npos = data.position();
    final byte[] arr = new byte[data.remaining()];
    data.get(arr);
    data.position(npos);
    return Buffers.newDirectByteBuffer(arr);
  }

  @Override
  public IntBuffer createIntBuffer(final IntBuffer data) {
    final int npos = data.position();
    final int[] arr = new int[data.remaining()];
    data.get(arr);
    data.position(npos);
    return Buffers.newDirectIntBuffer(arr);
  }

  @Override
  public ShortBuffer createShortBuffer(final ShortBuffer data) {
    final int npos = data.position();
    final short[] arr = new short[data.remaining()];
    data.get(arr);
    data.position(npos);
    return Buffers.newDirectShortBuffer(arr);
  }

  @Override
  public FloatBuffer createFloatBuffer(final FloatBuffer data) {
    final int npos = data.position();
    final float[] arr = new float[data.remaining()];
    data.get(arr);
    data.position(npos);
    return Buffers.newDirectFloatBuffer(arr);
  }

  @Override
  public DoubleBuffer createDoubleBuffer(final DoubleBuffer data) {
    final int npos = data.position();
    final double[] arr = new double[data.remaining()];
    data.get(arr);
    data.position(npos);
    return Buffers.newDirectDoubleBuffer(arr);
  }

  @Override
  public IntBuffer createIntBuffer(final int size) {
    return Buffers.newDirectIntBuffer(size);
  }

  @Override
  public FloatBuffer createFloatBuffer(final int size) {
    return Buffers.newDirectFloatBuffer(size);
  }

  @Override
  public ByteBuffer createByteBuffer(final int size) {
    return Buffers.newDirectByteBuffer(size);
  }

  @Override
  public DoubleBuffer createDoubleBuffer(final int size) {
    return Buffers.newDirectDoubleBuffer(size);
  }

  @Override
  public ShortBuffer createShortBuffer(final int size) {
    return Buffers.newDirectShortBuffer(size);
  }

  // JOGL annoyingly doesn't have a built in function for singular glGet(s)

  public static int glGetInteger(final GL gl, final int pname) {
    final int[] store = new int[1];
    gl.glGetIntegerv(pname, store, 0);
    return store[0];
  }

  public static float glGetFloat(final GL gl, final int pname) {
    final float[] store = new float[1];
    gl.glGetFloatv(pname, store, 0);
    return store[0];
  }

  @Override
  public boolean isNPOTSupported() {
    return getGLVersion().checkAgainst(GLVersion.GL20) || isNPOTHardwareSupported();
  }

  @Override
  public boolean isNPOTHardwareSupported() {
    return GLContext.getCurrentGL().isExtensionAvailable(GLExtensions.ARB_texture_non_power_of_two);
  }

  @Override
  public GLVersion getGLVersion() {
    final VersionNumber glVersion = GLContext.getCurrent().getGLVersionNumber();
    return CoreVersion.getGLVersion(glVersion.getMajor(), glVersion.getMinor());
  }

  @Override
  public GLSLVersion getGLSLVersion() {
    final VersionNumber glslVersion = GLContext.getCurrent().getGLSLVersionNumber();
    return CoreVersion.getGLSLVersion(glslVersion.getMajor() * 100 + glslVersion.getMinor());
  }
}
