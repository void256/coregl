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

public class LineCapsMain implements RenderLoopCallback {
  private final CoreShader lineShaderButtCaps;
  private final CoreShader lineShaderSquareCaps;
  private final CoreShader lineShaderRoundCaps;
  private final CoreVAO src;
  private final CoreVBO vbo;

  public LineCapsMain() {
    lineShaderButtCaps = CoreShader.newShaderWithVertexAttributes("aVertex");
    lineShaderButtCaps.vertexShader("line2.vs");
    lineShaderButtCaps.fragmentShader("line-butt-cap.fs");
    lineShaderButtCaps.geometryShader("line-butt-cap.gs");
    lineShaderButtCaps.link();

    lineShaderRoundCaps = CoreShader.newShaderWithVertexAttributes("aVertex");
    lineShaderRoundCaps.vertexShader("line2.vs");
    lineShaderRoundCaps.fragmentShader("line-round-cap.fs");
    lineShaderRoundCaps.geometryShader("line-round-cap.gs");
    lineShaderRoundCaps.link();

    lineShaderSquareCaps = CoreShader.newShaderWithVertexAttributes("aVertex");
    lineShaderSquareCaps.vertexShader("line2.vs");
    lineShaderSquareCaps.fragmentShader("line-square-cap.fs");
    lineShaderSquareCaps.geometryShader("line-square-cap.gs");
    lineShaderSquareCaps.link();

    src = new CoreVAO();
    src.bind();

    glEnable(GL_BLEND);

    vbo = CoreVBO.createDynamic(new float[8]);
    src.enableVertexAttributef(0, 2, 2, 0);
  }

  @Override
  public boolean render(final float deltaTime) {
    glClearColor(1.f, 1.f, 1.f, 1.f);
    glClear(GL_COLOR_BUFFER_BIT);

    float w = 50.f;
    float r = 1.f;

    lineShaderButtCaps.activate();
    lineShaderButtCaps.setUniformMatrix4f("uMvp", CoreMatrixFactory.createOrtho(0, 1024.f, 768.f, 0));
    lineShaderButtCaps.setUniformf("totalWidth", (2*r + w));
    lineShaderButtCaps.setUniformf("r2", (2*r));
    lineShaderButtCaps.setUniformf("halfWidth", (2*r + w) / 2.f);
    lineShaderButtCaps.setUniformf("halfWidthMinus2R", (2*r + w) / 2.f - 2 * r);
    lineShaderButtCaps.setUniformf("lineColor", 0.f, 0.f, 0.f, 1.f);
    FloatBuffer buffer = vbo.getBuffer();
    buffer.put(100.f);
    buffer.put(100.f);
    buffer.put(924.f);
    buffer.put(300.f);
    buffer.rewind();
    vbo.send();
    CoreRender.renderLines(2);

    lineShaderRoundCaps.activate();
    lineShaderRoundCaps.setUniformMatrix4f("uMvp", CoreMatrixFactory.createOrtho(0, 1024.f, 768.f, 0));
    lineShaderRoundCaps.setUniformf("totalWidth", (2*r + w));
    lineShaderRoundCaps.setUniformf("r2", (2*r));
    lineShaderRoundCaps.setUniformf("halfWidth", (2*r + w) / 2.f);
    lineShaderRoundCaps.setUniformf("halfWidthMinus2R", (2*r + w) / 2.f - 2 * r);
    lineShaderRoundCaps.setUniformf("lineColor", 0.f, 0.f, 0.f, 1.f);
    buffer = vbo.getBuffer();
    buffer.put(100.f);
    buffer.put(300.f);
    buffer.put(924.f);
    buffer.put(500.f);
    buffer.rewind();
    vbo.send();
    CoreRender.renderLines(2);

    lineShaderSquareCaps.activate();
    lineShaderSquareCaps.setUniformMatrix4f("uMvp", CoreMatrixFactory.createOrtho(0, 1024.f, 768.f, 0));
    lineShaderSquareCaps.setUniformf("totalWidth", (2*r + w));
    lineShaderSquareCaps.setUniformf("r2", (2*r));
    lineShaderSquareCaps.setUniformf("halfWidth", (2*r + w) / 2.f);
    lineShaderSquareCaps.setUniformf("halfWidthMinus2R", (2*r + w) / 2.f - 2 * r);
    lineShaderSquareCaps.setUniformf("lineColor", 0.f, 0.f, 0.f, 1.f);
    buffer = vbo.getBuffer();
    buffer.put(100.f);
    buffer.put(200.f);
    buffer.put(924.f);
    buffer.put(400.f);
    buffer.rewind();
    vbo.send();
    CoreRender.renderLines(2);

    // Draw white lines above

    w = 2.f;
    r = 1.f;
    lineShaderRoundCaps.activate();
    lineShaderRoundCaps.setUniformf("lineColor", 1.f, 1.f, 1.f, 1.f);
    lineShaderRoundCaps.setUniformf("totalWidth", (2*r + w));
    lineShaderRoundCaps.setUniformf("r2", (2*r));
    lineShaderRoundCaps.setUniformf("halfWidth", (2*r + w) / 2.f);
    lineShaderRoundCaps.setUniformf("halfWidthMinus2R", (2*r + w) / 2.f - 2 * r);

    buffer = vbo.getBuffer();
    buffer.put(100.f);
    buffer.put(100.f);
    buffer.put(924.f);
    buffer.put(300.f);
    buffer.rewind();
    vbo.send();
    CoreRender.renderLines(2);

    buffer = vbo.getBuffer();
    buffer.put(100.f);
    buffer.put(200.f);
    buffer.put(924.f);
    buffer.put(400.f);
    buffer.rewind();
    vbo.send();
    CoreRender.renderLines(2);

    buffer = vbo.getBuffer();
    buffer.put(100.f);
    buffer.put(300.f);
    buffer.put(924.f);
    buffer.put(500.f);
    buffer.rewind();
    vbo.send();
    CoreRender.renderLines(2);

    return false;
  }

  public static void main(final String[] args) throws Exception {
    CoreLwjglSetup setup = new CoreLwjglSetup();
    setup.initializeLogging(); // optional to get jdk14 to better format the log
    setup.initialize("Hello line caps", 1024, 768);
    setup.renderLoop(new LineCapsMain());
  }
}
