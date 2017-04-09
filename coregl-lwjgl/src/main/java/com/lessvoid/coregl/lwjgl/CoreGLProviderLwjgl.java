package com.lessvoid.coregl.lwjgl;

import com.lessvoid.coregl.spi.CoreGL;
import com.lessvoid.coregl.spi.CoreGLProvider;
import com.lessvoid.coregl.spi.CoreGLSetup;

/**
 * Created by void on 08.04.17.
 */
public class CoreGLProviderLwjgl implements CoreGLProvider {
  private CoreGL coreGL = new CoreGLLwjgl();
  private CoreGLSetup coreGLSetup = new CoreGLSetupLwjgl(coreGL);

  @Override
  public CoreGL coreGL() {
    return coreGL;
  }

  @Override
  public CoreGLSetup coreGLSetup() {
    return coreGLSetup;
  }
}
