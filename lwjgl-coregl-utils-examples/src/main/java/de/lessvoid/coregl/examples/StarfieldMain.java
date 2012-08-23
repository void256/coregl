package de.lessvoid.coregl.examples;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.nio.FloatBuffer;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import de.lessvoid.coregl.CoreLwjglSetup;
import de.lessvoid.coregl.CoreLwjglSetup.RenderLoopCallback;
import de.lessvoid.coregl.CoreMatrixFactory;
import de.lessvoid.coregl.CoreRender;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreVAO;
import de.lessvoid.coregl.CoreVBO;

/**
 * FIXME
 * @author void
 */
public class StarfieldMain implements RenderLoopCallback {
  private static final int STAR_COUNT = 20000;
  private CoreShader shader;
  private float z = 5.0f;
  private float angleX = 0.f;
  private float angleY = 0.f;

  public StarfieldMain() {
    shader = new CoreShader("aVertex", "aStarPos");
    shader.compile("star.vs", "star.fs");

    CoreVAO vao = new CoreVAO();
    vao.bind();

    CoreVBO.createStaticVBOAndSend(new float[] {
        -0.025f, -0.025f,
        -0.025f,  0.025f,
        0.025f, -0.025f,
        0.025f,  0.025f,
    });
    vao.enableVertexAttributef(shader.getAttribLocation("aVertex"), 2, 0, 0);

    CoreVBO starPosBuffer = CoreVBO.createStaticVBO(new float[STAR_COUNT * 3]);
    FloatBuffer buffer = starPosBuffer.getBuffer();
    float size = 10.f;

    for (int i=0; i<STAR_COUNT; i++) {
      buffer.put(i*3 + 0, (float) Math.random() * size - size / 2.f);
      buffer.put(i*3 + 1, (float) Math.random() * size - size / 2.f);
      buffer.put(i*3 + 2, (float) Math.random() * size - size / 2.f);
    }
/*
    buffer.put(0*3 + 0, -size); buffer.put(0*3 + 1, -size); buffer.put(0*3 + 2, -size);
    buffer.put(1*3 + 0,  size); buffer.put(1*3 + 1, -size); buffer.put(1*3 + 2, -size);
    buffer.put(2*3 + 0,  size); buffer.put(2*3 + 1,  size); buffer.put(2*3 + 2, -size);
    buffer.put(3*3 + 0, -size); buffer.put(3*3 + 1,  size); buffer.put(3*3 + 2, -size);
    buffer.put(4*3 + 0, -size); buffer.put(4*3 + 1, -size); buffer.put(4*3 + 2,  size);
    buffer.put(5*3 + 0,  size); buffer.put(5*3 + 1, -size); buffer.put(5*3 + 2,  size);
    buffer.put(6*3 + 0,  size); buffer.put(6*3 + 1,  size); buffer.put(6*3 + 2,  size);
    buffer.put(7*3 + 0, -size); buffer.put(7*3 + 1,  size); buffer.put(7*3 + 2,  size);
*/
    buffer.rewind();
    starPosBuffer.sendData();
    vao.enableVertexAttributeDivisorf(shader.getAttribLocation("aStarPos"), 3, 0, 0, 1);

    shader.activate();
    vao.bind();
    glPointSize(2);

    glEnable(GL_DEPTH_TEST);
  }

  @Override
  public boolean render(final float deltaTime) {
    //System.out.println(deltaTime);
    glClearColor(.1f, .1f, .3f, 0.f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    // render all the data in the currently active vao using triangle strips
    CoreRender.renderTriangleStripInstances(4, STAR_COUNT);

    Matrix4f translate = Matrix4f.translate(new Vector3f(0.f, 0.f, z), new Matrix4f(), null);
    Matrix4f rotateX = Matrix4f.rotate(angleX, new Vector3f(1.f, 0.f, 0.f), new Matrix4f(), null);
    Matrix4f rotateY = Matrix4f.rotate(angleY, new Vector3f(0.f, 1.f, 0.f), new Matrix4f(), null);
//    Matrix4f projection = CoreMatrixFactory.createProjection(-150.f, 150.f, -150.f, 150.f, 1f, 1000.f);
    Matrix4f projection = CoreMatrixFactory.createProjection(65.f, 1024.f/768.f, 1f, 1000.f);
//    System.out.println(projection);
    
    z -= deltaTime / 1000.f;// System.out.println(z);
//    angleX += deltaTime / 10000.f;
//    angleY += deltaTime / 20000.f;
    //z += deltaTime / 10000.f;

    Matrix4f modelViewProjection = Matrix4f.mul(projection, Matrix4f.mul(translate, Matrix4f.mul(rotateX, rotateY, null), null), null);
    shader.setUniformMatrix4f("uModelViewProjection", modelViewProjection);

    return false;
  }

  public static void main(final String[] args) throws Exception {
    CoreLwjglSetup setup = new CoreLwjglSetup();
    setup.initializeLogging(); // optional to get jdk14 to better format the log
    setup.initialize("Hello Lwjgl Core GL", 1024, 768);
    setup.renderLoop(new StarfieldMain());
  }
}
