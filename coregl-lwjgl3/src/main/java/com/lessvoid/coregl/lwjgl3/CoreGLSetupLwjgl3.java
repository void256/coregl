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
package com.lessvoid.coregl.lwjgl3;

import com.lessvoid.coregl.input.spi.CoreInput;
import com.lessvoid.coregl.spi.CoreGL;
import com.lessvoid.coregl.spi.CoreGLSetup;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.Platform;

import java.nio.IntBuffer;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_DEPTH_BITS;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_STENCIL_BITS;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetFramebufferSize;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_RENDERER;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.GL_VENDOR;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL11.glGetIntegerv;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL12.GL_MAX_3D_TEXTURE_SIZE;
import static org.lwjgl.opengl.GL20.GL_MAX_VERTEX_ATTRIBS;

public class CoreGLSetupLwjgl3 implements CoreGLSetup {
  private static final Logger log = Logger.getLogger(CoreGLSetupLwjgl3.class.getName());
  private final StringBuilder fpsText = new StringBuilder();
  private static final float NANO_TO_MS_CONVERSION = 1000000.f;
  private final CoreGL gl;
  private CoreInput input;
  private long window;
  private boolean fullscreen;
  private String lastFPS = "";
  private FramebufferSizeChangedCallback framebufferSizeChangedCallback;

  private static final class FramebufferSizeChangedCallback implements GLFWFramebufferSizeCallbackI {
    private RenderLoopCallback renderLoopCallback;
    private CoreGL gl;

    private FramebufferSizeChangedCallback(final RenderLoopCallback renderLoopCallback, final CoreGL gl) {
      this.renderLoopCallback = renderLoopCallback;
      this.gl = gl;
    }

    @Override
    public void invoke(final long window, final int width, final int height) {
      renderLoopCallback.sizeChanged(gl, width, height);
    }
  }

  public CoreGLSetupLwjgl3(final CoreGL gl) {
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
      LogManager.getLogManager().readConfiguration(CoreGLSetupLwjgl3.class.getResourceAsStream(loggingProperties));
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

    framebufferSizeChangedCallback = new FramebufferSizeChangedCallback(renderLoop, gl);
    glfwSetFramebufferSizeCallback(window, framebufferSizeChangedCallback);

    int width[] = new int[1];
    int height[] = new int[1];
    glfwGetFramebufferSize(window, width, height);
    framebufferSizeChangedCallback.invoke(window, width[0], height[0]);

    while (!glfwWindowShouldClose(window) && !renderLoop.endLoop(gl)) {
      final long nanoTime = System.nanoTime();
      if (renderLoop.render(gl, (nanoTime - prevTime) / NANO_TO_MS_CONVERSION)) {
        glfwPollEvents();
        glfwSwapBuffers(window);
        if (input != null) {
          input.update();
        }
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
    glfwSwapInterval(enable ? 1 : 0);
  }

  /**
   * Note: this must be called BEFORE the GLFW window is created via {@link #initGraphics(String, int, int)}
   */
  @Override
  public void enableFullscreen(final boolean enable) {
    fullscreen = enable;
  }

  @Override
  public String getFPS() {
    return lastFPS;
  }

  private void initGraphics(final String title, final int requestedWidth, final int requestedHeight) throws Exception {
    if (!glfwInit()) {
      throw new Exception("unable to init glfw");
    }

    final int width = requestedWidth;
    final int height = requestedHeight;

    // Create the actual window
    createWindow(title, width, height);

    // just output some infos about the system we're on
    log.info("platform: " + Platform.get());
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

  private void createWindow(final String title, final int width, final int height) {
    GLFWErrorCallback errorCallback = GLFWErrorCallback.createPrint();
    glfwSetErrorCallback(errorCallback);

    glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
    glfwWindowHint(GLFW_STENCIL_BITS, 8);
    glfwWindowHint(GLFW_DEPTH_BITS, 24);

    int windowWidth = width;
    int windowHeight = height;
    long monitor = glfwGetPrimaryMonitor();
    if (fullscreen) {
        GLFWVidMode vidMode = glfwGetVideoMode(monitor);
        windowWidth = vidMode.width();
        windowHeight = vidMode.height();
    }

    window = glfwCreateWindow(windowWidth, windowHeight, title, 0, 0);
    if (window == 0) {
      glfwTerminate();
      throw new RuntimeException("failed to create window");
    }
    glfwMakeContextCurrent(window);
    GL.createCapabilities();
    glfwShowWindow(window);
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
