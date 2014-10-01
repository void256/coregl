package de.lessvoid.coregl.jogl;

import java.awt.DisplayMode;
import java.nio.IntBuffer;
import java.util.*;
import java.util.logging.*;
import java.util.logging.Formatter;

import javax.media.nativewindow.util.Rectangle;
import javax.media.opengl.*;

import com.jogamp.newt.*;
import com.jogamp.newt.opengl.GLWindow;

import de.lessvoid.coregl.*;
import de.lessvoid.coregl.spi.CoreGL;

public class CoreSetupJogl implements CoreSetup {
	private static final Logger log = Logger.getLogger(CoreSetupJogl.class.getName());
	private static final float NANO_TO_MS_CONVERSION = 1000000.f;
	
	private final StringBuilder fpsText = new StringBuilder();
	
	private final CoreGL gl;
	
	private Screen newtScreen;
	private Display newtDisp;
	private GLWindow glWin;
	private CoreFactory coreFactory;
	private String lastFPS = "";
	
	private volatile boolean closeRequested;

	public CoreSetupJogl(final CoreFactory coreFactory, final CoreGL gl) {
		this.coreFactory = coreFactory;
		this.gl = gl;
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreDisplaySetup#initializeLogging()
	 */
	@Override
	public void initializeLogging() {
		for (Handler handler : Logger.getLogger("").getHandlers()) {
			handler.setFormatter(new Formatter() {
				@Override
				public String format(final LogRecord record) {
					Throwable throwable = record.getThrown();
					if (throwable != null) {
						throwable.printStackTrace();
					}
					return
							record.getMillis() + " " +  
							record.getLevel() + " [" +
							record.getSourceClassName() + "] " +
							record.getMessage() + "\n";
				}
			});
		}
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreDisplaySetup#initializeLogging(java.lang.String)
	 */
	@Override
	public void initializeLogging(final String loggingProperties) {
		try {
			LogManager.getLogManager().readConfiguration((new Object()).getClass().getResourceAsStream(loggingProperties));
		} catch (Exception e) {
			throw new RuntimeException("error reading jdk14 logging properties resource from: [" + loggingProperties + "]");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreDisplaySetup#initialize(java.lang.String, int, int)
	 */
	@Override
	public void initialize(final String title, final int width, final int height) throws Exception {
		initGraphics(title, width, height);
		initInput();
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreDisplaySetup#destroy()
	 */
	@Override
	public void destroy() {
		glWin.destroy();
		newtScreen.destroy();
		newtDisp.destroy();
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreDisplaySetup#renderLoop(de.lessvoid.coregl.CoreDisplaySetup.RenderLoopCallback)
	 */
	@Override
	public void renderLoop(final RenderLoopCallback renderLoop) {
		boolean done = false;
		long frameCounter = 0;
		long now = System.currentTimeMillis();
		long prevTime = System.nanoTime();

		while (!closeRequested && !done) {
			long nanoTime = System.nanoTime();
			done = renderLoop.render((nanoTime - prevTime) / NANO_TO_MS_CONVERSION);
			prevTime = nanoTime;
			glWin.display();

			frameCounter++;
			long diff = System.currentTimeMillis() - now;
			if (diff >= 1000) {
				now += diff;
				String fpsText = buildFpsText(frameCounter);
				lastFPS = fpsText;
				log.fine(fpsText);
				frameCounter = 0;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreDisplaySetup#renderLoop2(de.lessvoid.coregl.CoreDisplaySetup.RenderLoopCallback2)
	 */
	@Override
	public void renderLoop2(final RenderLoopCallback2 renderLoop) {
		boolean done = false;
		long frameCounter = 0;
		long now = System.currentTimeMillis();
		long prevTime = System.nanoTime();

		while (!closeRequested && !done) {
			long nanoTime = System.nanoTime();
			boolean newFrame = renderLoop.render((nanoTime - prevTime) / NANO_TO_MS_CONVERSION);
			prevTime = nanoTime;
			if (newFrame) {
				glWin.display();
			}

			done = renderLoop.shouldEnd();
			frameCounter++;
			long diff = System.currentTimeMillis() - now;
			if (diff >= 1000) {
				now += diff;
				log.info(buildFpsText(frameCounter));
				frameCounter = 0;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreSetup#getFactory()
	 */
	@Override
	public CoreFactory getFactory() {
		return coreFactory;
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreSetup#enableVSync(boolean)
	 */
	@Override
	public void enableVSync(final boolean enable) {
		glWin.getGL().setSwapInterval((enable) ? 1:0);
	}

	@Override
	public String getFPS() {
		return lastFPS;
	}

	private void initGraphics(final String title, final int requestedWidth, final int requestedHeight) throws Exception {
		GLProfile profile = GLProfile.getDefault();
		GLCapabilities glc = new GLCapabilities(profile);
		newtDisp = NewtFactory.createDisplay(null);
		
		// get current Screen
		Screen curr = NewtFactory.createScreen(newtDisp, Screen.getActiveScreenNumber());
		MonitorDevice device = curr.getMainMonitor(new Rectangle(0, 0, requestedWidth, requestedHeight));
		log.fine("using device: " + device.toString());
		MonitorMode mode = device.queryCurrentMode();
		int bitDepth = mode.getSurfaceSize().getBitsPerPixel();
		logMode("currentMode: ", mode);

		// find a matching DisplayMode by size
		MonitorMode[] matchingModes = sizeMatch(device, requestedWidth, requestedHeight, bitDepth);

		// match by frequency
		MonitorMode selectedMode = frequencyMatch(matchingModes, mode.getRefreshRate());
		if (selectedMode == null) {
			selectedMode = fallbackMode(matchingModes);
		}

		// change the current mode to selectedMode
		device.setCurrentMode(selectedMode);

		// make sure we center the display
		centerDisplay(mode);

		// Create the actual window
		createWindow(title, glc);
		logMode("current mode: ", mode);

		// just output some infos about the system we're on
		log.info("plattform: " + System.getProperty("os.name"));
		log.info("opengl version: " + gl.glGetString(gl.GL_VERSION()));
		log.info("opengl vendor: " + gl.glGetString(gl.GL_VENDOR()));
		log.info("opengl renderer: " + gl.glGetString(gl.GL_RENDERER()));
		IntBuffer maxVertexAttribts = gl.getUtil().createIntBuffer(4 * 4);
		gl.glGetIntegerv(gl.GL_MAX_VERTEX_ATTRIBS(), maxVertexAttribts);
		log.info("GL_MAX_VERTEX_ATTRIBS: " + maxVertexAttribts.get(0));
		gl.checkGLError("init phase 1");

		log.info("GL_MAX_3D_TEXTURE_SIZE: " + gl.glGetInteger(gl.GL_MAX_3D_TEXTURE_SIZE()));

		gl.glViewport(0, 0, glWin.getWidth(), glWin.getHeight());

		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClear(gl.GL_COLOR_BUFFER_BIT());
		gl.glEnable(gl.GL_BLEND());
		gl.glBlendFunc(gl.GL_SRC_ALPHA(), gl.GL_ONE_MINUS_SRC_ALPHA());
		gl.checkGLError("initialized");
	}

	private void createWindow(final String title, final GLCapabilities glc) {
		glWin = GLWindow.create(newtScreen, glc);
		glWin.setFullscreen(false);
	}
	
	private MonitorMode[] sizeMatch(final MonitorDevice device, final int requestedWidth, final int requestedHeight, 
			final int requestedBitDepth) {
		List<MonitorMode> modes = device.getSupportedModes();
		log.fine("Found " + modes.size() + " display modes supported by current MonitorDevice");

		List<MonitorMode> matching = new ArrayList<MonitorMode>();
		for (int i = 0; i < modes.size(); i++) {
			MonitorMode mode = modes.get(i);
			if (matchesRequestedMode(requestedWidth, requestedHeight, requestedBitDepth, mode)) {
				logMode("matching mode: ", mode);
				matching.add(mode);
			}
		}

		MonitorMode[] matchingModes = matching.toArray(new MonitorMode[matching.size()]);
		return matchingModes;
	}

	private MonitorMode frequencyMatch(final MonitorMode[] matchingModes, final float frequency) {
		for (int i = 0; i < matchingModes.length; i++) {
			if (matchingModes[i].getRefreshRate() == frequency) {
				logMode("using frequency matching mode: ", matchingModes[i]);
				return matchingModes[i];
			}
		}
		return null;
	}

	private MonitorMode fallbackMode(final MonitorMode[] matchingModes) {
		Arrays.sort(matchingModes);
		logMode("using fallback mode: ", matchingModes[0]);
		return matchingModes[0];
	}

	private void centerDisplay(final MonitorMode currentMode) {
		int x = (currentMode.getRotatedWidth() - glWin.getWidth()) / 2;
		int y = (currentMode.getRotatedHeight() - glWin.getHeight()) / 2;
		glWin.setPosition(x, y);
	}

	private boolean matchesRequestedMode(final int requestedWidth, final int requestedHeight, 
			final int requestedBitDepth, final MonitorMode mode) {
		return
				mode.getRotatedWidth() == requestedWidth &&
				mode.getRotatedHeight() == requestedHeight &&
				mode.getSurfaceSize().getBitsPerPixel() == requestedBitDepth;
	}

	private void logMode(final String message, final MonitorMode currentMode) {
		log.info(
				message +
				currentMode.getRotatedWidth() + ", " +
						currentMode.getRotatedHeight() + ", " +
						currentMode.getSurfaceSize().getBitsPerPixel() + ", " +
						currentMode.getRefreshRate() + " Hz");
	}

	private void initInput() throws Exception {
		// TODO ...
	}

	private String buildFpsText(long frameCounter) {
		fpsText.setLength(0);
		fpsText.append("fps: ");
		fpsText.append(frameCounter);
		fpsText.append(" (");
		fpsText.append(1000 / (float) frameCounter);
		fpsText.append(" ms)");
		return fpsText.toString();
	}
}
