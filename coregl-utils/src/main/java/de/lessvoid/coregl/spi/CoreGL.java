package de.lessvoid.coregl.spi;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * @author Aaron Mahan &lt;aaron@forerunnergames.com&gt;
 * @author Brian Groenke (groenke.5@osu.edu)
 */
public interface CoreGL {
  // OpenGL constants
  public int GL_ALPHA();

  public int GL_ALPHA_TEST();

  public int GL_BLEND();

  public int GL_BLEND_DST();

  public int GL_BLEND_SRC();

  public int GL_BYTE();

  public int GL_COLOR_BUFFER_BIT();

  public int GL_DEPTH_BUFFER_BIT();

  public int GL_CULL_FACE();

  public int GL_DEPTH_TEST();

  public int GL_DST_COLOR();

  public int GL_FALSE();

  public int GL_FLOAT();

  public int GL_INVALID_ENUM();

  public int GL_INVALID_OPERATION();

  public int GL_INVALID_VALUE();

  public int GL_LINEAR();

  public int GL_LINEAR_MIPMAP_LINEAR();

  public int GL_LINEAR_MIPMAP_NEAREST();

  public int GL_LUMINANCE();

  public int GL_LUMINANCE_ALPHA();

  public int GL_MAX_TEXTURE_SIZE();

  public int GL_NEAREST();

  public int GL_NEAREST_MIPMAP_LINEAR();

  public int GL_NEAREST_MIPMAP_NEAREST();

  public int GL_NO_ERROR();

  public int GL_NOTEQUAL();

  public int GL_OUT_OF_MEMORY();

  public int GL_POINTS();

  public int GL_RGB();

  public int GL_RGBA();

  public int GL_SHORT();

  public int GL_STACK_OVERFLOW();

  public int GL_STACK_UNDERFLOW();

  public int GL_TEXTURE_2D();

  public int GL_TEXTURE_BINDING_2D();

  public int GL_TEXTURE_3D();

  public int GL_TEXTURE_BINDING_3D();

  public int GL_TEXTURE_MAG_FILTER();

  public int GL_TEXTURE_MIN_FILTER();

  public int GL_LINE_STRIP();

  public int GL_LINE_STRIP_ADJACENCY();

  public int GL_TRIANGLES();

  public int GL_TRIANGLE_STRIP();

  public int GL_TRIANGLE_FAN();

  public int GL_TRUE();

  public int GL_UNSIGNED_BYTE();

  public int GL_UNSIGNED_SHORT();

  public int GL_UNSIGNED_SHORT_4_4_4_4();

  public int GL_UNSIGNED_SHORT_5_5_5_1();

  public int GL_UNSIGNED_SHORT_5_6_5();

  public int GL_VIEWPORT();

  public int GL_ZERO();

  public int GL_ONE();

  public int GL_ONE_MINUS_DST_ALPHA();

  public int GL_DST_ALPHA();

  public int GL_SRC_ALPHA();

  public int GL_ONE_MINUS_SRC_ALPHA();

  public int GL_MAX();

  public int GL_FUNC_ADD();

  public int GL_ACTIVE_TEXTURE();

  public int GL_ARRAY_BUFFER();

  public int GL_BGR();

  public int GL_BGRA();

  public int GL_BLUE();

  public int GL_COMPILE_STATUS();

  public int GL_COMPRESSED_RGB();

  public int GL_COMPRESSED_RGBA();

  public int GL_CURRENT_PROGRAM();

  public int GL_DYNAMIC_DRAW();

  public int GL_ELEMENT_ARRAY_BUFFER();

  public int GL_FRAGMENT_SHADER();

  public int GL_GEOMETRY_SHADER();

  public int GL_GREEN();

  public int GL_INT();

  public int GL_LINK_STATUS();

  public int GL_PRIMITIVE_RESTART();

  public int GL_PRIMITIVE_RESTART_INDEX();

  public int GL_RED();

  public int GL_STATIC_DRAW();

  public int GL_STREAM_DRAW();

  public int GL_TEXTURE0();

  public int GL_TEXTURE_2D_ARRAY();

  public int GL_TEXTURE_CUBE_MAP_NEGATIVE_X();

  public int GL_TEXTURE_CUBE_MAP_NEGATIVE_Y();

  public int GL_TEXTURE_CUBE_MAP_NEGATIVE_Z();

  public int GL_TEXTURE_CUBE_MAP_POSITIVE_X();

  public int GL_TEXTURE_CUBE_MAP_POSITIVE_Y();

  public int GL_TEXTURE_CUBE_MAP_POSITIVE_Z();

  public int GL_UNSIGNED_BYTE_2_3_3_REV();

  public int GL_UNSIGNED_BYTE_3_3_2();

  public int GL_UNSIGNED_INT();

  public int GL_UNSIGNED_INT_10_10_10_2();

  public int GL_UNSIGNED_INT_2_10_10_10_REV();

  public int GL_UNSIGNED_INT_8_8_8_8();

  public int GL_UNSIGNED_INT_8_8_8_8_REV();

  public int GL_HALF_FLOAT();

  public int GL_DOUBLE();

  public int GL_FIXED();

  public int GL_INT_2_10_10_10_REV();

  public int GL_UNSIGNED_INT_10F_11F_11F_REV();

  public int GL_UNSIGNED_SHORT_5_6_5_REV();

  public int GL_UNSIGNED_SHORT_4_4_4_4_REV();

  public int GL_UNSIGNED_SHORT_1_5_5_5_REV();

  public int GL_VERTEX_SHADER();

  public int GL_WRITE_ONLY();

  public int GL_UNIFORM_OFFSET();

  public int GL_UNIFORM_ARRAY_STRIDE();

  public int GL_UNIFORM_MATRIX_STRIDE();

  public int GL_PACK_ALIGNMENT();

  public int GL_STENCIL_INDEX();

  public int GL_TEXTURE_BUFFER();

  public int GL_R32F();

  public int GL_FRAMEBUFFER();

  public int GL_FRAMEBUFFER_COMPLETE();

  public int GL_FRAMEBUFFER_UNDEFINED();

  public int GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT();

  public int GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT();

  public int GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER();

  public int GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER();

  public int GL_FRAMEBUFFER_UNSUPPORTED();

  public int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE();

  public int GL_COLOR_ATTACHMENT0();

  public int GL_RENDERBUFFER();

  public int GL_STENCIL_INDEX8();

  public int GL_STENCIL_ATTACHMENT();

  public int GL_VERSION();

  public int GL_SHADING_LANGUAGE_VERSION();

  public int GL_VENDOR();

  public int GL_RENDERER();

  public int GL_MAX_VERTEX_ATTRIBS();

  public int GL_MAX_3D_TEXTURE_SIZE();

  public int GL_UNIFORM_BUFFER();

  // Non-core enums currently in use by CoreTexture2D
  public int GL_BITMAP();

  public int GL_COLOR_INDEX();

  public int GL_COMPRESSED_ALPHA();

  public int GL_COMPRESSED_LUMINANCE();

  public int GL_COMPRESSED_LUMINANCE_ALPHA();
  // --------------------------------------------- //

  // OpenGL methods
  public void glBindTexture(int target, int texture);

  public void glBlendFunc(int sfactor, int dfactor);

  public void glBlendFuncSeparate(int sfactorRGB, int dfactorRGB, int sfactorAlpha, int dfactorAlpha);

  public void glClear(int mask);

  public void glClearColor(float red, float green, float blue, float alpha);

  public void glDeleteTextures(int n, IntBuffer textures);

  public void glDisable(int cap);

  public void glDrawArrays(int mode, int first, int count);

  public void glDrawElements(int mode, int count, int type, int indices);

  public void glEnable(int cap);

  public void glGenTextures(int n, IntBuffer textures);

  public void glPixelStorei(int param, int n);

  public void glReadPixels(int x, int y, int width, int height, int format, int type, ByteBuffer pixelBuffer);

  public void glGetTexImage(int target, int level, int format, int type, ByteBuffer pixelBuffer);

  public int glGetError();

  public void glGetIntegerv(int pname, int[] params, int offset);

  public void glGetIntegerv(int pname, IntBuffer params);

  public int glGetInteger(int pname);

  public String glGetString(int pname);

  public boolean glIsEnabled(int cap);

  public void glTexImage2D(int target,
                           int level,
                           int internalformat,
                           int width,
                           int height,
                           int border,
                           int format,
                           int type,
                           ByteBuffer pixels);

  public void glTexImage2D(int target,
                           int level,
                           int internalformat,
                           int width,
                           int height,
                           int border,
                           int format,
                           int type,
                           DoubleBuffer pixels);

  public void glTexImage2D(int target,
                           int level,
                           int internalformat,
                           int width,
                           int height,
                           int border,
                           int format,
                           int type,
                           FloatBuffer pixels);

  public void glTexImage2D(int arget,
                           int level,
                           int internalformat,
                           int width,
                           int height,
                           int border,
                           int format,
                           int type,
                           IntBuffer pixels);

  public void glTexImage2D(int target,
                           int level,
                           int internalformat,
                           int width,
                           int height,
                           int border,
                           int format,
                           int type,
                           ShortBuffer pixels);

  public void glTexImage3D(int target,
                           int level,
                           int internalformat,
                           int width,
                           int height,
                           int depth,
                           int border,
                           int format,
                           int type,
                           ByteBuffer pixels);

  public void glTexImage3D(int target,
                           int level,
                           int internalformat,
                           int width,
                           int height,
                           int depth,
                           int border,
                           int format,
                           int type,
                           DoubleBuffer pixels);

  public void glTexImage3D(int target,
                           int level,
                           int internalformat,
                           int width,
                           int height,
                           int depth,
                           int border,
                           int format,
                           int type,
                           FloatBuffer pixels);

  public void glTexImage3D(int target,
                           int level,
                           int internalformat,
                           int width,
                           int height,
                           int depth,
                           int border,
                           int format,
                           int type,
                           IntBuffer pixels);

  public void glTexImage3D(int target,
                           int level,
                           int internalformat,
                           int width,
                           int height,
                           int depth,
                           int border,
                           int format,
                           int type,
                           ShortBuffer pixels);

  public void glTexParameterf(int target, int pname, float param);

  public void glTexParameteri(int target, int pname, int param);

  public void glTexSubImage2D(int target,
                              int level,
                              int xoffset,
                              int yoffset,
                              int width,
                              int height,
                              int format,
                              int type,
                              ByteBuffer pixels);

  public void glTexBuffer(int arg0, int arg1, int arg2);

  public void glViewport(int x, int y, int width, int height);

  public void glActiveTexture(int texture);

  public void glAttachShader(int program, int shader);

  public void glBindAttribLocation(int program, int index, String name);

  public void glBindBuffer(int target, int buffer);

  public void glBindVertexArray(int array);

  public void glVertexAttribDivisor(int index, int divisor);

  public void glBufferData(int target, IntBuffer data, int usage);

  public void glBufferData(int target, FloatBuffer data, int usage);

  public void glBufferData(int target, ShortBuffer data, int usage);

  public void glCompileShader(int shader);

  public int glCreateProgram();

  public int glCreateShader(int type);

  public void glDeleteBuffers(int n, IntBuffer buffers);

  public void glDeleteVertexArrays(int n, IntBuffer arrays);

  public void glDrawArraysInstanced(int mode, int first, int count, int primcount);

  public void glEnableVertexAttribArray(int index);

  public void glDisableVertexAttribArray(int index);

  public void glGenBuffers(int n, IntBuffer buffers);

  public void glGenerateMipmap(int target);

  public void glGenFramebuffers(int n, IntBuffer frameBuffs);

  public void glGenVertexArrays(int n, IntBuffer arrays);

  public int glGetAttribLocation(int program, String name);

  public void glGetProgramiv(int program, int pname, IntBuffer params);

  public String glGetProgramInfoLog(int program);

  public void glGetShaderiv(int shader, int pname, IntBuffer params);

  public String glGetShaderInfoLog(int shader);

  public int glGetUniformLocation(int program, String name);

  public void glGetUniformIndices(int program, String[] uniformNames, IntBuffer indexBuffer);

  public void glGetActiveUniforms(int program, int uniformCount, IntBuffer indices, int pname, IntBuffer params);

  public int glGetUniformBlockIndex(int program, String name);

  public void glLinkProgram(int program);

  public ByteBuffer glMapBuffer(int target, int access, long length, ByteBuffer oldBuffer);

  public void glPrimitiveRestartIndex(int index);

  public void glShaderSource(int shader, String string);

  public void glUniform1(int location, FloatBuffer values);

  public void glUniform1f(int location, float v0);

  public void glUniform2f(int location, float v0, float v1);

  public void glUniform3f(int location, float v0, float v1, float v2);

  public void glUniform4f(int location, float v0, float v1, float v2, float v3);

  public void glUniform1fv(int location, FloatBuffer ubuff);

  public void glUniform2fv(int location, FloatBuffer ubuff);

  public void glUniform3fv(int location, FloatBuffer ubuff);

  public void glUniform4fv(int location, FloatBuffer ubuff);

  public void glUniform1i(int location, int v0);

  public void glUniform2i(int location, int v0, int v1);

  public void glUniform3i(int location, int v0, int v1, int v2);

  public void glUniform4i(int location, int v0, int v1, int v2, int v3);

  public void glUniform1iv(int location, IntBuffer ubuff);

  public void glUniform2iv(int location, IntBuffer ubuff);

  public void glUniform3iv(int location, IntBuffer ubuff);

  public void glUniform4iv(int location, IntBuffer ubuff);

  public void glUniformMatrix2(int location, boolean transpose, FloatBuffer matrices);

  public void glUniformMatrix2x3(int location, boolean transpose, FloatBuffer matrices);

  public void glUniformMatrix2x4(int location, boolean transpose, FloatBuffer matrices);

  public void glUniformMatrix3(int location, boolean transpose, FloatBuffer matrices);

  public void glUniformMatrix3x2(int location, boolean transpose, FloatBuffer matrices);

  public void glUniformMatrix3x4(int location, boolean transpose, FloatBuffer matrices);

  public void glUniformMatrix4(int location, boolean transpose, FloatBuffer matrices);

  public void glUniformMatrix4x2(int location, boolean transpose, FloatBuffer matrices);

  public void glUniformMatrix4x3(int location, boolean transpose, FloatBuffer matrices);

  public void glUniformBlockBinding(int prog, int blockIndex, int blockBinding);

  public boolean glUnmapBuffer(int target);

  public void glUseProgram(int program);

  public void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, long offset);

  public void glVertexAttribIPointer(int index, int size, int type, int stride, int buffer);

  public int glCheckFramebufferStatus(int target);

  public void glBindFramebuffer(int target, int fbo);

  public void glDeleteFramebuffers(int fboCount, IntBuffer fboIds);

  public void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level);

  public void glFramebufferTextureLayer(int target, int attachment, int texture, int level, int layer);

  public void glDrawBuffer(int mode);

  public void glGenRenderBuffers(int buffCount, IntBuffer buffer);

  public void glBindRenderbuffer(int target, int renderBuffer);

  public void glRenderbufferStorage(int target, int internalFormat, int width, int height);

  public void glFramebufferRenderbuffer(int target, int attachment, int renderBufferTarget, int renderBuffer);

  public void glBindBufferBase(int target, int bindingPoint, int id);

  public void glPointSize(int psize);

  public void glBlendEquationSeparate(int e1, int e2);

  public void checkGLError();

  public void checkGLError(String msg);

  public void checkGLError(String msg, boolean throwException);

  public void setErrorChecksEnabled(boolean enabled);

  public CoreUtil getUtil();
}
