# OpenGL core utilities

## Important: toolchains.xml required to build

To build this project you'll need a toolchains.xml file in your Maven
directory. This is necessary to support Java 9. Even if you don't plan
to use Java 9 right now, you'll still need a toolchains.xml to build.

For details see the end of this readme.

## What is this?

**Simple utility classes and methods to make life with OpenGL core profile simpler.**

- The classes provided can be used independently from each other. You don't need to learn a complex API.
- The abstractions provided are rather low level. This is by intention. The library is supposed to be as simple and lightweight as possible.
- Adapter jars are provided for LWJGL, LWJGL3 and JOGL. If you want you can target both Java OpenGL providers or you can use the implementations directly. You decide.

It's main purpose is to reduce the amount of boilerplate code you would need to write when talking to OpenGL core profile.
**It's not meant to be anything else.**

I've came up with these classes while learning OpenGL core profile. Maybe you can find them useful too.

## Maven

CoreGL is available in the Maven central.

### LWJGL

```XML
<dependency>
  <groupId>com.lessvoid</groupId>
  <artifactId>coregl-utils-lwjgl</artifactId>
  <version>2.0.0</version>
</dependency>
```

### JOGL

```XML
<dependency>
  <groupId>com.lessvoid</groupId>
  <artifactId>coregl-utils-jogl</artifactId>
  <version>2.0.0</version>
</dependency>
```

## Example

```java
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

import com.lessvoid.coregl.CoreRender;
import com.lessvoid.coregl.CoreShader;
import com.lessvoid.coregl.CoreVAO;
import com.lessvoid.coregl.CoreVAO.FloatType;
import com.lessvoid.coregl.CoreVBO;
import com.lessvoid.coregl.CoreVBO.DataType;
import com.lessvoid.coregl.CoreVBO.UsageType;
import com.lessvoid.coregl.spi.CoreGL;
import com.lessvoid.coregl.spi.CoreSetup.RenderLoopCallback;

/**
 * The SuperSimpleExampleMain just renders a single quad using a triangle strip
 * with a very basic vertex and fragment shader. It demonstrates the use of the
 * core-utils classes.
 *
 * @author void
 */
public class SuperSimpleExampleMain implements RenderLoopCallback {

  private CoreRender coreRender;

  @Override
  public boolean render(final CoreGL gl, final float deltaTime) {
    // We don't have to use coreRender though but it's kinda easier that way
    coreRender.clearColor(.1f, .1f, .3f, 0.f);
    coreRender.clearColorBuffer();
    coreRender.renderTriangleStrip(4);
    return true;
  }

  @Override
  public boolean endLoop() {
    return false;
  }

  @Override
  public void init(final CoreGL gl) {
    coreRender = CoreRender.createCoreRender(gl);

    final CoreShader shader = CoreShader.createShaderWithVertexAttributes(gl, "vVertex", "vColor");
    shader.vertexShader("super-simple/super-simple.vs");
    shader.fragmentShader("super-simple/super-simple.fs");
    shader.link();

    final CoreVAO vao = CoreVAO.createCoreVAO(gl);
    vao.bind();

    CoreVBO.createCoreVBO(gl,
                          DataType.FLOAT,
                          UsageType.STATIC_DRAW,
                          new Float[] { -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 1.0f, -0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 0.5f,
                              -0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f, 1.0f, });

    // parameters are: index, size, stride, offset
    // this will use the currently active VBO to store the VBO in the VAO
    vao.enableVertexAttribute(0);
    vao.vertexAttribPointer(0, 2, FloatType.FLOAT, 6, 0);
    vao.enableVertexAttribute(1);
    vao.vertexAttribPointer(1, 4, FloatType.FLOAT, 6, 2);

    // we only use a single shader and a single vao so we can activate both here
    // and let them stay active the whole time.
    shader.activate();
    vao.bind();
  }

  // pass 'jogl' into program arguments for JOGL backend and 'lwjgl' for LWJGL backend
  public static void main(final String[] args) {
    final RenderLoopCallback superSimpleExample = new SuperSimpleExampleMain();
    CoreExampleMain.runExample(superSimpleExample, args);
  }
}
```

## Maven toolchains.xml support

You'll need to create a toolchains.xml at this location:

- Linux, OS X: ~/.m2
- Windows: C:\Documents and Settings\{your-username}\.m2

### Option 1: Compile with Java 8

The idea here is to have only Java 8 in the toolchains.xml.

#### toolchains.xml:

```xml
<toolchains>
  <!-- JDK toolchains -->
  <toolchain>
    <type>jdk</type>
    <provides>
      <version>1.8</version>
      <vendor>oracle</vendor>
    </provides>
    <configuration>
      <jdkHome>/path/to/jdk1.8.0</jdkHome>
    </configuration>
  </toolchain>
</toolchains>
```

### Option 2: Compile with Java 9 - the resulting Jar can be used with Java 8 and Java 9

We need both JDKs since for the time being we create backward compatible jars which
require both JDKs.

#### toolchains.xml:

```xml
<toolchains>
  <!-- JDK toolchains -->
  <toolchain>
    <type>jdk</type>
    <provides>
      <version>9</version>
      <vendor>oracle</vendor>
    </provides>
    <configuration>
      <jdkHome>/path/to/jdk-9</jdkHome>
    </configuration>
  </toolchain>
  <toolchain>
    <type>jdk</type>
    <provides>
      <version>1.8</version>
      <vendor>oracle</vendor>
    </provides>
    <configuration>
      <jdkHome>/path/to/jdk1.8.0</jdkHome>
    </configuration>
  </toolchain>
</toolchains>
```

### Build

`mvn clean install`
