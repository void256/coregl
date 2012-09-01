package de.lessvoid.coregl.examples;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import de.lessvoid.coregl.CoreLwjglSetup;
import de.lessvoid.coregl.CoreLwjglSetup.RenderLoopCallback;
import de.lessvoid.coregl.CoreTexture2D.ColorFormat;
import de.lessvoid.coregl.CoreTexture2D.ResizeFilter;
import de.lessvoid.coregl.CoreMatrixFactory;
import de.lessvoid.coregl.CoreRender;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreTexture2D;
import de.lessvoid.coregl.CoreVAO;
import de.lessvoid.coregl.CoreVBO;
import de.lessvoid.simpleimageloader.ImageData;
import de.lessvoid.simpleimageloader.SimpleImageLoader;

/**
 * The GeometryShaderExampleMain demonstrates the lwjgl-core-utils API to use an geometry shader. It will render a
 * quad from a single GL_POINT.
 *
 * @author void
 */
public class GeometryShaderExampleMain implements RenderLoopCallback {
  int vertexCount = 1000;
  int dataPerVertex = 28;
  CoreVBO vbo;

  public GeometryShaderExampleMain() throws Exception {
    CoreShader shader = CoreShader.newShader();
    shader.vertexShader("geometry-shader/vertex.vs");
    shader.fragmentShader("geometry-shader/fragment.fs");
    shader.geometryShader("geometry-shader/geometry.gs");
    shader.link();

    CoreVAO vao = new CoreVAO();
    vao.bind();

    float[] vertexData = new float[vertexCount*dataPerVertex];
    int index = 0;
    for (int i=0; i<vertexCount; i++) {
      vertexData[index++] = (float)Math.random() * (1024.f - 50.f);
      vertexData[index++] = (float)Math.random() * (768.f - 50.f);
      vertexData[index++] = (float)Math.random() * 50.f + 50.f;
      vertexData[index++] = (float)Math.random() * 50.f + 50.f;

      vertexData[index++] = (float)Math.random();
      vertexData[index++] = (float)Math.random();
      vertexData[index++] = (float)Math.random();
      vertexData[index++] = (float)Math.random();

      vertexData[index++] = (float)Math.random();
      vertexData[index++] = (float)Math.random();
      vertexData[index++] = (float)Math.random();
      vertexData[index++] = (float)Math.random();

      vertexData[index++] = (float)Math.random();
      vertexData[index++] = (float)Math.random();
      vertexData[index++] = (float)Math.random();
      vertexData[index++] = (float)Math.random();

      vertexData[index++] = (float)Math.random();
      vertexData[index++] = (float)Math.random();
      vertexData[index++] = (float)Math.random();
      vertexData[index++] = (float)Math.random();

      vertexData[index++] = 0.5f;
      vertexData[index++] = 0.5f;

      vertexData[index++] = 0.0001f;//1.0f;
      vertexData[index++] = 0.5f;

      vertexData[index++] = 0.5f;
      vertexData[index++] = 0.0001f;//1.0f;
      
      vertexData[index++] = 0.0001f;//1.0f;
      vertexData[index++] = 0.0001f;//1.0f;
  };
    
    vbo = CoreVBO.createDynamicVBO(vertexData);
    vbo.sendData();

    // parameters are: index, size, stride, offset
    // this will use the currently active VBO to store the VBO in the VAO
    vao.enableVertexAttributef(shader.getAttribLocation("aVertex"), 4, dataPerVertex, 0);
    vao.enableVertexAttributef(shader.getAttribLocation("aColor1"), 4, dataPerVertex, 4);
    vao.enableVertexAttributef(shader.getAttribLocation("aColor2"), 4, dataPerVertex, 8);
    vao.enableVertexAttributef(shader.getAttribLocation("aColor3"), 4, dataPerVertex, 12);
    vao.enableVertexAttributef(shader.getAttribLocation("aColor4"), 4, dataPerVertex, 16);
    vao.enableVertexAttributef(shader.getAttribLocation("aUV1"), 4, dataPerVertex, 20);
    vao.enableVertexAttributef(shader.getAttribLocation("aUV2"), 4, dataPerVertex, 24);

    // load texture
    SimpleImageLoader loader = new SimpleImageLoader();
    ImageData imageData = loader.load("demo.png", GeometryShaderExampleMain.class.getResourceAsStream("/nifty-logo-150x150.png"));

    CoreTexture2D texture = new CoreTexture2D(ColorFormat.RGBA, imageData.getWidth(), imageData.getHeight(), imageData.getData(), ResizeFilter.Linear);
    texture.bind();

    // we only use a single shader and a single vao so we can activate both her
    // and let them stay active the whole time.
    shader.activate();
    shader.setUniformMatrix4f("uMvp", CoreMatrixFactory.createOrtho(0, 1024.f, 768.f, 0));
    shader.setUniformf("uWidth", 1024.f);
    shader.setUniformf("uHeight", 768.f);
    shader.setUniformi("uTex", 0);

    System.out.println(Matrix4f.transform(CoreMatrixFactory.createOrtho(0, 1024.f, 768.f, 0), new Vector4f(10.0f, 758.0f, 0.0f, 1.0f), null));
    vao.bind();
  }

  @Override
  public boolean render(final float deltaTime) {
    glClearColor(.1f, .1f, .3f, 0.f);
    glClear(GL_COLOR_BUFFER_BIT);
/*
    FloatBuffer v = vbo.getBuffer();
    int index = 0;
    for (int i=0; i<vertexCount; i++) {
      v.put(index++, (float)Math.random() * (1024.f - 50.f));
      v.put(index++, (float)Math.random() * (768.f - 50.f));
      v.put(index++, (float)Math.random() * 50.f + 50.f);
      v.put(index++, (float)Math.random() * 50.f + 50.f);

      v.put(index++, (float)Math.random());
      v.put(index++, (float)Math.random());
      v.put(index++, (float)Math.random());
      v.put(index++, (float)Math.random());

      v.put(index++, (float)Math.random());
      v.put(index++, (float)Math.random());
      v.put(index++, (float)Math.random());
      v.put(index++, (float)Math.random());

      v.put(index++, (float)Math.random());
      v.put(index++, (float)Math.random());
      v.put(index++, (float)Math.random());
      v.put(index++, (float)Math.random());

      v.put(index++, (float)Math.random());
      v.put(index++, (float)Math.random());
      v.put(index++, (float)Math.random());
      v.put(index++, (float)Math.random());
    };

    v.rewind();
    vbo.sendData();
    */

    // render all the data in the currently active vao using triangle strips
    CoreRender.renderPoints(vertexCount);
    return false;
  }

  public static void main(final String[] args) throws Exception {
    CoreLwjglSetup setup = new CoreLwjglSetup();
    setup.initializeLogging(); // optional to get jdk14 to better format the log
    setup.initialize("Hello Lwjgl Core GL", 1024, 768);
    setup.renderLoop(new GeometryShaderExampleMain());
  }
}
