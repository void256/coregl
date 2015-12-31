package de.lessvoid.coregl.examples;

/**
 ** Wavefront .obj mesh loader with vertices, face and normal support. The code is slightly modified copypasta from
 ** the open source project "jglmark" (https://jglmark.dev.java.net/). Original author is Chris "Crash0veride007"
 ** Brown (crash0veride007@gmail.com). Also added support for compressed mesh files (.zip).
 **
 ** This code was modified to be used independently from jogl by void256 in 2012. Some of the convienence like creating
 ** a display list from the model data has been removed in favor of being usable without a specific GL lib.
 **/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;

/**
 * This class provides a way to load Wavefront Object models and provides simple
 * access to the loaded data.
 */
public class WavefrontObjectLoader {
  private int faceCount = 0;

  private final ArrayList<float[]> vertices = new ArrayList<float[]>();
  private final ArrayList<float[]> textureCoordinates = new ArrayList<float[]>();
  private final ArrayList<float[]> normals = new ArrayList<float[]>();

  private final ArrayList<int[]> facesVertexIndizes = new ArrayList<int[]>();
  private final ArrayList<int[]> facesTextureIndizes = new ArrayList<int[]>();
  private final ArrayList<int[]> facesNormalIndizes = new ArrayList<int[]>();

  /**
   * Load the model from a class path resource.
   * 
   * @param modelPath
   * @throws IOException
   */
  public WavefrontObjectLoader(final String modelPath) throws IOException {
    loadOBJModel(resourceInputStream(modelPath));
  }

  /**
   * Load the model from an InputStream. The InputStream will be closed after
   * reading.
   * 
   * @param stream
   *          stream to load model data from
   * @throws IOException
   */
  public WavefrontObjectLoader(final InputStream stream) throws IOException {
    loadOBJModel(stream);
  }

  @Override
  public String toString() {
    final StringBuilder result = new StringBuilder();
    result.append("faceCount:");
    result.append(faceCount);
    result.append(", vertices:");
    result.append(vertices.size());
    result.append(", texture coordinates:");
    result.append(textureCoordinates.size());
    result.append(", normals:");
    result.append(normals.size());
    return result.toString();
  }

  private void loadOBJModel(final InputStream inputStream) throws IOException {
    final BufferedReader br = createBufferedReader(inputStream);
    String line = null;
    while ((line = br.readLine()) != null) {
      if (line.startsWith("#")) {
        // Ignore comments
      } else if (line.equals("")) {
        // Ignore whitespace data
      } else if (line.startsWith("v ")) {
        readVertexLine(line);
      } else if (line.startsWith("vt ")) {
        readVertexTextureLine(line);
      } else if (line.startsWith("vn ")) {
        readVertexNormalLine(line);
      } else if (line.startsWith("f ")) {
        readFaceLine(line);
      }
    }
    br.close();
  }

  private InputStream resourceInputStream(final String modelPath) {
    return (new Object()).getClass().getResourceAsStream(modelPath);
  }

  private BufferedReader createBufferedReader(final InputStream modelData) {
    return new BufferedReader(new InputStreamReader(modelData));
  }

  private void readVertexLine(final String line) {
    vertices.add(processLine(line));
  }

  private void readVertexTextureLine(final String line) {
    textureCoordinates.add(processLine(line));
  }

  private void readVertexNormalLine(final String line) {
    normals.add(processLine(line));
  }

  private void readFaceLine(final String line) {
    final String s[] = splitLine(line);

    // pattern is present if obj has only v and vn in face data
    if (line.contains("//")) {
      for (int loop = 1; loop < s.length; loop++) {
        // insert a zero for missing vt data
        s[loop] = s[loop].replaceAll("//", "/0/");
      }
    }

    if (s.length == 4) {
      processFaceData(s);
      faceCount++;
    }
  }

  private String[] splitLine(final String line) {
    return line.split("\\s+");
  }

  private float[] processLine(final String line) {
    return (toFloatArray(splitLine(line)));
  }

  private float[] toFloatArray(final String sdata[]) {
    final float result[] = new float[sdata.length - 1];
    for (int loop = 0; loop < result.length; loop++) {
      result[loop] = Float.parseFloat(sdata[loop + 1]);
    }
    return result;
  }

  private void processFaceData(final String sdata[]) {
    final int vdata[] = new int[sdata.length - 1];
    final int vtdata[] = new int[sdata.length - 1];
    final int vndata[] = new int[sdata.length - 1];
    for (int loop = 1; loop < sdata.length; loop++) {
      final String s = sdata[loop];
      final String[] temp = s.split("/");
      vdata[loop - 1] = Integer.valueOf(temp[0]); // always add vertex indices
      if (temp.length > 1) { // we have v and vt data
        vtdata[loop - 1] = Integer.valueOf(temp[1]); // add in vt indices
      } else {
        vtdata[loop - 1] = 0; // if no vt data is present fill in zeros
      }
      if (temp.length > 2) { // we have v, vt, and vn data
        vndata[loop - 1] = Integer.valueOf(temp[2]); // add in vn indices
      } else {
        vndata[loop - 1] = 0; // if no vn data is present fill in zeros
      }
    }
    facesVertexIndizes.add(vdata);
    facesTextureIndizes.add(vtdata);
    facesNormalIndizes.add(vndata);
  }

  public int getFaceCount() {
    return faceCount;
  }

  public ArrayList<float[]> getVertices() {
    return vertices;
  }

  public ArrayList<float[]> getVerticesTextureCoordinates() {
    return textureCoordinates;
  }

  public ArrayList<float[]> getNormals() {
    return normals;
  }

  public ArrayList<int[]> getFacesVertexIndizes() {
    return facesVertexIndizes;
  }

  public ArrayList<int[]> getFacesTextureIndizes() {
    return facesTextureIndizes;
  }

  public ArrayList<int[]> getFacesNormalIndizes() {
    return facesNormalIndizes;
  }

  public FloatBuffer asInterleavedArray() {
    final int size = faceCount * 3 * (3 + 2 + 3);
    final FloatBuffer buffer = ByteBuffer.allocateDirect(size << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();

    for (int i = 0; i < faceCount; i++) {
      final int[] vIndex = facesVertexIndizes.get(i);
      final int[] textureIndex = facesTextureIndizes.get(i);
      final int[] normalIndex = facesNormalIndizes.get(i);
      for (int j = 0; j < 3; j++) {
        buffer.put(vertices.get(vIndex[j] - 1));
        buffer.put(textureCoordinates.get(textureIndex[j] - 1));
        buffer.put(normals.get(normalIndex[j] - 1));
      }
    }

    buffer.rewind();
    return buffer;
  }

  public Data asVertexAndIndexBuffer() {
    final List<Float> vertexBuffer = new ArrayList<Float>();
    final List<Integer> indexBuffer = new ArrayList<Integer>();
    int index = 0;
    int reused = 0;
    final Map<String, Integer> vertexCache = new HashMap<String, Integer>();
    final StringBuilder keyBuffer = new StringBuilder();
    for (int i = 0; i < faceCount; i++) {
      final int[] vIndex = facesVertexIndizes.get(i);
      final int[] textureIndex = facesTextureIndizes.get(i);
      final int[] normalIndex = facesNormalIndizes.get(i);
      for (int j = 0; j < 3; j++) {
        final int vertexIdx = vIndex[j] - 1;
        final int textureCoordIdx = textureIndex[j] - 1;
        final int normalIdx = normalIndex[j] - 1;

        keyBuffer.setLength(0);
        keyBuffer.append(vertexIdx);
        keyBuffer.append('/');
        keyBuffer.append(textureCoordIdx);
        keyBuffer.append('/');
        keyBuffer.append(normalIdx);
        final String key = keyBuffer.toString();
        System.out.println(key);
        final Integer existingVertex = vertexCache.get(key);
        if (existingVertex == null) {
          addArray(vertexBuffer, vertices.get(vertexIdx));
          addArray(vertexBuffer, textureCoordinates.get(textureCoordIdx));
          addArray(vertexBuffer, normals.get(normalIdx));
          indexBuffer.add(index);
          vertexCache.put(key, index);
          index++;
        } else {
          indexBuffer.add(existingVertex);
          reused++;
        }
      }
    }
    System.out.println("reused: " + reused);
    return new Data(vertexBuffer, indexBuffer);
  }

  private void addArray(final List<Float> vertexBuffer, final float[] vertex) {
    for (final float f : vertex) {
      vertexBuffer.add(f);
    }
  }

  public static class Data {
    private final FloatBuffer vertexData;
    private final IntBuffer indexData;

    public Data(final List<Float> vertices, final List<Integer> indices) {
      vertexData = BufferUtils.createFloatBuffer(vertices.size() * (3 + 2 + 3));
      vertexData.put(toFloatArray(vertices));
      vertexData.rewind();

      indexData = BufferUtils.createIntBuffer(indices.size());
      indexData.put(toIntArray(indices));
      indexData.rewind();
    }

    public FloatBuffer getVertexData() {
      return vertexData;
    }

    public IntBuffer getIndexData() {
      return indexData;
    }

    private float[] toFloatArray(final List<Float> src) {
      final float[] result = new float[src.size()];
      for (int index = 0; index < src.size(); index++) {
        result[index] = src.get(index);
      }
      return result;
    }

    private int[] toIntArray(final List<Integer> src) {
      final int[] result = new int[src.size()];
      for (int index = 0; index < src.size(); index++) {
        result[index] = src.get(index);
      }
      return result;
    }
  }
}
