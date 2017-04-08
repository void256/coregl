/**
 * Created by void on 26.03.17.
 */
module com.lessvoid.coregl.lwjglthree {
  requires java.base;
  requires java.logging;
  requires lwjgl;
  requires lwjgl.glfw;
  requires lwjgl.opengl;

  requires transitive com.lessvoid.coregl;

  provides com.lessvoid.coregl.spi.CoreGLProvider
      with com.lessvoid.coregl.lwjgl3.CoreGLProviderLwjgl3;
}
