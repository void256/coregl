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
package de.lessvoid.coregl.lwjgl3;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_MAX_3D_TEXTURE_SIZE;
import static org.lwjgl.opengl.GL20.GL_MAX_VERTEX_ATTRIBS;

import java.nio.IntBuffer;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.Platform;

import de.lessvoid.coregl.input.spi.CoreInput;
import de.lessvoid.coregl.spi.CoreGL;
import de.lessvoid.coregl.spi.CoreSetup;

public class CoreSetupLwjgl3 implements CoreSetup {
  private static final Logger log = Logger.getLogger(CoreSetupLwjgl3.class.getName());
  private final StringBuilder fpsText = new StringBuilder();
  private static final float NANO_TO_MS_CONVERSION = 1000000.f;
  private final CoreGL gl;
  private CoreInput input;
  private long window;
  private String lastFPS = "";

  public CoreSetupLwjgl3(final CoreGL gl) {
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
      LogManager.getLogManager().readConfiguration(CoreSetupLwjgl3.class.getResourceAsStream(loggingProperties));
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
    throw new UnsupportedOperationException ("LWJGL3 input not yet supported");
  }

  /*
   * (non-Javadoc)
   *
   * @see de.lessvoid.coregl.CoreDisplaySetup#destroy()
   */
  @Override
  public void destroy() {
    if (input != null) input.dispose();
    glfwDestroyWindow(window);
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
    while (glfwWindowShouldClose(window) == gl.GL_FALSE() && !renderLoop.endLoop()) {
      final long nanoTime = System.nanoTime();
      if (renderLoop.render(gl, (nanoTime - prevTime) / NANO_TO_MS_CONVERSION)) {
        glfwPollEvents ();
        glfwSwapBuffers(window);
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

  }

  @Override
  public void enableFullscreen(final boolean enable) {

  }

  @Override
  public String getFPS() {
    return lastFPS;
  }

  private void initGraphics(final String title, final int requestedWidth, final int requestedHeight) throws Exception {

    final int width = requestedWidth;
    final int height = requestedHeight;

    // Create the actual window
    createWindow(title, width, height);

    // just output some infos about the system we're on
    log.info("plattform: " + Platform.get());
    log.info("opengl version: " + glGetString(GL_VERSION));
    log.info("opengl vendor: " + glGetString(GL_VENDOR));
    log.info("opengl renderer: " + glGetString(GL_RENDERER));
    final IntBuffer maxVertexAttribts = BufferUtils.createIntBuffer(4 * 4);
    glGetIntegerv(GL_MAX_VERTEX_ATTRIBS, maxVertexAttribts);
    log.info("GL_MAX_VERTEX_ATTRIBS: " + maxVertexAttribts.get(0));
    gl.checkGLError("init phase 1");

    log.info("GL_MAX_3D_TEXTURE_SIZE: " + glGetInteger(GL_MAX_3D_TEXTURE_SIZE));

    glViewport(0, 0, width, height);

    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    glClear(GL_COLOR_BUFFER_BIT);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    gl.checkGLError("initialized");
  }

  private void createWindow(final String title, int width, int height) {
    glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
    glfwWindowHint(GLFW_OPENGL_CORE_PROFILE, GL_TRUE);
    glfwWindowHint(GLFW_STENCIL_BITS, 8);
    glfwWindowHint(GLFW_DEPTH_BITS, 24);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
    window = glfwCreateWindow(width, height, title, 0, 0);
  }

  private void initInput() throws Exception {
    // input = new CoreInputLwjgl();
    // input.initialize();
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
}
