/**
 * Created by void on 26.03.17.
 */
module coregl.utils {
  requires static java.base;
  requires static java.logging;
  requires static java.desktop;
  exports com.lessvoid.coregl;
  exports com.lessvoid.coregl.input;
  exports com.lessvoid.coregl.input.dispatch;
  exports com.lessvoid.coregl.input.spi;
  exports com.lessvoid.coregl.spi;
  exports com.lessvoid.coregl.state;
  exports com.lessvoid.math;
  exports com.lessvoid.textureatlas;
}
