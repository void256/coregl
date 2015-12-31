package de.lessvoid.coregl.jogl.input;

import com.jogamp.newt.event.MouseEvent;

import de.lessvoid.coregl.input.spi.CoreMouseEvent;

/**
 * @author Brian Groenke
 */
public final class CoreMouseEventJogl implements CoreMouseEvent {

  @Override
  public short BUTTON_COUNT() {
    return MouseEvent.BUTTON_COUNT;
  }

  @Override
  public short BUTTON1() {
    return MouseEvent.BUTTON1;
  }

  @Override
  public short BUTTON2() {
    return MouseEvent.BUTTON2;
  }

  @Override
  public short BUTTON3() {
    return MouseEvent.BUTTON3;
  }

  @Override
  public short BUTTON4() {
    return MouseEvent.BUTTON4;
  }

  @Override
  public short BUTTON5() {
    return MouseEvent.BUTTON5;
  }

  @Override
  public short BUTTON6() {
    return MouseEvent.BUTTON6;
  }

  @Override
  public short BUTTON7() {
    return MouseEvent.BUTTON7;
  }

  @Override
  public short BUTTON8() {
    return MouseEvent.BUTTON8;
  }

  @Override
  public short BUTTON9() {
    return MouseEvent.BUTTON9;
  }

  @Override
  public short EVENT_MOUSE_CLICKED() {
    return MouseEvent.EVENT_MOUSE_CLICKED;
  }

  @Override
  public short EVENT_MOUSE_DRAGGED() {
    return MouseEvent.EVENT_MOUSE_DRAGGED;
  }

  @Override
  public short EVENT_MOUSE_ENTERED() {
    return MouseEvent.EVENT_MOUSE_ENTERED;
  }

  @Override
  public short EVENT_MOUSE_EXITED() {
    return MouseEvent.EVENT_MOUSE_EXITED;
  }

  @Override
  public short EVENT_MOUSE_MOVED() {
    return MouseEvent.EVENT_MOUSE_MOVED;
  }

  @Override
  public short EVENT_MOUSE_PRESSED() {
    return MouseEvent.EVENT_MOUSE_PRESSED;
  }

  @Override
  public short EVENT_MOUSE_RELEASED() {
    return MouseEvent.EVENT_MOUSE_RELEASED;
  }

  @Override
  public short EVENT_MOUSE_WHEEL_MOVED() {
    return MouseEvent.EVENT_MOUSE_WHEEL_MOVED;
  }

  MouseEvent evt;

  public CoreMouseEventJogl(final MouseEvent evt) {

    this.evt = evt;
  }

  @Override
  public short getButton() {

    return evt.getButton();
  }

  @Override
  public short getClickCount() {

    return evt.getClickCount();
  }

  @Override
  public float getWheelRotation() {

    return evt.getRotationScale();
  }

  @Override
  public int getX() {

    return evt.getX();
  }

  @Override
  public int getY() {

    return evt.getY();
  }

  @Override
  public int getButtonDownCount() {

    return evt.getButtonDownCount();
  }

  @Override
  public boolean isAltDown() {

    return evt.isAltDown();
  }

  @Override
  public boolean isAltGraphDown() {

    return evt.isAltGraphDown();
  }

  @Override
  public boolean isAnyButtonDown() {

    return evt.isAnyButtonDown();
  }

  @Override
  public boolean isAutoRepeat() {

    return evt.isAutoRepeat();
  }

  @Override
  public boolean isButtonDown(final int btn) {

    return evt.isButtonDown(btn);
  }

  @Override
  public boolean isConfined() {

    return evt.isConfined();
  }

  @Override
  public boolean isControlDown() {

    return evt.isControlDown();
  }

  @Override
  public boolean isMetaDown() {

    return evt.isMetaDown();
  }

  @Override
  public boolean isShiftDown() {

    return evt.isShiftDown();
  }

  @Override
  public boolean isConsumed() {

    return evt.isConsumed();
  }

  @Override
  public long getTimestamp() {

    return evt.getWhen();
  }

  @Override
  public int getEventType() {

    return evt.getEventType();
  }

  @Override
  public boolean isType(final int type) {
    return evt.getEventType() == type;
  }

  @Override
  public void setConsumed(final boolean consumed) {

    evt.setConsumed(consumed);
  }

  @Override
  public String toString() {

    return evt.toString();
  }
}
