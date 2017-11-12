/**
 * Created by void on 26.03.17.
 */
module com.lessvoid.coregl.lwjglthree {
  requires java.base;
  requires java.logging;

  requires org.lwjgl;
  requires org.lwjgl.glfw;
  requires org.lwjgl.opengl;

  requires transitive com.lessvoid.coregl;

  provides com.lessvoid.coregl.spi.CoreGL
      with com.lessvoid.coregl.lwjgl3.CoreGLLwjgl3;
}
