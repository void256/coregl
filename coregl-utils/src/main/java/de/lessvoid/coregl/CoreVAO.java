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


/**
 * A Vertex Array Object (VAO).
 * @author void
 */
public interface CoreVAO {
  /**
   * Bind this VAO to make it the current VAO.
   */
  void bind();

  /**
   * Unbinds this VAO which makes the VAO with id 0 the active one.
   */
  void unbind();

  /**
   * Delete all resources for this VAO.
   */
  void delete();

  /**
   * Configure the vertex attribute with the given data. The type of the data will be
   * GL_FLOAT.
   *
   * @param index the index of the vertex attribute to modify
   * @param size the size of the data for this vertex attribute
   *        (the number of GL_FLOAT to use)
   * @param stride the stride between the data
   * @param offset the offset of the data
   */
  void enableVertexAttributef(int index, int size, int stride, int offset);

  /**
   * Disable thegiven vertex attribute index.
   * @param index the index of the vertex attribute to disable
   */
  void disableVertexAttribute(int index);

  /**
   * Configure the vertex attribute with the given data. The type of the data will be
   * GL_FLOAT. This will additionally call glVertexAttribDivisorARB to change the
   * frequency this data will be sent.
   *
   * @param index the index of the vertex attribute to modify
   * @param size the size of the data for this vertex attribute
   *        (the number of GL_FLOAT to use)
   * @param stride the stride between the data
   * @param offset the offset of the data
   * @param divisor Specify the number of instances that will pass between updates of the generic attribute at slot
   *        index.
   */
  void enableVertexAttributeDivisorf(int index, int size, int stride, int offset, int divisor);
}
