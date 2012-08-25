package de.lessvoid.coregl;


import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STREAM_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

/**
 * The CoreVBO class represents a VBO.
 * @author void
 */
public class CoreVBO {
  private int id;
  private int usage;
  private FloatBuffer buffer;

  /**
   * Create a new VBO with static vertex data (GL_STATIC_DRAW). This will
   * create the buffer object but does not bind or sends the data to the GPU.
   * You'll need to call bind() to bind this VBO and you'll need to call sendData()
   * to transmit the buffer data to the GPU.
   * 
   * @param data float array of buffer data
   * @return the CoreVBO instance created
   */
  public static CoreVBO createStaticVBO(final float[] data) {
    return new CoreVBO(GL_STATIC_DRAW, data);
  }

  /**
   * This provides the same functionality as createStaticVBO() but automatically
   * sends the data given to the GPU.
   * 
   * @param data float array of buffer data
   * @return the CoreVBO instance created
   */
  public static CoreVBO createStaticVBOAndSend(final float[] data) {
    CoreVBO result = new CoreVBO(GL_STATIC_DRAW, data);
    result.sendData();
    return result;
  }

  /**
   * This works exactly as createStaticVBO() but will use GL_DYNAMIC_DRAW instead.
   *
   * @param data float array of buffer data
   * @return the CoreVBO instance created
   */
  public static CoreVBO createDynamicVBO(final float[] data) {
    return new CoreVBO(GL_DYNAMIC_DRAW, data);
  }

  /**
   * This works exactly as createStaticVBO() but will use GL_STREAM_DRAW instead.
   *
   * @param data float array of buffer data
   * @return the CoreVBO instance created
   */
  public static CoreVBO createStreamVBO(final float[] data) {
    return new CoreVBO(GL_STREAM_DRAW, data);
  }

  /**
   * Allows access to the internally kept nio FloatBuffer that contains the original
   * buffer data. You can access and change this buffer if you want to update the
   * buffer content. Just make sure that you call rewind() before sending your new
   * data to the GPU with the sendData() method.
   *
   * @return the FloatBuffer with the original buffer data (stored in main memory
   * not GPU memory)
   */
  public FloatBuffer getBuffer() {
    return buffer;
  }

  /**
   * bind the buffer object as GL_ARRAY_BUFFER
   */
  public void bind() {
    glBindBuffer(GL_ARRAY_BUFFER, id);
    CoreCheckGL.checkGLError("glBindBuffer");
  }

  /**
   * Send the content of the FloatBuffer to the GPU.
   */
  public void sendData() {
    bind();

    glBufferData(GL_ARRAY_BUFFER, buffer, usage);
    CoreCheckGL.checkGLError("glBufferData");
  }

  /**
   * Delete all resources for this VBO.
   */
  public void delete() {
    glDeleteBuffers(id);
  }

  private CoreVBO(final int usageType, final float[] data) {
    usage = usageType;

    buffer = BufferUtils.createFloatBuffer(data.length);
    buffer.put(data);
    buffer.rewind();

    id = glGenBuffers();
    CoreCheckGL.checkGLError("glGenBuffers");
  }
}
