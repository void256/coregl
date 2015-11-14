package de.lessvoid.coregl.lwjgl.input;

import org.lwjgl.input.Keyboard;

import de.lessvoid.coregl.input.spi.NullCoreKeyEvent;

public final class CoreKeyEventLwjgl extends NullCoreKeyEvent {

  public static final short EVENT_KEY_PRESSED = 0xF00, EVENT_KEY_RELEASED = 0xF10;

  private final short eventType, key;
  private final char keyChar;
  private final long timestamp;
  private final boolean isRepeated, isCtrlDown, isShiftDown, isMetaDown, isAltDown, isRightAltDown;

  private boolean isConsumed;

  public CoreKeyEventLwjgl(final short eventType, final short key, final char keyChar, final long nanoTime,
      final boolean isRepeated, final boolean isCtrlDown, final boolean isShiftDown, final boolean isMetaDown,
      final boolean isAltDown, final boolean isRightAltDown) {

    this.eventType = eventType;
    this.key = key;
    this.keyChar = keyChar;
    this.isRepeated = isRepeated;
    this.isCtrlDown = isCtrlDown;
    this.isShiftDown = isShiftDown;
    this.isMetaDown = isMetaDown;
    this.isAltDown = isAltDown;
    this.isRightAltDown = isRightAltDown;

    timestamp = nanoTime / 1000000L;
  }

  /**
   * Creates a new CoreKeyEvent using the data collected from the last call to <code>Keyboard.poll()</code>. It is
   * the responsibility of the caller to call <code>Keyboard.next()</code> to advance to the next buffered event.
   * @return the CoreKeyEventLwjgl representing this event
   * @throws IllegalStateException if the Keyboard has not yet been initialized via <code>Keyboard.create()</code>.
   */
  public static CoreKeyEventLwjgl createKeyEventFromCurrentState() {
    if (!Keyboard.isCreated()) throw new IllegalStateException("Keyboard has not yet been initialized.");

    final short type = Keyboard.getEventKeyState() ? EVENT_KEY_PRESSED : EVENT_KEY_RELEASED;
    final boolean isAltDown = Keyboard.isKeyDown(Keyboard.KEY_LMENU),
        isRightAltDown = Keyboard.isKeyDown(Keyboard.KEY_RMENU);
    final boolean isCtrlDown = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
    final boolean isShiftDown = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    final boolean isMetaDown = Keyboard.isKeyDown(Keyboard.KEY_LMETA) || Keyboard.isKeyDown(Keyboard.KEY_RMETA);
    return new CoreKeyEventLwjgl(type,
                                 (short) Keyboard.getEventKey(),
                                 Keyboard.getEventCharacter(),
                                 Keyboard.getEventNanoseconds(),
                                 Keyboard.isRepeatEvent(),
                                 isCtrlDown,
                                 isShiftDown,
                                 isMetaDown,
                                 isAltDown,
                                 isRightAltDown);
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
  public boolean isType(int type) {
    return eventType == type;
  }

  @Override
  public void setConsumed(boolean consumed) {
    this.isConsumed = consumed;
  }

  @Override
  public char getKeyChar() {
    return keyChar;
  }

  @Override
  public short getKeyCode() {
    return key;
  }

  @Override
  public short getKeySymbol() {
    return key;
  }

  @Override
  public boolean isModifierKey() {
    return key == VK_SHIFT() || key == VK_CONTROL() || key == VK_META() || key == VK_WINDOWS() || key == VK_ALT();
  }

  @Override
  public boolean isAltDown() {
    return isAltDown;
  }

  @Override
  public boolean isRightAltDown() {
    return isRightAltDown;
  }

  /**
   * LWJGL does not provide any definition for "confined" keys. As such, this
   * method will always return false for this implementation.
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

  @Override
  public short EVENT_KEY_PRESSED() {
    return EVENT_KEY_PRESSED;
  }

  @Override
  public short EVENT_KEY_RELEASED() {
    return EVENT_KEY_RELEASED;
  }

  @Override
  public char NULL_CHAR() {
    return Keyboard.CHAR_NONE;
  }

  @Override
  public short VK_0() {
    return Keyboard.KEY_0;
  }

  @Override
  public short VK_1() {
    return Keyboard.KEY_1;
  }

  @Override
  public short VK_2() {
    return Keyboard.KEY_2;
  }

  @Override
  public short VK_3() {
    return Keyboard.KEY_3;
  }

  @Override
  public short VK_4() {
    return Keyboard.KEY_4;
  }

  @Override
  public short VK_5() {
    return Keyboard.KEY_5;
  }

  @Override
  public short VK_6() {
    return Keyboard.KEY_6;
  }

  @Override
  public short VK_7() {
    return Keyboard.KEY_7;
  }

  @Override
  public short VK_8() {
    return Keyboard.KEY_8;
  }

  @Override
  public short VK_9() {
    return Keyboard.KEY_9;
  }

  @Override
  public short VK_A() {
    return Keyboard.KEY_A;
  }

  @Override
  public short VK_ACCEPT() {
    return Keyboard.KEY_NEXT;
  }

  @Override
  public short VK_ADD() {
    return Keyboard.KEY_ADD;
  }

  @Override
  public short VK_ALT() {
    return Keyboard.KEY_LMENU;
  }

  @Override
  public short VK_AT() {
    return Keyboard.KEY_AT;
  }

  @Override
  public short VK_B() {
    return Keyboard.KEY_B;
  }

  @Override
  public short VK_BACK_SLASH() {
    return Keyboard.KEY_BACKSLASH;
  }

  @Override
  public short VK_BACK_SPACE() {
    return Keyboard.KEY_BACK;
  }

  @Override
  public short VK_C() {
    return Keyboard.KEY_C;
  }

  @Override
  public short VK_CAPS_LOCK() {
    return Keyboard.KEY_CAPITAL;
  }

  @Override
  public short VK_CIRCUMFLEX() {
    return Keyboard.KEY_CIRCUMFLEX;
  }

  @Override
  public short VK_CLEAR() {
    return Keyboard.KEY_CLEAR;
  }

  @Override
  public short VK_CLOSE_BRACKET() {
    return Keyboard.KEY_RBRACKET;
  }

  @Override
  public short VK_COLON() {
    return Keyboard.KEY_COLON;
  }

  @Override
  public short VK_COMMA() {
    return Keyboard.KEY_COMMA;
  }

  @Override
  public short VK_CONTROL() {
    return Keyboard.KEY_LCONTROL;
  }

  @Override
  public short VK_CONVERT() {
    return Keyboard.KEY_CONVERT;
  }

  @Override
  public short VK_D() {
    return Keyboard.KEY_D;
  }

  @Override
  public short VK_DECIMAL() {
    return Keyboard.KEY_DECIMAL;
  }

  @Override
  public short VK_DELETE() {
    return Keyboard.KEY_DELETE;
  }

  @Override
  public short VK_DIVIDE() {
    return Keyboard.KEY_DIVIDE;
  }

  @Override
  public short VK_DOWN() {
    return Keyboard.KEY_DOWN;
  }

  @Override
  public short VK_E() {
    return Keyboard.KEY_E;
  }

  @Override
  public short VK_END() {
    return Keyboard.KEY_END;
  }

  @Override
  public short VK_ENTER() {
    return Keyboard.KEY_RETURN;
  }

  @Override
  public short VK_EQUALS() {
    return Keyboard.KEY_EQUALS;
  }

  @Override
  public short VK_ESCAPE() {
    return Keyboard.KEY_ESCAPE;
  }

  @Override
  public short VK_F() {
    return Keyboard.KEY_F;
  }

  @Override
  public short VK_F1() {
    return Keyboard.KEY_F1;
  }

  @Override
  public short VK_F10() {
    return Keyboard.KEY_F10;
  }

  @Override
  public short VK_F11() {
    return Keyboard.KEY_F11;
  }

  @Override
  public short VK_F12() {
    return Keyboard.KEY_F12;
  }

  @Override
  public short VK_F13() {
    return Keyboard.KEY_F13;
  }

  @Override
  public short VK_F14() {
    return Keyboard.KEY_F14;
  }

  @Override
  public short VK_F15() {
    return Keyboard.KEY_F15;
  }

  @Override
  public short VK_F16() {
    return Keyboard.KEY_F16;
  }

  @Override
  public short VK_F17() {
    return Keyboard.KEY_F17;
  }

  @Override
  public short VK_F18() {
    return Keyboard.KEY_F18;
  }

  @Override
  public short VK_F19() {
    return Keyboard.KEY_F19;
  }

  @Override
  public short VK_F2() {
    return Keyboard.KEY_F2;
  }

  @Override
  public short VK_F3() {
    return Keyboard.KEY_F3;
  }

  @Override
  public short VK_F4() {
    return Keyboard.KEY_F4;
  }

  @Override
  public short VK_F5() {
    return Keyboard.KEY_F5;
  }

  @Override
  public short VK_F6() {
    return Keyboard.KEY_F6;
  }

  @Override
  public short VK_F7() {
    return Keyboard.KEY_F7;
  }

  @Override
  public short VK_F8() {
    return Keyboard.KEY_F8;
  }

  @Override
  public short VK_F9() {
    return Keyboard.KEY_F9;
  }

  @Override
  public short VK_G() {
    return Keyboard.KEY_G;
  }

  @Override
  public short VK_H() {
    return Keyboard.KEY_H;
  }

  @Override
  public short VK_HOME() {
    return Keyboard.KEY_HOME;
  }

  @Override
  public short VK_I() {
    return Keyboard.KEY_I;
  }

  @Override
  public short VK_INSERT() {
    return Keyboard.KEY_INSERT;
  }

  @Override
  public short VK_J() {
    return Keyboard.KEY_J;
  }

  @Override
  public short VK_K() {
    return Keyboard.KEY_K;
  }

  @Override
  public short VK_L() {
    return Keyboard.KEY_L;
  }

  @Override
  public short VK_LEFT() {
    return Keyboard.KEY_LEFT;
  }

  @Override
  public short VK_RIGHT_ALT() {
    return Keyboard.KEY_RMENU;
  }

  @Override
  public short VK_M() {
    return Keyboard.KEY_M;
  }

  @Override
  public short VK_META() {
    return Keyboard.KEY_LMETA;
  }

  @Override
  public short VK_MINUS() {
    return Keyboard.KEY_MINUS;
  }

  @Override
  public short VK_MULTIPLY() {
    return Keyboard.KEY_MULTIPLY;
  }

  @Override
  public short VK_N() {
    return Keyboard.KEY_N;
  }

  @Override
  public short VK_NUM_LOCK() {
    return Keyboard.KEY_NUMLOCK;
  }

  @Override
  public short VK_NUMPAD0() {
    return Keyboard.KEY_NUMPAD0;
  }

  @Override
  public short VK_NUMPAD1() {
    return Keyboard.KEY_NUMPAD1;
  }

  @Override
  public short VK_NUMPAD2() {
    return Keyboard.KEY_NUMPAD2;
  }

  @Override
  public short VK_NUMPAD3() {
    return Keyboard.KEY_NUMPAD3;
  }

  @Override
  public short VK_NUMPAD4() {
    return Keyboard.KEY_NUMPAD4;
  }

  @Override
  public short VK_NUMPAD5() {
    return Keyboard.KEY_NUMPAD5;
  }

  @Override
  public short VK_NUMPAD6() {
    return Keyboard.KEY_NUMPAD6;
  }

  @Override
  public short VK_NUMPAD7() {
    return Keyboard.KEY_NUMPAD7;
  }

  @Override
  public short VK_NUMPAD8() {
    return Keyboard.KEY_NUMPAD8;
  }

  @Override
  public short VK_NUMPAD9() {
    return Keyboard.KEY_NUMPAD9;
  }

  @Override
  public short VK_O() {
    return Keyboard.KEY_0;
  }

  @Override
  public short VK_OPEN_BRACKET() {
    return Keyboard.KEY_LBRACKET;
  }

  @Override
  public short VK_P() {
    return Keyboard.KEY_P;
  }

  @Override
  public short VK_PAUSE() {
    return Keyboard.KEY_PAUSE;
  }

  @Override
  public short VK_PERIOD() {
    return Keyboard.KEY_PERIOD;
  }

  @Override
  public short VK_Q() {
    return Keyboard.KEY_Q;
  }

  @Override
  public short VK_QUOTE() {
    return Keyboard.KEY_APOSTROPHE;
  }

  @Override
  public short VK_R() {
    return Keyboard.KEY_R;
  }

  @Override
  public short VK_RIGHT() {
    return Keyboard.KEY_RIGHT;
  }

  @Override
  public short VK_S() {
    return Keyboard.KEY_S;
  }

  @Override
  public short VK_SCROLL_LOCK() {
    return Keyboard.KEY_SCROLL;
  }

  @Override
  public short VK_SEMICOLON() {
    return Keyboard.KEY_SEMICOLON;
  }

  @Override
  public short VK_SHIFT() {
    return Keyboard.KEY_LSHIFT;
  }

  @Override
  public short VK_SLASH() {
    return Keyboard.KEY_SLASH;
  }

  @Override
  public short VK_SPACE() {
    return Keyboard.KEY_SPACE;
  }

  @Override
  public short VK_STOP() {
    return Keyboard.KEY_STOP;
  }

  @Override
  public short VK_SUBTRACT() {
    return Keyboard.KEY_SUBTRACT;
  }

  @Override
  public short VK_T() {
    return Keyboard.KEY_T;
  }

  @Override
  public short VK_TAB() {
    return Keyboard.KEY_TAB;
  }

  @Override
  public short VK_U() {
    return Keyboard.KEY_U;
  }

  @Override
  public short VK_UNDEFINED() {
    return Keyboard.KEY_UNLABELED;
  }

  @Override
  public short VK_UNDERSCORE() {
    return Keyboard.KEY_UNDERLINE;
  }

  @Override
  public short VK_UP() {
    return Keyboard.KEY_UP;
  }

  @Override
  public short VK_V() {
    return Keyboard.KEY_V;
  }

  @Override
  public short VK_W() {
    return Keyboard.KEY_W;
  }

  @Override
  public short VK_WINDOWS() {
    return Keyboard.KEY_RMETA;
  }

  @Override
  public short VK_X() {
    return Keyboard.KEY_X;
  }

  @Override
  public short VK_Y() {
    return Keyboard.KEY_Y;
  }

  @Override
  public short VK_Z() {
    return Keyboard.KEY_Z;
  }

  @Override
  public String toString() {
    final String type = (eventType == EVENT_KEY_PRESSED) ? "EVENT_KEY_PRESSED" : "EVENT_KEY_RELEASED";
    return String.format("%s: type: %s char: %c repeated: %b timestamp: %d", getClass().getSimpleName(), type, keyChar, isRepeated, timestamp);
  }
}
