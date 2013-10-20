package de.lessvoid.coregl.examples;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_STENCIL_TEST;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearStencil;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glStencilMask;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL20.glStencilFuncSeparate;
import static org.lwjgl.opengl.GL20.glStencilOpSeparate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.charset.Charset;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import de.lessvoid.coregl.CoreLwjglSetup;
import de.lessvoid.coregl.CoreLwjglSetup.RenderLoopCallback;
import de.lessvoid.coregl.CoreTexture2D.ColorFormat;
import de.lessvoid.coregl.CoreTexture2D.ResizeFilter;
import de.lessvoid.coregl.CoreFBO;
import de.lessvoid.coregl.CoreMatrixFactory;
import de.lessvoid.coregl.CoreRender;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreTexture2D;
import de.lessvoid.coregl.CoreVAO;
import de.lessvoid.coregl.CoreVBO;
import de.lessvoid.simpleimageloader.ImageData;
import de.lessvoid.simpleimageloader.SimpleImageLoader;
import de.lessvoid.simpleimageloader.SimpleImageLoaderConfig;

public class LineMain implements RenderLoopCallback {
  private static final Matrix4f ORTHO = CoreMatrixFactory.createOrtho(0, 1024.f, 768.f, 0);
  private final CoreShader texture;
  private final CoreShader lineShader1;
  private final CoreShader lineShader2;
  private final CoreVAO src;
  private final CoreVBO vbo;
  private final CoreVBO vboQuad;
  private final CoreVBO vboBackground;
  private final CoreFBO fbo;
  private float totalTime;
  private final CoreTexture2D fboTexture;
  private final CoreShader backgroundShader;
  private float time;

  public LineMain() throws Exception {

    texture = CoreShader.newShaderWithVertexAttributes("aVertex", "aUV");
    texture.vertexShader("line/line-pass2.vs");
    texture.fragmentShader("line/line-pass2.fs");
    texture.link();

    lineShader1 = CoreShader.newShaderWithVertexAttributes("aVertex");
    lineShader1.vertexShader("line/line.vs");
    lineShader1.geometryShader("line/line.gs", stream("#version 150 core\n#define CAP_ROUND\n#define JOIN_NONE\n"));
    lineShader1.fragmentShader("line/line.fs", stream("#version 150 core\n#define CAP_ROUND\n#define JOIN_NONE\n"));
    lineShader1.link();

    lineShader2 = CoreShader.newShaderWithVertexAttributes("aVertex");
    lineShader2.vertexShader("line/line.vs");
    lineShader2.geometryShader("line/line.gs", stream("#version 150 core\n#define CAP_BUTT\n#define JOIN_NONE\n"));
    lineShader2.fragmentShader("line/line.fs", stream("#version 150 core\n#define CAP_BUTT\n#define JOIN_NONE\n"));
    lineShader2.link();

    src = new CoreVAO();
    src.bind();

    vbo = CoreVBO.createDynamic(new float[2*5]);
    src.enableVertexAttributef(0, 2, 2, 0);
    totalTime = 0;

    vboQuad = CoreVBO.createDynamic(new float[4*4]);
    vboBackground = CoreVBO.createDynamic(new float[5*4]);

    fbo = new CoreFBO();
    fbo.bindFramebuffer();

    fboTexture = CoreTexture2D.createEmptyTexture(ColorFormat.Red, GL11.GL_UNSIGNED_BYTE, Display.getWidth(), Display.getHeight(), ResizeFilter.Linear);
    fbo.attachTexture(fboTexture.getTextureId(), 0);

    backgroundShader = CoreShader.newShaderWithVertexAttributes("aVertex", "aColor");
    backgroundShader.vertexShader("background-gradient.vs");
    backgroundShader.fragmentShader("background-gradient.fs");
    backgroundShader.link();
  }

  private InputStream stream(final String data) {
    return new ByteArrayInputStream(data.getBytes(Charset.forName("ISO-8859-1")));
  }

  @Override
  public boolean render(final float deltaTime) {
    time += deltaTime;

    fbo.bindFramebuffer();
    glViewport(0, 0, Display.getWidth(), Display.getHeight());

    glClearColor(0.f, 0.f, 0.f, 0.f);
    glClear(GL_COLOR_BUFFER_BIT);
//    glClearStencil(0);
//    glStencilFuncSeparate(GL_FRONT_AND_BACK, GL_EQUAL, 0, 0xff);
//    glStencilOpSeparate(GL_FRONT_AND_BACK, GL_KEEP, GL_KEEP, GL_INCR);
//    glStencilMask(0xFF);
//    glEnable(GL_STENCIL_TEST);
    
    glEnable(GL_BLEND);
    glBlendEquationSeparate(GL_MAX, GL_MAX);

    totalTime += deltaTime;
    float w = 100.f;
    float r = 2.f;

    lineShader1.activate();
    lineShader1.setUniformMatrix4f("uMvp", CoreMatrixFactory.createOrtho(0, 1024.f, 0, 768.f));
    lineShader1.setUniformf("lineColor", 1.f, 1.f, 1.f, (float)((Math.sin(time/1700.f) + 1.0) / 2.0));
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
    src.enableVertexAttributef(0, 2, 2, 0);
    src.disableVertexAttribute(1);
    vbo.send();
    CoreRender.renderLinesAdjacent(5);
    fbo.disable();

    // Render background
    glDisable(GL_BLEND);

    FloatBuffer background = vboBackground.getBuffer();
    background.put(0.f);
    background.put(0.f);
    background.put(1.f);
    background.put(0.f);
    background.put(0.f);

    background.put(0.f);
    background.put(0.f + 768);
    background.put(0.0f);
    background.put(1.0f);
    background.put(0.0f);

    background.put(0.f + 1024);
    background.put(0.f);
    background.put(0.0f);
    background.put(0.0f);
    background.put(1.0f);

    background.put(0.f + 1024);
    background.put(0.f + 768);
    background.put(1.0f);
    background.put(1.0f);
    background.put(1.0f);
    background.rewind();

    vboBackground.bind();
    src.enableVertexAttributef(0, 2, 5, 0);
    src.enableVertexAttributef(1, 3, 5, 2);
    vboBackground.send();

    glViewport(0, 0, Display.getWidth(), Display.getHeight());
    backgroundShader.activate();
    backgroundShader.setUniformMatrix4f("uMvp", ORTHO);
    CoreRender.renderTriangleStrip(4);

    // Render lines fbo

    glEnable(GL_BLEND);
    glBlendFunc (GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glBlendEquationSeparate(GL_FUNC_ADD, GL_FUNC_ADD);

    FloatBuffer quad = vboQuad.getBuffer();
    quad.put(0.f);
    quad.put(0.f);
    quad.put(0.f);
    quad.put(0.f);

    quad.put(0.f);
    quad.put(0.f + 768);
    quad.put(0.0f);
    quad.put(1.0f);

    quad.put(0.f + 1024);
    quad.put(0.f);
    quad.put(1.0f);
    quad.put(0.0f);

    quad.put(0.f + 1024);
    quad.put(0.f + 768);
    quad.put(1.0f);
    quad.put(1.0f);
    quad.rewind();

    vboQuad.bind();
    src.enableVertexAttributef(0, 2, 4, 0);
    src.enableVertexAttributef(1, 2, 4, 2);
    vboQuad.send();

    fboTexture.bind();
    texture.activate();
    texture.setUniformMatrix4f("uMvp", ORTHO);
    texture.setUniformi("uTexture", 0);
    texture.setUniformf("lineColor", 1.f, 1.f, 1.f, 1.f);
    CoreRender.renderTriangleStrip(4);
    /*
    glDisable(GL_STENCIL_TEST);

    // Draw thin purple lines above
    w = 1.f;
    r = 0.8f;
    lineShader2.activate();
    lineShader2.setUniformMatrix4f("uMvp", ORTHO);
    lineShader2.setUniformf("lineColor", 1.f, 0.f, 1.f, 1.f);
    lineShader2.setUniformf("lineParameters", (2*r + w), (2*r + w) / 2.f, (2*r + w) / 2.f - 2 * r, (2*r));
    CoreRender.renderLinesAdjacent(5);
*/
    return false;
  }

  public static void main(final String[] args) throws Exception {
    CoreLwjglSetup setup = new CoreLwjglSetup();
    setup.initializeLogging();
    setup.initialize("Hello line caps", 1024, 768);
    setup.renderLoop(new LineMain());
  }
}
