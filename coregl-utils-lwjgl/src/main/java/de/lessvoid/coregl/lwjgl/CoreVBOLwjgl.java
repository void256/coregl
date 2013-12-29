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
import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;

import de.lessvoid.coregl.CoreCheckGL;
import de.lessvoid.coregl.CoreVBO;

/**
 * The CoreArrayVBO class represents a VBO bound to GL_ARRAY_BUFFER.
 * @author void
 */
public class CoreVBOLwjgl implements CoreVBO {
  private final CoreCheckGL checkGL;

  private final int id;
  private final int usage;
  private final int byteLength;
  private ByteBuffer vertexBuffer;
  private ByteBuffer mappedBufferCache;

  CoreVBOLwjgl(final CoreCheckGL checkGLParam, final int usageType, final short[] data) {
    checkGL = checkGLParam;
    usage = usageType;
    byteLength = data.length << 1;

    vertexBuffer = BufferUtils.createByteBuffer(byteLength);
    vertexBuffer.asShortBuffer().put(data);
    vertexBuffer.rewind();

    id = initBuffer();
  }

  CoreVBOLwjgl(final CoreCheckGL checkGLParam, final int usageType, final ShortBuffer data) {
    checkGL = checkGLParam;
    usage = usageType;
    byteLength = data.limit() << 1;

    vertexBuffer = BufferUtils.createByteBuffer(byteLength);
    vertexBuffer.asShortBuffer().put(data);
    vertexBuffer.rewind();

    id = initBuffer();
  }

  CoreVBOLwjgl(final CoreCheckGL checkGLParam, final int usageType, final float[] data) {
    checkGL = checkGLParam;
    usage = usageType;
    byteLength = data.length << 2;

    vertexBuffer = BufferUtils.createByteBuffer(byteLength);
    vertexBuffer.asFloatBuffer().put(data);
    vertexBuffer.rewind();

    id = initBuffer();
  }

  CoreVBOLwjgl(final CoreCheckGL checkGLParam, final int usageType, final FloatBuffer data) {
    checkGL = checkGLParam;
    usage = usageType;
    byteLength = data.limit() << 2;

    vertexBuffer = BufferUtils.createByteBuffer(data.limit() * 4);
    vertexBuffer.asFloatBuffer().put(data);
    vertexBuffer.rewind();

    id = initBuffer();
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVBO#getFloatBuffer()
   */
  @Override
  public FloatBuffer getFloatBuffer() {
    return vertexBuffer.asFloatBuffer();
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVBO#getFloatBufferMapped()
   */
  @Override
  public FloatBuffer getFloatBufferMapped() {
    ByteBuffer dataBuffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, byteLength, mappedBufferCache);
    checkGL.checkGLError("getMappedBuffer(GL_ARRAY_BUFFER)");

    mappedBufferCache = dataBuffer;
    return dataBuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVBO#getFloatBuffer()
   */
  @Override
  public ShortBuffer getShortBuffer() {
    return vertexBuffer.asShortBuffer();
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVBO#getFloatBufferMapped()
   */
  @Override
  public ShortBuffer getShortBufferMapped() {
    ByteBuffer dataBuffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, byteLength, mappedBufferCache);
    checkGL.checkGLError("getMappedBuffer(GL_ARRAY_BUFFER)");

    mappedBufferCache = dataBuffer;
    return dataBuffer.order(ByteOrder.nativeOrder()).asShortBuffer();
  }

  /**
   * You'll need to call that when you're done writing data into a mapped buffer to return access back to the GPU.
   */
  @Override
  public void unmapBuffer() {
    glUnmapBuffer(GL_ARRAY_BUFFER);
  }

  /**
   * bind the buffer object as GL_ARRAY_BUFFER
   */
  @Override
  public void bind() {
    glBindBuffer(GL_ARRAY_BUFFER, id);
    checkGL.checkGLError("glBindBuffer(GL_ARRAY_BUFFER)");
  }

  /**
   * Send the content of the FloatBuffer to the GPU.
   */
  @Override
  public void send() {
    glBufferData(GL_ARRAY_BUFFER, vertexBuffer, usage);
    checkGL.checkGLError("glBufferData(GL_ARRAY_BUFFER)");
  }

  /**
   * Delete all resources for this VBO.
   */
  @Override
  public void delete() {
    glDeleteBuffers(id);
  }

  private int initBuffer() {
    int id = glGenBuffers();
    checkGL.checkGLError("glGenBuffers");

    glBindBuffer(GL_ARRAY_BUFFER, id);
    glBufferData(GL_ARRAY_BUFFER, vertexBuffer, usage);
    checkGL.checkGLError("glBufferData");
    return id;
  }
}
