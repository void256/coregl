package de.lessvoid.coregl;


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
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Hashtable;
import java.util.logging.Logger;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;

public class Shader {
  private static Logger log = Logger.getLogger(Shader.class.getName());
  private int program;
  private Hashtable<String, Integer> parameter = new Hashtable<String, Integer>();
  private FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
  private String[] attrib = new String[0];

  public Shader(final String ... names) {
    attrib = names;
  }

  public void compile(final String vertexShader, final String fragmentShader) {
    int vertexShaderId = createVertexShader();
    prepareShader(vertexShaderId, vertexShader);

    int fragmentShaderId = createFragmentShader();
    prepareShader(fragmentShaderId, fragmentShader);

    program = glCreateProgram();
    CheckGL.checkGLError("glCreateProgram");

    glAttachShader(program, vertexShaderId);
    CheckGL.checkGLError("glAttachShader (vertexShaderId)");

    glAttachShader(program, fragmentShaderId);
    CheckGL.checkGLError("glAttachShader (fragmentShaderId)");

    for (int i=0; i<attrib.length; i++) {
      glBindAttribLocation(program, i, attrib[i]);
      CheckGL.checkGLError("glBindAttribLocation (" + attrib[i] + ")");
    }

    glLinkProgram(program);
    CheckGL.checkGLError("glLinkProgram");

    if (glGetProgram(program, GL_LINK_STATUS) != GL_TRUE) {
      System.out.println("link error: " + glGetProgramInfoLog(program, 1024));
      CheckGL.checkGLError("glGetProgramInfoLog");
    }
    CheckGL.checkGLError("glGetProgram");
  }

  public void setUniform(final String name, final float value) {
    glUniform1f(getLocation(name), value);
    CheckGL.checkGLError("glUniform1f");
  }

  public void setUniform(final String name, final float v1, final float v2) {
    glUniform2f(getLocation(name), v1, v2);
    CheckGL.checkGLError("glUniform2f");
  }

  public void setUniform(final String name, final float v1, final float v2, final float v3) {
    glUniform3f(getLocation(name), v1, v2, v3);
    CheckGL.checkGLError("glUniform3f");
  }

  public void setUniform(final String name, final float x, final float y, final float z, final float w) {
    glUniform4f(getLocation(name), x, y, z, w);
    CheckGL.checkGLError("glUniform4f");
  }

  public void setUniform(final String name, final int v1) {
    glUniform1i(getLocation(name), v1);
    CheckGL.checkGLError("glUniform1i");
  }

  public void setUniform(final String name, final int v1, final int v2) {
    glUniform2i(getLocation(name), v1, v2);
    CheckGL.checkGLError("glUniform2i");
  }

  public void setUniform(final String name, final int v1, final int v2, final int v3) {
    glUniform3i(getLocation(name), v1, v2, v3);
    CheckGL.checkGLError("glUniform3i");
  }

  public void setUniform(final String name, final int v1, final int v2, final int v3, final int v4) {
    glUniform4i(getLocation(name), v1, v2, v3, v4);
    CheckGL.checkGLError("glUniform4i");
  }

  public void setUniform(final String name, final Matrix4f matrix) {
    matBuffer.clear();
    matrix.store(matBuffer);
    matBuffer.rewind();
    glUniformMatrix4(getLocation(name), false, matBuffer);
    CheckGL.checkGLError("glUniformMatrix4");
  }

  public void setUniform(final String name, final float[] kernelValues) {
    FloatBuffer buffer = BufferUtils.createFloatBuffer(kernelValues.length);
    buffer.put(kernelValues);
    buffer.rewind();
    glUniform1(getLocation(name), buffer);
    CheckGL.checkGLError("glUniform1");
  }

  public int getAttribLocation(final String name) {
    int result = glGetAttribLocation(program, name);
    CheckGL.checkGLError("glGetAttribLocation");
    return result;
  }

  public void bindAttribLocation(final String name, final int index) {
    glBindAttribLocation(program, index, name);
    CheckGL.checkGLError("glBindAttribLocation");
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

  public void activate() {
    glUseProgram(program);
    CheckGL.checkGLError("glUseProgram");
  }

  private int getUniform(final String uniformName) {
    try {
      byte[] bytes = uniformName.getBytes("ISO-8859-1");
      ByteBuffer name = BufferUtils.createByteBuffer(bytes.length + 1);
      name.put(bytes);
      name.put((byte)0x00);
      name.rewind();
      int result = glGetUniformLocation(program, name);
      CheckGL.checkGLError("glGetUniformLocation for [" + uniformName + "] failed");
      return result;
    } catch (UnsupportedEncodingException e) {
      log.warning(e.getMessage());
      return -1;
    }
  }

  private void prepareShader(final int shaderId, final String filename) {
    glShaderSource(shaderId, loadShader(filename));
    CheckGL.checkGLError("glShaderSource");

    glCompileShader(shaderId);
    CheckGL.checkGLError("glCompileShader");

    if (glGetShader(shaderId, GL_COMPILE_STATUS) == GL_FALSE) {
      System.out.println("compile error: " + glGetShaderInfoLog(shaderId, 1024));
    }

    printLogInfo(shaderId);
    CheckGL.checkGLError(filename);
  }

  private int createVertexShader() {
    int shaderId = glCreateShader(GL_VERTEX_SHADER);
    CheckGL.checkGLError("glCreateShader(GL_VERTEX_SHADER)");
    return shaderId;
  }

  private int createFragmentShader() {
    int fragmentId = glCreateShader(GL_FRAGMENT_SHADER);
    CheckGL.checkGLError("glCreateShader(GL_FRAGMENT_SHADER)");
    return fragmentId;
  }

  private ByteBuffer loadShader(final String filename) {
    log.fine("loading shader file [" + filename + "]");
    ensureFilename(filename);

    byte[] data = read(getStream(filename));

    ByteBuffer result = BufferUtils.createByteBuffer(data.length);
    result.put(data);
    result.flip();
    return result;
  }

  private InputStream getStream(final String filename) {
    return Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
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

  private void ensureFilename(final String filename) {
    if (filename == null || filename.length() == 0) {
      throw new RuntimeException("shader filename not given or empty");
    }
  }

  private void printLogInfo(final int obj) {
    ByteBuffer infoLog = BufferUtils.createByteBuffer(2048);
    IntBuffer lengthBuffer = BufferUtils.createIntBuffer(1);
    glGetShaderInfoLog(obj, lengthBuffer, infoLog);
    CheckGL.checkGLError("glGetShaderInfoLog");

    byte[] infoBytes = new byte[lengthBuffer.get()];
    infoLog.get(infoBytes);
    if (infoBytes.length == 0) {
      return;
    }
    try {
      log.info("Info log:\n" + new String(infoBytes, "ISO-8859-1"));
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    CheckGL.checkGLError("printLogInfo");
  }
}
