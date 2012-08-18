package de.lessvoid.coregl;


import static org.lwjgl.opengl.ARBInstancedArrays.glVertexAttribDivisorARB;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class CoreVAO {
  private int vao;

  public CoreVAO() {
    init();
  }

  public void bind() {
    glBindVertexArray(vao);
    CoreCheckGL.checkGLError("glBindVertexArray");
  }

  public void unbind() {
    glBindVertexArray(0);
    CoreCheckGL.checkGLError("glBindVertexArray(0)");
  }

  public void delete() {
    glDeleteVertexArrays(vao);
  }

  public void enableVertexAttributef(final int index, final int size, final int stride, final int offset) {
    glVertexAttribPointer(index, size, GL_FLOAT, false, stride * 4, offset * 4);
    glEnableVertexAttribArray(index);
    CoreCheckGL.checkGLError("glVertexAttribPointer (" + index + ")");
  }

  public void enableVertexAttributeDivisorf(final int index, final int size, final int stride, final int offset, final int divisor) {
    glVertexAttribPointer(index, size, GL_FLOAT, false, stride * 4, offset * 4);
    glVertexAttribDivisorARB(index, divisor);
    glEnableVertexAttribArray(index);
    CoreCheckGL.checkGLError("glVertexAttribPointer (" + index + ")");
  }

  private void init() {
    vao = glGenVertexArrays();
    CoreCheckGL.checkGLError("glGenVertexArrays");
  }
}
