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


import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetProgram;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform2i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform3i;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniform4i;
import static org.lwjgl.opengl.GL20.glUniformMatrix3;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;

import de.lessvoid.coregl.CoreCheckGL;
import de.lessvoid.coregl.CoreGLException;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.math.Mat3;
import de.lessvoid.math.Mat4;

public class CoreShaderLwjgl implements CoreShader {
  private static final CoreCheckGL checkGL = new CoreCheckGLLwjgl();

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
    return vertexShader(getStream(filename));
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#fragmentShader(java.lang.String)
   */
  @Override
  public int fragmentShader(final String filename) {
    return fragmentShader(getStream(filename));
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#fragmentShader(java.lang.String, java.io.InputStream[])
   */
  @Override
  public int fragmentShader(final String file, final InputStream ... inputStreams) throws FileNotFoundException {
    InputStream[] sources = new InputStream[inputStreams.length + 1];
    System.arraycopy(inputStreams, 0, sources, 0, inputStreams.length);
    sources[sources.length - 1] = getStream(file);
    return fragmentShader(sources);
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#geometryShader(java.lang.String)
   */
  @Override
  public int geometryShader(final String filename) {
    return geometryShader(getStream(filename));
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#vertexShader(java.io.File)
   */
  @Override
  public int vertexShader(final File file) throws FileNotFoundException {
    return vertexShader(getStream(file));
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#fragmentShader(java.io.File)
   */
  @Override
  public int fragmentShader(final File file) throws FileNotFoundException {
    return fragmentShader(getStream(file));
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#geometryShader(java.io.File)
   */
  @Override
  public int geometryShader(final File file) throws FileNotFoundException {
    return geometryShader(getStream(file));
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
    return geometryShader(sources);
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#geometryShader(java.lang.String, java.io.InputStream[])
   */
  @Override
  public int geometryShader(final String file, final InputStream ... inputStreams) throws FileNotFoundException {
    InputStream[] sources = new InputStream[inputStreams.length + 1];
    System.arraycopy(inputStreams, 0, sources, 0, inputStreams.length);
    sources[sources.length - 1] = getStream(file);
    return geometryShader(sources);
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#vertexShader(java.io.InputStream)
   */
  @Override
  public int vertexShader(final InputStream source) {
    int shaderId = glCreateShader(GL_VERTEX_SHADER);
    checkGLError("glCreateShader(GL_VERTEX_SHADER)");
    prepareShader(shaderId, source);
    glAttachShader(program, shaderId);
    checkGLError("glAttachShader");
    return shaderId;
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#fragmentShader(java.io.InputStream[])
   */
  @Override
  public int fragmentShader(final InputStream ... sources) {
    int shaderId = glCreateShader(GL_FRAGMENT_SHADER);
    checkGLError("glCreateShader(GL_FRAGMENT_SHADER)");
    prepareShader(shaderId, sources);
    glAttachShader(program, shaderId);
    checkGLError("glAttachShader");
    return shaderId;
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#geometryShader(java.io.InputStream[])
   */
  @Override
  public int geometryShader(final InputStream ... sources) {
    int shaderId = glCreateShader(GL_GEOMETRY_SHADER);
    checkGLError("glCreateShader(GL_GEOMETRY_SHADER)");
    prepareShader(shaderId, sources);
    glAttachShader(program, shaderId);
    checkGLError("glAttachShader");
    return shaderId;
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#vertexShader(int, java.lang.String)
   */
  @Override
  public void vertexShader(final int shaderId, final String filename) {
    vertexShader(shaderId, getStream(filename));
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#fragmentShader(int, java.lang.String)
   */
  @Override
  public void fragmentShader(final int shaderId, final String filename) {
    fragmentShader(shaderId, getStream(filename));
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#geometryShader(int, java.lang.String)
   */
  @Override
  public void geometryShader(final int shaderId, final String filename) {
    geometryShader(shaderId, getStream(filename));
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#vertexShader(int, java.io.File)
   */
  @Override
  public void vertexShader(final int shaderId, final File file) throws FileNotFoundException {
    vertexShader(shaderId, getStream(file));
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#fragmentShader(int, java.io.File)
   */
  @Override
  public void fragmentShader(final int shaderId, final File file) throws FileNotFoundException {
    fragmentShader(shaderId, getStream(file));
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#geometryShader(int, java.io.File)
   */
  @Override
  public void geometryShader(final int shaderId, final File file) throws FileNotFoundException {
    geometryShader(shaderId, getStream(file));
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#vertexShader(int, java.io.InputStream)
   */
  @Override
  public void vertexShader(final int shaderId, final InputStream source) {
    prepareShader(shaderId, source);
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#fragmentShader(int, java.io.InputStream)
   */
  @Override
  public void fragmentShader(final int shaderId, final InputStream source) {
    prepareShader(shaderId, source);
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#geometryShader(int, java.io.InputStream)
   */
  @Override
  public void geometryShader(final int shaderId, InputStream source) {
    prepareShader(shaderId, source);
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

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#setUniformf(java.lang.String, float)
   */
  @Override
  public void setUniformf(final String name, final float value) {
    glUniform1f(getLocation(name), value);
    checkGLError("glUniform1f");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#setUniformf(java.lang.String, float, float)
   */
  @Override
  public void setUniformf(final String name, final float v1, final float v2) {
    glUniform2f(getLocation(name), v1, v2);
    checkGLError("glUniform2f");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#setUniformf(java.lang.String, float, float, float)
   */
  @Override
  public void setUniformf(final String name, final float v1, final float v2, final float v3) {
    glUniform3f(getLocation(name), v1, v2, v3);
    checkGLError("glUniform3f");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#setUniformf(java.lang.String, float, float, float, float)
   */
  @Override
  public void setUniformf(final String name, final float x, final float y, final float z, final float w) {
    glUniform4f(getLocation(name), x, y, z, w);
    checkGLError("glUniform4f");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#setUniformi(java.lang.String, int)
   */
  @Override
  public void setUniformi(final String name, final int v1) {
    glUniform1i(getLocation(name), v1);
    checkGLError("glUniform1i");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#setUniformi(java.lang.String, int, int)
   */
  @Override
  public void setUniformi(final String name, final int v1, final int v2) {
    glUniform2i(getLocation(name), v1, v2);
    checkGLError("glUniform2i");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#setUniformi(java.lang.String, int, int, int)
   */
  @Override
  public void setUniformi(final String name, final int v1, final int v2, final int v3) {
    glUniform3i(getLocation(name), v1, v2, v3);
    checkGLError("glUniform3i");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#setUniformi(java.lang.String, int, int, int, int)
   */
  @Override
  public void setUniformi(final String name, final int v1, final int v2, final int v3, final int v4) {
    glUniform4i(getLocation(name), v1, v2, v3, v4);
    checkGLError("glUniform4i");
  }

  /*
   * 
   */
  @Override
  public <T> void setUniformMatrix4f(final String name, final T m) {
    toBuffer(m, matBuffer);
    glUniformMatrix4(getLocation(name), false, matBuffer);
    checkGLError("glUniformMatrix4");
  }

  /*
   * 
   */
  @Override
  public <T> void setUniformMatrix3f(final String name, final T m) {
    toBuffer(m, matBuffer);
    glUniformMatrix3(getLocation(name), false, matBuffer);
    checkGLError("glUniformMatrix3");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#setUniformfArray(java.lang.String, float[])
   */
  @Override
  public void setUniformfArray(final String name, final float[] values) {
    FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
    buffer.put(values);
    buffer.rewind();
    glUniform1(getLocation(name), buffer);
    checkGLError("glUniform1");
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

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreShader#activate()
   */
  @Override
  public void activate() {
    glUseProgram(program);
    checkGLError("glUseProgram");
  }

  CoreShaderLwjgl(final String ... vertexAttributes) {
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

  private void prepareShader(final int shaderId, final InputStream ... sources) {
    try {
      glShaderSource(shaderId, loadShader(sources));
      checkGLError("glShaderSource");
    } catch (IOException e) {
      throw new CoreGLException(e);
    }

    glCompileShader(shaderId);
    checkGLError("glCompileShader");

    if (glGetShader(shaderId, GL_COMPILE_STATUS) == GL_FALSE) {
      log.warning("compile error: " + glGetShaderInfoLog(shaderId, 1024));
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

  private <T> void toBuffer(final T m, final FloatBuffer buffer) {
    buffer.clear();

    if (m instanceof Matrix4f) {
      Matrix4f matrix = (Matrix4f) m;
      matrix.store(buffer);
    } else if (m instanceof Mat4) {
      Mat4 matrix = (Mat4) m;
      matrix.store(buffer);
    } else if (m instanceof Matrix3f) {
      Matrix3f matrix = (Matrix3f) m;
      matrix.store(buffer);
    } else if (m instanceof Mat3) {
      Mat3 matrix = (Mat3) m;
      matrix.store(buffer);
    } else {
      throw new IllegalArgumentException("Unsupported class argument: " + m.getClass());
    }
    
    buffer.rewind();
  }
}
