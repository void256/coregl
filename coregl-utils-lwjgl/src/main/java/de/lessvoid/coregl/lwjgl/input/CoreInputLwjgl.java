package de.lessvoid.coregl.lwjgl.input;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lessvoid.coregl.input.PollingEventQueue;
import de.lessvoid.coregl.input.spi.AbstractCoreInput;
import de.lessvoid.coregl.input.spi.CoreMouseEvent;

public class CoreInputLwjgl extends AbstractCoreInput {
  
  private static final Logger log = LoggerFactory.getLogger(CoreInputLwjgl.class);
  
  private final LwjglInputProcessor eventProcessor = new LwjglInputProcessor();
  private final LwjglMouseEventDriver mouseEventDriver = LwjglMouseEventDriver.getInstance();
  
  private PollingEventQueue eventQueue;

  @Override
  public void initialize() {
    if (eventQueue != null && eventQueue.isRunning()) return;
    
    eventQueue = new PollingEventQueue(this);
    enableDefaultDispatchers();
    try {
      if (!Keyboard.isCreated()) Keyboard.create();
      mouseEventDriver.init();
      eventProcessor.start();
    } catch (LWJGLException e) {
      log.error("Error initializing LWJGL input system: ", e);
      throw new RuntimeException(e);
    }
    
    log.info("{}: Initialized LWJGL input system", getClass().getSimpleName());
  }

  @Override
  public void dispose() {
    eventProcessor.stop();
    Keyboard.destroy();
    mouseEventDriver.destroy();
    
    log.info("{}: Disposed LWJGL input system", getClass().getSimpleName());
  }
  
  private class LwjglInputProcessor implements Runnable {
    
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final CyclicBarrier stopSync = new CyclicBarrier(2);

    @Override
    public void run() {
      Thread.currentThread().setName("input-processor-lwjgl");
      while (running.get()) {
        Keyboard.poll();
        Mouse.poll();
        log.trace("poll event buffer size: keyboard={}", Keyboard.getNumKeyboardEvents());
        processEventQueue();
        
        Thread.yield();
        try {
          Thread.sleep(1);
        } catch (InterruptedException e) {
          log.warn("Received interrupt in event processing loop: ", e);
        }
      }
      stopCheckpoint();
    }
    
    void start() {
      running.set(true);
      Executors.newSingleThreadExecutor().execute(this);
    }
    
    void stop() {
      running.set(false);
      stopCheckpoint();
      stopSync.reset();
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
      while (Mouse.next()) {
        for (final CoreMouseEvent evt : mouseEventDriver.update()) {
          eventQueue.enqueue(evt);
          count++;
        }
      }
      log.trace("enqueued {} mouse events", count);
    }
    
    private void stopCheckpoint() {
      try {
        stopSync.await();
      } catch (InterruptedException e) {
        log.warn("Error stopping event processor: ", e);
      } catch (BrokenBarrierException e) {
        log.warn("Error stopping event processor: ", e);
      }
    }
  }
}
