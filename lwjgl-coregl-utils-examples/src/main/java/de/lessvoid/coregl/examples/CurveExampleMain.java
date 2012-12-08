package de.lessvoid.coregl.examples;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import de.lessvoid.coregl.CoreVBO;
import de.lessvoid.coregl.CoreLwjglSetup;
import de.lessvoid.coregl.CoreLwjglSetup.RenderLoopCallback;
import de.lessvoid.coregl.CoreRender;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreVAO;

public class CurveExampleMain implements RenderLoopCallback {
  private CoreShader shader;
  private float r = 0.f;

  public CurveExampleMain() {
    shader = CoreShader.newShaderWithVertexAttributes("vVertex", "vTexture");
    shader.vertexShader("curve/curve.vs");
    shader.fragmentShader("curve/curve.fs");
    shader.link();

    CoreVAO vao = new CoreVAO();
    vao.bind();

    float aspect = 4.f/3.f;
    CoreVBO.createStaticAndSend(new float[] {
        -0.4f, -0.4f * aspect,    0.0f, 0.0f, 
         0.4f, -0.4f * aspect,    1.0f, 0.0f, 
        -0.4f,  0.4f * aspect,    0.0f, 1.0f, 
         0.4f,  0.4f * aspect,    1.0f, 1.0f, 
    });

    // parameters are: index, size, stride, offset
    // this will use the currently active VBO to store the VBO in the VAO
    vao.enableVertexAttributef(0, 2, 4, 0);
    vao.enableVertexAttributef(1, 2, 4, 2);

    // we only use a single shader and a single vao so we can activate both here
    // and let them stay active the whole time.
    shader.activate();
    vao.bind();
  }

  @Override
  public boolean render(final float deltaTime) {
    glClearColor(.1f, .1f, .3f, 0.f);
    glClear(GL_COLOR_BUFFER_BIT);

    shader.setUniformf("r", r);
    r += deltaTime / 10000.f;
    System.out.println(r);

    // render all the data in the currently active vao using triangle strips
    CoreRender.renderTriangleStrip(4);
    return false;
  }

  public static void main(final String[] args) throws Exception {
    CoreLwjglSetup setup = new CoreLwjglSetup();
    setup.initializeLogging(); // optional to get jdk14 to better format the log
    setup.initialize("Hello Lwjgl Core GL", 1024, 768);
    setup.renderLoop(new CurveExampleMain());
  }
}
