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

  public Result addImage(final CoreTexture2D texture, final String name, final int padding) {
    try {
      Result result = generator.addImage(texture.getWidth(), texture.getHeight(), name, padding);
      put(texture, result.getX(), result.getY());
      return result;
    } catch (TextureAtlasGeneratorException e) {
      return null;
    }
  }

  public CoreRenderToTexture getDone() {
    return renderToTexture;
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
    vbo.send();

    CoreRender.renderTriangleStrip(4);
    vao.unbind();
    renderToTexture.off();
  }

  public int getWidth() {
    return renderToTexture.getWidth();
  }

  public int getHeight() {
    return renderToTexture.getHeight();
  }
}
