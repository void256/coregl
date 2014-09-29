package de.lessvoid.coregl;

import java.nio.*;

/**
 * @author Brian Groenke
 */
public interface CoreUtil {
	
	IntBuffer createIntBuffer(int[] data);
	IntBuffer createIntBuffer(IntBuffer data);
	ShortBuffer createShortBuffer(short[] data);
	ShortBuffer createShortBuffer(ShortBuffer data);
	FloatBuffer createFloatBuffer(float[] data);
	FloatBuffer createFloatBuffer(FloatBuffer data);
	DoubleBuffer createDoubleBuffer(double[] data);
	DoubleBuffer createDoubleBuffer(DoubleBuffer data);
	ByteBuffer createByteBuffer(byte[] data);
	ByteBuffer createByteBuffer(ByteBuffer data);
	
	IntBuffer createIntBuffer(int size);
	FloatBuffer createFloatBuffer(int size);
	DoubleBuffer createDoubleBuffer(int size);
	ShortBuffer createShortBuffer(int size);
	ByteBuffer createByteBuffer(int size);
}
