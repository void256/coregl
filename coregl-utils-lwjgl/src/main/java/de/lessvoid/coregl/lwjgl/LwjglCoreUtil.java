package de.lessvoid.coregl.lwjgl;

import java.nio.*;

import de.lessvoid.coregl.CoreUtil;
import org.lwjgl.BufferUtils;

public class LwjglCoreUtil implements CoreUtil {

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

}
