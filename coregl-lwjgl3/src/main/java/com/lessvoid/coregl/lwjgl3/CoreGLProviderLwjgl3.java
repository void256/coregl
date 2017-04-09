package com.lessvoid.coregl.lwjgl3;

import com.lessvoid.coregl.spi.CoreGL;
import com.lessvoid.coregl.spi.CoreGLProvider;
import com.lessvoid.coregl.spi.CoreGLSetup;

/**
 * Created by void on 08.04.17.
 */
public class CoreGLProviderLwjgl3 implements CoreGLProvider {
  private CoreGL coreGL = new CoreGLLwjgl3();
  private CoreGLSetup coreGLSetup = new CoreGLSetupLwjgl3(coreGL);

  @Override
  public CoreGL coreGL() {
    return coreGL;
  }

  @Override
  public CoreGLSetup coreGLSetup() {
    return coreGLSetup;
  }
}
