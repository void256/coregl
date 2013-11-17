# OpenGL core utilities

**Simple utility classes and methods to make life with OpenGL core profile simpler.**

- The classes provided can be used independently from each other. You don't need to learn a complex API.
- The abstractions provided are rather low level. This is by intention. The library is supposed to be as simple and lightweight as possible.
- Adapter jars are provided for LWJGL and JOGL. If you want you can target both Java OpenGL providers or you can use the implementations directly. You decide. 

It's main purpose is to reduce the amount of boilerplate code you would need to write when talking to OpenGL core profile.
**It's not meant to be anything else.**

I've came up with these classes while learning OpenGL core profile. Maybe you can find them useful too.

## Maven

The dependencies you'll need.

### LWJGL

```
  <dependencies>
    <dependency>
      <groupId>de.lessvoid</groupId>
      <artifactId>coregl-utils-lwjgl</artifactId>
      <version>2.0-SNAPSHOT</version>
    </dependency>
  </dependencies>
```

### Jogl (WIP)

```
  <dependencies>
    <dependency>
      <groupId>de.lessvoid</groupId>
      <artifactId>coregl-utils-jogl</artifactId>
      <version>2.0-SNAPSHOT</version>
    </dependency>
  </dependencies>
```

### Maven Repository

```
  <repositories>
    <repository>
      <id>nifty-maven-repo.sourceforge.net</id>
      <url>http://nifty-gui.sourceforge.net/nifty-maven-repo</url>
    </repository>
  </repositories>
```

## Example (LWJGL)

```java
package de.lessvoid.coregl.examples.lwjgl;

import de.lessvoid.coregl.CoreFactory;
import de.lessvoid.coregl.CoreRender;
import de.lessvoid.coregl.CoreSetup;
import de.lessvoid.coregl.CoreSetup.RenderLoopCallback;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreVAO;
import de.lessvoid.coregl.lwjgl.CoreFactoryLwjgl;

/**
 * The SuperSimpleExampleMain just renders a single quad using a triangle strip
 * with a very basic vertex and fragment shader. It demonstrates the use of the
 * core-utils classes.
 *
 * @author void
 */
public class SuperSimpleExampleMain implements RenderLoopCallback {
  private final CoreRender coreRender;

  public SuperSimpleExampleMain(final CoreFactory factory) {
    coreRender = factory.getCoreRender();

    CoreShader shader = factory.newShaderWithVertexAttributes("vVertex", "vColor");
    shader.vertexShader("super-simple/super-simple.vs");
    shader.fragmentShader("super-simple/super-simple.fs");
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
    vao.bind();
  }

  @Override
  public boolean render(final float deltaTime) {
    // We don't have to use coreRender though but it's kinda easier that way
    coreRender.clearColor(.1f, .1f, .3f, 0.f);
    coreRender.clearColorBuffer();
    coreRender.renderTriangleStrip(4);
    return false;
  }

  public static void main(final String[] args) throws Exception {
    CoreFactory factory = new CoreFactoryLwjgl();
    CoreSetup setup = factory.createSetup();
    setup.initializeLogging(); // optional to get jdk14 to better format the log
    setup.initialize("Hello Lwjgl Core GL", 1024, 768);
    setup.renderLoop(new SuperSimpleExampleMain(factory));
  }
}
```