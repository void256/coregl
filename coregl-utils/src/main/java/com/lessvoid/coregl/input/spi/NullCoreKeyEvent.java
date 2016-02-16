package com.lessvoid.coregl.input.spi;

public abstract class NullCoreKeyEvent implements CoreKeyEvent {

  @Override
  public boolean isAutoRepeat() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isConsumed() {
    throw new UnsupportedOperationException();
  }

  @Override
  public long getTimestamp() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int getEventType() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isType(final int type) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setConsumed(final boolean consumed) {
    throw new UnsupportedOperationException();
  }

  @Override
  public short EVENT_KEY_PRESSED() {
    throw new UnsupportedOperationException();
  }

  @Override
  public short EVENT_KEY_RELEASED() {
    throw new UnsupportedOperationException();
  }

  @Override
  public char NULL_CHAR() {
    throw new UnsupportedOperationException();
  }

  @Override
  public short VK_0() {
    throw unsupported("VK_0");
  }

  @Override
  public short VK_1() {
    throw unsupported("VK_1");
  }

  @Override
  public short VK_2() {
    throw unsupported("VK_2");
  }

  @Override
  public short VK_3() {
    throw unsupported("VK_3");
  }

  @Override
  public short VK_4() {
    throw unsupported("VK_4");
  }

  @Override
  public short VK_5() {
    throw unsupported("VK_5");
  }

  @Override
  public short VK_6() {
    throw unsupported("VK_6");
  }

  @Override
  public short VK_7() {
    throw unsupported("VK_7");
  }

  @Override
  public short VK_8() {
    throw unsupported("VK_8");
  }

  @Override
  public short VK_9() {
    throw unsupported("VK_9");
  }

  @Override
  public short VK_A() {
    throw unsupported("VK_A");
  }

  @Override
  public short VK_ACCEPT() {
    throw unsupported("VK_ACCEPT");
  }

  @Override
  public short VK_ADD() {
    throw unsupported("VK_ADD");
  }

  @Override
  public short VK_AGAIN() {
    throw unsupported("VK_AGAIN");
  }

  @Override
  public short VK_ALL_CANDIDATES() {
    throw unsupported("VK_ALL_CANDIDATES");
  }

  @Override
  public short VK_ALPHANUMERIC() {
    throw unsupported("VK_ALPHANUMERIC");
  }

  @Override
  public short VK_ALT() {
    throw unsupported("VK_ALT");
  }

  @Override
  public short VK_RIGHT_ALT() {
    throw unsupported("VK_ALT_GRAPH");
  }

  @Override
  public short VK_AMPERSAND() {
    throw unsupported("VK_AMPERSAND");
  }

  @Override
  public short VK_ASTERISK() {
    throw unsupported("VK_ASTERISK");
  }

  @Override
  public short VK_AT() {
    throw unsupported("VK_AT");
  }

  @Override
  public short VK_B() {
    throw unsupported("VK_B");
  }

  @Override
  public short VK_BACK_QUOTE() {
    throw unsupported("VK_BACK_QUOTE");
  }

  @Override
  public short VK_BACK_SLASH() {
    throw unsupported("VK_BACK_SLASH");
  }

  @Override
  public short VK_BACK_SPACE() {
    throw unsupported("VK_BACK_SPACE");
  }

  @Override
  public short VK_BEGIN() {
    throw unsupported("VK_BEGIN");
  }

  @Override
  public short VK_C() {
    throw unsupported("VK_C");
  }

  @Override
  public short VK_CANCEL() {
    throw unsupported("VK_CANCEL");
  }

  @Override
  public short VK_CAPS_LOCK() {
    throw unsupported("VK_CAPS_LOCK");
  }

  @Override
  public short VK_CIRCUMFLEX() {
    throw unsupported("VK_CIRCUMFLEX");
  }

  @Override
  public short VK_CLEAR() {
    throw unsupported("VK_CLEAR");
  }

  @Override
  public short VK_CLOSE_BRACKET() {
    throw unsupported("VK_CLOSE_BRACKET");
  }

  @Override
  public short VK_CODE_INPUT() {
    throw unsupported("VK_CODE_INPUT");
  }

  @Override
  public short VK_COLON() {
    throw unsupported("VK_COLON");
  }

  @Override
  public short VK_COMMA() {
    throw unsupported("VK_COMMA");
  }

  @Override
  public short VK_COMPOSE() {
    throw unsupported("VK_COMPOSE");
  }

  @Override
  public short VK_CONTEXT_MENU() {
    throw unsupported("VK_CONTEXT_MENU");
  }

  @Override
  public short VK_CONTROL() {
    throw unsupported("VK_CONTROL");
  }

  @Override
  public short VK_CONVERT() {
    throw unsupported("VK_CONVERT");
  }

  @Override
  public short VK_COPY() {
    throw unsupported("VK_COPY");
  }

  @Override
  public short VK_CUT() {
    throw unsupported("VK_CUT");
  }

  @Override
  public short VK_D() {
    throw unsupported("VK_D");
  }

  @Override
  public short VK_DECIMAL() {
    throw unsupported("VK_DECIMAL");
  }

  @Override
  public short VK_DELETE() {
    throw unsupported("VK_DELETE");
  }

  @Override
  public short VK_DIVIDE() {
    throw unsupported("VK_DIVIDE");
  }

  @Override
  public short VK_DOLLAR() {
    throw unsupported("VK_DOLLAR");
  }

  @Override
  public short VK_DOWN() {
    throw unsupported("VK_DOWN");
  }

  @Override
  public short VK_E() {
    throw unsupported("VK_E");
  }

  @Override
  public short VK_END() {
    throw unsupported("VK_END");
  }

  @Override
  public short VK_ENTER() {
    throw unsupported("VK_ENTER");
  }

  @Override
  public short VK_EQUALS() {
    throw unsupported("VK_EQUALS");
  }

  @Override
  public short VK_ESCAPE() {
    throw unsupported("VK_ESCAPE");
  }

  @Override
  public short VK_EURO_SIGN() {
    throw unsupported("VK_EURO_SIGN");
  }

  @Override
  public short VK_EXCLAMATION_MARK() {
    throw unsupported("VK_EXCLAMATION_MARK");
  }

  @Override
  public short VK_F() {
    throw unsupported("VK_F");
  }

  @Override
  public short VK_F1() {
    throw unsupported("VK_F1");
  }

  @Override
  public short VK_F10() {
    throw unsupported("VK_F10");
  }

  @Override
  public short VK_F11() {
    throw unsupported("VK_F11");
  }

  @Override
  public short VK_F12() {
    throw unsupported("VK_F12");
  }

  @Override
  public short VK_F13() {
    throw unsupported("VK_F13");
  }

  @Override
  public short VK_F14() {
    throw unsupported("VK_F14");
  }

  @Override
  public short VK_F15() {
    throw unsupported("VK_F15");
  }

  @Override
  public short VK_F16() {
    throw unsupported("VK_F16");
  }

  @Override
  public short VK_F17() {
    throw unsupported("VK_F17");
  }

  @Override
  public short VK_F18() {
    throw unsupported("VK_F18");
  }

  @Override
  public short VK_F19() {
    throw unsupported("VK_F19");
  }

  @Override
  public short VK_F2() {
    throw unsupported("VK_F2");
  }

  @Override
  public short VK_F20() {
    throw unsupported("VK_F20");
  }

  @Override
  public short VK_F21() {
    throw unsupported("VK_F21");
  }

  @Override
  public short VK_F22() {
    throw unsupported("VK_F22");
  }

  @Override
  public short VK_F23() {
    throw unsupported("VK_F23");
  }

  @Override
  public short VK_F24() {
    throw unsupported("VK_F24");
  }

  @Override
  public short VK_F3() {
    throw unsupported("VK_F3");
  }

  @Override
  public short VK_F4() {
    throw unsupported("VK_F4");
  }

  @Override
  public short VK_F5() {
    throw unsupported("VK_F5");
  }

  @Override
  public short VK_F6() {
    throw unsupported("VK_F6");
  }

  @Override
  public short VK_F7() {
    throw unsupported("VK_F7");
  }

  @Override
  public short VK_F8() {
    throw unsupported("VK_F8");
  }

  @Override
  public short VK_F9() {
    throw unsupported("VK_F9");
  }

  @Override
  public short VK_FINAL() {
    throw unsupported("VK_FINAL");
  }

  @Override
  public short VK_FIND() {
    throw unsupported("VK_FIND");
  }

  @Override
  public short VK_FULL_WIDTH() {
    throw unsupported("VK_FULL_WIDTH");
  }

  @Override
  public short VK_G() {
    throw unsupported("VK_G");
  }

  @Override
  public short VK_GREATER() {
    throw unsupported("VK_GREATER");
  }

  @Override
  public short VK_H() {
    throw unsupported("VK_H");
  }

  @Override
  public short VK_HALF_WIDTH() {
    throw unsupported("VK_HALF_WIDTH");
  }

  @Override
  public short VK_HELP() {
    throw unsupported("VK_HELP");
  }

  @Override
  public short VK_HIRAGANA() {
    throw unsupported("VK_HIRAGANA");
  }

  @Override
  public short VK_HOME() {
    throw unsupported("VK_HOME");
  }

  @Override
  public short VK_I() {
    throw unsupported("VK_I");
  }

  @Override
  public short VK_INPUT_METHOD_ON_OFF() {
    throw unsupported("VK_INPUT_METHOD_ON_OFF");
  }

  @Override
  public short VK_INSERT() {
    throw unsupported("VK_INSERT");
  }

  @Override
  public short VK_INVERTED_EXCLAMATION_MARK() {
    throw unsupported("VK_INVERTED_EXCLAMATION_MARK");
  }

  @Override
  public short VK_J() {
    throw unsupported("VK_J");
  }

  @Override
  public short VK_JAPANESE_HIRAGANA() {
    throw unsupported("VK_JAPANESE_HIRAGANA");
  }

  @Override
  public short VK_JAPANESE_KATAKANA() {
    throw unsupported("VK_JAPANESE_KATAKANA");
  }

  @Override
  public short VK_JAPANESE_ROMAN() {
    throw unsupported("VK_JAPANESE_ROMAN");
  }

  @Override
  public short VK_K() {
    throw unsupported("VK_K");
  }

  @Override
  public short VK_KANA_LOCK() {
    throw unsupported("VK_KANA_LOCK");
  }

  @Override
  public short VK_KATAKANA() {
    throw unsupported("VK_KATAKANA");
  }

  @Override
  public short VK_KEYBOARD_INVISIBLE() {
    throw unsupported("VK_KEYBOARD_INVISIBLE");
  }

  @Override
  public short VK_L() {
    throw unsupported("VK_L");
  }

  @Override
  public short VK_LEFT() {
    throw unsupported("VK_LEFT");
  }

  @Override
  public short VK_LEFT_BRACE() {
    throw unsupported("VK_LEFT_BRACE");
  }

  @Override
  public short VK_LEFT_PARENTHESIS() {
    throw unsupported("VK_LEFT_PARENTHESIS");
  }

  @Override
  public short VK_LESS() {
    throw unsupported("VK_LESS");
  }

  @Override
  public short VK_M() {
    throw unsupported("VK_M");
  }

  @Override
  public short VK_META() {
    throw unsupported("VK_META");
  }

  @Override
  public short VK_MINUS() {
    throw unsupported("VK_MINUS");
  }

  @Override
  public short VK_MODECHANGE() {
    throw unsupported("VK_MODECHANGE");
  }

  @Override
  public short VK_MULTIPLY() {
    throw unsupported("VK_MULTIPLY");
  }

  @Override
  public short VK_N() {
    throw unsupported("VK_N");
  }

  @Override
  public short VK_NONCONVERT() {
    throw unsupported("VK_NONCONVERT");
  }

  @Override
  public short VK_NUM_LOCK() {
    throw unsupported("VK_NUM_LOCK");
  }

  @Override
  public short VK_NUMBER_SIGN() {
    throw unsupported("VK_NUMBER_SIGN");
  }

  @Override
  public short VK_NUMPAD0() {
    throw unsupported("VK_NUMPAD0");
  }

  @Override
  public short VK_NUMPAD1() {
    throw unsupported("VK_NUMPAD1");
  }

  @Override
  public short VK_NUMPAD2() {
    throw unsupported("VK_NUMPAD2");
  }

  @Override
  public short VK_NUMPAD3() {
    throw unsupported("VK_NUMPAD3");
  }

  @Override
  public short VK_NUMPAD4() {
    throw unsupported("VK_NUMPAD4");
  }

  @Override
  public short VK_NUMPAD5() {
    throw unsupported("VK_NUMPAD5");
  }

  @Override
  public short VK_NUMPAD6() {
    throw unsupported("VK_NUMPAD6");
  }

  @Override
  public short VK_NUMPAD7() {
    throw unsupported("VK_NUMPAD7");
  }

  @Override
  public short VK_NUMPAD8() {
    throw unsupported("VK_NUMPAD8");
  }

  @Override
  public short VK_NUMPAD9() {
    throw unsupported("VK_NUMPAD9");
  }

  @Override
  public short VK_O() {
    throw unsupported("VK_O");
  }

  @Override
  public short VK_OPEN_BRACKET() {
    throw unsupported("VK_OPEN_BRACKET");
  }

  @Override
  public short VK_P() {
    throw unsupported("VK_P");
  }

  @Override
  public short VK_PAGE_DOWN() {
    throw unsupported("VK_PAGE_DOWN");
  }

  @Override
  public short VK_PAGE_UP() {
    throw unsupported("VK_PAGE_UP");
  }

  @Override
  public short VK_PASTE() {
    throw unsupported("VK_PASTE");
  }

  @Override
  public short VK_PAUSE() {
    throw unsupported("VK_PAUSE");
  }

  @Override
  public short VK_PERCENT() {
    throw unsupported("VK_PERCENT");
  }

  @Override
  public short VK_PERIOD() {
    throw unsupported("VK_PERIOD");
  }

  @Override
  public short VK_PIPE() {
    throw unsupported("VK_PIPE");
  }

  @Override
  public short VK_PLUS() {
    throw unsupported("VK_PLUS");
  }

  @Override
  public short VK_PREVIOUS_CANDIDATE() {
    throw unsupported("VK_PREVIOUS_CANDIDATE");
  }

  @Override
  public short VK_PRINTSCREEN() {
    throw unsupported("VK_PRINTSCREEN");
  }

  @Override
  public short VK_PROPS() {
    throw unsupported("VK_PROPS");
  }

  @Override
  public short VK_Q() {
    throw unsupported("VK_Q");
  }

  @Override
  public short VK_QUESTIONMARK() {
    throw unsupported("VK_QUESTIONMARK");
  }

  @Override
  public short VK_QUOTE() {
    throw unsupported("VK_QUOTE");
  }

  @Override
  public short VK_QUOTEDBL() {
    throw unsupported("VK_QUOTEDBL");
  }

  @Override
  public short VK_R() {
    throw unsupported("VK_R");
  }

  @Override
  public short VK_RIGHT() {
    throw unsupported("VK_RIGHT");
  }

  @Override
  public short VK_RIGHT_BRACE() {
    throw unsupported("VK_RIGHT_BRACE");
  }

  @Override
  public short VK_RIGHT_PARENTHESIS() {
    throw unsupported("VK_RIGHT_PARENTHESIS");
  }

  @Override
  public short VK_ROMAN_CHARACTERS() {
    throw unsupported("VK_ROMAN_CHARACTERS");
  }

  @Override
  public short VK_S() {
    throw unsupported("VK_S");
  }

  @Override
  public short VK_SCROLL_LOCK() {
    throw unsupported("VK_SCROLL_LOCK");
  }

  @Override
  public short VK_SEMICOLON() {
    throw unsupported("VK_SEMICOLON");
  }

  @Override
  public short VK_SEPARATOR() {
    throw unsupported("VK_SEPARATOR");
  }

  @Override
  public short VK_SHIFT() {
    throw unsupported("VK_SHIFT");
  }

  @Override
  public short VK_SLASH() {
    throw unsupported("VK_SLASH");
  }

  @Override
  public short VK_SPACE() {
    throw unsupported("VK_SPACE");
  }

  @Override
  public short VK_STOP() {
    throw unsupported("VK_STOP");
  }

  @Override
  public short VK_SUBTRACT() {
    throw unsupported("VK_SUBTRACT");
  }

  @Override
  public short VK_T() {
    throw unsupported("VK_T");
  }

  @Override
  public short VK_TAB() {
    throw unsupported("VK_TAB");
  }

  @Override
  public short VK_TILDE() {
    throw unsupported("VK_TILDE");
  }

  @Override
  public short VK_U() {
    throw unsupported("VK_U");
  }

  @Override
  public short VK_UNDEFINED() {
    throw unsupported("VK_UNDEFINED");
  }

  @Override
  public short VK_UNDERSCORE() {
    throw unsupported("VK_UNDERSCORE");
  }

  @Override
  public short VK_UNDO() {
    throw unsupported("VK_UNDO");
  }

  @Override
  public short VK_UP() {
    throw unsupported("VK_UP");
  }

  @Override
  public short VK_V() {
    throw unsupported("VK_V");
  }

  @Override
  public short VK_W() {
    throw unsupported("VK_W");
  }

  @Override
  public short VK_WINDOWS() {
    throw unsupported("VK_WINDOWS");
  }

  @Override
  public short VK_X() {
    throw unsupported("VK_X");
  }

  @Override
  public short VK_Y() {
    throw unsupported("VK_Y");
  }

  @Override
  public short VK_Z() {
    throw unsupported("VK_Z");
  }

  @Override
  public char getKeyChar() {
    throw new UnsupportedOperationException();
  }

  @Override
  public short getKeyCode() {
    throw new UnsupportedOperationException();
  }

  @Override
  public short getKeySymbol() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isModifierKey() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isAltDown() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isRightAltDown() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isConfined() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isControlDown() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isMetaDown() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isShiftDown() {
    throw new UnsupportedOperationException();
  }

  protected UnsupportedOperationException unsupported(final String codeName) {
    return new UnsupportedOperationException(codeName + " not supported by " + getClass().getSimpleName());
  }
}
