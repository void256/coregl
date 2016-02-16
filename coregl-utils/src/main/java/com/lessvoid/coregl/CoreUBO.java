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

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Map;

import com.lessvoid.math.Vec3;
import com.lessvoid.math.Vec4;
import com.lessvoid.coregl.spi.CoreGL;
import com.lessvoid.math.Mat3;
import com.lessvoid.math.Mat4;
import com.lessvoid.math.Vec2;

public class CoreUBO {

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    System.out.println("HJSAHDKJAHSD");
  }

  private final CoreGL gl;

  private final int id;
  private final int usage;
  private final int byteLength;
  private final ByteBuffer byteBuffer;
  private final Map<String, UniformBlockInfo> blockInfos;

  public static CoreUBO createCoreUBO(final CoreGL gl, final int length, final Map<String, UniformBlockInfo> infos) {
    return new CoreUBO(gl, gl.GL_STATIC_DRAW(), length, infos);
  }

  CoreUBO(final CoreGL gl, final int usageType, final int length, final Map<String, UniformBlockInfo> infos) {
    this.gl = gl;
    usage = usageType;
    byteLength = length;
    byteBuffer = gl.getUtil().createByteBuffer(length);
    blockInfos = infos;
    final IntBuffer idbuff = gl.getUtil().createIntBuffer(1);
    gl.glGenBuffers(1, idbuff);
    id = idbuff.get(0);
    gl.checkGLError("glGenBuffers");
  }

  public void setVec2(final String name, final Vec2 vec) {
    final UniformBlockInfo blockInfo = getBlockInfo(name);
    byteBuffer.putFloat(blockInfo.offset, vec.x);
    byteBuffer.putFloat(blockInfo.offset + 4, vec.y);
  }

  public void setVec3(final String name, final Vec3 vec) {
    final UniformBlockInfo blockInfo = getBlockInfo(name);
    byteBuffer.putFloat(blockInfo.offset, vec.x);
    byteBuffer.putFloat(blockInfo.offset + 4, vec.y);
    byteBuffer.putFloat(blockInfo.offset + 8, vec.z);
  }

  public void setVec4(final String name, final Vec4 vec) {
    final UniformBlockInfo blockInfo = getBlockInfo(name);
    byteBuffer.putFloat(blockInfo.offset, vec.x);
    byteBuffer.putFloat(blockInfo.offset + 4, vec.y);
    byteBuffer.putFloat(blockInfo.offset + 8, vec.z);
    byteBuffer.putFloat(blockInfo.offset + 12, vec.w);
  }

  public void setMat3(final String name, final Mat3 mat) {
    final UniformBlockInfo blockInfo = getBlockInfo(name);
    int offset = blockInfo.offset + blockInfo.matrixStride * 0;
    byteBuffer.putFloat(offset, mat.m00);
    offset += 4;
    byteBuffer.putFloat(offset, mat.m10);
    offset += 4;
    byteBuffer.putFloat(offset, mat.m20);
    offset += 4;
    offset = blockInfo.offset + blockInfo.matrixStride * 1;
    byteBuffer.putFloat(offset, mat.m01);
    offset += 4;
    byteBuffer.putFloat(offset, mat.m11);
    offset += 4;
    byteBuffer.putFloat(offset, mat.m21);
    offset += 4;
    offset = blockInfo.offset + blockInfo.matrixStride * 2;
    byteBuffer.putFloat(offset, mat.m02);
    offset += 4;
    byteBuffer.putFloat(offset, mat.m12);
    offset += 4;
    byteBuffer.putFloat(offset, mat.m22);
    offset += 4;
  }

  public void setMat4(final String name, final Mat4 mat) {
    final UniformBlockInfo blockInfo = getBlockInfo(name);
    int offset = blockInfo.offset + blockInfo.matrixStride * 0;
    byteBuffer.putFloat(offset, mat.m00);
    offset += 4;
    byteBuffer.putFloat(offset, mat.m10);
    offset += 4;
    byteBuffer.putFloat(offset, mat.m20);
    offset += 4;
    byteBuffer.putFloat(offset, mat.m30);
    offset += 4;
    offset = blockInfo.offset + blockInfo.matrixStride * 1;
    byteBuffer.putFloat(offset, mat.m01);
    offset += 4;
    byteBuffer.putFloat(offset, mat.m11);
    offset += 4;
    byteBuffer.putFloat(offset, mat.m21);
    offset += 4;
    byteBuffer.putFloat(offset, mat.m31);
    offset += 4;
    offset = blockInfo.offset + blockInfo.matrixStride * 2;
    byteBuffer.putFloat(offset, mat.m02);
    offset += 4;
    byteBuffer.putFloat(offset, mat.m12);
    offset += 4;
    byteBuffer.putFloat(offset, mat.m22);
    offset += 4;
    byteBuffer.putFloat(offset, mat.m32);
    offset += 4;
    offset = blockInfo.offset + blockInfo.matrixStride * 2;
    byteBuffer.putFloat(offset, mat.m03);
    offset += 4;
    byteBuffer.putFloat(offset, mat.m13);
    offset += 4;
    byteBuffer.putFloat(offset, mat.m23);
    offset += 4;
    byteBuffer.putFloat(offset, mat.m33);
    offset += 4;
  }

  public void setFloat(final String name, final float... values) {
    for (int i = 0; i < values.length; i++) {
      byteBuffer.putFloat(blockInfos.get(name).offset + i * 4, values[i]);
    }
  }

  public void setFloatArray(final String name, final float[] values) {
    final UniformBlockInfo blockInfo = getBlockInfo(name);
    int offset = blockInfo.offset;
    for (int i = 0; i < values.length; i++) {
      byteBuffer.putFloat(offset, values[i]);
      offset += blockInfo.arrayStride;
    }
  }

  private UniformBlockInfo getBlockInfo(final String name) {
    final UniformBlockInfo blockInfo = blockInfos.get(name);
    if (blockInfo == null) {
      throw new CoreGLException("Missing uniform block with name [" + name + "]. Did you misspelled it?");
    }
    return blockInfo;
  }

  public void setInt(final String name, final int... values) {
    for (int i = 0; i < values.length; i++) {
      byteBuffer.putInt(blockInfos.get(name).offset + i * 4, values[i]);
    }
  }

  public void setIntArray(final String name, final int[] values) {
    final UniformBlockInfo blockInfo = getBlockInfo(name);
    int offset = blockInfo.offset;
    for (int i = 0; i < values.length; i++) {
      byteBuffer.putInt(offset, values[i]);
      offset += blockInfo.arrayStride;
    }
  }

  public void bindBufferBase(final int bindingPoint) {
    gl.glBindBufferBase(gl.GL_UNIFORM_BUFFER(), bindingPoint, id);
    gl.checkGLError("glBindBufferBase");
  }

  public ByteBuffer getBuffer() {
    return byteBuffer;
  }

  public void send() {
    for (int i = 0; i < byteBuffer.limit(); i++) {
      System.out.print(String.format("%02X ", byteBuffer.get(i)));
    }
    byteBuffer.rewind();
    gl.glBindBuffer(gl.GL_UNIFORM_BUFFER(), id);
    gl.glBufferData(gl.GL_UNIFORM_BUFFER(), byteBuffer.asIntBuffer(), usage);
    gl.checkGLError("glBufferData(GL_UNIFORM_BUFFER)");
  }

  public void delete() {
    gl.glDeleteBuffers(1, gl.getUtil().createIntBuffer(new int[] { id }));
    gl.checkGLError("glDeleteBuffers");
  }
}
