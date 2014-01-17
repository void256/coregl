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


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

import java.io.*;
import java.lang.reflect.*;
import java.nio.*;
import java.util.*;
import java.util.logging.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import de.lessvoid.coregl.*;

public class CoreShaderLwjgl implements CoreShader {
	private final CoreCheckGL checkGL;

	private static final Logger log = Logger.getLogger(CoreShaderLwjgl.class.getName());
	private int program;
	private Hashtable<String, Integer> parameter = new Hashtable<String, Integer>();
	private FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
	private final String[] attributes;

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#vertexShader(java.lang.String)
	 */
	@Override
	public int vertexShader(final String filename) {
		return vertexShader(filename, getStream(filename));
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#vertexShader(java.io.File)
	 */
	@Override
	public int vertexShader(final File file) throws FileNotFoundException {
		return vertexShader(file.getName(), getStream(file));
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#vertexShader(java.io.InputStream)
	 */
	public int vertexShader(final String streamName, final InputStream ... sources) {
		return vertexShaderFromStream(streamName, sources);
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#vertexShader(int, java.lang.String)
	 */
	@Override
	public void vertexShader(final int shaderId, final String filename) {
		vertexShader(shaderId, filename, getStream(filename));
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#vertexShader(int, java.io.File)
	 */
	@Override
	public void vertexShader(final int shaderId, final File file) throws FileNotFoundException {
		vertexShader(shaderId, file.getName(), getStream(file));
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#vertexShader(int, java.io.InputStream)
	 */
	@Override
	public void vertexShader(final int shaderId, final String streamName, final InputStream source) {
		prepareShader(shaderId, streamName, source);
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#geometryShader(int, java.lang.String)
	 */
	@Override
	public void geometryShader(final int shaderId, final String filename) {
		geometryShader(shaderId, filename, getStream(filename));
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#geometryShader(java.lang.String)
	 */
	@Override
	public int geometryShader(final String filename) {
		return geometryShaderFromStream(filename, getStream(filename));
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#geometryShader(java.io.File)
	 */
	@Override
	public int geometryShader(final File file) throws FileNotFoundException {
		return geometryShader(file.getName(), getStream(file));
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#geometryShader(java.io.File, java.io.InputStream[])
	 */
	@Override
	public int geometryShader(final File file, final InputStream ... inputStreams) throws FileNotFoundException {
		InputStream[] sources = new InputStream[inputStreams.length + 1];
		System.arraycopy(inputStreams, 0, sources, 0, inputStreams.length);
		sources[sources.length - 1] = getStream(file);
		return geometryShader(file.getName(), sources);
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#geometryShader(java.lang.String, java.io.InputStream[])
	 */
	@Override
	public int geometryShader(final String streamName, final InputStream ... inputStreams) throws FileNotFoundException {
		return geometryShaderFromStream(streamName, inputStreams);
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#geometryShader(int, java.io.InputStream)
	 */
	@Override
	public void geometryShader(final int shaderId, final String streamName, InputStream source) {
		prepareShader(shaderId, streamName, source);
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#geometryShader(int, java.io.File)
	 */
	@Override
	public void geometryShader(final int shaderId, final File file) throws FileNotFoundException {
		geometryShader(shaderId, file.getName(), getStream(file));
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#fragmentShader(java.lang.String)
	 */
	@Override
	public int fragmentShader(final String filename) {
		return fragmentShader(filename, getStream(filename));
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#fragmentShader(java.io.File)
	 */
	@Override
	public int fragmentShader(final File file) throws FileNotFoundException {
		return fragmentShaderFromStream(file.getName(), getStream(file));
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#fragmentShader(java.lang.String, java.io.InputStream[])
	 */
	@Override
	public int fragmentShader(final String streamName, final InputStream ... inputStreams) {
		return fragmentShaderFromStream(streamName, inputStreams);
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#fragmentShader(int, java.lang.String)
	 */
	@Override
	public void fragmentShader(final int shaderId, final String filename) {
		fragmentShader(shaderId, filename, getStream(filename));
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#fragmentShader(int, java.io.File)
	 */
	@Override
	public void fragmentShader(final int shaderId, final File file) throws FileNotFoundException {
		fragmentShader(shaderId, file.getName(), getStream(file));
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#fragmentShader(int, java.io.InputStream)
	 */
	@Override
	public void fragmentShader(final int shaderId, final String streamName, final InputStream source) {
		prepareShader(shaderId, streamName, source);
	}

	private int vertexShaderFromStream(final String streamName, final InputStream ... sources) {
		int shaderId = glCreateShader(GL_VERTEX_SHADER);
		checkGLError("glCreateShader(GL_VERTEX_SHADER)");
		prepareShader(shaderId, streamName, sources);
		glAttachShader(program, shaderId);
		checkGLError("glAttachShader");
		return shaderId;
	}

	private int geometryShaderFromStream(final String streamName, final InputStream ... sources) {
		int shaderId = glCreateShader(GL_GEOMETRY_SHADER);
		checkGLError("glCreateShader(GL_GEOMETRY_SHADER)");
		prepareShader(shaderId, streamName, sources);
		glAttachShader(program, shaderId);
		checkGLError("glAttachShader");
		return shaderId;
	}

	private int fragmentShaderFromStream(final String streamName, final InputStream ... sources) {
		int shaderId = glCreateShader(GL_FRAGMENT_SHADER);
		checkGLError("glCreateShader(GL_FRAGMENT_SHADER)");
		prepareShader(shaderId, streamName, sources);
		glAttachShader(program, shaderId);
		checkGLError("glAttachShader");
		return shaderId;
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#link()
	 */
	@Override
	public void link() {
		for (int i=0; i<attributes.length; i++) {
			glBindAttribLocation(program, i, attributes[i]);
			checkGLError("glBindAttribLocation (" + attributes[i] + ")");
		}

		glLinkProgram(program);
		checkGLError("glLinkProgram");

		if (glGetProgram(program, GL_LINK_STATUS) != GL_TRUE) {
			log.warning("link error: " + glGetProgramInfoLog(program, 1024));
			checkGLError("glGetProgramInfoLog");
		}
		checkGLError("glGetProgram");
	}

	// BEGIN TEMPORARY TEST METHODS //
	void setDirectUniform2i(final String name, final int...value) {
		glUniform2i(getLocation(name), value[0], value[1]);
	}

	void setDirectUniformf(final String name, final float...values) {
		switch(values.length) {
		case 1:
			glUniform1f(getLocation(name), values[0]);
			break;
		case 2:
			glUniform2f(getLocation(name), values[0], values[1]);
			break;
		case 3:
			glUniform3f(getLocation(name), values[0], values[1], values[2]);
			break;
		case 4:
			glUniform4f(getLocation(name), values[0], values[1], values[2], values[3]);
			break;
		}
	}
	
	void setDirectUniformMatrix4f(final String name, final FloatBuffer matBuffer) {
		glUniformMatrix4(getLocation(name), false, matBuffer);
		checkGLError("glUniformMatrix4");
	}
	// END TEMPORARY TEST METHODS //

	public void setUniformi(final String name, final int...values) {
		Object[] intObjs = new Integer[values.length];
		for(int i=0; i < values.length; i++)
			intObjs[i] = values[i];
		setUniform(name, UniformTypeLwjgl.INT, intObjs);
	}

	public void setUniformf(final String name, final float...values) {
		Object[] intObjs = new Float[values.length];
		for(int i=0; i < values.length; i++)
			intObjs[i] = values[i];
		setUniform(name, UniformTypeLwjgl.FLOAT, intObjs);
	}

	public void setUniformd(final String name, final double...values) {
		Object[] intObjs = new Double[values.length];
		for(int i=0; i < values.length; i++)
			intObjs[i] = values[i];
		setUniform(name, UniformTypeLwjgl.DOUBLE, intObjs);
	}

	@Override
	public void setUniformiv(final String name, final int componentNum, final int... values) {
		IntBuffer buff = BufferUtils.createIntBuffer(values.length);
		buff.put(values);
		buff.flip();
		setUniformv(name, componentNum, UniformTypeLwjgl.INT, buff);
	}

	@Override
	public void setUniformiv(final String name, final int componentNum, final IntBuffer values) {
		setUniformv(name, componentNum, UniformTypeLwjgl.INT, values);
	}

	@Override
	public void setUniformfv(final String name, final int componentNum, final float... values) {
		FloatBuffer buff = BufferUtils.createFloatBuffer(values.length);
		buff.put(values);
		buff.flip();
		setUniformv(name, componentNum, UniformTypeLwjgl.FLOAT, buff);

	}

	@Override
	public void setUniformfv(final String name, final int componentNum, final FloatBuffer values) {
		setUniformv(name, componentNum, UniformTypeLwjgl.INT, values);

	}

	@Override
	public void setUniformdv(final String name, final int componentNum, final double... values) {
		DoubleBuffer buff = BufferUtils.createDoubleBuffer(values.length);
		buff.put(values);
		buff.flip();
		setUniformv(name, componentNum, UniformTypeLwjgl.FLOAT, buff);

	}

	@Override
	public void setUniformdv(final String name, final int componentNum, final DoubleBuffer values) {
		setUniformv(name, componentNum, UniformTypeLwjgl.INT, values);
	}

	@Override
	public void setUniformMatrix(final String name, final int componentNum, final float... values) {
		matBuffer.clear();
		matBuffer.put(values);
		matBuffer.flip();
		setUniformMatrix(name, componentNum, UniformTypeLwjgl.FLOAT, matBuffer);
	}

	@Override
	public void setUniformMatrix(final String name, final int componentNum,
			FloatBuffer values) {
		setUniformMatrix(name, componentNum, UniformTypeLwjgl.FLOAT, values);
	}

	private void setUniform(final String name, final UniformTypeLwjgl type, final Object...values) {
		int loc = getLocation(name);
		String method = "glUniform"+values.length+type.suffix;
		try {
			switch(values.length) {
			case 1:
				Method m = GL20.class.getMethod(method, int.class, type.value);
				m.setAccessible(true);
				m.invoke(null, loc, values[0]);
				break;
			case 2:
				m = GL20.class.getMethod(method, int.class, type.value, type.value);
				m.setAccessible(true);
				m.invoke(null, loc, values[0], values[1]);
				break;
			case 3:
				m = GL20.class.getMethod(method, int.class, type.value, type.value, type.value);
				m.setAccessible(true);
				m.invoke(null, loc, values[0], values[1], values[2]);
				break;
			case 4:
				m = GL20.class.getMethod(method, int.class, type.value, type.value, type.value, type.value);
				m.setAccessible(true);
				m.invoke(null, loc, values[0], values[1], values[2], values[3]);
				break;
			default:
				throw(new IllegalArgumentException("illegal number of values supplied to "
						+ "setUniform"+type.suffix));
			}
		} catch (NoSuchMethodException e) {
			throw(new IllegalArgumentException("failed to locate set uniform method: " + method));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		checkGLError(method);
	}

	private void setUniformv(final String name, final int componentNum, 
			final UniformTypeLwjgl type, final Buffer data) {
		int loc = getLocation(name);
		if(componentNum < 1 || componentNum > 4)
			throw(new IllegalArgumentException("illegal number of compoments for setUniform"+type.suffix+"v"));
		String method = "glUniform"+componentNum;
		try {
			Method m = GL20.class.getMethod(method, int.class, type.buffer);
			m.setAccessible(true);
			m.invoke(null, loc, data);
		} catch (NoSuchMethodException e) {
			throw(new IllegalArgumentException("failed to locate set uniform method: " + method));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		checkGLError(method);
	}

	private void setUniformMatrix(final String name, final int componentNum,
			final UniformTypeLwjgl type, final Buffer data) {
		int loc = getLocation(name);
		if(componentNum < 2 || componentNum > 4)
			throw(new IllegalArgumentException("illegal number of compoments for setUniformMatrix"));
		String method = "glUniformMatrix"+componentNum;
		try {
			Method m = GL20.class.getMethod(method, int.class, boolean.class, type.buffer);
			m.setAccessible(true);
			m.invoke(null, loc, false, data);
		} catch (NoSuchMethodException e) {
			throw(new IllegalArgumentException("failed to locate set uniform method: " + method));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		checkGLError(method);
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#getAttribLocation(java.lang.String)
	 */
	@Override
	public int getAttribLocation(final String name) {
		int result = glGetAttribLocation(program, name);
		checkGLError("glGetAttribLocation");
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#bindAttribLocation(java.lang.String, int)
	 */
	@Override
	public void bindAttribLocation(final String name, final int index) {
		glBindAttribLocation(program, index, name);
		checkGLError("glBindAttribLocation");
	}

	public static class UniformBlockInfo {
		public String name;
		int offset;
		int arrayStride;
		int matrixStride;

		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("[");
			builder.append("name: ").append(name).append(", ");
			builder.append("offset: ").append(offset).append(", ");
			builder.append("arrayStride: ").append(arrayStride).append(", ");
			builder.append("matrixStride: ").append(matrixStride);
			builder.append("]");
			return builder.toString();
		}
	}

	public Map<String, UniformBlockInfo> getUniformIndices(final String ... uniformNames) {
		Map<String, UniformBlockInfo> result = new Hashtable<String, UniformBlockInfo>();

		IntBuffer intBuffer = BufferUtils.createIntBuffer(uniformNames.length);
		glGetUniformIndices(program, uniformNames, intBuffer);

		IntBuffer uniformOffsets = BufferUtils.createIntBuffer(uniformNames.length);
		glGetActiveUniforms(program, intBuffer, GL_UNIFORM_OFFSET, uniformOffsets);

		IntBuffer arrayStrides = BufferUtils.createIntBuffer(uniformNames.length);
		glGetActiveUniforms(program, intBuffer, GL_UNIFORM_ARRAY_STRIDE, arrayStrides);

		IntBuffer matrixStrides = BufferUtils.createIntBuffer(uniformNames.length);
		glGetActiveUniforms(program, intBuffer, GL_UNIFORM_MATRIX_STRIDE, matrixStrides);

		checkGL.checkGLError("getUniformIndices");

		for (int i=0; i<uniformNames.length; i++) {
			UniformBlockInfo blockInfo = new UniformBlockInfo();
			blockInfo.name = uniformNames[i];
			blockInfo.offset = uniformOffsets.get(i);
			blockInfo.arrayStride = arrayStrides.get(i);
			blockInfo.matrixStride = matrixStrides.get(i);
			result.put(blockInfo.name, blockInfo);
		}

		return result;
	}

	public void uniformBlockBinding(final String name, final int uniformBlockBinding) {
		int uniformBlockIndex = glGetUniformBlockIndex(program, name);
		checkGL.checkGLError("glGetUniformBlockIndex");

		glUniformBlockBinding(program, uniformBlockIndex, uniformBlockBinding);
		checkGL.checkGLError("glUniformBlockBinding");
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#activate()
	 */
	@Override
	public void activate() {
		glUseProgram(program);
		checkGLError("glUseProgram");
	}

	CoreShaderLwjgl(final CoreCheckGL checkGL, final String ... vertexAttributes) {
		this.checkGL = checkGL;
		this.attributes = vertexAttributes;
		this.program = glCreateProgram();
		checkGLError("glCreateProgram");
	}

	private int registerParameter(final String name) {
		int location = getUniform(name);
		parameter.put(name, location);
		return location;
	}

	private int getLocation(final String name) {
		Integer value = parameter.get(name);
		if (value == null) {
			return registerParameter(name);
		}
		return value;
	}

	private int getUniform(final String uniformName) {
		try {
			byte[] bytes = uniformName.getBytes("ISO-8859-1");
			ByteBuffer name = BufferUtils.createByteBuffer(bytes.length + 1);
			name.put(bytes);
			name.put((byte)0x00);
			name.rewind();
			int result = glGetUniformLocation(program, name);
			checkGLError("glGetUniformLocation for [" + uniformName + "] failed");
			log.fine(getLoggingPrefix() + "glUniformLocation for [" + uniformName + "] = [" + result + "]");
			return result;
		} catch (UnsupportedEncodingException e) {
			log.log(Level.WARNING, getLoggingPrefix() + e.getMessage(), e);
			return -1;
		}
	}

	private void prepareShader(final int shaderId, final String name, final InputStream ... sources) {
		try {
			glShaderSource(shaderId, loadShader(sources));
			checkGLError("glShaderSource");
		} catch (IOException e) {
			throw new CoreGLException(e);
		}

		glCompileShader(shaderId);
		checkGLError("glCompileShader");

		if (glGetShader(shaderId, GL_COMPILE_STATUS) == GL_FALSE) {
			log.warning("'" + name + "' compile error: " + glGetShaderInfoLog(shaderId, 1024));
		}

		printLogInfo(shaderId);
		checkGLError(String.valueOf(shaderId));
	}

	private ByteBuffer loadShader(final InputStream ... sources) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		for (InputStream source : sources) {
			out.write(read(source));
		}

		byte[] data = out.toByteArray();
		ByteBuffer result = BufferUtils.createByteBuffer(data.length);
		result.put(data);
		result.flip();
		return result;
	}

	private byte[] read(final InputStream dataStream) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		byte[] readBuffer = new byte[1024];
		int bytesRead = -1;
		try {
			while ((bytesRead = dataStream.read(readBuffer)) > 0) {
				out.write(readBuffer, 0, bytesRead);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				dataStream.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return out.toByteArray();
	}

	private void printLogInfo(final int obj) {
		ByteBuffer infoLog = BufferUtils.createByteBuffer(2048);
		IntBuffer lengthBuffer = BufferUtils.createIntBuffer(1);
		glGetShaderInfoLog(obj, lengthBuffer, infoLog);
		checkGLError("glGetShaderInfoLog");

		byte[] infoBytes = new byte[lengthBuffer.get()];
		infoLog.get(infoBytes);
		if (infoBytes.length == 0) {
			return;
		}
		try {
			log.info(getLoggingPrefix() + "Info log:\n" + new String(infoBytes, "ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		checkGLError("printLogInfo");
	}

	private void checkGLError(final String message) {
		checkGL.checkGLError(getLoggingPrefix() + message);
	}

	private String getLoggingPrefix() {
		return "[" + program + "] ";
	}

	private InputStream getStream(final File file) throws FileNotFoundException {
		log.fine("loading shader file [" + file + "]");
		return new ByteArrayInputStream(read(new FileInputStream(file)));
	}

	private InputStream getStream(final String filename) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
	}

	enum UniformTypeLwjgl {
		INT("i", int.class, int[].class, IntBuffer.class),
		FLOAT("f", float.class, float[].class, FloatBuffer.class),
		DOUBLE("d", double.class, double[].class, DoubleBuffer.class);

		String suffix;
		Class<?> value, array, buffer;

		UniformTypeLwjgl(String suffix, Class<?> value, Class<?> array, Class<?> buffer) {
			this.suffix = suffix;
			this.array = array;
			this.buffer = buffer;
			this.value = value;
		}
	}
}
