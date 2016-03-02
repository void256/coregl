package de.lessvoid.coregl.lwjgl3;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GL41;

import de.lessvoid.coregl.CoreLogger;
import de.lessvoid.coregl.spi.CoreGL;
import de.lessvoid.coregl.spi.CoreUtil;

/**
 * @author Aaron Mahan &lt;aaron@forerunnergames.com&gt;
 * @author Brian Groenke (groenke.5@osu.edu)
 */
public class Lwjgl3CoreGL implements CoreGL {

  private static CoreLogger log = CoreLogger.getCoreLogger(Lwjgl3CoreGL.class.getName());

  private boolean errorCheckingEnabled = false;

  private final CoreUtil util = new Lwjgl3CoreUtil();

  @Override
  public void checkGLError() {
    checkGLError("");
  }

  @Override
  public void checkGLError(final boolean throwException, final String msg, final Object...args) {
    if (!errorCheckingEnabled) return;
    log.checkGLError(this, throwException, msg, args);
  }

  @Override
  public void checkGLError(final String msg) {
    checkGLError(false, msg);
  }

  @Override
  public void checkGLError(final String msg, final Object...args) {
    checkGLError(false, msg, args);
  }

  @Override
  public CoreUtil getUtil() {
    return util;
  }

  @Override
  public int GL_ACTIVE_TEXTURE() {
    return GL13.GL_ACTIVE_TEXTURE;
  }

  @Override
  public int GL_ALPHA() {
    return GL11.GL_ALPHA;
  }

  @Override
  public int GL_ALPHA_TEST() {
    return GL11.GL_ALPHA_TEST;
  }

  @Override
  public int GL_ALWAYS() {
    return GL11.GL_ALWAYS;
  }

  @Override
  public int GL_ARRAY_BUFFER() {
    return GL15.GL_ARRAY_BUFFER;
  }

  @Override
  public int GL_BACK() {
    return GL11.GL_BACK;
  }

  @Override
  public int GL_BGR() {
    return GL12.GL_BGR;
  }

  @Override
  public int GL_BGRA() {
    return GL12.GL_BGRA;
  }

  @Override
  public int GL_BITMAP() {
    return GL11.GL_BITMAP;
  }

  @Override
  public int GL_BLEND() {
    return GL11.GL_BLEND;
  }

  @Override
  public int GL_BLEND_DST() {
    return GL11.GL_BLEND_DST;
  }

  @Override
  public int GL_BLEND_SRC() {
    return GL11.GL_BLEND_SRC;
  }

  @Override
  public int GL_BLUE() {
    return GL11.GL_BLUE;
  }

  @Override
  public int GL_BYTE() {
    return GL11.GL_BYTE;
  }

  @Override
  public int GL_COLOR_ATTACHMENT0() {
    return GL30.GL_COLOR_ATTACHMENT0;
  }

  @Override
  public int GL_COLOR_BUFFER_BIT() {
    return GL11.GL_COLOR_BUFFER_BIT;
  }

  @Override
  public int GL_COLOR_INDEX() {
    return GL11.GL_COLOR_INDEX;
  }

  @Override
  public int GL_COMPILE_STATUS() {
    return GL20.GL_COMPILE_STATUS;
  }

  @Override
  public int GL_COMPRESSED_ALPHA() {
    return GL13.GL_COMPRESSED_ALPHA;
  }

  @Override
  public int GL_COMPRESSED_LUMINANCE() {
    return GL13.GL_COMPRESSED_LUMINANCE;
  }

  @Override
  public int GL_COMPRESSED_LUMINANCE_ALPHA() {
    return GL13.GL_COMPRESSED_LUMINANCE_ALPHA;
  }

  @Override
  public int GL_COMPRESSED_RGB() {
    return GL13.GL_COMPRESSED_RGB;
  }

  @Override
  public int GL_COMPRESSED_RGBA() {
    return GL13.GL_COMPRESSED_RGBA;
  }

  @Override
  public int GL_CULL_FACE() {
    return GL11.GL_CULL_FACE;
  }

  @Override
  public int GL_CURRENT_PROGRAM() {
    return GL20.GL_CURRENT_PROGRAM;
  }

  @Override
  public int GL_DECR() {
    return GL11.GL_DECR;
  }

  @Override
  public int GL_DECR_WRAP() {
    return GL14.GL_DECR_WRAP;
  }

  @Override
  public int GL_DEPTH_BUFFER_BIT() {
    return GL11.GL_DEPTH_BUFFER_BIT;
  }

  @Override
  public int GL_DEPTH_TEST() {
    return GL11.GL_DEPTH_TEST;
  }

  @Override
  public int GL_DOUBLE() {
    return GL11.GL_DOUBLE;
  }

  @Override
  public int GL_DST_ALPHA() {
    return GL11.GL_DST_ALPHA;
  }

  @Override
  public int GL_DST_COLOR() {
    return GL11.GL_DST_COLOR;
  }

  @Override
  public int GL_DYNAMIC_DRAW() {
    return GL15.GL_DYNAMIC_DRAW;
  }

  @Override
  public int GL_ELEMENT_ARRAY_BUFFER() {
    return GL15.GL_ELEMENT_ARRAY_BUFFER;
  }

  @Override
  public int GL_EQUAL() {
    return GL11.GL_EQUAL;
  }

  @Override
  public int GL_FALSE() {
    return GL11.GL_FALSE;
  }

  @Override
  public int GL_FIXED() {
    return GL41.GL_FIXED;
  }

  @Override
  public int GL_FLOAT() {
    return GL11.GL_FLOAT;
  }

  @Override
  public int GL_FRAGMENT_SHADER() {
    return GL20.GL_FRAGMENT_SHADER;
  }

  @Override
  public int GL_FRAMEBUFFER() {
    return GL30.GL_FRAMEBUFFER;
  }

  @Override
  public int GL_FRAMEBUFFER_COMPLETE() {
    return GL30.GL_FRAMEBUFFER_COMPLETE;
  }

  @Override
  public int GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT() {
    return GL30.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT;
  }

  @Override
  public int GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER() {
    return GL30.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER;
  }

  @Override
  public int GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT() {
    return GL30.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT;
  }

  @Override
  public int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE() {
    return GL30.GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE;
  }

  @Override
  public int GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER() {
    return GL30.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER;
  }

  @Override
  public int GL_FRAMEBUFFER_UNDEFINED() {
    return GL30.GL_FRAMEBUFFER_UNDEFINED;
  }

  @Override
  public int GL_FRAMEBUFFER_UNSUPPORTED() {
    return GL30.GL_FRAMEBUFFER_UNSUPPORTED;
  }

  @Override
  public int GL_FRONT() {
    return GL11.GL_FRONT;
  }

  @Override
  public int GL_FRONT_AND_BACK() {
    return GL11.GL_FRONT_AND_BACK;
  }

  @Override
  public int GL_FUNC_ADD() {
    return GL14.GL_FUNC_ADD;
  }

  @Override
  public int GL_GEOMETRY_SHADER() {
    return GL32.GL_GEOMETRY_SHADER;
  }

  @Override
  public int GL_GEQUAL() {
    return GL11.GL_GEQUAL;
  }

  @Override
  public int GL_GREATER() {
    return GL11.GL_GREATER;
  }

  @Override
  public int GL_GREEN() {
    return GL11.GL_GREEN;
  }

  @Override
  public int GL_HALF_FLOAT() {
    return GL30.GL_HALF_FLOAT;
  }

  @Override
  public int GL_INCR() {
    return GL11.GL_INCR;
  }

  @Override
  public int GL_INCR_WRAP() {
    return GL14.GL_INCR_WRAP;
  }

  @Override
  public int GL_INT() {
    return GL11.GL_INT;
  }

  @Override
  public int GL_INT_2_10_10_10_REV() {
    return GL33.GL_INT_2_10_10_10_REV;
  }

  @Override
  public int GL_INVALID_ENUM() {
    return GL11.GL_INVALID_ENUM;
  }

  @Override
  public int GL_INVALID_OPERATION() {
    return GL11.GL_INVALID_OPERATION;
  }

  @Override
  public int GL_INVALID_VALUE() {
    return GL11.GL_INVALID_VALUE;
  }

  @Override
  public int GL_INVERT() {
    return GL11.GL_INVERT;
  }

  @Override
  public int GL_KEEP() {
    return GL11.GL_KEEP;
  }

  @Override
  public int GL_LEQUAL() {
    return GL11.GL_LEQUAL;
  }

  @Override
  public int GL_LESS() {
    return GL11.GL_LESS;
  }

  @Override
  public int GL_LINE_STRIP() {
    return GL11.GL_LINE_STRIP;
  }

  @Override
  public int GL_LINE_STRIP_ADJACENCY() {
    return GL32.GL_LINE_STRIP_ADJACENCY;
  }

  @Override
  public int GL_LINEAR() {
    return GL11.GL_LINEAR;
  }

  @Override
  public int GL_LINEAR_MIPMAP_LINEAR() {
    return GL11.GL_LINEAR_MIPMAP_LINEAR;
  }

  @Override
  public int GL_LINEAR_MIPMAP_NEAREST() {
    return GL11.GL_LINEAR_MIPMAP_NEAREST;
  }

  @Override
  public int GL_LINK_STATUS() {
    return GL20.GL_LINK_STATUS;
  }

  @Override
  public int GL_LUMINANCE() {
    return GL11.GL_LUMINANCE;
  }

  @Override
  public int GL_LUMINANCE_ALPHA() {
    return GL11.GL_LUMINANCE_ALPHA;
  }

  @Override
  public int GL_MAX() {
    return GL14.GL_MAX;
  }

  @Override
  public int GL_MAX_3D_TEXTURE_SIZE() {
    return GL12.GL_MAX_3D_TEXTURE_SIZE;
  }

  @Override
  public int GL_MAX_TEXTURE_SIZE() {
    return GL11.GL_MAX_TEXTURE_SIZE;
  }

  @Override
  public int GL_MAX_VERTEX_ATTRIBS() {
    return GL20.GL_MAX_VERTEX_ATTRIBS;
  }

  @Override
  public int GL_NEAREST() {
    return GL11.GL_NEAREST;
  }

  @Override
  public int GL_NEAREST_MIPMAP_LINEAR() {
    return GL11.GL_NEAREST_MIPMAP_LINEAR;
  }

  @Override
  public int GL_NEAREST_MIPMAP_NEAREST() {
    return GL11.GL_NEAREST_MIPMAP_NEAREST;
  }

  @Override
  public int GL_NEVER() {
    return GL11.GL_NEVER;
  }

  @Override
  public int GL_NO_ERROR() {
    return GL11.GL_NO_ERROR;
  }

  @Override
  public int GL_NOTEQUAL() {
    return GL11.GL_NOTEQUAL;
  }

  @Override
  public int GL_ONE() {
    return GL11.GL_ONE;
  }

  @Override
  public int GL_ONE_MINUS_DST_ALPHA() {
    return GL11.GL_ONE_MINUS_DST_ALPHA;
  }

  @Override
  public int GL_ONE_MINUS_SRC_ALPHA() {
    return GL11.GL_ONE_MINUS_SRC_ALPHA;
  }

  @Override
  public int GL_OUT_OF_MEMORY() {
    return GL11.GL_OUT_OF_MEMORY;
  }

  @Override
  public int GL_PACK_ALIGNMENT() {
    return GL11.GL_PACK_ALIGNMENT;
  }

  @Override
  public int GL_POINTS() {
    return GL11.GL_POINTS;
  }

  @Override
  public int GL_PRIMITIVE_RESTART() {
    return GL31.GL_PRIMITIVE_RESTART;
  }

  @Override
  public int GL_PRIMITIVE_RESTART_INDEX() {
    return GL31.GL_PRIMITIVE_RESTART_INDEX;
  }

  @Override
  public int GL_R32F() {
    return GL30.GL_R32F;
  }

  @Override
  public int GL_RED() {
    return GL11.GL_RED;
  }

  @Override
  public int GL_RENDERBUFFER() {
    return GL30.GL_RENDERBUFFER;
  }

  @Override
  public int GL_RENDERER() {
    return GL11.GL_RENDERER;
  }

  @Override
  public int GL_REPLACE() {
    return GL11.GL_REPLACE;
  }

  @Override
  public int GL_RGB() {
    return GL11.GL_RGB;
  }

  @Override
  public int GL_RGBA() {
    return GL11.GL_RGBA;
  }

  @Override
  public int GL_SHADING_LANGUAGE_VERSION() {
    return GL20.GL_SHADING_LANGUAGE_VERSION;
  }

  @Override
  public int GL_SHORT() {
    return GL11.GL_SHORT;
  }

  @Override
  public int GL_SRC_ALPHA() {
    return GL11.GL_SRC_ALPHA;
  }

  @Override
  public int GL_STACK_OVERFLOW() {
    return GL11.GL_STACK_OVERFLOW;
  }

  @Override
  public int GL_STACK_UNDERFLOW() {
    return GL11.GL_STACK_UNDERFLOW;
  }

  @Override
  public int GL_STATIC_DRAW() {
    return GL15.GL_STATIC_DRAW;
  }

  @Override
  public int GL_STENCIL_ATTACHMENT() {
    return GL30.GL_STENCIL_ATTACHMENT;
  }

  @Override
  public int GL_STENCIL_BUFFER_BIT() {
    return GL11.GL_STENCIL_BUFFER_BIT;
  }

  @Override
  public int GL_STENCIL_INDEX() {
    return GL11.GL_STENCIL_INDEX;
  }

  @Override
  public int GL_STENCIL_INDEX8() {
    return GL30.GL_STENCIL_INDEX8;
  }

  @Override
  public int GL_STENCIL_TEST() {
    return GL11.GL_STENCIL_TEST;
  }

  @Override
  public int GL_STREAM_DRAW() {
    return GL15.GL_STREAM_DRAW;
  }

  @Override
  public int GL_TEXTURE_2D() {
    return GL11.GL_TEXTURE_2D;
  }

  @Override
  public int GL_TEXTURE_2D_ARRAY() {
    return GL30.GL_TEXTURE_2D_ARRAY;
  }

  @Override
  public int GL_TEXTURE_3D() {
    return GL12.GL_TEXTURE_3D;
  }

  @Override
  public int GL_TEXTURE_BINDING_2D() {
    return GL11.GL_TEXTURE_BINDING_2D;
  }

  @Override
  public int GL_TEXTURE_BINDING_3D() {
    return GL12.GL_TEXTURE_BINDING_3D;
  }

  @Override
  public int GL_TEXTURE_BUFFER() {
    return GL31.GL_TEXTURE_BUFFER;
  }

  @Override
  public int GL_TEXTURE_CUBE_MAP_NEGATIVE_X() {
    return GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_X;
  }

  @Override
  public int GL_TEXTURE_CUBE_MAP_NEGATIVE_Y() {
    return GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y;
  }

  @Override
  public int GL_TEXTURE_CUBE_MAP_NEGATIVE_Z() {
    return GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z;
  }

  @Override
  public int GL_TEXTURE_CUBE_MAP_POSITIVE_X() {
    return GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
  }

  @Override
  public int GL_TEXTURE_CUBE_MAP_POSITIVE_Y() {
    return GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Y;
  }

  @Override
  public int GL_TEXTURE_CUBE_MAP_POSITIVE_Z() {
    return GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Z;
  }

  @Override
  public int GL_TEXTURE_MAG_FILTER() {
    return GL11.GL_TEXTURE_MAG_FILTER;
  }

  @Override
  public int GL_TEXTURE_MIN_FILTER() {
    return GL11.GL_TEXTURE_MIN_FILTER;
  }

  @Override
  public int GL_TEXTURE0() {
    return GL13.GL_TEXTURE0;
  }

  @Override
  public int GL_TRIANGLE_FAN() {
    return GL11.GL_TRIANGLE_FAN;
  }

  @Override
  public int GL_TRIANGLE_STRIP() {
    return GL11.GL_TRIANGLE_STRIP;
  }

  @Override
  public int GL_TRIANGLES() {
    return GL11.GL_TRIANGLES;
  }

  @Override
  public int GL_TRUE() {
    return GL11.GL_TRUE;
  }

  @Override
  public int GL_UNIFORM_ARRAY_STRIDE() {
    return GL31.GL_UNIFORM_ARRAY_STRIDE;
  }

  @Override
  public int GL_UNIFORM_BUFFER() {
    return GL31.GL_UNIFORM_BUFFER;
  }

  @Override
  public int GL_UNIFORM_MATRIX_STRIDE() {
    return GL31.GL_UNIFORM_MATRIX_STRIDE;
  }

  @Override
  public int GL_UNIFORM_OFFSET() {
    return GL31.GL_UNIFORM_OFFSET;
  }

  @Override
  public int GL_UNSIGNED_BYTE() {
    return GL11.GL_UNSIGNED_BYTE;
  }

  @Override
  public int GL_UNSIGNED_BYTE_2_3_3_REV() {
    return GL12.GL_UNSIGNED_BYTE_2_3_3_REV;
  }

  @Override
  public int GL_UNSIGNED_BYTE_3_3_2() {
    return GL12.GL_UNSIGNED_BYTE_3_3_2;
  }

  @Override
  public int GL_UNSIGNED_INT() {
    return GL11.GL_UNSIGNED_INT;
  }

  @Override
  public int GL_UNSIGNED_INT_10_10_10_2() {
    return GL12.GL_UNSIGNED_INT_10_10_10_2;
  }

  @Override
  public int GL_UNSIGNED_INT_10F_11F_11F_REV() {
    return GL30.GL_UNSIGNED_INT_10F_11F_11F_REV;
  }

  @Override
  public int GL_UNSIGNED_INT_2_10_10_10_REV() {
    return GL12.GL_UNSIGNED_INT_2_10_10_10_REV;
  }

  @Override
  public int GL_UNSIGNED_INT_8_8_8_8() {
    return GL12.GL_UNSIGNED_INT_8_8_8_8;
  }

  @Override
  public int GL_UNSIGNED_INT_8_8_8_8_REV() {
    return GL12.GL_UNSIGNED_INT_8_8_8_8_REV;
  }

  @Override
  public int GL_UNSIGNED_SHORT() {
    return GL11.GL_UNSIGNED_SHORT;
  }

  @Override
  public int GL_UNSIGNED_SHORT_1_5_5_5_REV() {
    return GL12.GL_UNSIGNED_SHORT_1_5_5_5_REV;
  }

  @Override
  public int GL_UNSIGNED_SHORT_4_4_4_4() {
    return GL12.GL_UNSIGNED_SHORT_4_4_4_4;
  }

  @Override
  public int GL_UNSIGNED_SHORT_4_4_4_4_REV() {
    return GL12.GL_UNSIGNED_SHORT_4_4_4_4_REV;
  }

  @Override
  public int GL_UNSIGNED_SHORT_5_5_5_1() {
    return GL12.GL_UNSIGNED_SHORT_5_5_5_1;
  }

  @Override
  public int GL_UNSIGNED_SHORT_5_6_5() {
    return GL12.GL_UNSIGNED_SHORT_5_6_5;
  }

  @Override
  public int GL_UNSIGNED_SHORT_5_6_5_REV() {
    return GL12.GL_UNSIGNED_SHORT_5_6_5_REV;
  }

  @Override
  public int GL_VENDOR() {
    return GL11.GL_VENDOR;
  }

  @Override
  public int GL_VERSION() {
    return GL11.GL_VERSION;
  }

  @Override
  public int GL_VERTEX_SHADER() {
    return GL20.GL_VERTEX_SHADER;
  }

  @Override
  public int GL_VIEWPORT() {
    return GL11.GL_VIEWPORT;
  }

  @Override
  public int GL_WRITE_ONLY() {
    return GL15.GL_WRITE_ONLY;
  }

  @Override
  public int GL_ZERO() {
    return GL11.GL_ZERO;
  }

  @Override
  public void glActiveTexture(final int texture) {
    GL13.glActiveTexture(texture);
  }

  @Override
  public void glAttachShader(final int program, final int shader) {
    GL20.glAttachShader(program, shader);
  }

  @Override
  public void glBindAttribLocation(final int program, final int index, final String name) {
    GL20.glBindAttribLocation(program, index, name);
  }

  @Override
  public void glBindBuffer(final int target, final int buffer) {
    GL15.glBindBuffer(target, buffer);
  }

  @Override
  public void glBindBufferBase(final int target, final int bindingPoint, final int id) {
    GL30.glBindBufferBase(target, bindingPoint, id);
  }

  @Override
  public void glBindFramebuffer(final int target, final int fbo) {
    GL30.glBindFramebuffer(target, fbo);
  }

  @Override
  public void glBindRenderbuffer(final int target, final int renderBuffer) {
    GL30.glBindRenderbuffer(target, renderBuffer);
  }

  @Override
  public void glBindTexture(final int target, final int texture) {
    GL11.glBindTexture(target, texture);
  }

  @Override
  public void glBindVertexArray(final int array) {
    GL30.glBindVertexArray(array);
  }

  @Override
  public void glBlendEquationSeparate(final int e1, final int e2) {
    GL20.glBlendEquationSeparate(e1, e2);
  }

  @Override
  public void glBlendFunc(final int sfactor, final int dfactor) {
    GL11.glBlendFunc(sfactor, dfactor);
  }

  @Override
  public void glBlendFuncSeparate(final int sfactorRGB,
                                  final int dfactorRGB,
                                  final int sfactorAlpha,
                                  final int dfactorAlpha) {
    GL14.glBlendFuncSeparate(sfactorRGB, dfactorRGB, sfactorAlpha, sfactorAlpha);
  }

  @Override
  public void glBufferData(final int target, final FloatBuffer data, final int usage) {
    GL15.glBufferData(target, data, usage);
  }

  @Override
  public void glBufferData(final int target, final IntBuffer data, final int usage) {
    GL15.glBufferData(target, data, usage);
  }

  @Override
  public void glBufferData(final int target, final ShortBuffer data, final int usage) {
    GL15.glBufferData(target, data, usage);
  }

  @Override
  public int glCheckFramebufferStatus(final int target) {
    return GL30.glCheckFramebufferStatus(target);
  }

  @Override
  public void glClear(final int mask) {
    GL11.glClear(mask);
  }

  @Override
  public void glClearColor(final float red, final float green, final float blue, final float alpha) {
    GL11.glClearColor(red, green, blue, alpha);
  }

  @Override
  public void glClearStencil(int arg0) {
    GL11.glClearStencil(arg0);
  }

  @Override
  public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
    GL11.glColorMask(red, green, blue, alpha);
  }

  @Override
  public void glCompileShader(final int shader) {
    GL20.glCompileShader(shader);
  }

  @Override
  public int glCreateProgram() {
    return GL20.glCreateProgram();
  }

  @Override
  public int glCreateShader(final int type) {
    return GL20.glCreateShader(type);
  }

  @Override
  public void glDeleteBuffers(final int n, final IntBuffer buffers) {
    GL15.glDeleteBuffers(buffers);
  }

  @Override
  public void glDeleteFramebuffers(final int fboCount, final IntBuffer fboIds) {
    GL30.glDeleteFramebuffers(fboIds);
  }

  @Override
  public void glDeleteTextures(final int n, final IntBuffer textures) {
    GL11.glDeleteTextures(textures);
  }

  @Override
  public void glDeleteVertexArrays(final int n, final IntBuffer arrays) {
    GL30.glDeleteVertexArrays(arrays);
  }

  @Override
  public void glDepthMask(boolean mask) {
    GL11.glDepthMask(mask);
  }

  @Override
  public void glDisable(final int cap) {
    GL11.glDisable(cap);
  }

  @Override
  public void glDisableVertexAttribArray(final int index) {
    GL20.glDisableVertexAttribArray(index);
  }

  @Override
  public void glDrawArrays(final int mode, final int first, final int count) {
    GL11.glDrawArrays(mode, first, count);
  }

  @Override
  public void glDrawArraysInstanced(final int mode, final int first, final int count, final int primcount) {
    GL31.glDrawArraysInstanced(mode, first, count, primcount);
  }

  @Override
  public void glDrawBuffer(final int mode) {
    GL11.glDrawBuffer(mode);
  }

  @Override
  public void glDrawElements(final int mode, final int count, final int type, final int indices) {
    GL11.glDrawElements(mode, count, type, indices);
  }

  @Override
  public void glEnable(final int cap) {
    GL11.glEnable(cap);
  }

  @Override
  public void glEnableVertexAttribArray(final int index) {
    GL20.glEnableVertexAttribArray(index);
  }

  @Override
  public void glFramebufferRenderbuffer(final int target,
                                        final int attachment,
                                        final int renderBufferTarget,
                                        final int renderBuffer) {
    GL30.glFramebufferRenderbuffer(target, attachment, renderBufferTarget, renderBuffer);
  }

  @Override
  public void glFramebufferTexture2D(final int target,
                                     final int attachment,
                                     final int textarget,
                                     final int texture,
                                     final int level) {
    GL30.glFramebufferTexture2D(target, attachment, textarget, texture, level);
  }

  @Override
  public void glFramebufferTextureLayer(final int target,
                                        final int attachment,
                                        final int texture,
                                        final int level,
                                        final int layer) {
    GL30.glFramebufferTextureLayer(target, attachment, texture, level, layer);
  }

  @Override
  public void glGenBuffers(final int n, final IntBuffer buffers) {
    GL15.glGenBuffers(buffers);
  }

  @Override
  public void glGenerateMipmap(final int target) {
    GL30.glGenerateMipmap(target);
  }

  @Override
  public void glGenFramebuffers(final int n, final IntBuffer frameBuffs) {
    GL30.glGenFramebuffers(frameBuffs);
  }

  @Override
  public void glGenRenderBuffers(final int buffCount, final IntBuffer buffer) {
    GL30.glGenRenderbuffers(buffer);
  }

  @Override
  public void glGenTextures(final int n, final IntBuffer textures) {
    GL11.glGenTextures(textures);
  }

  @Override
  public void glGenVertexArrays(final int n, final IntBuffer arrays) {
    GL30.glGenVertexArrays(arrays);
  }

  @Override
  public void glGetActiveUniforms(final int program,
                                  final int uniformCount,
                                  final IntBuffer indices,
                                  final int pname,
                                  final IntBuffer params) {
    GL31.glGetActiveUniformsiv(program, indices, pname, params);
  }

  @Override
  public int glGetAttribLocation(final int program, final String name) {
    return GL20.glGetAttribLocation(program, name);
  }

  @Override
  public int glGetError() {
    return GL11.glGetError();
  }

  @Override
  public int glGetInteger(final int pname) {
    return GL11.glGetInteger(pname);
  }

  @Override
  public void glGetIntegerv(final int pname, final int[] params, final int offset) {
    final IntBuffer paramsBuffer = BufferUtils.createIntBuffer(100);
    GL11.glGetIntegerv(pname, paramsBuffer);
    for (int i = offset, j = 0; i < params.length; i++, j++) {
      if (j == paramsBuffer.capacity()) return;
      params[i] = paramsBuffer.get(j);
    }
  }

  @Override
  public void glGetIntegerv(final int pname, final IntBuffer params) {
    GL11.glGetIntegerv(pname, params);
  }

  @Override
  public String glGetProgramInfoLog(final int program) {
    final int logLength = GL20.glGetProgrami(program, GL20.GL_INFO_LOG_LENGTH);
    return GL20.glGetProgramInfoLog(program, logLength);
  }

  @Override
  public void glGetProgramiv(final int program, final int pname, final IntBuffer params) {
    GL20.glGetProgramiv(program, pname, params);
  }

  @Override
  public String glGetShaderInfoLog(final int shader) {
    final int logLength = GL20.glGetShaderi(shader, GL20.GL_INFO_LOG_LENGTH);
    return GL20.glGetShaderInfoLog(shader, logLength);
  }

  @Override
  public void glGetShaderiv(final int shader, final int pname, final IntBuffer params) {
    GL20.glGetShaderiv(shader, pname, params);
  }

  @Override
  public String glGetString(final int pname) {
    return GL11.glGetString(pname);
  }

  @Override
  public void glGetTexImage(final int target,
                            final int level,
                            final int format,
                            final int type,
                            final ByteBuffer pixelBuffer) {
    GL11.glGetTexImage(target, level, format, type, pixelBuffer);
  }

  @Override
  public int glGetUniformBlockIndex(final int program, final String name) {
    return GL31.glGetUniformBlockIndex(program, name);
  }

  @Override
  public void glGetUniformIndices(final int program, final String[] uniformNames, final IntBuffer indexBuffer) {
    GL31.glGetUniformIndices(program, uniformNames, indexBuffer);
  }

  @Override
  public int glGetUniformLocation(final int program, final String name) {
    return GL20.glGetUniformLocation(program, name);
  }

  @Override
  public boolean glIsEnabled(final int cap) {
    return GL11.glIsEnabled(cap);
  }

  @Override
  public void glLinkProgram(final int program) {
    GL20.glLinkProgram(program);
  }

  @Override
  public ByteBuffer glMapBuffer(final int target, final int access, final long length, final ByteBuffer oldBuffer) {
    return GL15.glMapBuffer(target, access, oldBuffer);
  }

  @Override
  public void glPixelStorei(final int param, final int n) {
    GL11.glPixelStorei(param, n);
  }

  @Override
  public void glPointSize(final int psize) {
    GL11.glPointSize(psize);
  }

  @Override
  public void glPrimitiveRestartIndex(final int index) {
    GL31.glPrimitiveRestartIndex(index);
  }

  @Override
  public void glReadPixels(final int x,
                           final int y,
                           final int width,
                           final int height,
                           final int format,
                           final int type,
                           final ByteBuffer pixelBuffer) {
    GL11.glReadPixels(x, y, width, height, format, type, pixelBuffer);
  }

  @Override
  public void glRenderbufferStorage(final int target, final int internalFormat, final int width, final int height) {
    GL30.glRenderbufferStorage(target, internalFormat, width, height);
  }

  @Override
  public void glShaderSource(final int shader, final String string) {
    GL20.glShaderSource(shader, string);
  }

  @Override
  public void glStencilFunc(int func, int ref, int mask) {
    GL11.glStencilFunc(func, ref, mask);
  }

  @Override
  public void glStencilFuncSeparate(int face, int func, int ref, int mask) {
    GL20.glStencilFuncSeparate(face, func, ref, mask);
  }

  @Override
  public void glStencilMask(int mask) {
    GL11.glStencilMask(mask);
  }

  @Override
  public void glStencilMaskSeparate(int face, int mask) {
    GL20.glStencilMaskSeparate(face, mask);
  }

  @Override
  public void glStencilOp(int sfail, int dpfail, int dppass) {
    GL11.glStencilOp(sfail, dpfail, dppass);
  }

  @Override
  public void glStencilOpSeparate(int face, int sfail, int dpfail, int dppass) {
    GL20.glStencilOpSeparate(face, sfail, dpfail, dppass);
  }

  @Override
  public void glTexBuffer(final int arg0, final int arg1, final int arg2) {
    GL31.glTexBuffer(arg0, arg1, arg2);
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
    GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
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
    GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
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
    GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
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
    GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
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
    GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
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
    GL12.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
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
    GL12.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
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
    GL12.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
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
    GL12.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
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
    GL12.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
  }

  @Override
  public void glTexParameterf(final int target, final int pname, final float param) {
    GL11.glTexParameterf(target, pname, param);
  }

  @Override
  public void glTexParameteri(final int target, final int pname, final int param) {
    GL11.glTexParameteri(target, pname, param);
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
    GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
  }

  @Override
  public void glUniform1(final int location, final FloatBuffer values) {
    GL20.glUniform1fv(location, values);
  }

  @Override
  public void glUniform1f(final int location, final float v0) {
    GL20.glUniform1f(location, v0);
  }

  @Override
  public void glUniform1fv(final int location, final FloatBuffer ubuff) {
    GL20.glUniform1fv(location, ubuff);
  }

  @Override
  public void glUniform1i(final int location, final int v0) {
    GL20.glUniform1i(location, v0);
  }

  @Override
  public void glUniform1iv(final int location, final IntBuffer ubuff) {
    GL20.glUniform1iv(location, ubuff);
  }

  @Override
  public void glUniform2f(final int location, final float v0, final float v1) {
    GL20.glUniform2f(location, v0, v1);
  }

  @Override
  public void glUniform2fv(final int location, final FloatBuffer ubuff) {
    GL20.glUniform2fv(location, ubuff);
  }

  @Override
  public void glUniform2i(final int location, final int v0, final int v1) {
    GL20.glUniform2i(location, v0, v1);
  }

  @Override
  public void glUniform2iv(final int location, final IntBuffer ubuff) {
    GL20.glUniform2iv(location, ubuff);
  }

  @Override
  public void glUniform3f(final int location, final float v0, final float v1, final float v2) {
    GL20.glUniform3f(location, v0, v1, v2);
  }

  @Override
  public void glUniform3fv(final int location, final FloatBuffer ubuff) {
    GL20.glUniform3fv(location, ubuff);
  }

  @Override
  public void glUniform3i(final int location, final int v0, final int v1, final int v2) {
    GL20.glUniform3i(location, v0, v1, v2);
  }

  @Override
  public void glUniform3iv(final int location, final IntBuffer ubuff) {
    GL20.glUniform3iv(location, ubuff);
  }

  @Override
  public void glUniform4f(final int location, final float v0, final float v1, final float v2, final float v3) {
    GL20.glUniform4f(location, v0, v1, v2, v3);
  }

  @Override
  public void glUniform4fv(final int location, final FloatBuffer ubuff) {
    GL20.glUniform4fv(location, ubuff);
  }

  @Override
  public void glUniform4i(final int location, final int v0, final int v1, final int v2, final int v3) {
    GL20.glUniform4i(location, v0, v1, v2, v3);
  }

  @Override
  public void glUniform4iv(final int location, final IntBuffer ubuff) {
    GL20.glUniform4iv(location, ubuff);
  }

  @Override
  public void glUniformBlockBinding(final int prog, final int blockIndex, final int blockBinding) {
    GL31.glUniformBlockBinding(prog, blockIndex, blockBinding);
  }

  @Override
  public void glUniformMatrix2(final int location, final boolean transpose, final FloatBuffer matrices) {
    GL20.glUniformMatrix2fv(location, transpose, matrices);
  }

  @Override
  public void glUniformMatrix2x3(final int location, final boolean transpose, final FloatBuffer matrices) {
    GL21.glUniformMatrix2x3fv(location, transpose, matrices);
  }

  @Override
  public void glUniformMatrix2x4(final int location, final boolean transpose, final FloatBuffer matrices) {
    GL21.glUniformMatrix2x4fv(location, transpose, matrices);
  }

  @Override
  public void glUniformMatrix3(final int location, final boolean transpose, final FloatBuffer matrices) {
    GL20.glUniformMatrix3fv(location, transpose, matrices);
  }

  @Override
  public void glUniformMatrix3x2(final int location, final boolean transpose, final FloatBuffer matrices) {
    GL21.glUniformMatrix3x2fv(location, transpose, matrices);
  }

  @Override
  public void glUniformMatrix3x4(final int location, final boolean transpose, final FloatBuffer matrices) {
    GL21.glUniformMatrix3x4fv(location, transpose, matrices);
  }

  @Override
  public void glUniformMatrix4(final int location, final boolean transpose, final FloatBuffer matrices) {
    GL20.glUniformMatrix4fv(location, transpose, matrices);
  }

  @Override
  public void glUniformMatrix4x2(final int location, final boolean transpose, final FloatBuffer matrices) {
    GL21.glUniformMatrix4x2fv(location, transpose, matrices);
  }

  @Override
  public void glUniformMatrix4x3(final int location, final boolean transpose, final FloatBuffer matrices) {
    GL21.glUniformMatrix4x3fv(location, transpose, matrices);
  }

  @Override
  public boolean glUnmapBuffer(final int target) {
    return GL15.glUnmapBuffer(target);
  }

  @Override
  public void glUseProgram(final int program) {
    GL20.glUseProgram(program);
  }

  @Override
  public void glVertexAttribDivisor(final int index, final int divisor) {
    GL33.glVertexAttribDivisor(index, divisor);
  }

  @Override
  public void glVertexAttribIPointer(final int index,
                                     final int size,
                                     final int type,
                                     final int stride,
                                     final int buffer) {
    GL30.glVertexAttribIPointer(index, size, type, stride, buffer);
  }

  @Override
  public void glVertexAttribPointer(final int index,
                                    final int size,
                                    final int type,
                                    final boolean normalized,
                                    final int stride,
                                    final long offset) {
    GL20.glVertexAttribPointer(index, size, type, normalized, stride, offset);
  }

  @Override
  public void glViewport(final int x, final int y, final int width, final int height) {
    GL11.glViewport(x, y, width, height);
  }

  @Override
  public void setErrorChecksEnabled(final boolean enabled) {
    errorCheckingEnabled = enabled;
  }
}
