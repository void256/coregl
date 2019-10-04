package com.lessvoid.coregl.examples.runner;

import com.lessvoid.coregl.jogl.CoreGLJogl;
import com.lessvoid.coregl.lwjgl3.CoreGLLwjgl3;

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
   * Start the given renderLoop. This will choose whatever ServiceProvider that
   * is on the module path.
   * 
   * @param renderLoop
   */
  public static void runExample(final String[] args, final CoreExampleRenderLoop renderLoop) throws Exception {
    String adapter = "lwjgl3";
    if (args.length != 1) {
      System.out.println("please note that you can call this with [jogl, lwjgl3] too");
    } else {
      adapter = args[0];
    }
    System.out.println("using adapter [" + adapter + "]");

    if ("jogl".equals(adapter)) {
      runJogl(renderLoop);
      return;
    } else if ("lwjgl3".equals(adapter)) {
      runLwjgl3(renderLoop);
      return;
    }
    throw new Exception("unknown adapter [" + adapter + "]");
  }

  private static void runJogl(final CoreExampleRenderLoop renderLoop) throws Exception {
    CoreExampleSetupJogl setup = new CoreExampleSetupJogl(new CoreGLJogl());
    setup.initialize(renderLoop.getClass().getSimpleName() + " (" + setup.getClass().getSimpleName() + ")",
        DISPLAY_WIDTH,
        DISPLAY_HEIGHT);
    setup.enableVSync(true);
    setup.renderLoop(renderLoop);
    setup.destroy();
  }

  private static void runLwjgl3(final CoreExampleRenderLoop renderLoop) throws Exception {
    CoreExampleSetupLWJGL3 setup = new CoreExampleSetupLWJGL3(new CoreGLLwjgl3());
    setup.initialize(renderLoop.getClass().getSimpleName() + " (" + setup.getClass().getSimpleName() + ")",
        DISPLAY_WIDTH,
        DISPLAY_HEIGHT);
    setup.enableVSync(true);
    setup.renderLoop(renderLoop);
    setup.destroy();
  }
}
