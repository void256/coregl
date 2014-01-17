package de.lessvoid.coregl.lwjgl;

import java.io.*;

class Test {

	public static void main(String[] args) throws FileNotFoundException {
		CoreFactoryLwjgl factory = CoreFactoryLwjgl.create();
		CoreShaderLwjgl shader = (CoreShaderLwjgl) factory.newShader();
		int vert = shader.vertexShader(new File("src/main/resources/test/test.vs"));
		int frag = shader.fragmentShader(new File("src/main/resources/test/test.fs"));
		
		shader.activate();
		final double N = 1.0E6;
		for(int i=0; i < N/100; i++) {
			shader.setUniformi("uOffset", 0, 0);
			shader.setDirectUniform2i("uOffset", 0, 0);
		}
	}
}
