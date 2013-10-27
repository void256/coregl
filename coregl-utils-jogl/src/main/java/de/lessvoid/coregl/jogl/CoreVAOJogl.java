package de.lessvoid.coregl.jogl;

import static javax.media.opengl.GL.GL_FLOAT;

import javax.media.opengl.GL;
import javax.media.opengl.GLContext;

import de.lessvoid.coregl.CoreCheckGL;
import de.lessvoid.coregl.CoreVAO;



public class CoreVAOJogl implements CoreVAO {
  private static final CoreCheckGL checkGL = new CoreCheckGLJogl();
  private int vao;

  public CoreVAOJogl() {
    init();
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVAO#bind()
   */
  @Override
  public void bind() {
    final GL gl = GLContext.getCurrentGL();
    if (gl.isGL3()) {
      gl.getGL3().glBindVertexArray(vao);
      checkGL.checkGLError("glBindVertexArray");
    }
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVAO#unbind()
   */
  @Override
  public void unbind() {
    final GL gl = GLContext.getCurrentGL();
    if (gl.isGL3()) {
      gl.getGL3().glBindVertexArray(0);
      checkGL.checkGLError("glBindVertexArray(0)");
    }
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVAO#delete()
   */
  @Override
  public void delete() {
    final GL gl = GLContext.getCurrentGL();
    if (gl.isGL3()) {
      int[] buffer = new int[1];
      buffer[0] = vao;
      gl.getGL3().glDeleteVertexArrays(1, buffer, 0);
    }
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVAO#enableVertexAttributef(int, int, int, int)
   */
  @Override
  public void enableVertexAttributef(final int index, final int size, final int stride, final int offset) {
    final GL gl = GLContext.getCurrentGL();
    gl.getGL2ES2().glVertexAttribPointer(index, size, GL.GL_FLOAT, false, stride * 4, offset * 4);
    gl.getGL2ES2().glEnableVertexAttribArray(index);
    checkGL.checkGLError("glVertexAttribPointer (" + index + ")");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVAO#disableVertexAttribute(int)
   */
  @Override
  public void disableVertexAttribute(final int index) {
    final GL gl = GLContext.getCurrentGL();
    if (gl.isGL3()) {
      gl.getGL2GL3().glDisableVertexAttribArray(index);
      checkGL.checkGLError("glDisableVertexAttribArray (" + index + ")");
    }
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVAO#enableVertexAttributeDivisorf(int, int, int, int, int)
   */
  @Override
  public void enableVertexAttributeDivisorf(
      final int index,
      final int size,
      final int stride,
      final int offset,
      final int divisor) {
    final GL gl = GLContext.getCurrentGL();
    if (gl.isGL3()) {
      gl.getGL2GL3().glVertexAttribPointer(index, size, GL_FLOAT, false, stride * 4, offset * 4);
// FIXME      gl.getGL2GL3().glVertexAttribDivisorARB(index, divisor);
      gl.getGL2GL3().glEnableVertexAttribArray(index);
      checkGL.checkGLError("glVertexAttribPointer (" + index + ")");
    }
  }

  private void init() {
    final GL gl = GLContext.getCurrentGL();
    if (gl.isGL3()) {
      int[] buffer = new int[1];
      gl.getGL3().glGenVertexArrays(1, buffer, 0);
      vao = buffer[0];
      checkGL.checkGLError("glGenVertexArrays");
    }
  }
}
