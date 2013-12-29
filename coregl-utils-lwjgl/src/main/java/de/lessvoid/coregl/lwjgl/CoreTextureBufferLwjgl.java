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

import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL30.GL_R32F;
import static org.lwjgl.opengl.GL31.GL_TEXTURE_BUFFER;
import static org.lwjgl.opengl.GL31.glTexBuffer;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import de.lessvoid.coregl.CoreCheckGL;
import de.lessvoid.coregl.CoreTextureBuffer;

public class CoreTextureBufferLwjgl implements CoreTextureBuffer {
  private final CoreCheckGL checkGL;

  private int tbo;
  private int texture;

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreTextureBuffer#bind()
   */
  @Override
  public void bind() {
    glBindTexture(GL_TEXTURE_BUFFER, texture);
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreTextureBuffer#dispose()
   */
  @Override
  public void dispose() {
    glBindBuffer(GL_TEXTURE_BUFFER, tbo);
    checkGL.checkGLError("glBindBuffer");

    glDeleteBuffers(tbo);
    checkGL.checkGLError("glDeleteBuffers");

    glDeleteTextures(texture);
    checkGL.checkGLError("glDeleteTextures");
  }

  CoreTextureBufferLwjgl(final CoreCheckGL checkGL, final byte[] data) {
    this.checkGL = checkGL;
    // GL_R8Ibyte 1 NO R 0 0 1
    // GL_RG8Ibyte 2 NO R G 0 1
    // GL_RGBA8Ibyte 4 NO R G B A

    // GL_R8UI ubyte 1 NO R 0 0 1
    // GL_RG8UIubyte 2 NO R G 0 1
    // GL_RGBA8UIubyte 4 NO R G B A
    // GL_R8 ubyte 1 YES R 0 0 1
    // GL_RG8ubyte 2 YES R G 0 1

  }

  CoreTextureBufferLwjgl(final CoreCheckGL checkGL, final short[] data) {
    this.checkGL = checkGL;
    // GL_R16I short 1 NO R 0 0 1
    // GL_RG16I short 2 NO R G 0 1
    // GL_RGBA16I short 4 NO R G B A
    // GL_R16UI ushort 1 NO R 0 0 1
    // GL_RG16UI ushort 2 NO R G 0 1
    // GL_RGBA16UI ushort 4 NO R G B A
    // GL_R16 ushort 1 YES R 0 0 1
    // GL_RG16 ushort 2 YES R G 0 1
    // GL_RGBA16 short 4 YES R G B A

  }

  CoreTextureBufferLwjgl(final CoreCheckGL checkGL, final int[] data) {
    this.checkGL = checkGL;
    // GL_R32I int 1 NO R 0 0 1
    // GL_RG32I int 2 NO R G 0 1
    // GL_RGB32I int 3 NO R G B 1
    // GL_RGBA32I int 4 NO R G B A

    // GL_R32UI uint 1 NO R 0 0 1
    // GL_RG32UI uint 2 NO R G 0 1
    // GL_RGB32UI uint 3 NO R G B 1
    // GL_RGBA32UI uint 4 NO R G B A
    // GL_RGBA8 uint 4 YES R G B A

  }

  CoreTextureBufferLwjgl(final CoreCheckGL checkGL, final float[] data) {
    this.checkGL = checkGL;
    // GL_R16F half 1 NO R 0 0 1
    // GL_RG16F half 2 NO R G 0 1
    // GL_RGBA16F half 4 NO R G B A
    // GL_R32F float 1 NO R 0 0 1
    // GL_RG32F float 2 NO R G 0 1
    // GL_RGB32Ffloat 3 NO R G B 1
    // GL_RGBA32Ffloat 4 NO R G B A

    init(data);
  }

  private void init(final float[] data) {
    tbo = glGenBuffers();
    checkGL.checkGLError("glGenBuffers");

    glBindBuffer(GL_TEXTURE_BUFFER, tbo);
    checkGL.checkGLError("glBindBuffer");

    FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    glBufferData(GL_TEXTURE_BUFFER, buffer, GL_STATIC_DRAW);

    texture = glGenTextures();
    checkGL.checkGLError("glGenTextures");

    glBindTexture(GL_TEXTURE_BUFFER, texture);
    checkGL.checkGLError("glBindTexture");

    glTexBuffer(GL_TEXTURE_BUFFER, GL_R32F, tbo);
    checkGL.checkGLError("glTexBuffer");
  }
}
