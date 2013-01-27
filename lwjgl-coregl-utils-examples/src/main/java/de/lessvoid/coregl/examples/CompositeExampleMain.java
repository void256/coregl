package de.lessvoid.coregl.examples;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DST_ALPHA;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_DST_ALPHA;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_ZERO;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import de.lessvoid.coregl.CoreLwjglSetup;
import de.lessvoid.coregl.CoreLwjglSetup.RenderLoopCallback;
import de.lessvoid.coregl.CoreMatrixFactory;
import de.lessvoid.coregl.CoreRender;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreVAO;
import de.lessvoid.coregl.CoreVBO;

public class CompositeExampleMain implements RenderLoopCallback {
  private final CoreShader shader;
  private final CoreVAO src;
  private final CoreVAO dst;
  private final CoreVAO white;

  public CompositeExampleMain() {
    shader = CoreShader.newShaderWithVertexAttributes("aVertex", "aColor");
    shader.vertexShader("plain-color.vs");
    shader.fragmentShader("plain-color.fs");
    shader.link();

    src = new CoreVAO();
    src.bind();

    CoreVBO.createStaticAndSend(new float[] {
          0.f,   0.f,    1.f, 1.f, 0.f, 1.f,
        100.f,   0.f,    1.f, 1.f, 0.f, 1.f,
          0.f, 100.f,    1.f, 1.f, 0.f, 1.f,
        100.f,   0.f,    0.f, 0.f, 0.f, 0.f,
          0.f, 100.f,    0.f, 0.f, 0.f, 0.f,
        100.f, 100.f,    0.f, 0.f, 0.f, 0.f,
    });

    src.enableVertexAttributef(0, 2, 6, 0);
    src.enableVertexAttributef(1, 4, 6, 2);

    dst = new CoreVAO();
    dst.bind();

    CoreVBO.createStaticAndSend(new float[] {
          0.f,   0.f,    0.f, 0.f, 1.f, 1.f,
        100.f,   0.f,    0.f, 0.f, 1.f, 1.f,
        100.f, 100.f,    0.f, 0.f, 1.f, 1.f,
          0.f,   0.f,    0.f, 0.f, 0.f, 0.f,
          0.f, 100.f,    0.f, 0.f, 0.f, 0.f,
        100.f, 100.f,    0.f, 0.f, 0.f, 0.f,
    });

    dst.enableVertexAttributef(0, 2, 6, 0);
    dst.enableVertexAttributef(1, 4, 6, 2);

    white = new CoreVAO();
    white.bind();

    CoreVBO.createStaticAndSend(new float[] {
          0.f,   0.f,    1.f, 1.f, 1.f, 1.f,
        100.f,   0.f,    1.f, 1.f, 1.f, 1.f,
        100.f, 100.f,    1.f, 1.f, 1.f, 1.f,
          0.f,   0.f,    1.f, 1.f, 1.f, 1.f,
          0.f, 100.f,    1.f, 1.f, 1.f, 1.f,
        100.f, 100.f,    1.f, 1.f, 1.f, 1.f,
    });

    white.enableVertexAttributef(0, 2, 6, 0);
    white.enableVertexAttributef(1, 4, 6, 2);

    shader.activate();
    shader.setUniformMatrix4f("uMvp", CoreMatrixFactory.createOrtho(0, 1024.f, 768.f, 0));

    glEnable(GL_BLEND);
  }

  @Override
  public boolean render(final float deltaTime) {
    glClearColor(1.f, 1.f, 1.f, 1.f);
    glClear(GL_COLOR_BUFFER_BIT);

    float x = 100.f;
    float y = 100.f;

    // first row
    renderDst(x, y);
    glBlendFunc(GL_ZERO, GL_ZERO);
    renderSrc(x, y);
    renderWhite(x, y);
    x += 150.f;

    renderDst(x, y);
    glBlendFunc(GL_ONE, GL_ZERO);
    renderSrc(x, y);
    renderWhite(x, y);
    x += 150.f;

    renderDst(x, y);
    glBlendFunc(GL_ZERO, GL_ONE);
    renderSrc(x, y);
    renderWhite(x, y);
    x += 150.f;

    renderDst(x, y);
    glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
    renderSrc(x, y);
    renderWhite(x, y);
    x += 150.f;

    renderDst(x, y);
    glBlendFunc(GL_ONE_MINUS_DST_ALPHA, GL_ONE);
    renderSrc(x, y);
    renderWhite(x, y);
    x += 150.f;

    renderDst(x, y);
    glBlendFunc(GL_DST_ALPHA, GL_ZERO);
    renderSrc(x, y);
    renderWhite(x, y);

    x  = 100.f;
    y += 150.f;

    // second row
    renderDst(x, y);
    glBlendFunc(GL_ZERO, GL_SRC_ALPHA);
    renderSrc(x, y);
    renderWhite(x, y);
    x += 150.f;

    renderDst(x, y);
    glBlendFunc(GL_ONE_MINUS_DST_ALPHA, GL_ZERO);
    renderSrc(x, y);
    renderWhite(x, y);
    x += 150.f;

    renderDst(x, y);
    glBlendFunc(GL_ZERO, GL_ONE_MINUS_SRC_ALPHA);
    renderSrc(x, y);
    renderWhite(x, y);
    x += 150.f;

    renderDst(x, y);
    glBlendFunc(GL_DST_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    renderSrc(x, y);
    renderWhite(x, y);
    x += 150.f;

    renderDst(x, y);
    glBlendFunc(GL_ONE_MINUS_DST_ALPHA, GL_SRC_ALPHA);
    renderSrc(x, y);
    renderWhite(x, y);
    x += 150.f;

    renderDst(x, y);
    glBlendFunc(GL_ONE_MINUS_DST_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    renderSrc(x, y);
    renderWhite(x, y);

    return false;
  }

  private void renderDst(final float x, final float y) {
    shader.setUniformf("uOffset", x, y);
    glDisable(GL_BLEND);
    dst.bind();
    CoreRender.renderTriangles(6);
  }

  private void renderWhite(final float x, final float y) {
    shader.setUniformf("uOffset", x, y);
    glBlendFunc(GL_ONE_MINUS_DST_ALPHA, GL_ONE);
    white.bind();
    CoreRender.renderTriangles(6);
  }

  private void renderSrc(final float x, final float y) {
    shader.setUniformf("uOffset", x, y);

    glEnable(GL_BLEND);
    src.bind();
    CoreRender.renderTriangles(6);
  }

  public static void main(final String[] args) throws Exception {
    CoreLwjglSetup setup = new CoreLwjglSetup();
    setup.initializeLogging(); // optional to get jdk14 to better format the log
    setup.initialize("Hello Compositing", 1024, 768);
    setup.renderLoop(new CompositeExampleMain());
  }
}
