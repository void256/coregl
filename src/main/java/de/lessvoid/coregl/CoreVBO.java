package de.lessvoid.coregl;


import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class CoreVBO {
  private int id;
  private int usage;
  private FloatBuffer buffer;

  public static CoreVBO createStatic(final float[] data) {
    return new CoreVBO(GL_STATIC_DRAW, data);
  }

  public static CoreVBO createDynamic(final float[] data) {
    return new CoreVBO(GL_DYNAMIC_DRAW, data);
  }

  private CoreVBO(final int usageType, final float[] data) {
    usage = usageType;

    buffer = BufferUtils.createFloatBuffer(data.length);
    buffer.put(data);
    buffer.rewind();

    id = glGenBuffers();
    CoreCheckGL.checkGLError("glGenBuffers");

    glBindBuffer(GL_ARRAY_BUFFER, id);
    CoreCheckGL.checkGLError("glBindBuffer");
  
    glBufferData(GL_ARRAY_BUFFER, buffer, usage);
    CoreCheckGL.checkGLError("glBufferData");
  }

  public FloatBuffer getBuffer() {
    return buffer;
  }

  public void update() {
    glBindBuffer(GL_ARRAY_BUFFER, id);
    CoreCheckGL.checkGLError("glBindBuffer");

    buffer.rewind();

    glBufferData(GL_ARRAY_BUFFER, buffer, usage);
    CoreCheckGL.checkGLError("glBufferData");
  }

  public void delete() {
    glDeleteBuffers(id);
  }
}
