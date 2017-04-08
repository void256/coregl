package com.lessvoid.coregl.jogl;

import com.lessvoid.coregl.spi.CoreGL;
import com.lessvoid.coregl.spi.CoreGLProvider;
import com.lessvoid.coregl.spi.CoreGLSetup;

/**
 * Created by void on 08.04.17.
 */
public class CoreGLProviderJogl implements CoreGLProvider {
  private CoreGL coreGL = new CoreGLJogl();
  private CoreGLSetup coreGLSetup = new CoreGLSetupJogl(coreGL);

  @Override
  public CoreGL coreGL() {
    return coreGL;
  }

  @Override
  public CoreGLSetup coreGLSetup() {
    return coreGLSetup;
  }
}
