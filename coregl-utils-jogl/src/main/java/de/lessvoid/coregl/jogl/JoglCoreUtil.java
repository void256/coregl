package de.lessvoid.coregl.jogl;

import java.nio.*;

import javax.media.opengl.GL;

import com.jogamp.common.nio.Buffers;

import de.lessvoid.coregl.CoreUtil;

class JoglCoreUtil implements CoreUtil {
	
	@Override
	public IntBuffer createIntBuffer(int[] data) {
		return Buffers.newDirectIntBuffer(data);
	}

	@Override
	public FloatBuffer createFloatBuffer(float[] data) {
		return Buffers.newDirectFloatBuffer(data);
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
	public FloatBuffer createFloatBuffer(FloatBuffer data) {
		int npos = data.position();
		float[] arr = new float[data.remaining()];
		data.get(arr);
		data.position(npos);
		return Buffers.newDirectFloatBuffer(arr);
	}
	
	// JOGL annoyingly doesn't have a built in function for singular glGet(s)
	
	static int glGetInteger(GL gl, int pname) {
		int[] store = new int[1];
		gl.glGetIntegerv(pname, store, 0);
		return store[0];
	}
	
	static float glGetFloat(GL gl, int pname) {
		float[] store = new float[1];
		gl.glGetFloatv(pname, store, 0);
		return store[0];
	}
}
