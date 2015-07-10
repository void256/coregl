package de.lessvoid.coregl.input.dispatch;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lessvoid.coregl.input.spi.CoreInputEventDispatcher;
import de.lessvoid.coregl.input.spi.CoreKeyEvent;
import de.lessvoid.coregl.input.spi.CoreKeyListener;

public class CoreKeyEventDispatcher implements CoreInputEventDispatcher<CoreKeyEvent, CoreKeyListener> {

  private static final Logger log = LoggerFactory.getLogger(CoreKeyEventDispatcher.class);

  private final Set<CoreKeyListener> listeners = Collections.synchronizedSet(new HashSet<CoreKeyListener>());

  @Override
  public void dispatch(final CoreKeyEvent event) {
    for (final CoreKeyListener listener : listeners) {
      if (event.isType(event.EVENT_KEY_PRESSED())) listener.keyPressed(event);
      else if (event.isType(event.EVENT_KEY_RELEASED())) listener.keyReleased(event);
      else {
        log.warn("Ignoring unrecognized event: {}", event);
        break;
      }
    }
  }

  @Override
  public void register(final CoreKeyListener listener) {
    listeners.add(listener);
  }

  @Override
  public void unregister(final CoreKeyListener listener) {
    listeners.remove(listener);
  }

  @Override
  public Class<CoreKeyEvent> eventType() {
    return CoreKeyEvent.class;
  }

  @Override
  public Class<CoreKeyListener> listenerType() {
    return CoreKeyListener.class;
  }
}
