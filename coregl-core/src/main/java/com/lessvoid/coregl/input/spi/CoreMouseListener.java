package com.lessvoid.coregl.input.spi;

/**
 * @author Brian Groenke
 *
 */
public interface CoreMouseListener extends CoreInputListener<CoreMouseEvent> {

  void mouseClicked(CoreMouseEvent e);

  void mouseDragged(CoreMouseEvent e);

  void mouseEntered(CoreMouseEvent e);

  void mouseExited(CoreMouseEvent e);

  void mouseMoved(CoreMouseEvent e);

  void mousePressed(CoreMouseEvent e);

  void mouseReleased(CoreMouseEvent e);

  void mouseWheelMoved(CoreMouseEvent e);
}
