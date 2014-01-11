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
package de.lessvoid.coregl;


import java.lang.reflect.Array;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * The CoreArrayVBO class represents a VBO bound to GL_ARRAY_BUFFER.
 * @author void
 */
public interface CoreVBO < T extends Buffer > {

  /**
   * Type of the VBO.
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

    public <T extends Buffer> T createBuffer(final int size) {
      ByteBuffer byteBuffer = createByteBuffer(calcByteLength(size));
      if (FLOAT.equals(this)) {
        return (T) byteBuffer.asFloatBuffer();
      } else if (SHORT.equals(this)) {
        return (T) byteBuffer.asShortBuffer();
      }
      throw new CoreGLException("Unsupported CoreVBO.DataType (" + this + ")");
    }

    public <T extends Buffer> T asBuffer(final ByteBuffer dataBuffer) {
      if (FLOAT.equals(this)) {
        return (T)  dataBuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
      } else if (SHORT.equals(this)) {
        return (T)  dataBuffer.order(ByteOrder.nativeOrder()).asShortBuffer();
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

    private static ByteBuffer createByteBuffer(final int size) {
      return ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
    }

    private static float[] toFloatArray(final Object[] data) {
      int arrlength = Array.getLength(data);
      float[] outputArray = new float[arrlength];
      for(int i = 0; i < arrlength; ++i){
        if (data[i] != null) {
          outputArray[i] = (Float) data[i];
        }
      }
      return outputArray;
    }

    private static short[] toShortArray(final Object[] data) {
      int arrlength = Array.getLength(data);
      short[] outputArray = new short[arrlength];
      for(int i = 0; i < arrlength; ++i){
        if (data[i] != null) {
          outputArray[i] = (Short) data[i];
        }
      }
      return outputArray;
    }
  }

  /**
   * Allows access to the internally kept nio Buffer that contains the original
   * buffer data. You can access and change this buffer if you want to update the
   * buffer content. Just make sure that you call rewind() before sending your new
   * data to the GPU with the send() method.
   *
   * @return the Buffer with the original buffer data (stored in main memory not GPU memory)
   */
  T getBuffer();

  /**
   * Maps the buffer object that this represents into client space and returns the buffer
   * @return the FloatBuffer to directly write data into (mapped into client space but is actual memory on the GPU)
   */
  T getMappedBuffer();

  /**
   * You'll need to call that when you're done writing data into a mapped buffer to return access back to the GPU.
   */
  void unmapBuffer();

  /**
   * bind the buffer object as GL_ARRAY_BUFFER
   */
  void bind();

  /**
   * Send the content of the Buffer to the GPU.
   */
  void send();

  /**
   * Delete all resources for this VBO.
   */
  void delete();
}
