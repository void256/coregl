/**
 * Created by void on 26.03.17.
 */
module com.lessvoid.coregl.examples {
  requires java.desktop;

  // currently the first service provider available is being used so to
  // actual control which one is active we only enable a single one here.
  //
  // this is a little clumsy since this way we can't decide that at runtime
  // but for demonstrating purpose it should be enough.

  // enable LWJGL3
  requires com.lessvoid.coregl.lwjglthree;

  // enable LWJGL
  //requires com.lessvoid.coregl.lwjgl;

  // enable JOGL
  //requires com.lessvoid.coregl.jogl;
}
