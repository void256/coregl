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

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * The buffer type for a CoreBufferObject.
 * Created by void on 12.03.16.
 */
public enum CoreBufferDataType {
  BYTE(1) {
    public <T extends Buffer> T createBuffer(final CoreGL gl, final int size) {
      return (T) CoreBufferUtil.createByteBuffer(calcByteLength(size));
    }

    public <T extends Buffer> T asBuffer(final ByteBuffer dataBuffer) {
      return (T) dataBuffer.order(ByteOrder.nativeOrder());
    }
    public ByteBuffer bla(final Buffer vertexBuffer) {
      return null;
    }
  },
  FLOAT(2) {
    public <T extends Buffer> T createBuffer(final CoreGL gl, final int size) {
      return (T) CoreBufferUtil.createByteBuffer(calcByteLength(size)).asFloatBuffer();
    }

    public <T extends Buffer> T asBuffer(final ByteBuffer dataBuffer) {
      return (T) dataBuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
    }
    public FloatBuffer bla(final Buffer vertexBuffer) {
      return null;
    }
  },
  SHORT(1) {
    public <T extends Buffer> T createBuffer(final CoreGL gl, final int size) {
      return (T) CoreBufferUtil.createByteBuffer(calcByteLength(size)).asShortBuffer();
    }

    public <T extends Buffer> T asBuffer(final ByteBuffer dataBuffer) {
      return (T) dataBuffer.order(ByteOrder.nativeOrder()).asShortBuffer();
    }
    public ShortBuffer bla(final Buffer vertexBuffer) {
      return null;
    }
  };

  private final int byteSizeFactor;

  CoreBufferDataType(final int byteSizeFactor) {
    this.byteSizeFactor = byteSizeFactor;
  }

  public int calcByteLength(final int size) {
    return size << byteSizeFactor;
  }

  public abstract <T extends Buffer> T createBuffer(CoreGL gl, int size);
  public abstract <T extends Buffer> T asBuffer(ByteBuffer dataBuffer);
  public abstract <T extends Buffer> T bla(final Buffer vertexBuffer);
}
