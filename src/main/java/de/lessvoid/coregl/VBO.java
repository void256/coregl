package de.lessvoid.coregl;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class VBO {
  private int id;
  private int usage;
  private FloatBuffer buffer;

  public static VBO createStatic(final float[] data) {
    return new VBO(GL_STATIC_DRAW, data);
  }

  public static VBO createDynamic(final float[] data) {
    return new VBO(GL_DYNAMIC_DRAW, data);
  }

  private VBO(final int usageType, final float[] data) {
    usage = usageType;

    buffer = BufferUtils.createFloatBuffer(data.length);
    buffer.put(data);
    buffer.rewind();

    id = glGenBuffers();
    CheckGL.checkGLError("glGenBuffers");

    glBindBuffer(GL_ARRAY_BUFFER, id);
    CheckGL.checkGLError("glBindBuffer");
  
    glBufferData(GL_ARRAY_BUFFER, buffer, usage);
    CheckGL.checkGLError("glBufferData");
  }

  public FloatBuffer getBuffer() {
    return buffer;
  }

  public void update() {
    glBindBuffer(GL_ARRAY_BUFFER, id);
    CheckGL.checkGLError("glBindBuffer");

    buffer.rewind();

    glBufferData(GL_ARRAY_BUFFER, buffer, usage);
    CheckGL.checkGLError("glBufferData");
  }

  public void delete() {
    glDeleteBuffers(id);
  }
}
