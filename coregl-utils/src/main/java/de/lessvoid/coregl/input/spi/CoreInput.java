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

  /**
   * Initializes this CoreInput by starting all event polling services and enabling the default
   * event dispatchers. You may disable the default dispatchers and register your own via
   * {@link #disableDefaultDispatchers()} and {@link #register(CoreInputEventDispatcher)}.
   */
  void initialize();
  
  /**
   * Registers the default event dispatchers, if they were previously disabled.
   */
  void enableDefaultDispatchers ();
  
  /**
   * Unregisters the default event dispatchers. Use this method if you want listeners to receive
   * events dispatched only by your own custom dispatchers.
   */
  void disableDefaultDispatchers ();

  void dispose();
}
