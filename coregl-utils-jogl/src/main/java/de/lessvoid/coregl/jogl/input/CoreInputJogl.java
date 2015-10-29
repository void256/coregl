package de.lessvoid.coregl.jogl.input;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.opengl.GLWindow;

import de.lessvoid.coregl.input.PollingEventQueue;
import de.lessvoid.coregl.input.dispatch.CoreKeyEventDispatcher;
import de.lessvoid.coregl.input.dispatch.CoreMouseEventDispatcher;
import de.lessvoid.coregl.input.spi.AbstractCoreInput;

public class CoreInputJogl extends AbstractCoreInput {
  private final NewtKeyListener keyListener = new NewtKeyListener();
  private final NewtMouseListener mouseListener = new NewtMouseListener();
  private final GLWindow glWin;

  private PollingEventQueue eventQueue;

  // default key/mouse dispatchers
  private CoreKeyEventDispatcher keyEventDispatch;
  private CoreMouseEventDispatcher mouseEventDispatch;

  /**
   * Creates a new JOGL CoreInput system with initialized listeners and
   * dispatchers for key/mouse input events.
   *
   * @param glWin
   *          the GLWindow to which key/mouse listeners will be attached
   */
  public CoreInputJogl(final GLWindow glWin) {
    if (glWin == null) throw new IllegalArgumentException("Illegal null argument value.");

    this.glWin = glWin;
  }

  @Override
  public void initialize() {
    if (eventQueue != null && eventQueue.isRunning()) return;

    eventQueue = new PollingEventQueue(this);
    eventQueue.start();

    // initialize and register default dispatchers
    enableDefaultDispatchers();

    glWin.addKeyListener(keyListener);
    glWin.addMouseListener(mouseListener);
  }

  @Override
  public void dispose() {
    glWin.removeKeyListener(keyListener);
    glWin.removeMouseListener(mouseListener);
    if (eventQueue != null) eventQueue.destroy();
  }

  public void enableDefaultDispatchers() {
    if (keyEventDispatch == null) {
      keyEventDispatch = new CoreKeyEventDispatcher();
      super.register(keyEventDispatch);
    }

    if (mouseEventDispatch == null) {
      mouseEventDispatch = new CoreMouseEventDispatcher();
      super.register(mouseEventDispatch);
    }
  }

  public void disableDefaultDispatchers() {
    if (keyEventDispatch != null) {
      super.unregister(keyEventDispatch);
      keyEventDispatch = null;
    }

    if (mouseEventDispatch != null) {
      super.unregister(mouseEventDispatch);
      mouseEventDispatch = null;
    }
  }

  private class NewtKeyListener implements KeyListener {

    @Override
    public void keyPressed(KeyEvent arg0) {
      eventQueue.enqueue(new CoreKeyEventJogl(arg0));
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
      eventQueue.enqueue(new CoreKeyEventJogl(arg0));
    }
  }

  private class NewtMouseListener implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent arg0) {
      eventQueue.enqueue(new CoreMouseEventJogl(arg0));
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
      eventQueue.enqueue(new CoreMouseEventJogl(arg0));
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
      eventQueue.enqueue(new CoreMouseEventJogl(arg0));
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
      eventQueue.enqueue(new CoreMouseEventJogl(arg0));
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
      eventQueue.enqueue(new CoreMouseEventJogl(arg0));
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
      eventQueue.enqueue(new CoreMouseEventJogl(arg0));
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
      eventQueue.enqueue(new CoreMouseEventJogl(arg0));
    }

    @Override
    public void mouseWheelMoved(MouseEvent arg0) {
      eventQueue.enqueue(new CoreMouseEventJogl(arg0));
    }
  }
}
