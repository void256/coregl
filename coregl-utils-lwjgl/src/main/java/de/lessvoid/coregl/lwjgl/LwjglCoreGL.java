package de.lessvoid.coregl.lwjgl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.logging.Logger;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GL41;
import org.lwjgl.util.glu.GLU;

import de.lessvoid.coregl.CoreGLException;
import de.lessvoid.coregl.spi.CoreGL;
import de.lessvoid.coregl.spi.CoreUtil;

/**
 * @author Aaron Mahan &lt;aaron@forerunnergames.com&gt;
 * @author Brian Groenke
 */
public class LwjglCoreGL implements CoreGL {

	private static Logger log = Logger.getLogger(LwjglCoreGL.class.getName());

	private final CoreUtil util = new LwjglCoreUtil();
	
	public LwjglCoreGL() {
		
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
	public int GL_BYTE() {
		return GL11.GL_BYTE;
	}

	@Override
	public int GL_COLOR_BUFFER_BIT() {
		return GL11.GL_COLOR_BUFFER_BIT;
	}
	
	@Override
	public int GL_DEPTH_BUFFER_BIT() {
		return GL11.GL_DEPTH_BUFFER_BIT;
	}

	@Override
	public int GL_CULL_FACE() {
		return GL11.GL_CULL_FACE;
	}

	@Override
	public int GL_DEPTH_TEST() {
		return GL11.GL_DEPTH_TEST;
	}

	@Override
	public int GL_DST_COLOR() {
		return GL11.GL_DST_COLOR;
	}

	@Override
	public int GL_FALSE() {
		return GL11.GL_FALSE;
	}

	@Override
	public int GL_FLOAT() {
		return GL11.GL_FLOAT;
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
	public int GL_LUMINANCE() {
		return GL11.GL_LUMINANCE;
	}

	@Override
	public int GL_LUMINANCE_ALPHA() {
		return GL11.GL_LUMINANCE_ALPHA;
	}

	@Override
	public int GL_MAX_TEXTURE_SIZE() {
		return GL11.GL_MAX_TEXTURE_SIZE;
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
	public int GL_NO_ERROR() {
		return GL11.GL_NO_ERROR;
	}

	@Override
	public int GL_NOTEQUAL() {
		return GL11.GL_NOTEQUAL;
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
	public int GL_POINTS() {
		return GL11.GL_POINTS;
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
	public int GL_TEXTURE_2D() {
		return GL11.GL_TEXTURE_2D;
	}

	@Override
	public int GL_TEXTURE_BINDING_2D() {
		return GL11.GL_TEXTURE_BINDING_2D;
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
	public int GL_TRIANGLES() {
		return GL11.GL_TRIANGLES;
	}

	@Override
	public int GL_TRIANGLE_STRIP() {
		return GL11.GL_TRIANGLE_STRIP;
	}

	@Override
	public int GL_TRIANGLE_FAN() {
		return GL11.GL_TRIANGLE_FAN;
	}

	@Override
	public int GL_TRUE() {
		return GL11.GL_TRUE;
	}

	@Override
	public int GL_UNSIGNED_BYTE() {
		return GL11.GL_UNSIGNED_BYTE;
	}

	@Override
	public int GL_UNSIGNED_SHORT() {
		return GL11.GL_UNSIGNED_SHORT;
	}

	@Override
	public int GL_UNSIGNED_SHORT_4_4_4_4() {
		return GL12.GL_UNSIGNED_SHORT_4_4_4_4;
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
	public int GL_VIEWPORT() {
		return GL11.GL_VIEWPORT;
	}

	@Override
	public int GL_ZERO() {
		return GL11.GL_ZERO;
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
	public int GL_DST_ALPHA() {
		return GL11.GL_DST_ALPHA;
	}
	
	@Override
	public int GL_MAX() {
		return GL14.GL_MAX;
	}

	@Override
	public int GL_FUNC_ADD() {
		return GL14.GL_FUNC_ADD;
	}

	@Override
	public int GL_ACTIVE_TEXTURE() {
		return GL13.GL_ACTIVE_TEXTURE;
	}

	@Override
	public int GL_ARRAY_BUFFER() {
		return GL15.GL_ARRAY_BUFFER;
	}

	@Override
	public int GL_BITMAP() {
		return GL11.GL_BITMAP;
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
	public int GL_BLUE() {
		return GL11.GL_BLUE;
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
	public int GL_CURRENT_PROGRAM() {
		return GL20.GL_CURRENT_PROGRAM;
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
	public int GL_FRAGMENT_SHADER() {
		return GL20.GL_FRAGMENT_SHADER;
	}

	@Override
	public int GL_GEOMETRY_SHADER() {
		return GL32.GL_GEOMETRY_SHADER;
	}

	@Override
	public int GL_GREEN() {
		return GL11.GL_GREEN;
	}

	@Override
	public int GL_INT() {
		return GL11.GL_INT;
	}

	@Override
	public int GL_LINK_STATUS() {
		return GL20.GL_LINK_STATUS;
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
	public int GL_RED() {
		return GL11.GL_RED;
	}

	@Override
	public int GL_STATIC_DRAW() {
		return GL15.GL_STATIC_DRAW;
	}

	@Override
	public int GL_STREAM_DRAW() {
		return GL15.GL_STREAM_DRAW;
	}

	@Override
	public int GL_TEXTURE0() {
		return GL13.GL_TEXTURE0;
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
	public int GL_HALF_FLOAT() {
		return GL30.GL_HALF_FLOAT;
	}

	@Override
	public int GL_DOUBLE() {
		return GL11.GL_DOUBLE;
	}

	@Override
	public int GL_FIXED() {
		return GL41.GL_FIXED;
	}

	@Override
	public int GL_INT_2_10_10_10_REV() {
		return GL33.GL_INT_2_10_10_10_REV;
	}

	@Override
	public int GL_UNSIGNED_INT_10F_11F_11F_REV() {
		return GL30.GL_UNSIGNED_INT_10F_11F_11F_REV;
	}

	@Override
	public int GL_UNSIGNED_SHORT_5_6_5_REV() {
		return GL12.GL_UNSIGNED_SHORT_5_6_5_REV;
	}

	@Override
	public int GL_UNSIGNED_SHORT_4_4_4_4_REV() {
		return GL12.GL_UNSIGNED_SHORT_4_4_4_4_REV;
	}

	@Override
	public int GL_UNSIGNED_SHORT_1_5_5_5_REV() {
		return GL12.GL_UNSIGNED_SHORT_1_5_5_5_REV;
	}

	@Override
	public int GL_VERTEX_SHADER() {
		return GL20.GL_VERTEX_SHADER;
	}

	@Override
	public int GL_WRITE_ONLY() {
		return GL15.GL_WRITE_ONLY;
	}

	@Override
	public int GL_UNIFORM_OFFSET() {
		return GL31.GL_UNIFORM_OFFSET;
	}

	@Override
	public int GL_UNIFORM_ARRAY_STRIDE() {
		return GL31.GL_UNIFORM_ARRAY_STRIDE;
	}

	@Override
	public int GL_UNIFORM_MATRIX_STRIDE() {
		return GL31.GL_UNIFORM_MATRIX_STRIDE;
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
	public int GL_PACK_ALIGNMENT() {
		return GL11.GL_PACK_ALIGNMENT;
	}

	@Override
	public int GL_STENCIL_INDEX() {
		return GL11.GL_STENCIL_INDEX;
	}

	@Override
	public int GL_TEXTURE_BUFFER() {
		return GL31.GL_TEXTURE_BUFFER;
	}

	@Override
	public int GL_R32F() {
		return GL30.GL_R32F;
	}
	
	@Override
	public int GL_TEXTURE_2D_ARRAY() {
		return GL30.GL_TEXTURE_2D_ARRAY;
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
	public int GL_FRAMEBUFFER_UNDEFINED() {
		return GL30.GL_FRAMEBUFFER_UNDEFINED;
	}

	@Override
	public int GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT() {
		return GL30.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT;
	}

	@Override
	public int GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT() {
		return GL30.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT;
	}

	@Override
	public int GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER() {
		return GL30.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER;
	}

	@Override
	public int GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER() {
		return GL30.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER;
	}

	@Override
	public int GL_FRAMEBUFFER_UNSUPPORTED() {
		return GL30.GL_FRAMEBUFFER_UNSUPPORTED;
	}

	@Override
	public int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE() {
		return GL30.GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE;
	}

	@Override
	public int GL_COLOR_ATTACHMENT0() {
		return GL30.GL_COLOR_ATTACHMENT0;
	}

	@Override
	public int GL_RENDERBUFFER() {
		return GL30.GL_RENDERBUFFER;
	}

	@Override
	public int GL_STENCIL_INDEX8() {
		return GL30.GL_STENCIL_INDEX8;
	}

	@Override
	public int GL_STENCIL_ATTACHMENT() {
		return GL30.GL_STENCIL_ATTACHMENT;
	}
	
	@Override
	public int GL_VERSION() {
		return GL11.GL_VERSION;
	}
	
	@Override
	public int GL_SHADING_LANGUAGE_VERSION() {
		return GL20.GL_SHADING_LANGUAGE_VERSION;
	}

	@Override
	public int GL_VENDOR() {
		return GL11.GL_VENDOR;
	}

	@Override
	public int GL_RENDERER() {
		return GL11.GL_RENDERER;
	}

	@Override
	public int GL_MAX_VERTEX_ATTRIBS() {
		return GL20.GL_MAX_VERTEX_ATTRIBS;
	}

	@Override
	public int GL_MAX_3D_TEXTURE_SIZE() {
		return GL12.GL_MAX_3D_TEXTURE_SIZE;
	}
	
	@Override
	public int GL_UNIFORM_BUFFER() {
		return GL31.GL_UNIFORM_BUFFER;
	}

	@Override
	public void glBindTexture(int target, int texture) {
		GL11.glBindTexture(target, texture);
	}

	@Override
	public void glBlendFunc(int sfactor, int dfactor) {
		GL11.glBlendFunc(sfactor, dfactor);
	}

  @Override
  public void glBlendFuncSeparate(int sfactorRGB, int dfactorRGB, int sfactorAlpha, int dfactorAlpha) {
    GL14.glBlendFuncSeparate(sfactorRGB, dfactorRGB, sfactorAlpha, sfactorAlpha);
  }

	@Override
	public void glClear(int mask) {
		GL11.glClear(mask);
	}

	@Override
	public void glClearColor(float red, float green, float blue, float alpha) {
		GL11.glClearColor(red, green, blue, alpha);
	}

	@Override
	public void glDeleteTextures(int n, IntBuffer textures) {
		GL11.glDeleteTextures(textures);
	}

	@Override
	public void glDisable(int cap) {
		GL11.glDisable(cap);
	}

	@Override
	public void glDrawArrays(int mode, int first, int count) {
		GL11.glDrawArrays(mode, first, count);
	}

	@Override
	public void glDrawElements(int mode, int count, int type, int indices) {
		GL11.glDrawElements(mode, count, type, indices);
	}

	@Override
	public void glEnable(int cap) {
		GL11.glEnable(cap);
	}

	@Override
	public void glGenTextures(int n, IntBuffer textures) {
		GL11.glGenTextures(textures);
	}

	@Override
	public int glGetError() {
		return GL11.glGetError();
	}

	@Override
	public void glGetIntegerv(int pname, int[] params, int offset) {
		IntBuffer paramsBuffer = BufferUtils.createIntBuffer(100);
		GL11.glGetInteger(pname, paramsBuffer);
		for (int i = offset, j = 0; i < params.length; i++, j++) {
			if (j == paramsBuffer.capacity()) return;
			params[i] = paramsBuffer.get(j);
		}
	}

	@Override
	public void glGetIntegerv(int pname, IntBuffer params) {
		GL11.glGetInteger(pname, params);
	}

	@Override
	public int glGetInteger(int pname) {
		return GL11.glGetInteger(pname);
	}
	
	@Override
	public String glGetString(int pname) {
		return GL11.glGetString(pname);
	}
	
	@Override
	public boolean glIsEnabled(int cap) {
		return GL11.glIsEnabled(cap);
	}

	@Override
	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, ByteBuffer pixels) {
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
	}

	@Override
	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, DoubleBuffer pixels) {
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
	}

	@Override
	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, FloatBuffer pixels) {
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
	}

	@Override
	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, IntBuffer pixels) {
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
	}

	@Override
	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, ShortBuffer pixels) {
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
	}
	
	@Override
	public void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, ByteBuffer pixels) {
		GL12.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
	}
	
	@Override
	public void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, DoubleBuffer pixels) {
		GL12.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
	}
	
	@Override
	public void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, FloatBuffer pixels) {
		GL12.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
	}
	
	@Override
	public void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, IntBuffer pixels) {
		GL12.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
	}
	
	@Override
	public void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, ShortBuffer pixels) {
		GL12.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
	}

	@Override
	public void glTexParameterf(int target, int pname, float param) {
		GL11.glTexParameterf(target, pname, param);
	}

	@Override
	public void glTexParameteri(int target, int pname, int param) {
		GL11.glTexParameteri(target, pname, param);
	}

	@Override
	public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, ByteBuffer pixels) {
		GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
	}

	@Override
	public void glTexBuffer(int arg0, int arg1, int arg2) {
		GL31.glTexBuffer(arg0, arg1, arg2);
	}

	@Override
	public void glViewport(int x, int y, int width, int height) {
		GL11.glViewport(x, y, width, height);
	}

	@Override
	public void glActiveTexture(int texture) {
		GL13.glActiveTexture(texture);
	}

	@Override
	public void glAttachShader(int program, int shader) {
		GL20.glAttachShader(program, shader);
	}

	@Override
	public void glBindAttribLocation(int program, int index, String name) {
		GL20.glBindAttribLocation(program, index, name);
	}

	@Override
	public void glBindBuffer(int target, int buffer) {
		GL15.glBindBuffer(target, buffer);
	}

	@Override
	public void glBindVertexArray(int array) {
		GL30.glBindVertexArray(array);
	}

	@Override
	public void glBufferData(int target, IntBuffer data, int usage) {
		GL15.glBufferData(target, data, usage);
	}

	@Override
	public void glBufferData(int target, FloatBuffer data, int usage) {
		GL15.glBufferData(target, data, usage);
	}

	@Override
	public void glBufferData(int target, ShortBuffer data, int usage) {
		GL15.glBufferData(target, data, usage);
	}
	
	@Override
	public void glCompileShader(int shader) {
		GL20.glCompileShader(shader);
	}

	@Override
	public int glCreateProgram() {
		return GL20.glCreateProgram();
	}

	@Override
	public int glCreateShader(int type) {
		return GL20.glCreateShader(type);
	}

	@Override
	public void glDeleteBuffers(int n, IntBuffer buffers) {
		GL15.glDeleteBuffers(buffers);
	}

	@Override
	public void glDeleteVertexArrays(int n, IntBuffer arrays) {
		GL30.glDeleteVertexArrays(arrays);
	}

	@Override
	public void glDrawArraysInstanced(int mode, int first, int count, int primcount) {
		GL31.glDrawArraysInstanced(mode, first, count, primcount);
	}

	@Override
	public void glEnableVertexAttribArray(int index) {
		GL20.glEnableVertexAttribArray(index);
	}
	
	@Override
	public void glVertexAttribDivisor(int index, int divisor) {
		GL33.glVertexAttribDivisor(index, divisor);
	}

	@Override
	public void glDisableVertexAttribArray(int index) {
		GL20.glDisableVertexAttribArray(index);
	}

	@Override
	public void glVertexAttribIPointer(int index, int size, int type, int stride, int buffer) {
		GL30.glVertexAttribIPointer(index, size, type, stride, buffer);
	}

	@Override
	public void glGenBuffers(int n, IntBuffer buffers) {
		GL15.glGenBuffers(buffers);
	}

	@Override
	public void glGenerateMipmap(int target) {
		GL30.glGenerateMipmap(target);
	}

	@Override
	public void glGenVertexArrays(int n, IntBuffer arrays) {
		GL30.glGenVertexArrays(arrays);
	}

	@Override
	public int glGetAttribLocation(int program, String name) {
		return GL20.glGetAttribLocation(program, name);
	}

	@Override
	public void glGetProgramiv(int program, int pname, IntBuffer params) {
		GL20.glGetProgram(program, pname, params);
	}

	@Override
	public String glGetProgramInfoLog(int program) {
		int logLength = GL20.glGetProgrami(program, GL20.GL_INFO_LOG_LENGTH);
		return GL20.glGetProgramInfoLog(program, logLength);
	}

	@Override
	public void glGetShaderiv(int shader, int pname, IntBuffer params) {
		GL20.glGetShader(shader, pname, params);
	}

	@Override
	public String glGetShaderInfoLog(int shader) {
		int logLength = GL20.glGetShaderi(shader, GL20.GL_INFO_LOG_LENGTH);
		return GL20.glGetShaderInfoLog(shader, logLength);
	}

	@Override
	public int glGetUniformLocation(int program, String name) {
		return GL20.glGetUniformLocation(program, name);
	}

	@Override
	public void glLinkProgram(int program) {
		GL20.glLinkProgram(program);
	}

	@Override
	public ByteBuffer glMapBuffer(int target, int access, long length, ByteBuffer oldBuffer) {
		return GL15.glMapBuffer(target, access, oldBuffer);
	}

	@Override
	public void glPrimitiveRestartIndex(int index) {
		GL31.glPrimitiveRestartIndex(index);
	}

	@Override
	public void glShaderSource(int shader, String string) {
		GL20.glShaderSource(shader, string);
	}

	@Override
	public void glUniform1(int location, FloatBuffer values) {
		GL20.glUniform1(location, values);
	}

	@Override
	public void glUniform1f(int location, float v0) {
		GL20.glUniform1f(location, v0);
	}

	@Override
	public void glUniform2f(int location, float v0, float v1) {
		GL20.glUniform2f(location, v0, v1);
	}

	@Override
	public void glUniform3f(int location, float v0, float v1, float v2) {
		GL20.glUniform3f(location, v0, v1, v2);
	}

	@Override
	public void glUniform4f(int location, float v0, float v1, float v2, float v3) {
		GL20.glUniform4f(location, v0, v1, v2, v3);
	}
	
	@Override
	public void glUniform1fv(int location, FloatBuffer ubuff) {
		GL20.glUniform1(location, ubuff);
	}
	
	@Override
	public void glUniform2fv(int location, FloatBuffer ubuff) {
		GL20.glUniform2(location, ubuff);
	}
	
	@Override
	public void glUniform3fv(int location, FloatBuffer ubuff) {
		GL20.glUniform3(location, ubuff);
	}
	
	@Override
	public void glUniform4fv(int location, FloatBuffer ubuff) {
		GL20.glUniform4(location, ubuff);
	}

	@Override
	public void glUniform1i(int location, int v0) {
		GL20.glUniform1i(location, v0);
	}

	@Override
	public void glUniform2i(int location, int v0, int v1) {
		GL20.glUniform2i(location, v0, v1);
	}

	@Override
	public void glUniform3i(int location, int v0, int v1, int v2) {
		GL20.glUniform3i(location, v0, v1, v2);
	}

	@Override
	public void glUniform4i(int location, int v0, int v1, int v2, int v3) {
		GL20.glUniform4i(location, v0, v1, v2, v3);
	}
	
	@Override
	public void glUniform1iv(int location, IntBuffer ubuff) {
		GL20.glUniform1(location, ubuff);
	}
	
	@Override
	public void glUniform2iv(int location, IntBuffer ubuff) {
		GL20.glUniform2(location, ubuff);
	}
	
	@Override
	public void glUniform3iv(int location, IntBuffer ubuff) {
		GL20.glUniform3(location, ubuff);
	}
	
	@Override
	public void glUniform4iv(int location, IntBuffer ubuff) {
		GL20.glUniform4(location, ubuff);
	}

	@Override
	public void glUniformMatrix4(int location, boolean transpose, FloatBuffer matrices) {
		GL20.glUniformMatrix4(location, transpose, matrices);
	}

	@Override
	public boolean glUnmapBuffer(int target) {
		return GL15.glUnmapBuffer(target);
	}

	@Override
	public void glUseProgram(int program) {
		GL20.glUseProgram(program);
	}

	@Override
	public void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, long offset) {
		GL20.glVertexAttribPointer(index, size, type, normalized, stride, offset);
	}

	@Override
	public void glGetUniformIndices(int program, String[] uniformNames,
			IntBuffer indexBuffer) {
		GL31.glGetUniformIndices(program, uniformNames, indexBuffer);
	}

	@Override
	public void glGetActiveUniforms(int program, int uniformCount,
			IntBuffer indices, int pname, IntBuffer params) {
		GL31.glGetActiveUniforms(program, indices, pname, params);
	}

	@Override
	public int glGetUniformBlockIndex(int program, String name) {
		return GL31.glGetUniformBlockIndex(program, name);
	}

	@Override
	public void glUniformBlockBinding(int prog, int blockIndex, int blockBinding) {
		GL31.glUniformBlockBinding(prog, blockIndex, blockBinding);
	}

	@Override
	public void glPixelStorei(int param, int n) {
		GL11.glPixelStorei(param, n);
	}

	@Override
	public void glReadPixels(int x, int y, int width, int height, int format,
			int type, ByteBuffer pixelBuffer) {
		GL11.glReadPixels(x, y, width, height, format, type, pixelBuffer);
	}
	
  @Override
  public void glGetTexImage (int target, int level, int format, int type, ByteBuffer pixelBuffer) {
    GL11.glGetTexImage(target, level, format, type, pixelBuffer);
  }

	@Override
	public void glGenFramebuffers(int n, IntBuffer frameBuffs) {
		GL30.glGenFramebuffers(frameBuffs);
	}

	@Override
	public int glCheckFramebufferStatus(int target) {
		return GL30.glCheckFramebufferStatus(target);
	}

	@Override
	public void glBindFramebuffer(int target, int fbo) {
		GL30.glBindFramebuffer(target, fbo);
	}

	@Override
	public void glDeleteFramebuffers(int fboCount, IntBuffer fboIds) {
		GL30.glDeleteFramebuffers(fboIds);
	}

	@Override
	public void glFramebufferTexture2D(int target, int attachment,
			int textarget, int texture, int level) {
		GL30.glFramebufferTexture2D(target, attachment, textarget, texture, level);
	}

	@Override
	public void glFramebufferTextureLayer(int target, int attachment,
			int texture, int level, int layer) {
		GL30.glFramebufferTextureLayer(target, attachment, texture, level, layer);
	}

	@Override
	public void glDrawBuffer(int mode) {
		GL11.glDrawBuffer(mode);
	}

	@Override
	public void glGenRenderBuffers(int buffCount, IntBuffer buffer) {
		GL30.glGenRenderbuffers(buffer);
	}

	@Override
	public void glBindRenderbuffer(int target, int renderBuffer) {
		GL30.glBindRenderbuffer(target, renderBuffer);
	}

	@Override
	public void glRenderbufferStorage(int target, int internalFormat,
			int width, int height) {
		GL30.glRenderbufferStorage(target, internalFormat, width, height);
	}

	@Override
	public void glFramebufferRenderbuffer(int target, int attachment,
			int renderBufferTarget, int renderBuffer) {
		GL30.glFramebufferRenderbuffer(target, attachment, renderBufferTarget, renderBuffer);
	}
	

	@Override
	public void glBindBufferBase(int target, int bindingPoint, int id) {
		GL30.glBindBufferBase(target, bindingPoint, id);
	}
	
	@Override
	public void glPointSize(int psize) {
		GL11.glPointSize(psize);
	}
	
	@Override
	public void glBlendEquationSeparate(int e1, int e2) {
		GL20.glBlendEquationSeparate(e1, e2);
	}

	private boolean errorCheckingEnabled = true;

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreCheckGL#checkGLError()
	 */
	@Override
	public void checkGLError() {
		checkGLError("");
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreCheckGL#checkGLError(java.lang.String)
	 */
	@Override
	public void checkGLError(final String message) {
		checkGLError(message, false);
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreCheckGL#checkGLError(java.lang.String, boolean)
	 */
	@Override
	public void checkGLError(final String message, final boolean throwException) {
		if(!errorCheckingEnabled)
			return;
		int error = glGetError();
		boolean hasError = false;
		while (error != GL11.GL_NO_ERROR) {
			hasError = true;
			String glerrmsg = GLU.gluErrorString(error);
			StringBuilder stacktrace = new StringBuilder();
			for (StackTraceElement strackTraceElement : Thread.currentThread().getStackTrace()) {
				stacktrace.append(strackTraceElement.toString());
				stacktrace.append("\n");
			}
			log.warning("OpenGL Error: (" + error + ") " + glerrmsg + " {" + message + "} " + stacktrace.toString());
			error = glGetError();
		}

		if (hasError && throwException) {
			throw new CoreGLException("OpenGL Error occurred:" + message);
		}
	}

	@Override
	public void setErrorChecksEnabled(boolean enabled) {
		this.errorCheckingEnabled = enabled;
	}

	@Override
	public CoreUtil getUtil() {
		return util;
	}
}
