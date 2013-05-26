package de.lessvoid;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL11.GL_STENCIL_INDEX;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glReadPixels;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import de.lessvoid.coregl.CoreCheckGL;

public class Screenshot {
  public void color(final String filename, int width, int height) throws IOException {
    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    ByteBuffer pixels = BufferUtils.createByteBuffer(width*height*3);
    glPixelStorei(GL_PACK_ALIGNMENT, 1);
    glReadPixels(0, 0, width, height, GL_RGB, GL_UNSIGNED_BYTE, pixels);
    CoreCheckGL.checkGLError("glReadPixels");

    WritableRaster raster = bi.getRaster();
    int[] buffer = new int[width*height*3];
    for (int i=0; i<width*height*3; i++) {
      buffer[i] = pixels.get(i);
    }
    raster.setPixels(0, 0, width, height, buffer);

    File outputfile = new File(filename);
    ImageIO.write(bi, "png", outputfile);
  }

  public void stencil(final String filename, int width, int height) throws IOException {
    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
    ByteBuffer pixels = BufferUtils.createByteBuffer(width*height);
    glPixelStorei(GL_PACK_ALIGNMENT, 1);
    glReadPixels(0, 0, width, height, GL_STENCIL_INDEX, GL_UNSIGNED_BYTE, pixels);
    CoreCheckGL.checkGLError("glReadPixels");

    WritableRaster raster = bi.getRaster();
    int[] pixel = new int[1];
    for (int y=0; y<height; y++) {
      for (int x=0; x<width; x++) {
        pixel[0] = pixels.get(y*width+x)*255;
        System.out.print(pixel[0] + ", ");
        raster.setPixel(x, y, pixel);
      }
      System.out.println();
    }
    File outputfile = new File(filename);
    ImageIO.write(bi, "png", outputfile);
  }

  public static void saveStencil(final String filename, int width, int height) {
    try {
      new Screenshot().stencil(filename, width, height);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void saveColor(final String filename, int width, int height) {
    try {
      new Screenshot().color(filename, width, height);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
