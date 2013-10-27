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
package de.lessvoid.coregl.lwjgl;

import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STREAM_DRAW;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import de.lessvoid.coregl.CoreCheckGL;
import de.lessvoid.coregl.CoreSetup;
import de.lessvoid.coregl.CoreElementVBO;
import de.lessvoid.coregl.CoreFBO;
import de.lessvoid.coregl.CoreFactory;
import de.lessvoid.coregl.CoreRender;
import de.lessvoid.coregl.CoreScreenshot;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreTexture2D;
import de.lessvoid.coregl.CoreTexture2D.ColorFormat;
import de.lessvoid.coregl.CoreTexture2D.ResizeFilter;
import de.lessvoid.coregl.CoreTextureBuffer;
import de.lessvoid.coregl.CoreVAO;
import de.lessvoid.coregl.CoreVBO;

public class CoreFactoryLwjgl implements CoreFactory {

  // CoreTexture2D /////////////////////////////////////////////////////////////////////////////////////////////////////

  @Override
  public CoreTexture2D createEmptyTexture(
      final ColorFormat format,
      final CoreTexture2D.Type dataType,
      final int width,
      final int height,
      final ResizeFilter filter) {
    return CoreTexture2DLwjgl.createEmptyTexture(format, dataType, width, height, filter);
  }

  @Override
  public CoreTexture2D createEmptyTextureArray(
      final ColorFormat format,
      final CoreTexture2D.Type dataType,
      final int width,
      final int height,
      final int num,
      final ResizeFilter filter) {
    return CoreTexture2DLwjgl.createEmptyTextureArray(format, dataType, width, height, num, filter);
  }

  @Override
  public CoreTexture2D createTexture(
      final ColorFormat format,
      final int width,
      final int height,
      final Buffer data,
      final ResizeFilter filter) {
    return new CoreTexture2DLwjgl(format, width, height, data, filter);
  }

  @Override
  public CoreTexture2D createTexture(
      final ColorFormat internalFormat,
      final int width,
      final int height,
      final ColorFormat format,
      final Buffer data,
      final int magFilter,
      final int minFilter) {
    return new CoreTexture2DLwjgl(internalFormat, width, height, format, data, magFilter, minFilter);
  }

  @Override
  public CoreTexture2D createTexture(
      final int target,
      final ColorFormat internalFormat,
      final int width,
      final int height,
      final ColorFormat format,
      final Buffer data,
      final int magFilter,
      final int minFilter) {
    return new CoreTexture2DLwjgl(target, internalFormat, width, height, format, data, magFilter, minFilter);
  }

  @Override
  public CoreTexture2D createTexture(
      final int textureId,
      final int target,
      final int level,
      final ColorFormat internalFormat,
      final int width,
      final int height,
      final int border,
      final ColorFormat format,
      final CoreTexture2D.Type type,
      final Buffer data,
      final int magFilter,
      final int minFilter) {
    return new CoreTexture2DLwjgl(
        textureId, target, level, internalFormat, width, height, border, format, type, data, magFilter, minFilter);
  }

  // CoreShader ////////////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Create a new Shader.
   * @return the new CoreShader instance
   */
  public CoreShader newShader() {
    return new CoreShaderLwjgl();
  }

  /**
   * Create a new Shader with the given vertex attributes automatically bind to the generic attribute indices in
   * ascending order beginning with 0. This method can be used when you want to control the vertex attribute binding
   * on your own.
   *
   * @param vertexAttributes the name of the vertex attribute. The first String gets generic attribute index 0. the
   *        second String gets generic attribute index 1 and so on.
   * @return the CoreShader instance
   */
  public CoreShader newShaderWithVertexAttributes(final String ... vertexAttributes) {
    return new CoreShaderLwjgl(vertexAttributes);
  }

  // CoreVBO ///////////////////////////////////////////////////////////////////////////////////////////////////////////

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreFactory#createStatic(float[])
   */
  @Override
  public CoreVBO createStatic(final float[] data) {
    return new CoreVBOLwjgl(GL_STATIC_DRAW, data);
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreFactory#createStaticAndSend(float[])
   */
  @Override
  public CoreVBO createStaticAndSend(final float[] data) {
    CoreVBOLwjgl result = new CoreVBOLwjgl(GL_STATIC_DRAW, data);
    result.send();
    return result;
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreFactory#createStaticAndSend(java.nio.FloatBuffer)
   */
  @Override
  public CoreVBO createStaticAndSend(final FloatBuffer data) {
    CoreVBOLwjgl result = new CoreVBOLwjgl(GL_STATIC_DRAW, data);
    result.send();
    return result;
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreFactory#createDynamic(float[])
   */
  @Override
  public CoreVBO createDynamic(final float[] data) {
    return new CoreVBOLwjgl(GL_DYNAMIC_DRAW, data);
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreFactory#createStream(float[])
   */
  @Override
  public CoreVBO createStream(final float[] data) {
    return new CoreVBOLwjgl(GL_STREAM_DRAW, data);
  }

  // CoreVAO ///////////////////////////////////////////////////////////////////////////////////////////////////////////

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreFactory#createVAO()
   */
  @Override
  public CoreVAO createVAO() {
    return new CoreVAOLwjgl();
  }

  // CoreFBO ///////////////////////////////////////////////////////////////////////////////////////////////////////////

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreFactory#createCoreFBO()
   */
  @Override
  public CoreFBO createCoreFBO() {
    return new CoreFBOLwjgl();
  }

  // CoreTextureBuffer /////////////////////////////////////////////////////////////////////////////////////////////////

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreFactory#createCoreTextureBuffer(byte[])
   */
  @Override
  public CoreTextureBuffer createCoreTextureBuffer(byte[] data) {
    return new CoreTextureBufferLwjgl(data);
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreFactory#createCoreTextureBuffer(short[])
   */
  @Override
  public CoreTextureBuffer createCoreTextureBuffer(short[] data) {
    return new CoreTextureBufferLwjgl(data);
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreFactory#createCoreTextureBuffer(int[])
   */
  @Override
  public CoreTextureBuffer createCoreTextureBuffer(int[] data) {
    return new CoreTextureBufferLwjgl(data);
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreFactory#createCoreTextureBuffer(float[])
   */
  @Override
  public CoreTextureBuffer createCoreTextureBuffer(float[] data) {
    return new CoreTextureBufferLwjgl(data);
  }

  // CoreCheckGL ///////////////////////////////////////////////////////////////////////////////////////////////////////

  @Override
  public CoreCheckGL createCoreCheckGL() {
    return new CoreCheckGLLwjgl();
  }

  // CoreElementVBO ////////////////////////////////////////////////////////////////////////////////////////////////////

  @Override
  public CoreElementVBO createStatic(final int[] data) {
    return new CoreElementVBOLwjgl(GL_STATIC_DRAW, data);
  }

  @Override
  public CoreElementVBO createStaticAndSend(final int[] data) {
    CoreElementVBOLwjgl result = new CoreElementVBOLwjgl(GL_STATIC_DRAW, data);
    result.send();
    return result;
  }

  @Override
  public CoreElementVBO createStaticAndSend(final IntBuffer data) {
    CoreElementVBOLwjgl result = new CoreElementVBOLwjgl(GL_STATIC_DRAW, data);
    result.send();
    return result;
  }

  @Override
  public CoreElementVBO createDynamic(final int[] data) {
    return new CoreElementVBOLwjgl(GL_DYNAMIC_DRAW, data);
  }

  @Override
  public CoreElementVBO createStream(final int[] data) {
    return new CoreElementVBOLwjgl(GL_STREAM_DRAW, data);
  }

  // CoreDisplaySetup //////////////////////////////////////////////////////////////////////////////////////////////////

  @Override
  public CoreSetup createSetup() {
    return new CoreSetupLwjgl();
  }

  // CoreRender ////////////////////////////////////////////////////////////////////////////////////////////////////////

  @Override
  public CoreRender getCoreRender() {
    return CORE_RENDER_LWJGL;
  }

  // CoreScreenshot ////////////////////////////////////////////////////////////////////////////////////////////////////

  @Override
  public CoreScreenshot createCoreScreenshot() {
    return CORE_SCREENSHOT_LWJGL;
  }

  private static final CoreRenderLwjgl CORE_RENDER_LWJGL = new CoreRenderLwjgl();
  private static final CoreScreenshot CORE_SCREENSHOT_LWJGL = new CoreScreenshotLwjgl();
}
