/**
 * Copyright (c) 2013, Jens Hohmuth
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.lessvoid.coregl.lwjgl;

import com.lessvoid.coregl.input.spi.CoreInput;
import com.lessvoid.coregl.lwjgl.input.CoreInputLwjgl;
import com.lessvoid.coregl.spi.CoreGL;
import com.lessvoid.coregl.spi.CoreGLSetup;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

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

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_RENDERER;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_VENDOR;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL12.GL_MAX_3D_TEXTURE_SIZE;
import static org.lwjgl.opengl.GL20.GL_MAX_VERTEX_ATTRIBS;

public class CoreGLSetupLwjgl implements CoreGLSetup {
  private static final Logger log = Logger.getLogger(CoreGLSetupLwjgl.class.getName());
  private static final Comparator<DisplayMode> DisplayModeFrequencyComparator = new DisplayModeFrequencyComparator();
  private final StringBuilder fpsText = new StringBuilder();
  private static final float NANO_TO_MS_CONVERSION = 1000000.f;
  private final CoreGL gl;
  private CoreInput input;
  private String lastFPS = "";

  public CoreGLSetupLwjgl(final CoreGL gl) {
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
      LogManager.getLogManager().readConfiguration(CoreGLSetupLwjgl.class.getResourceAsStream(loggingProperties));
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
    if (input != null) input.dispose();
    Display.destroy();
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.lessvoid.coregl.CoreDisplaySetup#renderLoop(de.lessvoid.coregl.
   * CoreDisplaySetup.RenderLoopCallback)
   */
  @Override
  public void renderLoop(final RenderLoopCallback renderLoop) {
    long frameCounter = 0;
    long now = System.currentTimeMillis();
    long prevTime = System.nanoTime();

    renderLoop.init(gl);
    while (!Display.isCloseRequested() && !renderLoop.endLoop(gl)) {
      final long nanoTime = System.nanoTime();
      if (renderLoop.render(gl, (nanoTime - prevTime) / NANO_TO_MS_CONVERSION)) {
        Display.update();
        input.update();
      }
      prevTime = nanoTime;

      frameCounter++;
      final long diff = System.currentTimeMillis() - now;
      if (diff >= 1000) {
        now += diff;
        final String fpsText = buildFpsText(frameCounter);
        lastFPS = fpsText;
        log.fine(fpsText);
        frameCounter = 0;
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.lessvoid.coregl.CoreSetup#enableVSync(boolean)
   */
  @Override
  public void enableVSync(final boolean enable) {
    Display.setVSyncEnabled(enable);
  }

  @Override
  public void enableFullscreen(final boolean enable) {
    try {
      Display.setFullscreen(enable);
    } catch (final LWJGLException e) {
      log.warning("error setting fullscreen flag on Display: " + e.getMessage());
    }
  }

  @Override
  public String getFPS() {
    return lastFPS;
  }

  private void initGraphics(final String title, final int requestedWidth, final int requestedHeight) throws Exception {
    // get current DisplayMode
    final DisplayMode currentMode = Display.getDisplayMode();
    final int bitDepth = currentMode.getBitsPerPixel();
    logMode("current mode: ", currentMode);

    // find a matching DisplayMode by size
    final DisplayMode[] matchingModes = sizeMatch(requestedWidth, requestedHeight, bitDepth);

    log.info("found " + matchingModes.length + " DisplayMode(s) matching size");
    // match by frequency
    DisplayMode selectedMode = frequencyMatch(matchingModes, currentMode.getFrequency());
    if (selectedMode == null && matchingModes.length > 0) {
      selectedMode = fallbackMode(matchingModes);
    } else if (selectedMode == null) {
      log.warning("failed to match requested display mode: falling back to default");
      selectedMode = new DisplayMode(requestedWidth, requestedHeight);
    }

    // change the DisplayMode to the selectedMode
    Display.setDisplayMode(selectedMode);

    // make sure we center the display
    centerDisplay(currentMode);

    // Create the actual window
    createWindow(title);
    logMode("selected mode: ", Display.getDisplayMode());

    // just output some infos about the system we're on
    log.info("plattform: " + LWJGLUtil.getPlatformName());
    log.info("opengl version: " + glGetString(GL_VERSION));
    log.info("opengl vendor: " + glGetString(GL_VENDOR));
    log.info("opengl renderer: " + glGetString(GL_RENDERER));
    final IntBuffer maxVertexAttribts = BufferUtils.createIntBuffer(4 * 4);
    glGetInteger(GL_MAX_VERTEX_ATTRIBS, maxVertexAttribts);
    log.info("GL_MAX_VERTEX_ATTRIBS: " + maxVertexAttribts.get(0));
    gl.checkGLError("init phase 1");

    log.info("GL_MAX_3D_TEXTURE_SIZE: " + glGetInteger(GL_MAX_3D_TEXTURE_SIZE));

    glViewport(0, 0, Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight());

    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    glClear(GL_COLOR_BUFFER_BIT);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    gl.checkGLError("initialized");
  }

  private void createWindow(final String title) throws LWJGLException {
    Display.setFullscreen(false);
    Display.create(new PixelFormat().withStencilBits(8).withDepthBits(24),
                   new ContextAttribs(3, 2).withProfileCore(true));
    Display.setVSyncEnabled(false);
    Display.setTitle(title);
  }

  private DisplayMode[] sizeMatch(final int requestedWidth,
                                  final int requestedHeight,
                                  final int requestedBitDepth) throws LWJGLException {
    final DisplayMode[] modes = Display.getAvailableDisplayModes();
    log.fine("Found " + modes.length + " display modes");

    final List<DisplayMode> matching = new ArrayList<DisplayMode>();
    for (int i = 0; i < modes.length; i++) {
      final DisplayMode mode = modes[i];
      if (matchesRequestedMode(requestedWidth, requestedHeight, requestedBitDepth, mode)) {
        logMode("matching mode: ", mode);
        matching.add(mode);
      }
    }

    final DisplayMode[] matchingModes = matching.toArray(new DisplayMode[matching.size()]);
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
    final int x = (currentMode.getWidth() - Display.getDisplayMode().getWidth()) / 2;
    final int y = (currentMode.getHeight() - Display.getDisplayMode().getHeight()) / 2;
    Display.setLocation(x, y);
  }

  private boolean matchesRequestedMode(final int requestedWidth,
                                       final int requestedHeight,
                                       final int requestedBitDepth,
                                       final DisplayMode mode) {
    return mode.getWidth() == requestedWidth && mode.getHeight() == requestedHeight
        && mode.getBitsPerPixel() == requestedBitDepth;
  }

  private void logMode(final String message, final DisplayMode currentMode) {
    log.info(message + currentMode.getWidth() + ", " + currentMode.getHeight() + ", " + currentMode.getBitsPerPixel()
        + ", " + currentMode.getFrequency());
  }

  private void initInput() throws Exception {
    input = new CoreInputLwjgl();
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

  private static class DisplayModeFrequencyComparator implements Comparator<DisplayMode> {
    @Override
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
