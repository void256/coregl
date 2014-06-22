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
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STREAM_DRAW;
import static org.lwjgl.opengl.GL15.GL_WRITE_ONLY;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.glMapBuffer;
import static org.lwjgl.opengl.GL15.glUnmapBuffer;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Hashtable;
import java.util.Map;

import de.lessvoid.coregl.*;
import de.lessvoid.coregl.CoreVBO.DataType;
import de.lessvoid.coregl.CoreVBO.UsageType;

/**
 * The CoreArrayVBO class represents a VBO bound to GL_ARRAY_BUFFER.
 * @author void
 */
public class CoreVBOLwjgl < T extends Buffer > implements CoreVBO < T > {
  private static Map<UsageType, Integer> usageTypeMap = new Hashtable<UsageType, Integer>();
  static {
    initUsageTypeMap();
  }

  private final CoreCheckGL checkGL;
  private final int id;
  private final int usage;
  private final DataType dataType;
  private final int byteLength;

  private T vertexBuffer;
  private ByteBuffer mappedBufferCache;

  CoreVBOLwjgl(final CoreCheckGL checkGLParam, final DataType dataTypeParam, final UsageType usageType, final int size) {
    checkGL = checkGLParam;
    usage = usageTypeMap.get(usageType);
    dataType = dataTypeParam;
    byteLength = dataType.calcByteLength(size);

    vertexBuffer = dataType.createBuffer(size);
    vertexBuffer.rewind();

    id = initBuffer();
  }

  CoreVBOLwjgl(final CoreCheckGL checkGLParam, final DataType dataTypeParam, final UsageType usageType, final Object[] data) {
    this(checkGLParam, dataTypeParam, usageType, data.length);
    dataType.putArray(vertexBuffer, data);
    vertexBuffer.rewind();
    send();
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVBO#getBuffer()
   */
  @Override
  public T getBuffer() {
    return vertexBuffer;
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVBO#getMappedBuffer()
   */
  @Override
  public T getMappedBuffer() {
    ByteBuffer dataBuffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, byteLength, mappedBufferCache);
    checkGL.checkGLError("getMappedBuffer(GL_ARRAY_BUFFER)");

    mappedBufferCache = dataBuffer;
    return dataType.asBuffer(dataBuffer);
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
    glBindBuffer(GL_ARRAY_BUFFER, id);
    if (DataType.FLOAT.equals(dataType)) {
      glBufferData(GL_ARRAY_BUFFER, (FloatBuffer) vertexBuffer, usage);
    } else if (DataType.SHORT.equals(dataType)) {
      glBufferData(GL_ARRAY_BUFFER, ((ShortBuffer) vertexBuffer), usage);
    } else {
      throw new CoreGLException("Unsupported CoreVBO.DataType (" + dataType + ")");
    }
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
    return id;
  }

  private static void initUsageTypeMap() {
    usageTypeMap.put(UsageType.DYNAMIC_DRAW, GL_DYNAMIC_DRAW);
    usageTypeMap.put(UsageType.STATIC_DRAW, GL_STATIC_DRAW);
    usageTypeMap.put(UsageType.STREAM_DRAW, GL_STREAM_DRAW);
  }
}
