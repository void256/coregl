package com.lessvoid.coregl;

import com.lessvoid.coregl.spi.CoreGL;
import com.lessvoid.coregl.spi.CoreGLProvider;
import com.lessvoid.coregl.spi.CoreGLSetup;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Created by void on 08.04.17.
 */
public class CoreGLFactory {

  public CoreGL coreGL() {
    return coreSetup().coreGL();
  }

  public CoreGLSetup coreGLSetup() {
    return coreSetup().coreGLSetup();
  }

  private CoreGLProvider coreSetup() {
    ServiceLoader<CoreGLProvider> sl = ServiceLoader.load(CoreGLProvider.class);
    Iterator<CoreGLProvider> iter = sl.iterator();
    if (!iter.hasNext())
      throw new RuntimeException("No 'CoreGLProvider' service providers found!");
    return iter.next();
  }
}
