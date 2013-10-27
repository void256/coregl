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
package de.lessvoid.coregl;


/**
 * Simple helper methods to render vertex arrays.
 * @author void
 */
public interface CoreRender {

  // Lines

  /**
   * Render lines.
   * @param count number of vertices
   */
  void renderLines(final int count);

  /**
   * Render adjacent lines.
   * @param count number of vertices
   */
  void renderLinesAdjacent(int count);

  // Triangle Strip

  /**
   * Render the currently active VAO using triangle strips with the given
   * number of vertices.
   *
   * @param count number of vertices to render as triangle strips
   */
  void renderTriangleStrip(int count);

  /**
   * Render the currently active VAO using triangle strips, sending the given number of indizes.
   *
   * @param count number of indizes to render as triangle strips
   */
  void renderTriangleStripIndexed(int count);

  /**
   * Render the currently active VAO using triangle strips with the given
   * number of vertices AND do that primCount times.
   *
   * @param count number of vertices to render as triangle strips per primitve
   * @param primCount number of primitives to render
   */
  void renderTriangleStripInstances(int count, int primCount);

  // Triangle Fan

  /**
   * Render the currently active VAO using triangle fan with the given number of vertices.
   *
   * @param count number of vertices to render as triangle fan
   */
  void renderTriangleFan(int count);

  /**
   * Render the currently active VAO using triangle fans, sending the given number of indizes.
   *
   * @param count number of indizes to render as triangle fans.
   */
  void renderTriangleFanIndexed(int count);

  // Individual Triangles

  /**
   * Render the currently active VAO using triangles with the given number of vertices.
   * @param vertexCount number of vertices to render as triangle strips
   */
  void renderTriangles(int vertexCount);

  /**
   * Render the currently active VAO using triangles with the given number of vertices starting at the given offset.
   * @param offset offset to start sending vertices
   * @param vertexCount number of vertices to render as triangle strips
   */
  void renderTrianglesOffset(int offset, int vertexCount);

  /**
   * Render the currently active VAO using triangles with the given
   * number of vertices.
   *
   * @param count number of vertices to render as triangles
   */
  void renderTrianglesIndexed(int count);

  /**
   * Render the currently active VAO using triangles with the given number of vertices AND do that primCount times.
   *
   * @param count number of vertices to render as triangles per primitve
   * @param primCount number of primitives to render
   */
  void renderTrianglesInstances(int count, int primCount);

  // Points

  /**
   * Render the currently active VAO using points with the given
   * number of vertices.
   *
   * @param count number of vertices to render as points
   */
  void renderPoints(int count);

  /**
   * Render the currently active VAO using points with the given
   * number of vertices AND do that primCount times.
   *
   * @param count number of vertices to render as points per primitive
   * @param primCount number of primitives to render
   */
  void renderPointsInstances(int count, int primCount);

  // Utils

  /**
   * Set the clear color.
   * @param r red
   * @param g green
   * @param b blue
   * @param a alpha
   */
  void clearColor(float r, float g, float b, float a);

  /**
   * Clear the color buffer.
   */
  void clearColorBuffer();

}
