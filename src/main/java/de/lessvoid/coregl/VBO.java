package de.lessvoid.coregl;


import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class VBO {
  public static final int VERTEX_INDEX = 0;

  private int id;
  private int vao;
  private int type;
  private FloatBuffer buffer;

  public VBO(final int type, final float[] vertices, final int stride, final int ... componentSize) {
    this.type = type;

    buffer = BufferUtils.createFloatBuffer(vertices.length);
    buffer.put(vertices);
    buffer.rewind();
    init(buffer, stride, componentSize);
  }

  public FloatBuffer getBuffer() {
    return buffer;
  }

  public void update() {
    glBindBuffer(GL_ARRAY_BUFFER, id);
    CheckGL.checkGLError("glBindBuffer");

    buffer.rewind();

    glBufferData(GL_ARRAY_BUFFER, buffer, type);
    CheckGL.checkGLError("glBufferData");
  }

  private void init(final FloatBuffer buffer, final int stride, final int[] componentSize) {
    vao = glGenVertexArrays();
    CheckGL.checkGLError("glGenVertexArrays");

    glBindVertexArray(vao);
    CheckGL.checkGLError("glBindVertexArray");

      id = glGenBuffers();
      CheckGL.checkGLError("glGenBuffers");

      glBindBuffer(GL_ARRAY_BUFFER, id);
      CheckGL.checkGLError("glBindBuffer");
    
        glBufferData(GL_ARRAY_BUFFER, buffer, type);
        CheckGL.checkGLError("glBufferData");

      int offset = 0;
      for (int i = 0; i < componentSize.length; i++) {
        glVertexAttribPointer(i, componentSize[i], GL_FLOAT, false, stride * 4, offset);
        glEnableVertexAttribArray(i);
        CheckGL.checkGLError("glVertexAttribPointer (" + i + ", " + componentSize[i] + "," + stride + ")");

        offset += componentSize[i] * 4;
      }

    glBindVertexArray(0);
    CheckGL.checkGLError("glBindVertexArray(0)");
  }

  public void delete() {
    glDeleteBuffers(id);
    glDeleteVertexArrays(vao);
  }

  public void render() {
    glBindVertexArray(vao);
    CheckGL.checkGLError("glBindVertexArray");

    glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
    CheckGL.checkGLError("glDrawArrays");
  }
}
