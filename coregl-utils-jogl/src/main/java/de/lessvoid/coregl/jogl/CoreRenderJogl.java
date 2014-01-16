package de.lessvoid.coregl.jogl;

import static javax.media.opengl.GL.*;

import javax.media.opengl.*;

import de.lessvoid.coregl.*;

public class CoreRenderJogl implements CoreRender {

	  private final CoreCheckGL checkGL;

	  CoreRenderJogl(final CoreCheckGL checkGLParam) {
	    this.checkGL = checkGLParam;
	  }

	  /*
	   * (non-Javadoc)
	   * @see de.lessvoid.coregl.CoreRender#renderLines(int)
	   */
	  @Override
	  public void renderLines(final int count) {
	    getGL().glDrawArrays(GL_LINE_STRIP, 0, count);
	    checkGL.checkGLError("glDrawArrays");
	  }

	  /*
	   * (non-Javadoc)
	   * @see de.lessvoid.coregl.CoreRender#renderLinesAdjacent(int)
	   */
	  @Override
	  public void renderLinesAdjacent(final int count) {
	    getGL3().glDrawArrays(GL3.GL_LINE_STRIP_ADJACENCY, 0, count);
	    checkGL.checkGLError("glDrawArrays");
	  }

	  // Triangle Strip

	  /*
	   * (non-Javadoc)
	   * @see de.lessvoid.coregl.CoreRender#renderTriangleStrip(int)
	   */
	  @Override
	  public void renderTriangleStrip(final int count) {
	    getGL().glDrawArrays(GL_TRIANGLE_STRIP, 0, count);
	    checkGL.checkGLError("glDrawArrays");
	  }

	  /*
	   * (non-Javadoc)
	   * @see de.lessvoid.coregl.CoreRender#renderTriangleStripIndexed(int)
	   */
	  @Override
	  public void renderTriangleStripIndexed(final int count) {
		getGL().glDrawElements(GL_TRIANGLE_STRIP, count, GL_UNSIGNED_INT, 0);
	    checkGL.checkGLError("glDrawElements(GL_TRIANGLE_STRIP)");
	  }

	  /*
	   * (non-Javadoc)
	   * @see de.lessvoid.coregl.CoreRender#renderTriangleStripInstances(int, int)
	   */
	  @Override
	  public void renderTriangleStripInstances(final int count, final int primCount) {
		getGL3().glDrawArraysInstanced(GL_TRIANGLE_STRIP, 0, count, primCount);
	    checkGL.checkGLError("glDrawArraysInstanced(GL_TRIANGLE_STRIP)");
	  }

	  // Triangle Fan

	  /*
	   * (non-Javadoc)
	   * @see de.lessvoid.coregl.CoreRender#renderTriangleFan(int)
	   */
	  @Override
	  public void renderTriangleFan(final int count) {
		getGL().glDrawArrays(GL_TRIANGLE_FAN, 0, count);
	    checkGL.checkGLError("glDrawArrays");
	  }

	  /*
	   * (non-Javadoc)
	   * @see de.lessvoid.coregl.CoreRender#renderTriangleFanIndexed(int)
	   */
	  @Override
	  public void renderTriangleFanIndexed(final int count) {
	    getGL().glDrawElements(GL_TRIANGLE_FAN, count, GL_UNSIGNED_INT, 0);
	    checkGL.checkGLError("glDrawElements(GL_TRIANGLE_FAN)");
	  }

	  // Individual Triangles

	  /*
	   * (non-Javadoc)
	   * @see de.lessvoid.coregl.CoreRender#renderTriangles(int)
	   */
	  @Override
	  public void renderTriangles(final int vertexCount) {
	    getGL().glDrawArrays(GL_TRIANGLES, 0, vertexCount);
	    checkGL.checkGLError("glDrawArrays");
	  }

	  /*
	   * (non-Javadoc)
	   * @see de.lessvoid.coregl.CoreRender#renderTrianglesOffset(int, int)
	   */
	  @Override
	  public void renderTrianglesOffset(final int offset, final int vertexCount) {
	    getGL().glDrawArrays(GL_TRIANGLES, offset, vertexCount);
	    checkGL.checkGLError("glDrawArrays");
	  }

	  /*
	   * (non-Javadoc)
	   * @see de.lessvoid.coregl.CoreRender#renderTrianglesIndexed(int)
	   */
	  @Override
	  public void renderTrianglesIndexed(final int count) {
	    getGL().glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);
	    checkGL.checkGLError("glDrawElements");
	  }

	  /*
	   * (non-Javadoc)
	   * @see de.lessvoid.coregl.CoreRender#renderTrianglesInstances(int, int)
	   */
	  @Override
	  public void renderTrianglesInstances(final int count, final int primCount) {
	    getGL3().glDrawArraysInstanced(GL_TRIANGLES, 0, count, primCount);
	    checkGL.checkGLError("glDrawArraysInstanced(GL_TRIANGLES)");
	  }

	  // Points

	  /*
	   * (non-Javadoc)
	   * @see de.lessvoid.coregl.CoreRender#renderPoints(int)
	   */
	  @Override
	  public void renderPoints(final int count) {
	    getGL().glDrawArrays(GL_POINTS, 0, count);
	    checkGL.checkGLError("glDrawArrays(GL_POINTS)");
	  }

	  /*
	   * (non-Javadoc)
	   * @see de.lessvoid.coregl.CoreRender#renderPointsInstances(int, int)
	   */
	  @Override
	  public void renderPointsInstances(final int count, final int primCount) {
	    getGL3().glDrawArraysInstanced(GL_POINTS, 0, count, primCount);
	    checkGL.checkGLError("glDrawArraysInstanced(GL_POINTS)");
	  }

	  @Override
	  public void clearColor(final float r, final float g, final float b, final float a) {
	    getGL().glClearColor(r, g, b, a);
	  }

	  @Override
	  public void clearColorBuffer() {
	    getGL().glClear(GL_COLOR_BUFFER_BIT);
	  }
	  
	  private GL getGL() {
		  return GLContext.getCurrentGL();
	  }
	  
	  private GL3 getGL3() {
		  return getGL().getGL3();
	  }

}
