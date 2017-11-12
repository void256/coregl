/**
 * Created by void on 26.03.17.
 */
module com.lessvoid.coregl.jogl {
  requires java.base;
  requires java.logging;
  requires java.desktop;

  requires jogl.all;
  requires gluegen.rt;

  requires transitive com.lessvoid.coregl;

  provides com.lessvoid.coregl.spi.CoreGL
      with com.lessvoid.coregl.jogl.CoreGLJogl;
}
