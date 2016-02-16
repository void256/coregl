package com.lessvoid.coregl.spi;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import com.lessvoid.coregl.CoreVersion;

/**
 * @author Brian Groenke
 */
public interface CoreUtil {

  int gluBuild2DMipmaps(int target, int internalFormat, int width, int height, int format, int type, ByteBuffer data);

  String gluErrorString(int glError);

  IntBuffer createIntBuffer(int[] data);

  IntBuffer createIntBuffer(IntBuffer data);

  ShortBuffer createShortBuffer(short[] data);

  ShortBuffer createShortBuffer(ShortBuffer data);

  FloatBuffer createFloatBuffer(float[] data);

  FloatBuffer createFloatBuffer(FloatBuffer data);

  DoubleBuffer createDoubleBuffer(double[] data);

  DoubleBuffer createDoubleBuffer(DoubleBuffer data);

  ByteBuffer createByteBuffer(byte[] data);

  ByteBuffer createByteBuffer(ByteBuffer data);

  IntBuffer createIntBuffer(int size);

  FloatBuffer createFloatBuffer(int size);

  DoubleBuffer createDoubleBuffer(int size);

  ShortBuffer createShortBuffer(int size);

  ByteBuffer createByteBuffer(int size);

  boolean isNPOTSupported();

  boolean isNPOTHardwareSupported();

  CoreVersion.GLVersion getGLVersion();

  CoreVersion.GLSLVersion getGLSLVersion();
}
