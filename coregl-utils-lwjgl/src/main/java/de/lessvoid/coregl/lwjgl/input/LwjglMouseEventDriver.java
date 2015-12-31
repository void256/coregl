package de.lessvoid.coregl.lwjgl.input;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

import de.lessvoid.coregl.input.spi.CoreMouseEvent;

final class LwjglMouseEventDriver {

  private static final LwjglMouseEventDriver instance = new LwjglMouseEventDriver();

  private int mx, my;
  private boolean inWindow;

  public void init() throws LWJGLException {
    if (!Mouse.isCreated()) Mouse.create();
    mx = Mouse.getX();
    my = Mouse.getY();
    inWindow = Mouse.isInsideWindow();
  }

  public void destroy() {
    if (Mouse.isCreated()) Mouse.destroy();
  }

  public Collection<CoreMouseEvent> update() {
    final List<CoreMouseEvent> mouseEvents = new ArrayList<CoreMouseEvent>();
    final boolean isBtnEvent = Mouse.getEventButton() >= 0;
    final boolean eventBtnDown = Mouse.getEventButtonState();
    boolean anyBtnDown = false;
    for (int i = 0; i < Mouse.getButtonCount(); i++) {
      if (Mouse.isButtonDown(i)) {
        anyBtnDown = true;
        break;
      }
    }
    final boolean mouseMoved = Mouse.getEventDX() > 0 || Mouse.getEventDY() > 0;
    final boolean mouseWheelMoved = Mouse.getDWheel() > 0;
    final boolean inWindowUpdated = Mouse.isInsideWindow();

    if (isBtnEvent) {
      if (eventBtnDown) {
        mouseEvents.add(CoreMouseEventLwjgl.createMouseEventFromCurrentState(CoreMouseEventLwjgl.EVENT_MOUSE_PRESSED));
      } else {
        mouseEvents.add(CoreMouseEventLwjgl.createMouseEventFromCurrentState(CoreMouseEventLwjgl.EVENT_MOUSE_RELEASED));
        mouseEvents.add(CoreMouseEventLwjgl.createMouseEventFromCurrentState(CoreMouseEventLwjgl.EVENT_MOUSE_CLICKED));
      }
    }

    if (anyBtnDown && mouseMoved) {
      mouseEvents.add(CoreMouseEventLwjgl.createMouseEventFromCurrentState(CoreMouseEventLwjgl.EVENT_MOUSE_DRAGGED));
    } else if (mouseMoved) {
      mouseEvents.add(CoreMouseEventLwjgl.createMouseEventFromCurrentState(CoreMouseEventLwjgl.EVENT_MOUSE_MOVED));
    }

    if (mouseWheelMoved) {
      mouseEvents
          .add(CoreMouseEventLwjgl.createMouseEventFromCurrentState(CoreMouseEventLwjgl.EVENT_MOUSE_WHEEL_MOVED));
    }

    if (inWindowUpdated && !inWindow) {
      mouseEvents.add(CoreMouseEventLwjgl.createMouseEventFromCurrentState(CoreMouseEventLwjgl.EVENT_MOUSE_ENTERED));
    } else if (!inWindowUpdated && inWindow) {
      mouseEvents.add(CoreMouseEventLwjgl.createMouseEventFromCurrentState(CoreMouseEventLwjgl.EVENT_MOUSE_EXITED));
    }

    mx = Mouse.getX();
    my = Mouse.getY();
    inWindow = inWindowUpdated;

    return Collections.unmodifiableList(mouseEvents);
  }

  /**
   * @return the absolute mouse x coord since the last call to {@link #update()}
   */
  public int getX() {
    return mx;
  }

  /**
   * @return the absolute mouse y coord since the last call to {@link #update()}
   */
  public int getY() {
    return my;
  }

  /**
   * @return whether or not mouse is within the window bounds since last call to
   *         {@link #update()}
   */
  public boolean isInWindow() {
    return inWindow;
  }

  /**
   * @return the singleton instance of the mouse event driver
   */
  public static LwjglMouseEventDriver getInstance() {
    return instance;
  }

  private LwjglMouseEventDriver() {
  }
}
