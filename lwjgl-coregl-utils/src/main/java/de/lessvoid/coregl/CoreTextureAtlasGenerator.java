package de.lessvoid.coregl;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.nio.FloatBuffer;

import de.lessvoid.textureatlas.TextureAtlasGenerator;
import de.lessvoid.textureatlas.TextureAtlasGenerator.Result;
import de.lessvoid.textureatlas.TextureAtlasGeneratorException;

/**
 * This uses de.lessvoid.textureatlas.TextureAtlasGenerator while rendering individual CoreTextures into a
 * CoreRenderToTexture target.
 *
 * @author void
 */
public class CoreTextureAtlasGenerator {
  private CoreRenderToTexture renderToTexture;
  private CoreVAO vao;
  private CoreVBO vbo;
  private TextureAtlasGenerator generator;
  private CoreShader shader;

  /**
   * Prepare a RenderToTexture target of the given width x height that will be used as the rendering target for the
   * texture atlas algorithm.
   * 
   * @param width width of the texture
   * @param height height of the texture
   */
  public CoreTextureAtlasGenerator(final int width, final int height) {
    renderToTexture = new CoreRenderToTexture(width, height);

    shader = CoreShader.newShaderWithVertexAttributes("aVertex", "aUV");
    shader.vertexShader("de/lessvoid/coregl/plain-texture.vs");
    shader.fragmentShader("de/lessvoid/coregl/plain-texture.fs");
    shader.link();
    shader.activate();
    shader.setUniformi("uTexture", 0);

    vao = new CoreVAO();
    vao.bind();

    vbo = CoreVBO.createStream(new float[4*4]);
    vbo.bind();

    vao.enableVertexAttributef(0, 2, 4, 0);
    vao.enableVertexAttributef(1, 2, 4, 2);
    
    renderToTexture.on();
    glClearColor(0.0f, 0.0f, 0.0f, 0.f);
    glClear(GL_COLOR_BUFFER_BIT);
    renderToTexture.off();
    vao.unbind();

    generator = new TextureAtlasGenerator(width, height);
  }

  /**
   * Add a single CoreTexture2D to the atlas and return informations about the position in the texture atlas.
   *
   * @param texture the texture
   * @param name the name used to identify this texture
   * @param padding padding value around the texture when being placed into the atlas
   * @return the Result
   */
  public Result addImage(final CoreTexture2D texture, final String name, final int padding) {
    try {
      Result result = generator.addImage(texture.getWidth(), texture.getHeight(), name, padding);
      put(texture, result.getX(), result.getY());
      return result;
    } catch (TextureAtlasGeneratorException e) {
      return null;
    }
  }

  /**
   * The target texture allocated for the texture atlas. If you want to later render using the texture atlas you'll
   * need to call this and call bind() on it.
   * @return the CoreRenderToTexture allocated for the texture altas
   */
  public CoreRenderToTexture getTargetTexture() {
    return renderToTexture;
  }

  /**
   * Width of the texture atlas used.
   * @return width of the texture atlas
   */
  public int getWidth() {
    return renderToTexture.getWidth();
  }

  /**
   * Height of the texture atlas used.
   * @return height of the texture atlas
   */
  public int getHeight() {
    return renderToTexture.getHeight();
  }

  private void put(final CoreTexture2D source, final int x, final int y) {
    shader.activate();
    shader.setUniformMatrix4f("uMvp", CoreMatrixFactory.createOrtho(0, renderToTexture.getWidth(), 0, renderToTexture.getHeight()));

    vao.bind();
    renderToTexture.on();

    FloatBuffer buffer = vbo.getBuffer();
    buffer.put(x);
    buffer.put(y);
    buffer.put(0.0f);
    buffer.put(0.0f);

    buffer.put(x);
    buffer.put(y + source.getHeight());
    buffer.put(0.0f);
    buffer.put(1.0f);

    buffer.put(x + source.getWidth());
    buffer.put(y);
    buffer.put(1.0f);
    buffer.put(0.0f);

    buffer.put(x + source.getWidth());
    buffer.put(y + source.getHeight());
    buffer.put(1.0f);
    buffer.put(1.0f);
    buffer.rewind();
    vbo.bind();
    vbo.send();

    CoreRender.renderTriangleStrip(4);
    vao.unbind();
    renderToTexture.off();
  }
}
