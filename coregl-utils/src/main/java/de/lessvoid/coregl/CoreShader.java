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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import de.lessvoid.coregl.spi.CoreGL;

/**
 * Helper class that represents a shader (actually the combination of a vertex
 * and a fragment shader - what GL actually calls a program).
 *
 * @author void
 * @author Brian Groenke (groenke.5@osu.edu)
 */
public class CoreShader {

  private final CoreLogger log;
  private final int program;
  private final Map<String, Integer> parameter = new HashMap<String, Integer>();
  private FloatBuffer matBuffer;
  private final String[] attributes;

  private final CoreGL gl;

  /**
   * Create a new Shader.
   *
   * @return the new CoreShader instance
   */
  public static CoreShader createShader(final CoreGL gl) {
    return new CoreShader(gl);
  }

  /**
   * Create a new Shader with the given vertex attributes automatically bind to
   * the generic attribute indices in ascending order beginning with 0. This
   * method can be used when you want to control the vertex attribute binding on
   * your own.
   *
   * @param vertexAttributes
   *          the name of the vertex attribute. The first String gets generic
   *          attribute index 0. the second String gets generic attribute index
   *          1 and so on.
   * @return the CoreShader instance
   */
  public static CoreShader createShaderWithVertexAttributes(final CoreGL gl, final String... vertexAttributes) {
    return new CoreShader(gl, vertexAttributes);
  }

  CoreShader(final CoreGL gl, final String... vertexAttributes) {
    this.gl = gl;
    attributes = vertexAttributes;
    program = gl.glCreateProgram();
    final String progIdStr = "[" + program + "]";
    log = CoreLogger.getCoreLogger(getClass().getName() + progIdStr);
    log.setLoggingPrefix(progIdStr);
    gl.checkGLError("glCreateProgram");
  }

  public int vertexShader(final String filename) {
    return vertexShader(filename, getStream(filename));
  }

  public int vertexShader(final File file) throws FileNotFoundException {
    return vertexShader(file.getName(), getStream(file));
  }

  public int vertexShader(final String streamName, final InputStream... sources) {
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

  public int geometryShader(final File file, final InputStream... inputStreams) throws FileNotFoundException {
    final InputStream[] sources = new InputStream[inputStreams.length + 1];
    System.arraycopy(inputStreams, 0, sources, 0, inputStreams.length);
    sources[sources.length - 1] = getStream(file);
    return geometryShader(file.getName(), sources);
  }

  public int geometryShader(final String streamName, final InputStream... inputStreams) throws FileNotFoundException {
    return geometryShaderFromStream(streamName, inputStreams);
  }

  public void geometryShader(final int shaderId, final String streamName, final InputStream source) {
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

  public int fragmentShader(final String streamName, final InputStream... inputStreams) {
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

  private int vertexShaderFromStream(final String streamName, final InputStream... sources) {
    final int shaderId = gl.glCreateShader(gl.GL_VERTEX_SHADER());
    gl.checkGLError("glCreateShader(GL_VERTEX_SHADER)");
    prepareShader(shaderId, streamName, sources);
    gl.glAttachShader(program, shaderId);
    gl.checkGLError("glAttachShader");
    return shaderId;
  }

  private int geometryShaderFromStream(final String streamName, final InputStream... sources) {
    final int shaderId = gl.glCreateShader(gl.GL_GEOMETRY_SHADER());
    gl.checkGLError("glCreateShader(GL_GEOMETRY_SHADER)");
    prepareShader(shaderId, streamName, sources);
    gl.glAttachShader(program, shaderId);
    gl.checkGLError("glAttachShader");
    return shaderId;
  }

  private int fragmentShaderFromStream(final String streamName, final InputStream... sources) {
    final int shaderId = gl.glCreateShader(gl.GL_FRAGMENT_SHADER());
    gl.checkGLError("glCreateShader(GL_FRAGMENT_SHADER)");
    prepareShader(shaderId, streamName, sources);
    gl.glAttachShader(program, shaderId);
    gl.checkGLError("glAttachShader");
    return shaderId;
  }

  public void link() {
    for (int i = 0; i < attributes.length; i++) {
      gl.glBindAttribLocation(program, i, attributes[i]);
      log.checkGLError(gl, "glBindAttribLocation ({})", attributes[i]);
    }

    gl.glLinkProgram(program);
    gl.checkGLError("glLinkProgram");

    final IntBuffer params = gl.getUtil().createIntBuffer(1);
    gl.glGetProgramiv(program, gl.GL_LINK_STATUS(), params);
    if (params.get(0) != gl.GL_TRUE()) {
      log.warn("link error: {}", gl.glGetProgramInfoLog(program));
      gl.checkGLError("glGetProgramInfoLog");
    }
    gl.checkGLError("glGetProgram");
  }

  public void setUniformi(final String name, final int... values) {
    final int loc = getLocation(name);
    switch (values.length) {
    case 1:
      gl.glUniform1i(loc, values[0]);
      gl.checkGLError("glUniform1i");
      break;
    case 2:
      gl.glUniform2i(loc, values[0], values[1]);
      gl.checkGLError("glUniform2i");
      break;
    case 3:
      gl.glUniform3i(loc, values[0], values[1], values[2]);
      gl.checkGLError("glUniform3i");
      break;
    case 4:
      gl.glUniform4i(loc, values[0], values[1], values[2], values[3]);
      gl.checkGLError("glUniform4i");
      break;
    default:
      throw new IllegalArgumentException(String.format("Unsupported number of value arguments: %d", values.length));
    }
  }

  public void setUniformf(final String name, final float... values) {
    final int loc = getLocation(name);
    switch (values.length) {
    case 1:
      gl.glUniform1f(loc, values[0]);
      gl.checkGLError("glUniform1f");
      break;
    case 2:
      gl.glUniform2f(loc, values[0], values[1]);
      gl.checkGLError("glUniform2f");
      break;
    case 3:
      gl.glUniform3f(loc, values[0], values[1], values[2]);
      gl.checkGLError("glUniform3f");
      break;
    case 4:
      gl.glUniform4f(loc, values[0], values[1], values[2], values[3]);
      gl.checkGLError("glUniform4f");
      break;
    default:
      throw new IllegalArgumentException("Unsupported number of value arguments: " + values.length);
    }
  }

  public void setUniformiv(final String name, final int componentNum, final int... values) {
    final IntBuffer buff = gl.getUtil().createIntBuffer(values.length);
    buff.put(values);
    buff.flip();
    setUniformiv(name, componentNum, buff);
  }

  public void setUniformiv(final String name, final int componentNum, final IntBuffer values) {
    final int loc = getLocation(name);
    switch (componentNum) {
    case 1:
      gl.glUniform1iv(loc, values);
      gl.checkGLError("glUniform1iv");
      break;
    case 2:
      gl.glUniform2iv(loc, values);
      gl.checkGLError("glUniform2iv");
      break;
    case 3:
      gl.glUniform3iv(loc, values);
      gl.checkGLError("glUniform3iv");
      break;
    case 4:
      gl.glUniform4iv(loc, values);
      gl.checkGLError("glUniform4iv");
      break;
    default:
      throw new IllegalArgumentException("Unsupported component count value: " + componentNum);
    }
  }

  public void setUniformfv(final String name, final int componentNum, final float... values) {
    final FloatBuffer buff = gl.getUtil().createFloatBuffer(values.length);
    buff.put(values);
    buff.flip();
    setUniformfv(name, componentNum, buff);
  }

  public void setUniformfv(final String name, final int componentNum, final FloatBuffer values) {
    final int loc = getLocation(name);
    switch (componentNum) {
    case 1:
      gl.glUniform1fv(loc, values);
      gl.checkGLError("glUniform1fv");
      break;
    case 2:
      gl.glUniform2fv(loc, values);
      gl.checkGLError("glUniform2fv");
      break;
    case 3:
      gl.glUniform3fv(loc, values);
      gl.checkGLError("glUniform3fv");
      break;
    case 4:
      gl.glUniform4fv(loc, values);
      gl.checkGLError("glUniform4fv");
      break;
    default:
      throw new IllegalArgumentException("Unsupported component count value: " + componentNum);
    }
  }

  public void setUniformMatrix(final String name, final int componentNum, final float... values) {
    if (matBuffer == null) matBuffer = gl.getUtil().createFloatBuffer(16);

    matBuffer.clear();
    matBuffer.put(values);
    matBuffer.flip();
    setUniformMatrix(name, componentNum, matBuffer);
  }

  /**
   * Set uniform matrix of dimension 'n x n' where n = componentNum
   *
   * @param name
   *          name of the GLSL uniform variable
   * @param componentNum
   *          matrix dimension 'n'
   * @param values
   */
  public void setUniformMatrix(final String name, final int componentNum, final FloatBuffer values) {
    setUniformMatrix(name, componentNum, componentNum, values);
  }

  /**
   * Set a uniform matrix of dimension 'n x m'.
   *
   * @param name
   *          name of the GLSL uniform variable
   * @param n
   *          number of columns in the matrix
   * @param m
   *          number of rows in the matrix
   * @param values
   */
  public void setUniformMatrix(final String name, final int n, final int m, final FloatBuffer values) {
    final int loc = getLocation(name);
    final UniformMatrixType type = UniformMatrixType.typeFor(n, m);
    switch (type) {
    case M2x2:
      gl.glUniformMatrix2(loc, false, values);
      gl.checkGLError("glUniformMatrix2");
      break;
    case M2x3:
      gl.glUniformMatrix2x3(loc, false, values);
      gl.checkGLError("glUniformMatrix2x3");
      break;
    case M2x4:
      gl.glUniformMatrix2x4(loc, false, values);
      gl.checkGLError("glUniformMatrix2x4");
      break;
    case M3x2:
      gl.glUniformMatrix3x2(loc, false, values);
      gl.checkGLError("glUniformMatrix3x2");
      break;
    case M3x3:
      gl.glUniformMatrix3(loc, false, values);
      gl.checkGLError("glUniformMatrix3");
      break;
    case M3x4:
      gl.glUniformMatrix3x4(loc, false, values);
      gl.checkGLError("glUniformMatrix3x4");
      break;
    case M4x2:
      gl.glUniformMatrix4x2(loc, false, values);
      gl.checkGLError("glUniformMatrix4x2");
      break;
    case M4x3:
      gl.glUniformMatrix4x3(loc, false, values);
      gl.checkGLError("glUniformMatrix4x3");
      break;
    case M4x4:
      gl.glUniformMatrix4(loc, false, values);
      gl.checkGLError("glUniformMatrix4");
      break;
    default:
      throw new IllegalArgumentException(String.format("Unsupported dimension values for uniform matrix: %dx%d", n, m));
    }
  }

  public int getAttribLocation(final String name) {
    final int result = gl.glGetAttribLocation(program, name);
    gl.checkGLError("glGetAttribLocation");
    return result;
  }

  public void bindAttribLocation(final String name, final int index) {
    gl.glBindAttribLocation(program, index, name);
    gl.checkGLError("glBindAttribLocation");
  }

  public Map<String, UniformBlockInfo> getUniformIndices(final String... uniformNames) {
    final Map<String, UniformBlockInfo> result = new Hashtable<String, UniformBlockInfo>();

    final IntBuffer intBuffer = gl.getUtil().createIntBuffer(uniformNames.length);
    gl.glGetUniformIndices(program, uniformNames, intBuffer);

    final IntBuffer uniformOffsets = gl.getUtil().createIntBuffer(uniformNames.length);
    gl.glGetActiveUniforms(program, uniformNames.length, intBuffer, gl.GL_UNIFORM_OFFSET(), uniformOffsets);

    final IntBuffer arrayStrides = gl.getUtil().createIntBuffer(uniformNames.length);
    gl.glGetActiveUniforms(program, uniformNames.length, intBuffer, gl.GL_UNIFORM_ARRAY_STRIDE(), arrayStrides);

    final IntBuffer matrixStrides = gl.getUtil().createIntBuffer(uniformNames.length);
    gl.glGetActiveUniforms(program, uniformNames.length, intBuffer, gl.GL_UNIFORM_MATRIX_STRIDE(), matrixStrides);

    gl.checkGLError("getUniformIndices");

    for (int i = 0; i < uniformNames.length; i++) {
      final UniformBlockInfo blockInfo = new UniformBlockInfo();
      blockInfo.name = uniformNames[i];
      blockInfo.offset = uniformOffsets.get(i);
      blockInfo.arrayStride = arrayStrides.get(i);
      blockInfo.matrixStride = matrixStrides.get(i);
      result.put(blockInfo.name, blockInfo);
    }

    return result;
  }

  public void uniformBlockBinding(final String name, final int uniformBlockBinding) {
    final int uniformBlockIndex = gl.glGetUniformBlockIndex(program, name);
    gl.checkGLError("glGetUniformBlockIndex");

    gl.glUniformBlockBinding(program, uniformBlockIndex, uniformBlockBinding);
    gl.checkGLError("glUniformBlockBinding");
  }

  public void activate() {
    gl.glUseProgram(program);
    gl.checkGLError("glUseProgram");
  }

  private int registerParameter(final String name) {
    final int location = getUniform(name);
    parameter.put(name, location);
    return location;
  }

  private int getLocation(final String name) {
    final Integer value = parameter.get(name);
    if (value == null) {
      return registerParameter(name);
    }
    return value;
  }

  private int getUniform(final String uniformName) {
    final int result = gl.glGetUniformLocation(program, uniformName);
    log.checkGLError(gl, "glGetUniformLocation for [{}] failed", uniformName);
    log.fine("glUniformLocation for [{}] = [{}]", uniformName, result);
    return result;
  }

  private void prepareShader(final int shaderId, final String name, final InputStream... sources) {
    try {
      gl.glShaderSource(shaderId, loadShader(sources));
      gl.checkGLError("glShaderSource");
    } catch (final IOException e) {
      throw new CoreGLException(e);
    }

    gl.glCompileShader(shaderId);
    gl.checkGLError("glCompileShader");

    final IntBuffer ret = gl.getUtil().createIntBuffer(1);
    gl.glGetShaderiv(shaderId, gl.GL_COMPILE_STATUS(), ret);
    if (ret.get(0) == gl.GL_FALSE()) {
      log.warn("'{}' compile error: {}", name, gl.glGetShaderInfoLog(shaderId));
    }

    printLogInfo(shaderId);
    gl.checkGLError(String.valueOf(shaderId));
  }

  private String loadShader(final InputStream... sources) throws IOException {
    final StringBuilder srcbuff = new StringBuilder();
    for (final InputStream source : sources) {
      final InputStreamReader streamReader = new InputStreamReader(source);
      final BufferedReader buffReader = new BufferedReader(streamReader);
      String nextLine;
      while ((nextLine = buffReader.readLine()) != null) {
        srcbuff.append(nextLine).append('\n');
      }
      buffReader.close();
    }

    return srcbuff.toString();
  }

  private void printLogInfo(final int obj) {
    final String logInfoMsg = gl.glGetShaderInfoLog(obj);
    gl.checkGLError("glGetShaderInfoLog");
    if (!logInfoMsg.isEmpty()) {
      log.info("Info log:\n{}", logInfoMsg);
    }
    gl.checkGLError("printLogInfo");
  }

  private InputStream getStream(final File file) throws FileNotFoundException {
    log.fine("loading shader file [{}]", file);
    return new FileInputStream(file);
  }

  private InputStream getStream(final String filename) {
    return Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
  }

  /**
   * Internal enum for representing the 9 possible GLSL matrix types and mapping
   * them to a single key formed from the 'n x m' dimensions.
   *
   * @author Brian Groenke (groenke.5@osu.edu)
   */
  private enum UniformMatrixType {
    M2x2(2, 2),
    M2x3(2, 3),
    M2x4(2, 4),
    M3x3(3, 3),
    M3x2(3, 2),
    M3x4(3, 4),
    M4x4(4, 4),
    M4x2(4, 2),
    M4x3(4, 3),
    UNSUPPORTED(0, 0);

    private final static UniformMatrixType[] matDimsToType;

    final int n, m;

    UniformMatrixType(final int n, final int m) {
      this.n = n;
      this.m = m;
    }

    static {
      matDimsToType = new UniformMatrixType[keyFor(4, 4) + 1];
      for (final UniformMatrixType value : values()) {
        int key = keyFor(value.n, value.m);
        if (key >= 0 && key < matDimsToType.length) {
          matDimsToType[key] = value;
        }
      }
    }

    private static UniformMatrixType typeFor(final int n, final int m) {
      int key = keyFor(n, m);
      if (key >= 0 && key < matDimsToType.length) {
        return matDimsToType[key];
      }
      return UNSUPPORTED;
    }

    private static int keyFor(final int n, final int m) {
      return (n - 2) * 3 + m;
    }
  }
}
