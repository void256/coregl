/**
 * Copyright (c) 2013, Jens Hohmuth
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package de.lessvoid.coregl;
/**
 * Helper to initialize a display and manages the render loop. This is a completely optional interface/class that you
 * can use if you see it fits. Most actual programs that'll use the coregl-utils will use their own setup code and have
 * probably not a use for this class.
 *
 * Still it's a handy class if you want some code to get up and running in a couple of minutes :)
 *
 * @author void
 */
public interface CoreSetup {
	/**
	 * You can implement this interface when you use the renderLoop() method. This will be called each frame and allows
	 * you to actually draw.
	 * @author void
	 */
	public interface RenderLoopCallback {
		/**
		 * Do some awesome stuff in here!
		 * @param deltaTime the time past since the last call in ms
		 * @return true when the render loop should be stopped and false if you want it to continue.
		 */
		boolean render(float deltaTime);
	}
	/**
	 * You can implement this interface when you use the renderLoop() method. This will be called each frame and allows
	 * you to actually draw.
	 * @author void
	 */
	public interface RenderLoopCallback2 {
		/**
		 * Do some awesome stuff in here!
		 * @param deltaTime the time past since the last call in ms
		 * @return true when the render actually rendered a new frame and false otherwise
		 */
		boolean render(float deltaTime);
		/**
		 * Might return true when the render loop should stop.
		 * @return true when the render loop should stop and false if it should continue
		 */
		boolean shouldEnd();
	}
	/**
	 * (optional) This method will just set a new jdk14 Formatter that is more readable then the defaults.
	 */
	void initializeLogging();
	/**
	 * (optional) This method will just set a new jdk14 Formatter that is more readable then the defaults and
	 * additionally reads the given loggingProperties from the Classpath and uses it to initialize the LogManager.
	 *
	 * @parameter loggingProperties a jdk14 configuration file available in the Classpath
	 */
	void initializeLogging(String loggingProperties);
	/**
	 * Initialize.
	 * @param title The title of the window
	 * @param width width of the screen
	 * @param height height of the screen
	 * @throws Exception in case of any errors
	 */
	void initialize(String title, int width, int height) throws Exception;
	/**
	 * Destroy LWJGL and free resources.
	 */
	void destroy();
	/**
	 * The renderLoop will keep calling Display.update() and calls the RenderLoopCallback each frame.
	 * @param renderLoop the RenderLoopCallback implementation
	 */
	void renderLoop(RenderLoopCallback renderLoop);
	/**
	 * FIXME need a better name ;)
	 * @param renderLoop
	 */
	void renderLoop2(RenderLoopCallback2 renderLoop);
	/**
	 * Convenience Method to get a CoreFactory directly from this class.
	 * @return a CoreFactory
	 */
	CoreFactory getFactory();
	/**
	 * Enable vsync.
	 * @param enable true to enable and false to disable
	 */
	void enableVSync(boolean enable);
	/**
	 * Returns the last measured FPS and frametime info String.
	 * @return
	 */
	String getFPS();
}