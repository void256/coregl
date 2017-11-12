/**
 * Copyright (c) 2016, Jens Hohmuth
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

import com.lessvoid.coregl.spi.CoreGL;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * The CoreBufferObject class represents a OpenGL buffer object.
 *
 * @author void
 */
public class CoreBuffer<T extends Buffer> {
  private final CoreGL gl;
  private final CoreBufferDataType bufferType;
  private final int id;
  private final int usage;
  private final int byteLength;
  private final IntBuffer intBuffer;

  private T vertexBuffer;
  private ByteBuffer mappedBufferCache;

  private CoreBuffer(
      final CoreGL gl,
      final CoreBufferDataType bufferType,
      final CoreBufferUsageType usageType,
      final int size) {
    this.gl = gl;
    this.intBuffer = CoreBufferUtil.createIntBuffer(1);
    this.usage = gl.mapCoreBufferUsageType(usageType);
    this.bufferType = bufferType;
    this.byteLength = bufferType.calcByteLength(size);
    this.vertexBuffer = bufferType.createBuffer(gl, size);
    this.id = createBufferName();
  }

  private CoreBuffer(
      final CoreGL gl,
      final CoreBufferTargetType target,
      final CoreBufferUsageType usageType,
      final byte[] data) {
    this(gl, CoreBufferDataType.BYTE, usageType, data.length);
    ((ByteBuffer) vertexBuffer).put(data);
    vertexBuffer.rewind();
    send(target);
  }

  private CoreBuffer(
      final CoreGL gl,
      final CoreBufferTargetType target,
      final CoreBufferUsageType usageType,
      final float[] data) {
    this(gl, CoreBufferDataType.FLOAT, usageType, data.length);
    ((FloatBuffer) vertexBuffer).put(data);
    vertexBuffer.rewind();
    send(target);
  }

  private CoreBuffer(
      final CoreGL gl,
      final CoreBufferTargetType target,
      final CoreBufferUsageType usageType,
      final short[] data) {
    this(gl, CoreBufferDataType.SHORT, usageType, data.length);
    ((ShortBuffer) vertexBuffer).put(data);
    vertexBuffer.rewind();
    send(target);
  }

  /**
   * Create a new buffer object with the given bufferType and reserve space
   * for size number of elements. The buffer object will be created but will
   * not be bound or any data will be send to the GPU. You'll need to call
   * bind() to bind this buffer object and you'll need to call send() to actually
   * transmit the buffer data to the GPU.
   *
   * @param gl
   *          the CoreGL instance to use
   * @param bufferType
   *          the type of data that the NIO buffer of this instance stores
   * @param usageType
   *          the GL usage type for the buffer
   * @param size
   *          the size of the buffer
   * @return the CoreBufferObject instance
   */
  public static <T extends Buffer> CoreBuffer<T> createCoreBufferObject(
      final CoreGL gl,
      final CoreBufferDataType bufferType,
      final CoreBufferUsageType usageType,
      final int size) {
    return new CoreBuffer<>(gl, bufferType, usageType, size);
  }

  /**
   * @see CoreBuffer#createCoreBufferObject(CoreGL, CoreBufferDataType, CoreBufferUsageType, int)
   *
   * This will create a byte based CoreBufferObject and initialize it with the byte[] given.
   */
  public static CoreBuffer<ByteBuffer> createCoreBufferObject(
      final CoreGL gl,
      final CoreBufferTargetType target,
      final CoreBufferUsageType usageType,
      final byte[] data) {
    return new CoreBuffer<>(gl, target, usageType, data);
  }

  /**
   * @see CoreBuffer#createCoreBufferObject(CoreGL, CoreBufferDataType, CoreBufferUsageType, int)
   *
   * This will create a float based CoreBufferObject and initialize it with the float[] given.
   */
  public static CoreBuffer<FloatBuffer> createCoreBufferObject(
      final CoreGL gl,
      final CoreBufferTargetType target,
      final CoreBufferUsageType usageType,
      final float[] data) {
    return new CoreBuffer<>(gl, target, usageType, data);
  }

  /**
   * @see CoreBuffer#createCoreBufferObject(CoreGL, CoreBufferDataType, CoreBufferUsageType, int)
   *
   * This will create a short based CoreBufferObject and initialize it with the short[] given.
   */
  public static CoreBuffer<ShortBuffer> createCoreBufferObject(
      final CoreGL gl,
      final CoreBufferTargetType target,
      final CoreBufferUsageType usageType,
      final short[] data) {
    return new CoreBuffer<>(gl, target, usageType, data);
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
   * Maps the buffer object that this instance represents into client space and returns
   * the buffer.
   *
   * @param target the buffer target to bind to
   * @param access the access policy to use
   * @return the NIO buffer to directly write data into (mapped into client
   *         space but is actual memory on the GPU)
   */
  public T getMappedBuffer(
      final CoreBufferTargetType target,
      final CoreBufferAccessType access) {
    final ByteBuffer dataBuffer = gl.glMapBuffer(
        gl.mapCoreBufferTargetType(target),
        gl.mapCoreBufferAccessType(access),
        byteLength,
        mappedBufferCache);
    gl.checkGLError("getMappedBuffer()");
    mappedBufferCache = dataBuffer;
    return bufferType.asBuffer(dataBuffer);
  }

  /**
   * You'll need to call this method when you're done writing data into a mapped buffer
   * to return access back to the GPU.
   */
  public void unmapBuffer(final CoreBufferTargetType target) {
    gl.glUnmapBuffer(gl.mapCoreBufferTargetType(target));
    gl.checkGLError("glBindBuffer()");
  }

  /**
   * Bind this buffer object to the target given.
   * @param target the target to bind to
   */
  public void bind(final CoreBufferTargetType target) {
    gl.glBindBuffer(gl.mapCoreBufferTargetType(target), id);
    gl.checkGLError("glBindBuffer()");
  }

  /**
   * Send the content of this buffer to the GPU for the target given.
   */
  public void send(final CoreBufferTargetType target) {
    int t = gl.mapCoreBufferTargetType(target);
    gl.glBindBuffer(t, id);

    // Didn't find a way to get rid of the if/else here using bufferType directly unfortunately :/
    if (CoreBufferDataType.BYTE.equals(bufferType)) {
      gl.glBufferData(t, (ByteBuffer) vertexBuffer, usage);
    } else if (CoreBufferDataType.FLOAT.equals(bufferType)) {
      gl.glBufferData(t, (FloatBuffer) vertexBuffer, usage);
    } else if (CoreBufferDataType.SHORT.equals(bufferType)) {
      gl.glBufferData(t, (ShortBuffer) vertexBuffer, usage);
    } else {
      throw new CoreGLException("Unsupported CoreBufferObjectBufferType (" + bufferType + ")");
    }
    gl.checkGLError("glBufferData()");
  }

  /**
   * Delete the GPU resources allocated for this instance.
   */
  public void delete() {
    intBuffer.clear();
    intBuffer.put(id);
    intBuffer.flip();
    gl.glDeleteBuffers(1, intBuffer);
    gl.checkGLError("glDeleteBuffers()");
  }

  private int createBufferName() {
    intBuffer.clear();
    gl.glGenBuffers(1, intBuffer);
    gl.checkGLError("glGenBuffers");
    intBuffer.rewind();
    return intBuffer.get();
  }
}
