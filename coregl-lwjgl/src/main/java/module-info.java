/**
 * Created by void on 26.03.17.
 */
module com.lessvoid.coregl.lwjgl {
  requires java.base;
  requires java.logging;
  requires java.desktop;
  requires lwjgl;
  requires lwjgl.util;

  requires transitive com.lessvoid.coregl;

  provides com.lessvoid.coregl.spi.CoreGLProvider
      with com.lessvoid.coregl.lwjgl.CoreGLProviderLwjgl;
}
