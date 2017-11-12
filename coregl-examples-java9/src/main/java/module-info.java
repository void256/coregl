/**
 * Created by void on 12.11.17.
 */
module coregl.examples {
  // needed for the ImageLoaderImageIO class we use
  requires java.desktop;

  // jogl
  requires com.lessvoid.coregl.jogl;

  // lwjgl
  //requires com.lessvoid.coregl.lwjgl;
  //requires lwjgl;

  // lwjgl3
  requires com.lessvoid.coregl.lwjglthree;
  requires org.lwjgl.opengl;
  requires org.lwjgl.glfw;

  // we use the ServiceLoader to find CoreGL adapter implementations
  uses com.lessvoid.coregl.spi.CoreGL;

  // resources
  opens background;
}
