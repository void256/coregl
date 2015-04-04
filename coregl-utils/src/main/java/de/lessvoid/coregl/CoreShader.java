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

import java.io.*;
import java.lang.reflect.*;
import java.nio.*;
import java.util.*;
import java.util.logging.*;

import de.lessvoid.coregl.spi.CoreGL;

/**
 * Helper class that represents a shader (actually the combination of a vertex
 * and a fragment shader - what GL actually calls a program).
 * @author void
 */
public class CoreShader {

	private static final Logger log = Logger.getLogger(CoreShader.class.getName());
	private int program;
	private Hashtable<String, Integer> parameter = new Hashtable<String, Integer>();
	private FloatBuffer matBuffer;
	private final String[] attributes;

	private final CoreGL gl;

	/**
	 * Create a new Shader.
	 * @return the new CoreShader instance
	 */
	public static CoreShader createShader(final CoreGL gl) {
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
	public static CoreShader createShaderWithVertexAttributes(final CoreGL gl, String ... vertexAttributes) {
		return new CoreShader(gl, vertexAttributes);
	}

	CoreShader(final CoreGL gl, final String ... vertexAttributes) {
		this.gl = gl;
		this.attributes = vertexAttributes;
		this.program = gl.glCreateProgram();
		checkGLError("glCreateProgram");
	}

	public int vertexShader(final String filename) {
		return vertexShader(filename, getStream(filename));
	}

	public int vertexShader(final File file) throws FileNotFoundException {
		return vertexShader(file.getName(), getStream(file));
	}

	public int vertexShader(final String streamName, final InputStream ... sources) {
		return vertexShaderFromStream(streamName, sources);
	}

	public void vertexShader(final int shaderId, final String filename) {
		vertexShader(shaderId, filename, getStream(filename));
	}

	public void vertexShader(final int shaderId, final File file) throws FileNotFoundException {
		vertexShader(shaderId, file.getName(), getStream(file));
	}

	public void vertexShader(final int shaderId, final String streamName, final InputStream source) {
		prepareShader(shaderId, streamName, source);
	}

	public void geometryShader(final int shaderId, final String filename) {
		geometryShader(shaderId, filename, getStream(filename));
	}

	public int geometryShader(final String filename) {
		return geometryShaderFromStream(filename, getStream(filename));
	}

	public int geometryShader(final File file) throws FileNotFoundException {
		return geometryShader(file.getName(), getStream(file));
	}

	public int geometryShader(final File file, final InputStream ... inputStreams) throws FileNotFoundException {
		InputStream[] sources = new InputStream[inputStreams.length + 1];
		System.arraycopy(inputStreams, 0, sources, 0, inputStreams.length);
		sources[sources.length - 1] = getStream(file);
		return geometryShader(file.getName(), sources);
	}

	public int geometryShader(final String streamName, final InputStream ... inputStreams) throws FileNotFoundException {
		return geometryShaderFromStream(streamName, inputStreams);
	}

	public void geometryShader(final int shaderId, final String streamName, InputStream source) {
		prepareShader(shaderId, streamName, source);
	}

	public void geometryShader(final int shaderId, final File file) throws FileNotFoundException {
		geometryShader(shaderId, file.getName(), getStream(file));
	}

	public int fragmentShader(final String filename) {
		return fragmentShader(filename, getStream(filename));
	}

	public int fragmentShader(final File file) throws FileNotFoundException {
		return fragmentShaderFromStream(file.getName(), getStream(file));
	}

	public int fragmentShader(final String streamName, final InputStream ... inputStreams) {
		return fragmentShaderFromStream(streamName, inputStreams);
	}

	public void fragmentShader(final int shaderId, final String filename) {
		fragmentShader(shaderId, filename, getStream(filename));
	}

	public void fragmentShader(final int shaderId, final File file) throws FileNotFoundException {
		fragmentShader(shaderId, file.getName(), getStream(file));
	}

	public void fragmentShader(final int shaderId, final String streamName, final InputStream source) {
		prepareShader(shaderId, streamName, source);
	}

	private int vertexShaderFromStream(final String streamName, final InputStream ... sources) {
		int shaderId = gl.glCreateShader(gl.GL_VERTEX_SHADER());
		checkGLError("glCreateShader(GL_VERTEX_SHADER)");
		prepareShader(shaderId, streamName, sources);
		gl.glAttachShader(program, shaderId);
		checkGLError("glAttachShader");
		return shaderId;
	}

	private int geometryShaderFromStream(final String streamName, final InputStream ... sources) {
		int shaderId = gl.glCreateShader(gl.GL_GEOMETRY_SHADER());
		checkGLError("glCreateShader(GL_GEOMETRY_SHADER)");
		prepareShader(shaderId, streamName, sources);
		gl.glAttachShader(program, shaderId);
		checkGLError("glAttachShader");
		return shaderId;
	}

	private int fragmentShaderFromStream(final String streamName, final InputStream ... sources) {
		int shaderId = gl.glCreateShader(gl.GL_FRAGMENT_SHADER());
		checkGLError("glCreateShader(GL_FRAGMENT_SHADER)");
		prepareShader(shaderId, streamName, sources);
		gl.glAttachShader(program, shaderId);
		checkGLError("glAttachShader");
		return shaderId;
	}

	public void link() {
		for (int i=0; i<attributes.length; i++) {
			gl.glBindAttribLocation(program, i, attributes[i]);
			checkGLError("glBindAttribLocation (" + attributes[i] + ")");
		}

		gl.glLinkProgram(program);
		checkGLError("glLinkProgram");

		IntBuffer params = gl.getUtil().createIntBuffer(1);
		gl.glGetProgramiv(program, gl.GL_LINK_STATUS(), params);
		if (params.get(0) != gl.GL_TRUE()) {
			log.warning("link error: " + gl.glGetProgramInfoLog(program));
			checkGLError("glGetProgramInfoLog");
		}
		checkGLError("glGetProgram");
	}

	public void setUniformi(final String name, final int...values) {
		setUniform(name, UniformType.INT, toObjectArray(values));
	}

	public void setUniformf(final String name, final float...values) {
		setUniform(name, UniformType.FLOAT, toObjectArray(values));
	}

	public void setUniformd(final String name, final double...values) {
		setUniform(name, UniformType.DOUBLE, toObjectArray(values));
	}

	public void setUniformiv(final String name, final int componentNum, final int... values) {
		IntBuffer buff = gl.getUtil().createIntBuffer(values.length);
		buff.put(values);
		buff.flip();
		setUniformv(name, componentNum, UniformType.INT, buff);
	}

	public void setUniformiv(final String name, final int componentNum, final IntBuffer values) {
		setUniformv(name, componentNum, UniformType.INT, values);
	}

	public void setUniformfv(final String name, final int componentNum, final float... values) {
		FloatBuffer buff = gl.getUtil().createFloatBuffer(values.length);
		buff.put(values);
		buff.flip();
		setUniformv(name, componentNum, UniformType.FLOAT, buff);
	}

	public void setUniformfv(final String name, final int componentNum, final FloatBuffer values) {
		setUniformv(name, componentNum, UniformType.FLOAT, values);

	}

	public void setUniformdv(final String name, final int componentNum, final double... values) {
		DoubleBuffer buff = gl.getUtil().createDoubleBuffer(values.length);
		buff.put(values);
		buff.flip();
		setUniformv(name, componentNum, UniformType.DOUBLE, buff);

	}

	public void setUniformdv(final String name, final int componentNum, final DoubleBuffer values) {
		setUniformv(name, componentNum, UniformType.DOUBLE, values);
	}

	public void setUniformMatrix(final String name, final int componentNum, final float... values) {
		if(matBuffer == null)
			gl.getUtil().createFloatBuffer(16);

		matBuffer.clear();
		matBuffer.put(values);
		matBuffer.flip();
		setUniformMatrix(name, componentNum, UniformType.FLOAT, matBuffer);
	}

	public void setUniformMatrix(final String name, final int componentNum,
			FloatBuffer values) {
		setUniformMatrix(name, componentNum, UniformType.FLOAT, values);
	}

	private void setUniform(final String name, final UniformType type, final Object...values) {
		int loc = getLocation(name);
		String method = "glUniform"+values.length+type.suffix;
		try {
			switch(values.length) {
			case 1:
				Method m = CoreGL.class.getMethod(method, int.class, type.value);
				m.setAccessible(true);
				m.invoke(gl, loc, values[0]);
				break;
			case 2:
				m = CoreGL.class.getMethod(method, int.class, type.value, type.value);
				m.setAccessible(true);
				m.invoke(gl, loc, values[0], values[1]);
				break;
			case 3:
				m = CoreGL.class.getMethod(method, int.class, type.value, type.value, type.value);
				m.setAccessible(true);
				m.invoke(gl, loc, values[0], values[1], values[2]);
				break;
			case 4:
				m = CoreGL.class.getMethod(method, int.class, type.value, type.value, type.value, type.value);
				m.setAccessible(true);
				m.invoke(gl, loc, values[0], values[1], values[2], values[3]);
				break;
			default:
				throw(new IllegalArgumentException("illegal number of values supplied to "
						+ "setUniform"+type.suffix));
			}
		} catch (NoSuchMethodException e) {
			throw(new CoreGLException("failed to locate set uniform method: " + method));
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
			final UniformType type, final Buffer data) {
		int loc = getLocation(name);
		if(componentNum < 1 || componentNum > 4)
			throw(new IllegalArgumentException("illegal number of compoments for setUniform"+type.suffix+"v"));
		String method = "glUniform"+componentNum+type.suffix+"v";
		try {
			Method m = CoreGL.class.getMethod(method, int.class, type.buffer);
			m.setAccessible(true);
			m.invoke(gl, loc, data);
		} catch (NoSuchMethodException e) {
			throw(new CoreGLException("failed to locate set uniform method: " + method));
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
			final UniformType type, final Buffer data) {
		int loc = getLocation(name);
		if(componentNum < 2 || componentNum > 4)
			throw(new IllegalArgumentException("illegal number of compoments for setUniformMatrix"));
		String method = "glUniformMatrix"+componentNum;
		try {
			Method m = CoreGL.class.getMethod(method, int.class, boolean.class, type.buffer);
			m.setAccessible(true);
			m.invoke(gl, loc, false, data);
		} catch (NoSuchMethodException e) {
			throw(new CoreGLException("failed to locate set uniform method: " + method));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		checkGLError(method);
	}

	public int getAttribLocation(final String name) {
		int result = gl.glGetAttribLocation(program, name);
		checkGLError("glGetAttribLocation");
		return result;
	}

	public void bindAttribLocation(final String name, final int index) {
		gl.glBindAttribLocation(program, index, name);
		checkGLError("glBindAttribLocation");
	}

	public Map<String, UniformBlockInfo> getUniformIndices(final String ... uniformNames) {
		Map<String, UniformBlockInfo> result = new Hashtable<String, UniformBlockInfo>();

		IntBuffer intBuffer = gl.getUtil().createIntBuffer(uniformNames.length);
		gl.glGetUniformIndices(program, uniformNames, intBuffer);

		IntBuffer uniformOffsets = gl.getUtil().createIntBuffer(uniformNames.length);
		gl.glGetActiveUniforms(program, uniformNames.length, intBuffer, gl.GL_UNIFORM_OFFSET(), uniformOffsets);

		IntBuffer arrayStrides = gl.getUtil().createIntBuffer(uniformNames.length);
		gl.glGetActiveUniforms(program, uniformNames.length, intBuffer, gl.GL_UNIFORM_ARRAY_STRIDE(), arrayStrides);

		IntBuffer matrixStrides = gl.getUtil().createIntBuffer(uniformNames.length);
		gl.glGetActiveUniforms(program, uniformNames.length, intBuffer, gl.GL_UNIFORM_MATRIX_STRIDE(), matrixStrides);

		checkGLError("getUniformIndices");

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
		int uniformBlockIndex = gl.glGetUniformBlockIndex(program, name);
		checkGLError("glGetUniformBlockIndex");

		gl.glUniformBlockBinding(program, uniformBlockIndex, uniformBlockBinding);
		checkGLError("glUniformBlockBinding");
	}

	public void activate() {
		gl.glUseProgram(program);
		checkGLError("glUseProgram");
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
		int result = gl.glGetUniformLocation(program, uniformName);
		checkGLError("glGetUniformLocation for [" + uniformName + "] failed");
		log.fine(getLoggingPrefix() + "glUniformLocation for [" + uniformName + "] = [" + result + "]");
		return result;
	}

	private void prepareShader(final int shaderId, final String name, final InputStream ... sources) {
		try {
			gl.glShaderSource(shaderId, loadShader(sources));
			checkGLError("glShaderSource");
		} catch (IOException e) {
			throw new CoreGLException(e);
		}

		gl.glCompileShader(shaderId);
		checkGLError("glCompileShader");

		IntBuffer ret = gl.getUtil().createIntBuffer(1);
		gl.glGetShaderiv(shaderId, gl.GL_COMPILE_STATUS(), ret);
		if (ret.get(0) == gl.GL_FALSE()) {
			log.warning("'" + name + "' compile error: " + gl.glGetShaderInfoLog(shaderId));
		}

		printLogInfo(shaderId);
		checkGLError(String.valueOf(shaderId));
	}

	private String loadShader(final InputStream ... sources) throws IOException {
		StringBuilder srcbuff = new StringBuilder();
		for (InputStream source : sources) {
			InputStreamReader streamReader = new InputStreamReader(source);
			BufferedReader buffReader = new BufferedReader(streamReader);
			String nextLine = null;
			while((nextLine = buffReader.readLine()) != null) {
				srcbuff.append(nextLine + "\n");
			}
			buffReader.close();
		}

		return srcbuff.toString();
	}

	private void printLogInfo(final int obj) {
		String logInfoMsg = gl.glGetShaderInfoLog(obj);
		checkGLError("glGetShaderInfoLog");
		if (!logInfoMsg.isEmpty()) {
			log.info(getLoggingPrefix() + "Info log:\n" + logInfoMsg);
		}
		checkGLError("printLogInfo");
	}

	private void checkGLError(final String message) {
		gl.checkGLError(getLoggingPrefix() + message);
	}

	private String getLoggingPrefix() {
		return "[" + program + "] ";
	}

	private InputStream getStream(final File file) throws FileNotFoundException {
		log.fine("loading shader file [" + file + "]");
		return new FileInputStream(file);
	}

	private InputStream getStream(final String filename) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
	}

	private Object[] toObjectArray(int[] ints) {
		Object[] intObjs = new Integer[ints.length];
		for(int i=0; i < intObjs.length; i++)
			intObjs[i] = ints[i];
		return intObjs;
	}

	private Object[] toObjectArray(float[] floats) {
		Object[] intObjs = new Float[floats.length];
		for(int i=0; i < intObjs.length; i++)
			intObjs[i] = floats[i];
		return intObjs;
	}

	private Object[] toObjectArray(double[] doubles) {
		Object[] intObjs = new Double[doubles.length];
		for(int i=0; i < intObjs.length; i++)
			intObjs[i] = doubles[i];
		return intObjs;
	}

	enum UniformType {
		INT("i", int.class, IntBuffer.class),
		FLOAT("f", float.class, FloatBuffer.class),
		DOUBLE("d", double.class, DoubleBuffer.class);

		String suffix;
		Class<?> value, buffer;

		UniformType(String suffix, Class<?> value, Class<?> buffer) {
			this.suffix = suffix;
			this.buffer = buffer;
			this.value = value;
		}
	}
}
