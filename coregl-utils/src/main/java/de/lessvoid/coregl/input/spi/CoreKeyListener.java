package de.lessvoid.coregl.input.spi;

/**
 * @author Brian Groenke
 *
 */
public interface CoreKeyListener extends CoreInputListener<CoreKeyEvent> {

  void keyPressed(CoreKeyEvent event);

  void keyReleased(CoreKeyEvent event);
}
