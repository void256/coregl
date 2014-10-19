package de.lessvoid.coregl.spi;

import java.nio.*;

import de.lessvoid.coregl.CoreVersion;

/**
 * @author Brian Groenke &lt;bgroe8@gmail.com&gt;
 */
public interface CoreUtil {
	
	int gluBuild2DMipmaps(int target, int internalFormat, int width, int height, int format, int type, ByteBuffer data);
	
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
	
	boolean isNPOTSupported();
	boolean isNPOTHardwareSupported();
	
	CoreVersion.GLVersion getGLVersion();
	CoreVersion.GLSLVersion getGLSLVersion();
}
