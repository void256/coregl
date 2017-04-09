package com.lessvoid.coregl.input;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.lessvoid.coregl.input.spi.CoreInput;
import com.lessvoid.coregl.input.spi.CoreInputEvent;

public class PendingEventQueue {

  private final Queue<CoreInputEvent> eventQueue = new ConcurrentLinkedQueue<CoreInputEvent>();
  private final CoreInput input;

  public PendingEventQueue(final CoreInput input) {
    this.input = input;
  }

  public void flush() {
    while (eventQueue.size() > 0) {
      input.fireEvent(eventQueue.poll());
    }
  }

  public void enqueue(final CoreInputEvent event) {
    eventQueue.offer(event);
  }
}
