package com.lessvoid.coregl.lwjgl.input;

import com.lessvoid.coregl.spi.CoreGL;
import com.lessvoid.coregl.spi.CoreGLSetup.RenderLoopCallback;

import java.util.concurrent.atomic.AtomicBoolean;

public class CoreInputLwjglTest {

  public static void main(final String[] args) {
    /*
    final CoreGL gl = new LwjglCoreGL();
    final CoreSetup setup = new CoreSetupLwjgl(gl);
    try {
      setup.initialize("Test LWJGL Input", 1024, 768);
      setup.initializeLogging();
      Keyboard.enableRepeatEvents(true);
      final CoreInput input = setup.getInput();
      final CoreKeyListener keyInput = new CoreKeyListener() {
        @Override
        public void keyPressed(final CoreKeyEvent event) {
          System.out.println("pressed: " + event);
        }

        @Override
        public void keyReleased(final CoreKeyEvent event) {
          System.out.println("released: " + event);
        }
      };
      final CoreMouseListener mouseListener = new CoreMouseListener() {

        @Override
        public void mouseClicked(final CoreMouseEvent e) {
          System.out.println("clicked: " + e.getX() + " " + e.getY());
        }

        @Override
        public void mouseDragged(final CoreMouseEvent e) {
          System.out.println("dragged: " + e.getX() + " " + e.getY());
        }

        @Override
        public void mouseEntered(final CoreMouseEvent e) {
          System.out.println("entered: " + e.getX() + " " + e.getY());
        }

        @Override
        public void mouseExited(final CoreMouseEvent e) {
          System.out.println("exited: " + e.getX() + " " + e.getY());
        }

        @Override
        public void mouseMoved(final CoreMouseEvent e) {
          System.out.println("moved: " + e.getX() + " " + e.getY());
        }

        @Override
        public void mousePressed(final CoreMouseEvent e) {
          System.out.println("pressed: " + e.getX() + " " + e.getY());
        }

        @Override
        public void mouseReleased(final CoreMouseEvent e) {
          System.out.println("released: " + e.getX() + " " + e.getY());
        }

        @Override
        public void mouseWheelMoved(final CoreMouseEvent e) {
          System.out.println("mouseWheel: " + e.getX() + " " + e.getY());
        }
      };
      input.addListener(keyInput);
      input.addListener(mouseListener);
      setup.renderLoop(new NOPRenderLoop());
      setup.destroy();
    } catch (final Exception e) {
      e.printStackTrace();
    }
    */
  }

  private static class NOPRenderLoop implements RenderLoopCallback {

    private final AtomicBoolean stop = new AtomicBoolean();

    @Override
    public void init(final CoreGL gl, final int framebufferWidth, final int framebufferHeight) {
    }

    @Override
    public boolean render(final CoreGL gl, final float deltaTime) {
      return true;
    }

    @Override
    public boolean endLoop(final CoreGL gl) {
      return stop.get();
    }

    @Override
    public void sizeChanged(final CoreGL gl, final int width, final int height) {

    }
  }
}
