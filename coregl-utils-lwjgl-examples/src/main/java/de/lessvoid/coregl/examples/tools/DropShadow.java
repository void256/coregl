package de.lessvoid.coregl.examples.tools;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import de.lessvoid.coregl.CoreFBO;
import de.lessvoid.coregl.CoreFactory;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreTexture2D;
import de.lessvoid.coregl.CoreTexture2D.ColorFormat;
import de.lessvoid.coregl.CoreTexture2D.ResizeFilter;
import de.lessvoid.coregl.CoreTexture2D.Type;
import de.lessvoid.coregl.CoreTextureBuffer;
import de.lessvoid.coregl.CoreVBO;
import de.lessvoid.math.Mat4;
import de.lessvoid.math.MatrixFactory;

public class DropShadow {
  private CoreShader black;
  private Mat4 scale = new Mat4();
  private int shadowSize = 100;
  private float overDraw = 40;
  private CoreFBO render;
  private CoreTexture2D render1;
  private CoreTexture2D render2;
  private CoreTexture2D render3;
  private CoreTexture2D image;
  private CoreVBO quad;
  private CoreVBO fullQuad;
  private CoreShader plainTextureShader;
  private float angle = 0;

  private CoreTextureBuffer tbo;
  private CoreShader tboShader;

  private int shadowTextureWidth;
  private int shadowTextureHeight;
  private CoreFactory factory;

  public DropShadow(final CoreFactory factory, final CoreTexture2D image) {
    this.factory = factory;
    this.image = image;

    Mat4 projection = MatrixFactory.createProjection2(75.f, 1024.f / 768.f, 1f, 1000.f);

    shadowTextureWidth = image.getWidth() + 2*shadowSize;
    shadowTextureHeight = image.getHeight() + 2*shadowSize;

    float kernelValues[] = new float[101];
    calcGaussKernel(kernelValues);

    tbo = factory.createCoreTextureBuffer(kernelValues);
    tboShader = factory.newShaderWithVertexAttributes("vVertex", "vTexCoords");
    tboShader.fragmentShader("blur2.fs");
    tboShader.vertexShader("blur2.vs");
    tboShader.activate();
    tboShader.setUniformMatrix4f("projection", MatrixFactory.createProjection(0, shadowTextureWidth, 0, shadowTextureHeight).toBuffer());
    tboShader.setUniformf("texture", 0);
    tboShader.setUniformf("off", 0.f, 0.f);
    tboShader.setUniformf("blurSize", 1.f/((shadowTextureWidth)*1f));
    tboShader.setUniformf("lookup", 1);

    black = factory.newShaderWithVertexAttributes("vVertex", "vTexCoords");
    black.fragmentShader("dropShadow-black.fs");
    black.vertexShader("nifty.vs");
    black.activate();
    black.setUniformMatrix4f("matProj", MatrixFactory.createProjection(0, shadowTextureWidth, 0, shadowTextureHeight).toBuffer());
    black.setUniformMatrix4f("matScale", scale.toBuffer());
    black.setUniformf("off", 0.f, 0.f);
    black.setUniformf("tex", 0);

    plainTextureShader = factory.newShaderWithVertexAttributes("vVertex", "vTexCoords");
    plainTextureShader.fragmentShader("nifty-fixedalpha.fs");
    plainTextureShader.vertexShader("nifty.vs");
    plainTextureShader.activate();
    plainTextureShader.setUniformMatrix4f("matScale", scale.toBuffer());
    plainTextureShader.setUniformMatrix4f("matProj", projection.toBuffer());
    plainTextureShader.setUniformf("tex", 0);
    plainTextureShader.setUniformf("alpha", 0.25f);

    render = factory.createCoreFBO();
    render1 = factory.createEmptyTexture(ColorFormat.RGBA, Type.UNSIGNED_BYTE, shadowTextureWidth, shadowTextureHeight, ResizeFilter.Linear);
    render2 = factory.createEmptyTexture(ColorFormat.RGBA, Type.UNSIGNED_BYTE, shadowTextureWidth, shadowTextureHeight, ResizeFilter.Linear);
    render3 = factory.createEmptyTexture(ColorFormat.RGBA, Type.UNSIGNED_BYTE, shadowTextureWidth, shadowTextureHeight, ResizeFilter.Linear);

    quad = createQuadVBO(factory, shadowSize - overDraw, shadowSize - overDraw, image.getWidth() + 2*overDraw, image.getHeight() + 2*overDraw);
    fullQuad = createQuadVBO(factory, 0, 0, shadowTextureWidth, shadowTextureHeight);

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
    render.bindFramebuffer();
    render.attachTexture(render1.getTextureId(), 0);
      glViewport(0, 0, shadowTextureWidth, shadowTextureHeight);

      glClearColor(0.0f, 0.f, 0.f, 0.f);
      glClear(GL_COLOR_BUFFER_BIT);

      black.activate();
      image.bind();
      quad.bind();
      factory.getCoreRender().renderTriangles(2);

   render.attachTexture(render2.getTextureId(), 0);

      glClearColor(0.0f, 0.f, 0.f, 0.f);
      glClear(GL_COLOR_BUFFER_BIT);

      tboShader.activate();
      tboShader.setUniformf("dir", 1.f, 0.f);
      glActiveTexture(GL_TEXTURE0);
      render1.bind();
      glActiveTexture(GL_TEXTURE1);
      tbo.bind();
      glActiveTexture(GL_TEXTURE0);
      fullQuad.bind();
      factory.getCoreRender().renderTriangles(2);

    render.attachTexture(render3.getTextureId(), 0);

      glClearColor(0.f, 0.f, 0.f, 0.f);
      glClear(GL_COLOR_BUFFER_BIT);

      tboShader.setUniformf("dir", 0.f, 1.f);
      render2.bind();
      fullQuad.bind();
      factory.getCoreRender().renderTriangles(2);

    render.disable();
  }

  public Mat4 render(final float x, final float y) {
    plainTextureShader.activate();
    plainTextureShader.setUniformf("off", x, y);
    Mat4 projection = MatrixFactory.createProjection(0, 1024, 0, 768);

    Mat4 move1 = Mat4.createTranslate(x + 256, y + 256, 0);
    Mat4 move2 = Mat4.createTranslate(-(x + 256), -(y + 256), 0);
    Mat4 rotate = Mat4.createRotate(angle, 0.f, 0.f, 1.f);

    Mat4 matrix = new Mat4();
    Mat4 temp1 = new Mat4();
    Mat4 temp2 = new Mat4();
    Mat4.mul(rotate, move2, temp1);
    Mat4.mul(move1, temp1, temp2);
    Mat4.mul(projection, temp2, matrix);

    plainTextureShader.setUniformMatrix4f("matProj", matrix.toBuffer());
    render3.bind();
    fullQuad.bind();
    factory.getCoreRender().renderTriangles(2);
    return matrix;
  }

  private static CoreVBO createQuadVBO(final CoreFactory factory, final float x, final float y, final float w, final float h) {
    return factory.createStatic(new float[] {
        x,     y,     0.f, 0.f,
        x + w, y,     1.f, 0.f,
        x,     y + h, 0.f, 1.f,
        x + w, y + h, 1.f, 1.f,
    });
  }
}
