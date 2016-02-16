package com.lessvoid.coregl.examples.bench;

import com.lessvoid.coregl.examples.SuperSimpleExampleMain;
import com.lessvoid.coregl.lwjgl.LwjglCoreGL;
import com.lessvoid.coregl.jogl.CoreSetupJogl;
import com.lessvoid.coregl.jogl.JoglCoreGL;
import com.lessvoid.coregl.lwjgl.CoreSetupLwjgl;
import com.lessvoid.coregl.spi.CoreGL;
import com.lessvoid.coregl.spi.CoreSetup;
import com.lessvoid.coregl.spi.CoreSetup.RenderLoopCallback;

public class SimpleFPSBenchmark {

  static final int DURATION_SECONDS = 20;

  public static void main(final String[] args) {
    new SimpleFPSBenchmark().benchTestAll();
  }

  public double benchTestJogl() {
    final CoreGL gl = new JoglCoreGL();
    final CoreSetup setup = new CoreSetupJogl(gl);
    try {
      setup.initialize("CoreGL JOGL Benchmark", 1024, 768);
      final RenderLoopCallback example = new SuperSimpleExampleMain();
      final TimedBenchmark results = runBenchmark(example, setup, DURATION_SECONDS);
      System.err.println("average fps (JOGL): " + results.totalFrames / (results.duration / 1000));
      setup.destroy();
      return results.renderTimeSum / results.totalFrames;
    } catch (final Exception e) {
      e.printStackTrace();
    }
    return -1;
  }

  public double benchTestLwjgl() {
    final CoreGL gl = new LwjglCoreGL();
    final CoreSetup setup = new CoreSetupLwjgl(gl);
    try {
      setup.initialize("CoreGL LWJGL Benchmark", 1024, 768);
      final RenderLoopCallback example = new SuperSimpleExampleMain();
      final TimedBenchmark results = runBenchmark(example, setup, DURATION_SECONDS);
      System.err.println("average fps (LWJGL): " + results.totalFrames / (results.duration / 1000));
      setup.destroy();
      return results.renderTimeSum / results.totalFrames;
    } catch (final Exception e) {
      e.printStackTrace();
    }
    return -1;
  }

  public void benchTestAll() {
    System.err.println("running benchmark (JOGL): ");
    final double joglAvg = benchTestJogl();
    try {
      System.err.println("invoking garbage collector...");
      System.runFinalization();
      System.gc();
      System.err.println("pausing for gc...");
      Thread.sleep(5000);
    } catch (final InterruptedException e) {
      e.printStackTrace();
    }
    System.err.println("running benchmark (LWJGL): ");
    final double lwjglAvg = benchTestLwjgl();
    System.err.println("Average frame render time (JOGL) = " + joglAvg / 1E6 + "ms");
    System.err.println("Average frame render time (LWJGL) = " + lwjglAvg / 1E6 + "ms");
  }

  public static TimedBenchmark runBenchmark(final RenderLoopCallback example,
                                            final CoreSetup setup,
                                            final int duration) {
    final TimedBenchmark timedBench = new TimedBenchmark(example, duration);
    setup.renderLoop(timedBench);
    // double avgFrameRenderTime = timedBench.renderTimeSum /
    // timedBench.totalFrames;
    // System.err.println("average render time: " + avgFrameRenderTime / 1E6 +
    // "ms per frame");
    // System.err.println("average frames-per-second: " + timedBench.totalFrames
    // / duration + " fps");
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
    public void init(final CoreGL gl) {
      example.init(gl);
    }

    @Override
    public boolean render(final CoreGL gl, final float deltaTime) {
      final long t1 = System.nanoTime();
      example.render(gl, deltaTime);
      final long t2 = System.nanoTime();
      final long dt = t2 - t1;
      renderTimeSum += dt;
      totalFrames++;

      // System.out.println("frame render time: " + dt / 1E6 + "ms - time
      // elapsed: " +
      // Math.round(elapsed) + "/" + duration + "ms");

      final long now = System.currentTimeMillis();
      elapsed += (now - lastMilliTime);
      lastMilliTime = now;
      return true;
    }

    @Override
    public boolean endLoop() {
      return elapsed >= duration;
    }
  }
}
