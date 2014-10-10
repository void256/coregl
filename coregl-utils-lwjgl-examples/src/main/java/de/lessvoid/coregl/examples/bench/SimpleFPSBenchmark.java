package de.lessvoid.coregl.examples.bench;

import org.junit.Test;

import de.lessvoid.coregl.examples.*;
import de.lessvoid.coregl.examples.spi.CoreExample;
import de.lessvoid.coregl.jogl.*;
import de.lessvoid.coregl.lwjgl.*;
import de.lessvoid.coregl.spi.*;
import de.lessvoid.coregl.spi.CoreSetup.RenderLoopCallback;

public class SimpleFPSBenchmark {
	
	static final int DURATION_SECONDS = 30;
	
	@Test
	public void benchTestJogl() {
		CoreGL gl = new JoglCoreGL();
		CoreSetup setup = new CoreSetupJogl(gl);
		try {
			setup.initialize("CoreGL JOGL Benchmark", 1024, 768);
			CoreExample example = new StarfieldMain();
			runBenchmark(example, setup, DURATION_SECONDS);
			setup.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void benchTestLwjgl() {
		CoreGL gl = new LwjglCoreGL();
		CoreSetup setup = new CoreSetupLwjgl(gl);
		try {
			setup.initialize("CoreGL LWJGL Benchmark", 1024, 768);
			CoreExample example = new StarfieldMain();
			runBenchmark(example, setup, DURATION_SECONDS);
			setup.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void benchTestAll() {
		System.err.println("running benchmark (JOGL): ");
		benchTestJogl();
		System.err.println("waiting...");
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.err.println("running benchmark (LWJGL): ");
		benchTestLwjgl();
	}
	
	public static void runBenchmark(CoreExample example, CoreSetup setup, int duration) {
		TimedBenchmark timedBench = new TimedBenchmark(example, duration);
		setup.renderLoop(timedBench);
		double avgFrameRenderTime = timedBench.renderTimeSum / timedBench.totalFrames;
		System.err.println("average render time: " + avgFrameRenderTime / 1E6 + "ms per frame");
		System.err.println("average frames-per-second: " + timedBench.totalFrames / duration + " fps");
	}
	
	private static class TimedBenchmark implements RenderLoopCallback {
		
		final CoreExample example;
		final long duration;
		long elapsed, lastMilliTime = System.currentTimeMillis();
		
		double renderTimeSum, totalFrames;
		
		TimedBenchmark(final CoreExample example, final int duration) {
			this.example = example;
			this.duration = duration * 1000;
		}

		@Override
		public void init(CoreGL gl) {
			example.init(gl);
		}

		@Override
		public boolean render(CoreGL gl, float deltaTime) {
			long t1 = System.nanoTime();
			example.render(gl, deltaTime);
			long t2 = System.nanoTime();
			long dt = t2 - t1;
			renderTimeSum += dt;
			totalFrames++;
			
			//System.out.println("frame render time: " + dt / 1E6 + "ms - time elapsed: " + 
			 //   Math.round(elapsed) + "/" + duration + "ms");
			
			long now = System.currentTimeMillis();
			elapsed += (now - lastMilliTime);
			lastMilliTime = now;
			return elapsed >= duration;
		}
	}
}
