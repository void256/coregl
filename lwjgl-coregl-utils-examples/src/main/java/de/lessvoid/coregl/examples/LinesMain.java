package de.lessvoid.coregl.examples;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;

import java.nio.FloatBuffer;

import de.lessvoid.coregl.CoreLwjglSetup;
import de.lessvoid.coregl.CoreLwjglSetup.RenderLoopCallback;
import de.lessvoid.coregl.CoreMatrixFactory;
import de.lessvoid.coregl.CoreRender;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreVAO;
import de.lessvoid.coregl.CoreVBO;

public class LinesMain implements RenderLoopCallback {
  private final CoreShader shader;
  private final CoreVAO src;
  private float totalTime;
  private final CoreVBO vbo;

  public LinesMain() {
    shader = CoreShader.newShaderWithVertexAttributes("aVertex");
    shader.vertexShader("line.vs");
    shader.fragmentShader("line.fs");
    shader.geometryShader("line.gs");
    shader.link();

    src = new CoreVAO();
    src.bind();

    glEnable(GL_BLEND);

    vbo = CoreVBO.createDynamic(new float[8]);
    src.enableVertexAttributef(0, 2, 2, 0);

    shader.activate();
    shader.setUniformMatrix4f("uMvp", CoreMatrixFactory.createOrtho(0, 1024.f, 768.f, 0));
    float w = 5.f;
    float r = 10.f;
    shader.setUniformf("totalWidth", (2*r + w));
    shader.setUniformf("halfWidth", (2*r + w) / 2.f);
    shader.setUniformf("sx", (r / ((2*r + w) / 2.f)));
  }

  @Override
  public boolean render(final float deltaTime) {
    glClearColor(0.f, 0.f, 0.f, 1.f);
    glClear(GL_COLOR_BUFFER_BIT);

    totalTime += deltaTime;

    double x0 = Math.cos(totalTime/1000.)*200 + 400;
    double y0 = Math.sin(totalTime/1000.)*200 + 400;
    double x1 = Math.cos(totalTime/5000. + 500)*200 + 400;
    double y1 = Math.sin(totalTime/5000. + 500)*200 + 400;
    double x2 = Math.cos(totalTime/6000. + 750)*200 + 400;
    double y2 = Math.sin(totalTime/6000. + 750)*200 + 400;
    double x3 = Math.cos(totalTime/2000. + 250)*200 + 400;
    double y3 = Math.sin(totalTime/2000. + 250)*200 + 400;
    FloatBuffer buffer = vbo.getBuffer();
    buffer.put((float)x0);
    buffer.put((float)y0);
    buffer.put((float)x1);
    buffer.put((float)y1);
    buffer.put((float)x2);
    buffer.put((float)y2);
    buffer.put((float)x3);
    buffer.put((float)y3);
    buffer.rewind();
    vbo.send();

    CoreRender.renderLines(4);

    return false;
  }

  public static void main(final String[] args) throws Exception {
    CoreLwjglSetup setup = new CoreLwjglSetup();
    setup.initializeLogging(); // optional to get jdk14 to better format the log
    setup.initialize("Hello Lines", 1024, 768);
    setup.renderLoop(new LinesMain());
  }
}
