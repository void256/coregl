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

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * CoreTextureBuffer - texture buffer support
 * 
 * @author void
 */
public class CoreTextureBuffer {
  private final CoreGL gl;
  private final int internalFormat;
  private int textureBuffer;
  private int textureId;

  public enum InternalFormatByte {
    R8Ibyte,
    RG8Ibyte,
    RGBA8Ibyte,
    R8UI,
    RG8UIubyte,
    RGBA8UIubyte,
    R8,
    RG8ubyte
  }

  public enum InternalFormatShort {
    R16I,
    RG16I,
    RGBA16I,
    R16UI,
    RG16UI,
    RGBA16UI,
    R16,
    RG16,
    RGBA16
  }

  public enum InternalFormatInt {
    R32I,
    RG32I,
    RGB32I,
    RGBA32I,
    R32UI,
    RG32UI,
    RGB32UI,
    RGBA32UI,
    RGBA8
  }

  public enum InternalFormatFloat {
    R16F,
    RG16F,
    RGBA16F,
    R32F,
    RG32F,
    RGB32F,
    RGBA32F
  }

  public static CoreTextureBuffer create(final CoreGL gl, final InternalFormatByte internalFormat, final byte[] data) {
    return new CoreTextureBuffer(gl, translate(gl, internalFormat)).fillBuffer(toBuffer(data));
  }

  public static CoreTextureBuffer create(final CoreGL gl, final InternalFormatShort internalFormat, final short[] data) {
    return new CoreTextureBuffer(gl, translate(gl, internalFormat)).fillBuffer(toBuffer(data));
  }

  public static CoreTextureBuffer create(final CoreGL gl, final InternalFormatInt internalFormat, final int[] data) {
    return new CoreTextureBuffer(gl, translate(gl, internalFormat)).fillBuffer(toBuffer(data));
  }

  public static CoreTextureBuffer create(final CoreGL gl, final InternalFormatFloat internalFormat, final float[] data) {
    return new CoreTextureBuffer(gl, translate(gl, internalFormat)).fillBuffer(toBuffer(data));
  }

  /**
   * Bind this texture buffer.
   */
  public void bind() {
    gl.glBindTexture(gl.GL_TEXTURE_BUFFER(), textureId);
    gl.glTexBuffer(gl.GL_TEXTURE_BUFFER(), internalFormat, textureBuffer);
    gl.checkGLError("glTexBuffer");
  }

  /**
   * Dispose this texture buffer.
   */
  public void dispose() {
    gl.glBindBuffer(gl.GL_TEXTURE_BUFFER(), textureBuffer);
    gl.checkGLError("glBindBuffer");

    final IntBuffer buff = CoreBufferUtil.createIntBuffer(1);
    buff.put(textureBuffer).flip();
    gl.glDeleteBuffers(1, buff);
    gl.checkGLError("glDeleteBuffers");

    buff.clear();
    buff.put(textureId).flip();
    gl.glDeleteTextures(1, buff);
    gl.checkGLError("glDeleteTextures");
  }

  private CoreTextureBuffer fillBuffer(final ByteBuffer buffer) {
    gl.glBindBuffer(gl.GL_TEXTURE_BUFFER(), textureBuffer);
    gl.checkGLError("glBindBuffer");

    gl.glBufferData(gl.GL_TEXTURE_BUFFER(), buffer, gl.GL_STATIC_DRAW());
    gl.checkGLError("glBufferData");

    return this;
  }

  private CoreTextureBuffer fillBuffer(final ShortBuffer buffer) {
    gl.glBindBuffer(gl.GL_TEXTURE_BUFFER(), textureBuffer);
    gl.checkGLError("glBindBuffer");

    gl.glBufferData(gl.GL_TEXTURE_BUFFER(), buffer, gl.GL_STATIC_DRAW());
    gl.checkGLError("glBufferData");

    return this;
  }

  private CoreTextureBuffer fillBuffer(final IntBuffer buffer) {
    gl.glBindBuffer(gl.GL_TEXTURE_BUFFER(), textureBuffer);
    gl.checkGLError("glBindBuffer");

    gl.glBufferData(gl.GL_TEXTURE_BUFFER(), buffer, gl.GL_STATIC_DRAW());
    gl.checkGLError("glBufferData");

    return this;
  }

  private CoreTextureBuffer fillBuffer(final FloatBuffer buffer) {
    gl.glBindBuffer(gl.GL_TEXTURE_BUFFER(), textureBuffer);
    gl.checkGLError("glBindBuffer");

    gl.glBufferData(gl.GL_TEXTURE_BUFFER(), buffer, gl.GL_STATIC_DRAW());
    gl.checkGLError("glBufferData");

    return this;
  }

  private CoreTextureBuffer(final CoreGL gl, final int internalFormat) {
    this.gl = gl;
    this.internalFormat = internalFormat;
    this.textureBuffer = glGenBuffers();
    this.textureId = glGenTextures();
  }

  private int glGenBuffers() {
    final IntBuffer id = CoreBufferUtil.createIntBuffer(1);

    gl.glGenBuffers(1, id);
    gl.checkGLError("glGenBuffers");

    return id.get();
  }

  private int glGenTextures() {
    final IntBuffer id = CoreBufferUtil.createIntBuffer(1);

    gl.glGenTextures(1, id);
    gl.checkGLError("glGenTextures");

    return id.get();
  }

  private static int translate(final CoreGL gl, final InternalFormatByte internalFormat) {
    switch (internalFormat) {
      case R8Ibyte: return gl.GL_R8I();
      case RG8Ibyte: return gl.GL_RG8I();
      case RGBA8Ibyte: return gl.GL_RGBA8I();
      case R8UI: return gl.GL_R8UI();
      case RG8UIubyte: return gl.GL_RG8UI();
      case RGBA8UIubyte: return gl.GL_RGBA8UI();
      case R8: return gl.GL_R8();
      case RG8ubyte: return gl.GL_RG8();
    }
    throw new CoreGLException("Unknown InternalFormatByte[" + internalFormat + "]");
  }

  private static int translate(final CoreGL gl, final InternalFormatShort internalFormat) {
    switch (internalFormat) {
      case R16I: return gl.GL_R16I();
      case RG16I: return gl.GL_RG16I();
      case RGBA16I: return gl.GL_RGBA16I();
      case R16UI: return gl.GL_R16UI();
      case RG16UI: return gl.GL_RG16UI();
      case RGBA16UI: return gl.GL_RGBA16UI();
      case R16: return gl.GL_R16();
      case RG16: return gl.GL_RG16();
      case RGBA16: return gl.GL_RGBA16();
    }
    throw new CoreGLException("Unknown InternalFormatShort[" + internalFormat + "]");
  }

  private static int translate(final CoreGL gl, final InternalFormatInt internalFormat) {
    switch (internalFormat) {
      case R32I: return gl.GL_R32I();
      case RG32I: return gl.GL_RG32I();
      case RGB32I: return gl.GL_RGB32I();
      case RGBA32I: return gl.GL_RGBA32I();
      case R32UI: return gl.GL_R32UI();
      case RG32UI: return gl.GL_RG32UI();
      case RGB32UI: return gl.GL_RGB32UI();
      case RGBA32UI: return gl.GL_RGBA32UI();
      case RGBA8: return gl.GL_RGBA8();
    }
    throw new CoreGLException("Unknown InternalFormatInt[" + internalFormat + "]");
  }

  private static int translate(final CoreGL gl, final InternalFormatFloat internalFormat) {
    switch (internalFormat) {
      case R16F: return gl.GL_R16F();
      case RG16F: return gl.GL_RG16F();
      case RGBA16F: return gl.GL_RGBA16F();
      case R32F: return gl.GL_R32F();
      case RG32F: return gl.GL_RG32F();
      case RGB32F: return gl.GL_RGB32F();
      case RGBA32F: return gl.GL_RGBA32F();
    }
    throw new CoreGLException("Unknown InternalFormatFloat[" + internalFormat + "]");
  }

  private static ByteBuffer toBuffer(final byte[] data) {
    ByteBuffer buffer = CoreBufferUtil.createByteBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  private static ShortBuffer toBuffer(final short[] data) {
    ShortBuffer buffer = CoreBufferUtil.createShortBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  private static IntBuffer toBuffer(final int[] data) {
    IntBuffer buffer = CoreBufferUtil.createIntBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  private static FloatBuffer toBuffer(final float[] data) {
    FloatBuffer buffer = CoreBufferUtil.createFloatBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }
}
