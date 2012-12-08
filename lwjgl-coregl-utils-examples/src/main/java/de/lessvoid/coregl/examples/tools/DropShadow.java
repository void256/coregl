package de.lessvoid.coregl.examples.tools;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import de.lessvoid.coregl.CoreVBO;
import de.lessvoid.coregl.CoreMatrixFactory;
import de.lessvoid.coregl.CoreRender;
import de.lessvoid.coregl.CoreRenderToTexture;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreTexture2D;
import de.lessvoid.coregl.CoreTextureBuffer;

public class DropShadow {
  private CoreShader black;
  private Matrix4f scale = new Matrix4f();
  private int shadowSize = 100;
  private float overDraw = 40;
  private CoreRenderToTexture render1;
  private CoreRenderToTexture render2;
  private CoreRenderToTexture render3;
  private CoreTexture2D image;
  private CoreVBO quad;
  private CoreVBO fullQuad;
  private CoreShader plainTextureShader;
  private float angle = 0;

  private CoreTextureBuffer tbo;
  private CoreShader tboShader;

  public DropShadow(final CoreTexture2D image) {
    this.image = image;

    Matrix4f projection = CoreMatrixFactory.createProjection2(75.f, 1024.f / 768.f, 1f, 1000.f);

    int shadowTextureWidth = image.getWidth() + 2*shadowSize;
    int shadowTextureHeight = image.getHeight() + 2*shadowSize;

    float kernelValues[] = new float[101];
    calcGaussKernel(kernelValues);

    tbo = new CoreTextureBuffer(kernelValues);
    tboShader = CoreShader.newShaderWithVertexAttributes("vVertex", "vTexCoords");
    tboShader.fragmentShader("blur2.fs");
    tboShader.vertexShader("blur2.vs");
    tboShader.activate();
    tboShader.setUniformMatrix4f("projection", CoreMatrixFactory.createProjection(0, shadowTextureWidth, 0, shadowTextureHeight));
    tboShader.setUniformf("texture", 0);
    tboShader.setUniformf("off", 0.f, 0.f);
    tboShader.setUniformf("blurSize", 1.f/((shadowTextureWidth)*1f));
    tboShader.setUniformf("lookup", 1);

    black = CoreShader.newShaderWithVertexAttributes("vVertex", "vTexCoords");
    black.fragmentShader("dropShadow-black.fs");
    black.vertexShader("nifty.vs");
    black.activate();
    black.setUniformMatrix4f("matProj", CoreMatrixFactory.createProjection(0, shadowTextureWidth, 0, shadowTextureHeight));
    black.setUniformMatrix4f("matScale", scale);
    black.setUniformf("off", 0.f, 0.f);
    black.setUniformf("tex", 0);

    plainTextureShader = CoreShader.newShaderWithVertexAttributes("vVertex", "vTexCoords");
    plainTextureShader.fragmentShader("nifty-fixedalpha.fs");
    plainTextureShader.vertexShader("nifty.vs");
    plainTextureShader.activate();
    plainTextureShader.setUniformMatrix4f("matScale", scale);
    plainTextureShader.setUniformMatrix4f("matProj", projection);
    plainTextureShader.setUniformf("tex", 0);
    plainTextureShader.setUniformf("alpha", 0.25f);

    render1 = new CoreRenderToTexture(shadowTextureWidth, shadowTextureHeight);
    render2 = new CoreRenderToTexture(shadowTextureWidth, shadowTextureHeight);
    render3 = new CoreRenderToTexture(shadowTextureWidth, shadowTextureHeight);

    quad = createQuadVBO(shadowSize - overDraw, shadowSize - overDraw, image.getWidth() + 2*overDraw, image.getHeight() + 2*overDraw);
    fullQuad = createQuadVBO(0, 0, shadowTextureWidth, shadowTextureHeight);

    prepare();
  }

  private float gauss(final float x, final float mu, final float sigma) {
    return (float)Math.exp(-Math.pow(((x-mu)/(sigma)), 2)/2.0f);
  }

  private void calcGaussKernel(final float[] values) {
    float kernelRadius = (values.length - 1) / 2;
    float sigma = kernelRadius / 2.f;
    float sum = 0;

    for (int i=0; i<values.length; i++) {
      float current = gauss(i, kernelRadius, sigma);
      values[i] = current;
      sum += values[i];
    }

    for (int i=0; i<values.length; i++) {
      values[i] = values[i] / sum;
    }
  }

  public void prepare() {
    render1.on();

      glClearColor(0.0f, 0.f, 0.f, 0.f);
      glClear(GL_COLOR_BUFFER_BIT);

      black.activate();
      image.bind();
      quad.bind();
      CoreRender.renderTriangles(2);

    render2.on();

      glClearColor(0.0f, 0.f, 0.f, 0.f);
      glClear(GL_COLOR_BUFFER_BIT);

      tboShader.activate();
      tboShader.setUniformf("dir", 1.f, 0.f);
      glActiveTexture(GL_TEXTURE0);
      render1.bindTexture();
      glActiveTexture(GL_TEXTURE1);
      tbo.bind();
      glActiveTexture(GL_TEXTURE0);
      fullQuad.bind();
      CoreRender.renderTriangles(2);

    render3.on();

      glClearColor(0.f, 0.f, 0.f, 0.f);
      glClear(GL_COLOR_BUFFER_BIT);

      tboShader.setUniformf("dir", 0.f, 1.f);
      render2.bindTexture();
      fullQuad.bind();
      CoreRender.renderTriangles(2);

    render3.off();
  }

  public Matrix4f render(final float x, final float y) {
    plainTextureShader.activate();
    plainTextureShader.setUniformf("off", x, y);
    Matrix4f projection = CoreMatrixFactory.createProjection(0, 1024, 0, 768);

    Matrix4f move1 = new Matrix4f();
    move1.translate(new Vector2f(x + 256, y + 256));

    Matrix4f move2 = new Matrix4f();
    move2.translate(new Vector2f(-(x + 256), -(y + 256)));

    Matrix4f rotate = new Matrix4f();
    rotate.rotate(angle, new Vector3f(0.f, 0.f, 1.f));
//    angle += 0.001;

    Matrix4f matrix = new Matrix4f();
    Matrix4f temp1 = new Matrix4f();
    Matrix4f temp2 = new Matrix4f();
    Matrix4f.mul(rotate, move2, temp1);
    Matrix4f.mul(move1, temp1, temp2);
    Matrix4f.mul(projection, temp2, matrix);

    plainTextureShader.setUniformMatrix4f("matProj", matrix);
    render3.bindTexture();
    fullQuad.bind();
    CoreRender.renderTriangles(2);
    return matrix;
  }

  private static CoreVBO createQuadVBO(final float x, final float y, final float w, final float h) {
    return CoreVBO.createStatic(new float[] {
        x,     y,     0.f, 0.f,
        x + w, y,     1.f, 0.f,
        x,     y + h, 0.f, 1.f,
        x + w, y + h, 1.f, 1.f,
    });
  }
}
