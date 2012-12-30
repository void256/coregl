package de.lessvoid.coregl.examples;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.FloatBuffer;

import de.lessvoid.coregl.CoreVBO;
import de.lessvoid.coregl.CoreLwjglSetup;
import de.lessvoid.coregl.CoreLwjglSetup.RenderLoopCallback;
import de.lessvoid.coregl.CoreMatrixFactory;
import de.lessvoid.coregl.CoreRender;
import de.lessvoid.coregl.CoreRenderToTexture;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreTexture2D;
import de.lessvoid.coregl.CoreTexture2D.ColorFormat;
import de.lessvoid.coregl.CoreTexture2D.ResizeFilter;
import de.lessvoid.coregl.CoreTextureAtlasGenerator;
import de.lessvoid.coregl.CoreVAO;
import de.lessvoid.simpleimageloader.ImageData;
import de.lessvoid.simpleimageloader.SimpleImageLoader;
import de.lessvoid.simpleimageloader.SimpleImageLoaderConfig;

public class TextureAtlasGeneratorMain implements RenderLoopCallback {
  private SimpleImageLoader loader = new SimpleImageLoader();

  private CoreVAO vao;
  private CoreVBO vbo;
  private CoreShader shader;
  private CoreRenderToTexture textureAtlas;

  public TextureAtlasGeneratorMain() throws Exception {
    shader = CoreShader.newShaderWithVertexAttributes("aVertex", "aUV");
    shader.vertexShader("texture-atlas/texture.vs");
    shader.fragmentShader("texture-atlas/texture.fs");
    shader.link();
    shader.activate();
    shader.setUniformi("uTexture", 0);

    vao = new CoreVAO();
    vao.bind();

    vbo = CoreVBO.createStatic(new float[4*4]);
    vbo.bind();

    vao.enableVertexAttributef(0, 2, 4, 0);
    vao.enableVertexAttributef(1, 2, 4, 2);

    CoreTextureAtlasGenerator generator = new CoreTextureAtlasGenerator(1024, 1024);
    File base = new File("src/main/resources/texture-atlas");
    for (String f : base.list(new PNGFileFilter())) {
      String filename = "/texture-atlas/" + f;
      ImageData imageData = loader.load(filename, GeometryShaderExampleMain.class.getResourceAsStream(filename), new SimpleImageLoaderConfig().forceAlpha());
      CoreTexture2D texture = new CoreTexture2D(ColorFormat.RGBA, imageData.getWidth(), imageData.getHeight(), imageData.getData(),  ResizeFilter.Linear);
      if (null == generator.addImage(texture, filename, 5)) {
        System.out.println("failed to add image: " + filename);
      }
    }
    textureAtlas = generator.getDone();
  }

  @Override
  public boolean render(final float deltaTime) {
    glClearColor(.1f, .1f, .3f, 0.f);
    glClear(GL_COLOR_BUFFER_BIT);

    textureAtlas.bindTexture();

    FloatBuffer buffer = vbo.getBuffer();
    buffer.put(0.f);
    buffer.put(0.f);
    buffer.put(0.0f);
    buffer.put(0.0f);

    buffer.put(0.f);
    buffer.put(0.f + 768);
    buffer.put(0.0f);
    buffer.put(1.0f);
    
    buffer.put(0.f + 1024);
    buffer.put(0.f);
    buffer.put(1.0f);
    buffer.put(0.0f);
    
    buffer.put(0.f + 1024);
    buffer.put(0.f + 768);
    buffer.put(1.0f);
    buffer.put(1.0f);
    buffer.rewind();
    vbo.bind();
    vbo.send();
    vao.bind();

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

  private static class PNGFileFilter implements FilenameFilter {
    @Override
    public boolean accept(File dir, String name) {
      return name.endsWith(".png");
    }
  }
}
