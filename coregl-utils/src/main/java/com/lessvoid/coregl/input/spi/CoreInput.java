package com.lessvoid.coregl.input.spi;

public interface CoreInput {

  void register(CoreInputEventDispatcher<? extends CoreInputEvent, ? extends CoreInputListener<?>> dispatcher);

  void unregister(CoreInputEventDispatcher<? extends CoreInputEvent, ? extends CoreInputListener<?>> dispatcher);

  /**
   * Adds the given input listener to all dispatchers that support it.
   */
  void addListener(CoreInputListener<? extends CoreInputEvent> listener);

  void removeListener(CoreInputListener<? extends CoreInputEvent> listener);

  void fireEvent(CoreInputEvent event);

  /**
   * Initializes this CoreInput system and registers the default event
   * dispatchers. You may disable the default dispatchers and register your own
   * via {@link #disableDefaultDispatchers()} and
   * {@link #register(CoreInputEventDispatcher)}.
   */
  void initialize();

  /**
   * Polls the underlying event system and pushes all pending input events to
   * the registered dispatchers.
   */
  void update();

  /**
   * Registers the default event dispatchers, if they were previously disabled.
   */
  void enableDefaultDispatchers();

  /**
   * Unregisters the default event dispatchers. Use this method if you want
   * listeners to receive events dispatched only by your own custom dispatchers.
   */
  void disableDefaultDispatchers();

  void dispose();
}
