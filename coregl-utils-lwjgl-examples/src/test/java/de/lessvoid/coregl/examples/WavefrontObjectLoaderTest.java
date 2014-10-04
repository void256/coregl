package de.lessvoid.coregl.examples;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.junit.Test;

import de.lessvoid.coregl.examples.WavefrontObjectLoader;

public class WavefrontObjectLoaderTest {
  private static final float EPSILON = 0.00000001f;

  @Test
  public void testAllIgnoredLines() throws Exception {
    WavefrontObjectLoader loader = new WavefrontObjectLoader(stream("# OBJ written from\n"
        + "o Mesh\n"
        + "mtllib block.mtl\n"
        + "\n"
        + "usemtl Default_095blender_045box_095box_046png\n"));
    assertSize(loader, 0, 0, 0, 0, 0, 0, 0);
  }

  @Test
  public void testVertexLine() throws Exception {
    WavefrontObjectLoader loader = new WavefrontObjectLoader(stream("v 1.51263 2.4e-05 0.28125"));
    assertSize(loader, 1, 0, 0, 0, 0, 0, 0);
    assertFloats(loader.getVertices().get(0), 1.512630f, 0.000024f, 0.281250f);
  }

  @Test
  public void testVertexTextureCoords() throws Exception {
    WavefrontObjectLoader loader = new WavefrontObjectLoader(stream("vt 0.0307216 0.755072"));
    assertSize(loader, 0, 1, 0, 0, 0, 0, 0);
    assertFloats(loader.getVerticesTextureCoordinates().get(0), 0.0307216f, 0.7550720f);
  }

  @Test
  public void testVertexNormal() throws Exception {
    WavefrontObjectLoader loader = new WavefrontObjectLoader(stream("vn -1 0 0"));
    assertSize(loader, 0, 0, 1, 0, 0, 0, 0);
    assertFloats(loader.getNormals().get(0), -1f, 0f, 0f);
  }

  @Test
  public void testSimpleFace() throws Exception {
    WavefrontObjectLoader loader = new WavefrontObjectLoader(stream("f 21 22 23"));
    assertSize(loader, 0, 0, 0, 1, 1, 1, 1);
    assertInts(loader.getFacesVertexIndizes().get(0), 21, 22, 23);
    assertInts(loader.getFacesTextureIndizes().get(0), 0, 0, 0);
    assertInts(loader.getFacesNormalIndizes().get(0), 0, 0, 0);
  }

  @Test
  public void testSimpleFaceWithTextureCooreds() throws Exception {
    WavefrontObjectLoader loader = new WavefrontObjectLoader(stream("f 21/1 22/2 23/3"));
    assertSize(loader, 0, 0, 0, 1, 1, 1, 1);
    assertInts(loader.getFacesVertexIndizes().get(0), 21, 22, 23);
    assertInts(loader.getFacesTextureIndizes().get(0), 1, 2, 3);
    assertInts(loader.getFacesNormalIndizes().get(0), 0, 0, 0);
  }

  @Test
  public void testSimpleFaceWithNormals() throws Exception {
    WavefrontObjectLoader loader = new WavefrontObjectLoader(stream("f 21//1 22//2 23//3"));
    assertSize(loader, 0, 0, 0, 1, 1, 1, 1);
    assertInts(loader.getFacesVertexIndizes().get(0), 21, 22, 23);
    assertInts(loader.getFacesTextureIndizes().get(0), 0, 0, 0);
    assertInts(loader.getFacesNormalIndizes().get(0), 1, 2, 3);
  }

  @Test
  public void testFace() throws Exception {
    WavefrontObjectLoader loader = new WavefrontObjectLoader(stream("f 21/1/1 22/2/1 23/3/1"));
    assertSize(loader, 0, 0, 0, 1, 1, 1, 1);
    assertInts(loader.getFacesVertexIndizes().get(0), 21, 22, 23);
    assertInts(loader.getFacesTextureIndizes().get(0), 1, 2, 3);
    assertInts(loader.getFacesNormalIndizes().get(0), 1, 1, 1);
  }

  private void assertSize(
      final WavefrontObjectLoader loader,
      final int countVertex,
      final int countVertexTextureCoords,
      final int countVertexNormals,
      final int countFaces,
      final int countFaceVertices,
      final int countFaceTextureCoords,
      final int countFaceNormals) {
    assertEquals(countVertex, loader.getVertices().size());
    assertEquals(countVertexTextureCoords, loader.getVerticesTextureCoordinates().size());
    assertEquals(countVertexNormals, loader.getNormals().size());

    assertEquals(countFaces, loader.getFaceCount());
    assertEquals(countFaceVertices, loader.getFacesVertexIndizes().size());
    assertEquals(countFaceTextureCoords, loader.getFacesTextureIndizes().size());
    assertEquals(countFaceNormals, loader.getFacesNormalIndizes().size());
  }

  private InputStream stream(final String data) {
    return new ByteArrayInputStream(data.getBytes(Charset.forName("ISO-8859-1")));
  }

  private void assertFloats(final float[] value, final float ... expected) {
    assertEquals(expected.length, value.length);

    for (int i = 0; i < expected.length; i++) {
      assertEquals(expected[i], value[i], EPSILON);
    }
  }

  private void assertInts(final int[] value, final int ... expected) {
    assertEquals(expected.length, value.length);

    for (int i = 0; i < expected.length; i++) {
      assertEquals(expected[i], value[i]);
    }
  }
}