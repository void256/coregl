package de.lessvoid.coregl.lwjgl.input;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import de.lessvoid.coregl.input.spi.CoreMouseEvent;

public final class CoreMouseEventLwjgl implements CoreMouseEvent {

  public static final short EVENT_MOUSE_CLICKED = 0xE00, EVENT_MOUSE_MOVED = 0xE10, EVENT_MOUSE_DRAGGED = 0xE20,
      EVENT_MOUSE_ENTERED = 0xE30, EVENT_MOUSE_EXITED = 0xE40, EVENT_MOUSE_PRESSED = 0xE50,
      EVENT_MOUSE_RELEASED = 0xE60, EVENT_MOUSE_WHEEL_MOVED = 0xE70;

  private final short eventType, btn;
  private final int mouseX, mouseY;
  private final float wheelRot;
  private final long timestamp;
  private final boolean isRepeated, isCtrlDown, isShiftDown, isMetaDown, isAltDown, isRightAltDown;

  private boolean isConsumed;

  public CoreMouseEventLwjgl(final short eventType, final short btn, final long nanoTime, final int mouseX,
      final int mouseY, final float wheelRot, final boolean isRepeated, final boolean isCtrlDown,
      final boolean isShiftDown, final boolean isMetaDown, final boolean isAltDown, final boolean isRightAltDown) {
    this.eventType = eventType;
    this.btn = btn;
    this.mouseX = mouseX;
    this.mouseY = mouseY;
    this.wheelRot = wheelRot;
    this.isRepeated = isRepeated;
    this.isCtrlDown = isCtrlDown;
    this.isShiftDown = isShiftDown;
    this.isMetaDown = isMetaDown;
    this.isAltDown = isAltDown;
    this.isRightAltDown = isRightAltDown;

    timestamp = nanoTime / 1000000L;
  }

  /**
   * Creates a new CoreMouseEvent using the data collected from the last call to
   * <code>Mouse.poll()</code>. It is the responsibility of the caller to call
   * <code>Mouse.next()</code> to advance to the next buffered event. <br>
   * <br>
   * Mouse event type resolution requires information about the previous state
   * of the mouse that is outside of the scope of this class. It may sometimes
   * even be necessary to create more than one event from the data polled via
   * the LWJGL Mouse class. It is the responsibility of the caller to determine
   * the appropriate type for the event being created; this method will then
   * pull the rest of the static event data from the Mouse's current event
   * state.
   *
   * @param mouseEventType
   *          the type of the mouse event, as determined by the caller
   * @return the CoreMouseEventLwjgl representing this event
   * @throws IllegalStateException
   *           if the Keyboard has not yet been initialized via
   *           <code>Keyboard.create()</code>.
   */
  public static CoreMouseEventLwjgl createMouseEventFromCurrentState(final short mouseEventType) {
    if (!Keyboard.isCreated()) throw new IllegalStateException("Keyboard has not yet been initialized.");
    if (!Mouse.isCreated()) throw new IllegalStateException("Mouse has not yet been initialized.");

    final boolean isAltDown = Keyboard.isKeyDown(Keyboard.KEY_LMENU),
        isRightAltDown = Keyboard.isKeyDown(Keyboard.KEY_RMENU);
    final boolean isCtrlDown = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
    final boolean isShiftDown = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    final boolean isMetaDown = Keyboard.isKeyDown(Keyboard.KEY_LMETA) || Keyboard.isKeyDown(Keyboard.KEY_RMETA);
    return new CoreMouseEventLwjgl(mouseEventType,
                                   (short) Mouse.getEventButton(),
                                   Keyboard.getEventNanoseconds(),
                                   Mouse.getEventX(),
                                   Mouse.getEventY(),
                                   Mouse.getEventDWheel(),
                                   Keyboard.isRepeatEvent(),
                                   isCtrlDown,
                                   isShiftDown,
                                   isMetaDown,
                                   isAltDown,
                                   isRightAltDown);
  }

  @Override
  public short BUTTON_COUNT() {
    return (short) Mouse.getButtonCount();
  }

  @Override
  public short BUTTON1() {
    return 0;
  }

  @Override
  public short BUTTON2() {
    return 1;
  }

  @Override
  public short BUTTON3() {
    return 2;
  }

  @Override
  public short BUTTON4() {
    return 3;
  }

  @Override
  public short BUTTON5() {
    return 4;
  }

  @Override
  public short BUTTON6() {
    return 5;
  }

  @Override
  public short BUTTON7() {
    return 6;
  }

  @Override
  public short BUTTON8() {
    return 7;
  }

  @Override
  public short BUTTON9() {
    return 8;
  }

  @Override
  public short EVENT_MOUSE_CLICKED() {
    return EVENT_MOUSE_CLICKED;
  }

  @Override
  public short EVENT_MOUSE_DRAGGED() {
    return EVENT_MOUSE_DRAGGED;
  }

  @Override
  public short EVENT_MOUSE_ENTERED() {
    return EVENT_MOUSE_ENTERED;
  }

  @Override
  public short EVENT_MOUSE_EXITED() {
    return EVENT_MOUSE_EXITED;
  }

  @Override
  public short EVENT_MOUSE_MOVED() {
    return EVENT_MOUSE_MOVED;
  }

  @Override
  public short EVENT_MOUSE_PRESSED() {
    return EVENT_MOUSE_PRESSED;
  }

  @Override
  public short EVENT_MOUSE_RELEASED() {
    return EVENT_MOUSE_RELEASED;
  }

  @Override
  public short EVENT_MOUSE_WHEEL_MOVED() {
    return EVENT_MOUSE_WHEEL_MOVED;
  }

  @Override
  public boolean isAutoRepeat() {
    return isRepeated;
  }

  @Override
  public boolean isConsumed() {
    return isConsumed;
  }

  @Override
  public long getTimestamp() {
    return timestamp;
  }

  @Override
  public int getEventType() {
    return eventType;
  }

  @Override
  public boolean isType(final int type) {
    return eventType == type;
  }

  @Override
  public void setConsumed(final boolean isConsumed) {
    this.isConsumed = isConsumed;
  }

  @Override
  public short getButton() {
    return btn;
  }

  /**
   * Always returns 1 for LWJGL implementation. LWJGL then presumably registers
   * two separate events in the queue for double/triple clicks.
   */
  @Override
  public short getClickCount() {
    return 1;
  }

  @Override
  public float getWheelRotation() {
    return wheelRot;
  }

  @Override
  public int getX() {
    return mouseX;
  }

  @Override
  public int getY() {
    return mouseY;
  }

  /**
   * Always returns -1 for LWJGL implementation.
   */
  @Override
  public int getButtonDownCount() {
    return -1;
  }

  @Override
  public boolean isAltDown() {
    return isAltDown;
  }

  @Override
  public boolean isAltGraphDown() {
    return isRightAltDown;
  }

  @Override
  public boolean isAnyButtonDown() {
    return eventType == EVENT_MOUSE_PRESSED || eventType == EVENT_MOUSE_DRAGGED;
  }

  @Override
  public boolean isButtonDown(final int btn) {
    return this.btn == btn && (eventType == EVENT_MOUSE_PRESSED || eventType == EVENT_MOUSE_DRAGGED);
  }

  /**
   * Always returns false for LWJGL implementation. LWJGL does not provide any
   * definition for "confined" events.
   */
  @Override
  public boolean isConfined() {
    return false;
  }

  @Override
  public boolean isControlDown() {
    return isCtrlDown;
  }

  @Override
  public boolean isMetaDown() {
    return isMetaDown;
  }

  @Override
  public boolean isShiftDown() {
    return isShiftDown;
  }
}
