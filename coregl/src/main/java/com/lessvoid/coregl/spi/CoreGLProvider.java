package com.lessvoid.coregl.spi;

/**
 * Created by void on 08.04.17.
 */
public interface CoreGLProvider {
  CoreGL coreGL();
  CoreGLSetup coreGLSetup();
}
