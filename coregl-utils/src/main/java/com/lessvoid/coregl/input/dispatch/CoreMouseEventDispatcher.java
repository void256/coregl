package com.lessvoid.coregl.input.dispatch;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.lessvoid.coregl.CoreLogger;
import com.lessvoid.coregl.input.spi.CoreInputEventDispatcher;
import com.lessvoid.coregl.input.spi.CoreMouseEvent;
import com.lessvoid.coregl.input.spi.CoreMouseListener;

public class CoreMouseEventDispatcher implements CoreInputEventDispatcher<CoreMouseEvent, CoreMouseListener> {

  private static final CoreLogger log = CoreLogger.getCoreLogger(CoreMouseEventDispatcher.class);

  private final Set<CoreMouseListener> listeners = Collections.synchronizedSet(new HashSet<CoreMouseListener>());

  @Override
  public void dispatch(final CoreMouseEvent event) {
    for (final CoreMouseListener listener : listeners) {
      if (event.isType(event.EVENT_MOUSE_CLICKED())) listener.mouseClicked(event);
      else if (event.isType(event.EVENT_MOUSE_DRAGGED())) listener.mouseDragged(event);
      else if (event.isType(event.EVENT_MOUSE_ENTERED())) listener.mouseEntered(event);
      else if (event.isType(event.EVENT_MOUSE_EXITED())) listener.mouseExited(event);
      else if (event.isType(event.EVENT_MOUSE_MOVED())) listener.mouseMoved(event);
      else if (event.isType(event.EVENT_MOUSE_PRESSED())) listener.mousePressed(event);
      else if (event.isType(event.EVENT_MOUSE_RELEASED())) listener.mouseReleased(event);
      else if (event.isType(event.EVENT_MOUSE_WHEEL_MOVED())) listener.mouseWheelMoved(event);
      else {
        log.warn("Ignoring unrecognized event: {}", event);
        break;
      }
    }
  }

  @Override
  public void register(final CoreMouseListener listener) {
    listeners.add(listener);
  }

  @Override
  public void unregister(final CoreMouseListener listener) {
    listeners.remove(listener);
  }

  @Override
  public Class<CoreMouseEvent> eventType() {
    return CoreMouseEvent.class;
  }

  @Override
  public Class<CoreMouseListener> listenerType() {
    return CoreMouseListener.class;
  }
}
