package de.lessvoid.coregl.examples;

import de.lessvoid.coregl.CoreLwjglSetup;
import de.lessvoid.coregl.CoreLwjglSetup.RenderLoopCallback;

public class SimpleExampleMain implements RenderLoopCallback {

  public static void main(final String[] args) throws Exception {
    CoreLwjglSetup setup = new CoreLwjglSetup();
    setup.initializeLogging();
    setup.initialize("Hello Lwjgl Core GL", 1024, 768);
    setup.renderLoop(new SimpleExampleMain());
  }

  @Override
  public boolean process() {
    return false;
  }
}
