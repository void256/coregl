/*
 *  Copyright (C) 2011-2014 Brian Groenke
 *  All rights reserved.
 *
 *  This file is part of the 2DX Graphics Library.
 *
 *  This Source Code Form is subject to the terms of the
 *  Mozilla Public License, v. 2.0. If a copy of the MPL
 *  was not distributed with this file, You can obtain one at
 *  http://mozilla.org/MPL/2.0/.
 */

package com.lessvoid.coregl.jogl.input;

import com.jogamp.newt.event.KeyEvent;

import com.lessvoid.coregl.input.spi.CoreKeyEvent;

/**
 * @author Brian Groenke
 */
public final class CoreKeyEventJogl implements CoreKeyEvent {

  @Override
  public short EVENT_KEY_PRESSED() {
    return KeyEvent.EVENT_KEY_PRESSED;
  }

  @Override
  public short EVENT_KEY_RELEASED() {
    return KeyEvent.EVENT_KEY_RELEASED;
  }

  @Override
  public char NULL_CHAR() {
    return KeyEvent.NULL_CHAR;
  }

  @Override
  public short VK_0() {
    return KeyEvent.VK_0;
  }

  @Override
  public short VK_1() {
    return KeyEvent.VK_1;
  }

  @Override
  public short VK_2() {
    return KeyEvent.VK_2;
  }

  @Override
  public short VK_3() {
    return KeyEvent.VK_3;
  }

  @Override
  public short VK_4() {
    return KeyEvent.VK_4;
  }

  @Override
  public short VK_5() {
    return KeyEvent.VK_5;
  }

  @Override
  public short VK_6() {
    return KeyEvent.VK_6;
  }

  @Override
  public short VK_7() {
    return KeyEvent.VK_7;
  }

  @Override
  public short VK_8() {
    return KeyEvent.VK_8;
  }

  @Override
  public short VK_9() {
    return KeyEvent.VK_9;
  }

  @Override
  public short VK_A() {
    return KeyEvent.VK_A;
  }

  @Override
  public short VK_ACCEPT() {
    return KeyEvent.VK_ACCEPT;
  }

  @Override
  public short VK_ADD() {
    return KeyEvent.VK_ADD;
  }

  @Override
  public short VK_AGAIN() {
    return KeyEvent.VK_AGAIN;
  }

  @Override
  public short VK_ALL_CANDIDATES() {
    return KeyEvent.VK_ALL_CANDIDATES;
  }

  @Override
  public short VK_ALPHANUMERIC() {
    return KeyEvent.VK_ALPHANUMERIC;
  }

  @Override
  public short VK_ALT() {
    return KeyEvent.VK_ALT;
  }

  @Override
  public short VK_RIGHT_ALT() {
    return KeyEvent.VK_ALT_GRAPH;
  }

  @Override
  public short VK_AMPERSAND() {
    return KeyEvent.VK_AMPERSAND;
  }

  @Override
  public short VK_ASTERISK() {
    return KeyEvent.VK_ASTERISK;
  }

  @Override
  public short VK_AT() {
    return KeyEvent.VK_AT;
  }

  @Override
  public short VK_B() {
    return KeyEvent.VK_B;
  }

  @Override
  public short VK_BACK_QUOTE() {
    return KeyEvent.VK_BACK_QUOTE;
  }

  @Override
  public short VK_BACK_SLASH() {
    return KeyEvent.VK_BACK_SLASH;
  }

  @Override
  public short VK_BACK_SPACE() {
    return KeyEvent.VK_BACK_SPACE;
  }

  @Override
  public short VK_BEGIN() {
    return KeyEvent.VK_BEGIN;
  }

  @Override
  public short VK_C() {
    return KeyEvent.VK_C;
  }

  @Override
  public short VK_CANCEL() {
    return KeyEvent.VK_CANCEL;
  }

  @Override
  public short VK_CAPS_LOCK() {
    return KeyEvent.VK_CAPS_LOCK;
  }

  @Override
  public short VK_CIRCUMFLEX() {
    return KeyEvent.VK_CIRCUMFLEX;
  }

  @Override
  public short VK_CLEAR() {
    return KeyEvent.VK_CLEAR;
  }

  @Override
  public short VK_CLOSE_BRACKET() {
    return KeyEvent.VK_CLOSE_BRACKET;
  }

  @Override
  public short VK_CODE_INPUT() {
    return KeyEvent.VK_CODE_INPUT;
  }

  @Override
  public short VK_COLON() {
    return KeyEvent.VK_COLON;
  }

  @Override
  public short VK_COMMA() {
    return KeyEvent.VK_COMMA;
  }

  @Override
  public short VK_COMPOSE() {
    return KeyEvent.VK_COMPOSE;
  }

  @Override
  public short VK_CONTEXT_MENU() {
    return KeyEvent.VK_CONTEXT_MENU;
  }

  @Override
  public short VK_CONTROL() {
    return KeyEvent.VK_CONTROL;
  }

  @Override
  public short VK_CONVERT() {
    return KeyEvent.VK_CONVERT;
  }

  @Override
  public short VK_COPY() {
    return KeyEvent.VK_COPY;
  }

  @Override
  public short VK_CUT() {
    return KeyEvent.VK_CUT;
  }

  @Override
  public short VK_D() {
    return KeyEvent.VK_D;
  }

  @Override
  public short VK_DECIMAL() {
    return KeyEvent.VK_DECIMAL;
  }

  @Override
  public short VK_DELETE() {
    return KeyEvent.VK_DELETE;
  }

  @Override
  public short VK_DIVIDE() {
    return KeyEvent.VK_DIVIDE;
  }

  @Override
  public short VK_DOLLAR() {
    return KeyEvent.VK_DOLLAR;
  }

  @Override
  public short VK_DOWN() {
    return KeyEvent.VK_DOWN;
  }

  @Override
  public short VK_E() {
    return KeyEvent.VK_E;
  }

  @Override
  public short VK_END() {
    return KeyEvent.VK_END;
  }

  @Override
  public short VK_ENTER() {
    return KeyEvent.VK_ENTER;
  }

  @Override
  public short VK_EQUALS() {
    return KeyEvent.VK_EQUALS;
  }

  @Override
  public short VK_ESCAPE() {
    return KeyEvent.VK_ESCAPE;
  }

  @Override
  public short VK_EURO_SIGN() {
    return KeyEvent.VK_EURO_SIGN;
  }

  @Override
  public short VK_EXCLAMATION_MARK() {
    return KeyEvent.VK_EXCLAMATION_MARK;
  }

  @Override
  public short VK_F() {
    return KeyEvent.VK_F;
  }

  @Override
  public short VK_F1() {
    return KeyEvent.VK_F1;
  }

  @Override
  public short VK_F10() {
    return KeyEvent.VK_F10;
  }

  @Override
  public short VK_F11() {
    return KeyEvent.VK_F11;
  }

  @Override
  public short VK_F12() {
    return KeyEvent.VK_F12;
  }

  @Override
  public short VK_F13() {
    return KeyEvent.VK_F13;
  }

  @Override
  public short VK_F14() {
    return KeyEvent.VK_F14;
  }

  @Override
  public short VK_F15() {
    return KeyEvent.VK_F15;
  }

  @Override
  public short VK_F16() {
    return KeyEvent.VK_F16;
  }

  @Override
  public short VK_F17() {
    return KeyEvent.VK_F17;
  }

  @Override
  public short VK_F18() {
    return KeyEvent.VK_F18;
  }

  @Override
  public short VK_F19() {
    return KeyEvent.VK_F19;
  }

  @Override
  public short VK_F2() {
    return KeyEvent.VK_F2;
  }

  @Override
  public short VK_F20() {
    return KeyEvent.VK_F20;
  }

  @Override
  public short VK_F21() {
    return KeyEvent.VK_F21;
  }

  @Override
  public short VK_F22() {
    return KeyEvent.VK_F22;
  }

  @Override
  public short VK_F23() {
    return KeyEvent.VK_F23;
  }

  @Override
  public short VK_F24() {
    return KeyEvent.VK_F24;
  }

  @Override
  public short VK_F3() {
    return KeyEvent.VK_F3;
  }

  @Override
  public short VK_F4() {
    return KeyEvent.VK_F4;
  }

  @Override
  public short VK_F5() {
    return KeyEvent.VK_F5;
  }

  @Override
  public short VK_F6() {
    return KeyEvent.VK_F6;
  }

  @Override
  public short VK_F7() {
    return KeyEvent.VK_F7;
  }

  @Override
  public short VK_F8() {
    return KeyEvent.VK_F8;
  }

  @Override
  public short VK_F9() {
    return KeyEvent.VK_F9;
  }

  @Override
  public short VK_FINAL() {
    return KeyEvent.VK_FINAL;
  }

  @Override
  public short VK_FIND() {
    return KeyEvent.VK_FIND;
  }

  @Override
  public short VK_FULL_WIDTH() {
    return KeyEvent.VK_FULL_WIDTH;
  }

  @Override
  public short VK_G() {
    return KeyEvent.VK_G;
  }

  @Override
  public short VK_GREATER() {
    return KeyEvent.VK_GREATER;
  }

  @Override
  public short VK_H() {
    return KeyEvent.VK_H;
  }

  @Override
  public short VK_HALF_WIDTH() {
    return KeyEvent.VK_HALF_WIDTH;
  }

  @Override
  public short VK_HELP() {
    return KeyEvent.VK_HELP;
  }

  @Override
  public short VK_HIRAGANA() {
    return KeyEvent.VK_HIRAGANA;
  }

  @Override
  public short VK_HOME() {
    return KeyEvent.VK_HOME;
  }

  @Override
  public short VK_I() {
    return KeyEvent.VK_I;
  }

  @Override
  public short VK_INPUT_METHOD_ON_OFF() {
    return KeyEvent.VK_INPUT_METHOD_ON_OFF;
  }

  @Override
  public short VK_INSERT() {
    return KeyEvent.VK_INSERT;
  }

  @Override
  public short VK_INVERTED_EXCLAMATION_MARK() {
    return KeyEvent.VK_INVERTED_EXCLAMATION_MARK;
  }

  @Override
  public short VK_J() {
    return KeyEvent.VK_J;
  }

  @Override
  public short VK_JAPANESE_HIRAGANA() {
    return KeyEvent.VK_JAPANESE_HIRAGANA;
  }

  @Override
  public short VK_JAPANESE_KATAKANA() {
    return KeyEvent.VK_JAPANESE_KATAKANA;
  }

  @Override
  public short VK_JAPANESE_ROMAN() {
    return KeyEvent.VK_JAPANESE_ROMAN;
  }

  @Override
  public short VK_K() {
    return KeyEvent.VK_K;
  }

  @Override
  public short VK_KANA_LOCK() {
    return KeyEvent.VK_KANA_LOCK;
  }

  @Override
  public short VK_KATAKANA() {
    return KeyEvent.VK_KATAKANA;
  }

  @Override
  public short VK_KEYBOARD_INVISIBLE() {
    return KeyEvent.VK_KEYBOARD_INVISIBLE;
  }

  @Override
  public short VK_L() {
    return KeyEvent.VK_L;
  }

  @Override
  public short VK_LEFT() {
    return KeyEvent.VK_LEFT;
  }

  @Override
  public short VK_LEFT_BRACE() {
    return KeyEvent.VK_LEFT_BRACE;
  }

  @Override
  public short VK_LEFT_PARENTHESIS() {
    return KeyEvent.VK_LEFT_PARENTHESIS;
  }

  @Override
  public short VK_LESS() {
    return KeyEvent.VK_LESS;
  }

  @Override
  public short VK_M() {
    return KeyEvent.VK_M;
  }

  @Override
  public short VK_META() {
    return KeyEvent.VK_META;
  }

  @Override
  public short VK_MINUS() {
    return KeyEvent.VK_MINUS;
  }

  @Override
  public short VK_MODECHANGE() {
    return KeyEvent.VK_MODECHANGE;
  }

  @Override
  public short VK_MULTIPLY() {
    return KeyEvent.VK_MULTIPLY;
  }

  @Override
  public short VK_N() {
    return KeyEvent.VK_N;
  }

  @Override
  public short VK_NONCONVERT() {
    return KeyEvent.VK_NONCONVERT;
  }

  @Override
  public short VK_NUM_LOCK() {
    return KeyEvent.VK_NUM_LOCK;
  }

  @Override
  public short VK_NUMBER_SIGN() {
    return KeyEvent.VK_NUMBER_SIGN;
  }

  @Override
  public short VK_NUMPAD0() {
    return KeyEvent.VK_NUMPAD0;
  }

  @Override
  public short VK_NUMPAD1() {
    return KeyEvent.VK_NUMPAD1;
  }

  @Override
  public short VK_NUMPAD2() {
    return KeyEvent.VK_NUMPAD2;
  }

  @Override
  public short VK_NUMPAD3() {
    return KeyEvent.VK_NUMPAD3;
  }

  @Override
  public short VK_NUMPAD4() {
    return KeyEvent.VK_NUMPAD4;
  }

  @Override
  public short VK_NUMPAD5() {
    return KeyEvent.VK_NUMPAD5;
  }

  @Override
  public short VK_NUMPAD6() {
    return KeyEvent.VK_NUMPAD6;
  }

  @Override
  public short VK_NUMPAD7() {
    return KeyEvent.VK_NUMPAD7;
  }

  @Override
  public short VK_NUMPAD8() {
    return KeyEvent.VK_NUMPAD8;
  }

  @Override
  public short VK_NUMPAD9() {
    return KeyEvent.VK_NUMPAD9;
  }

  @Override
  public short VK_O() {
    return KeyEvent.VK_O;
  }

  @Override
  public short VK_OPEN_BRACKET() {
    return KeyEvent.VK_OPEN_BRACKET;
  }

  @Override
  public short VK_P() {
    return KeyEvent.VK_P;
  }

  @Override
  public short VK_PAGE_DOWN() {
    return KeyEvent.VK_PAGE_DOWN;
  }

  @Override
  public short VK_PAGE_UP() {
    return KeyEvent.VK_PAGE_UP;
  }

  @Override
  public short VK_PASTE() {
    return KeyEvent.VK_PASTE;
  }

  @Override
  public short VK_PAUSE() {
    return KeyEvent.VK_PAUSE;
  }

  @Override
  public short VK_PERCENT() {
    return KeyEvent.VK_PERCENT;
  }

  @Override
  public short VK_PERIOD() {
    return KeyEvent.VK_PERIOD;
  }

  @Override
  public short VK_PIPE() {
    return KeyEvent.VK_PIPE;
  }

  @Override
  public short VK_PLUS() {
    return KeyEvent.VK_PLUS;
  }

  @Override
  public short VK_PREVIOUS_CANDIDATE() {
    return KeyEvent.VK_PREVIOUS_CANDIDATE;
  }

  @Override
  public short VK_PRINTSCREEN() {
    return KeyEvent.VK_PRINTSCREEN;
  }

  @Override
  public short VK_PROPS() {
    return KeyEvent.VK_PROPS;
  }

  @Override
  public short VK_Q() {
    return KeyEvent.VK_Q;
  }

  @Override
  public short VK_QUESTIONMARK() {
    return KeyEvent.VK_QUESTIONMARK;
  }

  @Override
  public short VK_QUOTE() {
    return KeyEvent.VK_QUOTE;
  }

  @Override
  public short VK_QUOTEDBL() {
    return KeyEvent.VK_QUOTEDBL;
  }

  @Override
  public short VK_R() {
    return KeyEvent.VK_R;
  }

  @Override
  public short VK_RIGHT() {
    return KeyEvent.VK_RIGHT;
  }

  @Override
  public short VK_RIGHT_BRACE() {
    return KeyEvent.VK_RIGHT_BRACE;
  }

  @Override
  public short VK_RIGHT_PARENTHESIS() {
    return KeyEvent.VK_RIGHT_PARENTHESIS;
  }

  @Override
  public short VK_ROMAN_CHARACTERS() {
    return KeyEvent.VK_ROMAN_CHARACTERS;
  }

  @Override
  public short VK_S() {
    return KeyEvent.VK_S;
  }

  @Override
  public short VK_SCROLL_LOCK() {
    return KeyEvent.VK_SCROLL_LOCK;
  }

  @Override
  public short VK_SEMICOLON() {
    return KeyEvent.VK_SEMICOLON;
  }

  @Override
  public short VK_SEPARATOR() {
    return KeyEvent.VK_SEPARATOR;
  }

  @Override
  public short VK_SHIFT() {
    return KeyEvent.VK_SHIFT;
  }

  @Override
  public short VK_SLASH() {
    return KeyEvent.VK_SLASH;
  }

  @Override
  public short VK_SPACE() {
    return KeyEvent.VK_SPACE;
  }

  @Override
  public short VK_STOP() {
    return KeyEvent.VK_STOP;
  }

  @Override
  public short VK_SUBTRACT() {
    return KeyEvent.VK_SUBTRACT;
  }

  @Override
  public short VK_T() {
    return KeyEvent.VK_T;
  }

  @Override
  public short VK_TAB() {
    return KeyEvent.VK_TAB;
  }

  @Override
  public short VK_TILDE() {
    return KeyEvent.VK_TILDE;
  }

  @Override
  public short VK_U() {
    return KeyEvent.VK_U;
  }

  @Override
  public short VK_UNDEFINED() {
    return KeyEvent.VK_UNDEFINED;
  }

  @Override
  public short VK_UNDERSCORE() {
    return KeyEvent.VK_UNDERSCORE;
  }

  @Override
  public short VK_UNDO() {
    return KeyEvent.VK_UNDO;
  }

  @Override
  public short VK_UP() {
    return KeyEvent.VK_UP;
  }

  @Override
  public short VK_V() {
    return KeyEvent.VK_V;
  }

  @Override
  public short VK_W() {
    return KeyEvent.VK_W;
  }

  @Override
  public short VK_WINDOWS() {
    return KeyEvent.VK_WINDOWS;
  }

  @Override
  public short VK_X() {
    return KeyEvent.VK_X;
  }

  @Override
  public short VK_Y() {
    return KeyEvent.VK_Y;
  }

  @Override
  public short VK_Z() {
    return KeyEvent.VK_Z;
  }
  // ---- //

  KeyEvent evt;

  /**
   *
   */
  public CoreKeyEventJogl(final KeyEvent evt) {

    this.evt = evt;
  }

  @Override
  public char getKeyChar() {

    return evt.getKeyChar();
  }

  @Override
  public short getKeyCode() {

    return evt.getKeyCode();
  }

  @Override
  public short getKeySymbol() {

    return evt.getKeySymbol();
  }

  @Override
  public boolean isModifierKey() {

    return evt.isModifierKey();
  }

  @Override
  public boolean isAltDown() {

    return evt.isAltDown();
  }

  @Override
  public boolean isRightAltDown() {

    return evt.isAltGraphDown();
  }

  @Override
  public boolean isAutoRepeat() {

    return evt.isAutoRepeat();
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
