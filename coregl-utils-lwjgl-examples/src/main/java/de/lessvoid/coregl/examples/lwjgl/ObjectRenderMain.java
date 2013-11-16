/**
 * Copyright (c) 2013, Jens Hohmuth 
 * All rights reserved. 
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are 
 * met: 
 * 
 *  * Redistributions of source code must retain the above copyright 
 *    notice, this list of conditions and the following disclaimer. 
 *  * Redistributions in binary form must reproduce the above copyright 
 *    notice, this list of conditions and the following disclaimer in the 
 *    documentation and/or other materials provided with the distribution. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF 
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package de.lessvoid.coregl.examples.lwjgl;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import de.lessvoid.coregl.CoreFactory;
import de.lessvoid.coregl.CoreSetup;
import de.lessvoid.coregl.CoreSetup.RenderLoopCallback;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreTexture2D;
import de.lessvoid.coregl.CoreTexture2D.ColorFormat;
import de.lessvoid.coregl.CoreTexture2D.ResizeFilter;
import de.lessvoid.coregl.CoreVAO;
import de.lessvoid.coregl.examples.lwjgl.WavefrontObjectLoader.Data;
import de.lessvoid.coregl.examples.tools.FileChangeWatcher;
import de.lessvoid.coregl.examples.tools.FileChangeWatcher.Callback;
import de.lessvoid.coregl.lwjgl.CoreFactoryLwjgl;
import de.lessvoid.math.Mat3;
import de.lessvoid.math.Mat4;
import de.lessvoid.math.MatrixFactory;
import de.lessvoid.simpleimageloader.ImageData;
import de.lessvoid.simpleimageloader.SimpleImageLoader;

public class ObjectRenderMain implements RenderLoopCallback{
  private static final String FRAGMENT_SHADER_FILE = "src/main/resources/object/object-lighting.fs";
  private static final String VERTEX_SHADER_FILE = "src/main/resources/object/object-lighting.vs";
  CoreShader shader;
  WavefrontObjectLoader loader;
  CoreVAO vao;
  CoreTexture2D texture;
  private float angleX = 0.f;
  private float angleY = (float) Math.PI / 2.f;
  private float time = 0;
  private int vertexShaderId;
  private int fragmentShaderId;
  private FileChangeWatcher changeWatcher = new FileChangeWatcher();
  private static SimpleImageLoader imageLoader = new SimpleImageLoader();
  private Data data; 
  private CoreFactory factory;

  private static class VertexShaderUpdateCallback implements FileChangeWatcher.Callback {
    private CoreShader shader;
    private int shaderId;

    public VertexShaderUpdateCallback(final CoreShader shader, final int shaderId) {
      this.shader = shader;
      this.shaderId = shaderId;
    }

    @Override
    public void refresh(final File file) {
      try {
        shader.vertexShader(shaderId, file);
        shader.link();
        System.out.println("updated " + file);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
  }

  private static class FragmentShaderUpdateCallback implements FileChangeWatcher.Callback {
    private CoreShader shader;
    private int shaderId;

    public FragmentShaderUpdateCallback(final CoreShader shader, final int shaderId) {
      this.shader = shader;
      this.shaderId = shaderId;
    }

    @Override
    public void refresh(final File file) {
      try {
        shader.fragmentShader(shaderId, file);
        shader.link();
        System.out.println("updated " + file);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
  }

  private static class TextureChange implements Callback {
    private CoreTexture2D texture;

    public TextureChange(final CoreTexture2D texture) {
      this.texture = texture;
    }

    @Override
    public void refresh(final File file) {
      try {
        ImageData imageData = imageLoader.load(file.getName(), new FileInputStream(file));
        texture.updateTextureData(imageData.getData());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public ObjectRenderMain(final CoreFactory factory) throws Exception {
    this.factory = factory;
    shader = factory.newShaderWithVertexAttributes("aVertex", "aUV", "aNormal");
    vertexShaderId = shader.vertexShader(new File(VERTEX_SHADER_FILE));
    fragmentShaderId = shader.fragmentShader(new File(FRAGMENT_SHADER_FILE));
    shader.link();

    changeWatcher.add(new File(VERTEX_SHADER_FILE), new VertexShaderUpdateCallback(shader, vertexShaderId));
    changeWatcher.add(new File(FRAGMENT_SHADER_FILE), new FragmentShaderUpdateCallback(shader, fragmentShaderId));

    vao = factory.createVAO();
    vao.bind();

    try {
      ImageData imageData = imageLoader.load("/texture.png", new FileInputStream(new File("src/main/resources/texture.png")));

      texture = factory.createTexture(ColorFormat.RGB, imageData.getWidth(), imageData.getHeight(), imageData.getData(), ResizeFilter.LinearMipMapLinear);
      texture.bind();
      changeWatcher.add(new File("src/main/resources/texture.png"), new TextureChange(texture));

      loader = new WavefrontObjectLoader("/new-ship.obj");
      System.out.println(loader);

      System.out.println("Interleaved floats: " + loader.asInterleavedArray().limit());

      data = loader.asVertexAndIndexBuffer();
      System.out.println(data.getIndexData().limit() / 3);
      System.out.println(data.getVertexData().limit());
      factory.createStaticAndSend(data.getVertexData());
      factory.createStaticAndSend(data.getIndexData());
  
      // parameters are: index, size, stride, offset
      // this will use the currently active VBO to store the VBO in the VAO
      vao.enableVertexAttributef(0, 3, 8, 0);
      vao.enableVertexAttributef(1, 2, 8, 3);
      vao.enableVertexAttributef(2, 3, 8, 5);
  
      // we only use a single shader and a single vao so we can activate both here
      // and let them stay active the whole time.
      shader.activate();
      shader.setUniformi("uTex", 0);

      vao.bind();

      GL11.glEnable(GL11.GL_DEPTH_TEST);

    } catch (IOException e) {
      e.printStackTrace();
    }

    GL11.glEnable(GL11.GL_CULL_FACE);
    GL11.glFrontFace(GL11.GL_CCW);
    GL11.glEnable(GL13.GL_MULTISAMPLE);
  }

  @Override
  public boolean render(final float deltaTime) {
    glClearColor(.0f, .0f, .3f, 1.f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    changeWatcher.poll();

    float x = 0f;
    float y = 0f;//(float) Math.PI / 2.f;
    float z = -5f; //(float) Math.sin(time / 5000.f) * 27 - 30f;

    time += deltaTime;
        
    Mat4 translate = Mat4.createTranslate(x, y, z);
    angleX = 0;//(float) Math.sin(time / 500.f) / 2.f + (float)( Math.PI );
    angleY = -time / 5000.f;
    Mat4 rotateX = Mat4.createRotate(angleX, 1.f, 0.f, 0.f);
    Mat4 rotateY = Mat4.createRotate(angleY, 0.f, 1.f, 0.f);
    Mat4 modelView = Mat4.mul(translate, Mat4.mul(rotateX, rotateY, null), null);
    shader.setUniformMatrix4f("uModelViewMatrix", modelView.toBuffer());

    Mat4 projection = MatrixFactory.createProjection2(75.f, 1024.f / 768.f, 1f, 1000.f);
    Mat4 modelViewProjection = Mat4.mul(projection, modelView, null);
    shader.setUniformMatrix4f("uModelViewProjectionMatrix", modelViewProjection.toBuffer());

    Mat3 normalMatrix = new Mat3();
    normalMatrix.m00 = modelView.m00;
    normalMatrix.m10 = modelView.m10;
    normalMatrix.m20 = modelView.m20;
    normalMatrix.m01 = modelView.m01;
    normalMatrix.m11 = modelView.m11;
    normalMatrix.m21 = modelView.m21;
    normalMatrix.m02 = modelView.m02;
    normalMatrix.m12 = modelView.m12;
    normalMatrix.m22 = modelView.m22;
    shader.setUniformMatrix3f("uNormalMatrix", normalMatrix.toBuffer());

    shader.setUniformf("uLightPosition", 0.f, 0.f, 100.f, 1.f);
    shader.setUniformf("uKd", 1.f, 1.f, 1.f);
    shader.setUniformf("uLd", 1.f, 1.f, 1.f);

    IntBuffer indexData = data.getIndexData();
    factory.getCoreRender().renderTrianglesIndexed(indexData.limit());
    return false;
  }

  public static void main(final String[] args) throws Exception {
    CoreFactory factory = new CoreFactoryLwjgl();
    CoreSetup setup = factory.createSetup();
    setup.initializeLogging(); // optional to get jdk14 to better format the log
    setup.initialize("Hello Lwjgl Core GL", 1024, 768);
    setup.renderLoop(new ObjectRenderMain(factory));
  }
}
