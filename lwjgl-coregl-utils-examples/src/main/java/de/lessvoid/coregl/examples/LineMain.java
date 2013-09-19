package de.lessvoid.coregl.examples;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.charset.Charset;

import org.lwjgl.util.vector.Matrix4f;

import de.lessvoid.coregl.CoreLwjglSetup;
import de.lessvoid.coregl.CoreLwjglSetup.RenderLoopCallback;
import de.lessvoid.coregl.CoreMatrixFactory;
import de.lessvoid.coregl.CoreRender;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreVAO;
import de.lessvoid.coregl.CoreVBO;

public class LineMain implements RenderLoopCallback {
  private static final Matrix4f ORTHO = CoreMatrixFactory.createOrtho(0, 1024.f, 768.f, 0);
  private final CoreShader lineShader1;
  private final CoreShader lineShader2;
  private final CoreVAO src;
  private final CoreVBO vbo;
  private float totalTime;

  public LineMain() throws Exception {
    lineShader1 = CoreShader.newShaderWithVertexAttributes("aVertex");
    lineShader1.vertexShader("line/line.vs");
    lineShader1.geometryShader("line/line.gs", stream("#version 150 core\n#define LINE_CAP_ROUND\n#define LINE_JOIN_MITER\n"));
    lineShader1.fragmentShader("line/line.fs", stream("#version 150 core\n#define LINE_CAP_ROUND\n#define LINE_JOIN_MITER\n"));
    lineShader1.link();

    lineShader2 = CoreShader.newShaderWithVertexAttributes("aVertex");
    lineShader2.vertexShader("line/line.vs");
    lineShader2.geometryShader("line/line.gs", stream("#version 150 core\n#define LINE_CAP_BUTT\n#define LINE_JOIN_NONE\n"));
    lineShader2.fragmentShader("line/line.fs", stream("#version 150 core\n#define LINE_CAP_BUTT\n#define LINE_JOIN_NONE\n"));
    lineShader2.link();

    src = new CoreVAO();
    src.bind();

    glEnable(GL_BLEND);

    vbo = CoreVBO.createDynamic(new float[2*5]);
    src.enableVertexAttributef(0, 2, 2, 0);
    totalTime = 0;
  }

  private InputStream stream(final String data) {
    return new ByteArrayInputStream(data.getBytes(Charset.forName("ISO-8859-1")));
  }

  @Override
  public boolean render(final float deltaTime) {
    glClearColor(1.f, 1.f, 1.f, 1.f);
    glClear(GL_COLOR_BUFFER_BIT);

    totalTime += deltaTime;
    float w = 50.f;
    float r = 1.f;

    lineShader1.activate();
    lineShader1.setUniformMatrix4f("uMvp", ORTHO);
    lineShader1.setUniformf("lineColor", 0.f, 0.f, 0.f, 1.f);
    lineShader1.setUniformf("lineParameters", (2*r + w), (2*r + w) / 2.f, (2*r + w) / 2.f - 2 * r, (2*r));

    FloatBuffer buffer = vbo.getBuffer();
    buffer.put(100.f);
    buffer.put(100.f);
    buffer.put(100.f);
    buffer.put(100.f);
    buffer.put(600.f);
    buffer.put(300.f);
    buffer.put(600.f + (float)Math.cos(totalTime/1500.f)*200.f);
    buffer.put(300.f + (float)Math.sin(totalTime/1500.f)*200.f);
    buffer.put(600.f + (float)Math.cos(totalTime/1500.f)*200.f);
    buffer.put(300.f + (float)Math.sin(totalTime/1500.f)*200.f);
    buffer.rewind();
    vbo.send();
    CoreRender.renderLinesAdjacent(5);

    // Draw thin purple lines above
    w = 2.f;
    r = 0.75f;
    lineShader2.activate();
    lineShader2.setUniformMatrix4f("uMvp", ORTHO);
    lineShader2.setUniformf("lineColor", 1.f, 0.f, 1.f, 1.f);
    lineShader2.setUniformf("lineParameters", (2*r + w), (2*r + w) / 2.f, (2*r + w) / 2.f - 2 * r, (2*r));
    CoreRender.renderLinesAdjacent(5);

    return false;
  }

  public static void main(final String[] args) throws Exception {
    CoreLwjglSetup setup = new CoreLwjglSetup();
    setup.initializeLogging();
    setup.initialize("Hello line caps", 1024, 768);
    setup.renderLoop(new LineMain());
  }
}
