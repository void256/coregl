/**
 * Created by void on 26.03.17.
 */
module coregl.utils.jogl {
  requires java.base;
  requires java.logging;
  requires java.desktop;
  requires coregl.utils;
  requires jogl.all;
  requires gluegen.rt;
  exports com.lessvoid.coregl.jogl;
}
