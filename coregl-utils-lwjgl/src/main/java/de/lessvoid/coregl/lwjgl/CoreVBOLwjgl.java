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
package de.lessvoid.coregl.lwjgl;


import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_WRITE_ONLY;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.glMapBuffer;
import static org.lwjgl.opengl.GL15.glUnmapBuffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import de.lessvoid.coregl.CoreCheckGL;
import de.lessvoid.coregl.CoreVBO;

/**
 * The CoreArrayVBO class represents a VBO bound to GL_ARRAY_BUFFER.
 * @author void
 */
public class CoreVBOLwjgl implements CoreVBO {
  private static final CoreCheckGL checkGL = new CoreCheckGLLwjgl();

  private final int id;
  private final int usage;
  private final long byteLength;
  private FloatBuffer vertexBuffer;
  private ByteBuffer mappedBufferCache;

  CoreVBOLwjgl(final int usageType, final float[] data) {
    usage = usageType;
    byteLength = data.length << 2;

    vertexBuffer = BufferUtils.createFloatBuffer(data.length);
    vertexBuffer.put(data);
    vertexBuffer.rewind();

    id = glGenBuffers();
    checkGL.checkGLError("glGenBuffers");

    glBindBuffer(GL_ARRAY_BUFFER, id);
    glBufferData(GL_ARRAY_BUFFER, vertexBuffer, usage);
    checkGL.checkGLError("glBufferData");
  }

  CoreVBOLwjgl(final int usageType, final FloatBuffer data) {
    usage = usageType;
    byteLength = data.limit() << 2;

    vertexBuffer = BufferUtils.createFloatBuffer(data.limit());
    vertexBuffer.put(data);
    vertexBuffer.rewind();

    id = glGenBuffers();
    checkGL.checkGLError("glGenBuffers");

    glBindBuffer(GL_ARRAY_BUFFER, id);
    glBufferData(GL_ARRAY_BUFFER, vertexBuffer, usage);
    checkGL.checkGLError("glBufferData");
  }

  /**
   * Allows access to the internally kept nio FloatBuffer that contains the original
   * buffer data. You can access and change this buffer if you want to update the
   * buffer content. Just make sure that you call rewind() before sending your new
   * data to the GPU with the sendData() method.
   *
   * @return the FloatBuffer with the original buffer data (stored in main memory
   * not GPU memory)
   */
  public FloatBuffer getBuffer() {
    return vertexBuffer;
  }

  /**
   * Maps the buffer object that this represents into client space and returns the buffer as a FloatBuffer
   * @return the FloatBuffer to directly write data into (mapped into client space but is actual memory on the GPU)
   */
  public FloatBuffer getMappedBuffer() {
    ByteBuffer dataBuffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, byteLength, mappedBufferCache);
    checkGL.checkGLError("getMappedBuffer(GL_ARRAY_BUFFER)");

    mappedBufferCache = dataBuffer;
    return dataBuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
  }

  /**
   * You'll need to call that when you're done writing data into a mapped buffer to return access back to the GPU.
   */
  public void unmapBuffer() {
    glUnmapBuffer(GL_ARRAY_BUFFER);
  }

  /**
   * bind the buffer object as GL_ARRAY_BUFFER
   */
  public void bind() {
    glBindBuffer(GL_ARRAY_BUFFER, id);
    checkGL.checkGLError("glBindBuffer(GL_ARRAY_BUFFER)");
  }

  /**
   * Send the content of the FloatBuffer to the GPU.
   */
  public void send() {
    glBufferData(GL_ARRAY_BUFFER, vertexBuffer, usage);
    checkGL.checkGLError("glBufferData(GL_ARRAY_BUFFER)");
  }

  /**
   * Delete all resources for this VBO.
   */
  public void delete() {
    glDeleteBuffers(id);
  }
}
