package com.lessvoid.coregl.examples;

import com.lessvoid.coregl.CoreGLFactory;
import com.lessvoid.coregl.spi.CoreGLSetup;

/**
 * Launcher class for CoreGL examples. The purpose of CoreExampleMain is to
 * abstract the backend initialization all the way to the program launch
 * arguments so that each example program can be freed of implementation
 * specific code.
 * 
 * @author Brian Groenke
 */
public class CoreExampleMain {
  private static final int DISPLAY_WIDTH = 1024, DISPLAY_HEIGHT = 768;

  /**
   * Start the given example. This will choose whatever ServiceProvider that
   * is on the module path.
   * 
   * @param example
   */
  public static void runExample(final CoreGLSetup.RenderLoopCallback example) {
    CoreGLFactory coreGLFactory = new CoreGLFactory();
    CoreGLSetup setup = coreGLFactory.coreGLSetup();
    try {
      setup.initialize(example.getClass().getSimpleName() + " (" + setup.getClass().getSimpleName() + ")",
                       DISPLAY_WIDTH,
                       DISPLAY_HEIGHT);
      setup.enableVSync(true);
      setup.renderLoop(example);
      setup.destroy();
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }
}
