package de.lessvoid.coregl.lwjgl;

import java.nio.*;

import de.lessvoid.coregl.spi.CoreUtil;

import org.lwjgl.BufferUtils;

/**
 * @author Brian Groenkes
 */
public class LwjglCoreUtil implements CoreUtil {

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
}
