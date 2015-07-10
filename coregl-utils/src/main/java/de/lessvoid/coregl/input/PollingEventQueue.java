package de.lessvoid.coregl.input;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import de.lessvoid.coregl.input.spi.CoreInput;
import de.lessvoid.coregl.input.spi.CoreInputEvent;

public class PollingEventQueue {
  private final ConcurrentLinkedQueue<CoreInputEvent> eventQueue = new ConcurrentLinkedQueue<CoreInputEvent>();
  private final AtomicBoolean running = new AtomicBoolean(true);
  private final CoreInput input;

  public PollingEventQueue(final CoreInput input) {
    this.input = input;
  }

  void flushQueue() {
    while (eventQueue.size() > 0) {
      input.fireEvent(eventQueue.poll());
    }
  }

  public void enqueue(final CoreInputEvent event) {
    eventQueue.offer(event);
  }

  public void start() {
    final Thread queueThread = new Thread(new EventPoll());
    queueThread.setDaemon(true);
    queueThread.start();
  }

  public void destroy() {
    eventQueue.clear();
    running.set(false);
  }

  public boolean isRunning() {
    return running.get();
  }

  private class EventPoll implements Runnable {
    public void run() {
      try {
        while (running.get()) {
          if (eventQueue.size() > 0) flushQueue();
          Thread.sleep(1);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
