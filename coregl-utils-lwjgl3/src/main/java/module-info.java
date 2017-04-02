/**
 * Created by void on 26.03.17.
 */
module coregl.lwjglthree {
  requires static java.base;
  requires static java.logging;
  requires static coregl.utils;
  requires static lwjgl;
  requires static lwjgl.glfw;
  requires static lwjgl.opengl;
  exports de.lessvoid.coregl.lwjgl3;
}
