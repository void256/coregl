package de.lessvoid.coregl.input.spi;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractCoreInput implements CoreInput {
  private final Set<CoreInputEventDispatcher<?, ?>> dispatchers = Collections
      .synchronizedSet(new HashSet<CoreInputEventDispatcher<?, ?>>());

  @Override
  public void register(final CoreInputEventDispatcher<?, ?> dispatcher) {
    dispatchers.add(dispatcher);
  }

  @Override
  public void unregister(final CoreInputEventDispatcher<?, ?> dispatcher) {
    dispatchers.add(dispatcher);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void addListener(final CoreInputListener<? extends CoreInputEvent> listener) {
    for (@SuppressWarnings("rawtypes") final CoreInputEventDispatcher dispatcher : dispatchers) {
      if (dispatcher.listenerType().isInstance(listener)) dispatcher.register(listener);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public void removeListener(final CoreInputListener<? extends CoreInputEvent> listener) {
    for (@SuppressWarnings("rawtypes") final CoreInputEventDispatcher dispatcher : dispatchers) {
      if (dispatcher.listenerType().isInstance(listener)) dispatcher.unregister(listener);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fireEvent(final CoreInputEvent inputEvent) {
    for (@SuppressWarnings("rawtypes") final CoreInputEventDispatcher dispatcher : dispatchers) {
      if (dispatcher.eventType().isInstance(inputEvent)) dispatcher.dispatch(inputEvent);
    }
  }
}
