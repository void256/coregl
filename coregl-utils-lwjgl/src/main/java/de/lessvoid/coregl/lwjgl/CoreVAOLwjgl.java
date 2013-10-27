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


import static org.lwjgl.opengl.ARBInstancedArrays.glVertexAttribDivisorARB;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import de.lessvoid.coregl.CoreCheckGL;
import de.lessvoid.coregl.CoreVAO;

/**
 * A Vertex Array Object (VAO).
 * @author void
 */
public class CoreVAOLwjgl implements CoreVAO {
  private static final CoreCheckGL checkGL = new CoreCheckGLLwjgl();
  private int vao;

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVAO#bind()
   */
  @Override
  public void bind() {
    glBindVertexArray(vao);
    checkGL.checkGLError("glBindVertexArray");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVAO#unbind()
   */
  @Override
  public void unbind() {
    glBindVertexArray(0);
    checkGL.checkGLError("glBindVertexArray(0)");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVAO#delete()
   */
  @Override
  public void delete() {
    glDeleteVertexArrays(vao);
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVAO#enableVertexAttributef(int, int, int, int)
   */
  @Override
  public void enableVertexAttributef(final int index, final int size, final int stride, final int offset) {
    glVertexAttribPointer(index, size, GL_FLOAT, false, stride * 4, offset * 4);
    glEnableVertexAttribArray(index);
    checkGL.checkGLError("glVertexAttribPointer (" + index + ")");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVAO#disableVertexAttribute(int)
   */
  @Override
  public void disableVertexAttribute(final int index) {
    glDisableVertexAttribArray(index);
    checkGL.checkGLError("glDisableVertexAttribArray (" + index + ")");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVAO#enableVertexAttributeDivisorf(int, int, int, int, int)
   */
  @Override
  public void enableVertexAttributeDivisorf(final int index, final int size, final int stride, final int offset, final int divisor) {
    glVertexAttribPointer(index, size, GL_FLOAT, false, stride * 4, offset * 4);
    glVertexAttribDivisorARB(index, divisor);
    glEnableVertexAttribArray(index);
    checkGL.checkGLError("glVertexAttribPointer (" + index + ")");
  }

  /**
   * Create a new VAO. This calls glGenVertexArrays.
   */
  CoreVAOLwjgl() {
    vao = glGenVertexArrays();
    checkGL.checkGLError("glGenVertexArrays");
  }
}
