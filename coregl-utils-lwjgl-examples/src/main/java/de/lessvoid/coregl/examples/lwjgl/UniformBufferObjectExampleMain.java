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
package de.lessvoid.coregl.examples.lwjgl;

import java.util.Map;

import de.lessvoid.coregl.CoreFactory;
import de.lessvoid.coregl.CoreRender;
import de.lessvoid.coregl.CoreSetup;
import de.lessvoid.coregl.CoreSetup.RenderLoopCallback;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreVAO;
import de.lessvoid.coregl.lwjgl.CoreFactoryLwjgl;
import de.lessvoid.coregl.lwjgl.CoreShaderLwjgl;
import de.lessvoid.coregl.lwjgl.CoreUBOLwjgl;
import de.lessvoid.coregl.lwjgl.CoreShaderLwjgl.UniformBlockInfo;
import de.lessvoid.math.Vec2;

public class UniformBufferObjectExampleMain implements RenderLoopCallback {
  private final CoreRender coreRender;
  private CoreUBOLwjgl ubo;

  public UniformBufferObjectExampleMain(final CoreFactory factory) {
    coreRender = factory.getCoreRender();

    CoreShader shader = factory.newShaderWithVertexAttributes("vVertex", "vColor");
    shader.vertexShader("ubo/ubo.vs");
    shader.fragmentShader("ubo/ubo.fs");
    shader.link();

    CoreVAO vao = factory.createVAO();
    vao.bind();

    factory.createStaticAndSend(new float[] {
        -0.5f, -0.5f,    1.0f, 0.0f, 0.0f, 1.0f,
        -0.5f,  0.5f,    0.0f, 1.0f, 0.0f, 1.0f,
         0.5f, -0.5f,    0.0f, 0.0f, 1.0f, 1.0f,
         0.5f,  0.5f,    1.0f, 1.0f, 1.0f, 1.0f,
    });

    // parameters are: index, size, stride, offset
    // this will use the currently active VBO to store the VBO in the VAO
    vao.enableVertexAttributef(0, 2, 6, 0);
    vao.enableVertexAttributef(1, 4, 6, 2);

    // we only use a single shader and a single vao so we can activate both here
    // and let them stay active the whole time.
    shader.activate();

    CoreShaderLwjgl bla = (CoreShaderLwjgl) shader;
    Map<String, UniformBlockInfo> blockInfos = bla.getUniformIndices("TransformBlock.off");
    System.out.println(blockInfos);

    ubo = CoreUBOLwjgl.createStatic(256, blockInfos);
    ubo.setFloatArray("TransformBlock.off", new float[]{ 0.1f, 0.2f, 0.3f, 0.4f });
    ubo.send();
    bla.uniformBlockBinding("TransformBlock", 2);
    ubo.bindBufferBase(2);

    vao.bind();
  }

  @Override
  public boolean render(final float deltaTime) {
    // We don't have to use coreRender though but it's kinda easier that way
    coreRender.clearColor(.1f, .1f, .3f, 0.f);
    coreRender.clearColorBuffer();
    coreRender.renderTriangleStripInstances(4, 4);
    return false;
  }

  public static void main(final String[] args) throws Exception {
    CoreFactory factory = new CoreFactoryLwjgl();
    CoreSetup setup = factory.createSetup();
    setup.initializeLogging(); // optional to get jdk14 to better format the log
    setup.initialize("Hello Lwjgl Core GL", 1024, 768);
    setup.renderLoop(new UniformBufferObjectExampleMain(factory));
  }
}
