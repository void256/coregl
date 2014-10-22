package de.lessvoid.coregl.lwjgl;

import java.nio.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;

import de.lessvoid.coregl.*;
import de.lessvoid.coregl.CoreVersion.GLSLVersion;
import de.lessvoid.coregl.CoreVersion.GLVersion;
import de.lessvoid.coregl.spi.*;

/**
 * @author Brian Groenke &lt;bgroe8@gmail.com&gt;
 */
public class LwjglCoreUtil implements CoreUtil {
	
	@Override
	public int gluBuild2DMipmaps(int target, int internalFormat, int width,
			int height, int format, int type, ByteBuffer data) {
		return GLU.gluBuild2DMipmaps(target, internalFormat, width, height, format, type, data);
	}

	@Override
	public ByteBuffer createByteBuffer(byte[] data) {
		ByteBuffer buffer = BufferUtils.createByteBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	@Override
	public ByteBuffer createByteBuffer(ByteBuffer data) {
		ByteBuffer buffer = BufferUtils.createByteBuffer(data.limit());
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	@Override
	public IntBuffer createIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	@Override
	public IntBuffer createIntBuffer(IntBuffer data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.limit());
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	@Override
	public ShortBuffer createShortBuffer(short[] data) {
		ShortBuffer buffer = BufferUtils.createShortBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	@Override
	public ShortBuffer createShortBuffer(ShortBuffer data) {
		ShortBuffer buffer = BufferUtils.createShortBuffer(data.limit());
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	@Override
	public FloatBuffer createFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	@Override
	public FloatBuffer createFloatBuffer(FloatBuffer data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.limit());
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	@Override
	public DoubleBuffer createDoubleBuffer(double[] data) {
		DoubleBuffer buffer = BufferUtils.createDoubleBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	@Override
	public DoubleBuffer createDoubleBuffer(DoubleBuffer data) {
		DoubleBuffer buffer = BufferUtils.createDoubleBuffer(data.limit());
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	@Override
	public IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}

	@Override
	public FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}

	@Override
	public ByteBuffer createByteBuffer(int size) {
		return BufferUtils.createByteBuffer(size);
	}
	
	@Override
	public ShortBuffer createShortBuffer(int size) {
		return BufferUtils.createShortBuffer(size);
	}
	
	@Override
	public DoubleBuffer createDoubleBuffer(int size) {
		return BufferUtils.createDoubleBuffer(size);
	}
	
	@Override
	public boolean isNPOTSupported() {
		return getGLVersion().checkAgainst(GLVersion.GL20) || isNPOTHardwareSupported();
	}
	
	@Override
	public boolean isNPOTHardwareSupported() {
		return GLContext.getCapabilities().GL_ARB_texture_non_power_of_two;
	}
	
	@Override
	public GLVersion getGLVersion() {
		String glVersionString = GL11.glGetString(GL11.GL_VERSION);
		return CoreVersion.getGLVersionFromString(glVersionString);
	}
	
	@Override
	public GLSLVersion getGLSLVersion() {
		String glslVersionString = GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION);
		return CoreVersion.getGLSLVersionFromString(glslVersionString);
	}
}
