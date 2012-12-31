package de.lessvoid.coregl.examples;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import de.lessvoid.coregl.CoreLwjglSetup;
import de.lessvoid.coregl.CoreRender;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreVAO;
import de.lessvoid.coregl.CoreVBO;
import de.lessvoid.coregl.CoreLwjglSetup.RenderLoopCallback;

public class BackgroundMain implements RenderLoopCallback {
  private CoreShader shader;
  private long startTime = System.currentTimeMillis();

  public BackgroundMain() {
    shader = CoreShader.newShaderWithVertexAttributes("vVertex");
    shader.vertexShader("background/background.vs");
    shader.fragmentShader("background/background.fs");
    shader.link();

    CoreVAO vao = new CoreVAO();
    vao.bind();

    CoreVBO.createStaticAndSend(new float[] {
        -1.0f, -1.0f,
        -1.0f,  1.0f,
         1.0f, -1.0f,
         1.0f,  1.0f,
    });

    // parameters are: index, size, stride, offset
    // this will use the currently active VBO to store the VBO in the VAO
    vao.enableVertexAttributef(0, 2, 2, 0);

    // we only use a single shader and a single vao so we can activate both here
    // and let them stay active the whole time.
    shader.activate();
    vao.bind();
  }

  @Override
  public boolean render(final float deltaTime) {
    glClearColor(.1f, .1f, .3f, 0.f);
    glClear(GL_COLOR_BUFFER_BIT);

    float time = (System.currentTimeMillis() - startTime) / 1000.f;
    shader.setUniformf("time", time);
    shader.setUniformf("resolution", 1024.f, 768.f);

    // render all the data in the currently active vao using triangle strips
    CoreRender.renderTriangleStrip(4);
    return false;
  }

  public static void main(final String[] args) throws Exception {
    CoreLwjglSetup setup = new CoreLwjglSetup();
    setup.initializeLogging(); // optional to get jdk14 to better format the log
    setup.initialize("Core GL Background", 1024, 768);
    setup.renderLoop(new BackgroundMain());
  }
}