package de.lessvoid.coregl.examples;

import de.lessvoid.coregl.examples.spi.CoreExample;

class ExampleMain {

	/**
	 * Evaluates main method arguments to 
	 * @param example
	 * @param mainArgs
	 */
	static void runExample(CoreExample example, String[] mainArgs) {
		String backendName = "jogl"; // default backend to use in case one isn't specified
		if(mainArgs.length == 0) {
			System.err.println("No backend argument supplied. Defaulting to '" + backendName + "'");
			printUsageString();
		}
		if (backendName.equalsIgnoreCase("jogl")) {
			System.err.println("running " + example.getClass().getName() + ": " + "backend=JOGL");
			example.runJogl();
		} else if (backendName.equalsIgnoreCase("lwjgl")) {
			System.err.println("running " + example.getClass().getName() + ": " + "backend=LWJGL");
			example.runLwjgl();
		} else {
			System.err.println("unrecognized backend name: " + mainArgs[0]);
			printUsageString();
		}
	}
	
	private static void printUsageString() {
		System.err.println("Usage: to specify a CoreGL backend, pass its identifier as a program argument (JOGL = 'jogl' LWJGL = 'lwjgl')");
	}
}
