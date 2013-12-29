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
package de.lessvoid.coregl.lwjgl;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL31.glDrawArraysInstanced;

import org.lwjgl.opengl.GL32;

import de.lessvoid.coregl.CoreCheckGL;
import de.lessvoid.coregl.CoreRender;

/**
 * Simple helper methods to render vertex arrays.
 * @author void
 */
public class CoreRenderLwjgl implements CoreRender {
  private final CoreCheckGL checkGL;

  CoreRenderLwjgl(final CoreCheckGL checkGLParam) {
    this.checkGL = checkGLParam;
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreRender#renderLines(int)
   */
  @Override
  public void renderLines(final int count) {
    glDrawArrays(GL_LINE_STRIP, 0, count);
    checkGL.checkGLError("glDrawArrays");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreRender#renderLinesAdjacent(int)
   */
  @Override
  public void renderLinesAdjacent(final int count) {
    glDrawArrays(GL32.GL_LINE_STRIP_ADJACENCY, 0, count);
    checkGL.checkGLError("glDrawArrays");
  }

  // Triangle Strip

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreRender#renderTriangleStrip(int)
   */
  @Override
  public void renderTriangleStrip(final int count) {
    glDrawArrays(GL_TRIANGLE_STRIP, 0, count);
    checkGL.checkGLError("glDrawArrays");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreRender#renderTriangleStripIndexed(int)
   */
  @Override
  public void renderTriangleStripIndexed(final int count) {
    glDrawElements(GL_TRIANGLE_STRIP, count, GL_UNSIGNED_INT, 0);
    checkGL.checkGLError("glDrawElements(GL_TRIANGLE_STRIP)");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreRender#renderTriangleStripInstances(int, int)
   */
  @Override
  public void renderTriangleStripInstances(final int count, final int primCount) {
    glDrawArraysInstanced(GL_TRIANGLE_STRIP, 0, count, primCount);
    checkGL.checkGLError("glDrawArraysInstanced(GL_TRIANGLE_STRIP)");
  }

  // Triangle Fan

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreRender#renderTriangleFan(int)
   */
  @Override
  public void renderTriangleFan(final int count) {
    glDrawArrays(GL_TRIANGLE_FAN, 0, count);
    checkGL.checkGLError("glDrawArrays");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreRender#renderTriangleFanIndexed(int)
   */
  @Override
  public void renderTriangleFanIndexed(final int count) {
    glDrawElements(GL_TRIANGLE_FAN, count, GL_UNSIGNED_INT, 0);
    checkGL.checkGLError("glDrawElements(GL_TRIANGLE_FAN)");
  }

  // Individual Triangles

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreRender#renderTriangles(int)
   */
  @Override
  public void renderTriangles(final int vertexCount) {
    glDrawArrays(GL_TRIANGLES, 0, vertexCount);
    checkGL.checkGLError("glDrawArrays");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreRender#renderTrianglesOffset(int, int)
   */
  @Override
  public void renderTrianglesOffset(final int offset, final int vertexCount) {
    glDrawArrays(GL_TRIANGLES, offset, vertexCount);
    checkGL.checkGLError("glDrawArrays");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreRender#renderTrianglesIndexed(int)
   */
  @Override
  public void renderTrianglesIndexed(final int count) {
    glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);
    checkGL.checkGLError("glDrawElements");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreRender#renderTrianglesInstances(int, int)
   */
  @Override
  public void renderTrianglesInstances(final int count, final int primCount) {
    glDrawArraysInstanced(GL_TRIANGLES, 0, count, primCount);
    checkGL.checkGLError("glDrawArraysInstanced(GL_TRIANGLES)");
  }

  // Points

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreRender#renderPoints(int)
   */
  @Override
  public void renderPoints(final int count) {
    glDrawArrays(GL_POINTS, 0, count);
    checkGL.checkGLError("glDrawArrays(GL_POINTS)");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreRender#renderPointsInstances(int, int)
   */
  @Override
  public void renderPointsInstances(final int count, final int primCount) {
    glDrawArraysInstanced(GL_POINTS, 0, count, primCount);
    checkGL.checkGLError("glDrawArraysInstanced(GL_POINTS)");
  }

  @Override
  public void clearColor(final float r, final float g, final float b, final float a) {
    glClearColor(r, g, b, a);
  }

  @Override
  public void clearColorBuffer() {
    glClear(GL_COLOR_BUFFER_BIT);
  }
}
