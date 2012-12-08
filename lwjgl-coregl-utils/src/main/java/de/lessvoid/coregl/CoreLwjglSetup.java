package de.lessvoid.coregl;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL15.*;
import org.lwjgl.opengl.PixelFormat;

/**
 * This class helps to initialize LWJGL and to execute the renderloop.
 * @author void
 */
public class CoreLwjglSetup {
  private static final Logger log = Logger.getLogger(CoreLwjglSetup.class.getName());
  private static final Comparator<DisplayMode> DisplayModeFrequencyComparator = new DisplayModeFrequencyComparator();
  private final StringBuilder fpsText = new StringBuilder();
  private static final float NANO_TO_MS_CONVERSION = 1000000.f;

  /**
   * You can implement this interface when you use the renderLoop() method. This will be called each frame and allows
   * you to actually draw.
   * @author void
   */
  public static interface RenderLoopCallback {

    /**
     * Do some awesome stuff in here!
     * @param l 
     * @return true when the render loop should be stopped and false if you want it to continue.
     */
    boolean render(float deltaTime);
  }

  /**
   * (optional) This method will just set a new jdk14 Formatter that is more readable then the defaults.
   */
  public void initializeLogging() {
    for (Handler handler : Logger.getLogger("").getHandlers()) {
      handler.setFormatter(new Formatter() {
        @Override
        public String format(final LogRecord record) {
          Throwable throwable = record.getThrown();
          if (throwable != null) {
            throwable.printStackTrace();
          }
           return
             record.getMillis() + " " +  
             record.getLevel() + " [" +
             record.getSourceClassName() + "] " +
             record.getMessage() + "\n";
        }
      });
    }

    try {
      LogManager.getLogManager().readConfiguration((new Object()).getClass().getResourceAsStream("/logging.properties"));
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  /**
   * Initialize LWJGL.
   * @param title The title of the window
   * @param width width of the screen
   * @param height height of the screen
   * @throws Exception in case of any errors
   */
  public void initialize(final String title, final int width, final int height) throws Exception {
    initGraphics(title, width, height);
    initInput();
  }

  /**
   * Destroy LWJGL and free resources.
   */
  public void destroy() {
    Display.destroy();
  }

  /**
   * The renderLoop will keep calling Display.update() and calls the RenderLoopCallback each frame.
   * @param renderLoop the RenderLoopCallback implementation
   */
  public void renderLoop(final RenderLoopCallback renderLoop) {
    boolean done = false;
    long frameCounter = 0;
    long now = System.currentTimeMillis();
    long prevTime = System.nanoTime();

    while (!Display.isCloseRequested() && !done) {
      long nanoTime = System.nanoTime();
      done = renderLoop.render((nanoTime - prevTime) / NANO_TO_MS_CONVERSION);
      prevTime = nanoTime;
      Display.update();
      CoreCheckGL.checkGLError("render loop check for errors");

      frameCounter++;
      long diff = System.currentTimeMillis() - now;
      if (diff >= 1000) {
        now += diff;
        log.info(buildFpsText(frameCounter));
        frameCounter = 0;
      }
    }
  }

  private void initGraphics(final String title, final int requestedWidth, final int requestedHeight) throws Exception {
    // get current DisplayMode
    DisplayMode currentMode = Display.getDisplayMode();
    logMode("currentMode: ", currentMode);

    // find a matching DisplayMode by size
    DisplayMode[] matchingModes = sizeMatch(requestedWidth, requestedHeight);

    // match by frequency
    DisplayMode selectedMode = frequencyMatch(matchingModes, currentMode.getFrequency());
    if (selectedMode == null) {
      selectedMode = fallbackMode(matchingModes);
    }

    // change the DisplayMode to the selectedMode
    Display.setDisplayMode(selectedMode);

    // make sure we center the display
    centerDisplay(currentMode);

    // Create the actual window
    createWindow(title);
    logMode("current mode: ", Display.getDisplayMode());

    // just output some infos about the system we're on
    log.info("plattform: " + LWJGLUtil.getPlatformName());
    log.info("opengl version: " + glGetString(GL_VERSION));
    log.info("opengl vendor: " + glGetString(GL_VENDOR));
    log.info("opengl renderer: " + glGetString(GL_RENDERER));
    IntBuffer maxVertexAttribts = BufferUtils.createIntBuffer(4 * 4);
    glGetInteger(GL_MAX_VERTEX_ATTRIBS, maxVertexAttribts);
    log.info("GL_MAX_VERTEX_ATTRIBS: " + maxVertexAttribts.get(0));
    CoreCheckGL.checkGLError("init phase 1");

    IntBuffer viewportBuffer = BufferUtils.createIntBuffer(4 * 4);
    glGetInteger(GL_VIEWPORT, viewportBuffer);
    int viewportWidth = viewportBuffer.get(2);
    int viewportHeight = viewportBuffer.get(3);

    log.info("GL_MAX_3D_TEXTURE_SIZE: " + glGetInteger(GL_MAX_3D_TEXTURE_SIZE));
    
    glViewport(0, 0, Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight());

    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    glClear(GL_COLOR_BUFFER_BIT);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    CoreCheckGL.checkGLError("initialized");
  }

  private void createWindow(final String title) throws LWJGLException {
    Display.setFullscreen(false);
    Display.create(new PixelFormat().withSamples(8), new ContextAttribs(3, 2).withProfileCore(true));
    Display.setVSyncEnabled(false);
    Display.setTitle(title);
  }

  private DisplayMode[] sizeMatch(final int requestedWidth, final int requestedHeight) throws LWJGLException {
    DisplayMode[] modes = Display.getAvailableDisplayModes();
    log.fine("Found " + modes.length + " display modes");

    List<DisplayMode> matching = new ArrayList<DisplayMode>();
    for (int i = 0; i < modes.length; i++) {
      DisplayMode mode = modes[i];
      if (matchesRequestedMode(requestedWidth, requestedHeight, mode)) {
        logMode("matching mode: ", mode);
        matching.add(mode);
      }
    }

    DisplayMode[] matchingModes = matching.toArray(new DisplayMode[0]);
    return matchingModes;
  }

  private DisplayMode frequencyMatch(final DisplayMode[] matchingModes, final int frequency) {
    for (int i = 0; i < matchingModes.length; i++) {
      if (matchingModes[i].getFrequency() == frequency) {
        logMode("using frequency matching mode: ", matchingModes[i]);
        return matchingModes[i];
      }
    }
    return null;
  }

  private DisplayMode fallbackMode(final DisplayMode[] matchingModes) {
    Arrays.sort(matchingModes, DisplayModeFrequencyComparator);
    logMode("using fallback mode: ", matchingModes[0]);
    return matchingModes[0];
  }

  private void centerDisplay(final DisplayMode currentMode) {
    int x = (currentMode.getWidth() - Display.getDisplayMode().getWidth()) / 2;
    int y = (currentMode.getHeight() - Display.getDisplayMode().getHeight()) / 2;
    Display.setLocation(x, y);
  }

  private boolean matchesRequestedMode(final int requestedWidth, final int requestedHeight, final DisplayMode mode) {
    return
        mode.getWidth() == requestedWidth &&
        mode.getHeight() == requestedHeight &&
        mode.getBitsPerPixel() == 32;
  }

  private void logMode(final String message, final DisplayMode currentMode) {
    log.info(
        message +
        currentMode.getWidth() + ", " +
        currentMode.getHeight() + ", " +
        currentMode.getBitsPerPixel() + ", " +
        currentMode.getFrequency());
  }

  private void initInput() throws Exception {
    // TODO ...
  }

  private String buildFpsText(long frameCounter) {
    fpsText.setLength(0);
    fpsText.append("fps: ");
    fpsText.append(frameCounter);
    fpsText.append(" (");
    fpsText.append(1000 / (float) frameCounter);
    fpsText.append(" ms)");
    return fpsText.toString();
  }

  private static class DisplayModeFrequencyComparator implements Comparator<DisplayMode> {
    public int compare(final DisplayMode o1, final DisplayMode o2) {
      if (o1.getFrequency() > o2.getFrequency()) {
        return 1;
      } else if (o1.getFrequency() < o2.getFrequency()) {
        return -1;
      } else {
        return 0;
      }
    }
  }
}
