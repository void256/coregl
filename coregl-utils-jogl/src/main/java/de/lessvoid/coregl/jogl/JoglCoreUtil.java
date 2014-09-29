package de.lessvoid.coregl.jogl;

import java.nio.*;

import javax.media.opengl.GL;

import com.jogamp.common.nio.Buffers;

import de.lessvoid.coregl.CoreUtil;

/**
 * @author Brian Groenke
 */
class JoglCoreUtil implements CoreUtil {
	
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
}
