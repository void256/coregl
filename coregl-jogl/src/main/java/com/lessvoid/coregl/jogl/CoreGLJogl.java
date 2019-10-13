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
package com.lessvoid.coregl.jogl;

import com.jogamp.common.util.VersionNumber;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GL2GL3;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLExtensions;
import com.jogamp.opengl.glu.GLU;
import com.lessvoid.coregl.CoreBufferAccessType;
import com.lessvoid.coregl.CoreBufferTargetType;
import com.lessvoid.coregl.CoreBufferUsageType;
import com.lessvoid.coregl.CoreLogger;
import com.lessvoid.coregl.CoreVersion;
import com.lessvoid.coregl.CoreVersion.GLSLVersion;
import com.lessvoid.coregl.CoreVersion.GLVersion;
import com.lessvoid.coregl.spi.CoreGL;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

import static com.lessvoid.coregl.CoreBufferAccessType.READ_ONLY;
import static com.lessvoid.coregl.CoreBufferAccessType.READ_WRITE;
import static com.lessvoid.coregl.CoreBufferAccessType.WRITE_ONLY;
import static com.lessvoid.coregl.CoreBufferTargetType.ARRAY_BUFFER;
import static com.lessvoid.coregl.CoreBufferTargetType.COPY_READ_BUFFER;
import static com.lessvoid.coregl.CoreBufferTargetType.COPY_WRITE_BUFFER;
import static com.lessvoid.coregl.CoreBufferTargetType.ELEMENT_ARRAY_BUFFER;
import static com.lessvoid.coregl.CoreBufferTargetType.PIXEL_PACK_BUFFER;
import static com.lessvoid.coregl.CoreBufferTargetType.PIXEL_UNPACK_BUFFER;
import static com.lessvoid.coregl.CoreBufferTargetType.TEXTURE_BUFFER;
import static com.lessvoid.coregl.CoreBufferTargetType.TRANSFORM_FEEDBACK_BUFFER;
import static com.lessvoid.coregl.CoreBufferTargetType.UNIFORM_BUFFER;
import static com.lessvoid.coregl.CoreBufferUsageType.DYNAMIC_COPY;
import static com.lessvoid.coregl.CoreBufferUsageType.DYNAMIC_DRAW;
import static com.lessvoid.coregl.CoreBufferUsageType.DYNAMIC_READ;
import static com.lessvoid.coregl.CoreBufferUsageType.STATIC_COPY;
import static com.lessvoid.coregl.CoreBufferUsageType.STATIC_DRAW;
import static com.lessvoid.coregl.CoreBufferUsageType.STATIC_READ;
import static com.lessvoid.coregl.CoreBufferUsageType.STREAM_COPY;
import static com.lessvoid.coregl.CoreBufferUsageType.STREAM_DRAW;
import static com.lessvoid.coregl.CoreBufferUsageType.STREAM_READ;

/**
 * @author Aaron Mahan &lt;aaron@forerunnergames.com&gt;
 * @author Brian Groenke (groenke.5@osu.edu)
 */
public class CoreGLJogl implements CoreGL {
  private static CoreLogger log = CoreLogger.getCoreLogger(CoreGLJogl.class);

  private final Map<CoreBufferUsageType, Integer> bufferUsageTypeMap;
  private final Map<CoreBufferTargetType, Integer> bufferTargetTypeMap;
  private final Map<CoreBufferAccessType, Integer> bufferAccessTypeMap;
  private int windowWidth;
  private int windowHeight;
  private Object[] emptyObjectArray = new Object[0];

  public CoreGLJogl() {
    Map<CoreBufferUsageType, Integer> mapUsage = new Hashtable<CoreBufferUsageType, Integer>();
    mapUsage.put(STREAM_DRAW, GL_STREAM_DRAW());
    mapUsage.put(STREAM_READ, GL_STREAM_READ());
    mapUsage.put(STREAM_COPY, GL_STREAM_COPY());
    mapUsage.put(STATIC_DRAW, GL_STATIC_DRAW());
    mapUsage.put(STATIC_READ, GL_STATIC_READ());
    mapUsage.put(STATIC_COPY, GL_STATIC_COPY());
    mapUsage.put(DYNAMIC_DRAW, GL_DYNAMIC_DRAW());
    mapUsage.put(DYNAMIC_READ, GL_DYNAMIC_READ());
    mapUsage.put(DYNAMIC_COPY, GL_DYNAMIC_COPY());
    bufferUsageTypeMap = Collections.unmodifiableMap(mapUsage);

    Map<CoreBufferTargetType, Integer> mapTarget = new Hashtable<CoreBufferTargetType, Integer>();
    mapTarget.put(ARRAY_BUFFER, GL_ARRAY_BUFFER());
    mapTarget.put(COPY_READ_BUFFER, GL_COPY_READ_BUFFER());
    mapTarget.put(COPY_WRITE_BUFFER, GL_COPY_WRITE_BUFFER());
    mapTarget.put(ELEMENT_ARRAY_BUFFER, GL_ELEMENT_ARRAY_BUFFER());
    mapTarget.put(PIXEL_PACK_BUFFER, GL_PIXEL_PACK_BUFFER());
    mapTarget.put(PIXEL_UNPACK_BUFFER, GL_PIXEL_UNPACK_BUFFER());
    mapTarget.put(TEXTURE_BUFFER, GL_TEXTURE_BUFFER());
    mapTarget.put(TRANSFORM_FEEDBACK_BUFFER, GL_TRANSFORM_FEEDBACK_BUFFER());
    mapTarget.put(UNIFORM_BUFFER, GL_UNIFORM_BUFFER());
    bufferTargetTypeMap = Collections.unmodifiableMap(mapTarget);

    Map<CoreBufferAccessType, Integer> mapAccess = new Hashtable<CoreBufferAccessType, Integer>();
    mapAccess.put(READ_ONLY, GL_READ_ONLY());
    mapAccess.put(WRITE_ONLY, GL_WRITE_ONLY());
    mapAccess.put(READ_WRITE, GL_READ_WRITE());
    bufferAccessTypeMap = Collections.unmodifiableMap(mapAccess);
  }

  @Override
  public String name() {
    return "jogl";
  }

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
  public int GL_COPY_READ_BUFFER() {
    return GL2.GL_COPY_READ_BUFFER;
  }

  @Override
  public int GL_COPY_WRITE_BUFFER() {
    return GL2.GL_COPY_WRITE_BUFFER;
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
  public int GL_DEPTH_STENCIL_ATTACHMENT() {
    return GL2ES3.GL_DEPTH_STENCIL_ATTACHMENT;
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
  public int GL_RGB32F() {
    return GL.GL_RGB32F;
  }

  @Override
  public int GL_RGB32I() {
    return GL3.GL_RGB32I;
  }

  @Override
  public int GL_RGB32UI() {
    return GL3.GL_RGB32UI;
  }

  @Override
  public int GL_RGBA() {
    return GL.GL_RGBA;
  }

  @Override
  public int GL_RGBA16() {
    return GL3.GL_RGBA16;
  }

  @Override
  public int GL_RGBA16F() {
    return GL.GL_RGBA16F;
  }

  @Override
  public int GL_RGBA16I() {
    return GL3.GL_RGBA16I;
  }

  @Override
  public int GL_RGBA16UI() {
    return GL3.GL_RGBA16UI;
  }

  @Override
  public int GL_RGBA32F() {
    return GL.GL_RGBA32F;
  }

  @Override
  public int GL_RGBA32I() {
    return GL3.GL_RGBA32I;
  }

  @Override
  public int GL_RGBA32UI() {
    return GL3.GL_RGBA32UI;
  }

  @Override
  public int GL_RGBA8() {
    return GL.GL_RGBA8;
  }

  @Override
  public int GL_RGBA8I() {
    return GL3.GL_RGBA8I;
  }

  @Override
  public int GL_RGBA8UI() {
    return GL3.GL_RGBA8UI;
  }

  @Override
  public int GL_SCISSOR_TEST() {
    return GL.GL_SCISSOR_TEST;
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
  public int GL_TEXTURE_2D_MULTISAMPLE() {
    return GL2ES3.GL_TEXTURE_2D_MULTISAMPLE;
  }

  @Override
  public int GL_PROXY_TEXTURE_2D_MULTISAMPLE() {
    return GL2ES3.GL_PROXY_TEXTURE_2D_MULTISAMPLE;
  }

  @Override
  public int GL_R16() {
    return GL3.GL_R16;
  }

  @Override
  public int GL_R16F() {
    return GL.GL_R16F;
  }

  @Override
  public int GL_R16I() {
    return GL3.GL_R16I;
  }

  @Override
  public int GL_R16UI() {
    return GL3.GL_R16UI;
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
  public int GL_DEPTH24_STENCIL8() {
    return GL.GL_DEPTH24_STENCIL8;
  }

  @Override
  public int GL_DYNAMIC_DRAW() {
    return GL.GL_DYNAMIC_DRAW;
  }

  @Override
  public int GL_DYNAMIC_READ() {
    return GL2.GL_DYNAMIC_READ;
  }

  @Override
  public int GL_DYNAMIC_COPY() {
    return GL2.GL_DYNAMIC_COPY;
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
  public int GL_STATIC_READ() {
    return GL2.GL_STATIC_READ;
  }

  @Override
  public int GL_STATIC_COPY() {
    return GL2.GL_STATIC_COPY;
  }

  @Override
  public int GL_STREAM_DRAW() {
    return GL2ES2.GL_STREAM_DRAW;
  }

  @Override
  public int GL_STREAM_READ() {
    return GL2.GL_STREAM_READ;
  }

  @Override
  public int GL_STREAM_COPY() {
    return GL2.GL_STREAM_COPY;
  }

  @Override
  public int GL_TRANSFORM_FEEDBACK_BUFFER() {
    return GL2.GL_TRANSFORM_FEEDBACK_BUFFER;
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
  public int GL_DRAW_FRAMEBUFFER() {
    return GL2GL3.GL_DRAW_FRAMEBUFFER;
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
  public int GL_PIXEL_UNPACK_BUFFER() {
    return GL2.GL_PIXEL_UNPACK_BUFFER;
  }

  @Override
  public int GL_PIXEL_UNPACK_BUFFER_BINDING() {
    return GL2.GL_PIXEL_UNPACK_BUFFER_BINDING;
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
  public int GL_PIXEL_PACK_BUFFER() {
    return GL2.GL_PIXEL_PACK_BUFFER;
  }

  @Override
  public int GL_PIXEL_PACK_BUFFER_BINDING() {
    return GL2.GL_PIXEL_PACK_BUFFER_BINDING;
  }

  @Override
  public int GL_STENCIL_INDEX() {
    return GL2ES2.GL_STENCIL_INDEX;
  }

  @Override
  public int GL_TEXTURE_BUFFER() {
    return GL2ES3.GL_TEXTURE_BUFFER;
  }

  @Override
  public int GL_R32F() {
    return GL.GL_R32F;
  }

  @Override
  public int GL_R32I() {
    return GL3.GL_R32I;
  }

  @Override
  public int GL_R32UI() {
    return GL3.GL_R32UI;
  }

  @Override
  public int GL_R8() {
    return GL.GL_R8;
  }

  @Override
  public int GL_R8I() {
    return GL3.GL_R8I;
  }

  @Override
  public int GL_R8UI() {
    return GL3.GL_R8UI;
  }

  @Override
  public int GL_READ_FRAMEBUFFER() {
    return GL.GL_READ_FRAMEBUFFER;
  }

  @Override
  public int GL_READ_ONLY() {
    return GL2.GL_READ_ONLY;
  }

  @Override
  public int GL_READ_WRITE() {
    return GL2.GL_READ_WRITE;
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
  public int GL_RG16() {
    return GL3.GL_RG16;
  }

  @Override
  public int GL_RG16F() {
    return GL.GL_RG16F;
  }

  @Override
  public int GL_RG16I() {
    return GL3.GL_RG16I;
  }

  @Override
  public int GL_RG16UI() {
    return GL3.GL_RG16UI;
  }

  @Override
  public int GL_RG32F() {
    return GL.GL_RG32F;
  }

  @Override
  public int GL_RG32I() {
    return GL3.GL_RG32I;
  }

  @Override
  public int GL_RG32UI() {
    return GL3.GL_RG32UI;
  }

  @Override
  public int GL_RG8() {
    return GL.GL_RG8;
  }

  @Override
  public int GL_RG8I() {
    return GL3.GL_RG8I;
  }

  @Override
  public int GL_RG8UI() {
    return GL3.GL_RG8UI;
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
  public void glBlitFramebuffer(final int srcX0, final int srcY0, final int srcX1, final int srcY1, final int dstX0, final int dstY0, final int dstX1, final int dstY1, final int mask, final int filter) {
    GLContext.getCurrentGL().getGL2ES3().glBlitFramebuffer(srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
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
  public void glClearStencil(final int s) {
    GLContext.getCurrentGL().glClearStencil(s);
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
  public void glDrawRangeElements(final int mode, final int start, final int end, final int count, final int type, final int indices) {
    GLContext.getCurrentGL().getGL2().glDrawRangeElements(mode, start, end, count, type, indices);
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
    return glGetInteger(GLContext.getCurrentGL(), pname);
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
  public void glTexImage2DMultisample(final int target, final int samples, final int internalformat, final int width, final int height, final boolean fixedsamplelocations) {
    GLContext.getCurrentGL().getGL2ES3().glTexImage2DMultisample(target, samples, internalformat, width, height, fixedsamplelocations);
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
  public void glBufferData(final int target, final ByteBuffer data, final int usage) {
    GLContext.getCurrentGL().glBufferData(target, data.remaining(), data, usage);
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
  public void glFinish() {
    GLContext.getCurrentGL().getGL2ES2().glFinish();
  }

  @Override
  public void glFlush() {
    GLContext.getCurrentGL().getGL2ES2().glFlush();
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
  public void glScissor(final int x, final int y, final int width, final int height) {
    GLContext.getCurrentGL().glScissor(x, y, width, height);
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

  @Override
  public long getCurrentContext() {
    return 0;
  }

  @Override
  public void makeContextCurrent(final long context) {

  }

  private boolean errorChecksEnabled = false;

  @Override
  public void checkGLError() {
    checkGLError("");
  }

  @Override
  public void checkGLError(final String msg) {
    checkGLError(msg, new Object[0]);
  }

  @Override
  public void checkGLError(final String msg, final Object...args) {
    checkGLError(false, msg, args);
  }

  @Override
  public void checkGLError(final boolean throwException, final String msg, final Object...args) {
    if (!errorChecksEnabled) return;
    log.checkGLError(this, throwException, msg, args);
  }

  @Override
  public void checkGLError(final boolean throwException, final String msg) {
    checkGLError(throwException, msg, emptyObjectArray);
  }

  @Override
  public void setErrorChecksEnabled(final boolean enabled) {
    errorChecksEnabled = enabled;
  }

  @Override
  public int mapCoreBufferTargetType(final CoreBufferTargetType target) {
    return bufferTargetTypeMap.get(target);
  }

  @Override
  public int mapCoreBufferUsageType(final CoreBufferUsageType usage) {
    return bufferUsageTypeMap.get(usage);
  }

  @Override
  public int mapCoreBufferAccessType(final CoreBufferAccessType access) {
    return bufferAccessTypeMap.get(access);
  }

  @Override
  public int gluBuild2DMipmaps(final int target, final int internalFormat, final int width, final int height, final int format, final int type, final ByteBuffer data) {
    return GLU.createGLU().gluBuild2DMipmaps(target, internalFormat, width, height, format, type, data);
  }

  @Override
  public String gluErrorString(final int glError) {
    return GLU.createGLU().gluErrorString(glError);
  }

  @Override
  public boolean isNPOTSupported() {
    return getGLVersion().checkAgainst(GLVersion.GL20) || isNPOTHardwareSupported();
  }

  @Override
  public boolean isNPOTHardwareSupported() {
    return GLContext.getCurrentGL().isExtensionAvailable(GLExtensions.ARB_texture_non_power_of_two);
  }

  @Override
  public GLVersion getGLVersion() {
    final VersionNumber glVersion = GLContext.getCurrent().getGLVersionNumber();
    return CoreVersion.getGLVersion(glVersion.getMajor(), glVersion.getMinor());
  }

  @Override
  public GLSLVersion getGLSLVersion() {
    final VersionNumber glslVersion = GLContext.getCurrent().getGLSLVersionNumber();
    return CoreVersion.getGLSLVersion(glslVersion.getMajor() * 100 + glslVersion.getMinor());
  }

  @Override
  public float getScaleX() {
    return GLContext.getCurrent().getGLDrawable().getSurfaceWidth() / (float) windowWidth;
  }

  @Override
  public float getScaleY() {
    return GLContext.getCurrent().getGLDrawable().getSurfaceHeight() / (float) windowHeight;
  }

  private int glGetInteger(final GL gl, final int pname) {
    final int[] store = new int[1];
    gl.glGetIntegerv(pname, store, 0);
    return store[0];
  }

  private float glGetFloat(final GL gl, final int pname) {
    final float[] store = new float[1];
    gl.glGetFloatv(pname, store, 0);
    return store[0];
  }

  public void setWindowAttributes(final int width, final int height) {
    this.windowWidth = width;
    this.windowHeight = height;
  }
}
