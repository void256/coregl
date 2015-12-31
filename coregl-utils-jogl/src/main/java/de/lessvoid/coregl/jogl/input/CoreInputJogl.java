package de.lessvoid.coregl.jogl.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.opengl.GLWindow;

import de.lessvoid.coregl.input.PendingEventQueue;
import de.lessvoid.coregl.input.spi.AbstractCoreInput;

public class CoreInputJogl extends AbstractCoreInput {

  private static final Logger log = LoggerFactory.getLogger(CoreInputJogl.class);

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
      log.trace("keyPressed: {}", arg0);
      eventQueue.enqueue(new CoreKeyEventJogl(arg0));
    }

    @Override
    public void keyReleased(final KeyEvent arg0) {
      // ignore release events sent by auto-repeat
      if (arg0.isAutoRepeat()) return;
      log.info("keyReleased: {}", arg0);
      eventQueue.enqueue(new CoreKeyEventJogl(arg0));
    }
  }

  private class NewtMouseListener implements MouseListener {

    @Override
    public void mouseClicked(final MouseEvent arg0) {
      log.trace("mouseClicked: {}", arg0);
      eventQueue.enqueue(new CoreMouseEventJogl(arg0));
    }

    @Override
    public void mouseDragged(final MouseEvent arg0) {
      log.trace("mouseDragged: {}", arg0);
      eventQueue.enqueue(new CoreMouseEventJogl(arg0));
    }

    @Override
    public void mouseEntered(final MouseEvent arg0) {
      log.trace("mouseEntered: {}", arg0);
      eventQueue.enqueue(new CoreMouseEventJogl(arg0));
    }

    @Override
    public void mouseExited(final MouseEvent arg0) {
      log.trace("mouseExited: {}", arg0);
      eventQueue.enqueue(new CoreMouseEventJogl(arg0));
    }

    @Override
    public void mouseMoved(final MouseEvent arg0) {
      log.trace("mouseMoved: {}", arg0);
      eventQueue.enqueue(new CoreMouseEventJogl(arg0));
    }

    @Override
    public void mousePressed(final MouseEvent arg0) {
      log.trace("mousePressed: {}", arg0);
      eventQueue.enqueue(new CoreMouseEventJogl(arg0));
    }

    @Override
    public void mouseReleased(final MouseEvent arg0) {
      log.trace("mouseReleased: {}", arg0);
      eventQueue.enqueue(new CoreMouseEventJogl(arg0));
    }

    @Override
    public void mouseWheelMoved(final MouseEvent arg0) {
      log.trace("mouseWheelMoved: {}", arg0);
      eventQueue.enqueue(new CoreMouseEventJogl(arg0));
    }
  }
}
