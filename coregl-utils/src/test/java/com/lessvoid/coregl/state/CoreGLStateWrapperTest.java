package com.lessvoid.coregl.state;

import com.lessvoid.coregl.spi.CoreGL;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by void on 03.03.16.
 */
public class CoreGLStateWrapperTest {
    private CoreGLStateWrapper sut;
    private CoreGL gl;

    private ByteBuffer byteBuffer = ByteBuffer.allocate(1);
    private ShortBuffer shortBuffer = ShortBuffer.allocate(1);
    private IntBuffer intBuffer = IntBuffer.allocate(1);
    private FloatBuffer floatBuffer = FloatBuffer.allocate(1);
    private DoubleBuffer doubleBuffer = DoubleBuffer.allocate(1);

    @Before
    public void before() {
        gl = createStrictMock(CoreGL.class);
        sut = new CoreGLStateWrapper(gl);
    }

    @After
    public void after() {
        verify(gl);
    }

    @Test
    public void testGL_ACTIVE_TEXTURE() throws Exception {
        expect(gl.GL_ACTIVE_TEXTURE()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_ACTIVE_TEXTURE());
    }

    @Test
    public void testGL_ALPHA() throws Exception {
        expect(gl.GL_ALPHA()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_ALPHA());
    }

    @Test
    public void testGL_ALPHA_TEST() throws Exception {
        expect(gl.GL_ALPHA_TEST()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_ALPHA_TEST());
    }

    @Test
    public void testGL_ALWAYS() throws Exception {
        expect(gl.GL_ALWAYS()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_ALWAYS());
    }

    @Test
    public void testGL_ARRAY_BUFFER() throws Exception {
        expect(gl.GL_ARRAY_BUFFER()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_ARRAY_BUFFER());
    }

    @Test
    public void testGL_BACK() throws Exception {
        expect(gl.GL_BACK()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_BACK());
    }

    @Test
    public void testGL_BGR() throws Exception {
        expect(gl.GL_BGR()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_BGR());
    }

    @Test
    public void testGL_BGRA() throws Exception {
        expect(gl.GL_ACTIVE_TEXTURE()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_ACTIVE_TEXTURE());
    }

    @Test
    public void testGL_BITMAP() throws Exception {
        expect(gl.GL_BITMAP()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_BITMAP());
    }

    @Test
    public void testGL_BLEND() throws Exception {
        expect(gl.GL_BLEND()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_BLEND());
    }

    @Test
    public void testGL_BLEND_DST() throws Exception {
        expect(gl.GL_BLEND_DST()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_BLEND_DST());
    }

    @Test
    public void testGL_BLEND_SRC() throws Exception {
        expect(gl.GL_BLEND_SRC()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_BLEND_SRC());
    }

    @Test
    public void testGL_BLUE() throws Exception {
        expect(gl.GL_BLUE()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_BLUE());
    }

    @Test
    public void testGL_BYTE() throws Exception {
        expect(gl.GL_BYTE()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_BYTE());
    }

    @Test
    public void testGL_COLOR_ATTACHMENT0() throws Exception {
        expect(gl.GL_COLOR_ATTACHMENT0()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_COLOR_ATTACHMENT0());
    }

    @Test
    public void testGL_COLOR_BUFFER_BIT() throws Exception {
        expect(gl.GL_COLOR_BUFFER_BIT()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_COLOR_BUFFER_BIT());
    }

    @Test
    public void testGL_COLOR_INDEX() throws Exception {
        expect(gl.GL_COLOR_INDEX()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_COLOR_INDEX());
    }

    @Test
    public void testGL_COMPILE_STATUS() throws Exception {
        expect(gl.GL_COMPILE_STATUS()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_COMPILE_STATUS());
    }

    @Test
    public void testGL_COMPRESSED_ALPHA() throws Exception {
        expect(gl.GL_COMPRESSED_ALPHA()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_COMPRESSED_ALPHA());
    }

    @Test
    public void testGL_COMPRESSED_LUMINANCE() throws Exception {
        expect(gl.GL_COMPRESSED_LUMINANCE()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_COMPRESSED_LUMINANCE());
    }

    @Test
    public void testGL_COMPRESSED_LUMINANCE_ALPHA() throws Exception {
        expect(gl.GL_COMPRESSED_LUMINANCE_ALPHA()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_COMPRESSED_LUMINANCE_ALPHA());
    }

    @Test
    public void testGL_COMPRESSED_RGB() throws Exception {
        expect(gl.GL_COMPRESSED_RGB()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_COMPRESSED_RGB());
    }

    @Test
    public void testGL_COMPRESSED_RGBA() throws Exception {
        expect(gl.GL_COMPRESSED_RGBA()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_COMPRESSED_RGBA());
    }

    @Test
    public void testGL_CULL_FACE() throws Exception {
        expect(gl.GL_CULL_FACE()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_CULL_FACE());
    }

    @Test
    public void testGL_CURRENT_PROGRAM() throws Exception {
        expect(gl.GL_CURRENT_PROGRAM()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_CURRENT_PROGRAM());
    }

    @Test
    public void testGL_DECR() throws Exception {
        expect(gl.GL_DECR()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_DECR());
    }

    @Test
    public void testGL_DECR_WRAP() throws Exception {
        expect(gl.GL_DECR_WRAP()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_DECR_WRAP());
    }

    @Test
    public void testGL_DEPTH24_STENCIL8() throws Exception {
        expect(gl.GL_DEPTH24_STENCIL8()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_DEPTH24_STENCIL8());
    }

    @Test
    public void testGL_DEPTH_BUFFER_BIT() throws Exception {
        expect(gl.GL_DEPTH_BUFFER_BIT()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_DEPTH_BUFFER_BIT());
    }

    @Test
    public void testGL_DEPTH_STENCIL_ATTACHMENT() throws Exception {
        expect(gl.GL_DEPTH_STENCIL_ATTACHMENT()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_DEPTH_STENCIL_ATTACHMENT());
    }

    @Test
    public void testGL_DEPTH_TEST() throws Exception {
        expect(gl.GL_DEPTH_TEST()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_DEPTH_TEST());
    }

    @Test
    public void testGL_DOUBLE() throws Exception {
        expect(gl.GL_DOUBLE()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_DOUBLE());
    }

    @Test
    public void testGL_DST_ALPHA() throws Exception {
        expect(gl.GL_DST_ALPHA()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_DST_ALPHA());
    }

    @Test
    public void testGL_DST_COLOR() throws Exception {
        expect(gl.GL_DST_COLOR()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_DST_COLOR());
    }

    @Test
    public void testGL_DYNAMIC_DRAW() throws Exception {
        expect(gl.GL_DYNAMIC_DRAW()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_DYNAMIC_DRAW());
    }

    @Test
    public void testGL_ELEMENT_ARRAY_BUFFER() throws Exception {
        expect(gl.GL_ELEMENT_ARRAY_BUFFER()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_ELEMENT_ARRAY_BUFFER());
    }

    @Test
    public void testGL_EQUAL() throws Exception {
        expect(gl.GL_EQUAL()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_EQUAL());
    }

    @Test
    public void testGL_FALSE() throws Exception {
        expect(gl.GL_FALSE()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_FALSE());
    }

    @Test
    public void testGL_FIXED() throws Exception {
        expect(gl.GL_FIXED()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_FIXED());
    }

    @Test
    public void testGL_FLOAT() throws Exception {
        expect(gl.GL_FLOAT()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_FLOAT());
    }

    @Test
    public void testGL_FRAGMENT_SHADER() throws Exception {
        expect(gl.GL_FRAGMENT_SHADER()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_FRAGMENT_SHADER());
    }

    @Test
    public void testGL_FRAMEBUFFER() throws Exception {
        expect(gl.GL_FRAMEBUFFER()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_FRAMEBUFFER());
    }

    @Test
    public void testGL_FRAMEBUFFER_COMPLETE() throws Exception {
        expect(gl.GL_FRAMEBUFFER_COMPLETE()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_FRAMEBUFFER_COMPLETE());
    }

    @Test
    public void testGL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT() throws Exception {
        expect(gl.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT());
    }

    @Test
    public void testGL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER() throws Exception {
        expect(gl.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER());
    }

    @Test
    public void testGL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT() throws Exception {
        expect(gl.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT());
    }

    @Test
    public void testGL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE() throws Exception {
        expect(gl.GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE());
    }

    @Test
    public void testGL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER() throws Exception {
        expect(gl.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER());
    }

    @Test
    public void testGL_FRAMEBUFFER_UNDEFINED() throws Exception {
        expect(gl.GL_FRAMEBUFFER_UNDEFINED()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_FRAMEBUFFER_UNDEFINED());
    }

    @Test
    public void testGL_FRAMEBUFFER_UNSUPPORTED() throws Exception {
        expect(gl.GL_FRAMEBUFFER_UNSUPPORTED()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_FRAMEBUFFER_UNSUPPORTED());
    }

    @Test
    public void testGL_FRONT() throws Exception {
        expect(gl.GL_FRONT()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_FRONT());
    }

    @Test
    public void testGL_FRONT_AND_BACK() throws Exception {
        expect(gl.GL_FRONT_AND_BACK()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_FRONT_AND_BACK());
    }

    @Test
    public void testGL_FUNC_ADD() throws Exception {
        expect(gl.GL_FUNC_ADD()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_FUNC_ADD());
    }

    @Test
    public void testGL_GEOMETRY_SHADER() throws Exception {
        expect(gl.GL_GEOMETRY_SHADER()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_GEOMETRY_SHADER());
    }

    @Test
    public void testGL_GEQUAL() throws Exception {
        expect(gl.GL_GEQUAL()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_GEQUAL());
    }

    @Test
    public void testGL_GREATER() throws Exception {
        expect(gl.GL_GREATER()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_GREATER());
    }

    @Test
    public void testGL_GREEN() throws Exception {
        expect(gl.GL_GREEN()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_GREEN());
    }

    @Test
    public void testGL_HALF_FLOAT() throws Exception {
        expect(gl.GL_HALF_FLOAT()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_HALF_FLOAT());
    }

    @Test
    public void testGL_LESS() throws Exception {
        expect(gl.GL_LESS()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_LESS());
    }

    @Test
    public void testGL_LEQUAL() throws Exception {
        expect(gl.GL_LEQUAL()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_LEQUAL());
    }

    @Test
    public void testGL_INCR() throws Exception {
        expect(gl.GL_INCR()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_INCR());
    }

    @Test
    public void testGL_INCR_WRAP() throws Exception {
        expect(gl.GL_INCR_WRAP()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_INCR_WRAP());
    }

    @Test
    public void testGL_INT() throws Exception {
        expect(gl.GL_INT()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_INT());
    }

    @Test
    public void testGL_INT_2_10_10_10_REV() throws Exception {
        expect(gl.GL_INT_2_10_10_10_REV()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_INT_2_10_10_10_REV());
    }

    @Test
    public void testGL_INVERT() throws Exception {
        expect(gl.GL_INVERT()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_INVERT());
    }

    @Test
    public void testGL_INVALID_ENUM() throws Exception {
        expect(gl.GL_INVALID_ENUM()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_INVALID_ENUM());
    }

    @Test
    public void testGL_INVALID_OPERATION() throws Exception {
        expect(gl.GL_INVALID_OPERATION()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_INVALID_OPERATION());
    }

    @Test
    public void testGL_INVALID_VALUE() throws Exception {
        expect(gl.GL_INVALID_VALUE()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_INVALID_VALUE());
    }

    @Test
    public void testGL_KEEP() throws Exception {
        expect(gl.GL_KEEP()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_KEEP());
    }

    @Test
    public void testGL_LINEAR() throws Exception {
        expect(gl.GL_LINEAR()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_LINEAR());
    }

    @Test
    public void testGL_LINEAR_MIPMAP_LINEAR() throws Exception {
        expect(gl.GL_LINEAR_MIPMAP_LINEAR()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_LINEAR_MIPMAP_LINEAR());
    }

    @Test
    public void testGL_LINEAR_MIPMAP_NEAREST() throws Exception {
        expect(gl.GL_LINEAR_MIPMAP_NEAREST()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_LINEAR_MIPMAP_NEAREST());
    }

    @Test
    public void testGL_LINE_STRIP() throws Exception {
        expect(gl.GL_LINE_STRIP()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_LINE_STRIP());
    }

    @Test
    public void testGL_LINE_STRIP_ADJACENCY() throws Exception {
        expect(gl.GL_LINE_STRIP_ADJACENCY()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_LINE_STRIP_ADJACENCY());
    }

    @Test
    public void testGL_LINK_STATUS() throws Exception {
        expect(gl.GL_LINK_STATUS()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_LINK_STATUS());
    }

    @Test
    public void testGL_LUMINANCE() throws Exception {
        expect(gl.GL_LUMINANCE()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_LUMINANCE());
    }

    @Test
    public void testGL_LUMINANCE_ALPHA() throws Exception {
        expect(gl.GL_LUMINANCE_ALPHA()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_LUMINANCE_ALPHA());
    }

    @Test
    public void testGL_MAX() throws Exception {
        expect(gl.GL_MAX()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_MAX());
    }

    @Test
    public void testGL_MAX_3D_TEXTURE_SIZE() throws Exception {
        expect(gl.GL_MAX_3D_TEXTURE_SIZE()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_MAX_3D_TEXTURE_SIZE());
    }

    @Test
    public void testGL_MAX_TEXTURE_SIZE() throws Exception {
        expect(gl.GL_MAX_TEXTURE_SIZE()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_MAX_TEXTURE_SIZE());
    }

    @Test
    public void testGL_MAX_VERTEX_ATTRIBS() throws Exception {
        expect(gl.GL_MAX_VERTEX_ATTRIBS()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_MAX_VERTEX_ATTRIBS());
    }

    @Test
    public void testGL_NEAREST() throws Exception {
        expect(gl.GL_NEAREST()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_NEAREST());
    }

    @Test
    public void testGL_NEAREST_MIPMAP_LINEAR() throws Exception {
        expect(gl.GL_NEAREST_MIPMAP_LINEAR()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_NEAREST_MIPMAP_LINEAR());
    }

    @Test
    public void testGL_NEAREST_MIPMAP_NEAREST() throws Exception {
        expect(gl.GL_NEAREST_MIPMAP_NEAREST()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_NEAREST_MIPMAP_NEAREST());
    }

    @Test
    public void testGL_NEVER() throws Exception {
        expect(gl.GL_NEVER()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_NEVER());
    }

    @Test
    public void testGL_NOTEQUAL() throws Exception {
        expect(gl.GL_NOTEQUAL()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_NOTEQUAL());
    }

    @Test
    public void testGL_NO_ERROR() throws Exception {
        expect(gl.GL_NO_ERROR()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_NO_ERROR());
    }

    @Test
    public void testGL_ONE() throws Exception {
        expect(gl.GL_ONE()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_ONE());
    }

    @Test
    public void testGL_ONE_MINUS_DST_ALPHA() throws Exception {
        expect(gl.GL_ONE_MINUS_DST_ALPHA()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_ONE_MINUS_DST_ALPHA());
    }

    @Test
    public void testGL_ONE_MINUS_SRC_ALPHA() throws Exception {
        expect(gl.GL_ONE_MINUS_SRC_ALPHA()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_ONE_MINUS_SRC_ALPHA());
    }

    @Test
    public void testGL_OUT_OF_MEMORY() throws Exception {
        expect(gl.GL_OUT_OF_MEMORY()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_OUT_OF_MEMORY());
    }

    @Test
    public void testGL_PACK_ALIGNMENT() throws Exception {
        expect(gl.GL_PACK_ALIGNMENT()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_PACK_ALIGNMENT());
    }

    @Test
    public void testGL_POINTS() throws Exception {
        expect(gl.GL_POINTS()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_POINTS());
    }

    @Test
    public void testGL_PRIMITIVE_RESTART() throws Exception {
        expect(gl.GL_PRIMITIVE_RESTART()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_PRIMITIVE_RESTART());
    }

    @Test
    public void testGL_PRIMITIVE_RESTART_INDEX() throws Exception {
        expect(gl.GL_PRIMITIVE_RESTART_INDEX()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_PRIMITIVE_RESTART_INDEX());
    }

    @Test
    public void testGL_R32F() throws Exception {
        expect(gl.GL_R32F()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_R32F());
    }

    @Test
    public void testGL_RED() throws Exception {
        expect(gl.GL_RED()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_RED());
    }

    @Test
    public void testGL_RENDERBUFFER() throws Exception {
        expect(gl.GL_RENDERBUFFER()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_RENDERBUFFER());
    }

    @Test
    public void testGL_RENDERER() throws Exception {
        expect(gl.GL_RENDERER()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_RENDERER());
    }

    @Test
    public void testGL_REPLACE() throws Exception {
        expect(gl.GL_REPLACE()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_REPLACE());
    }

    @Test
    public void testGL_RGB() throws Exception {
        expect(gl.GL_RGB()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_RGB());
    }

    @Test
    public void testGL_RGBA() throws Exception {
        expect(gl.GL_RGBA()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_RGBA());
    }

    @Test
    public void testGL_SHADING_LANGUAGE_VERSION() throws Exception {
        expect(gl.GL_SHADING_LANGUAGE_VERSION()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_SHADING_LANGUAGE_VERSION());
    }

    @Test
    public void testGL_SHORT() throws Exception {
        expect(gl.GL_SHORT()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_SHORT());
    }

    @Test
    public void testGL_SRC_ALPHA() throws Exception {
        expect(gl.GL_SRC_ALPHA()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_SRC_ALPHA());
    }

    @Test
    public void testGL_STACK_OVERFLOW() throws Exception {
        expect(gl.GL_STACK_OVERFLOW()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_STACK_OVERFLOW());
    }

    @Test
    public void testGL_STACK_UNDERFLOW() throws Exception {
        expect(gl.GL_STACK_UNDERFLOW()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_STACK_UNDERFLOW());
    }

    @Test
    public void testGL_STATIC_DRAW() throws Exception {
        expect(gl.GL_STATIC_DRAW()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_STATIC_DRAW());
    }

    @Test
    public void testGL_STENCIL_ATTACHMENT() throws Exception {
        expect(gl.GL_STENCIL_ATTACHMENT()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_STENCIL_ATTACHMENT());
    }

    @Test
    public void testGL_STENCIL_BUFFER_BIT() throws Exception {
        expect(gl.GL_STENCIL_BUFFER_BIT()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_STENCIL_BUFFER_BIT());
    }

    @Test
    public void testGL_STENCIL_INDEX() throws Exception {
        expect(gl.GL_STENCIL_INDEX()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_STENCIL_INDEX());
    }

    @Test
    public void testGL_STENCIL_INDEX8() throws Exception {
        expect(gl.GL_STENCIL_INDEX8()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_STENCIL_INDEX8());
    }

    @Test
    public void testGL_STENCIL_TEST() throws Exception {
        expect(gl.GL_STENCIL_TEST()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_STENCIL_TEST());
    }

    @Test
    public void testGL_STREAM_DRAW() throws Exception {
        expect(gl.GL_STREAM_DRAW()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_STREAM_DRAW());
    }

    @Test
    public void testGL_TEXTURE0() throws Exception {
        expect(gl.GL_TEXTURE0()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_TEXTURE0());
    }

    @Test
    public void testGL_TEXTURE_2D() throws Exception {
        expect(gl.GL_TEXTURE_2D()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_TEXTURE_2D());
    }

    @Test
    public void testGL_TEXTURE_2D_ARRAY() throws Exception {
        expect(gl.GL_TEXTURE_2D_ARRAY()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_TEXTURE_2D_ARRAY());
    }

    @Test
    public void testGL_TEXTURE_3D() throws Exception {
        expect(gl.GL_TEXTURE_3D()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_TEXTURE_3D());
    }

    @Test
    public void testGL_TEXTURE_BINDING_2D() throws Exception {
        expect(gl.GL_TEXTURE_BINDING_2D()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_TEXTURE_BINDING_2D());
    }

    @Test
    public void testGL_TEXTURE_BINDING_3D() throws Exception {
        expect(gl.GL_TEXTURE_BINDING_3D()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_TEXTURE_BINDING_3D());
    }

    @Test
    public void testGL_TEXTURE_BUFFER() throws Exception {
        expect(gl.GL_TEXTURE_BUFFER()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_TEXTURE_BUFFER());
    }

    @Test
    public void testGL_TEXTURE_CUBE_MAP_NEGATIVE_X() throws Exception {
        expect(gl.GL_TEXTURE_CUBE_MAP_NEGATIVE_X()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_TEXTURE_CUBE_MAP_NEGATIVE_X());
    }

    @Test
    public void testGL_TEXTURE_CUBE_MAP_NEGATIVE_Y() throws Exception {
        expect(gl.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y());
    }

    @Test
    public void testGL_TEXTURE_CUBE_MAP_NEGATIVE_Z() throws Exception {
        expect(gl.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z());
    }

    @Test
    public void testGL_TEXTURE_CUBE_MAP_POSITIVE_X() throws Exception {
        expect(gl.GL_TEXTURE_CUBE_MAP_POSITIVE_X()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_TEXTURE_CUBE_MAP_POSITIVE_X());
    }

    @Test
    public void testGL_TEXTURE_CUBE_MAP_POSITIVE_Y() throws Exception {
        expect(gl.GL_TEXTURE_CUBE_MAP_POSITIVE_Y()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_TEXTURE_CUBE_MAP_POSITIVE_Y());
    }

    @Test
    public void testGL_TEXTURE_CUBE_MAP_POSITIVE_Z() throws Exception {
        expect(gl.GL_TEXTURE_CUBE_MAP_POSITIVE_Z()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_TEXTURE_CUBE_MAP_POSITIVE_Z());
    }

    @Test
    public void testGL_TEXTURE_MAG_FILTER() throws Exception {
        expect(gl.GL_TEXTURE_MAG_FILTER()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_TEXTURE_MAG_FILTER());
    }

    @Test
    public void testGL_TEXTURE_MIN_FILTER() throws Exception {
        expect(gl.GL_TEXTURE_MIN_FILTER()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_TEXTURE_MIN_FILTER());
    }

    @Test
    public void testGL_TRIANGLES() throws Exception {
        expect(gl.GL_TRIANGLES()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_TRIANGLES());
    }

    @Test
    public void testGL_TRIANGLE_FAN() throws Exception {
        expect(gl.GL_TRIANGLE_FAN()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_TRIANGLE_FAN());
    }

    @Test
    public void testGL_TRIANGLE_STRIP() throws Exception {
        expect(gl.GL_TRIANGLE_STRIP()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_TRIANGLE_STRIP());
    }

    @Test
    public void testGL_TRUE() throws Exception {
        expect(gl.GL_TRUE()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_TRUE());
    }

    @Test
    public void testGL_UNIFORM_ARRAY_STRIDE() throws Exception {
        expect(gl.GL_UNIFORM_ARRAY_STRIDE()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_UNIFORM_ARRAY_STRIDE());
    }

    @Test
    public void testGL_UNIFORM_BUFFER() throws Exception {
        expect(gl.GL_UNIFORM_BUFFER()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_UNIFORM_BUFFER());
    }

    @Test
    public void testGL_UNIFORM_MATRIX_STRIDE() throws Exception {
        expect(gl.GL_UNIFORM_MATRIX_STRIDE()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_UNIFORM_MATRIX_STRIDE());
    }

    @Test
    public void testGL_UNIFORM_OFFSET() throws Exception {
        expect(gl.GL_UNIFORM_OFFSET()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_UNIFORM_OFFSET());
    }

    @Test
    public void testGL_UNSIGNED_BYTE() throws Exception {
        expect(gl.GL_UNSIGNED_BYTE()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_UNSIGNED_BYTE());
    }

    @Test
    public void testGL_UNSIGNED_BYTE_2_3_3_REV() throws Exception {
        expect(gl.GL_UNSIGNED_BYTE_2_3_3_REV()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_UNSIGNED_BYTE_2_3_3_REV());
    }

    @Test
    public void testGL_UNSIGNED_BYTE_3_3_2() throws Exception {
        expect(gl.GL_UNSIGNED_BYTE_3_3_2()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_UNSIGNED_BYTE_3_3_2());
    }

    @Test
    public void testGL_UNSIGNED_INT() throws Exception {
        expect(gl.GL_UNSIGNED_INT()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_UNSIGNED_INT());
    }

    @Test
    public void testGL_UNSIGNED_INT_10F_11F_11F_REV() throws Exception {
        expect(gl.GL_UNSIGNED_INT_10F_11F_11F_REV()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_UNSIGNED_INT_10F_11F_11F_REV());
    }

    @Test
    public void testGL_UNSIGNED_INT_10_10_10_2() throws Exception {
        expect(gl.GL_UNSIGNED_INT_10_10_10_2()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_UNSIGNED_INT_10_10_10_2());
    }

    @Test
    public void testGL_UNSIGNED_INT_2_10_10_10_REV() throws Exception {
        expect(gl.GL_UNSIGNED_INT_2_10_10_10_REV()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_UNSIGNED_INT_2_10_10_10_REV());
    }

    @Test
    public void testGL_UNSIGNED_INT_8_8_8_8() throws Exception {
        expect(gl.GL_UNSIGNED_INT_8_8_8_8()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_UNSIGNED_INT_8_8_8_8());
    }

    @Test
    public void testGL_UNSIGNED_INT_8_8_8_8_REV() throws Exception {
        expect(gl.GL_UNSIGNED_INT_8_8_8_8_REV()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_UNSIGNED_INT_8_8_8_8_REV());
    }

    @Test
    public void testGL_UNSIGNED_SHORT() throws Exception {
        expect(gl.GL_UNSIGNED_SHORT()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_UNSIGNED_SHORT());
    }

    @Test
    public void testGL_UNSIGNED_SHORT_1_5_5_5_REV() throws Exception {
        expect(gl.GL_UNSIGNED_SHORT_1_5_5_5_REV()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_UNSIGNED_SHORT_1_5_5_5_REV());
    }

    @Test
    public void testGL_UNSIGNED_SHORT_4_4_4_4() throws Exception {
        expect(gl.GL_UNSIGNED_SHORT_4_4_4_4()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_UNSIGNED_SHORT_4_4_4_4());
    }

    @Test
    public void testGL_UNSIGNED_SHORT_4_4_4_4_REV() throws Exception {
        expect(gl.GL_UNSIGNED_SHORT_4_4_4_4_REV()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_UNSIGNED_SHORT_4_4_4_4_REV());
    }

    @Test
    public void testGL_UNSIGNED_SHORT_5_5_5_1() throws Exception {
        expect(gl.GL_UNSIGNED_SHORT_5_5_5_1()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_UNSIGNED_SHORT_5_5_5_1());
    }

    @Test
    public void testGL_UNSIGNED_SHORT_5_6_5() throws Exception {
        expect(gl.GL_UNSIGNED_SHORT_5_6_5()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_UNSIGNED_SHORT_5_6_5());
    }

    @Test
    public void testGL_UNSIGNED_SHORT_5_6_5_REV() throws Exception {
        expect(gl.GL_UNSIGNED_SHORT_5_6_5_REV()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_UNSIGNED_SHORT_5_6_5_REV());
    }

    @Test
    public void testGL_VENDOR() throws Exception {
        expect(gl.GL_VENDOR()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_VENDOR());
    }

    @Test
    public void testGL_VERSION() throws Exception {
        expect(gl.GL_VERSION()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_VERSION());
    }

    @Test
    public void testGL_VERTEX_SHADER() throws Exception {
        expect(gl.GL_VERTEX_SHADER()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_VERTEX_SHADER());
    }

    @Test
    public void testGL_VIEWPORT() throws Exception {
        expect(gl.GL_VIEWPORT()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_VIEWPORT());
    }

    @Test
    public void testGL_WRITE_ONLY() throws Exception {
        expect(gl.GL_WRITE_ONLY()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_WRITE_ONLY());
    }

    @Test
    public void testGL_ZERO() throws Exception {
        expect(gl.GL_ZERO()).andReturn(42);
        replay(gl);
        assertEquals(42, sut.GL_ZERO());
    }

    @Test
    public void testGlActiveTexture() throws Exception {
        gl.glActiveTexture(42);
        replay(gl);
        sut.glActiveTexture(42);
        sut.glActiveTexture(42);
    }

    @Test
    public void testGlAttachShader() throws Exception {
        gl.glAttachShader(42, 43);
        replay(gl);
        sut.glAttachShader(42, 43);
        sut.glAttachShader(42, 43);
    }

    @Test
    public void testGlBindAttribLocation() throws Exception {
        gl.glBindAttribLocation(42, 43, "huhu");
        replay(gl);
        sut.glBindAttribLocation(42, 43, "huhu");
        sut.glBindAttribLocation(42, 43, "huhu");
    }

    @Test
    public void testGlBindBuffer() throws Exception {
        gl.glBindBuffer(42, 43);
        replay(gl);
        sut.glBindBuffer(42, 43);
        sut.glBindBuffer(42, 43);
    }

    @Test
    public void testGlBindBufferBase() throws Exception {
        gl.glBindBufferBase(42, 43, 44);
        replay(gl);
        sut.glBindBufferBase(42, 43, 44);
        sut.glBindBufferBase(42, 43, 44);
    }

    @Test
    public void testGlBindFramebuffer() throws Exception {
        gl.glBindFramebuffer(42, 43);
        replay(gl);
        sut.glBindFramebuffer(42, 43);
        sut.glBindFramebuffer(42, 43);
    }

    @Test
    public void testGlBindRenderbuffer() throws Exception {
        gl.glBindRenderbuffer(42, 43);
        replay(gl);
        sut.glBindRenderbuffer(42, 43);
        sut.glBindRenderbuffer(42, 43);
    }

    @Test
    public void testGlBindTexture() throws Exception {
        gl.glBindTexture(42, 43);
        replay(gl);
        sut.glBindTexture(42, 43);
        sut.glBindTexture(42, 43);
    }

    @Test
    public void testGlBindVertexArray() throws Exception {
        gl.glBindVertexArray(42);
        replay(gl);
        sut.glBindVertexArray(42);
        sut.glBindVertexArray(42);
    }

    @Test
    public void testGlBlendEquationSeparate() throws Exception {
        gl.glBlendEquationSeparate(42, 43);
        replay(gl);
        sut.glBlendEquationSeparate(42, 43);
        sut.glBlendEquationSeparate(42, 43);
    }

    @Test
    public void testGlBlendFunc() throws Exception {
        gl.glBlendFunc(42, 43);
        replay(gl);
        sut.glBlendFunc(42, 43);
        sut.glBlendFunc(42, 43);
    }

    @Test
    public void testGlBlendFuncSeparate() throws Exception {
        gl.glBlendFuncSeparate(42, 43, 44, 45);
        replay(gl);
        sut.glBlendFuncSeparate(42, 43, 44, 45);
        sut.glBlendFuncSeparate(42, 43, 44, 45);
    }

    @Test
    public void testGlBufferDataFloat() throws Exception {
        gl.glBufferData(42, floatBuffer, 43);
        expectLastCall().times(2);
        replay(gl);
        sut.glBufferData(42, floatBuffer, 43);
        sut.glBufferData(42, floatBuffer, 43);
    }

    @Test
    public void testGlBufferDataInt() throws Exception {
        gl.glBufferData(42, intBuffer, 43);
        expectLastCall().times(2);
        replay(gl);
        sut.glBufferData(42, intBuffer, 43);
        sut.glBufferData(42, intBuffer, 43);
    }

    @Test
    public void testGlBufferDataShort() throws Exception {
        gl.glBufferData(42, shortBuffer, 43);
        expectLastCall().times(2);
        replay(gl);
        sut.glBufferData(42, shortBuffer, 43);
        sut.glBufferData(42, shortBuffer, 43);
    }

    @Test
    public void testGlCheckFramebufferStatus() throws Exception {
        expect(gl.glCheckFramebufferStatus(42)).andReturn(1);
        expectLastCall().times(2);
        replay(gl);
        assertEquals(1, sut.glCheckFramebufferStatus(42));
        assertEquals(1, sut.glCheckFramebufferStatus(42));
    }

    @Test
    public void testGlClear() throws Exception {
        gl.glClear(42);
        expectLastCall().times(2);
        replay(gl);
        sut.glClear(42);
        sut.glClear(42);
    }

    @Test
    public void testGlClearColor() throws Exception {
        gl.glClearColor(1.0f, 2.0f, 3.0f, 4.0f);
        replay(gl);
        sut.glClearColor(1.0f, 2.0f, 3.0f, 4.0f);
        sut.glClearColor(1.0f, 2.0f, 3.0f, 4.0f);
    }

    @Test
    public void testGlClearStencil() throws Exception {
        gl.glClearStencil(42);
        replay(gl);
        sut.glClearStencil(42);
        sut.glClearStencil(42);
    }

    @Test
    public void testGlCompileShader() throws Exception {
        gl.glCompileShader(42);
        expectLastCall().times(2);
        replay(gl);
        sut.glCompileShader(42);
        sut.glCompileShader(42);
    }

    @Test
    public void testGlColorMask() throws Exception {
        gl.glColorMask(true, false, true, false);
        replay(gl);
        sut.glColorMask(true, false, true, false);
        sut.glColorMask(true, false, true, false);
    }

    @Test
    public void testGlCreateProgram() throws Exception {
        expect(gl.glCreateProgram()).andReturn(1);
        expectLastCall().times(2);
        replay(gl);
        assertEquals(1, sut.glCreateProgram());
        assertEquals(1, sut.glCreateProgram());
    }

    @Test
    public void testGlCreateShader() throws Exception {
        expect(gl.glCreateShader(42)).andReturn(1);
        expectLastCall().times(2);
        replay(gl);
        assertEquals(1, sut.glCreateShader(42));
        assertEquals(1, sut.glCreateShader(42));
    }

    @Test
    public void testGlDepthMask() throws Exception {
        gl.glDepthMask(true);
        replay(gl);
        sut.glDepthMask(true);
        sut.glDepthMask(true);
    }

    @Test
    public void testGlDeleteBuffers() throws Exception {
        gl.glDeleteBuffers(42, intBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glDeleteBuffers(42, intBuffer);
        sut.glDeleteBuffers(42, intBuffer);
    }

    @Test
    public void testGlDeleteFramebuffers() throws Exception {
        gl.glDeleteFramebuffers(42, intBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glDeleteFramebuffers(42, intBuffer);
        sut.glDeleteFramebuffers(42, intBuffer);
    }

    @Test
    public void testGlDeleteTextures() throws Exception {
        gl.glDeleteTextures(42, intBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glDeleteTextures(42, intBuffer);
        sut.glDeleteTextures(42, intBuffer);
    }

    @Test
    public void testGlDeleteVertexArrays() throws Exception {
        gl.glDeleteVertexArrays(42, intBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glDeleteVertexArrays(42, intBuffer);
        sut.glDeleteVertexArrays(42, intBuffer);
    }

    @Test
    public void testGlDisableWithoutPriorEnable() throws Exception {
        replay(gl);
        sut.glDisable(42);
    }

    @Test
    public void testGlDisableWithPriorEnable() throws Exception {
        gl.glEnable(42);
        gl.glDisable(42);
        replay(gl);
        sut.glEnable(42);
        sut.glDisable(42);
        sut.glDisable(42);
    }

    @Test
    public void testGlDisableVertexAttribArrayWithoutPriorEnable() throws Exception {
        replay(gl);
        sut.glDisableVertexAttribArray(42);
        sut.glDisableVertexAttribArray(42);
    }

    @Test
    public void testGlDisableVertexAttribArrayWithPriorEnable() throws Exception {
        gl.glEnableVertexAttribArray(42);
        gl.glDisableVertexAttribArray(42);
        replay(gl);
        sut.glEnableVertexAttribArray(42);
        sut.glDisableVertexAttribArray(42);
        sut.glDisableVertexAttribArray(42);
    }

    @Test
    public void testGlDrawArrays() throws Exception {
        gl.glDrawArrays(42, 43, 44);
        expectLastCall().times(2);
        replay(gl);
        sut.glDrawArrays(42, 43, 44);
        sut.glDrawArrays(42, 43, 44);
    }

    @Test
    public void testGlDrawArraysInstanced() throws Exception {
        gl.glDrawArraysInstanced(42, 43, 44, 45);
        expectLastCall().times(2);
        replay(gl);
        sut.glDrawArraysInstanced(42, 43, 44, 45);
        sut.glDrawArraysInstanced(42, 43, 44, 45);
    }

    @Test
    public void testGlDrawBuffer() throws Exception {
        gl.glDrawBuffer(42);
        replay(gl);
        sut.glDrawBuffer(42);
        sut.glDrawBuffer(42);
    }

    @Test
    public void testGlDrawElements() throws Exception {
        gl.glDrawElements(42, 43, 44, 45);
        expectLastCall().times(2);
        replay(gl);
        sut.glDrawElements(42, 43, 44, 45);
        sut.glDrawElements(42, 43, 44, 45);
    }

    @Test
    public void testGlEnable() throws Exception {
        gl.glEnable(42);
        replay(gl);
        sut.glEnable(42);
        sut.glEnable(42);
    }

    @Test
    public void testGlEnableVertexAttribArray() throws Exception {
        gl.glEnableVertexAttribArray(42);
        replay(gl);
        sut.glEnableVertexAttribArray(42);
        sut.glEnableVertexAttribArray(42);
    }

    @Test
    public void testGlFramebufferRenderbuffer() throws Exception {
        gl.glFramebufferRenderbuffer(42, 43, 44, 45);
        expectLastCall().times(2);
        replay(gl);
        sut.glFramebufferRenderbuffer(42, 43, 44, 45);
        sut.glFramebufferRenderbuffer(42, 43, 44, 45);
    }

    @Test
    public void testGlFramebufferTexture2D() throws Exception {
        gl.glFramebufferTexture2D(42, 43, 44, 45, 46);
        expectLastCall().times(2);
        replay(gl);
        sut.glFramebufferTexture2D(42, 43, 44, 45, 46);
        sut.glFramebufferTexture2D(42, 43, 44, 45, 46);
    }

    @Test
    public void testGlFramebufferTextureLayer() throws Exception {
        gl.glFramebufferTextureLayer(42, 43, 44, 45, 46);
        expectLastCall().times(2);
        replay(gl);
        sut.glFramebufferTextureLayer(42, 43, 44, 45, 46);
        sut.glFramebufferTextureLayer(42, 43, 44, 45, 46);
    }

    @Test
    public void testGlGenBuffers() throws Exception {
        gl.glGenBuffers(42, intBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glGenBuffers(42, intBuffer);
        sut.glGenBuffers(42, intBuffer);
    }

    @Test
    public void testGlGenFramebuffers() throws Exception {
        gl.glGenFramebuffers(42, intBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glGenFramebuffers(42, intBuffer);
        sut.glGenFramebuffers(42, intBuffer);
    }

    @Test
    public void testGlGenRenderBuffers() throws Exception {
        gl.glGenRenderBuffers(42, intBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glGenRenderBuffers(42, intBuffer);
        sut.glGenRenderBuffers(42, intBuffer);
    }

    @Test
    public void testGlGenTextures() throws Exception {
        gl.glGenTextures(42, intBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glGenTextures(42, intBuffer);
        sut.glGenTextures(42, intBuffer);
    }

    @Test
    public void testGlGenVertexArrays() throws Exception {
        gl.glGenVertexArrays(42, intBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glGenVertexArrays(42, intBuffer);
        sut.glGenVertexArrays(42, intBuffer);
    }

    @Test
    public void testGlGenerateMipmap() throws Exception {
        gl.glGenerateMipmap(42);
        expectLastCall().times(2);
        replay(gl);
        sut.glGenerateMipmap(42);
        sut.glGenerateMipmap(42);
    }

    @Test
    public void testGlGetActiveUniforms() throws Exception {
        gl.glGetActiveUniforms(42, 43, intBuffer, 44, intBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glGetActiveUniforms(42, 43, intBuffer, 44, intBuffer);
        sut.glGetActiveUniforms(42, 43, intBuffer, 44, intBuffer);
    }

    @Test
    public void testGlGetAttribLocation() throws Exception {
        expect(gl.glGetAttribLocation(42, "huhu")).andReturn(1).times(2);
        replay(gl);
        assertEquals(1, sut.glGetAttribLocation(42, "huhu"));
        assertEquals(1, sut.glGetAttribLocation(42, "huhu"));
    }

    @Test
    public void testGlGetError() throws Exception {
        expect(gl.glGetError()).andReturn(42).times(2);
        replay(gl);
        assertEquals(42, sut.glGetError());
        assertEquals(42, sut.glGetError());
    }

    @Test
    public void testGlGetInteger() throws Exception {
        expect(gl.glGetInteger(42)).andReturn(44).times(2);
        replay(gl);
        assertEquals(44, sut.glGetInteger(42));
        assertEquals(44, sut.glGetInteger(42));
    }

    @Test
    public void testGlGetIntegerv() throws Exception {
        int[] intArray = new int[2];
        gl.glGetIntegerv(42, intArray, 43);
        expectLastCall().times(2);
        replay(gl);
        sut.glGetIntegerv(42, intArray, 43);
        sut.glGetIntegerv(42, intArray, 43);
    }

    @Test
    public void testGlGetIntegerv1() throws Exception {
        gl.glGetIntegerv(42, intBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glGetIntegerv(42, intBuffer);
        sut.glGetIntegerv(42, intBuffer);
    }

    @Test
    public void testGlGetProgramInfoLog() throws Exception {
        expect(gl.glGetProgramInfoLog(42)).andReturn("huhu").times(2);
        replay(gl);
        assertEquals("huhu", sut.glGetProgramInfoLog(42));
        assertEquals("huhu", sut.glGetProgramInfoLog(42));
    }

    @Test
    public void testGlGetProgramiv() throws Exception {
        gl.glGetProgramiv(42, 43, intBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glGetProgramiv(42, 43, intBuffer);
        sut.glGetProgramiv(42, 43, intBuffer);
    }

    @Test
    public void testGlGetShaderInfoLog() throws Exception {
        expect(gl.glGetShaderInfoLog(42)).andReturn("huhu");
        expectLastCall().times(2);
        replay(gl);
        assertEquals("huhu", sut.glGetShaderInfoLog(42));
        assertEquals("huhu", sut.glGetShaderInfoLog(42));
    }

    @Test
    public void testGlGetShaderiv() throws Exception {
        gl.glGetShaderiv(42, 43, intBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glGetShaderiv(42, 43, intBuffer);
        sut.glGetShaderiv(42, 43, intBuffer);
    }

    @Test
    public void testGlGetString() throws Exception {
        expect(gl.glGetString(42)).andReturn("huhu");
        expectLastCall().times(2);
        replay(gl);
        assertEquals("huhu", sut.glGetString(42));
        assertEquals("huhu", sut.glGetString(42));
    }

    @Test
    public void testGlGetTexImage() throws Exception {
        gl.glGetTexImage(42, 43, 44, 45, byteBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glGetTexImage(42, 43, 44, 45, byteBuffer);
        sut.glGetTexImage(42, 43, 44, 45, byteBuffer);
    }

    @Test
    public void testGlGetUniformBlockIndex() throws Exception {
        expect(gl.glGetUniformBlockIndex(42, "huhu")).andReturn(1);
        expectLastCall().times(2);
        replay(gl);
        assertEquals(1, sut.glGetUniformBlockIndex(42, "huhu"));
        assertEquals(1, sut.glGetUniformBlockIndex(42, "huhu"));
    }

    @Test
    public void testGlGetUniformIndices() throws Exception {
        String[] stringArray = new String[1];
        gl.glGetUniformIndices(42, stringArray, intBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glGetUniformIndices(42, stringArray, intBuffer);
        sut.glGetUniformIndices(42, stringArray, intBuffer);
    }

    @Test
    public void testGlGetUniformLocation() throws Exception {
        expect(gl.glGetUniformLocation(42, "huhu")).andReturn(1);
        expectLastCall().times(2);
        replay(gl);
        assertEquals(1, sut.glGetUniformLocation(42, "huhu"));
        assertEquals(1, sut.glGetUniformLocation(42, "huhu"));
    }

    @Test
    public void testGlIsEnabled() throws Exception {
        expect(gl.glIsEnabled(42)).andReturn(true);
        expectLastCall().times(2);
        replay(gl);
        assertTrue(sut.glIsEnabled(42));
        assertTrue(sut.glIsEnabled(42));
    }

    @Test
    public void testGlLinkProgram() throws Exception {
        gl.glLinkProgram(42);
        expectLastCall().times(2);
        replay(gl);
        sut.glLinkProgram(42);
        sut.glLinkProgram(42);
    }

    @Test
    public void testGlMapBuffer() throws Exception {
        expect(gl.glMapBuffer(42, 43, 44, byteBuffer)).andReturn(byteBuffer);
        expectLastCall().times(2);
        replay(gl);
        assertEquals(byteBuffer, sut.glMapBuffer(42, 43, 44, byteBuffer));
        assertEquals(byteBuffer, sut.glMapBuffer(42, 43, 44, byteBuffer));
    }

    @Test
    public void testGlPixelStorei() throws Exception {
        gl.glPixelStorei(42, 43);
        replay(gl);
        sut.glPixelStorei(42, 43);
        sut.glPixelStorei(42, 43);
    }

    @Test
    public void testGlPointSize() throws Exception {
        gl.glPointSize(42);
        replay(gl);
        sut.glPointSize(42);
        sut.glPointSize(42);
    }

    @Test
    public void testGlPrimitiveRestartIndex() throws Exception {
        gl.glPrimitiveRestartIndex(42);
        replay(gl);
        sut.glPrimitiveRestartIndex(42);
        sut.glPrimitiveRestartIndex(42);
    }

    @Test
    public void testGlReadPixels() throws Exception {
        gl.glReadPixels(42, 43, 44, 45, 46, 47, byteBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glReadPixels(42, 43, 44, 45, 46, 47, byteBuffer);
        sut.glReadPixels(42, 43, 44, 45, 46, 47, byteBuffer);
    }

    @Test
    public void testGlRenderbufferStorage() throws Exception {
        gl.glRenderbufferStorage(42, 43, 44, 45);
        replay(gl);
        sut.glRenderbufferStorage(42, 43, 44, 45);
        sut.glRenderbufferStorage(42, 43, 44, 45);
    }

    @Test
    public void testGlShaderSource() throws Exception {
        gl.glShaderSource(42, "huhu");
        expectLastCall().times(2);
        replay(gl);
        sut.glShaderSource(42, "huhu");
        sut.glShaderSource(42, "huhu");
    }

    @Test
    public void testGlStencilFunc() throws Exception {
        gl.glStencilFunc(42, 43, 44);
        replay(gl);
        sut.glStencilFunc(42, 43, 44);
        sut.glStencilFunc(42, 43, 44);
    }

    @Test
    public void testGlStencilFuncSeparate() throws Exception {
        gl.glStencilFuncSeparate(42, 43, 44, 45);
        replay(gl);
        sut.glStencilFuncSeparate(42, 43, 44, 45);
        sut.glStencilFuncSeparate(42, 43, 44, 45);
    }

    @Test
    public void testGlStencilMask() throws Exception {
        gl.glStencilMask(42);
        replay(gl);
        sut.glStencilMask(42);
        sut.glStencilMask(42);
    }

    @Test
    public void testGlStencilMaskSeparate() throws Exception {
        gl.glStencilMaskSeparate(42, 43);
        replay(gl);
        sut.glStencilMaskSeparate(42, 43);
        sut.glStencilMaskSeparate(42, 43);
    }

    @Test
    public void testGlStencilOp() throws Exception {
        gl.glStencilOp(42, 43, 44);
        replay(gl);
        sut.glStencilOp(42, 43, 44);
        sut.glStencilOp(42, 43, 44);
    }

    @Test
    public void testGlStencilOpSeparate() throws Exception {
        gl.glStencilOpSeparate(42, 43, 44, 45);
        replay(gl);
        sut.glStencilOpSeparate(42, 43, 44, 45);
        sut.glStencilOpSeparate(42, 43, 44, 45);
    }

    @Test
    public void testGlTexBuffer() throws Exception {
        gl.glTexBuffer(42, 43, 44);
        replay(gl);
        sut.glTexBuffer(42, 43, 44);
        sut.glTexBuffer(42, 43, 44);
    }

    @Test
    public void testGlTexImage2DByte() throws Exception {
        gl.glTexImage2D(42, 43, 44, 45, 46, 47, 48, 49, byteBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glTexImage2D(42, 43, 44, 45, 46, 47, 48, 49, byteBuffer);
        sut.glTexImage2D(42, 43, 44, 45, 46, 47, 48, 49, byteBuffer);
    }

    @Test
    public void testGlTexImage2DShort() throws Exception {
        gl.glTexImage2D(42, 43, 44, 45, 46, 47, 48, 49, shortBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glTexImage2D(42, 43, 44, 45, 46, 47, 48, 49, shortBuffer);
        sut.glTexImage2D(42, 43, 44, 45, 46, 47, 48, 49, shortBuffer);
    }

    @Test
    public void testGlTexImage2DInt() throws Exception {
        gl.glTexImage2D(42, 43, 44, 45, 46, 47, 48, 49, intBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glTexImage2D(42, 43, 44, 45, 46, 47, 48, 49, intBuffer);
        sut.glTexImage2D(42, 43, 44, 45, 46, 47, 48, 49, intBuffer);
    }

    @Test
    public void testGlTexImage2DFloat() throws Exception {
        gl.glTexImage2D(42, 43, 44, 45, 46, 47, 48, 49, floatBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glTexImage2D(42, 43, 44, 45, 46, 47, 48, 49, floatBuffer);
        sut.glTexImage2D(42, 43, 44, 45, 46, 47, 48, 49, floatBuffer);
    }

    @Test
    public void testGlTexImage2DDouble() throws Exception {
        gl.glTexImage2D(42, 43, 44, 45, 46, 47, 48, 49, doubleBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glTexImage2D(42, 43, 44, 45, 46, 47, 48, 49, doubleBuffer);
        sut.glTexImage2D(42, 43, 44, 45, 46, 47, 48, 49, doubleBuffer);
    }

    @Test
    public void testGlTexImage3DByte() throws Exception {
        gl.glTexImage3D(42, 43, 44, 45, 46, 47, 48, 49, 50, byteBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glTexImage3D(42, 43, 44, 45, 46, 47, 48, 49, 50, byteBuffer);
        sut.glTexImage3D(42, 43, 44, 45, 46, 47, 48, 49, 50, byteBuffer);
    }

    @Test
    public void testGlTexImage3DShort() throws Exception {
        gl.glTexImage3D(42, 43, 44, 45, 46, 47, 48, 49, 50, shortBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glTexImage3D(42, 43, 44, 45, 46, 47, 48, 49, 50, shortBuffer);
        sut.glTexImage3D(42, 43, 44, 45, 46, 47, 48, 49, 50, shortBuffer);
    }

    @Test
    public void testGlTexImage3DInt() throws Exception {
        gl.glTexImage3D(42, 43, 44, 45, 46, 47, 48, 49, 50, intBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glTexImage3D(42, 43, 44, 45, 46, 47, 48, 49, 50, intBuffer);
        sut.glTexImage3D(42, 43, 44, 45, 46, 47, 48, 49, 50, intBuffer);
    }

    @Test
    public void testGlTexImage3DFloat() throws Exception {
        gl.glTexImage3D(42, 43, 44, 45, 46, 47, 48, 49, 50, floatBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glTexImage3D(42, 43, 44, 45, 46, 47, 48, 49, 50, floatBuffer);
        sut.glTexImage3D(42, 43, 44, 45, 46, 47, 48, 49, 50, floatBuffer);
    }

    @Test
    public void testGlTexImage3DDouble() throws Exception {
        gl.glTexImage3D(42, 43, 44, 45, 46, 47, 48, 49, 50, doubleBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glTexImage3D(42, 43, 44, 45, 46, 47, 48, 49, 50, doubleBuffer);
        sut.glTexImage3D(42, 43, 44, 45, 46, 47, 48, 49, 50, doubleBuffer);
    }

    @Test
    public void testGlTexParameterf() throws Exception {
        gl.glTexParameterf(42, 43, 0.5f);
        replay(gl);
        sut.glTexParameterf(42, 43, 0.5f);
        sut.glTexParameterf(42, 43, 0.5f);
    }

    @Test
    public void testGlTexParameteri() throws Exception {
        gl.glTexParameteri(42, 43, 44);
        replay(gl);
        sut.glTexParameteri(42, 43, 44);
        sut.glTexParameteri(42, 43, 44);
    }

    @Test
    public void testGlTexSubImage2D() throws Exception {
        gl.glTexSubImage2D(42, 43, 44, 45, 46, 47, 48, 49, byteBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glTexSubImage2D(42, 43, 44, 45, 46, 47, 48, 49, byteBuffer);
        sut.glTexSubImage2D(42, 43, 44, 45, 46, 47, 48, 49, byteBuffer);
    }

    @Test
    public void testGlUniform1() throws Exception {
        gl.glUniform1(42, floatBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniform1(42, floatBuffer);
        sut.glUniform1(42, floatBuffer);
    }

    @Test
    public void testGlUniform1f() throws Exception {
        gl.glUniform1f(42, 0.5f);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniform1f(42, 0.5f);
        sut.glUniform1f(42, 0.5f);
    }

    @Test
    public void testGlUniform1fv() throws Exception {
        gl.glUniform1fv(42, floatBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniform1fv(42, floatBuffer);
        sut.glUniform1fv(42, floatBuffer);
    }

    @Test
    public void testGlUniform1i() throws Exception {
        gl.glUniform1i(42, 43);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniform1i(42, 43);
        sut.glUniform1i(42, 43);
    }

    @Test
    public void testGlUniform1iv() throws Exception {
        gl.glUniform1iv(42, intBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniform1iv(42, intBuffer);
        sut.glUniform1iv(42, intBuffer);
    }

    @Test
    public void testGlUniform2f() throws Exception {
        gl.glUniform2f(42, 0.4f, 0.5f);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniform2f(42, 0.4f, 0.5f);
        sut.glUniform2f(42, 0.4f, 0.5f);
    }

    @Test
    public void testGlUniform2fv() throws Exception {
        gl.glUniform2fv(42, floatBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniform2fv(42, floatBuffer);
        sut.glUniform2fv(42, floatBuffer);
    }

    @Test
    public void testGlUniform2i() throws Exception {
        gl.glUniform2i(42, 43, 44);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniform2i(42, 43, 44);
        sut.glUniform2i(42, 43, 44);
    }

    @Test
    public void testGlUniform2iv() throws Exception {
        gl.glUniform2iv(42, intBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniform2iv(42, intBuffer);
        sut.glUniform2iv(42, intBuffer);
    }

    @Test
    public void testGlUniform3f() throws Exception {
        gl.glUniform3f(42, 0.1f, 0.2f, 0.3f);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniform3f(42, 0.1f, 0.2f, 0.3f);
        sut.glUniform3f(42, 0.1f, 0.2f, 0.3f);
    }

    @Test
    public void testGlUniform3fv() throws Exception {
        gl.glUniform3fv(42, floatBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniform3fv(42, floatBuffer);
        sut.glUniform3fv(42, floatBuffer);
    }

    @Test
    public void testGlUniform3i() throws Exception {
        gl.glUniform3i(42, 43, 44, 45);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniform3i(42, 43, 44, 45);
        sut.glUniform3i(42, 43, 44, 45);
    }

    @Test
    public void testGlUniform3iv() throws Exception {
        gl.glUniform3iv(42, intBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniform3iv(42, intBuffer);
        sut.glUniform3iv(42, intBuffer);
    }

    @Test
    public void testGlUniform4f() throws Exception {
        gl.glUniform4f(42, 0.1f, 0.2f, 0.3f, 0.4f);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniform4f(42, 0.1f, 0.2f, 0.3f, 0.4f);
        sut.glUniform4f(42, 0.1f, 0.2f, 0.3f, 0.4f);
    }

    @Test
    public void testGlUniform4fv() throws Exception {
        gl.glUniform4fv(42, floatBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniform4fv(42, floatBuffer);
        sut.glUniform4fv(42, floatBuffer);
    }

    @Test
    public void testGlUniform4i() throws Exception {
        gl.glUniform4i(42, 43, 44, 45, 46);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniform4i(42, 43, 44, 45, 46);
        sut.glUniform4i(42, 43, 44, 45, 46);
    }

    @Test
    public void testGlUniform4iv() throws Exception {
        gl.glUniform4iv(42, intBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniform4iv(42, intBuffer);
        sut.glUniform4iv(42, intBuffer);
    }

    @Test
    public void testGlUniformBlockBinding() throws Exception {
        gl.glUniformBlockBinding(42, 43, 44);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniformBlockBinding(42, 43, 44);
        sut.glUniformBlockBinding(42, 43, 44);
    }

    @Test
    public void testGlUniformMatrix2() throws Exception {
        gl.glUniformMatrix2(42, true, floatBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniformMatrix2(42, true, floatBuffer);
        sut.glUniformMatrix2(42, true, floatBuffer);
    }

    @Test
    public void testGlUniformMatrix2x3() throws Exception {
        gl.glUniformMatrix2x3(42, true, floatBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniformMatrix2x3(42, true, floatBuffer);
        sut.glUniformMatrix2x3(42, true, floatBuffer);
    }

    @Test
    public void testGlUniformMatrix2x4() throws Exception {
        gl.glUniformMatrix2x4(42, true, floatBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniformMatrix2x4(42, true, floatBuffer);
        sut.glUniformMatrix2x4(42, true, floatBuffer);
    }

    @Test
    public void testGlUniformMatrix3() throws Exception {
        gl.glUniformMatrix3(42, true, floatBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniformMatrix3(42, true, floatBuffer);
        sut.glUniformMatrix3(42, true, floatBuffer);
    }

    @Test
    public void testGlUniformMatrix3x2() throws Exception {
        gl.glUniformMatrix3x2(42, true, floatBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniformMatrix3x2(42, true, floatBuffer);
        sut.glUniformMatrix3x2(42, true, floatBuffer);
    }

    @Test
    public void testGlUniformMatrix3x4() throws Exception {
        gl.glUniformMatrix3x4(42, true, floatBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniformMatrix3x4(42, true, floatBuffer);
        sut.glUniformMatrix3x4(42, true, floatBuffer);
    }

    @Test
    public void testGlUniformMatrix4() throws Exception {
        gl.glUniformMatrix4(42, true, floatBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniformMatrix4(42, true, floatBuffer);
        sut.glUniformMatrix4(42, true, floatBuffer);
    }

    @Test
    public void testGlUniformMatrix4x2() throws Exception {
        gl.glUniformMatrix4x2(42, true, floatBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniformMatrix4x2(42, true, floatBuffer);
        sut.glUniformMatrix4x2(42, true, floatBuffer);
    }

    @Test
    public void testGlUniformMatrix4x3() throws Exception {
        gl.glUniformMatrix4x3(42, true, floatBuffer);
        expectLastCall().times(2);
        replay(gl);
        sut.glUniformMatrix4x3(42, true, floatBuffer);
        sut.glUniformMatrix4x3(42, true, floatBuffer);
    }

    @Test
    public void testGlUnmapBuffer() throws Exception {
        expect(gl.glUnmapBuffer(42)).andReturn(true).times(2);
        replay(gl);
        assertTrue(sut.glUnmapBuffer(42));
        assertTrue(sut.glUnmapBuffer(42));
    }

    @Test
    public void testGlUseProgram() throws Exception {
        gl.glUseProgram(42);
        replay(gl);
        sut.glUseProgram(42);
        sut.glUseProgram(42);
    }

    @Test
    public void testGlVertexAttribDivisor() throws Exception {
        gl.glVertexAttribDivisor(42, 43);
        replay(gl);
        sut.glVertexAttribDivisor(42, 43);
        sut.glVertexAttribDivisor(42, 43);
    }

    @Test
    public void testGlVertexAttribIPointer() throws Exception {
        gl.glVertexAttribIPointer(42, 43, 44, 45, 46);
        expectLastCall().times(2);
        replay(gl);
        sut.glVertexAttribIPointer(42, 43, 44, 45, 46);
        sut.glVertexAttribIPointer(42, 43, 44, 45, 46);
    }

    @Test
    public void testGlVertexAttribPointer() throws Exception {
        gl.glVertexAttribPointer(42, 43, 44, true, 45, 46);
        expectLastCall().times(2);
        replay(gl);
        sut.glVertexAttribPointer(42, 43, 44, true, 45, 46);
        sut.glVertexAttribPointer(42, 43, 44, true, 45, 46);
    }

    @Test
    public void testGlViewport() throws Exception {
        gl.glViewport(42, 43, 44, 45);
        replay(gl);
        sut.glViewport(42, 43, 44, 45);
        sut.glViewport(42, 43, 44, 45);
    }
}
