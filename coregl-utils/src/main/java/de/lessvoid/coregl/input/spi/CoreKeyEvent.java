package de.lessvoid.coregl.input.spi;

/**
 * @author Brian Groenke
 */
public interface CoreKeyEvent extends CoreInputEvent {

  short EVENT_KEY_PRESSED();

  short EVENT_KEY_RELEASED();

  char NULL_CHAR();

  short VK_0();

  short VK_1();

  short VK_2();

  short VK_3();

  short VK_4();

  short VK_5();

  short VK_6();

  short VK_7();

  short VK_8();

  short VK_9();

  short VK_A();

  short VK_ACCEPT();

  short VK_ADD();

  short VK_AGAIN();

  short VK_ALL_CANDIDATES();

  short VK_ALPHANUMERIC();

  short VK_ALT();

  short VK_AMPERSAND();

  short VK_ASTERISK();

  short VK_AT();

  short VK_B();

  short VK_BACK_QUOTE();

  short VK_BACK_SLASH();

  short VK_BACK_SPACE();

  short VK_BEGIN();

  short VK_C();

  short VK_CANCEL();

  short VK_CAPS_LOCK();

  short VK_CIRCUMFLEX();

  short VK_CLEAR();

  short VK_CLOSE_BRACKET();

  short VK_CODE_INPUT();

  short VK_COLON();

  short VK_COMMA();

  short VK_COMPOSE();

  short VK_CONTEXT_MENU();

  short VK_CONTROL();

  short VK_CONVERT();

  short VK_COPY();

  short VK_CUT();

  short VK_D();

  short VK_DECIMAL();

  short VK_DELETE();

  short VK_DIVIDE();

  short VK_DOLLAR();

  short VK_DOWN();

  short VK_E();

  short VK_END();

  short VK_ENTER();

  short VK_EQUALS();

  short VK_ESCAPE();

  short VK_EURO_SIGN();

  short VK_EXCLAMATION_MARK();

  short VK_F();

  short VK_F1();

  short VK_F10();

  short VK_F11();

  short VK_F12();

  short VK_F13();

  short VK_F14();

  short VK_F15();

  short VK_F16();

  short VK_F17();

  short VK_F18();

  short VK_F19();

  short VK_F2();

  short VK_F20();

  short VK_F21();

  short VK_F22();

  short VK_F23();

  short VK_F24();

  short VK_F3();

  short VK_F4();

  short VK_F5();

  short VK_F6();

  short VK_F7();

  short VK_F8();

  short VK_F9();

  short VK_FINAL();

  short VK_FIND();

  short VK_FULL_WIDTH();

  short VK_G();

  short VK_GREATER();

  short VK_H();

  short VK_HALF_WIDTH();

  short VK_HELP();

  short VK_HIRAGANA();

  short VK_HOME();

  short VK_I();

  short VK_INPUT_METHOD_ON_OFF();

  short VK_INSERT();

  short VK_INVERTED_EXCLAMATION_MARK();

  short VK_J();

  short VK_JAPANESE_HIRAGANA();

  short VK_JAPANESE_KATAKANA();

  short VK_JAPANESE_ROMAN();

  short VK_K();

  short VK_KANA_LOCK();

  short VK_KATAKANA();

  short VK_KEYBOARD_INVISIBLE();

  short VK_L();

  short VK_LEFT();

  short VK_LEFT_BRACE();

  short VK_LEFT_PARENTHESIS();

  short VK_LESS();

  short VK_M();

  short VK_META();

  short VK_MINUS();

  short VK_MODECHANGE();

  short VK_MULTIPLY();

  short VK_N();

  short VK_NONCONVERT();

  short VK_NUM_LOCK();

  short VK_NUMBER_SIGN();

  short VK_NUMPAD0();

  short VK_NUMPAD1();

  short VK_NUMPAD2();

  short VK_NUMPAD3();

  short VK_NUMPAD4();

  short VK_NUMPAD5();

  short VK_NUMPAD6();

  short VK_NUMPAD7();

  short VK_NUMPAD8();

  short VK_NUMPAD9();

  short VK_O();

  short VK_OPEN_BRACKET();

  short VK_P();

  short VK_PAGE_DOWN();

  short VK_PAGE_UP();

  short VK_PASTE();

  short VK_PAUSE();

  short VK_PERCENT();

  short VK_PERIOD();

  short VK_PIPE();

  short VK_PLUS();

  short VK_PREVIOUS_CANDIDATE();

  short VK_PRINTSCREEN();

  short VK_PROPS();

  short VK_Q();

  short VK_QUESTIONMARK();

  short VK_QUOTE();

  short VK_QUOTEDBL();

  short VK_R();

  short VK_RIGHT();

  short VK_RIGHT_ALT();

  short VK_RIGHT_BRACE();

  short VK_RIGHT_PARENTHESIS();

  short VK_ROMAN_CHARACTERS();

  short VK_S();

  short VK_SCROLL_LOCK();

  short VK_SEMICOLON();

  short VK_SEPARATOR();

  short VK_SHIFT();

  short VK_SLASH();

  short VK_SPACE();

  short VK_STOP();

  short VK_SUBTRACT();

  short VK_T();

  short VK_TAB();

  short VK_TILDE();

  short VK_U();

  short VK_UNDEFINED();

  short VK_UNDERSCORE();

  short VK_UNDO();

  short VK_UP();

  short VK_V();

  short VK_W();

  short VK_WINDOWS();

  short VK_X();

  short VK_Y();

  short VK_Z();

  char getKeyChar();

  short getKeyCode();

  short getKeySymbol();

  boolean isModifierKey();

  boolean isAltDown();

  boolean isRightAltDown();

  boolean isConfined();

  boolean isControlDown();

  boolean isMetaDown();

  boolean isShiftDown();
}
