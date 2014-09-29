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

import de.lessvoid.coregl.CoreTexture2DConstants.ColorFormat;
import de.lessvoid.coregl.CoreTexture2DConstants.ResizeFilter;
import de.lessvoid.coregl.CoreTexture2DConstants.Type;
import de.lessvoid.coregl.CoreVBO.UsageType;

public class CoreFactory {
	
	private final CoreTexture2DConstants texConsts;
	
	private CoreGL gl;
	
	/**
	 * Equivalent to <code>CoreFactory(gl, true)</code>
	 * @param gl
	 */
	public CoreFactory(final CoreGL gl) {
		this(gl, true);
	}

	/**
	 * Creates a new CoreFactory using the CoreGL interface provided and with optional error checking.
	 * @param gl
	 * @param errorChecksEnabled true if error checking should be enabeld, false otherwise
	 */
	public CoreFactory(final CoreGL gl, final boolean errorChecksEnabled) {
		this.gl = gl;
		gl.setErrorChecksEnabled(errorChecksEnabled);
		texConsts = new CoreTexture2DConstants(gl);
		CoreVAO.initIntTypeMap(gl);
		CoreVAO.initFloatTypeMap(gl);
		CoreVBO.initUsageTypeMap(gl);
	}

	// CoreTexture2D /////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Create an empty texture.
	 *
	 * @param format the ColorFormat for the texture
	 * @param dataType the data type you'll later send the pixel data
	 * @param width the width of the texture
	 * @param height the height of the texture
	 * @param filter the ResizeFilter to use
	 * @return the new allocated CoreTexture2D
	 */
	CoreTexture2D createEmptyTexture(
			ColorFormat format,
			Type dataType,
			int width,
			int height,
			ResizeFilter filter) {
		return CoreTexture2D.createEmptyTexture(format, dataType, width, height, filter);
	}

	/**
	 * Create an empty texture.
	 *
	 * @param format the ColorFormat for the texture
	 * @param dataType the data type you'll later send the pixel data
	 * @param width the width of the texture
	 * @param height the height of the texture
	 * @param filter the ResizeFilter to use
	 * @return the new allocated CoreTexture2D
	 */
	CoreTexture2D createEmptyTextureArray(
			ColorFormat format,
			Type dataType,
			int width,
			int height,
			int num,
			ResizeFilter filter);

	/**
	 * This is one of the simple constructors that only allow very limited possibilities for settings. How ever they use
	 * settings that should fit the need on most cases.
	 *
	 * @param format the texture format
	 * @param width the width of the texture
	 * @param height the height of the texture
	 * @param data the pixel data
	 * @param filter the used filter
	 * @throws CoreGLException in case the creation of the texture fails for any reason
	 */
	CoreTexture2D createTexture(
			ColorFormat format,
			int width,
			int height,
			Buffer data,
			ResizeFilter filter);

	/**
	 * This is the constructor is a slightly reduced version that defines some common options automatically.
	 *
	 * @param internalFormat the internal format of the texture
	 * @param width the width of the texture in pixels
	 * @param height the height of the texture in pixels
	 * @param format the format of the pixel data
	 * @param data the pixel data
	 * @param magFilter the magnifying filter
	 * @param minFilter the minimizing filter
	 * @throws CoreGLException in case the creation of the texture fails for any reason
	 */
	CoreTexture2D createTexture(
			ColorFormat internalFormat,
			int width,
			int height,
			ColorFormat format,
			Buffer data,
			int magFilter,
			int minFilter);

	/**
	 * This is the constructor is a slightly reduced version that defines some common options automatically.
	 *
	 * @param target the target type of the texture operations, has to be a valid 2D texture target
	 * @param internalFormat the internal format of the texture
	 * @param width the width of the texture in pixels
	 * @param height the height of the texture in pixels
	 * @param format the format of the pixel data
	 * @param data the pixel data
	 * @param magFilter the magnifying filter
	 * @param minFilter the minimizing filter
	 * @throws CoreGLException in case the creation of the texture fails for any reason
	 */
	CoreTexture2D createTexture(
			int target,
			ColorFormat internalFormat,
			int width,
			int height,
			ColorFormat format,
			Buffer data,
			int magFilter,
			int minFilter);

	/**
	 * This is the constructor that allows to define all the settings required to create a texture. Using this causes the
	 * class to disable all assumptions and do exactly what you want.
	 *
	 * @param textureId the ID that is supposed to be used with the texture, it has to be a valid texture ID for the
	 *                  selected target. Use {@link #AUTO} to tell the class to fetch a texture ID in its own.
	 * @param target the target type of the texture operations, has to be a valid 2D texture target
	 * @param level the mipmap level of the texture, in case you want the automated mipmap generation to kick in leave
	 *              this value on {@code 0} and selected a fitting minimizing filter
	 * @param internalFormat the internal format of the texture
	 * @param width the width of the texture in pixels
	 * @param height the height of the texture in pixels
	 * @param border the width of the border of the texture
	 * @param format the format of the pixel data
	 * @param type the data type of the pixel data
	 * @param data the pixel data
	 * @param magFilter the magnifying filter
	 * @param minFilter the minimizing filter
	 * @throws CoreGLException in case the creation of the texture fails for any reason
	 */
	CoreTexture2D createTexture(
			int textureId,
			int target,
			int level,
			ColorFormat internalFormat,
			int width,
			int height,
			int border,
			ColorFormat format,
			Type type,
			Buffer data,
			int magFilter,
			int minFilter);

	// CoreShader ////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Create a new Shader.
	 * @return the new CoreShader instance
	 */
	public CoreShader newShader() {
		return new CoreShader(gl);
	}
	
	/**
	 * Create a new Shader with the given vertex attributes automatically bind to the generic attribute indices in
	 * ascending order beginning with 0. This method can be used when you want to control the vertex attribute binding
	 * on your own.
	 *
	 * @param vertexAttributes the name of the vertex attribute. The first String gets generic attribute index 0. the
	 *        second String gets generic attribute index 1 and so on.
	 * @return the CoreShader instance
	 */
	public CoreShader newShaderWithVertexAttributes(String ... vertexAttributes) {
		return new CoreShader(gl, vertexAttributes);
	}

	// CoreVBO ///////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Create a new VBO with the given Type containing float data. This will create the buffer object but does not bind
	 * or send the data to the GPU. You'll need to call bind() to bind this VBO and you'll need to call send() to transmit
	 * the buffer data to the GPU.
	 *
	 * @param dataType the CoreVBO.DataType of the NIO Buffer that the CoreVBO instance should contain
	 * @param usageType the GL usage type for the buffer @see {@link UsageType}
	 * @param size the size of the buffer
	 * @return the CoreVBO instance
	 */
	public <T extends Buffer> CoreVBO<T> createVBO(CoreVBO.DataType dataType, CoreVBO.UsageType usageType, int size) {
		return new CoreVBO<T>(gl, dataType, usageType, size);
	}

	/**
	 * Create a new VBO with the given Type containing float data. This will create the buffer object but does not bind
	 * or send the data to the GPU. You'll need to call bind() to bind this VBO and you'll need to call send() to transmit
	 * the buffer data to the GPU.
	 *
	 * @param dataType the CoreVBO.DataType of the NIO Buffer that the CoreVBO instance should contain
	 * @param usageType the GL usage type for the buffer @see {@link UsageType}
	 * @param size the size of the buffer
	 * @return the CoreVBO instance
	 */
	public <T extends Buffer> CoreVBO<T> createVBO(CoreVBO.DataType dataType, CoreVBO.UsageType usageType, Object[] data) {
		return new CoreVBO<T>(gl, dataType, usageType, data);
	}

	// CoreVAO ///////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Create a new CoreVAO.
	 * @return the CoreVAO instance created
	 */
	public CoreVAO createVAO() {
		return new CoreVAO(gl);
	}

	// CoreFBO ///////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Create a new CoreFBO.
	 * @return the CoreFBO instance created
	 */
	public CoreFBO createCoreFBO() {
		return new CoreFBO(gl);
	}

	// CoreTextureBuffer /////////////////////////////////////////////////////////////////////////////////////////////////

	CoreTextureBuffer createCoreTextureBuffer(byte[] data);
	CoreTextureBuffer createCoreTextureBuffer(short[] data);
	CoreTextureBuffer createCoreTextureBuffer(int[] data);
	CoreTextureBuffer createCoreTextureBuffer(float[] data);

	// CoreElementVBO ////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Create a new VBO with static vertex data (GL_STATIC_DRAW). This will
	 * create the buffer object but does not bind or send the data to the GPU.
	 * You'll need to call bind() to bind this VBO and you'll need to call sendData()
	 * to transmit the buffer data to the GPU.
	 * 
	 * @param data float array of buffer data
	 * @return the CoreVBO instance created
	 */
	public CoreElementVBO createStatic(int[] data) {
		return new CoreElementVBO(gl, gl.GL_STATIC_DRAW(), data);
	}

	/**
	 * This provides the same functionality as createStaticVBO() but automatically
	 * sends the data given to the GPU.
	 * 
	 * @param data float array of buffer data
	 * @return the CoreVBO instance created
	 */
	public CoreElementVBO createStaticAndSend(int[] data) {
		CoreElementVBO result = new CoreElementVBO(gl, gl.GL_STATIC_DRAW(), data);
		result.send();
		return result;
	}

	/**
	 * This provides the same functionality as createStatic() but automatically
	 * sends the data given to the GPU.
	 * 
	 * @param data float array of buffer data
	 * @return the CoreVBO instance created
	 */
	public CoreElementVBO createStaticAndSend(IntBuffer data) {
		CoreElementVBO result = new CoreElementVBO(gl, gl.GL_STATIC_DRAW(), data);
		result.send();
		return result;
	}

	/**
	 * This works exactly as createStaticVBO() but will use GL_DYNAMIC_DRAW instead.
	 *
	 * @param data float array of buffer data
	 * @return the CoreVBO instance created
	 */
	public CoreElementVBO createDynamic(int[] data) {
		return new CoreElementVBO(gl, gl.GL_DYNAMIC_DRAW(), data);
	}

	/**
	 * This works exactly as createStaticVBO() but will use GL_STREAM_DRAW instead.
	 *
	 * @param data float array of buffer data
	 * @return the CoreVBO instance created
	 */
	public CoreElementVBO createStream(int[] data) {
		return new CoreElementVBO(gl, gl.GL_STREAM_DRAW(), data);
	}

	// CoreDisplaySetup //////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Create the CoreDisplaySetup class that is completely optional but a handy tool to setup the rendering system.
	 * @return
	 */
	public CoreSetup createSetup() {
		return null; // FIXME !!!! CoreSetup needs implementation
	}

	// CoreRender ////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Create the CoreRender class.
	 * @return CoreRender instance
	 */
	public CoreRender getCoreRender() {
		return new CoreRender(gl);
	}

	// CoreScreenshot ////////////////////////////////////////////////////////////////////////////////////////////////////

	public CoreScreenshot createCoreScreenshot() {
		return new CoreScreenshot(gl);
	}

}
