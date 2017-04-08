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

import com.lessvoid.coregl.spi.CoreGL;

import java.nio.IntBuffer;

/**
 * The CoreElementVBO class represents a VBO bound to GL_ELEMENT_BUFFER.
 * 
 * @author void
 */
public class CoreElementVBO {

  private final CoreGL gl;
  private final int id;
  private final int usage;
  private final IntBuffer indexBuffer;

  CoreElementVBO(final CoreGL gl, final int usageType, final int[] data) {
    this.gl = gl;
    usage = usageType;

    indexBuffer = CoreBufferUtil.createIntBuffer(data);

    final IntBuffer idbuff = IntBuffer.allocate(1);
    gl.glGenBuffers(1, idbuff);
    idbuff.rewind(); // in case glGenBuffers doesn't call flip()
    id = idbuff.get();
    gl.checkGLError("glGenBuffers");
    bind();
    send();
  }

  CoreElementVBO(final CoreGL gl, final int usageType, final IntBuffer data) {
    this.gl = gl;
    usage = usageType;

    indexBuffer = CoreBufferUtil.createIntBuffer(data);

    final IntBuffer idbuff = IntBuffer.allocate(1);
    gl.glGenBuffers(1, idbuff);
    idbuff.rewind(); // in case glGenBuffers doesn't call flip()
    id = idbuff.get();
    gl.checkGLError("glGenBuffers");
    bind();
    send();
  }

  public static CoreElementVBO createCoreElementVBO(final CoreGL gl, final int usageType, final int[] data) {
    return new CoreElementVBO(gl, usageType, data);
  }

  public static CoreElementVBO createCoreElementVBO(final CoreGL gl, final int usageType, final IntBuffer data) {
    return new CoreElementVBO(gl, usageType, data);
  }

  /**
   * Allows access to the internally kept nio FloatBuffer that contains the
   * original buffer data. You can access and change this buffer if you want to
   * update the buffer content. Just make sure that you call rewind() before
   * sending your new data to the GPU with the sendData() method.
   *
   * @return the FloatBuffer with the original buffer data (stored in main
   *         memory not GPU memory)
   */
  IntBuffer getBuffer() {
    return indexBuffer;
  }

  /**
   * bind the buffer object as GL_ARRAY_BUFFER
   */
  void bind() {
    gl.glBindBuffer(gl.GL_ELEMENT_ARRAY_BUFFER(), id);
    gl.checkGLError("glBindBuffer(GL_ELEMENT_ARRAY_BUFFER)");
  }

  /**
   * bind the buffer with id 0
   */
  void unbind() {
    gl.glBindBuffer(gl.GL_ELEMENT_ARRAY_BUFFER(), 0);
    gl.checkGLError("glBindBuffer(GL_ELEMENT_ARRAY_BUFFER -> unbind)");
  }

  /**
   * Send the content of the FloatBuffer to the GPU.
   */
  void send() {
    gl.glBufferData(id, indexBuffer, usage);
    gl.checkGLError("glBufferData(GL_ELEMENT_ARRAY_BUFFER)");
  }

  /**
   * Delete all resources for this VBO.
   */
  void delete() {
    final IntBuffer idbuff = IntBuffer.allocate(1);
    idbuff.put(id);
    idbuff.flip();
    gl.glDeleteBuffers(1, idbuff);
  }

  /**
   * Enable primitive restart using the given value.
   * 
   * @param value
   *          the value to use as primitive restart
   */
  void enablePrimitiveRestart(final int value) {
    gl.glPrimitiveRestartIndex(value);
    gl.glEnable(gl.GL_PRIMITIVE_RESTART());
  }

  /**
   * Disable primitive restart again.
   */
  void disablePrimitiveRestart() {
    gl.glDisable(gl.GL_PRIMITIVE_RESTART());
  }
}
