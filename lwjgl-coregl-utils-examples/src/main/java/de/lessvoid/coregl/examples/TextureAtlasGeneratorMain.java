package de.lessvoid.coregl.examples;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.nio.FloatBuffer;

import de.lessvoid.coregl.CoreLwjglSetup;
import de.lessvoid.coregl.CoreLwjglSetup.RenderLoopCallback;
import de.lessvoid.coregl.CoreMatrixFactory;
import de.lessvoid.coregl.CoreRender;
import de.lessvoid.coregl.CoreRenderToTexture;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreTexture;
import de.lessvoid.coregl.CoreVAO;
import de.lessvoid.coregl.CoreVBO;
import de.lessvoid.simpleimageloader.ImageData;
import de.lessvoid.simpleimageloader.SimpleImageLoader;

public class TextureAtlasGeneratorMain implements RenderLoopCallback {
  private CoreRenderToTexture renderToTexture;
  private CoreVBO vbo;
  private CoreShader shader;
  private CoreTexture texture;

  public TextureAtlasGeneratorMain() throws Exception {
    shader = CoreShader.newShaderWithVertexAttributes("aVertex", "aUV");
    shader.vertexShader("texture-atlas/texture-atlas.vs");
    shader.fragmentShader("texture-atlas/texture-atlas.fs");
    shader.link();

    CoreVAO vao = new CoreVAO();
    vao.bind();

    vbo = CoreVBO.createStaticVBOAndSend(new float[] {
         100.f,  100.f,    0.0f, 0.0f,
         100.f,  250.f,    0.0f, 1.0f,
         250.f,  100.f,    1.0f, 0.0f,
         250.f,  250.f,    1.0f, 1.0f,
    });

    // parameters are: index, size, stride, offset
    // this will use the currently active VBO to store the VBO in the VAO
    vao.enableVertexAttributef(0, 2, 4, 0);
    vao.enableVertexAttributef(1, 2, 4, 2);

    // we only use a single shader and a single vao so we can activate both her
    // and let them stay active the whole time.
    shader.activate();
    shader.setUniformi("uTexture", 0);

    SimpleImageLoader loader = new SimpleImageLoader();
    ImageData imageData = loader.load("demo.png", GeometryShaderExampleMain.class.getResourceAsStream("/nifty-logo-150x150.png"));

    texture = new CoreTexture(true, 0, 0, imageData.getWidth(), imageData.getHeight(), imageData.getBitsPerPixel(), imageData.getData());

    vao.bind();

    renderToTexture = new CoreRenderToTexture(500, 500);
    renderToTexture.on();
    texture.bind();

    glClearColor(1.f, 0.f, 0.f, 1.f);
    glClear(GL_COLOR_BUFFER_BIT);
    
    FloatBuffer buffer = vbo.getBuffer();
    buffer.put(0.f);
    buffer.put(0.f);
    buffer.put(0.0f);
    buffer.put(0.0f);

    buffer.put(0.f);
    buffer.put(50.f);
    buffer.put(0.0f);
    buffer.put(1.0f);

    buffer.put(50.f);
    buffer.put(0.f);
    buffer.put(1.0f);
    buffer.put(0.0f);

    buffer.put(50.f);
    buffer.put(50.f);
    buffer.put(1.0f);
    buffer.put(1.0f);
    buffer.rewind();
    vbo.sendData();

    shader.setUniformMatrix4f("uMvp", CoreMatrixFactory.createOrtho(0, 500.f, 0, 500.f));
    CoreRender.renderTriangleStrip(4);
    renderToTexture.off();
  }

  @Override
  public boolean render(final float deltaTime) {
    glClearColor(.1f, .1f, .3f, 0.f);
    glClear(GL_COLOR_BUFFER_BIT);

    // render all the data in the currently active vao using triangle strips
    //texture.bind();
    renderToTexture.bindTexture();

    FloatBuffer buffer = vbo.getBuffer();
    buffer.put(100.f);
    buffer.put(200.f);
    buffer.put(0.0f);
    buffer.put(0.0f);

    buffer.put(100.f);
    buffer.put(700.f);
    buffer.put(0.0f);
    buffer.put(1.0f);
    
    buffer.put(600.f);
    buffer.put(200.f);
    buffer.put(1.0f);
    buffer.put(0.0f);
    
    buffer.put(600.f);
    buffer.put(700.f);
    buffer.put(1.0f);
    buffer.put(1.0f);
    buffer.rewind();
    vbo.sendData();

    shader.setUniformMatrix4f("uMvp", CoreMatrixFactory.createOrtho(0, 1024.f, 768.f, 0));
    CoreRender.renderTriangleStrip(4);
    return false;
  }

  public static void main(final String[] args) throws Exception {
    CoreLwjglSetup setup = new CoreLwjglSetup();
    setup.initializeLogging(); // optional to get jdk14 to better format the log
    setup.initialize("Texture Atlas Generator", 1024, 768);
    setup.renderLoop(new TextureAtlasGeneratorMain());
  }
}
