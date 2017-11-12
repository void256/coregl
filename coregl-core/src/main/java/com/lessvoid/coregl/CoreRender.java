/**
 * Copyright (c) 2013, Jens Hohmuth
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
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
package com.lessvoid.coregl;

import com.lessvoid.coregl.spi.CoreGL;

/**
 * Simple helper methods to render vertex arrays.
 * 
 * @author void
 */
public class CoreRender {

  private final CoreGL gl;

  CoreRender(final CoreGL gl) {
    this.gl = gl;
  }

  public static CoreRender createCoreRender(final CoreGL gl) {
    return new CoreRender(gl);
  }

  // Lines

  /**
   * Render lines.
   * 
   * @param count
   *          number of vertices
   */
  public void renderLines(final int count) {
    gl.glDrawArrays(gl.GL_LINE_STRIP(), 0, count);
    gl.checkGLError("glDrawArrays");
  }

  /**
   * Render adjacent lines.
   * 
   * @param count
   *          number of vertices
   */
  public void renderLinesAdjacent(final int count) {
    gl.glDrawArrays(gl.GL_LINE_STRIP_ADJACENCY(), 0, count);
    gl.checkGLError("glDrawArrays");
  }

  // Triangle Strip

  /**
   * Render the currently active VAO using triangle strips with the given number
   * of vertices.
   *
   * @param count
   *          number of vertices to render as triangle strips
   */
  public void renderTriangleStrip(final int count) {
    gl.glDrawArrays(gl.GL_TRIANGLE_STRIP(), 0, count);
    gl.checkGLError("glDrawArrays");
  }

  /**
   * Render the currently active VAO using triangle strips, sending the given
   * number of indizes.
   *
   * @param count
   *          number of indizes to render as triangle strips
   */
  public void renderTriangleStripIndexed(final int count) {
    gl.glDrawElements(gl.GL_TRIANGLE_STRIP(), count, gl.GL_UNSIGNED_INT(), 0);
    gl.checkGLError("glDrawElements(GL_TRIANGLE_STRIP)");
  }

  /**
   * Render the currently active VAO using triangle strips with the given number
   * of vertices AND do that primCount times.
   *
   * @param count
   *          number of vertices to render as triangle strips per primitve
   * @param primCount
   *          number of primitives to render
   */
  public void renderTriangleStripInstances(final int count, final int primCount) {
    gl.glDrawArraysInstanced(gl.GL_TRIANGLE_STRIP(), 0, count, primCount);
    gl.checkGLError("glDrawArraysInstanced(GL_TRIANGLE_STRIP)");
  }

  // Triangle Fan

  /**
   * Render the currently active VAO using triangle fan with the given number of
   * vertices.
   *
   * @param count
   *          number of vertices to render as triangle fan
   */
  public void renderTriangleFan(final int count) {
    gl.glDrawArrays(gl.GL_TRIANGLE_FAN(), 0, count);
    gl.checkGLError("glDrawArrays");
  }

  /**
   * Render the currently active VAO using triangle fans, sending the given
   * number of indizes.
   *
   * @param count
   *          number of indizes to render as triangle fans.
   */
  public void renderTriangleFanIndexed(final int count) {
    gl.glDrawElements(gl.GL_TRIANGLE_FAN(), count, gl.GL_UNSIGNED_INT(), 0);
    gl.checkGLError("glDrawElements(GL_TRIANGLE_FAN)");
  }

  // Individual Triangles

  /**
   * Render the currently active VAO using triangles with the given number of
   * vertices.
   * 
   * @param vertexCount
   *          number of vertices to render as triangle strips
   */
  public void renderTriangles(final int vertexCount) {
    gl.glDrawArrays(gl.GL_TRIANGLES(), 0, vertexCount);
    gl.checkGLError("glDrawArrays");
  }

  /**
   * Render the currently active VAO using triangles with the given number of
   * vertices starting at the given offset.
   * 
   * @param offset
   *          offset to start sending vertices
   * @param vertexCount
   *          number of vertices to render as triangle strips
   */
  public void renderTrianglesOffset(final int offset, final int vertexCount) {
    gl.glDrawArrays(gl.GL_TRIANGLES(), offset, vertexCount);
    gl.checkGLError("glDrawArrays");
  }

  /**
   * Render the currently active VAO using triangles with the given number of
   * vertices.
   *
   * @param count
   *          number of vertices to render as triangles
   */
  public void renderTrianglesIndexed(final int count) {
    gl.glDrawElements(gl.GL_TRIANGLES(), count, gl.GL_UNSIGNED_INT(), 0);
    gl.checkGLError("glDrawElements");
  }

  /**
   * Render the currently active VAO using triangles with the given number of
   * vertices AND do that primCount times.
   *
   * @param count
   *          number of vertices to render as triangles per primitve
   * @param primCount
   *          number of primitives to render
   */
  public void renderTrianglesInstances(final int count, final int primCount) {
    gl.glDrawArraysInstanced(gl.GL_TRIANGLES(), 0, count, primCount);
    gl.checkGLError("glDrawArraysInstanced(GL_TRIANGLES)");
  }

  // Points

  /**
   * Render the currently active VAO using points with the given number of
   * vertices.
   *
   * @param count
   *          number of vertices to render as points
   */
  public void renderPoints(final int count) {
    gl.glDrawArrays(gl.GL_POINTS(), 0, count);
    gl.checkGLError("glDrawArrays(GL_POINTS)");
  }

  /**
   * Render the currently active VAO using points with the given number of
   * vertices AND do that primCount times.
   *
   * @param count
   *          number of vertices to render as points per primitive
   * @param primCount
   *          number of primitives to render
   */
  public void renderPointsInstances(final int count, final int primCount) {
    gl.glDrawArraysInstanced(gl.GL_POINTS(), 0, count, primCount);
    gl.checkGLError("glDrawArraysInstanced(GL_POINTS)");
  }

  // Utils

  /**
   * Set the clear color.
   * 
   * @param r
   *          red
   * @param g
   *          green
   * @param b
   *          blue
   * @param a
   *          alpha
   */
  public void clearColor(final float r, final float g, final float b, final float a) {
    gl.glClearColor(r, g, b, a);
  }

  /**
   * Clear the color buffer.
   */
  public void clearColorBuffer() {
    gl.glClear(gl.GL_COLOR_BUFFER_BIT());
  }

}
