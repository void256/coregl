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
import java.util.Hashtable;
import java.util.Map;

/**
 * A Vertex Array Object (VAO).
 * 
 * @author void
 */
public class CoreVAO {

  private final static Map<IntType, Integer> intTypeMap = new Hashtable<IntType, Integer>();
  private final static Map<FloatType, Integer> floatTypeMap = new Hashtable<FloatType, Integer>();

  private final CoreGL gl;

  private final int vao;

  CoreVAO(final CoreGL gl) {
    checkLazyInit(gl);
    this.gl = gl;
    final IntBuffer vaoBuff = CoreBufferUtil.createIntBuffer(1);
    gl.glGenVertexArrays(1, vaoBuff);
    gl.checkGLError("glGenVertexArrays");
    vao = vaoBuff.get(0);
  }

  /**
   * Create a new CoreVAO.
   * 
   * @return the CoreVAO instance created
   */
  public static CoreVAO createCoreVAO(final CoreGL gl) {
    return new CoreVAO(gl);
  }

  /**
   * Bind this VAO to make it the current VAO.
   */
  public void bind() {
    gl.glBindVertexArray(vao);
    gl.checkGLError("glBindVertexArray");
  }

  /**
   * Unbinds this VAO which makes the VAO with id 0 the active one.
   */
  public void unbind() {
    gl.glBindVertexArray(0);
    gl.checkGLError("glBindVertexArray(0)");
  }

  /**
   * Delete all resources for this VAO.
   */
  public void delete() {
    gl.glDeleteVertexArrays(1, CoreBufferUtil.createIntBuffer(new int[] { vao }));
    gl.checkGLError("glDeleteVertexArrays");
  }

  /**
   * Configure the vertex attribute with the given data. The type of the data
   * will be GL_FLOAT.
   *
   * @param index
   *          the index of the vertex attribute to modify
   * @param size
   *          the size of the data for this vertex attribute (the number of
   *          GL_FLOAT to use)
   * @param stride
   *          the stride between the data
   * @param offset
   *          the offset of the data
   */
  public void vertexAttribPointer(final int index,
                                  final int size,
                                  final FloatType vertexType,
                                  final int stride,
                                  final int offset) {
    gl.glVertexAttribPointer(index, size, floatTypeMap.get(vertexType), false, stride * 4, offset * 4);
    gl.checkGLError("glVertexAttribPointer");
  }

  /**
   * Configure the vertex attribute with the given data. The type of the data
   * will be GL_FLOAT. This will additionally call glVertexAttribDivisorARB to
   * change the frequency this data will be sent.
   *
   * @param index
   *          the index of the vertex attribute to modify
   * @param size
   *          the size of the data for this vertex attribute (the number of
   *          GL_FLOAT to use)
   * @param stride
   *          the stride between the data
   * @param offset
   *          the offset of the data
   * @param divisor
   *          Specify the number of instances that will pass between updates of
   *          the generic attribute at slot index.
   */
  public void enableVertexAttributeDivisorf(final int index,
                                            final int size,
                                            final FloatType vertexType,
                                            final int stride,
                                            final int offset,
                                            final int divisor) {
    gl.glVertexAttribPointer(index, size, floatTypeMap.get(vertexType), false, stride * 4, offset * 4);
    gl.glVertexAttribDivisor(index, divisor);
    gl.glEnableVertexAttribArray(index);
    gl.checkGLError("glVertexAttribPointer");
  }

  /**
   * Enable the vertex attribute with the given index.
   *
   * @param index
   *          the index of the vertex attribute to modify
   */
  public void enableVertexAttribute(final int index) {
    gl.glEnableVertexAttribArray(index);
    gl.checkGLError("glEnableVertexAttribArray");
  }

  /**
   * Configure the vertex attribute with the given data. The type of the data
   * will be GL_FLOAT.
   *
   * @param index
   *          the index of the vertex attribute to modify
   * @param size
   *          the size of the data for this vertex attribute (the number of
   *          GL_FLOAT to use)
   * @param stride
   *          the stride between the data
   * @param offset
   *          the offset of the data
   */
  public void vertexAttribIPointer(final int index,
                                   final int size,
                                   final IntType vertexType,
                                   final int stride,
                                   final int offset) {
    gl.glVertexAttribIPointer(index, size, intTypeMap.get(vertexType), stride * 4, offset * 4);
    gl.checkGLError("glVertexAttribIPointer");
  }

  /**
   * Configure the vertex attribute with the given data. The type of the data
   * will be GL_FLOAT. This will additionally call glVertexAttribDivisorARB to
   * change the frequency this data will be sent.
   *
   * @param index
   *          the index of the vertex attribute to modify
   * @param size
   *          the size of the data for this vertex attribute (the number of
   *          GL_FLOAT to use)
   * @param stride
   *          the stride between the data
   * @param offset
   *          the offset of the data
   * @param divisor
   *          Specify the number of instances that will pass between updates of
   *          the generic attribute at slot index.
   */
  public void enableVertexAttributeDivisori(final int index,
                                            final int size,
                                            final IntType vertexType,
                                            final int stride,
                                            final int offset,
                                            final int divisor) {
    gl.glVertexAttribIPointer(index, size, intTypeMap.get(vertexType), stride * 4, offset * 4);
    gl.glVertexAttribDivisor(index, divisor);
    gl.glEnableVertexAttribArray(index);
    gl.checkGLError("glVertexAttribPointer");
  }

  /**
   * Disable thegiven vertex attribute index.
   * 
   * @param index
   *          the index of the vertex attribute to disable
   */
  public void disableVertexAttribute(final int index) {
    gl.glDisableVertexAttribArray(index);
    gl.checkGLError("glDisableVertexAttribArray");
  }

  public enum IntType {
    BYTE,
    UNSIGNED_BYTE,
    SHORT,
    UNSIGNED_SHORT,
    INT,
    UNSIGNED_INT
  }

  public enum FloatType {
    BYTE,
    UNSIGNED_BYTE,
    SHORT,
    UNSIGNED_SHORT,
    INT,
    UNSIGNED_INT,
    HALF_FLOAT,
    FLOAT,
    DOUBLE,
    FIXED,
    INT_2_10_10_10_REV,
    UNSIGNED_INT_2_10_10_10_REV,
    UNSIGNED_INT_10F_11F_11F_REV
  }

  static void initIntTypeMap(final CoreGL gl) {
    intTypeMap.put(IntType.BYTE, gl.GL_BYTE());
    intTypeMap.put(IntType.UNSIGNED_BYTE, gl.GL_UNSIGNED_BYTE());
    intTypeMap.put(IntType.SHORT, gl.GL_SHORT());
    intTypeMap.put(IntType.UNSIGNED_SHORT, gl.GL_UNSIGNED_SHORT());
    intTypeMap.put(IntType.INT, gl.GL_INT());
    intTypeMap.put(IntType.UNSIGNED_INT, gl.GL_UNSIGNED_INT());
  }

  static void initFloatTypeMap(final CoreGL gl) {
    floatTypeMap.put(FloatType.BYTE, gl.GL_BYTE());
    floatTypeMap.put(FloatType.UNSIGNED_BYTE, gl.GL_UNSIGNED_BYTE());
    floatTypeMap.put(FloatType.SHORT, gl.GL_SHORT());
    floatTypeMap.put(FloatType.UNSIGNED_SHORT, gl.GL_UNSIGNED_SHORT());
    floatTypeMap.put(FloatType.INT, gl.GL_INT());
    floatTypeMap.put(FloatType.UNSIGNED_INT, gl.GL_UNSIGNED_INT());
    floatTypeMap.put(FloatType.HALF_FLOAT, gl.GL_HALF_FLOAT());
    floatTypeMap.put(FloatType.FLOAT, gl.GL_FLOAT());
    floatTypeMap.put(FloatType.DOUBLE, gl.GL_DOUBLE());
    floatTypeMap.put(FloatType.FIXED, gl.GL_FIXED());
    floatTypeMap.put(FloatType.INT_2_10_10_10_REV, gl.GL_INT_2_10_10_10_REV());
    floatTypeMap.put(FloatType.UNSIGNED_INT_2_10_10_10_REV, gl.GL_UNSIGNED_INT_2_10_10_10_REV());
    floatTypeMap.put(FloatType.UNSIGNED_INT_10F_11F_11F_REV, gl.GL_UNSIGNED_INT_10F_11F_11F_REV());
  }

  /*
   * Lazy initialization of static maps (init requires instance of CoreGL)
   */
  private static void checkLazyInit(final CoreGL gl) {
    if (intTypeMap.size() == 0) {
      initIntTypeMap(gl);
    }
    if (floatTypeMap.size() == 0) {
      initFloatTypeMap(gl);
    }
  }
}
