package com.lessvoid.coregl.input.spi;

/**
 * @author Brian Groenke
 */
public interface CoreMouseEvent extends CoreInputEvent {

  short BUTTON_COUNT();

  short BUTTON1();

  short BUTTON2();

  short BUTTON3();

  short BUTTON4();

  short BUTTON5();

  short BUTTON6();

  short BUTTON7();

  short BUTTON8();

  short BUTTON9();

  short EVENT_MOUSE_CLICKED();

  short EVENT_MOUSE_DRAGGED();

  short EVENT_MOUSE_ENTERED();

  short EVENT_MOUSE_EXITED();

  short EVENT_MOUSE_MOVED();

  short EVENT_MOUSE_PRESSED();

  short EVENT_MOUSE_RELEASED();

  short EVENT_MOUSE_WHEEL_MOVED();

  short getButton();

  short getClickCount();

  float getWheelRotation();

  int getX();

  int getY();

  int getButtonDownCount();

  boolean isAltDown();

  boolean isAltGraphDown();

  boolean isAnyButtonDown();

  boolean isButtonDown(final int btn);

  boolean isConfined();

  boolean isControlDown();

  boolean isMetaDown();

  boolean isShiftDown();
}
