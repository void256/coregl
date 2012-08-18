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

public class SuperSimpleExampleMain implements RenderLoopCallback {
  private CoreShader shader;
  private CoreVAO vao;

  public SuperSimpleExampleMain() {
    shader = new CoreShader("vVertex", "vColor");
    shader.compile("nifty.vs", "nifty.fs");

    vao = new CoreVAO();
    vao.bind();

    CoreVBO.createStatic(new float[] {
        -0.5f, -0.5f,    1.0f, 0.0f, 0.0f, 1.0f,
        -0.5f,  0.5f,    0.0f, 1.0f, 0.0f, 1.0f,
         0.5f, -0.5f,    0.0f, 0.0f, 1.0f, 1.0f,
         0.5f,  0.5f,    1.0f, 1.0f, 1.0f, 1.0f,
    });

    vao.enableVertexAttributef(0, 2, 6, 0);
    vao.enableVertexAttributef(1, 4, 6, 2);

    shader.activate();
    vao.bind();
  }

  @Override
  public boolean render() {
    glClearColor(.1f, .1f, .3f, 0.f);
    glClear(GL_COLOR_BUFFER_BIT);

    CoreRender.renderTriangleStrip(4);
    return false;
  }

  public static void main(final String[] args) throws Exception {
    CoreLwjglSetup setup = new CoreLwjglSetup();
    setup.initializeLogging();
    setup.initialize("Hello Lwjgl Core GL", 1024, 768);
    setup.renderLoop(new SuperSimpleExampleMain());
  }
}
