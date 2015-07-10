package de.lessvoid.coregl.input.spi;

public interface CoreInputEventDispatcher<E extends CoreInputEvent, L extends CoreInputListener<E>> {

  void dispatch(E event);

  void register(L listener);

  void unregister(L listener);

  Class<E> eventType();

  Class<L> listenerType();
}
