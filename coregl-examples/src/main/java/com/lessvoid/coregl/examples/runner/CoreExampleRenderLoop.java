package com.lessvoid.coregl.examples.runner;

import com.lessvoid.coregl.spi.CoreGL;

/**
 * You can implement this interface when you use the renderLoop() method. This
 * will be called each frame and allows you to actually draw.
 *
 * @author void
 */
public interface CoreExampleRenderLoop {

  /**
   * Called once after the OpenGL context has been initialized and before any
   * calls are made to {@link #render(CoreGL, float)}. Use this method to
   * initialize any and all necessary Core components.
   *
   * @param gl
   *          the CoreGL instance in case you want to use that
   * @param framebufferWidth the framebuffer width
   * @param framebufferHeight the framebuffer height
   */
  void init(CoreGL gl, int framebufferWidth, int framebufferHeight);

  /**
   * Do some awesome stuff in here!
   *
   * @param gl
   *          the CoreGL instance in case you want to use that
   * @param deltaTime
   *          the time past since the last call in ms
   * @return true when data has been rendered and the rendered data should be
   *         displayed. false when nothing has been changed and the display
   *         should not be updated.
   */
  boolean render(CoreGL gl, float deltaTime);

  /**
   * When you're finishing rendering awesome stuff and the render loop should
   * be ended, return true in here.
   *
   * @return true render loop should be stopped and false if you want it to
   *         continue.
   */
  boolean endLoop(CoreGL gl);

  /**
   * When the visible region of the current window changes dimensions.
   * @param gl CoreGL instance this is connected to
   * @param width the new width
   * @param height the new height
   */
  void sizeChanged(CoreGL gl, int width, int height);
}
