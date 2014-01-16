package de.lessvoid.coregl.jogl;

import static javax.media.opengl.GL2ES2.*;

import java.io.*;
import java.lang.reflect.*;
import java.nio.*;
import java.util.Hashtable;
import java.util.logging.Logger;

import javax.media.opengl.*;

import com.jogamp.common.nio.Buffers;

import de.lessvoid.coregl.*;

public class CoreShaderJogl implements CoreShader {

	private final CoreCheckGL checkGL;

	private static final Logger log = Logger.getLogger(CoreShaderJogl.class.getName());
	private int program;
	private Hashtable<String, Integer> parameter = new Hashtable<String, Integer>();
	private FloatBuffer matBuffer = Buffers.newDirectFloatBuffer(new float[16]);
	private final String[] attributes;

	CoreShaderJogl(final CoreCheckGL checkGL, final String... vertexAttrs) {
		final GL2ES2 gl = getGL2();
		this.checkGL = checkGL;
		this.attributes = vertexAttrs;
		this.program = gl.glCreateProgram();
		checkGLError("glCreateProgram");
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#vertexShader(java.lang.String)
	 */
	@Override
	public int vertexShader(final String filename) {
		return vertexShader(filename, getStream(filename));
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#vertexShader(java.io.File)
	 */
	@Override
	public int vertexShader(final File file) throws FileNotFoundException {
		return vertexShader(file.getName(), getStream(file));
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#vertexShader(java.io.InputStream)
	 */
	public int vertexShader(final String streamName, final InputStream ... sources) {
		return vertexShaderFromStream(streamName, sources);
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#vertexShader(int, java.lang.String)
	 */
	@Override
	public void vertexShader(final int shaderId, final String filename) {
		vertexShader(shaderId, filename, getStream(filename));
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#vertexShader(int, java.io.File)
	 */
	@Override
	public void vertexShader(final int shaderId, final File file) throws FileNotFoundException {
		vertexShader(shaderId, file.getName(), getStream(file));
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#vertexShader(int, java.io.InputStream)
	 */
	@Override
	public void vertexShader(final int shaderId, final String streamName, final InputStream source) {
		prepareShader(shaderId, streamName, source);
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#geometryShader(int, java.lang.String)
	 */
	@Override
	public void geometryShader(final int shaderId, final String filename) {
		geometryShader(shaderId, filename, getStream(filename));
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#geometryShader(java.lang.String)
	 */
	@Override
	public int geometryShader(final String filename) {
		return geometryShaderFromStream(filename, getStream(filename));
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#geometryShader(java.io.File)
	 */
	@Override
	public int geometryShader(final File file) throws FileNotFoundException {
		return geometryShader(file.getName(), getStream(file));
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#geometryShader(java.io.File, java.io.InputStream[])
	 */
	@Override
	public int geometryShader(final File file, final InputStream ... inputStreams) throws FileNotFoundException {
		InputStream[] sources = new InputStream[inputStreams.length + 1];
		System.arraycopy(inputStreams, 0, sources, 0, inputStreams.length);
		sources[sources.length - 1] = getStream(file);
		return geometryShader(file.getName(), sources);
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#geometryShader(java.lang.String, java.io.InputStream[])
	 */
	@Override
	public int geometryShader(final String streamName, final InputStream ... inputStreams) throws FileNotFoundException {
		return geometryShaderFromStream(streamName, inputStreams);
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#geometryShader(int, java.io.InputStream)
	 */
	@Override
	public void geometryShader(final int shaderId, final String streamName, InputStream source) {
		prepareShader(shaderId, streamName, source);
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#geometryShader(int, java.io.File)
	 */
	@Override
	public void geometryShader(final int shaderId, final File file) throws FileNotFoundException {
		geometryShader(shaderId, file.getName(), getStream(file));
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#fragmentShader(java.lang.String)
	 */
	@Override
	public int fragmentShader(final String filename) {
		return fragmentShader(filename, getStream(filename));
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#fragmentShader(java.io.File)
	 */
	@Override
	public int fragmentShader(final File file) throws FileNotFoundException {
		return fragmentShaderFromStream(file.getName(), getStream(file));
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#fragmentShader(java.lang.String, java.io.InputStream[])
	 */
	@Override
	public int fragmentShader(final String streamName, final InputStream ... inputStreams) {
		return fragmentShaderFromStream(streamName, inputStreams);
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#fragmentShader(int, java.lang.String)
	 */
	@Override
	public void fragmentShader(final int shaderId, final String filename) {
		fragmentShader(shaderId, filename, getStream(filename));
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#fragmentShader(int, java.io.File)
	 */
	@Override
	public void fragmentShader(final int shaderId, final File file) throws FileNotFoundException {
		fragmentShader(shaderId, file.getName(), getStream(file));
	}

	/*
	 * (non-Javadoc)
	 * @see de.lessvoid.coregl.CoreShader#fragmentShader(int, java.io.InputStream)
	 */
	@Override
	public void fragmentShader(final int shaderId, final String streamName, final InputStream source) {
		prepareShader(shaderId, streamName, source);
	}

	private int vertexShaderFromStream(final String streamName, final InputStream ... sources) {
		final GL2ES2 gl = getGL2();
		int shaderId = gl.glCreateShader(GL_VERTEX_SHADER);
		checkGLError("glCreateShader(GL_VERTEX_SHADER)");
		prepareShader(shaderId, streamName, sources);
		gl.glAttachShader(program, shaderId);
		checkGLError("glAttachShader");
		return shaderId;
	}

	private int geometryShaderFromStream(final String streamName, final InputStream ... sources) {
		final GL2ES2 gl = getGL2();
		int shaderId = gl.glCreateShader(GL3.GL_GEOMETRY_SHADER);
		checkGLError("glCreateShader(GL_GEOMETRY_SHADER)");
		prepareShader(shaderId, streamName, sources);
		gl.glAttachShader(program, shaderId);
		checkGLError("glAttachShader");
		return shaderId;
	}

	private int fragmentShaderFromStream(final String streamName, final InputStream ... sources) {
		final GL2ES2 gl = getGL2();
		int shaderId = gl.glCreateShader(GL_FRAGMENT_SHADER);
		checkGLError("glCreateShader(GL_FRAGMENT_SHADER)");
		prepareShader(shaderId, streamName, sources);
		gl.glAttachShader(program, shaderId);
		checkGLError("glAttachShader");
		return shaderId;
	}

	private void prepareShader(final int shaderId, final String name, final InputStream ... srcStreams) {
		final GL2ES2 gl = getGL2();
		try {
			String[] sources = loadShader(srcStreams);
			int[] strlens = new int[sources.length];
			for(int i=0; i < sources.length; i++)
				strlens[i] = sources[i].length();
			gl.glShaderSource(shaderId, 1, sources, strlens, 0);
			checkGLError("glShaderSource");
		} catch (IOException e) {
			throw new CoreGLException(e);
		}

		gl.glCompileShader(shaderId);
		checkGLError("glCompileShader");

		int[] stat = new int[1];
		gl.glGetShaderiv(shaderId, GL2.GL_COMPILE_STATUS, stat, 0);

		if (stat[0] == GL_FALSE) {
			log.warning("'" + name + "' compile error: " + getShaderInfoLogJogl(shaderId));
		}

		printLogInfo(shaderId);
		checkGLError(String.valueOf(shaderId));
	}

	@Override
	public void link() {
		final GL2ES2 gl = getGL2();

		for(int i=0; i < attributes.length; i++) {
			gl.glBindAttribLocation(program, i, attributes[i]);
			checkGLError("glBindAttribLocation (" + attributes[i] + ")");
		}

		gl.glLinkProgram(program);
		checkGLError("glLinkProgram");
		gl.glValidateProgram(program);

		IntBuffer intBuff = IntBuffer.allocate(1);
		gl.glGetProgramiv(program, GL2.GL_LINK_STATUS, intBuff);
		if(intBuff.get(0) == GL_FALSE)
			log.warning("link error: " + getProgramInfoLogJogl());

		checkGLError("glGetProgram");
	}

	@Override
	public void activate() {
		final GL2ES2 gl = getGL2();
		gl.glUseProgram(program);
		checkGLError("glUseProgram");
	}
	
	// uniform data

	@Override
	public void setUniformi(final String name, final int... values) {
		setUniform(name, UniformTypeJogl.INT, values);
	}

	@Override
	public void setUniformf(final String name, final float... values) {
		setUniform(name, UniformTypeJogl.FLOAT, values);
	}

	@Override
	public void setUniformd(final String name, final double... values) {
		setUniform(name, UniformTypeJogl.DOUBLE, values);
	}

	@Override
	public void setUniformiv(final String name, final int componentNum, final int... values) {
		IntBuffer buff = Buffers.newDirectIntBuffer(values);
		setUniformv(name, componentNum, UniformTypeJogl.INT, buff);
	}

	@Override
	public void setUniformiv(final String name, final int componentNum, final IntBuffer values) {
		setUniformv(name, componentNum, UniformTypeJogl.INT, values);
	}

	@Override
	public void setUniformfv(final String name, final int componentNum, final float... values) {
		FloatBuffer buff = Buffers.newDirectFloatBuffer(values);
		setUniformv(name, componentNum, UniformTypeJogl.FLOAT, buff);

	}

	@Override
	public void setUniformfv(final String name, final int componentNum, final FloatBuffer values) {
		setUniformv(name, componentNum, UniformTypeJogl.FLOAT, values);

	}

	@Override
	public void setUniformdv(final String name, final int componentNum, final double... values) {
		DoubleBuffer buff = Buffers.newDirectDoubleBuffer(values);
		setUniformv(name, componentNum, UniformTypeJogl.DOUBLE, buff);

	}

	@Override
	public void setUniformdv(final String name, final int componentNum, final DoubleBuffer values) {
		setUniformv(name, componentNum, UniformTypeJogl.DOUBLE, values);
	}

	@Override
	public void setUniformMatrix(final String name, final int componentNum, final float... values) {
		FloatBuffer buff = Buffers.newDirectFloatBuffer(values);
		setUniformMatrix(name, componentNum, UniformTypeJogl.FLOAT, buff);
	}

	@Override
	public void setUniformMatrix(final String name, final int componentNum,
			FloatBuffer values) {
		setUniformMatrix(name, componentNum, UniformTypeJogl.FLOAT, values);
	}
	
	private void setUniform(final String name, final UniformTypeJogl type, final Object...values) {
		int loc = getLocation(name);
		String method = "glUniform"+values.length+type.suffix;
		try {
			switch(values.length) {
			case 1:
				Method m = GL2ES2.class.getMethod(method, int.class, type.value);
				m.setAccessible(true);
				m.invoke(getGL2(), loc, values[0]);
				break;
			case 2:
				m = GL2ES2.class.getMethod(method, int.class, type.value, type.value);
				m.setAccessible(true);
				m.invoke(getGL2(), loc, values[0], values[1]);
				break;
			case 3:
				m = GL2ES2.class.getMethod(method, int.class, type.value, type.value, type.value);
				m.setAccessible(true);
				m.invoke(getGL2(), loc, values[0], values[1], values[2]);
				break;
			case 4:
				m = GL2ES2.class.getMethod(method, int.class, type.value, type.value, type.value, type.value);
				m.setAccessible(true);
				m.invoke(getGL2(), loc, values[0], values[1], values[2], values[3]);
				break;
			default:
				throw(new IllegalArgumentException("illegal number of values supplied to "
						+ "setUniform"+type.suffix));
			}
		} catch (NoSuchMethodException e) {
			throw(new IllegalArgumentException("failed to locate set uniform method: " + method));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		checkGLError(method);
	}

	private void setUniformv(final String name, final int componentNum, 
			final UniformTypeJogl type, final Buffer data) {
        int loc = getLocation(name);
        if(componentNum < 1 || componentNum > 4)
        	throw(new IllegalArgumentException("illegal number of compoments for setUniform"+type.suffix+"v"));
        String method = "glUniform"+componentNum+type.suffix+"v";
        try {
                Method m = GL2ES2.class.getMethod(method, int.class, int.class, type.buffer);
                m.setAccessible(true);
                m.invoke(getGL2(), loc, data.limit(), data);
        } catch (NoSuchMethodException e) {
        	throw(new IllegalArgumentException("failed to locate set uniform method: " + method));
        } catch (SecurityException e) {
                e.printStackTrace();
        } catch (IllegalAccessException e) {
                e.printStackTrace();
        } catch (InvocationTargetException e) {
                e.printStackTrace();
        }
        checkGLError(method);
	}
	
	private void setUniformMatrix(final String name, final int componentNum,
			final UniformTypeJogl type, final Buffer data) {
        int loc = getLocation(name);
        if(componentNum < 2 || componentNum > 4)
        	throw(new IllegalArgumentException("illegal number of compoments for setUniformMatrix"));
        String method = "glUniformMatrix"+componentNum+type.suffix+"v";
        try {
                Method m = GL2ES2.class.getMethod(method, int.class, int.class, boolean.class, type.buffer);
                m.setAccessible(true);
                m.invoke(getGL2(), loc, 1, false, data);
        } catch (NoSuchMethodException e) {
        	throw(new IllegalArgumentException("failed to locate set uniform method: " + method));
        } catch (SecurityException e) {
                e.printStackTrace();
        } catch (IllegalAccessException e) {
                e.printStackTrace();
        } catch (InvocationTargetException e) {
                e.printStackTrace();
        }
        checkGLError(method);
	}
	
	//

	@Override
	public int getAttribLocation(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void bindAttribLocation(String name, int index) {
		// TODO Auto-generated method stub

	}

	private String[] loadShader(final InputStream ... srcStreams) throws IOException {
		String[] sources = new String[srcStreams.length];
		for(int i=0; i < srcStreams.length; i++) {
			BufferedReader buffIn = new BufferedReader(new InputStreamReader(srcStreams[i]));
			StringBuilder sb = new StringBuilder();
			String nextLine = null;
			while((nextLine = buffIn.readLine()) != null)
				sb.append(nextLine + "\n");
			sources[i] = sb.toString();
		}

		return sources;
	}

	private byte[] read(final InputStream dataStream) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		byte[] readBuffer = new byte[1024];
		int bytesRead = -1;
		try {
			while ((bytesRead = dataStream.read(readBuffer)) > 0) {
				out.write(readBuffer, 0, bytesRead);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				dataStream.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return out.toByteArray();
	}

	private String getShaderInfoLogJogl(int shaderId) {
		final GL2ES2 gl = getGL2();
		int[] logLength = new int[1];
		gl.glGetShaderiv(shaderId, GL_INFO_LOG_LENGTH, logLength, 0);
		byte[] logData = new byte[logLength[0]];
		gl.glGetShaderInfoLog(shaderId, logLength[0], null, 0, logData, 0);
		checkGLError("glGetShaderInfoLog");
		return new String(logData);
	}

	private String getProgramInfoLogJogl() {
		final GL2ES2 gl = getGL2();
		int[] loglen = new int[1];
		gl.glGetProgramiv(program, GL_INFO_LOG_LENGTH, loglen, 0);
		String logstr = null;
		if (loglen[0] > 0) {
			log.info("GLProgram link error: " + "[log len="+loglen[0]+"] ");
			ByteBuffer byteBuffer = ByteBuffer.allocate(loglen[0]);
			gl.glGetProgramInfoLog(program, loglen[0], null, byteBuffer);
			logstr = new String(byteBuffer.array());

		} else {
			log.warning("Info log is unavailable");
		}
		checkGLError("glGetProgramInfoLog");
		return logstr;
	}

	private int registerParameter(final String name) {
		int location = getUniform(name);
		parameter.put(name, location);
		return location;
	}

	private int getLocation(final String name) {
		Integer value = parameter.get(name);
		if (value == null) {
			return registerParameter(name);
		}
		return value;
	}
	
	private int getUniform(final String name) {
		return getGL2().glGetUniformLocation(program, name);
	}

	private void printLogInfo(final int obj) {
		String logStr = getShaderInfoLogJogl(obj);
		checkGLError("glGetShaderInfoLog");
		log.info(getLoggingPrefix() + "Info log:\n" + logStr);
		checkGLError("printLogInfo");
	}

	private void checkGLError(final String message) {
		checkGL.checkGLError(getLoggingPrefix() + message);
	}

	private String getLoggingPrefix() {
		return "[" + program + "] ";
	}

	private InputStream getStream(final File file) throws FileNotFoundException {
		log.fine("loading shader file [" + file + "]");
		return new ByteArrayInputStream(read(new FileInputStream(file)));
	}

	private InputStream getStream(final String filename) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
	}

	private GL2ES2 getGL2() {
		return GLContext.getCurrentGL().getGL2ES2();
	}
	
	enum UniformTypeJogl {
		INT("i", int.class, int[].class, IntBuffer.class),
		FLOAT("f", float.class, float[].class, FloatBuffer.class),
		DOUBLE("d", double.class, double[].class, DoubleBuffer.class);

		String suffix;
		Class<?> value, array, buffer;

		UniformTypeJogl(String suffix, Class<?> value, Class<?> array, Class<?> buffer) {
			this.suffix = suffix;
			this.array = array;
			this.buffer = buffer;
			this.value = value;
		}
	}
}
