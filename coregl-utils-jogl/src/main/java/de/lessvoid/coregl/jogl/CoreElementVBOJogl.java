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
package de.lessvoid.coregl.jogl;


import java.nio.IntBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2GL3;
import javax.media.opengl.GLContext;

import com.jogamp.common.nio.Buffers;

import de.lessvoid.coregl.CoreCheckGL;
import de.lessvoid.coregl.CoreElementVBO;

public class CoreElementVBOJogl implements CoreElementVBO {
  private static final CoreCheckGL checkGL = new CoreCheckGLJogl();
  private int[] id = new int[1];
  private int usage;
  private IntBuffer indexBuffer;

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreElementVBO#getBuffer()
   */
  @Override
  public IntBuffer getBuffer() {
    return indexBuffer;
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreElementVBO#bind()
   */
  @Override
  public void bind() {
    final GL gl = GLContext.getCurrentGL();
    gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, id[0]);
    checkGL.checkGLError("glBindBuffer(GL_ELEMENT_ARRAY_BUFFER)");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreElementVBO#unbind()
   */
  @Override
  public void unbind() {
    final GL gl = GLContext.getCurrentGL();
    gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, 0);
    checkGL.checkGLError("glBindBuffer(GL_ELEMENT_ARRAY_BUFFER -> unbind)");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreElementVBO#send()
   */
  @Override
  public void send() {
    final GL gl = GLContext.getCurrentGL();
    gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, indexBuffer.limit()*4, indexBuffer, usage);
    checkGL.checkGLError("glBufferData(GL_ELEMENT_ARRAY_BUFFER)");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreElementVBO#delete()
   */
  @Override
  public void delete() {
    final GL gl = GLContext.getCurrentGL();
    gl.glDeleteBuffers(1, id, 0);
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreElementVBO#enablePrimitiveRestart(int)
   */
  @Override
  public void enablePrimitiveRestart(int value) {
    final GL gl = GLContext.getCurrentGL();
    gl.getGL2GL3().glPrimitiveRestartIndex(value);
    gl.glEnable(GL2GL3.GL_PRIMITIVE_RESTART);
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreElementVBO#disablePrimitiveRestart()
   */
  @Override
  public void disablePrimitiveRestart() {
    final GL gl = GLContext.getCurrentGL();
    gl.glDisable(GL2GL3.GL_PRIMITIVE_RESTART);
  }

  private CoreElementVBOJogl(final int usageType, final int[] data) {
    final GL gl = GLContext.getCurrentGL();
    usage = usageType;

    indexBuffer = Buffers.newDirectIntBuffer(data.length);
    indexBuffer.put(data);
    indexBuffer.rewind();

    gl.glGenBuffers(1, id, 0);
    checkGL.checkGLError("glGenBuffers");
    bind();
    send();
  }
}
