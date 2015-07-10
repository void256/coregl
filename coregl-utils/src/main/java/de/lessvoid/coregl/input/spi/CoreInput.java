package de.lessvoid.coregl.input.spi;

public interface CoreInput {

  void register(CoreInputEventDispatcher<? extends CoreInputEvent, ? extends CoreInputListener<?>> dispatcher);

  void unregister(CoreInputEventDispatcher<? extends CoreInputEvent, ? extends CoreInputListener<?>> dispatcher);

  /**
   * Adds the given input listener to all dispatchers that support it.
   */
  void addListener(CoreInputListener<? extends CoreInputEvent> listener);

  void removeListener(CoreInputListener<? extends CoreInputEvent> listener);

  void fireEvent(CoreInputEvent event);

  void initialize();

  void dispose();
}
