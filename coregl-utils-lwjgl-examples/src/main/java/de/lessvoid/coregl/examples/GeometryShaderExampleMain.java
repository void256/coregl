/**
 * Copyright (c) 2013, Jens Hohmuth 
 * All rights reserved. 
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are 
 * met: 
 * 
 *  * Redistributions of source code must retain the above copyright 
 *    notice, this list of conditions and the following disclaimer. 
 *  * Redistributions in binary form must reproduce the above copyright 
 *    notice, this list of conditions and the following disclaimer in the 
 *    documentation and/or other materials provided with the distribution. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF 
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package de.lessvoid.coregl.examples;

import java.io.IOException;

import org.junit.Test;

import de.lessvoid.coregl.*;
import de.lessvoid.coregl.CoreTexture2DConstants.ColorFormat;
import de.lessvoid.coregl.CoreTexture2DConstants.ResizeFilter;
import de.lessvoid.coregl.CoreVAO.FloatType;
import de.lessvoid.coregl.CoreVBO.DataType;
import de.lessvoid.coregl.CoreVBO.UsageType;
import de.lessvoid.coregl.examples.spi.CoreExample;
import de.lessvoid.coregl.jogl.*;
import de.lessvoid.coregl.lwjgl.*;
import de.lessvoid.coregl.spi.*;
import de.lessvoid.coregl.spi.CoreSetup.RenderLoopCallback;
import de.lessvoid.math.*;
import de.lessvoid.simpleimageloader.*;

/**
 * The GeometryShaderExampleMain demonstrates the lwjgl-core-utils API to use an geometry shader. It will render a
 * quad from a single GL_POINT.
 *
 * @author void
 */
public class GeometryShaderExampleMain implements RenderLoopCallback, CoreExample {
	
	int vertexCount = 1000;
	int dataPerVertex = 28;
	CoreRender coreRender;

	@Override
	public void init(final CoreGL gl) {
		coreRender = CoreRender.createCoreRender(gl);
		CoreShader shader = CoreShader.createShader(gl);
		shader.vertexShader("geometry-shader/vertex.vs");
		shader.fragmentShader("geometry-shader/fragment.fs");
		shader.geometryShader("geometry-shader/geometry.gs");
		shader.link();

		CoreVAO vao = CoreVAO.createCoreVAO(gl);
		vao.bind();

		Float[] vertexData = new Float[vertexCount*dataPerVertex];
		int index = 0;
		for (int i=0; i<vertexCount; i++) {
			vertexData[index++] = (float)Math.random() * (1024.f - 50.f);
			vertexData[index++] = (float)Math.random() * (768.f - 50.f);
			vertexData[index++] = (float)Math.random() * 50.f + 50.f;
			vertexData[index++] = (float)Math.random() * 50.f + 50.f;

			vertexData[index++] = (float)Math.random();
			vertexData[index++] = (float)Math.random();
			vertexData[index++] = (float)Math.random();
			vertexData[index++] = (float)Math.random();

			vertexData[index++] = (float)Math.random();
			vertexData[index++] = (float)Math.random();
			vertexData[index++] = (float)Math.random();
			vertexData[index++] = (float)Math.random();

			vertexData[index++] = (float)Math.random();
			vertexData[index++] = (float)Math.random();
			vertexData[index++] = (float)Math.random();
			vertexData[index++] = (float)Math.random();

			vertexData[index++] = (float)Math.random();
			vertexData[index++] = (float)Math.random();
			vertexData[index++] = (float)Math.random();
			vertexData[index++] = (float)Math.random();

			vertexData[index++] = 0.5f;
			vertexData[index++] = 0.5f;

			vertexData[index++] = 0.0001f;//1.0f;
			vertexData[index++] = 0.5f;

			vertexData[index++] = 0.5f;
			vertexData[index++] = 0.0001f;//1.0f;

			vertexData[index++] = 0.0001f;//1.0f;
			vertexData[index++] = 0.0001f;//1.0f;
		};

		// create and send VBO
		CoreVBO.createCoreVBO(gl, DataType.FLOAT, UsageType.DYNAMIC_DRAW, vertexData).send();

		// parameters are: index, size, stride, offset
		// this will use the currently active VBO to store the VBO in the VAO

		int attribLocation = shader.getAttribLocation("aVertex");
		vao.enableVertexAttribute(attribLocation);
		vao.vertexAttribPointer(attribLocation, 4, FloatType.FLOAT, dataPerVertex, 0);

		attribLocation = shader.getAttribLocation("aColor1");
		vao.enableVertexAttribute(attribLocation);
		vao.vertexAttribPointer(shader.getAttribLocation("aColor1"), 4, FloatType.FLOAT, dataPerVertex, 4);

		attribLocation = shader.getAttribLocation("aColor2");
		vao.enableVertexAttribute(attribLocation);
		vao.vertexAttribPointer(shader.getAttribLocation("aColor2"), 4, FloatType.FLOAT, dataPerVertex, 8);

		attribLocation = shader.getAttribLocation("aColor3");
		vao.enableVertexAttribute(attribLocation);
		vao.vertexAttribPointer(shader.getAttribLocation("aColor3"), 4, FloatType.FLOAT, dataPerVertex, 12);

		attribLocation = shader.getAttribLocation("aColor4");
		vao.enableVertexAttribute(attribLocation);
		vao.vertexAttribPointer(shader.getAttribLocation("aColor4"), 4, FloatType.FLOAT, dataPerVertex, 16);

		attribLocation = shader.getAttribLocation("aUV1");
		vao.enableVertexAttribute(attribLocation);
		vao.vertexAttribPointer(shader.getAttribLocation("aUV1"), 4, FloatType.FLOAT, dataPerVertex, 20);

		attribLocation = shader.getAttribLocation("aUV2");
		vao.enableVertexAttribute(attribLocation);
		vao.vertexAttribPointer(shader.getAttribLocation("aUV2"), 4, FloatType.FLOAT, dataPerVertex, 24);

		// load texture
		SimpleImageLoader loader = new SimpleImageLoader();
		ImageData imageData;
		CoreTexture2D texture;
		try {
			imageData = loader.load("demo.png", GeometryShaderExampleMain.class.getResourceAsStream("/nifty-logo-150x150.png"));
			texture = CoreTexture2D.createCoreTexture(gl, ColorFormat.RGBA, imageData.getWidth(), imageData.getHeight(), imageData.getData(), ResizeFilter.Linear);
			texture.bind();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// we only use a single shader and a single vao so we can activate both here
		// and let them stay active the whole time.
		shader.activate();
		shader.setUniformMatrix("uMvp", 4, MatrixFactory.createOrtho(0, 1024.f, 768.f, 0).toBuffer());
		shader.setUniformf("uWidth", 1024.f);
		shader.setUniformf("uHeight", 768.f);
		shader.setUniformi("uTex", 0);

		System.out.println(Mat4.transform(MatrixFactory.createOrtho(0, 1024.f, 768.f, 0), new Vec4(10.0f, 758.0f, 0.0f, 1.0f), null));
		vao.bind();
	}

	@Override
	public boolean render(final CoreGL gl, final float deltaTime) {
		gl.glClearColor(.1f, .1f, .3f, 0.f);
		gl.glClear(gl.GL_COLOR_BUFFER_BIT());

		// render all the data in the currently active vao using triangle strips
		coreRender.renderPoints(vertexCount);
		return false;
	}
	
	@Override
	@Test
	public void runJogl() {
		CoreGL gl = new JoglCoreGL();
		CoreSetup setup = new CoreSetupJogl(gl);
		setup.initializeLogging(); // optional to get jdk14 to better format the log
		try {
			setup.initialize("Hello JOGL Core GL", 1024, 768);
			setup.renderLoop(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Test
	public void runLwjgl() {
		CoreGL gl = new LwjglCoreGL();
		CoreSetup setup = new CoreSetupLwjgl(gl);
		setup.initializeLogging(); // optional to get jdk14 to better format the log
		try {
			setup.initialize("Hello LWJGL Core GL", 1024, 768);
			setup.renderLoop(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public GeometryShaderExampleMain() throws Exception {

	}

	public static void main(final String[] args) throws Exception {
		CoreExample geomShaderExample = new GeometryShaderExampleMain();
		ExampleMain.runExample(geomShaderExample, args);
	}
}
