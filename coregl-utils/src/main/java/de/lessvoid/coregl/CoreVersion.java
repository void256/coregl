package de.lessvoid.coregl;



/**
 * Version enumeration and identification for OpenGL and GLSL.
 * @author Brian Groenke &lt;bgroe8@gmail.com&gt;
 */
public class CoreVersion {

	/**
	 * Version enumeration for the OpenGL library.
	 * @author Brian Groenke
	 *
	 */
	public enum GLVersion {
		GL10(1,0), GL11(1,1), GL12(1,2), GL13(1,3), GL14(1,4), GL15(1,5), GL20(2,0), 
		GL21(2,1), GL30(3,0), GL31(3,1), GL32(3,2), GL33(3,3), GL40(4,0), GL41(4,1), 
		GL42(4,2),GL43(4,3);

		public final int versionMajor, versionMinor;

		GLVersion(int versionMajor, int versionMinor) {
			this.versionMajor = versionMajor;
			this.versionMinor = versionMinor;
		}

		@Override
		public String toString() {
			return String.valueOf(versionMajor) + "." + String.valueOf(versionMinor);
		}
		
		/**
		 * Checks to see if this GLVersion is equal to or follows the given GLVersion.
		 * @param version the version to check against this GLVersion
		 * @return true if this version is equal to or greater than <code>version</code>
		 */
		public boolean checkAgainst(GLVersion version) {
			return this.compareTo(version) >= 0;
		}
	}
	
	public static GLVersion getGLVersion(int versionMajor, int versionMinor) {
		for (GLVersion ver : GLVersion.values()) {
			if (ver.versionMajor == versionMajor && ver.versionMinor == versionMinor)
				return ver;
		}
		return null;
	}
	
	/**
	 * Returns a GLVersion enum instance corresponding to the OpenGL version specified by the given string.
	 * It is valid to pass the output from <code>glGetString(GL_VERSION)</string> into this method, as it will look
	 * for the GL version major/minor only at the beginning of the string and ingore everything that follows (i.e. 
	 * vendor/driver info, release number, etc).
	 * @param vstr the GL version string to parse
	 * @return corresponding GLVersion, if given input is a valid OpenGL version.
	 * @throws IllegalArgumentException if there is a problem parsing the format of the input string
	 */
	public static GLVersion getGLVersionFromString(String vstr) throws IllegalArgumentException {
		String[] vpts = vstr.split(" ")[0].split("\\.");
		if(vpts.length < 2)
			throw(new IllegalArgumentException("invalid format for version string: too few parts from '.' split - need version [MAJOR].[MINOR]"));
		GLVersion glVersion = null;
		try {
			int major = Integer.parseInt(vpts[0]);
			int minor = Integer.parseInt(vpts[1]);
			glVersion = getGLVersion(major, minor);
		} catch (NumberFormatException nfe) {
			throw(new IllegalArgumentException("invalid format for version string: error parsing version number (NumberFormatException)"));
		}
		
		return glVersion;
	}
	
	private static final String GLSL_VERSION_PREFIX = "#version";
	
	/**
	 * Version enumeration for the OpenGL Shading Language
	 * @author Brian Groenke
	 *
	 */
	public enum GLSLVersion {
		GLSL_110(110, GLVersion.GL20), GLSL_120(120, GLVersion.GL21), GLSL_130(130, GLVersion.GL30), 
		GLSL_140(140, GLVersion.GL31), GLSL_150(150, GLVersion.GL32), GLSL_330(330, GLVersion.GL33),
		GLSL_400(400, GLVersion.GL40), GLSL_410(410, GLVersion.GL41), GLSL_420(410, GLVersion.GL42),
		GLSL_430(430, GLVersion.GL43);
		
		public final int versionNum;
		
		private final GLVersion glVersion;
		
		GLSLVersion(int versionInt, GLVersion glVersion) {
			this.versionNum = versionInt;
			this.glVersion = glVersion;
		}
		
		/**
		 * Returns the GL version that corresponds to this version of GLSL
		 * @return this GLSL version's corresponding GLVersion
		 */
		public GLVersion getGLVersion() {
			return glVersion;
		}
	}
	
	public static GLSLVersion getGLSLVersion(int versionNumber) {
		for (GLSLVersion glslVer : GLSLVersion.values()) {
			if (versionNumber == glslVer.versionNum)
				return glslVer;
		}
		return null;
	}
	
	/**
	 * Returns a GLVersion enum instance corresponding to the GLSL version represented
	 * by the given string. There are two valid formats for the input string:<br/>
	 * 1) <code>glGetString(GL_SHADING_LANGUAGE_VERSION)</code> should return a version String
	 * that includes the '#version' tag at the beginning; i.e. for GLSL version 150 it returns
	 * '#version 150'. It is valid to pass this full String into {@link #getGLSLVersionFromString(String)}.
	 * The preceding version tag will be discarded and the version number parsed automatically.<br/>
	 * 2) It is also valid to simply pass in a string value of the version number; i.e. "150" for GLSL version 150.
	 * @param vstr the GLSL version string (in a correct format) to parse.
	 * @return the GLSLVersion corresponding to the given version input string.
	 * @throws IllegalArgumentException if there is a problem parsing the format of the input string
	 */
	public static GLSLVersion getGLSLVersionFromString(String vstr) throws IllegalArgumentException {
		if(vstr.startsWith(GLSL_VERSION_PREFIX)) {
			vstr = vstr.substring(GLSL_VERSION_PREFIX.length()); // (length - 1) + 1 to account for space
		}
		GLSLVersion glslVersion = null;
		try {
			glslVersion = getGLSLVersion(Integer.parseInt(vstr));
		} catch (NumberFormatException nfe) {
			throw(new IllegalArgumentException("error parsing GLSL version string: NumberFormatException"));
		}
		return glslVersion;
	}
}
