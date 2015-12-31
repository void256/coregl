package de.lessvoid.coregl.examples;

import de.lessvoid.coregl.jogl.CoreSetupJogl;
import de.lessvoid.coregl.jogl.JoglCoreGL;
import de.lessvoid.coregl.lwjgl.CoreSetupLwjgl;
import de.lessvoid.coregl.lwjgl.LwjglCoreGL;
import de.lessvoid.coregl.spi.CoreGL;
import de.lessvoid.coregl.spi.CoreSetup;
import de.lessvoid.coregl.spi.CoreSetup.RenderLoopCallback;

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
   * Evaluates main method arguments to launch the given RenderLoopCallback
   * example using specified backend (JOGL or LWJGL).<br/>
   * Program argument flags are 'jogl' and 'lwjgl' for JOGL and LWJGL backends
   * respectively. If no program arguments are specified, runExample defaults to
   * the JOGL backend.
   * 
   * @param example
   * @param mainArgs
   */
  public static void runExample(final RenderLoopCallback example, final String[] mainArgs) {
    String backendName = "JOGL"; // default backend to use in case one isn't
                                 // specified
    if (mainArgs.length == 0) {
      System.err.println("No backend argument supplied. Defaulting to '" + backendName + "'");
      printUsageString();
    } else {
      backendName = mainArgs[0];
    }
    CoreGL gl = null;
    CoreSetup setup = null;
    if (backendName.equalsIgnoreCase("jogl")) {
      System.err.println("running " + example.getClass().getName() + ": " + "backend=JOGL");
      gl = new JoglCoreGL();
      setup = new CoreSetupJogl(gl);
    } else if (backendName.equalsIgnoreCase("lwjgl")) {
      System.err.println("running " + example.getClass().getName() + ": " + "backend=LWJGL");
      gl = new LwjglCoreGL();
      setup = new CoreSetupLwjgl(gl);
    } else {
      System.err.println("unrecognized backend name: " + mainArgs[0]);
      printUsageString();
    }

    if (gl != null && setup != null) {
      try {
        setup.initialize(example.getClass().getSimpleName() + " (" + backendName.toUpperCase() + ")",
                         DISPLAY_WIDTH,
                         DISPLAY_HEIGHT);
        setup.renderLoop(example);
        setup.destroy();
      } catch (final Exception e) {
        e.printStackTrace();
      }
    }
  }

  private static void printUsageString() {
    System.err
        .println("Usage: to specify a CoreGL backend, pass its identifier as a program argument (JOGL = 'jogl' LWJGL = 'lwjgl')");
  }
}
