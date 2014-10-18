package de.lessvoid.coregl.examples.bench;

import de.lessvoid.coregl.examples.SuperSimpleExampleMain;
import de.lessvoid.coregl.jogl.*;
import de.lessvoid.coregl.lwjgl.*;
import de.lessvoid.coregl.spi.*;
import de.lessvoid.coregl.spi.CoreSetup.RenderLoopCallback;

public class SimpleFPSBenchmark {
	
	static final int DURATION_SECONDS = 20;
	
	public static void main(String[] args) {
		new SimpleFPSBenchmark().benchTestAll();
	}
	
	public double benchTestJogl() {
		CoreGL gl = new JoglCoreGL();
		CoreSetup setup = new CoreSetupJogl(gl);
		try {
			setup.initialize("CoreGL JOGL Benchmark", 1024, 768);
			RenderLoopCallback example = new SuperSimpleExampleMain();
			TimedBenchmark results = runBenchmark(example, setup, DURATION_SECONDS);
			System.err.println("average fps (JOGL): " + results.totalFrames / (results.duration / 1000));
			setup.destroy();
			return results.renderTimeSum / results.totalFrames;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public double benchTestLwjgl() {
		CoreGL gl = new LwjglCoreGL();
		CoreSetup setup = new CoreSetupLwjgl(gl);
		try {
			setup.initialize("CoreGL LWJGL Benchmark", 1024, 768);
			RenderLoopCallback example = new SuperSimpleExampleMain();
			TimedBenchmark results = runBenchmark(example, setup, DURATION_SECONDS);
			System.err.println("average fps (LWJGL): " + results.totalFrames / (results.duration / 1000));
			setup.destroy();
			return results.renderTimeSum / results.totalFrames;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public void benchTestAll() {
		System.err.println("running benchmark (JOGL): ");
		double joglAvg = benchTestJogl();
		try {
			System.err.println("invoking garbage collector...");
			System.runFinalization();
			System.gc();
			System.err.println("pausing for gc...");
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.err.println("running benchmark (LWJGL): ");
		double lwjglAvg = benchTestLwjgl();
		System.err.println("Average frame render time (JOGL) = " + joglAvg / 1E6 + "ms");
		System.err.println("Average frame render time (LWJGL) = " + lwjglAvg / 1E6 + "ms");
	}
	
	public static TimedBenchmark runBenchmark(RenderLoopCallback example, CoreSetup setup, int duration) {
		TimedBenchmark timedBench = new TimedBenchmark(example, duration);
		setup.renderLoop(timedBench);
		//double avgFrameRenderTime = timedBench.renderTimeSum / timedBench.totalFrames;
		//System.err.println("average render time: " + avgFrameRenderTime / 1E6 + "ms per frame");
		//System.err.println("average frames-per-second: " + timedBench.totalFrames / duration + " fps");
		return timedBench;
	}
	
	private static class TimedBenchmark implements RenderLoopCallback {
		
		final RenderLoopCallback example;
		final long duration;
		long elapsed, lastMilliTime = System.currentTimeMillis();
		
		double renderTimeSum, totalFrames;
		
		TimedBenchmark(final RenderLoopCallback example, final int duration) {
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
