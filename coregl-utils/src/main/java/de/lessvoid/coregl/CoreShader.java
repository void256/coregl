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


import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.FloatBuffer;

/**
 * Helper class that represents a shader (actually the combination of a vertex
 * and a fragment shader - what GL actually calls a program).
 * @author void
 */
public interface CoreShader {

  // vertex shader

  /**
   * Attach the given vertex shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param resourcename the filename of the shader
   */
  int vertexShader(String resourcename);

  /**
   * Attach the given vertex shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param filename the file of the shader
   */
  int vertexShader(File file) throws FileNotFoundException;

  /**
   * Attach the given vertex shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param streamName a name for the InputStream that will be logged if any compile errors occur
   * @param filename the file of the shader
   */
  int vertexShader(String streamName, InputStream ... sources);

  /**
   * Attach the given vertex shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param resourcename the resourcename of the shader
   */
  void vertexShader(int shaderId, String resourcename);

  /**
   * Attach the given vertex shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param filename the file of the shader
   */
  void vertexShader(int shaderId, File file) throws FileNotFoundException;

  /**
   * Attach the given vertex shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param shaderId shader id
   * @param streamName a name for the InputStream that will be logged if any compile errors occur
   * @param source the actual stream source
   */
  void vertexShader(int shaderId, String streamName, InputStream source);

  // geometry shader

  /**
   * Attach the given geometry shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param resourcename the resourcename of the shader
   */
  int geometryShader(String resourcename);

  /**
   * Attach the given geometry shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param filename the file of the shader
   */
  int geometryShader(File file) throws FileNotFoundException;

  /**
   * Attach the given geometry shader file to this CoreShader.
   * @param file the file of the shader
   * @param inputStreams the inputStreams to read the shader source
   */
  int geometryShader(File file, InputStream ... inputStreams) throws FileNotFoundException;

  /**
   * Attach the given geometry shader file to this CoreShader.
   * @param streamName the name of the streams of the shader
   * @param inputStreams the inputStreams to read the shader source
   */
  int geometryShader(String streamName, InputStream ... inputStreams) throws FileNotFoundException;

  /**
   * Attach the given geometry shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param filename the file of the shader
   */
  void geometryShader(int shaderId, File file) throws FileNotFoundException;

  /**
   * Attach the given geometry shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param shaderId shader id
   * @param streamName a name for the InputStream that will be logged if any compile errors occur
   * @param source the actual stream source
   */
  void geometryShader(int shaderId, String streamName, InputStream source);

  /**
   * Attach the given geometry shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param resourcename the resourcename of the shader
   */
  void geometryShader(int shaderId, String resourcename);

  // fragment shader

  /**
   * Attach the given fragment shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param resourcename the resourcename of the shader
   */
  int fragmentShader(String resourcename);

  /**
   * Attach the given fragment shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param filename the file of the shader
   */
  int fragmentShader(File file) throws FileNotFoundException;

  /**
   * Attach the given fragment shader from the InputStream.
   * @param streamName the streamName (used to identify the shader)
   * @param inputStreams the source stream of the shader
   * @return 
   * @throws FileNotFoundException
   */
  int fragmentShader(String streamName, InputStream ... inputStreams) throws FileNotFoundException;

  /**
   * Attach the given fragment shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param resourcename the resourcename of the shader
   */
  void fragmentShader(int shaderId, String resourcename);

  /**
   * Attach the given fragment shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param filename the file of the shader
   */
  void fragmentShader(int shaderId, File file) throws FileNotFoundException;

  /**
   * Attach the given fragment shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param shaderId shader id
   * @param streamName a name for the InputStream that will be logged if any compile errors occur
   * @param source the actual stream source
   */
  void fragmentShader(int shaderId, String streamName, InputStream source);

  // link and activate

  /**
   * Link the Shader.
   */
  void link();

  /**
   * Activate this program.
   */
  void activate();

  // uniform access

  /**
   * Set the uniform with the given name to the given float value.
   * @param name name of the uniform to set
   * @param value the new float value
   */
  void setUniformf(String name, float value);

  /**
   * Set the uniform with the given name to the given value (two floats = vec2).
   * @param name name of the uniform to set
   * @param v1 first component of the vec2
   * @param v2 second component of the vec2
   */
  void setUniformf(String name, float v1, float v2);

  /**
   * Set the uniform with the given name to the given value (three floats = vec3).
   * @param name name of the uniform to set
   * @param v1 first component of the vec3
   * @param v2 second component of the vec3
   * @param v3 third component of the vec3
   */
  void setUniformf(String name, float v1, float v2, float v3);

  /**
   * Set the uniform with the given name to the given value (four floats = vec4).
   * @param name name of the uniform to set
   * @param v1 first component of the vec4
   * @param v2 second component of the vec4
   * @param v3 third component of the vec4
   * @param v4 fourth component of the vec4
   */
  void setUniformf(String name, float x, float y, float z, float w);

  /**
   * Set the uniform with the given name to the given int value.
   * @param name name of the uniform to set
   * @param value the new int value
   */
  void setUniformi(String name, int v1);

  /**
   * Set the uniform with the given name to the given int values (two values = ivec2).
   * @param name name of the uniform to set
   * @param v1 the first int value of the ivec2
   * @param v2 the second int value of the ivec2
   */
  void setUniformi(String name, int v1, int v2);

  /**
   * Set the uniform with the given name to the given int values (three values = ivec3).
   * @param name name of the uniform to set
   * @param v1 the first int value of the ivec3
   * @param v2 the second int value of the ivec3
   * @param v3 the third int value of the ivec3
   */
  void setUniformi(String name, int v1, int v2, int v3);

  /**
   * Set the uniform with the given name to the given int values (four values = ivec4).
   * @param name name of the uniform to set
   * @param v1 the first int value of the ivec4
   * @param v2 the second int value of the ivec4
   * @param v3 the third int value of the ivec4
   * @param v3 the fourth int value of the ivec4
   */
  void setUniformi(String name, int v1, int v2, int v3, int v4);

  /**
   * Set the uniform mat4 with the given name to the given matrix (Matrix4f).
   * @param name the name of the uniform
   * @param matrix the Matrix4f to set
   */
  void setUniformMatrix4f(String name, FloatBuffer matrix);

  /**
   * Set the uniform mat4 with the given name to the given matrix (Matrix4f).
   * @param name the name of the uniform
   * @param matrix the Matrix3f to set
   */
  void setUniformMatrix3f(String name, FloatBuffer matrix);

  /**
   * Set the uniform float array with the given name to a new value.
   * @param name the name of the uniform
   * @param values the new float array to set
   */
  void setUniformfArray(String name, float[] values);

  /**
   * Set the uniform int array with the given name to a new value.
   * @param name the name of the uniform
   * @param values the new int array to set
   */
  void setUniformiArray(String name, int[] values);

  // attribute location

  /**
   * Get the vertex attribute location of the vertex attribute with the given name.
   * @param name the name of the vertex attribute
   * @return the generic vertex attribute index value
   */
  int getAttribLocation(String name);

  /**
   * You can manually bind the vertex attribute with the given name to the given specific index value. You'll need to
   * call this method before calling the link() method!
   * 
   * @param name the name of the vertex attribute
   * @param index the new index you want to give that vertex attribute
   */
  void bindAttribLocation(String name, int index);

}
