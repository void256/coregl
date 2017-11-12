package com.lessvoid.coregl.examples9;

import com.lessvoid.coregl.spi.CoreGL;
import com.lessvoid.coregl.spi.CoreGLSetup;

import java.util.Comparator;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.stream.Collectors;

/**
 * Launcher class for CoreGL examples. The purpose of CoreExampleMain is to
 * abstract the backend initialization all the way to the program launch
 * arguments so that each example program can be freed of implementation
 * specific code.
 * 
 * @author Brian Groenke
 */
public class CoreExampleMain9 {
  private static final int DISPLAY_WIDTH = 1024, DISPLAY_HEIGHT = 768;

  /**
   * Start the given example. This will choose whatever ServiceProvider that
   * is on the module path.
   * 
   * @param example
   */
  public static void runExample(final String[] args, final CoreGLSetup.RenderLoopCallback example) throws Exception {
    CoreGLSetup setup = createCoreGLSetup(args);
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

  private static CoreGLSetup createCoreGLSetup(final String[] args) throws Exception {
    System.out.print("Available Adapters: ");
    System.out.println(
        ServiceLoader.load(CoreGL.class)
            .stream()
            .sorted(Comparator.comparing(o -> o.get().name()))
            .map(i -> i.get().name())
            .collect(Collectors.joining(", ")));

    Optional<Provider<CoreGL>> coreGL = ServiceLoader.load(CoreGL.class)
        .stream()
        .sorted(Comparator.comparing(o -> o.get().name()))
        .filter(o -> {
          if (args.length == 0) {
            return true;
          }
          return o.get().name().equals(args[0]);
        })
        .findFirst();
    if (coreGL.isPresent()) {
      System.out.println("Using " + coreGL.get().get().name());
      return coreGL.get().get().coreGLSetup();
    }
    throw new Exception("adapter [" + ((args.length == 0)?"":args[0]) + "] not available");
  }
}
