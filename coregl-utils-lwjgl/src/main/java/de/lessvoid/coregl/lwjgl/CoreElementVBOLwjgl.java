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


import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL31.GL_PRIMITIVE_RESTART;
import static org.lwjgl.opengl.GL31.glPrimitiveRestartIndex;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import de.lessvoid.coregl.CoreCheckGL;
import de.lessvoid.coregl.CoreElementVBO;

public class CoreElementVBOLwjgl implements CoreElementVBO {
  private final CoreCheckGL checkGL;

  private int id;
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
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
    checkGL.checkGLError("glBindBuffer(GL_ELEMENT_ARRAY_BUFFER)");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreElementVBO#unbind()
   */
  @Override
  public void unbind() {
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    checkGL.checkGLError("glBindBuffer(GL_ELEMENT_ARRAY_BUFFER -> unbind)");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreElementVBO#send()
   */
  @Override
  public void send() {
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, usage);
    checkGL.checkGLError("glBufferData(GL_ELEMENT_ARRAY_BUFFER)");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreElementVBO#delete()
   */
  public void delete() {
    glDeleteBuffers(id);
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreElementVBO#enablePrimitiveRestart(int)
   */
  @Override
  public void enablePrimitiveRestart(final int value) {
    glPrimitiveRestartIndex(value);
    glEnable(GL_PRIMITIVE_RESTART);
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreElementVBO#disablePrimitiveRestart()
   */
  @Override
  public void disablePrimitiveRestart() {
    glDisable(GL_PRIMITIVE_RESTART);
  }

  CoreElementVBOLwjgl(final CoreCheckGL checkGLParam, final int usageType, final int[] data) {
    checkGL = checkGLParam;
    usage = usageType;

    indexBuffer = BufferUtils.createIntBuffer(data.length);
    indexBuffer.put(data);
    indexBuffer.rewind();

    id = glGenBuffers();
    checkGL.checkGLError("glGenBuffers");
    bind();
    send();
  }

  CoreElementVBOLwjgl(final CoreCheckGL checkGLParam, final int usageType, final IntBuffer data) {
    checkGL = checkGLParam;
    usage = usageType;

    indexBuffer = BufferUtils.createIntBuffer(data.limit());
    indexBuffer.put(data);
    indexBuffer.rewind();

    id = glGenBuffers();
    checkGL.checkGLError("glGenBuffers");
    bind();
    send();
  }

}
