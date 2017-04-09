package com.lessvoid.coregl.jogl;

import com.jogamp.nativewindow.WindowClosingProtocol.WindowClosingMode;
import com.jogamp.nativewindow.util.Rectangle;
import com.jogamp.newt.Display;
import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.Screen;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.lessvoid.coregl.CoreBufferUtil;
import com.lessvoid.coregl.CoreLogger;
import com.lessvoid.coregl.input.spi.CoreInput;
import com.lessvoid.coregl.jogl.input.CoreInputJogl;
import com.lessvoid.coregl.spi.CoreGL;
import com.lessvoid.coregl.spi.CoreGLSetup;

import java.nio.IntBuffer;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * CoreSetup implementation for JOGL
 * 
 * @author Brian Groenke
 */
public class CoreGLSetupJogl implements CoreGLSetup {
  private static final CoreLogger log = CoreLogger.getCoreLogger(CoreGLSetupJogl.class);
  private static final float NANO_TO_MS_CONVERSION = 1000000.f;

  private final StringBuilder fpsText = new StringBuilder();

  private final CoreGL gl;

  private Screen newtScreen;
  private Display newtDisp;
  private GLWindow glWin;
  private CoreInput input;
  private String lastFPS = "";

  private volatile boolean closeRequested, vsync, updateVsync;

  public CoreGLSetupJogl(final CoreGL gl) {
    this.gl = gl;
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.lessvoid.coregl.CoreDisplaySetup#initializeLogging()
   */
  @Override
  public void initializeLogging() {
    for (final Handler handler : Logger.getLogger("").getHandlers()) {
      handler.setFormatter(new Formatter() {
        @Override
        public String format(final LogRecord record) {
          final Throwable throwable = record.getThrown();
          if (throwable != null) {
            throwable.printStackTrace();
          }
          return record.getMillis() + " " + record.getLevel() + " [" + record.getSourceClassName() + "] "
              + record.getMessage() + "\n";
        }
      });
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * de.lessvoid.coregl.CoreDisplaySetup#initializeLogging(java.lang.String)
   */
  @Override
  public void initializeLogging(final String loggingProperties) {
    try {
      LogManager.getLogManager().readConfiguration(CoreGLSetupJogl.class.getResourceAsStream(loggingProperties));
    } catch (final Exception e) {
      throw new RuntimeException("error reading jdk14 logging properties resource from: [" + loggingProperties + "]",
                                 e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.lessvoid.coregl.CoreDisplaySetup#initialize(java.lang.String, int,
   * int)
   */
  @Override
  public void initialize(final String title, final int width, final int height) throws Exception {
    initGraphics(title, width, height);
    initInput();
  }

  @Override
  public CoreInput getInput() {
    return input;
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.lessvoid.coregl.CoreDisplaySetup#destroy()
   */
  @Override
  public void destroy() {
    if (glWin != null) glWin.destroy();
    if (input != null) input.dispose();
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.lessvoid.coregl.CoreDisplaySetup#renderLoop(de.lessvoid.coregl.
   * CoreDisplaySetup.RenderLoopCallback)
   */
  @Override
  public void renderLoop(final RenderLoopCallback renderCallback) {
    int frames = 0;
    long lastPrintTime = System.currentTimeMillis();

    final GLEventReceiver receiver = new GLEventReceiver(renderCallback);
    glWin.addGLEventListener(receiver);
    glWin.setExclusiveContextThread(Thread.currentThread());

    while (!closeRequested && !receiver.shouldStop()) {
      glWin.display();
      input.update();
      frames++;
      final long now = System.currentTimeMillis();
      if (now - lastPrintTime > 1000) {
        lastPrintTime = now;
        final String fpsText = buildFpsText(frames);
        log.fine(fpsText);
        lastFPS = fpsText;
        frames = 0;
      }
    }

    glWin.removeGLEventListener(receiver);
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.lessvoid.coregl.CoreSetup#enableVSync(boolean)
   */
  @Override
  public void enableVSync(final boolean enable) {
    vsync = enable;
    updateVsync = true;
  }

  @Override
  public void enableFullscreen(final boolean enable) {
    glWin.setFullscreen(enable);
  }

  @Override
  public String getFPS() {
    return lastFPS;
  }

  private void initGraphics(final String title, final int requestedWidth, final int requestedHeight) throws Exception {
    final GLProfile profile = GLProfile.getMaxProgrammableCore(true);
    final GLCapabilities glc = new GLCapabilities(profile);
    newtDisp = NewtFactory.createDisplay(null);

    // get current Screen
    newtScreen = NewtFactory.createScreen(newtDisp, Screen.getActiveScreenNumber());
    newtScreen.addReference();

    // Create the actual window
    createWindow(title, requestedWidth, requestedHeight, glc);
    centerWindow();
    newtScreen.removeReference();
    glWin.setVisible(true);
    glWin.windowRepaint(0, 0, requestedWidth, requestedHeight);

    log.info("current mode: {}",
        newtScreen.getMainMonitor(new Rectangle(0, 0, requestedWidth, requestedHeight)).getCurrentMode().getSizeAndRRate());
  }

  private void createWindow(final String title, final int width, final int height, final GLCapabilities glc) {
    glWin = GLWindow.create(newtScreen, glc);
    glWin.setTitle(title);
    glWin.setSize(width, height);
    glWin.setDefaultCloseOperation(WindowClosingMode.DISPOSE_ON_CLOSE);
    glWin.addWindowListener(new WindowAdapter() {
      @Override
      public void windowDestroyNotify(final WindowEvent event) {
        closeRequested = true;
      }
    });
    enableVSync(false);
    enableFullscreen(false);
  }

  private void centerWindow() {
    glWin.setPosition((newtScreen.getWidth() - glWin.getWidth()) / 2, (newtScreen.getHeight() - glWin.getHeight()) / 2);
  }

  private void initInput() throws Exception {
    input = new CoreInputJogl(glWin);
    input.initialize();
  }

  private String buildFpsText(final long frameCounter) {
    fpsText.setLength(0);
    fpsText.append("fps: ");
    fpsText.append(frameCounter);
    fpsText.append(" (");
    fpsText.append(1000 / (float) frameCounter);
    fpsText.append(" ms)");
    return fpsText.toString();
  }

  private class GLEventReceiver implements GLEventListener {

    final RenderLoopCallback callback;

    long prevTime = System.nanoTime();

    GLEventReceiver(final RenderLoopCallback callback) {
      this.callback = callback;
    }

    @Override
    public void init(final GLAutoDrawable drawable) {
      // just output some info about the system we're on
      log.info("platform: {}", System.getProperty("os.name"));
      log.info("opengl version: {}", gl.glGetString(gl.GL_VERSION()));
      log.info("opengl vendor: {}", gl.glGetString(gl.GL_VENDOR()));
      log.info("opengl renderer: {}", gl.glGetString(gl.GL_RENDERER()));
      final IntBuffer maxVertexAttribts = CoreBufferUtil.createIntBuffer(4 * 4);
      gl.glGetIntegerv(gl.GL_MAX_VERTEX_ATTRIBS(), maxVertexAttribts);
      log.info("GL_MAX_VERTEX_ATTRIBS: {}", maxVertexAttribts.get(0));
      log.checkGLError(gl, "init phase 1");

      log.info("GL_MAX_3D_TEXTURE_SIZE: {}", gl.glGetInteger(gl.GL_MAX_3D_TEXTURE_SIZE()));

      gl.glViewport(0, 0, glWin.getWidth(), glWin.getHeight());

      gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
      gl.glClear(gl.GL_COLOR_BUFFER_BIT());
      gl.glEnable(gl.GL_BLEND());
      gl.glBlendFunc(gl.GL_SRC_ALPHA(), gl.GL_ONE_MINUS_SRC_ALPHA());
      log.checkGLError(gl, "initialized");
      callback.init(gl);
    }

    @Override
    public void dispose(final GLAutoDrawable drawable) {

    }

    @Override
    public void display(final GLAutoDrawable drawable) {
      if (updateVsync) {
        glWin.getGL().setSwapInterval((vsync) ? 1 : 0);
        updateVsync = false;
      }
      final long now = System.nanoTime();
      callback.render(gl, (now - prevTime) / NANO_TO_MS_CONVERSION);
      prevTime = now;
    }

    @Override
    public void reshape(final GLAutoDrawable drawable, final int x, final int y, final int width, final int height) {

    }

    boolean shouldStop() {
      return callback.endLoop();
    }
  }
}
