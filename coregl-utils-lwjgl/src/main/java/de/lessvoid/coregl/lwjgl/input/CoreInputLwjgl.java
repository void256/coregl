package de.lessvoid.coregl.lwjgl.input;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lessvoid.coregl.input.PendingEventQueue;
import de.lessvoid.coregl.input.spi.AbstractCoreInput;
import de.lessvoid.coregl.input.spi.CoreMouseEvent;

public class CoreInputLwjgl extends AbstractCoreInput {

  private static final Logger log = LoggerFactory.getLogger(CoreInputLwjgl.class);

  private final LwjglInputProcessor eventProcessor = new LwjglInputProcessor();
  private final LwjglMouseEventDriver mouseEventDriver = LwjglMouseEventDriver.getInstance();

  private PendingEventQueue eventQueue;

  @Override
  public void initialize() {
    if (eventQueue != null) return;

    eventQueue = new PendingEventQueue(this);
    enableDefaultDispatchers();

    try {
      if (!Keyboard.isCreated()) Keyboard.create();
      mouseEventDriver.init();
    } catch (LWJGLException e) {
      log.error("Error initializing LWJGL input system: ", e);
      throw new RuntimeException(e);
    }

    log.info("{}: Initialized LWJGL input system", getClass().getSimpleName());
  }

  @Override
  public void update() {
    eventProcessor.update();
    eventQueue.flush();
  }

  @Override
  public void dispose() {
    Keyboard.destroy();
    mouseEventDriver.destroy();
    eventQueue = null;

    log.info("{}: Disposed LWJGL input system", getClass().getSimpleName());
  }

  private class LwjglInputProcessor {

    public void update() {
      Keyboard.poll();
      Mouse.poll();
      processEventQueue();
    }

    private void processEventQueue() {
      // process key events
      int count = 0;
      while (Keyboard.next()) {
        eventQueue.enqueue(CoreKeyEventLwjgl.createKeyEventFromCurrentState());
        count++;
      }
      log.trace("enqueued {} key events", count);

      // process mouse events
      count = 0;
      while (Mouse.next()) {
        for (final CoreMouseEvent evt : mouseEventDriver.update()) {
          eventQueue.enqueue(evt);
          count++;
        }
      }
      log.trace("enqueued {} mouse events", count);
    }
  }
}
