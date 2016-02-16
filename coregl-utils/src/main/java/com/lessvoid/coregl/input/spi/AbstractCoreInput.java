package com.lessvoid.coregl.input.spi;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.lessvoid.coregl.input.dispatch.CoreKeyEventDispatcher;
import com.lessvoid.coregl.input.dispatch.CoreMouseEventDispatcher;

public abstract class AbstractCoreInput implements CoreInput {
  private final Set<CoreInputEventDispatcher<?, ?>> dispatchers = Collections
      .synchronizedSet(new HashSet<CoreInputEventDispatcher<?, ?>>());

  // default key/mouse dispatchers
  protected CoreKeyEventDispatcher defaultKeyDispatch;
  protected CoreMouseEventDispatcher defaultMouseDispatch;

  @Override
  public void register(final CoreInputEventDispatcher<? extends CoreInputEvent, ? extends CoreInputListener<?>> dispatcher) {
    dispatchers.add(dispatcher);
  }

  @Override
  public void unregister(final CoreInputEventDispatcher<? extends CoreInputEvent, ? extends CoreInputListener<?>> dispatcher) {
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

  @Override
  public void enableDefaultDispatchers() {
    if (defaultKeyDispatch == null) {
      defaultKeyDispatch = new CoreKeyEventDispatcher();
      register(defaultKeyDispatch);
    }

    if (defaultMouseDispatch == null) {
      defaultMouseDispatch = new CoreMouseEventDispatcher();
      register(defaultMouseDispatch);
    }
  }

  @Override
  public void disableDefaultDispatchers() {
    if (defaultKeyDispatch != null) {
      unregister(defaultKeyDispatch);
      defaultKeyDispatch = null;
    }

    if (defaultMouseDispatch != null) {
      unregister(defaultMouseDispatch);
      defaultMouseDispatch = null;
    }
  }
}
