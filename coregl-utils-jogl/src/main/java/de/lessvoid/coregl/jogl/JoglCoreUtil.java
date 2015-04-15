package de.lessvoid.coregl.jogl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import com.jogamp.common.nio.Buffers;
import com.jogamp.common.util.VersionNumber;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLExtensions;
import com.jogamp.opengl.glu.GLU;

import de.lessvoid.coregl.CoreVersion;
import de.lessvoid.coregl.CoreVersion.GLSLVersion;
import de.lessvoid.coregl.CoreVersion.GLVersion;
import de.lessvoid.coregl.spi.CoreUtil;

/**
 * @author Brian Groenke
 */
class JoglCoreUtil implements CoreUtil {

	@Override
	public int gluBuild2DMipmaps(int target, int internalFormat, int width,
			int height, int format, int type, ByteBuffer data) {
		return GLU.createGLU().gluBuild2DMipmaps(target, internalFormat, width, height, format, type, data);
	}

	@Override
	public ByteBuffer createByteBuffer(byte[] data) {
		return Buffers.newDirectByteBuffer(data);
	}

	@Override
	public IntBuffer createIntBuffer(int[] data) {
		return Buffers.newDirectIntBuffer(data);
	}

	@Override
	public ShortBuffer createShortBuffer(short[] data) {
		return Buffers.newDirectShortBuffer(data);
	}

	@Override
	public FloatBuffer createFloatBuffer(float[] data) {
		return Buffers.newDirectFloatBuffer(data);
	}

	@Override
	public DoubleBuffer createDoubleBuffer(double[] data) {
		return Buffers.newDirectDoubleBuffer(data);
	}

	@Override
	public ByteBuffer createByteBuffer(ByteBuffer data) {
		int npos = data.position();
		byte[] arr = new byte[data.remaining()];
		data.get(arr);
		data.position(npos);
		return Buffers.newDirectByteBuffer(arr);
	}

	@Override
	public IntBuffer createIntBuffer(IntBuffer data) {
		int npos = data.position();
		int[] arr = new int[data.remaining()];
		data.get(arr);
		data.position(npos);
		return Buffers.newDirectIntBuffer(arr);
	}

	@Override
	public ShortBuffer createShortBuffer(ShortBuffer data) {
		int npos = data.position();
		short[] arr = new short[data.remaining()];
		data.get(arr);
		data.position(npos);
		return Buffers.newDirectShortBuffer(arr);
	}

	@Override
	public FloatBuffer createFloatBuffer(FloatBuffer data) {
		int npos = data.position();
		float[] arr = new float[data.remaining()];
		data.get(arr);
		data.position(npos);
		return Buffers.newDirectFloatBuffer(arr);
	}

	@Override
	public DoubleBuffer createDoubleBuffer(DoubleBuffer data) {
		int npos = data.position();
		double[] arr = new double[data.remaining()];
		data.get(arr);
		data.position(npos);
		return Buffers.newDirectDoubleBuffer(arr);
	}

	@Override
	public IntBuffer createIntBuffer(int size) {
		return Buffers.newDirectIntBuffer(size);
	}

	@Override
	public FloatBuffer createFloatBuffer(int size) {
		return Buffers.newDirectFloatBuffer(size);
	}

	@Override
	public ByteBuffer createByteBuffer(int size) {
		return Buffers.newDirectByteBuffer(size);
	}

	@Override
	public DoubleBuffer createDoubleBuffer(int size) {
		return Buffers.newDirectDoubleBuffer(size);
	}

	@Override
	public ShortBuffer createShortBuffer(int size) {
		return Buffers.newDirectShortBuffer(size);
	}

	// JOGL annoyingly doesn't have a built in function for singular glGet(s)

	public static int glGetInteger(GL gl, int pname) {
		int[] store = new int[1];
		gl.glGetIntegerv(pname, store, 0);
		return store[0];
	}

	public static float glGetFloat(GL gl, int pname) {
		float[] store = new float[1];
		gl.glGetFloatv(pname, store, 0);
		return store[0];
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
		VersionNumber glVersion = GLContext.getCurrent().getGLVersionNumber();
		return CoreVersion.getGLVersion(glVersion.getMajor(), glVersion.getMinor());
	}

	@Override
	public GLSLVersion getGLSLVersion() {
		VersionNumber glslVersion = GLContext.getCurrent().getGLSLVersionNumber();
		return CoreVersion.getGLSLVersion(glslVersion.getMajor() * 100 + glslVersion.getMinor());
	}
}
