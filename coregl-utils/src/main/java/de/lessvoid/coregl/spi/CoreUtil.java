package de.lessvoid.coregl.spi;

import java.nio.*;

/**
 * @author Brian Groenke
 */
public interface CoreUtil {
	
	// provide some common version values as interface constants
	public static final String GL15 = "1.5", GL20 = "2.0", GL21 = "2.1", GL30 = "30", GL33 = "3.3", GL40 = "4.0", GL43 = "4.3",
			GLSL120 = "1.20", GLSL130 = "1.30", GLSL150 = "1.50";
	
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
	
	/**
	 * Fetch the version of the current GL context. Some (not all) possible return values are provided as constants
	 * in CoreUtil.
	 * @return the version of the current GL context in the form [MAJOR.[MINOR]] i.e. GL 3.3 returns "3.3"
	 */
	String getGLVersionString();
	/**
	 * Fetch the maximum GLSL version supported in the current context. Some (not all) possible values are provided as
	 * constants in CoreUtil.
	 * @return the supported GLSL version in the form [MAJOR.[MINOR]] i.e. GLSL 150 returns "1.50"
	 */
	String getGLSLVersionString();
	boolean isGLVersion(String version);
	boolean isGLSLVersion(String version);
}
