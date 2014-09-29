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

import java.nio.*;


/**
 * CoreTextureBuffer - texture buffer support (work in progress)
 * @author void
 */
public class CoreTextureBuffer {

	private int tbo;
	private int texture;

	private final CoreGL gl;

	/**
	 * Bind this texture buffer.
	 */
	public void bind() {
		gl.glBindTexture(gl.GL_TEXTURE_BUFFER(), texture);
	}

	/**
	 * Dispose this texture buffer.
	 */
	public void dispose() {
		gl.glBindBuffer(gl.GL_TEXTURE_BUFFER(), tbo);
		gl.checkGLError("glBindBuffer");

		IntBuffer buff = gl.getUtil().createIntBuffer(1);
		buff.put(tbo).flip();
		gl.glDeleteBuffers(1, buff);
		gl.checkGLError("glDeleteBuffers");

		buff.clear();
		buff.put(texture).flip();
		gl.glDeleteTextures(1, buff);
		gl.checkGLError("glDeleteTextures");
	}

	CoreTextureBuffer(final CoreGL gl, final byte[] data) {
		this.gl = gl;
		// GL_R8Ibyte 1 NO R 0 0 1
		// GL_RG8Ibyte 2 NO R G 0 1
		// GL_RGBA8Ibyte 4 NO R G B A

		// GL_R8UI ubyte 1 NO R 0 0 1
		// GL_RG8UIubyte 2 NO R G 0 1
		// GL_RGBA8UIubyte 4 NO R G B A
		// GL_R8 ubyte 1 YES R 0 0 1
		// GL_RG8ubyte 2 YES R G 0 1

	}

	CoreTextureBuffer(final CoreGL gl, final short[] data) {
		this.gl = gl;
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

	CoreTextureBuffer(final CoreGL gl, final int[] data) {
		this.gl = gl;
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

	CoreTextureBuffer(final CoreGL gl, final float[] data) {
		this.gl = gl;
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
		IntBuffer buff = gl.getUtil().createIntBuffer(1);
		gl.glGenBuffers(1, buff);
		gl.checkGLError("glGenBuffers");
		buff.rewind();
		tbo = buff.get();

		gl.glBindBuffer(gl.GL_TEXTURE_BUFFER(), tbo);
		gl.checkGLError("glBindBuffer");

		FloatBuffer buffer = gl.getUtil().createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		gl.glBufferData(gl.GL_TEXTURE_BUFFER(), buffer, gl.GL_STATIC_DRAW());

		buff.clear();
		gl.glGenTextures(1, buff);
		buff.rewind();
		texture = buff.get();
		gl.checkGLError("glGenTextures");

		gl.glBindTexture(gl.GL_TEXTURE_BUFFER(), texture);
		gl.checkGLError("glBindTexture");

		gl.glTexBuffer(gl.GL_TEXTURE_BUFFER(), gl.GL_R32F(), tbo);
		gl.checkGLError("glTexBuffer");
	}
}
