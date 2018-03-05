package com.lessvoid.coregl.state;

import com.lessvoid.coregl.CoreBufferAccessType;
import com.lessvoid.coregl.CoreBufferTargetType;
import com.lessvoid.coregl.CoreBufferUsageType;
import com.lessvoid.coregl.CoreVersion.GLSLVersion;
import com.lessvoid.coregl.CoreVersion.GLVersion;
import com.lessvoid.coregl.spi.CoreGL;
import com.lessvoid.coregl.spi.CoreGLSetup;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Wrapper for any CoreGL implementation class that will cache state changes internally so that the same state
 * is not sent multiple times to GL.
 *
 * Created by void on 02.03.16.
 */
public class CoreGLStateWrapper implements CoreGL {
    private final CoreGL gl;
    private int glActiveTextureTexture;
    private int glAttachShaderProgram;
    private int glAttachShaderShader;
    private int glBindAttribLocationProgram;
    private int glBindAttribLocationIndex;
    private String glBindAttribLocationName;
    private int glBindBufferTarget;
    private int glBindBufferBuffer;
    private int glBindBufferBaseTarget;
    private int glBindBufferBaseBindingPoint;
    private int glBindBufferBaseId;
    private int glBindFramebufferTarget;
    private int glBindFramebufferFbo;
    private int glBindRenderbufferTarget;
    private int glBindRenderbufferRenderBuffer;
    private int glBindTextureTarget;
    private int glBindTextureTexture;
    private int glBindVertexArrayArray;
    private int glBlendEquationSeparateModeRGB;
    private int glBlendEquationSeparateModeAlpha;
    private int glBlendFuncSfactor;
    private int glBlendFuncDfactor;
    private int glBlendFuncSeparateSfactorRGB;
    private int glBlendFuncSeparateDfactorRGB;
    private int glBlendFuncSeparateSfactorAlpha;
    private int glBlendFuncSeparateDfactorAlpha;
    private float glClearColorRed;
    private float glClearColorGreen;
    private float glClearColorBlue;
    private float glClearColorAlpha;
    private int glClearStencilS;
    private boolean glColorMaskRed;
    private boolean glColorMaskGreen;
    private boolean glColorMaskBlue;
    private boolean glColorMaskAlpha;
    private boolean glDepthMaskFlag;
    private Set<Integer> glEnableSet = new HashSet<Integer>();
    private int glDrawBufferMode;
    private int glPixelStoreiPname;
    private int glPixelStoreiParam;
    private int glPointSizePSize;
    private int glPrimitiveRestartIndexIndex;
    private int glRenderbufferStorageTarget;
    private int glRenderbufferStorageInternalFormat;
    private int glRenderbufferStorageWidth;
    private int glRenderbufferStorageHeight;
    private int glStencilFuncFunc;
    private int glStencilFuncRef;
    private int glStencilFuncMask;
    private int glStencilFuncSeparateFace;
    private int glStencilFuncSeparateFunc;
    private int glStencilFuncSeparateRef;
    private int glStencilFuncSeparateMask;
    private int glStencilMaskMask;
    private int glStencilMaskSeparateFace;
    private int glStencilMaskSeparateMask;
    private int glStencilOpSFail;
    private int glStencilOpDpFail;
    private int glStencilOpDpPass;
    private int glStencilOpSeparateFace;
    private int glStencilOpSeparateSFail;
    private int glStencilOpSeparateDpFail;
    private int glStencilOpSeparateDpPass;
    private int glTexBufferTarget;
    private int glTexBufferInternalFormat;
    private int glTexBufferBuffer;
    private int glTexParameterfTarget;
    private int glTexParameterfPName;
    private float glTexParameterfParam;
    private int glTexParameteriTarget;
    private int glTexParameteriPName;
    private int glTexParameteriParam;
    private int glUseProgramProgram;
    private int glVertexAttribDivisorIndex;
    private int glVertexAttribDivisorDivisor;
    private int glViewportX;
    private int glViewportY;
    private int glViewportWidth;
    private int glViewportHeight;

    public CoreGLStateWrapper(final CoreGL gl) {
        this.gl = gl;
    }

    @Override
    public String name() {
        return gl.name();
    }

    @Override
    public CoreGLSetup coreGLSetup() {
        return gl.coreGLSetup();
    }

    @Override
    public int GL_ACTIVE_TEXTURE() {
        return gl.GL_ACTIVE_TEXTURE();
    }

    @Override
    public int GL_ALPHA() {
        return gl.GL_ALPHA();
    }

    @Override
    public int GL_ALPHA_TEST() {
        return gl.GL_ALPHA_TEST();
    }

    @Override
    public int GL_ALWAYS() {
        return gl.GL_ALWAYS();
    }

    @Override
    public int GL_ARRAY_BUFFER() {
        return gl.GL_ARRAY_BUFFER();
    }

    @Override
    public int GL_BACK() {
        return gl.GL_BACK();
    }

    @Override
    public int GL_BGR() {
        return gl.GL_BGR();
    }

    @Override
    public int GL_BGRA() {
        return gl.GL_BGRA();
    }

    @Override
    public int GL_BITMAP() {
        return gl.GL_BITMAP();
    }

    @Override
    public int GL_BLEND() {
        return gl.GL_BLEND();
    }

    @Override
    public int GL_BLEND_DST() {
        return gl.GL_BLEND_DST();
    }

    @Override
    public int GL_BLEND_SRC() {
        return gl.GL_BLEND_SRC();
    }

    @Override
    public int GL_BLUE() {
        return gl.GL_BLUE();
    }

    @Override
    public int GL_BYTE() {
        return gl.GL_BYTE();
    }

    @Override
    public int GL_COPY_READ_BUFFER() {
        return gl.GL_COPY_READ_BUFFER();
    }

    @Override
    public int GL_COPY_WRITE_BUFFER() {
        return gl.GL_COPY_WRITE_BUFFER();
    }

    @Override
    public int GL_COLOR_ATTACHMENT0() {
        return gl.GL_COLOR_ATTACHMENT0();
    }

    @Override
    public int GL_COLOR_BUFFER_BIT() {
        return gl.GL_COLOR_BUFFER_BIT();
    }

    @Override
    public int GL_COLOR_INDEX() {
        return gl.GL_COLOR_INDEX();
    }

    @Override
    public int GL_COMPILE_STATUS() {
        return gl.GL_COMPILE_STATUS();
    }

    @Override
    public int GL_COMPRESSED_ALPHA() {
        return gl.GL_COMPRESSED_ALPHA();
    }

    @Override
    public int GL_COMPRESSED_LUMINANCE() {
        return gl.GL_COMPRESSED_LUMINANCE();
    }

    @Override
    public int GL_COMPRESSED_LUMINANCE_ALPHA() {
        return gl.GL_COMPRESSED_LUMINANCE_ALPHA();
    }

    @Override
    public int GL_COMPRESSED_RGB() {
        return gl.GL_COMPRESSED_RGB();
    }

    @Override
    public int GL_COMPRESSED_RGBA() {
        return gl.GL_COMPRESSED_RGBA();
    }

    @Override
    public int GL_CULL_FACE() {
        return gl.GL_CULL_FACE();
    }

    @Override
    public int GL_CURRENT_PROGRAM() {
        return gl.GL_CURRENT_PROGRAM();
    }

    @Override
    public int GL_DECR() {
        return gl.GL_DECR();
    }

    @Override
    public int GL_DECR_WRAP() {
        return gl.GL_DECR_WRAP();
    }

    @Override
    public int GL_DEPTH24_STENCIL8() {
        return gl.GL_DEPTH24_STENCIL8();
    }

    @Override
    public int GL_DEPTH_BUFFER_BIT() {
        return gl.GL_DEPTH_BUFFER_BIT();
    }

    @Override
    public int GL_DEPTH_STENCIL_ATTACHMENT() {
        return gl.GL_DEPTH_STENCIL_ATTACHMENT();
    }

    @Override
    public int GL_DEPTH_TEST() {
        return gl.GL_DEPTH_TEST();
    }

    @Override
    public int GL_DOUBLE() {
        return gl.GL_DOUBLE();
    }

    @Override
    public int GL_DRAW_FRAMEBUFFER() {
        return gl.GL_DRAW_FRAMEBUFFER();
    }

    @Override
    public int GL_DST_ALPHA() {
        return gl.GL_DST_ALPHA();
    }

    @Override
    public int GL_DST_COLOR() {
        return gl.GL_DST_COLOR();
    }

    @Override
    public int GL_DYNAMIC_DRAW() {
        return gl.GL_DYNAMIC_DRAW();
    }

    @Override
    public int GL_DYNAMIC_READ() {
        return gl.GL_DYNAMIC_READ();
    }

    @Override
    public int GL_DYNAMIC_COPY() {
        return gl.GL_DYNAMIC_COPY();
    }

    @Override
    public int GL_ELEMENT_ARRAY_BUFFER() {
        return gl.GL_ELEMENT_ARRAY_BUFFER();
    }

    @Override
    public int GL_EQUAL() {
        return gl.GL_EQUAL();
    }

    @Override
    public int GL_FALSE() {
        return gl.GL_FALSE();
    }

    @Override
    public int GL_FIXED() {
        return gl.GL_FIXED();
    }

    @Override
    public int GL_FLOAT() {
        return gl.GL_FLOAT();
    }

    @Override
    public int GL_FRAGMENT_SHADER() {
        return gl.GL_FRAGMENT_SHADER();
    }

    @Override
    public int GL_FRAMEBUFFER() {
        return gl.GL_FRAMEBUFFER();
    }

    @Override
    public int GL_FRAMEBUFFER_COMPLETE() {
        return gl.GL_FRAMEBUFFER_COMPLETE();
    }

    @Override
    public int GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT() {
        return gl.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT();
    }

    @Override
    public int GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER() {
        return gl.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER();
    }

    @Override
    public int GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT() {
        return gl.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT();
    }

    @Override
    public int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE() {
        return gl.GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE();
    }

    @Override
    public int GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER() {
        return gl.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER();
    }

    @Override
    public int GL_FRAMEBUFFER_UNDEFINED() {
        return gl.GL_FRAMEBUFFER_UNDEFINED();
    }

    @Override
    public int GL_FRAMEBUFFER_UNSUPPORTED() {
        return gl.GL_FRAMEBUFFER_UNSUPPORTED();
    }

    @Override
    public int GL_FRONT() {
        return gl.GL_FRONT();
    }

    @Override
    public int GL_FRONT_AND_BACK() {
        return gl.GL_FRONT_AND_BACK();
    }

    @Override
    public int GL_FUNC_ADD() {
        return gl.GL_FUNC_ADD();
    }

    @Override
    public int GL_GEOMETRY_SHADER() {
        return gl.GL_GEOMETRY_SHADER();
    }

    @Override
    public int GL_GEQUAL() {
        return gl.GL_GEQUAL();
    }

    @Override
    public int GL_GREATER() {
        return gl.GL_GREATER();
    }

    @Override
    public int GL_GREEN() {
        return gl.GL_GREEN();
    }

    @Override
    public int GL_HALF_FLOAT() {
        return gl.GL_HALF_FLOAT();
    }

    @Override
    public int GL_LESS() {
        return gl.GL_LESS();
    }

    @Override
    public int GL_LEQUAL() {
        return gl.GL_LEQUAL();
    }

    @Override
    public int GL_INCR() {
        return gl.GL_INCR();
    }

    @Override
    public int GL_INCR_WRAP() {
        return gl.GL_INCR_WRAP();
    }

    @Override
    public int GL_INT() {
        return gl.GL_INT();
    }

    @Override
    public int GL_INT_2_10_10_10_REV() {
        return gl.GL_INT_2_10_10_10_REV();
    }

    @Override
    public int GL_INVERT() {
        return gl.GL_INVERT();
    }

    @Override
    public int GL_INVALID_ENUM() {
        return gl.GL_INVALID_ENUM();
    }

    @Override
    public int GL_INVALID_OPERATION() {
        return gl.GL_INVALID_OPERATION();
    }

    @Override
    public int GL_INVALID_VALUE() {
        return gl.GL_INVALID_VALUE();
    }

    @Override
    public int GL_KEEP() {
        return gl.GL_KEEP();
    }

    @Override
    public int GL_LINEAR() {
        return gl.GL_LINEAR();
    }

    @Override
    public int GL_LINEAR_MIPMAP_LINEAR() {
        return gl.GL_LINEAR_MIPMAP_LINEAR();
    }

    @Override
    public int GL_LINEAR_MIPMAP_NEAREST() {
        return gl.GL_LINEAR_MIPMAP_NEAREST();
    }

    @Override
    public int GL_LINE_STRIP() {
        return gl.GL_LINE_STRIP();
    }

    @Override
    public int GL_LINE_STRIP_ADJACENCY() {
        return gl.GL_LINE_STRIP_ADJACENCY();
    }

    @Override
    public int GL_LINK_STATUS() {
        return gl.GL_LINK_STATUS();
    }

    @Override
    public int GL_LUMINANCE() {
        return gl.GL_LUMINANCE();
    }

    @Override
    public int GL_LUMINANCE_ALPHA() {
        return gl.GL_LUMINANCE_ALPHA();
    }

    @Override
    public int GL_MAX() {
        return gl.GL_MAX();
    }

    @Override
    public int GL_MAX_3D_TEXTURE_SIZE() {
        return gl.GL_MAX_3D_TEXTURE_SIZE();
    }

    @Override
    public int GL_MAX_TEXTURE_SIZE() {
        return gl.GL_MAX_TEXTURE_SIZE();
    }

    @Override
    public int GL_MAX_VERTEX_ATTRIBS() {
        return gl.GL_MAX_VERTEX_ATTRIBS();
    }

    @Override
    public int GL_NEAREST() {
        return gl.GL_NEAREST();
    }

    @Override
    public int GL_NEAREST_MIPMAP_LINEAR() {
        return gl.GL_NEAREST_MIPMAP_LINEAR();
    }

    @Override
    public int GL_NEAREST_MIPMAP_NEAREST() {
        return gl.GL_NEAREST_MIPMAP_NEAREST();
    }

    @Override
    public int GL_NEVER() {
        return gl.GL_NEVER();
    }

    @Override
    public int GL_NOTEQUAL() {
        return gl.GL_NOTEQUAL();
    }

    @Override
    public int GL_NO_ERROR() {
        return gl.GL_NO_ERROR();
    }

    @Override
    public int GL_ONE() {
        return gl.GL_ONE();
    }

    @Override
    public int GL_ONE_MINUS_DST_ALPHA() {
        return gl.GL_ONE_MINUS_DST_ALPHA();
    }

    @Override
    public int GL_ONE_MINUS_SRC_ALPHA() {
        return gl.GL_ONE_MINUS_SRC_ALPHA();
    }

    @Override
    public int GL_OUT_OF_MEMORY() {
        return gl.GL_OUT_OF_MEMORY();
    }

    @Override
    public int GL_PACK_ALIGNMENT() {
        return gl.GL_PACK_ALIGNMENT();
    }

    @Override
    public int GL_PIXEL_PACK_BUFFER() {
        return gl.GL_PIXEL_PACK_BUFFER();
    }

    @Override
    public int GL_PIXEL_PACK_BUFFER_BINDING() {
        return gl.GL_PIXEL_PACK_BUFFER_BINDING();
    }

    @Override
    public int GL_POINTS() {
        return gl.GL_POINTS();
    }

    @Override
    public int GL_PRIMITIVE_RESTART() {
        return gl.GL_PRIMITIVE_RESTART();
    }

    @Override
    public int GL_PRIMITIVE_RESTART_INDEX() {
        return gl.GL_PRIMITIVE_RESTART_INDEX();
    }

    @Override
    public int GL_R32F() {
        return gl.GL_R32F();
    }

    @Override
    public int GL_READ_FRAMEBUFFER() {
        return gl.GL_READ_FRAMEBUFFER();
    }

    @Override
    public int GL_READ_ONLY() {
        return gl.GL_READ_ONLY();
    }

    @Override
    public int GL_READ_WRITE() {
        return gl.GL_READ_WRITE();
    }

    @Override
    public int GL_RED() {
        return gl.GL_RED();
    }

    @Override
    public int GL_RENDERBUFFER() {
        return gl.GL_RENDERBUFFER();
    }

    @Override
    public int GL_RENDERER() {
        return gl.GL_RENDERER();
    }

    @Override
    public int GL_REPLACE() {
        return gl.GL_REPLACE();
    }

    @Override
    public int GL_RGB() {
        return gl.GL_RGB();
    }

    @Override
    public int GL_RGBA() {
        return gl.GL_RGBA();
    }

    @Override
    public int GL_SHADING_LANGUAGE_VERSION() {
        return gl.GL_SHADING_LANGUAGE_VERSION();
    }

    @Override
    public int GL_SHORT() {
        return gl.GL_SHORT();
    }

    @Override
    public int GL_SRC_ALPHA() {
        return gl.GL_SRC_ALPHA();
    }

    @Override
    public int GL_STACK_OVERFLOW() {
        return gl.GL_STACK_OVERFLOW();
    }

    @Override
    public int GL_STACK_UNDERFLOW() {
        return gl.GL_STACK_UNDERFLOW();
    }

    @Override
    public int GL_STATIC_DRAW() {
        return gl.GL_STATIC_DRAW();
    }

    @Override
    public int GL_STATIC_READ() {
        return gl.GL_STATIC_READ();
    }

    @Override
    public int GL_STATIC_COPY() {
        return gl.GL_STATIC_COPY();
    }

    @Override
    public int GL_STENCIL_ATTACHMENT() {
        return gl.GL_STENCIL_ATTACHMENT();
    }

    @Override
    public int GL_STENCIL_BUFFER_BIT() {
        return gl.GL_STENCIL_BUFFER_BIT();
    }

    @Override
    public int GL_STENCIL_INDEX() {
        return gl.GL_STENCIL_INDEX();
    }

    @Override
    public int GL_STENCIL_INDEX8() {
        return gl.GL_STENCIL_INDEX8();
    }

    @Override
    public int GL_STENCIL_TEST() {
        return gl.GL_STENCIL_TEST();
    }

    @Override
    public int GL_STREAM_DRAW() {
        return gl.GL_STREAM_DRAW();
    }

    @Override
    public int GL_STREAM_READ() {
        return gl.GL_STREAM_READ();
    }

    @Override
    public int GL_STREAM_COPY() {
        return gl.GL_STREAM_COPY();
    }

    @Override
    public int GL_TRANSFORM_FEEDBACK_BUFFER() {
        return gl.GL_TRANSFORM_FEEDBACK_BUFFER();
    }

    @Override
    public int GL_TEXTURE0() {
        return gl.GL_TEXTURE0();
    }

    @Override
    public int GL_TEXTURE_2D() {
        return gl.GL_TEXTURE_2D();
    }

    @Override
    public int GL_TEXTURE_2D_ARRAY() {
        return gl.GL_TEXTURE_2D_ARRAY();
    }

    @Override
    public int GL_TEXTURE_2D_MULTISAMPLE() {
        return gl.GL_TEXTURE_2D_MULTISAMPLE();
    }

    @Override
    public int GL_PROXY_TEXTURE_2D_MULTISAMPLE() {
        return gl.GL_PROXY_TEXTURE_2D_MULTISAMPLE();
    }

    @Override
    public int GL_TEXTURE_3D() {
        return gl.GL_TEXTURE_3D();
    }

    @Override
    public int GL_TEXTURE_BINDING_2D() {
        return gl.GL_TEXTURE_BINDING_2D();
    }

    @Override
    public int GL_TEXTURE_BINDING_3D() {
        return gl.GL_TEXTURE_BINDING_3D();
    }

    @Override
    public int GL_TEXTURE_BUFFER() {
        return gl.GL_TEXTURE_BUFFER();
    }

    @Override
    public int GL_TEXTURE_CUBE_MAP_NEGATIVE_X() {
        return gl.GL_TEXTURE_CUBE_MAP_NEGATIVE_X();
    }

    @Override
    public int GL_TEXTURE_CUBE_MAP_NEGATIVE_Y() {
        return gl.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y();
    }

    @Override
    public int GL_TEXTURE_CUBE_MAP_NEGATIVE_Z() {
        return gl.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z();
    }

    @Override
    public int GL_TEXTURE_CUBE_MAP_POSITIVE_X() {
        return gl.GL_TEXTURE_CUBE_MAP_POSITIVE_X();
    }

    @Override
    public int GL_TEXTURE_CUBE_MAP_POSITIVE_Y() {
        return gl.GL_TEXTURE_CUBE_MAP_POSITIVE_Y();
    }

    @Override
    public int GL_TEXTURE_CUBE_MAP_POSITIVE_Z() {
        return gl.GL_TEXTURE_CUBE_MAP_POSITIVE_Z();
    }

    @Override
    public int GL_TEXTURE_MAG_FILTER() {
        return gl.GL_TEXTURE_MAG_FILTER();
    }

    @Override
    public int GL_TEXTURE_MIN_FILTER() {
        return gl.GL_TEXTURE_MIN_FILTER();
    }

    @Override
    public int GL_TRIANGLES() {
        return gl.GL_TRIANGLES();
    }

    @Override
    public int GL_TRIANGLE_FAN() {
        return gl.GL_TRIANGLE_FAN();
    }

    @Override
    public int GL_TRIANGLE_STRIP() {
        return gl.GL_TRIANGLE_STRIP();
    }

    @Override
    public int GL_TRUE() {
        return gl.GL_TRUE();
    }

    @Override
    public int GL_UNIFORM_ARRAY_STRIDE() {
        return gl.GL_UNIFORM_ARRAY_STRIDE();
    }

    @Override
    public int GL_UNIFORM_BUFFER() {
        return gl.GL_UNIFORM_BUFFER();
    }

    @Override
    public int GL_UNIFORM_MATRIX_STRIDE() {
        return gl.GL_UNIFORM_MATRIX_STRIDE();
    }

    @Override
    public int GL_UNIFORM_OFFSET() {
        return gl.GL_UNIFORM_OFFSET();
    }

    @Override
    public int GL_PIXEL_UNPACK_BUFFER() {
        return gl.GL_PIXEL_UNPACK_BUFFER();
    }

    @Override
    public int GL_PIXEL_UNPACK_BUFFER_BINDING() {
        return gl.GL_PIXEL_UNPACK_BUFFER_BINDING();
    }

    @Override
    public int GL_UNSIGNED_BYTE() {
        return gl.GL_UNSIGNED_BYTE();
    }

    @Override
    public int GL_UNSIGNED_BYTE_2_3_3_REV() {
        return gl.GL_UNSIGNED_BYTE_2_3_3_REV();
    }

    @Override
    public int GL_UNSIGNED_BYTE_3_3_2() {
        return gl.GL_UNSIGNED_BYTE_3_3_2();
    }

    @Override
    public int GL_UNSIGNED_INT() {
        return gl.GL_UNSIGNED_INT();
    }

    @Override
    public int GL_UNSIGNED_INT_10F_11F_11F_REV() {
        return gl.GL_UNSIGNED_INT_10F_11F_11F_REV();
    }

    @Override
    public int GL_UNSIGNED_INT_10_10_10_2() {
        return gl.GL_UNSIGNED_INT_10_10_10_2();
    }

    @Override
    public int GL_UNSIGNED_INT_2_10_10_10_REV() {
        return gl.GL_UNSIGNED_INT_2_10_10_10_REV();
    }

    @Override
    public int GL_UNSIGNED_INT_8_8_8_8() {
        return gl.GL_UNSIGNED_INT_8_8_8_8();
    }

    @Override
    public int GL_UNSIGNED_INT_8_8_8_8_REV() {
        return gl.GL_UNSIGNED_INT_8_8_8_8_REV();
    }

    @Override
    public int GL_UNSIGNED_SHORT() {
        return gl.GL_UNSIGNED_SHORT();
    }

    @Override
    public int GL_UNSIGNED_SHORT_1_5_5_5_REV() {
        return gl.GL_UNSIGNED_SHORT_1_5_5_5_REV();
    }

    @Override
    public int GL_UNSIGNED_SHORT_4_4_4_4() {
        return gl.GL_UNSIGNED_SHORT_4_4_4_4();
    }

    @Override
    public int GL_UNSIGNED_SHORT_4_4_4_4_REV() {
        return gl.GL_UNSIGNED_SHORT_4_4_4_4_REV();
    }

    @Override
    public int GL_UNSIGNED_SHORT_5_5_5_1() {
        return gl.GL_UNSIGNED_SHORT_5_5_5_1();
    }

    @Override
    public int GL_UNSIGNED_SHORT_5_6_5() {
        return gl.GL_UNSIGNED_SHORT_5_6_5();
    }

    @Override
    public int GL_UNSIGNED_SHORT_5_6_5_REV() {
        return gl.GL_UNSIGNED_SHORT_5_6_5_REV();
    }

    @Override
    public int GL_VENDOR() {
        return gl.GL_VENDOR();
    }

    @Override
    public int GL_VERSION() {
        return gl.GL_VERSION();
    }

    @Override
    public int GL_VERTEX_SHADER() {
        return gl.GL_VERTEX_SHADER();
    }

    @Override
    public int GL_VIEWPORT() {
        return gl.GL_VIEWPORT();
    }

    @Override
    public int GL_WRITE_ONLY() {
        return gl.GL_WRITE_ONLY();
    }

    @Override
    public int GL_ZERO() {
        return gl.GL_ZERO();
    }

    @Override
    public void glActiveTexture(int texture) {
        if (texture == glActiveTextureTexture) {
            return;
        }
        gl.glActiveTexture(texture);
        glActiveTextureTexture = texture;
    }

    @Override
    public void glAttachShader(int program, int shader) {
        if (program == glAttachShaderProgram &&
            shader == glAttachShaderShader) {
            return;
        }
        gl.glAttachShader(program, shader);
        glAttachShaderProgram = program;
        glAttachShaderShader = shader;
    }

    @Override
    public void glBindAttribLocation(int program, int index, String name) {
        if (program == glBindAttribLocationProgram &&
            index == glBindAttribLocationIndex &&
            stringEquals(name, glBindAttribLocationName)) {
            return;
        }
        gl.glBindAttribLocation(program, index, name);
        glBindAttribLocationProgram = program;
        glBindAttribLocationIndex = index;
        glBindAttribLocationName = name;
    }

    @Override
    public void glBindBuffer(int target, int buffer) {
        if (target == glBindBufferTarget &&
            buffer == glBindBufferBuffer) {
            return;
        }
        gl.glBindBuffer(target, buffer);
        glBindBufferTarget = target;
        glBindBufferBuffer = buffer;
    }

    @Override
    public void glBindBufferBase(int target, int bindingPoint, int id) {
        if (target == glBindBufferBaseTarget &&
            bindingPoint == glBindBufferBaseBindingPoint &&
            id == glBindBufferBaseId) {
            return;
        }
        gl.glBindBufferBase(target, bindingPoint, id);
        glBindBufferBaseTarget = target;
        glBindBufferBaseBindingPoint = bindingPoint;
        glBindBufferBaseId = id;
    }

    @Override
    public void glBindFramebuffer(int target, int fbo) {
        if (target == glBindFramebufferTarget &&
            fbo == glBindFramebufferFbo) {
            return;
        }
        gl.glBindFramebuffer(target, fbo);
        glBindFramebufferTarget = target;
        glBindFramebufferFbo = fbo;
    }

    @Override
    public void glBindRenderbuffer(int target, int renderBuffer) {
        if (target == glBindRenderbufferTarget &&
            renderBuffer == glBindRenderbufferRenderBuffer) {
            return;
        }
        gl.glBindRenderbuffer(target, renderBuffer);
        glBindRenderbufferTarget = target;
        glBindRenderbufferRenderBuffer = renderBuffer;
    }

    @Override
    public void glBindTexture(int target, int texture) {
        if (target == glBindTextureTarget &&
            texture == glBindTextureTexture) {
            return;
        }
        gl.glBindTexture(target, texture);
        glBindTextureTarget = target;
        glBindTextureTexture = texture;
    }

    @Override
    public void glBindVertexArray(int array) {
        if (array == glBindVertexArrayArray) {
            return;
        }
        gl.glBindVertexArray(array);
        glBindVertexArrayArray = array;
    }

    @Override
    public void glBlendEquationSeparate(int modeRGB, int modeAlpha) {
        if (modeRGB == glBlendEquationSeparateModeRGB &&
            modeAlpha == glBlendEquationSeparateModeAlpha) {
            return;
        }
        gl.glBlendEquationSeparate(modeRGB, modeAlpha);
        glBlendEquationSeparateModeRGB = modeRGB;
        glBlendEquationSeparateModeAlpha = modeAlpha;
    }

    @Override
    public void glBlendFunc(int sfactor, int dfactor) {
        if (sfactor == glBlendFuncSfactor &&
            dfactor == glBlendFuncDfactor) {
            return;
        }
        gl.glBlendFunc(sfactor, dfactor);
        glBlendFuncSfactor = sfactor;
        glBlendFuncDfactor = dfactor;
    }

    @Override
    public void glBlendFuncSeparate(int sfactorRGB, int dfactorRGB, int sfactorAlpha, int dfactorAlpha) {
        if (sfactorRGB == glBlendFuncSeparateSfactorRGB &&
            dfactorRGB == glBlendFuncSeparateDfactorRGB &&
            sfactorAlpha == glBlendFuncSeparateSfactorAlpha &&
            dfactorAlpha == glBlendFuncSeparateDfactorAlpha) {
            return;
        }
        gl.glBlendFuncSeparate(sfactorRGB, dfactorRGB, sfactorAlpha, dfactorAlpha);
        glBlendFuncSeparateSfactorRGB = sfactorRGB;
        glBlendFuncSeparateDfactorRGB = dfactorRGB;
        glBlendFuncSeparateSfactorAlpha = sfactorAlpha;
        glBlendFuncSeparateDfactorAlpha = dfactorAlpha;
    }

    @Override
    public void glBlitFramebuffer(final int srcX0, final int srcY0, final int srcX1, final int srcY1, final int dstX0, final int dstY0, final int dstX1, final int dstY1, final int mask, final int filter) {
        gl.glBlitFramebuffer(srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
    }

    @Override
    public void glBufferData(final int target, final ByteBuffer data, final int usage) {
        gl.glBufferData(target, data, usage);
    }

    @Override
    public void glBufferData(int target, FloatBuffer data, int usage) {
        gl.glBufferData(target, data, usage);
    }

    @Override
    public void glBufferData(int target, IntBuffer data, int usage) {
        gl.glBufferData(target, data, usage);
    }

    @Override
    public void glBufferData(int target, ShortBuffer data, int usage) {
        gl.glBufferData(target, data, usage);
    }

    @Override
    public int glCheckFramebufferStatus(int target) {
        return gl.glCheckFramebufferStatus(target);
    }

    @Override
    public void glClear(int mask) {
        gl.glClear(mask);
    }

    @Override
    public void glClearColor(float red, float green, float blue, float alpha) {
        if (floatEquals(red, glClearColorRed) &&
            floatEquals(green, glClearColorGreen) &&
            floatEquals(blue, glClearColorBlue) &&
            floatEquals(alpha, glClearColorAlpha)) {
            return;
        }
        gl.glClearColor(red, green, blue, alpha);
        glClearColorRed = red;
        glClearColorGreen = green;
        glClearColorBlue = blue;
        glClearColorAlpha = alpha;
    }

    @Override
    public void glClearStencil(int s) {
        if (s == glClearStencilS) {
            return;
        }
        gl.glClearStencil(s);
        glClearStencilS = s;
    }

    @Override
    public void glCompileShader(int shader) {
        gl.glCompileShader(shader);
    }

    @Override
    public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        if (red == glColorMaskRed &&
            green == glColorMaskGreen &&
            blue == glColorMaskBlue &&
            alpha == glColorMaskAlpha) {
            return;
        }
        gl.glColorMask(red, green, blue, alpha);
        glColorMaskRed = red;
        glColorMaskGreen = green;
        glColorMaskBlue = blue;
        glColorMaskAlpha = alpha;
    }

    @Override
    public int glCreateProgram() {
        return gl.glCreateProgram();
    }

    @Override
    public int glCreateShader(int type) {
        return gl.glCreateShader(type);
    }

    @Override
    public void glDepthMask(boolean flag) {
        if (flag == glDepthMaskFlag) {
            return;
        }
        gl.glDepthMask(flag);
        glDepthMaskFlag = flag;
    }

    @Override
    public void glDeleteBuffers(int n, IntBuffer buffers) {
        gl.glDeleteBuffers(n, buffers);
    }

    @Override
    public void glDeleteFramebuffers(int fboCount, IntBuffer fboIds) {
        gl.glDeleteFramebuffers(fboCount, fboIds);
    }

    @Override
    public void glDeleteTextures(int n, IntBuffer textures) {
        gl.glDeleteTextures(n, textures);
    }

    @Override
    public void glDeleteVertexArrays(int n, IntBuffer arrays) {
        gl.glDeleteVertexArrays(n, arrays);
    }

    @Override
    public void glDisable(int cap) {
        if (!glEnableSet.contains(cap)) {
            return;
        }
        gl.glDisable(cap);
        glEnableSet.remove(cap);
    }

    @Override
    public void glDisableVertexAttribArray(int index) {
        gl.glDisableVertexAttribArray(index);
    }

    @Override
    public void glDrawArrays(int mode, int first, int count) {
        gl.glDrawArrays(mode, first, count);
    }

    @Override
    public void glDrawArraysInstanced(int mode, int first, int count, int primcount) {
        gl.glDrawArraysInstanced(mode, first, count, primcount);
    }

    @Override
    public void glDrawBuffer(int mode) {
        if (mode == glDrawBufferMode) {
            return;
        }
        gl.glDrawBuffer(mode);
        glDrawBufferMode = mode;
    }

    @Override
    public void glDrawElements(int mode, int count, int type, int indices) {
        gl.glDrawElements(mode, count, type, indices);
    }

    @Override
    public void glDrawRangeElements(final int mode, final int start, final int end, final int count, final int type, final int indices) {
        gl.glDrawRangeElements(mode, start, end, count, type, indices);
    }

    @Override
    public void glEnable(int cap) {
        if (glEnableSet.contains(cap)) {
            return;
        }
        gl.glEnable(cap);
        glEnableSet.add(cap);
    }

    @Override
    public void glEnableVertexAttribArray(int index) {
        gl.glEnableVertexAttribArray(index);
    }

    @Override
    public void glFinish() {
        gl.glFinish();
    }

    @Override
    public void glFlush() {
        gl.glFlush();
    }

    @Override
    public void glFramebufferRenderbuffer(int target, int attachment, int renderBufferTarget, int renderBuffer) {
        gl.glFramebufferRenderbuffer(target, attachment, renderBufferTarget, renderBuffer);
    }

    @Override
    public void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
        gl.glFramebufferTexture2D(target, attachment, textarget, texture, level);
    }

    @Override
    public void glFramebufferTextureLayer(int target, int attachment, int texture, int level, int layer) {
        gl.glFramebufferTextureLayer(target, attachment, texture, level, layer);
    }

    @Override
    public void glGenBuffers(int n, IntBuffer buffers) {
        gl.glGenBuffers(n, buffers);
    }

    @Override
    public void glGenFramebuffers(int n, IntBuffer frameBuffs) {
        gl.glGenFramebuffers(n, frameBuffs);
    }

    @Override
    public void glGenRenderBuffers(int buffCount, IntBuffer buffer) {
        gl.glGenRenderBuffers(buffCount, buffer);
    }

    @Override
    public void glGenTextures(int n, IntBuffer textures) {
        gl.glGenTextures(n, textures);
    }

    @Override
    public void glGenVertexArrays(int n, IntBuffer arrays) {
        gl.glGenVertexArrays(n, arrays);
    }

    @Override
    public void glGenerateMipmap(int target) {
        gl.glGenerateMipmap(target);
    }

    @Override
    public void glGetActiveUniforms(int program, int uniformCount, IntBuffer indices, int pname, IntBuffer params) {
        gl.glGetActiveUniforms(program, uniformCount, indices, pname, params);
    }

    @Override
    public int glGetAttribLocation(int program, String name) {
        return gl.glGetAttribLocation(program, name);
    }

    @Override
    public int glGetError() {
        return gl.glGetError();
    }

    @Override
    public int glGetInteger(int pname) {
        return gl.glGetInteger(pname);
    }

    @Override
    public void glGetIntegerv(int pname, int[] params, int offset) {
        gl.glGetIntegerv(pname, params, offset);
    }

    @Override
    public void glGetIntegerv(int pname, IntBuffer params) {
        gl.glGetIntegerv(pname, params);
    }

    @Override
    public String glGetProgramInfoLog(int program) {
        return gl.glGetProgramInfoLog(program);
    }

    @Override
    public void glGetProgramiv(int program, int pname, IntBuffer params) {
        gl.glGetProgramiv(program, pname, params);
    }

    @Override
    public String glGetShaderInfoLog(int shader) {
        return gl.glGetShaderInfoLog(shader);
    }

    @Override
    public void glGetShaderiv(int shader, int pname, IntBuffer params) {
        gl.glGetShaderiv(shader, pname, params);
    }

    @Override
    public String glGetString(int pname) {
        return gl.glGetString(pname);
    }

    @Override
    public void glGetTexImage(int target, int level, int format, int type, ByteBuffer pixelBuffer) {
        gl.glGetTexImage(target, level, format, type, pixelBuffer);
    }

    @Override
    public int glGetUniformBlockIndex(int program, String name) {
        return gl.glGetUniformBlockIndex(program, name);
    }

    @Override
    public void glGetUniformIndices(int program, String[] uniformNames, IntBuffer indexBuffer) {
        gl.glGetUniformIndices(program, uniformNames, indexBuffer);
    }

    @Override
    public int glGetUniformLocation(int program, String name) {
        return gl.glGetUniformLocation(program, name);
    }

    @Override
    public boolean glIsEnabled(int cap) {
        return gl.glIsEnabled(cap);
    }

    @Override
    public void glLinkProgram(int program) {
        gl.glLinkProgram(program);
    }

    @Override
    public ByteBuffer glMapBuffer(int target, int access, long length, ByteBuffer oldBuffer) {
        return gl.glMapBuffer(target, access, length, oldBuffer);
    }

    @Override
    public void glPixelStorei(int pname, int param) {
        if (pname == glPixelStoreiPname &&
            param == glPixelStoreiParam) {
            return;
        }
        gl.glPixelStorei(pname, param);
        glPixelStoreiPname = pname;
        glPixelStoreiParam = param;
    }

    @Override
    public void glPointSize(int psize) {
        if (psize == glPointSizePSize) {
            return;
        }
        gl.glPointSize(psize);
        glPointSizePSize = psize;
    }

    @Override
    public void glPrimitiveRestartIndex(int index) {
        if (index == glPrimitiveRestartIndexIndex) {
            return;
        }
        gl.glPrimitiveRestartIndex(index);
        glPrimitiveRestartIndexIndex = index;
    }

    @Override
    public void glReadPixels(int x, int y, int width, int height, int format, int type, ByteBuffer pixelBuffer) {
        gl.glReadPixels(x, y, width, height, format, type, pixelBuffer);
    }

    @Override
    public void glRenderbufferStorage(int target, int internalFormat, int width, int height) {
        if (target == glRenderbufferStorageTarget &&
            internalFormat == glRenderbufferStorageInternalFormat &&
            width == glRenderbufferStorageWidth &&
            height == glRenderbufferStorageHeight) {
            return;
        }
        gl.glRenderbufferStorage(target, internalFormat, width, height);
        glRenderbufferStorageTarget = target;
        glRenderbufferStorageInternalFormat = internalFormat;
        glRenderbufferStorageWidth = width;
        glRenderbufferStorageHeight = height;
    }

    @Override
    public void glShaderSource(int shader, String string) {
        gl.glShaderSource(shader, string);
    }

    @Override
    public void glStencilFunc(int func, int ref, int mask) {
        if (func == glStencilFuncFunc &&
            ref == glStencilFuncRef &&
            mask == glStencilFuncMask) {
            return;
        }
        gl.glStencilFunc(func, ref, mask);
        glStencilFuncFunc = func;
        glStencilFuncRef = ref;
        glStencilFuncMask = mask;
    }

    @Override
    public void glStencilFuncSeparate(int face, int func, int ref, int mask) {
        if (face == glStencilFuncSeparateFace &&
            func == glStencilFuncSeparateFunc &&
            ref == glStencilFuncSeparateRef &&
            mask == glStencilFuncSeparateMask) {
            return;
        }
        gl.glStencilFuncSeparate(face, func, ref, mask);
        glStencilFuncSeparateFace = face;
        glStencilFuncSeparateFunc = func;
        glStencilFuncSeparateRef = ref;
        glStencilFuncSeparateMask = mask;
    }

    @Override
    public void glStencilMask(int mask) {
        if (mask == glStencilMaskMask) {
            return;
        }
        gl.glStencilMask(mask);
        glStencilMaskMask = mask;
    }

    @Override
    public void glStencilMaskSeparate(int face, int mask) {
        if (face == glStencilMaskSeparateFace &&
            mask == glStencilMaskSeparateMask) {
            return;
        }
        gl.glStencilMaskSeparate(face, mask);
        glStencilMaskSeparateFace = face;
        glStencilMaskSeparateMask = mask;
    }

    @Override
    public void glStencilOp(int sfail, int dpfail, int dppass) {
        if (sfail == glStencilOpSFail &&
            dpfail == glStencilOpDpFail &&
            dppass == glStencilOpDpPass) {
            return;
        }
        gl.glStencilOp(sfail, dpfail, dppass);
        glStencilOpSFail = sfail;
        glStencilOpDpFail = dpfail;
        glStencilOpDpPass = dppass;
    }

    @Override
    public void glStencilOpSeparate(int face, int sfail, int dpfail, int dppass) {
        if (face == glStencilOpSeparateFace &&
            sfail == glStencilOpSeparateSFail &&
            dpfail == glStencilOpSeparateDpFail &&
            dppass == glStencilOpSeparateDpPass) {
            return;
        }
        gl.glStencilOpSeparate(face, sfail, dpfail, dppass);
        glStencilOpSeparateFace = face;
        glStencilOpSeparateSFail = sfail;
        glStencilOpSeparateDpFail = dpfail;
        glStencilOpSeparateDpPass = dppass;
    }

    @Override
    public void glTexBuffer(int target, int internalFormat, int buffer) {
        if (target == glTexBufferTarget &&
            internalFormat == glTexBufferInternalFormat &&
            buffer == glTexBufferBuffer) {
            return;
        }
        gl.glTexBuffer(target, internalFormat, buffer);
        glTexBufferTarget = target;
        glTexBufferInternalFormat = internalFormat;
        glTexBufferBuffer = buffer;
    }

    @Override
    public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, ByteBuffer pixels) {
        gl.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
    }

    @Override
    public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, DoubleBuffer pixels) {
        gl.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
    }

    @Override
    public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, FloatBuffer pixels) {
        gl.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
    }

    @Override
    public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, IntBuffer pixels) {
        gl.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
    }

    @Override
    public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, ShortBuffer pixels) {
        gl.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
    }

    @Override
    public void glTexImage2DMultisample(final int target, final int samples, final int internalformat, final int width, final int height, final boolean fixedsamplelocations) {
        gl.glTexImage2DMultisample(target, samples, internalformat, width, height, fixedsamplelocations);
    }

    @Override
    public void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, ByteBuffer pixels) {
        gl.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
    }

    @Override
    public void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, DoubleBuffer pixels) {
        gl.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
    }

    @Override
    public void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, FloatBuffer pixels) {
        gl.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
    }

    @Override
    public void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, IntBuffer pixels) {
        gl.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
    }

    @Override
    public void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, ShortBuffer pixels) {
        gl.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
    }

    @Override
    public void glTexParameterf(int target, int pname, float param) {
        if (target == glTexParameterfTarget &&
            pname == glTexParameterfPName &&
            floatEquals(param, glTexParameterfParam)) {
            return;
        }
        gl.glTexParameterf(target, pname, param);
        glTexParameterfTarget = target;
        glTexParameterfPName = pname;
        glTexParameterfParam = param;
    }

    @Override
    public void glTexParameteri(int target, int pname, int param) {
        if (target == glTexParameteriTarget &&
            pname == glTexParameteriPName &&
            param == glTexParameteriParam) {
            return;
        }
        gl.glTexParameteri(target, pname, param);
        glTexParameteriTarget = target;
        glTexParameteriPName = pname;
        glTexParameteriParam = param;
    }

    @Override
    public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, ByteBuffer pixels) {
        gl.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
    }

    @Override
    public void glUniform1(int location, FloatBuffer values) {
        gl.glUniform1(location, values);
    }

    @Override
    public void glUniform1f(int location, float v0) {
        gl.glUniform1f(location, v0);
    }

    @Override
    public void glUniform1fv(int location, FloatBuffer ubuff) {
        gl.glUniform1fv(location, ubuff);
    }

    @Override
    public void glUniform1i(int location, int v0) {
        gl.glUniform1i(location, v0);
    }

    @Override
    public void glUniform1iv(int location, IntBuffer ubuff) {
        gl.glUniform1iv(location, ubuff);
    }

    @Override
    public void glUniform2f(int location, float v0, float v1) {
        gl.glUniform2f(location, v0, v1);
    }

    @Override
    public void glUniform2fv(int location, FloatBuffer ubuff) {
        gl.glUniform2fv(location, ubuff);
    }

    @Override
    public void glUniform2i(int location, int v0, int v1) {
        gl.glUniform2i(location, v0, v1);
    }

    @Override
    public void glUniform2iv(int location, IntBuffer ubuff) {
        gl.glUniform2iv(location, ubuff);
    }

    @Override
    public void glUniform3f(int location, float v0, float v1, float v2) {
        gl.glUniform3f(location, v0, v1, v2);
    }

    @Override
    public void glUniform3fv(int location, FloatBuffer ubuff) {
        gl.glUniform3fv(location, ubuff);
    }

    @Override
    public void glUniform3i(int location, int v0, int v1, int v2) {
        gl.glUniform3i(location, v0, v1, v2);
    }

    @Override
    public void glUniform3iv(int location, IntBuffer ubuff) {
        gl.glUniform3iv(location, ubuff);
    }

    @Override
    public void glUniform4f(int location, float v0, float v1, float v2, float v3) {
        gl.glUniform4f(location, v0, v1, v2, v3);
    }

    @Override
    public void glUniform4fv(int location, FloatBuffer ubuff) {
        gl.glUniform4fv(location, ubuff);
    }

    @Override
    public void glUniform4i(int location, int v0, int v1, int v2, int v3) {
        gl.glUniform4i(location, v0, v1, v2, v3);
    }

    @Override
    public void glUniform4iv(int location, IntBuffer ubuff) {
        gl.glUniform4iv(location, ubuff);
    }

    @Override
    public void glUniformBlockBinding(int prog, int blockIndex, int blockBinding) {
        gl.glUniformBlockBinding(prog, blockIndex, blockBinding);
    }

    @Override
    public void glUniformMatrix2(int location, boolean transpose, FloatBuffer matrices) {
        gl.glUniformMatrix2(location, transpose, matrices);
    }

    @Override
    public void glUniformMatrix2x3(int location, boolean transpose, FloatBuffer matrices) {
        gl.glUniformMatrix2x3(location, transpose, matrices);
    }

    @Override
    public void glUniformMatrix2x4(int location, boolean transpose, FloatBuffer matrices) {
        gl.glUniformMatrix2x4(location, transpose, matrices);
    }

    @Override
    public void glUniformMatrix3(int location, boolean transpose, FloatBuffer matrices) {
        gl.glUniformMatrix3(location, transpose, matrices);
    }

    @Override
    public void glUniformMatrix3x2(int location, boolean transpose, FloatBuffer matrices) {
        gl.glUniformMatrix3x2(location, transpose, matrices);
    }

    @Override
    public void glUniformMatrix3x4(int location, boolean transpose, FloatBuffer matrices) {
        gl.glUniformMatrix3x4(location, transpose, matrices);
    }

    @Override
    public void glUniformMatrix4(int location, boolean transpose, FloatBuffer matrices) {
        gl.glUniformMatrix4(location, transpose, matrices);
    }

    @Override
    public void glUniformMatrix4x2(int location, boolean transpose, FloatBuffer matrices) {
        gl.glUniformMatrix4x2(location, transpose, matrices);
    }

    @Override
    public void glUniformMatrix4x3(int location, boolean transpose, FloatBuffer matrices) {
        gl.glUniformMatrix4x3(location, transpose, matrices);
    }

    @Override
    public boolean glUnmapBuffer(int target) {
        return gl.glUnmapBuffer(target);
    }

    @Override
    public void glUseProgram(int program) {
        if (program == glUseProgramProgram) {
            return;
        }
        gl.glUseProgram(program);
        glUseProgramProgram = program;
    }

    @Override
    public void glVertexAttribDivisor(int index, int divisor) {
        if (index == glVertexAttribDivisorIndex &&
            divisor == glVertexAttribDivisorDivisor) {
            return;
        }
        gl.glVertexAttribDivisor(index, divisor);
        glVertexAttribDivisorIndex = index;
        glVertexAttribDivisorDivisor = divisor;
    }

    @Override
    public void glVertexAttribIPointer(int index, int size, int type, int stride, int buffer) {
        gl.glVertexAttribIPointer(index, size, type, stride, buffer);
    }

    @Override
    public void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, long offset) {
        gl.glVertexAttribPointer(index, size, type, normalized, stride, offset);
    }

    @Override
    public void glViewport(int x, int y, int width, int height) {
        if (x == glViewportX &&
            y == glViewportY &&
            width == glViewportWidth &&
            height == glViewportHeight) {
            return;
        }
        gl.glViewport(x, y, width, height);
        glViewportX = x;
        glViewportY = y;
        glViewportWidth = width;
        glViewportHeight = height;
    }

    @Override
    public long getCurrentContext() {
        return gl.getCurrentContext();
    }

    @Override
    public void makeContextCurrent(final long context) {
        gl.makeContextCurrent(context);
    }

    @Override
    public void checkGLError() {
        gl.checkGLError();
    }

    @Override
    public void checkGLError(boolean throwException, String msg, Object... args) {
        gl.checkGLError(throwException, msg, args);
    }

    @Override
    public void checkGLError(String msg) {
        gl.checkGLError(msg);
    }

    @Override
    public void checkGLError(String msg, Object... args) {
        gl.checkGLError(msg, args);
    }

    @Override
    public void setErrorChecksEnabled(boolean enabled) {
        gl.setErrorChecksEnabled(enabled);
    }

    @Override
    public int mapCoreBufferTargetType(final CoreBufferTargetType target) {
        return gl.mapCoreBufferTargetType(target);
    }

    @Override
    public int mapCoreBufferUsageType(final CoreBufferUsageType usage) {
        return gl.mapCoreBufferUsageType(usage);
    }

    @Override
    public int mapCoreBufferAccessType(final CoreBufferAccessType access) {
        return gl.mapCoreBufferAccessType(access);
    }

    @Override
    public int gluBuild2DMipmaps(final int target, final int internalFormat, final int width, final int height, final int format, final int type, final ByteBuffer data) {
        return gl.gluBuild2DMipmaps(target, internalFormat, width, height, format, type, data);
    }

    @Override
    public String gluErrorString(final int glError) {
        return gl.gluErrorString(glError);
    }

    @Override
    public boolean isNPOTSupported() {
        return gl.isNPOTSupported();
    }

    @Override
    public boolean isNPOTHardwareSupported() {
        return gl.isNPOTHardwareSupported();
    }

    @Override
    public GLVersion getGLVersion() {
        return gl.getGLVersion();
    }

    @Override
    public GLSLVersion getGLSLVersion() {
        return gl.getGLSLVersion();
    }

    @Override
    public float getScaleX() {
        return gl.getScaleX();
    }

    @Override
    public float getScaleY() {
        return gl.getScaleY();
    }

    private boolean stringEquals(final String a, final String b) {
        return Objects.equals(a, b);
    }

    private boolean floatEquals(final float a, final float b) {
        return (Math.abs(a - b) <= 0.0000000000000001);
    }
}
