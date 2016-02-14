package de.lessvoid.coregl.jogl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GL2GL3;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GLContext;

import de.lessvoid.coregl.CoreLogger;
import de.lessvoid.coregl.spi.CoreGL;
import de.lessvoid.coregl.spi.CoreUtil;

/**
 * @author Aaron Mahan &lt;aaron@forerunnergames.com&gt;
 * @author Brian Groenke (groenke.5@osu.edu)
 */
public class JoglCoreGL implements CoreGL {

  private static CoreLogger log = CoreLogger.getCoreLogger(JoglCoreGL.class);

  private final CoreUtil util = new JoglCoreUtil();

  @Override
  public int GL_ALPHA() {
    return GL.GL_ALPHA;
  }

  @Override
  public int GL_ALPHA_TEST() {
    return GL2ES1.GL_ALPHA_TEST;
  }

  @Override
  public int GL_ALWAYS() {
    return GL.GL_ALWAYS;
  }

  @Override
  public int GL_BLEND() {
    return GL.GL_BLEND;
  }

  @Override
  public int GL_BLEND_DST() {
    return GL.GL_BLEND_DST;
  }

  @Override
  public int GL_BLEND_SRC() {
    return GL.GL_BLEND_SRC;
  }

  @Override
  public int GL_BYTE() {
    return GL.GL_BYTE;
  }

  @Override
  public int GL_COLOR_BUFFER_BIT() {
    return GL.GL_COLOR_BUFFER_BIT;
  }

  @Override
  public int GL_DEPTH_BUFFER_BIT() {
    return GL.GL_DEPTH_BUFFER_BIT;
  }

  @Override
  public int GL_CULL_FACE() {
    return GL.GL_CULL_FACE;
  }

  @Override
  public int GL_DEPTH_TEST() {
    return GL.GL_DEPTH_TEST;
  }

  @Override
  public int GL_DST_COLOR() {
    return GL.GL_DST_COLOR;
  }

  @Override
  public int GL_FALSE() {
    return GL.GL_FALSE;
  }

  @Override
  public int GL_FLOAT() {
    return GL.GL_FLOAT;
  }

  @Override
  public int GL_INVALID_ENUM() {
    return GL.GL_INVALID_ENUM;
  }

  @Override
  public int GL_INVALID_OPERATION() {
    return GL.GL_INVALID_OPERATION;
  }

  @Override
  public int GL_INVALID_VALUE() {
    return GL.GL_INVALID_VALUE;
  }

  @Override
  public int GL_KEEP() {
    return GL.GL_KEEP;
  }

  @Override
  public int GL_LINEAR() {
    return GL.GL_LINEAR;
  }

  @Override
  public int GL_LINEAR_MIPMAP_LINEAR() {
    return GL.GL_LINEAR_MIPMAP_LINEAR;
  }

  @Override
  public int GL_LINEAR_MIPMAP_NEAREST() {
    return GL.GL_LINEAR_MIPMAP_NEAREST;
  }

  @Override
  public int GL_LUMINANCE() {
    return GL.GL_LUMINANCE;
  }

  @Override
  public int GL_LUMINANCE_ALPHA() {
    return GL.GL_LUMINANCE_ALPHA;
  }

  @Override
  public int GL_MAX_TEXTURE_SIZE() {
    return GL.GL_MAX_TEXTURE_SIZE;
  }

  @Override
  public int GL_NEAREST() {
    return GL.GL_NEAREST;
  }

  @Override
  public int GL_NEAREST_MIPMAP_LINEAR() {
    return GL.GL_NEAREST_MIPMAP_LINEAR;
  }

  @Override
  public int GL_NEAREST_MIPMAP_NEAREST() {
    return GL.GL_NEAREST_MIPMAP_NEAREST;
  }

  @Override
  public int GL_NEVER() {
    return GL.GL_NEVER;
  }

  @Override
  public int GL_NO_ERROR() {
    return GL.GL_NO_ERROR;
  }

  @Override
  public int GL_NOTEQUAL() {
    return GL.GL_NOTEQUAL;
  }

  @Override
  public int GL_ONE_MINUS_SRC_ALPHA() {
    return GL.GL_ONE_MINUS_SRC_ALPHA;
  }

  @Override
  public int GL_OUT_OF_MEMORY() {
    return GL.GL_OUT_OF_MEMORY;
  }

  @Override
  public int GL_POINTS() {
    return GL.GL_POINTS;
  }

  @Override
  public int GL_RGB() {
    return GL.GL_RGB;
  }

  @Override
  public int GL_RGBA() {
    return GL.GL_RGBA;
  }

  @Override
  public int GL_SHORT() {
    return GL.GL_SHORT;
  }

  @Override
  public int GL_SRC_ALPHA() {
    return GL.GL_SRC_ALPHA;
  }

  @Override
  public int GL_STACK_OVERFLOW() {
    return GL2ES2.GL_STACK_OVERFLOW;
  }

  @Override
  public int GL_STACK_UNDERFLOW() {
    return GL2ES2.GL_STACK_UNDERFLOW;
  }

  @Override
  public int GL_TEXTURE_2D() {
    return GL.GL_TEXTURE_2D;
  }

  @Override
  public int GL_TEXTURE_2D_ARRAY() {
    return GL2ES3.GL_TEXTURE_2D_ARRAY;
  }

  @Override
  public int GL_TEXTURE_BINDING_2D() {
    return GL.GL_TEXTURE_BINDING_2D;
  }

  @Override
  public int GL_TEXTURE_3D() {
    return GL2ES2.GL_TEXTURE_3D;
  }

  @Override
  public int GL_TEXTURE_BINDING_3D() {
    return GL2ES2.GL_TEXTURE_BINDING_3D;
  }

  @Override
  public int GL_TEXTURE_MAG_FILTER() {
    return GL.GL_TEXTURE_MAG_FILTER;
  }

  @Override
  public int GL_TEXTURE_MIN_FILTER() {
    return GL.GL_TEXTURE_MIN_FILTER;
  }

  @Override
  public int GL_TRIANGLES() {
    return GL.GL_TRIANGLES;
  }

  @Override
  public int GL_TRIANGLE_STRIP() {
    return GL.GL_TRIANGLE_STRIP;
  }

  @Override
  public int GL_TRIANGLE_FAN() {
    return GL.GL_TRIANGLE_FAN;
  }

  @Override
  public int GL_TRUE() {
    return GL.GL_TRUE;
  }

  @Override
  public int GL_UNSIGNED_BYTE() {
    return GL.GL_UNSIGNED_BYTE;
  }

  @Override
  public int GL_UNSIGNED_SHORT() {
    return GL.GL_UNSIGNED_SHORT;
  }

  @Override
  public int GL_UNSIGNED_SHORT_4_4_4_4() {
    return GL.GL_UNSIGNED_SHORT_4_4_4_4;
  }

  @Override
  public int GL_UNSIGNED_SHORT_5_5_5_1() {
    return GL.GL_UNSIGNED_SHORT_5_5_5_1;
  }

  @Override
  public int GL_UNSIGNED_SHORT_5_6_5() {
    return GL.GL_UNSIGNED_SHORT_5_6_5;
  }

  @Override
  public int GL_VIEWPORT() {
    return GL.GL_VIEWPORT;
  }

  @Override
  public int GL_ZERO() {
    return GL.GL_ZERO;
  }

  @Override
  public int GL_ONE() {
    return GL.GL_ONE;
  }

  @Override
  public int GL_ONE_MINUS_DST_ALPHA() {
    return GL.GL_ONE_MINUS_DST_ALPHA;
  }

  @Override
  public int GL_DST_ALPHA() {
    return GL.GL_DST_ALPHA;
  }

  @Override
  public int GL_MAX() {
    return GL2ES3.GL_MAX;
  }

  @Override
  public int GL_FUNC_ADD() {
    return GL.GL_FUNC_ADD;
  }

  @Override
  public int GL_ACTIVE_TEXTURE() {
    return GL.GL_ACTIVE_TEXTURE;
  }

  @Override
  public int GL_ARRAY_BUFFER() {
    return GL.GL_ARRAY_BUFFER;
  }

  @Override
  public int GL_BACK() {
    return GL.GL_BACK;
  }

  @Override
  public int GL_BGR() {
    return GL.GL_BGR;
  }

  @Override
  public int GL_BGRA() {
    return GL.GL_BGRA;
  }

  @Override
  public int GL_BLUE() {
    return GL2ES3.GL_BLUE;
  }

  @Override
  public int GL_COMPILE_STATUS() {
    return GL2ES2.GL_COMPILE_STATUS;
  }

  @Override
  public int GL_COMPRESSED_RGB() {
    return GL2GL3.GL_COMPRESSED_RGB;
  }

  @Override
  public int GL_COMPRESSED_RGBA() {
    return GL2GL3.GL_COMPRESSED_RGBA;
  }

  @Override
  public int GL_CURRENT_PROGRAM() {
    return GL2ES2.GL_CURRENT_PROGRAM;
  }

  @Override
  public int GL_DECR() {
    return GL.GL_DECR;
  }

  @Override
  public int GL_DECR_WRAP() {
    return GL.GL_DECR_WRAP;
  }

  @Override
  public int GL_DYNAMIC_DRAW() {
    return GL.GL_DYNAMIC_DRAW;
  }

  @Override
  public int GL_ELEMENT_ARRAY_BUFFER() {
    return GL.GL_ELEMENT_ARRAY_BUFFER;
  }

  @Override
  public int GL_EQUAL() {
    return GL.GL_EQUAL;
  }

  @Override
  public int GL_FRAGMENT_SHADER() {
    return GL2ES2.GL_FRAGMENT_SHADER;
  }

  @Override
  public int GL_GEOMETRY_SHADER() {
    return GL3.GL_GEOMETRY_SHADER;
  }

  @Override
  public int GL_GEQUAL() {
    return GL.GL_GEQUAL;
  }

  @Override
  public int GL_GREATER() {
    return GL.GL_GREATER;
  }

  @Override
  public int GL_GREEN() {
    return GL2ES3.GL_GREEN;
  }

  @Override
  public int GL_INT() {
    return GL2ES2.GL_INT;
  }

  @Override
  public int GL_LINK_STATUS() {
    return GL2ES2.GL_LINK_STATUS;
  }

  @Override
  public int GL_PRIMITIVE_RESTART() {
    return GL2GL3.GL_PRIMITIVE_RESTART;
  }

  @Override
  public int GL_PRIMITIVE_RESTART_INDEX() {
    return GL2GL3.GL_PRIMITIVE_RESTART_INDEX;
  }

  @Override
  public int GL_RED() {
    return GL2ES2.GL_RED;
  }

  @Override
  public int GL_STATIC_DRAW() {
    return GL.GL_STATIC_DRAW;
  }

  @Override
  public int GL_STREAM_DRAW() {
    return GL2ES2.GL_STREAM_DRAW;
  }

  @Override
  public int GL_TEXTURE0() {
    return GL.GL_TEXTURE0;
  }

  @Override
  public int GL_TEXTURE_CUBE_MAP_NEGATIVE_X() {
    return GL.GL_TEXTURE_CUBE_MAP_NEGATIVE_X;
  }

  @Override
  public int GL_TEXTURE_CUBE_MAP_NEGATIVE_Y() {
    return GL.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y;
  }

  @Override
  public int GL_TEXTURE_CUBE_MAP_NEGATIVE_Z() {
    return GL.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z;
  }

  @Override
  public int GL_TEXTURE_CUBE_MAP_POSITIVE_X() {
    return GL.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
  }

  @Override
  public int GL_TEXTURE_CUBE_MAP_POSITIVE_Y() {
    return GL.GL_TEXTURE_CUBE_MAP_POSITIVE_Y;
  }

  @Override
  public int GL_TEXTURE_CUBE_MAP_POSITIVE_Z() {
    return GL.GL_TEXTURE_CUBE_MAP_POSITIVE_Z;
  }

  @Override
  public int GL_UNSIGNED_BYTE_2_3_3_REV() {
    return GL2GL3.GL_UNSIGNED_BYTE_2_3_3_REV;
  }

  @Override
  public int GL_UNSIGNED_BYTE_3_3_2() {
    return GL2GL3.GL_UNSIGNED_BYTE_3_3_2;
  }

  @Override
  public int GL_UNSIGNED_INT() {
    return GL.GL_UNSIGNED_INT;
  }

  @Override
  public int GL_UNSIGNED_INT_10_10_10_2() {
    return GL2ES2.GL_UNSIGNED_INT_10_10_10_2;
  }

  @Override
  public int GL_UNSIGNED_INT_2_10_10_10_REV() {
    return GL2ES2.GL_UNSIGNED_INT_2_10_10_10_REV;
  }

  @Override
  public int GL_UNSIGNED_INT_8_8_8_8() {
    return GL2GL3.GL_UNSIGNED_INT_8_8_8_8;
  }

  @Override
  public int GL_UNSIGNED_INT_8_8_8_8_REV() {
    return GL2GL3.GL_UNSIGNED_INT_8_8_8_8_REV;
  }

  @Override
  public int GL_HALF_FLOAT() {
    return GL.GL_HALF_FLOAT;
  }

  @Override
  public int GL_LESS() {
    return GL.GL_LESS;
  }

  @Override
  public int GL_LEQUAL() {
    return GL.GL_LEQUAL;
  }

  @Override
  public int GL_INCR() {
    return GL.GL_INCR;
  }

  @Override
  public int GL_INCR_WRAP() {
    return GL.GL_INCR_WRAP;
  }

  @Override
  public int GL_DOUBLE() {
    return GL2GL3.GL_DOUBLE;
  }

  @Override
  public int GL_FIXED() {
    return GL.GL_FIXED;
  }

  @Override
  public int GL_INT_2_10_10_10_REV() {
    return GL3ES3.GL_INT_2_10_10_10_REV;
  }

  @Override
  public int GL_INVERT() {
    return GL.GL_INVERT;
  }

  @Override
  public int GL_UNSIGNED_INT_10F_11F_11F_REV() {
    return GL.GL_UNSIGNED_INT_10F_11F_11F_REV;
  }

  @Override
  public int GL_UNSIGNED_SHORT_5_6_5_REV() {
    return GL2GL3.GL_UNSIGNED_SHORT_5_6_5_REV;
  }

  @Override
  public int GL_UNSIGNED_SHORT_4_4_4_4_REV() {
    return GL2GL3.GL_UNSIGNED_SHORT_4_4_4_4_REV;
  }

  @Override
  public int GL_UNSIGNED_SHORT_1_5_5_5_REV() {
    return GL2GL3.GL_UNSIGNED_SHORT_1_5_5_5_REV;
  }

  @Override
  public int GL_VERTEX_SHADER() {
    return GL2ES2.GL_VERTEX_SHADER;
  }

  @Override
  public int GL_WRITE_ONLY() {
    return GL.GL_WRITE_ONLY;
  }

  @Override
  public int GL_UNIFORM_OFFSET() {
    return GL2ES3.GL_UNIFORM_OFFSET;
  }

  @Override
  public int GL_UNIFORM_ARRAY_STRIDE() {
    return GL2ES3.GL_UNIFORM_ARRAY_STRIDE;
  }

  @Override
  public int GL_UNIFORM_MATRIX_STRIDE() {
    return GL2ES3.GL_UNIFORM_MATRIX_STRIDE;
  }

  @Override
  public int GL_LINE_STRIP() {
    return GL.GL_LINE_STRIP;
  }

  @Override
  public int GL_LINE_STRIP_ADJACENCY() {
    return GL3.GL_LINE_STRIP_ADJACENCY;
  }

  @Override
  public int GL_PACK_ALIGNMENT() {
    return GL.GL_PACK_ALIGNMENT;
  }

  @Override
  public int GL_STENCIL_INDEX() {
    return GL2ES2.GL_STENCIL_INDEX;
  }

  @Override
  public int GL_TEXTURE_BUFFER() {
    return GL2GL3.GL_TEXTURE_BUFFER;
  }

  @Override
  public int GL_R32F() {
    return GL.GL_R32F;
  }

  @Override
  public int GL_FRAMEBUFFER() {
    return GL.GL_FRAMEBUFFER;
  }

  @Override
  public int GL_FRAMEBUFFER_COMPLETE() {
    return GL.GL_FRAMEBUFFER_COMPLETE;
  }

  @Override
  public int GL_FRAMEBUFFER_UNDEFINED() {
    return GL2ES3.GL_FRAMEBUFFER_UNDEFINED;
  }

  @Override
  public int GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT() {
    return GL.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT;
  }

  @Override
  public int GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT() {
    return GL.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT;
  }

  @Override
  public int GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER() {
    return GL2GL3.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER;
  }

  @Override
  public int GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER() {
    return GL2GL3.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER;
  }

  @Override
  public int GL_FRAMEBUFFER_UNSUPPORTED() {
    return GL.GL_FRAMEBUFFER_UNSUPPORTED;
  }

  @Override
  public int GL_FRONT() {
    return GL.GL_FRONT;
  }

  @Override
  public int GL_FRONT_AND_BACK() {
    return GL.GL_FRONT_AND_BACK;
  }

  @Override
  public int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE() {
    return GL.GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE;
  }

  @Override
  public int GL_COLOR_ATTACHMENT0() {
    return GL.GL_COLOR_ATTACHMENT0;
  }

  @Override
  public int GL_RENDERBUFFER() {
    return GL.GL_RENDERBUFFER;
  }

  @Override
  public int GL_STENCIL_INDEX8() {
    return GL.GL_STENCIL_INDEX8;
  }

  @Override
  public int GL_STENCIL_TEST() {
    return GL.GL_STENCIL_TEST;
  }

  @Override
  public int GL_STENCIL_ATTACHMENT() {
    return GL.GL_STENCIL_ATTACHMENT;
  }

  @Override
  public int GL_STENCIL_BUFFER_BIT() {
    return GL.GL_STENCIL_BUFFER_BIT;
  }

  @Override
  public int GL_VERSION() {
    return GL.GL_VERSION;
  }

  @Override
  public int GL_SHADING_LANGUAGE_VERSION() {
    return GL2ES2.GL_SHADING_LANGUAGE_VERSION;
  }

  @Override
  public int GL_VENDOR() {
    return GL.GL_VENDOR;
  }

  @Override
  public int GL_RENDERER() {
    return GL.GL_RENDERER;
  }

  @Override
  public int GL_REPLACE() {
    return GL.GL_REPLACE;
  }

  @Override
  public int GL_MAX_VERTEX_ATTRIBS() {
    return GL2ES2.GL_MAX_VERTEX_ATTRIBS;
  }

  @Override
  public int GL_MAX_3D_TEXTURE_SIZE() {
    return GL2ES2.GL_MAX_3D_TEXTURE_SIZE;
  }

  @Override
  public int GL_UNIFORM_BUFFER() {
    return GL2ES3.GL_UNIFORM_BUFFER;
  }

  // Non-core //
  @Override
  public int GL_BITMAP() {
    return GL2.GL_BITMAP;
  }

  @Override
  public int GL_COLOR_INDEX() {
    return GL2.GL_COLOR_INDEX;
  }

  @Override
  public int GL_COMPRESSED_ALPHA() {
    return GL2.GL_COMPRESSED_ALPHA;
  }

  @Override
  public int GL_COMPRESSED_LUMINANCE() {
    return GL2.GL_COMPRESSED_LUMINANCE;
  }

  @Override
  public int GL_COMPRESSED_LUMINANCE_ALPHA() {
    return GL2.GL_COMPRESSED_ALPHA;
  }
  // -------- //

  @Override
  public void glBindTexture(final int target, final int texture) {
    GLContext.getCurrentGL().glBindTexture(target, texture);
  }

  @Override
  public void glBlendFunc(final int sfactor, final int dfactor) {
    GLContext.getCurrentGL().glBlendFunc(sfactor, dfactor);
  }

  @Override
  public void glBlendFuncSeparate(final int sfactorRGB,
                                  final int dfactorRGB,
                                  final int sfactorAlpha,
                                  final int dfactorAlpha) {
    GLContext.getCurrentGL().glBlendFuncSeparate(sfactorRGB, dfactorRGB, sfactorAlpha, sfactorAlpha);
  }

  @Override
  public void glClear(final int mask) {
    GLContext.getCurrentGL().glClear(mask);
  }

  @Override
  public void glClearColor(final float red, final float green, final float blue, final float alpha) {
    GLContext.getCurrentGL().glClearColor(red, green, blue, alpha);
  }

  @Override
  public void glDeleteTextures(final int n, final IntBuffer textures) {
    GLContext.getCurrentGL().glDeleteTextures(n, textures);
  }

  @Override
  public void glDisable(final int cap) {
    GLContext.getCurrentGL().glDisable(cap);
  }

  @Override
  public void glDrawArrays(final int mode, final int first, final int count) {
    GLContext.getCurrentGL().glDrawArrays(mode, first, count);
  }

  @Override
  public void glDrawElements(final int mode, final int count, final int type, final int indices) {
    GLContext.getCurrentGL().glDrawElements(mode, count, type, indices);
  }

  @Override
  public void glEnable(final int cap) {
    GLContext.getCurrentGL().glEnable(cap);
  }

  @Override
  public void glGenTextures(final int n, final IntBuffer textures) {
    GLContext.getCurrentGL().glGenTextures(n, textures);
  }

  @Override
  public int glGetError() {
    return GLContext.getCurrentGL().glGetError();
  }

  @Override
  public void glGetIntegerv(final int pname, final int[] params, final int offset) {
    GLContext.getCurrentGL().glGetIntegerv(pname, params, offset);
  }

  @Override
  public void glGetIntegerv(final int pname, final IntBuffer params) {
    GLContext.getCurrentGL().glGetIntegerv(pname, params);
  }

  @Override
  public int glGetInteger(final int pname) {
    return JoglCoreUtil.glGetInteger(GLContext.getCurrentGL(), pname);
  }

  @Override
  public String glGetString(final int pname) {
    return GLContext.getCurrentGL().glGetString(pname);
  }

  @Override
  public boolean glIsEnabled(final int cap) {
    return GLContext.getCurrentGL().glIsEnabled(cap);
  }

  @Override
  public void glTexImage2D(final int target,
                           final int level,
                           final int internalformat,
                           final int width,
                           final int height,
                           final int border,
                           final int format,
                           final int type,
                           final ByteBuffer pixels) {
    GLContext.getCurrentGL().glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
  }

  @Override
  public void glTexImage2D(final int target,
                           final int level,
                           final int internalformat,
                           final int width,
                           final int height,
                           final int border,
                           final int format,
                           final int type,
                           final DoubleBuffer pixels) {
    GLContext.getCurrentGL().glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
  }

  @Override
  public void glTexImage2D(final int target,
                           final int level,
                           final int internalformat,
                           final int width,
                           final int height,
                           final int border,
                           final int format,
                           final int type,
                           final FloatBuffer pixels) {
    GLContext.getCurrentGL().glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
  }

  @Override
  public void glTexImage2D(final int target,
                           final int level,
                           final int internalformat,
                           final int width,
                           final int height,
                           final int border,
                           final int format,
                           final int type,
                           final IntBuffer pixels) {
    GLContext.getCurrentGL().glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
  }

  @Override
  public void glTexImage2D(final int target,
                           final int level,
                           final int internalformat,
                           final int width,
                           final int height,
                           final int border,
                           final int format,
                           final int type,
                           final ShortBuffer pixels) {
    GLContext.getCurrentGL().glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
  }

  @Override
  public void glTexImage3D(final int target,
                           final int level,
                           final int internalformat,
                           final int width,
                           final int height,
                           final int depth,
                           final int border,
                           final int format,
                           final int type,
                           final ByteBuffer pixels) {
    GLContext.getCurrentGL().getGL2ES2()
        .glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
  }

  @Override
  public void glTexImage3D(final int target,
                           final int level,
                           final int internalformat,
                           final int width,
                           final int height,
                           final int depth,
                           final int border,
                           final int format,
                           final int type,
                           final DoubleBuffer pixels) {
    GLContext.getCurrentGL().getGL2ES2()
        .glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
  }

  @Override
  public void glTexImage3D(final int target,
                           final int level,
                           final int internalformat,
                           final int width,
                           final int height,
                           final int depth,
                           final int border,
                           final int format,
                           final int type,
                           final FloatBuffer pixels) {
    GLContext.getCurrentGL().getGL2ES2()
        .glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
  }

  @Override
  public void glTexImage3D(final int target,
                           final int level,
                           final int internalformat,
                           final int width,
                           final int height,
                           final int depth,
                           final int border,
                           final int format,
                           final int type,
                           final IntBuffer pixels) {
    GLContext.getCurrentGL().getGL2ES2()
        .glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
  }

  @Override
  public void glTexImage3D(final int target,
                           final int level,
                           final int internalformat,
                           final int width,
                           final int height,
                           final int depth,
                           final int border,
                           final int format,
                           final int type,
                           final ShortBuffer pixels) {
    GLContext.getCurrentGL().getGL2ES2()
        .glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
  }

  @Override
  public void glTexParameterf(final int target, final int pname, final float param) {
    GLContext.getCurrentGL().glTexParameterf(target, pname, param);
  }

  @Override
  public void glTexParameteri(final int target, final int pname, final int param) {
    GLContext.getCurrentGL().glTexParameteri(target, pname, param);
  }

  @Override
  public void glTexSubImage2D(final int target,
                              final int level,
                              final int xoffset,
                              final int yoffset,
                              final int width,
                              final int height,
                              final int format,
                              final int type,
                              final ByteBuffer pixels) {
    GLContext.getCurrentGL().glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
  }

  @Override
  public void glTexBuffer(final int arg0, final int arg1, final int arg2) {
    GLContext.getCurrentGL().getGL2GL3().glTexBuffer(arg0, arg1, arg2);
  }

  @Override
  public void glViewport(final int x, final int y, final int width, final int height) {
    GLContext.getCurrentGL().glViewport(x, y, width, height);
  }

  @Override
  public void glActiveTexture(final int texture) {
    GLContext.getCurrentGL().glActiveTexture(texture);
  }

  @Override
  public void glAttachShader(final int program, final int shader) {
    GLContext.getCurrentGL().getGL2ES2().glAttachShader(program, shader);
  }

  @Override
  public void glBindAttribLocation(final int program, final int index, final String name) {
    GLContext.getCurrentGL().getGL2ES2().glBindAttribLocation(program, index, name);
  }

  @Override
  public void glBindBuffer(final int target, final int buffer) {
    GLContext.getCurrentGL().glBindBuffer(target, buffer);
  }

  @Override
  public void glBindVertexArray(final int array) {
    GLContext.getCurrentGL().getGL2ES3().glBindVertexArray(array);
  }

  @Override
  public void glBufferData(final int target, final IntBuffer data, final int usage) {
    GLContext.getCurrentGL().glBufferData(target, data.remaining() * 4, data, usage);
  }

  @Override
  public void glBufferData(final int target, final FloatBuffer data, final int usage) {
    GLContext.getCurrentGL().glBufferData(target, data.remaining() * 4, data, usage);
  }

  @Override
  public void glBufferData(final int target, final ShortBuffer data, final int usage) {
    GLContext.getCurrentGL().glBufferData(target, data.remaining() * 2, data, usage);
  }

  @Override
  public void glCompileShader(final int shader) {
    GLContext.getCurrentGL().getGL2ES2().glCompileShader(shader);
  }

  @Override
  public void glColorMask(final boolean red, final boolean green, final boolean blue, final boolean alpha) {
    GLContext.getCurrentGL().glColorMask(red, green, blue, alpha);
  }

  @Override
  public int glCreateProgram() {
    return GLContext.getCurrentGL().getGL2ES2().glCreateProgram();
  }

  @Override
  public int glCreateShader(final int type) {
    return GLContext.getCurrentGL().getGL2ES2().glCreateShader(type);
  }

  @Override
  public void glDepthMask(final boolean flag) {
    GLContext.getCurrentGL().glDepthMask(flag);
  }

  @Override
  public void glDeleteBuffers(final int n, final IntBuffer buffers) {
    GLContext.getCurrentGL().glDeleteBuffers(n, buffers);
  }

  @Override
  public void glDeleteVertexArrays(final int n, final IntBuffer arrays) {
    GLContext.getCurrentGL().getGL2ES3().glDeleteVertexArrays(n, arrays);
  }

  @Override
  public void glDrawArraysInstanced(final int mode, final int first, final int count, final int primcount) {
    GLContext.getCurrentGL().getGL2ES3().glDrawArraysInstanced(mode, first, count, primcount);
  }

  @Override
  public void glEnableVertexAttribArray(final int index) {
    GLContext.getCurrentGL().getGL2ES2().glEnableVertexAttribArray(index);
  }

  @Override
  public void glDisableVertexAttribArray(final int index) {
    GLContext.getCurrentGL().getGL2ES2().glDisableVertexAttribArray(index);
  }

  @Override
  public void glVertexAttribDivisor(final int index, final int divisor) {
    GLContext.getCurrentGL().getGL3ES3().glVertexAttribDivisor(index, divisor);
  }

  @Override
  public void glVertexAttribIPointer(final int index,
                                     final int size,
                                     final int type,
                                     final int stride,
                                     final int buffer) {
    GLContext.getCurrentGL().getGL2ES3().glVertexAttribIPointer(index, size, type, stride, buffer);
  }

  @Override
  public void glGenBuffers(final int n, final IntBuffer buffers) {
    GLContext.getCurrentGL().glGenBuffers(n, buffers);
  }

  @Override
  public void glGenerateMipmap(final int target) {
    GLContext.getCurrentGL().glGenerateMipmap(target);
  }

  @Override
  public void glGenVertexArrays(final int n, final IntBuffer arrays) {
    GLContext.getCurrentGL().getGL2ES3().glGenVertexArrays(n, arrays);
  }

  @Override
  public int glGetAttribLocation(final int program, final String name) {
    return GLContext.getCurrentGL().getGL2ES2().glGetAttribLocation(program, name);
  }

  @Override
  public void glGetProgramiv(final int program, final int pname, final IntBuffer params) {
    GLContext.getCurrentGL().getGL2ES2().glGetProgramiv(program, pname, params);
  }

  @Override
  public String glGetProgramInfoLog(final int program) {
    final int[] logLength = new int[1];
    GLContext.getCurrentGL().getGL2ES2().glGetProgramiv(program, GL2ES2.GL_INFO_LOG_LENGTH, logLength, 0);
    final byte[] log = new byte[logLength[0]];
    GLContext.getCurrentGL().getGL2ES2().glGetShaderInfoLog(program, logLength[0], null, 0, log, 0);
    return new String(log);
  }

  @Override
  public void glGetShaderiv(final int shader, final int pname, final IntBuffer params) {
    GLContext.getCurrentGL().getGL2ES2().glGetShaderiv(shader, pname, params);
  }

  @Override
  public String glGetShaderInfoLog(final int shader) {
    final int[] logLength = new int[1];
    GLContext.getCurrentGL().getGL2ES2().glGetShaderiv(shader, GL2ES2.GL_INFO_LOG_LENGTH, logLength, 0);
    String logStr = "";
    if (logLength[0] > 1) {
      final byte[] log = new byte[logLength[0]];
      GLContext.getCurrentGL().getGL2ES2().glGetShaderInfoLog(shader, logLength[0], null, 0, log, 0);
      logStr = new String(log);
    }
    return logStr;
  }

  @Override
  public int glGetUniformLocation(final int program, final String name) {
    return GLContext.getCurrentGL().getGL2ES2().glGetUniformLocation(program, name);
  }

  @Override
  public void glLinkProgram(final int program) {
    GLContext.getCurrentGL().getGL2ES2().glLinkProgram(program);
  }

  @Override
  public ByteBuffer glMapBuffer(final int target, final int access, final long length, final ByteBuffer oldBuffer) {
    return GLContext.getCurrentGL().glMapBuffer(target, access);
  }

  @Override
  public void glPrimitiveRestartIndex(final int index) {
    GLContext.getCurrentGL().getGL2GL3().glPrimitiveRestartIndex(index);
  }

  @Override
  public void glShaderSource(final int shader, final String string) {
    final String[] sources = new String[] { string };
    final int[] sourceLengths = new int[] { sources[0].length() };
    GLContext.getCurrentGL().getGL2ES2().glShaderSource(shader, sources.length, sources, sourceLengths, 0);
  }

  @Override
  public void glStencilFunc(final int func, final int ref, final int mask) {
    GLContext.getCurrentGL().glStencilFunc(func, ref, mask);
  }

  @Override
  public void glStencilFuncSeparate(final int face, final int func, final int ref, final int mask) {
    GLContext.getCurrentGL().getGL2ES2().glStencilFuncSeparate(face, func, ref, mask);
  }

  @Override
  public void glStencilMask(final int mask) {
    GLContext.getCurrentGL().glStencilMask(mask);
  }

  @Override
  public void glStencilMaskSeparate(final int face, final int mask) {
    GLContext.getCurrentGL().getGL2ES2().glStencilMaskSeparate(face, mask);
  }

  @Override
  public void glStencilOp(final int sfail, final int dpfail, final int dppass) {
    GLContext.getCurrentGL().glStencilOp(sfail, dpfail, dppass);
  }

  @Override
  public void glStencilOpSeparate(final int face, final int sfail, final int dpfail, final int dppass) {
    GLContext.getCurrentGL().getGL2ES2().glStencilOpSeparate(face, sfail, dpfail, dppass);
  }

  @Override
  public void glUniform1(final int location, final FloatBuffer values) {
    GLContext.getCurrentGL().getGL2ES2().glUniform1fv(location, values.remaining(), values);
  }

  @Override
  public void glUniform1f(final int location, final float v0) {
    GLContext.getCurrentGL().getGL2ES2().glUniform1f(location, v0);
  }

  @Override
  public void glUniform2f(final int location, final float v0, final float v1) {
    GLContext.getCurrentGL().getGL2ES2().glUniform2f(location, v0, v1);
  }

  @Override
  public void glUniform3f(final int location, final float v0, final float v1, final float v2) {
    GLContext.getCurrentGL().getGL2ES2().glUniform3f(location, v0, v1, v2);
  }

  @Override
  public void glUniform4f(final int location, final float v0, final float v1, final float v2, final float v3) {
    GLContext.getCurrentGL().getGL2ES2().glUniform4f(location, v0, v1, v2, v3);
  }

  @Override
  public void glUniform1fv(final int location, final FloatBuffer ubuff) {
    GLContext.getCurrentGL().getGL2ES2().glUniform1fv(location, ubuff.remaining(), ubuff);
  }

  @Override
  public void glUniform2fv(final int location, final FloatBuffer ubuff) {
    GLContext.getCurrentGL().getGL2ES2().glUniform2fv(location, ubuff.remaining() / 2, ubuff);
  }

  @Override
  public void glUniform3fv(final int location, final FloatBuffer ubuff) {
    GLContext.getCurrentGL().getGL2ES2().glUniform3fv(location, ubuff.remaining() / 3, ubuff);
  }

  @Override
  public void glUniform4fv(final int location, final FloatBuffer ubuff) {
    GLContext.getCurrentGL().getGL2ES2().glUniform4fv(location, ubuff.remaining() / 4, ubuff);
  }

  @Override
  public void glUniform1i(final int location, final int v0) {
    GLContext.getCurrentGL().getGL2ES2().glUniform1i(location, v0);
  }

  @Override
  public void glUniform2i(final int location, final int v0, final int v1) {
    GLContext.getCurrentGL().getGL2ES2().glUniform2i(location, v0, v1);
  }

  @Override
  public void glUniform3i(final int location, final int v0, final int v1, final int v2) {
    GLContext.getCurrentGL().getGL2ES2().glUniform3i(location, v0, v1, v2);
  }

  @Override
  public void glUniform4i(final int location, final int v0, final int v1, final int v2, final int v3) {
    GLContext.getCurrentGL().getGL2ES2().glUniform4i(location, v0, v1, v2, v3);
  }

  @Override
  public void glUniform1iv(final int location, final IntBuffer ubuff) {
    GLContext.getCurrentGL().getGL2ES2().glUniform1iv(location, ubuff.remaining(), ubuff);
  }

  @Override
  public void glUniform2iv(final int location, final IntBuffer ubuff) {
    GLContext.getCurrentGL().getGL2ES2().glUniform2iv(location, ubuff.remaining() / 2, ubuff);
  }

  @Override
  public void glUniform3iv(final int location, final IntBuffer ubuff) {
    GLContext.getCurrentGL().getGL2ES2().glUniform3iv(location, ubuff.remaining() / 3, ubuff);
  }

  @Override
  public void glUniform4iv(final int location, final IntBuffer ubuff) {
    GLContext.getCurrentGL().getGL2ES2().glUniform4iv(location, ubuff.remaining() / 4, ubuff);
  }

  @Override
  public void glUniformMatrix4(final int location, final boolean transpose, final FloatBuffer matrices) {
    // for second parameter 'count', supply the number of remaining floats in
    // the float buffer divided by the element size
    // which in this case is the number of values in a 4x4 matrix (16 floats per
    // element index)
    GLContext.getCurrentGL().getGL2ES2().glUniformMatrix4fv(location, matrices.remaining() / 16, transpose, matrices);
  }

  @Override
  public void glUniformMatrix2(final int location, final boolean transpose, final FloatBuffer matrices) {
    GLContext.getCurrentGL().getGL2ES2().glUniformMatrix2fv(location, matrices.remaining() / 4, transpose, matrices);
  }

  @Override
  public void glUniformMatrix2x3(final int location, final boolean transpose, final FloatBuffer matrices) {
    GLContext.getCurrentGL().getGL2ES3().glUniformMatrix2x3fv(location, matrices.remaining() / 6, transpose, matrices);
  }

  @Override
  public void glUniformMatrix2x4(final int location, final boolean transpose, final FloatBuffer matrices) {
    GLContext.getCurrentGL().getGL2ES3().glUniformMatrix2x4fv(location, matrices.remaining() / 8, transpose, matrices);
  }

  @Override
  public void glUniformMatrix3(final int location, final boolean transpose, final FloatBuffer matrices) {
    GLContext.getCurrentGL().getGL2ES2().glUniformMatrix3fv(location, matrices.remaining() / 9, transpose, matrices);
  }

  @Override
  public void glUniformMatrix3x2(final int location, final boolean transpose, final FloatBuffer matrices) {
    GLContext.getCurrentGL().getGL2ES3().glUniformMatrix3x2fv(location, matrices.remaining() / 6, transpose, matrices);
  }

  @Override
  public void glUniformMatrix3x4(final int location, final boolean transpose, final FloatBuffer matrices) {
    GLContext.getCurrentGL().getGL2ES3().glUniformMatrix3x4fv(location, matrices.remaining() / 12, transpose, matrices);
  }

  @Override
  public void glUniformMatrix4x2(final int location, final boolean transpose, final FloatBuffer matrices) {
    GLContext.getCurrentGL().getGL2ES3().glUniformMatrix4x2fv(location, matrices.remaining() / 8, transpose, matrices);
  }

  @Override
  public void glUniformMatrix4x3(final int location, final boolean transpose, final FloatBuffer matrices) {
    GLContext.getCurrentGL().getGL2ES3().glUniformMatrix4x3fv(location, matrices.remaining() / 12, transpose, matrices);
  }

  @Override
  public boolean glUnmapBuffer(final int target) {
    return GLContext.getCurrentGL().glUnmapBuffer(target);
  }

  @Override
  public void glUseProgram(final int program) {
    GLContext.getCurrentGL().getGL2ES2().glUseProgram(program);
  }

  @Override
  public void glVertexAttribPointer(final int index,
                                    final int size,
                                    final int type,
                                    final boolean normalized,
                                    final int stride,
                                    final long offset) {
    GLContext.getCurrentGL().getGL2ES2().glVertexAttribPointer(index, size, type, normalized, stride, offset);
  }

  @Override
  public void glGetUniformIndices(final int program, final String[] uniformNames, final IntBuffer indexBuffer) {
    GLContext.getCurrentGL().getGL2ES3().glGetUniformIndices(program, uniformNames.length, uniformNames, indexBuffer);
  }

  @Override
  public void glGetActiveUniforms(final int program,
                                  final int uniformCount,
                                  final IntBuffer indices,
                                  final int pname,
                                  final IntBuffer params) {
    GLContext.getCurrentGL().getGL2ES3().glGetActiveUniformsiv(program, uniformCount, indices, pname, params);
  }

  @Override
  public int glGetUniformBlockIndex(final int program, final String name) {
    return GLContext.getCurrentGL().getGL2ES3().glGetUniformBlockIndex(program, name);
  }

  @Override
  public void glUniformBlockBinding(final int prog, final int blockIndex, final int blockBinding) {
    GLContext.getCurrentGL().getGL2ES3().glUniformBlockBinding(prog, blockIndex, blockBinding);
  }

  @Override
  public void glPixelStorei(final int param, final int n) {
    GLContext.getCurrentGL().getGL().glPixelStorei(param, n);
  }

  @Override
  public void glReadPixels(final int x,
                           final int y,
                           final int width,
                           final int height,
                           final int format,
                           final int type,
                           final ByteBuffer pixelBuffer) {
    GLContext.getCurrentGL().getGL().glReadPixels(x, y, width, height, format, type, pixelBuffer);
  }

  @Override
  public void glGetTexImage(final int target,
                            final int level,
                            final int format,
                            final int type,
                            final ByteBuffer pixelBuffer) {
    GLContext.getCurrentGL().getGL2().glGetTexImage(target, level, format, type, pixelBuffer);
  }

  @Override
  public void glGenFramebuffers(final int n, final IntBuffer frameBuffs) {
    GLContext.getCurrentGL().getGL().glGenFramebuffers(n, frameBuffs);
  }

  @Override
  public int glCheckFramebufferStatus(final int target) {
    return GLContext.getCurrentGL().glCheckFramebufferStatus(target);
  }

  @Override
  public void glBindFramebuffer(final int target, final int fbo) {
    GLContext.getCurrentGL().glBindFramebuffer(target, fbo);
  }

  @Override
  public void glDeleteFramebuffers(final int fboCount, final IntBuffer fboIds) {
    GLContext.getCurrentGL().glDeleteFramebuffers(fboCount, fboIds);
  }

  @Override
  public void glFramebufferTexture2D(final int target,
                                     final int attachment,
                                     final int textarget,
                                     final int texture,
                                     final int level) {
    GLContext.getCurrentGL().glFramebufferTexture2D(target, attachment, textarget, texture, level);
  }

  @Override
  public void glFramebufferTextureLayer(final int target,
                                        final int attachment,
                                        final int texture,
                                        final int level,
                                        final int layer) {
    GLContext.getCurrentGL().getGL2ES3().glFramebufferTextureLayer(target, attachment, texture, level, layer);
  }

  @Override
  public void glDrawBuffer(final int mode) {
    GLContext.getCurrentGL().getGL2GL3().glDrawBuffer(mode);
  }

  @Override
  public void glGenRenderBuffers(final int buffCount, final IntBuffer buffer) {
    GLContext.getCurrentGL().glGenRenderbuffers(buffCount, buffer);

  }

  @Override
  public void glBindRenderbuffer(final int target, final int renderBuffer) {
    GLContext.getCurrentGL().glBindRenderbuffer(target, renderBuffer);
  }

  @Override
  public void glRenderbufferStorage(final int target, final int internalFormat, final int width, final int height) {
    GLContext.getCurrentGL().glRenderbufferStorage(target, internalFormat, width, height);
  }

  @Override
  public void glFramebufferRenderbuffer(final int target,
                                        final int attachment,
                                        final int renderBufferTarget,
                                        final int renderBuffer) {
    GLContext.getCurrentGL().glFramebufferRenderbuffer(target, attachment, renderBufferTarget, renderBuffer);
  }

  @Override
  public void glBindBufferBase(final int target, final int bindingPoint, final int id) {
    GLContext.getCurrentGL().getGL2GL3().glBindBufferBase(target, bindingPoint, id);
  }

  @Override
  public void glPointSize(final int psize) {
    GLContext.getCurrentGL().getGL2GL3().glPointSize(psize);
  }

  @Override
  public void glBlendEquationSeparate(final int e1, final int e2) {
    GLContext.getCurrentGL().glBlendEquationSeparate(e1, e2);
  }

  private boolean errorChecksEnabled = true;

  @Override
  public void checkGLError() {
    checkGLError("");
  }

  @Override
  public void checkGLError(final String msg) {
    log.checkGLError(this, msg);
  }

  @Override
  public void checkGLError(final String msg, final Object...args) {
    log.checkGLError(this, false, msg, args);
  }

  @Override
  public void checkGLError(final boolean throwException, final String msg, final Object...args) {
    if (!errorChecksEnabled) return;
    log.checkGLError(this, throwException, msg, args);
  }

  @Override
  public void setErrorChecksEnabled(final boolean enabled) {
    errorChecksEnabled = enabled;
  }

  @Override
  public CoreUtil getUtil() {
    return util;
  }
}
