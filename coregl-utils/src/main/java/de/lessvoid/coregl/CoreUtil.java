package de.lessvoid.coregl;

import java.nio.*;

public interface CoreUtil {
	
	IntBuffer createIntBuffer(int[] data);
	IntBuffer createIntBuffer(IntBuffer data);
	FloatBuffer createFloatBuffer(float[] data);
	FloatBuffer createFloatBuffer(FloatBuffer data);
}
