package com.lessvoid.coregl.examples;

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
  public static void runExample(final String[] args, final CoreGLSetup.RenderLoopCallback example) throws Exception {
    String adapter = "lwjgl3";
    if (args.length != 1) {
      System.out.println("please note that you can call this with [jogl, lwjgl, lwjgl3] too");
    } else {
      adapter = args[0];
    }
    System.out.println("using adapter [" + adapter + "]");

    CoreGLSetup setup = createCoreGLSetup(adapter);
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

  private static CoreGLSetup createCoreGLSetup(final String adapter) throws Exception {
    if ("jogl".equals(adapter)) {
      return new com.lessvoid.coregl.jogl.CoreGLJogl().coreGLSetup();
    } else if ("lwjgl".equals(adapter)) {
      return new com.lessvoid.coregl.lwjgl.CoreGLLwjgl().coreGLSetup();
    } else if ("lwjgl3".equals(adapter)) {
      return new com.lessvoid.coregl.lwjgl3.CoreGLLwjgl3().coreGLSetup();
    } else {
      throw new Exception("unknown adapter [" + adapter + "]");
    }
  }
}
