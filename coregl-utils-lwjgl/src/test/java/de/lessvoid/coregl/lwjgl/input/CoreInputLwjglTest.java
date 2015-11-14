package de.lessvoid.coregl.lwjgl.input;

import java.util.concurrent.atomic.AtomicBoolean;

import de.lessvoid.coregl.input.spi.CoreInput;
import de.lessvoid.coregl.input.spi.CoreKeyEvent;
import de.lessvoid.coregl.input.spi.CoreKeyListener;
import de.lessvoid.coregl.input.spi.CoreMouseEvent;
import de.lessvoid.coregl.input.spi.CoreMouseListener;
import de.lessvoid.coregl.lwjgl.CoreSetupLwjgl;
import de.lessvoid.coregl.lwjgl.LwjglCoreGL;
import de.lessvoid.coregl.spi.CoreGL;
import de.lessvoid.coregl.spi.CoreSetup;
import de.lessvoid.coregl.spi.CoreSetup.RenderLoopCallback;

public class CoreInputLwjglTest {

  public static void main(String[] args) {
    final CoreGL gl = new LwjglCoreGL();
    final CoreSetup setup = new CoreSetupLwjgl(gl);
    try {
      setup.initialize("Test LWJGL Input", 1024, 768);
      setup.initializeLogging();
      final CoreInput input = setup.getInput();
      final CoreKeyListener keyInput = new CoreKeyListener() {
        @Override
        public void keyPressed(CoreKeyEvent event) {
          System.out.println("pressed: " + event);
        }

        @Override
        public void keyReleased(CoreKeyEvent event) {

        }
      };
      final CoreMouseListener mouseListener = new CoreMouseListener() {

        @Override
        public void mouseClicked(CoreMouseEvent e) {
          System.out.println("clicked: " + e.getX() + " " + e.getY());
        }

        @Override
        public void mouseDragged(CoreMouseEvent e) {

        }

        @Override
        public void mouseEntered(CoreMouseEvent e) {

        }

        @Override
        public void mouseExited(CoreMouseEvent e) {

        }

        @Override
        public void mouseMoved(CoreMouseEvent e) {
          System.out.println("moved: " + e.getX() + " " + e.getY());
        }

        @Override
        public void mousePressed(CoreMouseEvent e) {
          System.out.println("pressed: " + e.getX() + " " + e.getY());
        }

        @Override
        public void mouseReleased(CoreMouseEvent e) {

        }

        @Override
        public void mouseWheelMoved(CoreMouseEvent e) {

        }
      };
      input.addListener(keyInput);
      input.addListener(mouseListener);
      setup.renderLoop(new NOPRenderLoop());
      setup.destroy();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static class NOPRenderLoop implements RenderLoopCallback {

    private final AtomicBoolean stop = new AtomicBoolean();

    @Override
    public void init(CoreGL gl) {
    }

    @Override
    public boolean render(CoreGL gl, float deltaTime) {
      return true;
    }

    @Override
    public boolean endLoop() {
      return stop.get();
    }
  }
}
