package de.lessvoid.coregl;

import java.util.logging.Logger;

import de.lessvoid.coregl.spi.CoreGL;



/**
 * Version enumeration and identification for OpenGL and GLSL.
 * @author Brian Groenke
 */
public class CoreVersion {
	
	private static final Logger LOG = Logger.getLogger(CoreVersion.class.getName());

	/**
	 * Version enumeration for the OpenGL library.
	 * @author Brian Groenke
	 *
	 */
	public enum GLVersion {
		GL10(1,0), GL11(1,1), GL12(1,2), GL13(1,3), GL14(1,4), GL15(1,5), GL20(2,0), 
		GL21(2,1), GL30(3,0), GL31(3,1), GL32(3,2), GL33(3,3), GL40(4,0), GL41(4,1), 
		GL42(4,2), GL43(4,3), GL44(4,4);

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
	 */
	public static GLVersion getGLVersionFromString(String vstr) {
		String[] vpts = vstr.split(" ")[0].split("\\.");
		if(vpts.length < 2) {
			LOG.warning("invalid format for GL version string: too few parts from '.' split - need version [MAJOR].[MINOR]");
			return null; // abort and return early
		}
		GLVersion glVersion = null;
		try {
			int major = Integer.parseInt(vpts[0]);
			int minor = Integer.parseInt(vpts[1]);
			glVersion = getGLVersion(major, minor);
		} catch (NumberFormatException nfe) {
			LOG.warning("error parsing GL version string: " + nfe.toString());
		}
		
		return glVersion;
	}
	
	/**
	 * Convenience method - equivalent to: <code>gl.getUtil().getGLVersion().checkAgainst(verCheck);</code>
	 * @param gl used to get the version of the currently active GL context
	 * @param verCheck the version to check against
	 * @return true if the current GL version is greater than or equal to <code>verCheck</code>
	 */
	public static boolean checkCurrentGLVersion(CoreGL gl, GLVersion verCheck) {
		return gl.getUtil().getGLVersion().checkAgainst(verCheck);
	}
	
	/**
	 * Version enumeration for the OpenGL Shading Language
	 * @author Brian Groenke
	 *
	 */
	public enum GLSLVersion {
		GLSL_110(110, GLVersion.GL20), GLSL_120(120, GLVersion.GL21), GLSL_130(130, GLVersion.GL30), 
		GLSL_140(140, GLVersion.GL31), GLSL_150(150, GLVersion.GL32), GLSL_330(330, GLVersion.GL33),
		GLSL_400(400, GLVersion.GL40), GLSL_410(410, GLVersion.GL41), GLSL_420(410, GLVersion.GL42),
		GLSL_430(430, GLVersion.GL43), GLSL_440(440, GLVersion.GL44);
		
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
		
		/**
		 * Checks to see if this GLSLVersion is equal to or follows the given GLVersion.
		 * @param version the version to check against this GLSLVersion
		 * @return true if this version is equal to or greater than <code>version</code>
		 */
		public boolean checkAgainst(GLSLVersion version) {
			return this.compareTo(version) >= 0;
		}
		
		@Override
		/**
		 * @return the GLSL version string as it appears in shader files: e.g. #version 150
		 */
		public String toString() {
			return String.valueOf("#version " + versionNum);
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
	 * in the version format [MAJOR].[MINOR] or [MAJOR].[MINOR].[RELEASE] followed by vendor specific
	 * driver information. It is valid to pass in this value to this method; the release number (if any)
	 * as well as the trailing vendor information will be discarded and the major/minor version IDs parsed.<br/>
	 * 2) It is also valid to simply pass in a string value of the version number; i.e. "150" for GLSL version 150.
	 * @param vstr the GLSL version string (in a correct format) to parse.
	 * @return the GLSLVersion corresponding to the given version input string.
	 */
	public static GLSLVersion getGLSLVersionFromString(String vstr) {
		GLSLVersion glslVersion = null;
		vstr = vstr.split("\\s+")[0];
		String[] vpts = vstr.split("\\.");
		
		// if two part [MAJOR].[MINOR] format is not detected, try parsing as a single GLSL version string
		// i.e. '440' -- on failure, print log warning and return null reference
		if (vpts.length < 2) {
			try {
				glslVersion = getGLSLVersion(Integer.parseInt(vstr));
			} catch (NumberFormatException nfe) {
				LOG.warning("invalid format for GLSL version string: must begin with either [MAJOR].[MINOR] "
						+ "or [MAJOR[MINOR]]");
			}
			return glslVersion;
		}
		
		try {
			String glslVersionStr = vpts[0] + vpts[1]; // major + minor, no period i.e. for 4.40: '4' + '40' -> '440'
			glslVersion = getGLSLVersion(Integer.parseInt(glslVersionStr));
			if (glslVersion == null)
				LOG.warning("unrecognized GLSL version number: " + vstr);
		} catch (NumberFormatException nfe) {
			LOG.warning("error parsing GLSL version string: " + nfe.toString());
		}
		return glslVersion;
	}
	
	/**
	 * Convenience method - equivalent to: <code>gl.getUtil().getGLSLVersion().checkAgainst(verCheck);</code>
	 * @param gl used to get the version of GLSL supported by the currently active GL context
	 * @param verCheck the version to check against
	 * @return true if the current GLSL version is greater than or equal to <code>verCheck</code>
	 */
	public static boolean checkCurrentGLSLVersion(CoreGL gl, GLSLVersion verCheck) {
		return gl.getUtil().getGLSLVersion().checkAgainst(verCheck);
	}
}
