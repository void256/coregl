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
package com.lessvoid.coregl;

import java.lang.reflect.Array;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Hashtable;
import java.util.Map;

import com.lessvoid.coregl.spi.CoreGL;

/**
 * The CoreArrayVBO class represents a VBO bound to GL_ARRAY_BUFFER.
 * 
 * @author void
 */
public class CoreVBO<T extends Buffer> {

  private static Map<UsageType, Integer> usageTypeMap = new Hashtable<UsageType, Integer>();

  private CoreGL gl;
  private final int id;
  private final int usage;
  private final DataType dataType;
  private final int byteLength;

  private T vertexBuffer;
  private ByteBuffer mappedBufferCache;

  /**
   * Type of the VBO.
   * 
   * @author void
   */
  public enum UsageType {
    DYNAMIC_DRAW,
    STATIC_DRAW,
    STREAM_DRAW
  }

  public enum DataType {
    FLOAT(2),
    SHORT(1);

    private final int byteSizeFactor;

    private DataType(final int byteSizeFactor) {
      this.byteSizeFactor = byteSizeFactor;
    }

    public int calcByteLength(final int size) {
      return size << byteSizeFactor;
    }

    public <T extends Buffer> T createBuffer(final CoreGL gl, final int size) {
      final ByteBuffer byteBuffer = gl.getUtil().createByteBuffer(calcByteLength(size));
      if (FLOAT.equals(this)) {
        return (T) byteBuffer.asFloatBuffer();
      } else if (SHORT.equals(this)) {
        return (T) byteBuffer.asIntBuffer();
      }
      throw new CoreGLException("Unsupported CoreVBO.DataType (" + this + ")");
    }

    public <T extends Buffer> T asBuffer(final ByteBuffer dataBuffer) {
      if (FLOAT.equals(this)) {
        return (T) dataBuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
      } else if (SHORT.equals(this)) {
        return (T) dataBuffer.order(ByteOrder.nativeOrder()).asIntBuffer();
      }
      throw new CoreGLException("Unsupported CoreVBO.DataType (" + this + ")");
    }

    public void putArray(final Buffer b, final Object[] data) {
      if (DataType.FLOAT.equals(this)) {
        ((FloatBuffer) b).put(toFloatArray(data));
      } else if (DataType.SHORT.equals(this)) {
        ((ShortBuffer) b).put(toShortArray(data));
      } else {
        throw new CoreGLException("Unsupported CoreVBO.DataType (" + this + ")");
      }
    }

    private static float[] toFloatArray(final Object[] data) {
      final int arrlength = Array.getLength(data);
      final float[] outputArray = new float[arrlength];
      for (int i = 0; i < arrlength; ++i) {
        if (data[i] != null) {
          outputArray[i] = (Float) data[i];
        }
      }
      return outputArray;
    }

    private static short[] toShortArray(final Object[] data) {
      final int arrlength = Array.getLength(data);
      final short[] outputArray = new short[arrlength];
      for (int i = 0; i < arrlength; ++i) {
        if (data[i] != null) {
          outputArray[i] = (Short) data[i];
        }
      }
      return outputArray;
    }
  }

  CoreVBO(final CoreGL gl, final DataType dataTypeParam, final UsageType usageType, final int size) {
    checkLazyInit(gl);
    this.gl = gl;
    usage = usageTypeMap.get(usageType);
    dataType = dataTypeParam;
    byteLength = dataType.calcByteLength(size);

    vertexBuffer = dataType.createBuffer(gl, size);
    vertexBuffer.rewind();

    id = initBuffer();
  }

  CoreVBO(final CoreGL gl, final DataType dataTypeParam, final UsageType usageType, final Object[] data) {
    this(gl, dataTypeParam, usageType, data.length);
    dataType.putArray(vertexBuffer, data);
    vertexBuffer.rewind();
    send();
  }

  /**
   * Create a new VBO with the given Type containing float data. This will
   * create the buffer object but does not bind or send the data to the GPU.
   * You'll need to call bind() to bind this VBO and you'll need to call send()
   * to transmit the buffer data to the GPU.
   *
   * @param dataType
   *          the CoreVBO.DataType of the NIO Buffer that the CoreVBO instance
   *          should contain
   * @param usageType
   *          the GL usage type for the buffer @see {@link UsageType}
   * @param size
   *          the size of the buffer
   * @return the CoreVBO instance
   */
  public static <T extends Buffer> CoreVBO<T> createCoreVBO(final CoreGL gl,
                                                            final CoreVBO.DataType dataType,
                                                            final CoreVBO.UsageType usageType,
                                                            final int size) {
    return new CoreVBO<T>(gl, dataType, usageType, size);
  }

  /**
   * Create a new VBO with the given Type containing float data. This will
   * create the buffer object but does not bind or send the data to the GPU.
   * You'll need to call bind() to bind this VBO and you'll need to call send()
   * to transmit the buffer data to the GPU.
   *
   * @param dataType
   *          the CoreVBO.DataType of the NIO Buffer that the CoreVBO instance
   *          should contain
   * @param usageType
   *          the GL usage type for the buffer @see {@link UsageType}
   * @param size
   *          the size of the buffer
   * @return the CoreVBO instance
   */
  public static <T extends Buffer> CoreVBO<T> createCoreVBO(final CoreGL gl,
                                                            final CoreVBO.DataType dataType,
                                                            final CoreVBO.UsageType usageType,
                                                            final Object[] data) {
    return new CoreVBO<T>(gl, dataType, usageType, data);
  }

  /**
   * Allows access to the internally kept nio Buffer that contains the original
   * buffer data. You can access and change this buffer if you want to update
   * the buffer content. Just make sure that you call rewind() before sending
   * your new data to the GPU with the send() method.
   *
   * @return the Buffer with the original buffer data (stored in main memory not
   *         GPU memory)
   */
  public T getBuffer() {
    return vertexBuffer;
  }

  /**
   * Maps the buffer object that this represents into client space and returns
   * the buffer
   * 
   * @return the FloatBuffer to directly write data into (mapped into client
   *         space but is actual memory on the GPU)
   */
  public T getMappedBuffer() {
    final ByteBuffer dataBuffer = gl.glMapBuffer(gl.GL_ARRAY_BUFFER(),
                                                 gl.GL_WRITE_ONLY(),
                                                 byteLength,
                                                 mappedBufferCache);
    gl.checkGLError("getMappedBuffer(GL_ARRAY_BUFFER)");
    mappedBufferCache = dataBuffer;
    return dataType.asBuffer(dataBuffer);
  }

  /**
   * You'll need to call that when you're done writing data into a mapped buffer
   * to return access back to the GPU.
   */
  public void unmapBuffer() {
    gl.glUnmapBuffer(gl.GL_ARRAY_BUFFER());
  }

  /**
   * bind the buffer object as GL_ARRAY_BUFFER
   */
  public void bind() {
    gl.glBindBuffer(gl.GL_ARRAY_BUFFER(), id);
    gl.checkGLError("glBindBuffer(GL_ARRAY_BUFFER)");
  }

  /**
   * Send the content of the Buffer to the GPU.
   */
  public void send() {
    gl.glBindBuffer(gl.GL_ARRAY_BUFFER(), id);
    if (DataType.FLOAT.equals(dataType)) {
      gl.glBufferData(gl.GL_ARRAY_BUFFER(), (FloatBuffer) vertexBuffer, usage);
    } else if (DataType.SHORT.equals(dataType)) {
      gl.glBufferData(gl.GL_ARRAY_BUFFER(), (IntBuffer) vertexBuffer, usage); // FIXME
                                                                              // illegal
                                                                              // cast
                                                                              // short
                                                                              // buffer
                                                                              // ->
                                                                              // int
                                                                              // buffer
    } else {
      throw new CoreGLException("Unsupported CoreVBO.DataType (" + dataType + ")");
    }
    gl.checkGLError("glBufferData(GL_ARRAY_BUFFER)");
  }

  /**
   * Delete all resources for this VBO.
   */
  public void delete() {
    final IntBuffer idbuff = gl.getUtil().createIntBuffer(1);
    idbuff.put(id);
    idbuff.flip();
    gl.glDeleteBuffers(1, idbuff);
  }

  private int initBuffer() {
    final IntBuffer idbuff = gl.getUtil().createIntBuffer(1);
    gl.glGenBuffers(1, idbuff);
    idbuff.rewind();
    final int id = idbuff.get();
    gl.checkGLError("glGenBuffers");
    return id;
  }

  static void initUsageTypeMap(final CoreGL gl) {
    usageTypeMap.put(UsageType.DYNAMIC_DRAW, gl.GL_DYNAMIC_DRAW());
    usageTypeMap.put(UsageType.STATIC_DRAW, gl.GL_STATIC_DRAW());
    usageTypeMap.put(UsageType.STREAM_DRAW, gl.GL_STREAM_DRAW());
  }

  private static void checkLazyInit(final CoreGL gl) {
    if (usageTypeMap.size() == 0) {
      initUsageTypeMap(gl);
    }
  }
}
