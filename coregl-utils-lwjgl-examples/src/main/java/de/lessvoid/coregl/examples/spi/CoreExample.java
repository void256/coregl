package de.lessvoid.coregl.examples.spi;

import de.lessvoid.coregl.spi.CoreSetup.RenderLoopCallback;

public interface CoreExample extends RenderLoopCallback {
	
	void runJogl();
	void runLwjgl();
}
