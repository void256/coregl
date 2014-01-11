package de.lessvoid.coregl.jogl;


import java.nio.Buffer;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GLContext;

import com.jogamp.common.nio.Buffers;

import de.lessvoid.coregl.CoreCheckGL;
import de.lessvoid.coregl.CoreVBO;

public class CoreVBOJogl < T extends Buffer > implements CoreVBO < T > {
  private static final CoreCheckGL checkGL = new CoreCheckGLJogl();
  private final int id;
  private final int usage;
  private FloatBuffer vertexBuffer;

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVBO#bind()
   */
  @Override
  public void bind() {
    final GL gl = GLContext.getCurrentGL();
    gl.getGL2ES2().glBindBuffer(GL.GL_ARRAY_BUFFER, id);
    checkGL.checkGLError("glBindBuffer(GL_ARRAY_BUFFER)");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVBO#send()
   */
  @Override
  public void send() {
    final GL gl = GLContext.getCurrentGL();
    gl.getGL2ES2().glBufferData(GL.GL_ARRAY_BUFFER, vertexBuffer.limit()*4, vertexBuffer, usage);
    checkGL.checkGLError("glBufferData(GL_ARRAY_BUFFER)");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVBO#delete()
   */
  @Override
  public void delete() {
    int[] buffer = new int[1];
    buffer[0] = id;
    final GL gl = GLContext.getCurrentGL();
    gl.getGL2ES2().glDeleteBuffers(1, buffer, 0);
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVBO#unmapBuffer()
   */
  @Override
  public void unmapBuffer() {
    // FIXME
    throw new UnsupportedOperationException();
  }

  private CoreVBOJogl(final int usageType, final float[] data) {
    usage = usageType;

    vertexBuffer = Buffers.newDirectFloatBuffer(data.length);
    vertexBuffer.put(data);
    vertexBuffer.rewind();

    final GL gl = GLContext.getCurrentGL();
    int[] buffer = new int[1];
    gl.getGL2ES2().glGenBuffers(1, buffer, 0);
    id = buffer[0];
    checkGL.checkGLError("glGenBuffers");

    gl.getGL2ES2().glBindBuffer(GL.GL_ARRAY_BUFFER, id);
    gl.getGL2ES2().glBufferData(GL.GL_ARRAY_BUFFER, data.length, vertexBuffer, usage);
    checkGL.checkGLError("glBufferData");
  }

  @Override
  public T getBuffer() {
    return null;
  }

  @Override
  public T getMappedBuffer() {
    return null;
  }
}
