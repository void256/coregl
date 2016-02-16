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
package com.lessvoid.coregl.examples;

import java.util.Map;

import com.lessvoid.coregl.CoreRender;
import com.lessvoid.coregl.CoreShader;
import com.lessvoid.coregl.CoreUBO;
import com.lessvoid.coregl.CoreVAO;
import com.lessvoid.coregl.CoreVAO.FloatType;
import com.lessvoid.coregl.CoreVBO;
import com.lessvoid.coregl.CoreVBO.DataType;
import com.lessvoid.coregl.CoreVBO.UsageType;
import com.lessvoid.coregl.UniformBlockInfo;
import com.lessvoid.coregl.spi.CoreGL;
import com.lessvoid.coregl.spi.CoreSetup.RenderLoopCallback;

public class UniformBufferObjectExampleMain implements RenderLoopCallback {

  private CoreRender coreRender;
  private CoreUBO ubo;
  private CoreShader shader;
  private CoreVAO vao;

  @Override
  public void init(final CoreGL gl) {
    coreRender = CoreRender.createCoreRender(gl);

    shader = CoreShader.createShaderWithVertexAttributes(gl, "vVertex", "vColor");
    shader.vertexShader("ubo/ubo.vs");
    shader.fragmentShader("ubo/ubo.fs");
    shader.link();

    vao = CoreVAO.createCoreVAO(gl);
    vao.bind();

    CoreVBO.createCoreVBO(gl,
                          DataType.FLOAT,
                          UsageType.STATIC_DRAW,
                          new Float[] { -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 1.0f, -0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 0.5f,
                              -0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f, 1.0f, });

    // parameters are: index, size, stride, offset
    // this will use the currently active VBO to store the VBO in the VAO
    vao.vertexAttribPointer(0, 2, FloatType.FLOAT, 6, 0);
    vao.vertexAttribPointer(1, 4, FloatType.FLOAT, 6, 2);
    vao.enableVertexAttribute(0);
    vao.enableVertexAttribute(1);

    final Map<String, UniformBlockInfo> blockInfos = shader.getUniformIndices("TransformBlock.off");

    ubo = CoreUBO.createCoreUBO(gl, 256, blockInfos);
    ubo.setFloatArray("TransformBlock.off", new float[] { -0.4f, 0.2f, 0.3f, 0.4f });
    ubo.send();
    shader.uniformBlockBinding("TransformBlock", 2);
    ubo.bindBufferBase(2);

    // we only use a single shader and a single vao so we can activate both here
    // and let them stay active the whole time.
    vao.bind();
    shader.activate();
  }

  @Override
  public boolean render(final CoreGL gl, final float deltaTime) {
    // We don't have to use coreRender though but it's kinda easier that way
    coreRender.clearColor(.1f, .1f, .3f, 0.f);
    coreRender.clearColorBuffer();
    shader.activate();
    coreRender.renderTriangleStripInstances(4, 4);
    return true;
  }

  @Override
  public boolean endLoop() {
    return false;
  }

  public static void main(final String[] args) throws Exception {
    final RenderLoopCallback uboExample = new UniformBufferObjectExampleMain();
    CoreExampleMain.runExample(uboExample, args);
  }
}
