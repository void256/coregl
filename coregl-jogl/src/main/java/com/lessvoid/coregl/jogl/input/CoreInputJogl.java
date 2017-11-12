package com.lessvoid.coregl.jogl.input;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.opengl.GLWindow;
import com.lessvoid.coregl.CoreLogger;
import com.lessvoid.coregl.input.PendingEventQueue;
import com.lessvoid.coregl.input.spi.AbstractCoreInput;

public class CoreInputJogl extends AbstractCoreInput {

  private static final CoreLogger log = CoreLogger.getCoreLogger(CoreInputJogl.class);

  private final NewtKeyListener keyListener = new NewtKeyListener();
  private final NewtMouseListener mouseListener = new NewtMouseListener();
  private final GLWindow glWin;

  private PendingEventQueue eventQueue;

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
    if (eventQueue != null) return;

    eventQueue = new PendingEventQueue(this);

    // initialize and register default dispatchers
    enableDefaultDispatchers();

    glWin.addKeyListener(keyListener);
    glWin.addMouseListener(mouseListener);

    log.info("{}: Initialized input system for NEWT window: [{}]", getClass().getSimpleName(), glWin);
  }

  @Override
  public void update() {
    eventQueue.flush();
  }

  @Override
  public void dispose() {
    glWin.removeKeyListener(keyListener);
    glWin.removeMouseListener(mouseListener);
    eventQueue = null;

    log.info("{}: Disposed input system for NEWT window: [{}]", getClass().getSimpleName(), glWin);
  }

  private class NewtKeyListener implements KeyListener {

    @Override
    public void keyPressed(final KeyEvent arg0) {
      log.fine("keyPressed: {}", arg0);
      eventQueue.enqueue(new CoreKeyEventJogl(arg0));
    }

    @Override
    public void keyReleased(final KeyEvent arg0) {
      // ignore release events sent by auto-repeat
      if (arg0.isAutoRepeat()) return;
      log.fine("keyReleased: {}", arg0);
      eventQueue.enqueue(new CoreKeyEventJogl(arg0));
    }
  }

  private class NewtMouseListener implements MouseListener {

    @Override
    public void mouseClicked(final MouseEvent arg0) {
      log.fine("mouseClicked: {}", arg0);
      eventQueue.enqueue(new CoreMouseEventJogl(arg0));
    }

    @Override
    public void mouseDragged(final MouseEvent arg0) {
      log.fine("mouseDragged: {}", arg0);
      eventQueue.enqueue(new CoreMouseEventJogl(arg0));
    }

    @Override
    public void mouseEntered(final MouseEvent arg0) {
      log.fine("mouseEntered: {}", arg0);
      eventQueue.enqueue(new CoreMouseEventJogl(arg0));
    }

    @Override
    public void mouseExited(final MouseEvent arg0) {
      log.fine("mouseExited: {}", arg0);
      eventQueue.enqueue(new CoreMouseEventJogl(arg0));
    }

    @Override
    public void mouseMoved(final MouseEvent arg0) {
      log.fine("mouseMoved: {}", arg0);
      eventQueue.enqueue(new CoreMouseEventJogl(arg0));
    }

    @Override
    public void mousePressed(final MouseEvent arg0) {
      log.fine("mousePressed: {}", arg0);
      eventQueue.enqueue(new CoreMouseEventJogl(arg0));
    }

    @Override
    public void mouseReleased(final MouseEvent arg0) {
      log.fine("mouseReleased: {}", arg0);
      eventQueue.enqueue(new CoreMouseEventJogl(arg0));
    }

    @Override
    public void mouseWheelMoved(final MouseEvent arg0) {
      log.fine("mouseWheelMoved: {}", arg0);
      eventQueue.enqueue(new CoreMouseEventJogl(arg0));
    }
  }
}
