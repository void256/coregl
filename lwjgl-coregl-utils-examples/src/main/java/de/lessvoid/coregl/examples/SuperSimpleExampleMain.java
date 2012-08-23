package de.lessvoid.coregl.examples;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import de.lessvoid.coregl.CoreLwjglSetup;
import de.lessvoid.coregl.CoreLwjglSetup.RenderLoopCallback;
import de.lessvoid.coregl.CoreRender;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreVAO;
import de.lessvoid.coregl.CoreVBO;

/**
 * The SuperSimpleExampleMain just renders a single quad using a triangle strip
 * with a very basic vertex and fragment shader. It demonstrates the use of the
 * lwjgl-core-utils classes.
 *
 * @author void
 */
public class SuperSimpleExampleMain implements RenderLoopCallback {

  public SuperSimpleExampleMain() {
    CoreShader shader = new CoreShader("vVertex", "vColor");
    shader.compile("super-simple.vs", "super-simple.fs");

    CoreVAO vao = new CoreVAO();
    vao.bind();

    CoreVBO.createStaticVBOAndSend(new float[] {
        -0.5f, -0.5f,    1.0f, 0.0f, 0.0f, 1.0f,
        -0.5f,  0.5f,    0.0f, 1.0f, 0.0f, 1.0f,
         0.5f, -0.5f,    0.0f, 0.0f, 1.0f, 1.0f,
         0.5f,  0.5f,    1.0f, 1.0f, 1.0f, 1.0f,
    });

    // parameters are: index, size, stride, offset
    // this will use the currently active VBO to store the VBO in the VAO
    vao.enableVertexAttributef(0, 2, 6, 0);
    vao.enableVertexAttributef(1, 4, 6, 2);

    // we only use a single shader and a single vao so we can activate both her
    // and let them stay active the whole time.
    shader.activate();
    vao.bind();
  }

  @Override
  public boolean render(final float deltaTime) {
    glClearColor(.1f, .1f, .3f, 0.f);
    glClear(GL_COLOR_BUFFER_BIT);

    // render all the data in the currently active vao using triangle strips
    CoreRender.renderTriangleStrip(4);
    return false;
  }

  public static void main(final String[] args) throws Exception {
    CoreLwjglSetup setup = new CoreLwjglSetup();
    setup.initializeLogging(); // optional to get jdk14 to better format the log
    setup.initialize("Hello Lwjgl Core GL", 1024, 768);
    setup.renderLoop(new SuperSimpleExampleMain());
  }
}
