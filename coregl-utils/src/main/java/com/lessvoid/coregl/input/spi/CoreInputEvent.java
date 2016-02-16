package com.lessvoid.coregl.input.spi;

public interface CoreInputEvent {

  boolean isAutoRepeat();

  boolean isConsumed();

  long getTimestamp();

  int getEventType();

  boolean isType(int type);

  void setConsumed(final boolean consumed);
}
