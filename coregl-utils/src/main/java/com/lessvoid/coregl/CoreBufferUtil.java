package com.lessvoid.coregl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * @author Brian Groenke
 */
public class CoreBufferUtil {

  public static ByteBuffer createByteBuffer(final byte[] data) {
    final ByteBuffer buffer = createByteBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  public static ByteBuffer createByteBuffer(final ByteBuffer data) {
    final ByteBuffer buffer = createByteBuffer(data.limit());
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  public static IntBuffer createIntBuffer(final int[] data) {
    final IntBuffer buffer = createIntBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  public static IntBuffer createIntBuffer(final IntBuffer data) {
    final IntBuffer buffer = createIntBuffer(data.limit());
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  public static ShortBuffer createShortBuffer(final short[] data) {
    final ShortBuffer buffer = createShortBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  public static ShortBuffer createShortBuffer(final ShortBuffer data) {
    final ShortBuffer buffer = createShortBuffer(data.limit());
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  public static FloatBuffer createFloatBuffer(final float[] data) {
    final FloatBuffer buffer = createFloatBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  public static FloatBuffer createFloatBuffer(final FloatBuffer data) {
    final FloatBuffer buffer = createFloatBuffer(data.limit());
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  public static DoubleBuffer createDoubleBuffer(final double[] data) {
    final DoubleBuffer buffer = createDoubleBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  public static DoubleBuffer createDoubleBuffer(final DoubleBuffer data) {
    final DoubleBuffer buffer = createDoubleBuffer(data.limit());
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  public static IntBuffer createIntBuffer(final int size) {
    return createByteBuffer(size << 2).asIntBuffer();
  }

  public static FloatBuffer createFloatBuffer(final int size) {
    return createByteBuffer(size << 2).asFloatBuffer();
  }

  public static ByteBuffer createByteBuffer(final int size) {
    return ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
  }

  public static ShortBuffer createShortBuffer(final int size) {
    return createByteBuffer(size << 1).asShortBuffer();
  }

  public static DoubleBuffer createDoubleBuffer(final int size) {
    return createByteBuffer(size << 3).asDoubleBuffer();
  }
}
