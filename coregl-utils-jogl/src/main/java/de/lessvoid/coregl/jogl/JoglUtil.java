package de.lessvoid.coregl.jogl;

import javax.media.opengl.*;

class JoglUtil {
	
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
