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
package com.lessvoid.coregl;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import com.lessvoid.coregl.spi.CoreGL;

/**
 * This is not really OpenGL core profile specific. It's just a helper class to
 * save the content of the color or stencil buffer to a file. I always wanted to
 * have a class like that ... and now I have one ;D
 * 
 * @author void
 */
public class CoreScreenshot {

  private final CoreGL gl;

  CoreScreenshot(final CoreGL gl) {
    this.gl = gl;
  }

  public static CoreScreenshot createCoreScreenshot(final CoreGL gl) {
    return new CoreScreenshot(gl);
  }

  /**
   * Save the color buffer to a disk file. Since this is really meant for
   * development purpose it will catch any IOExceptions and throws a
   * RuntimeException if it fails for easy drop-in use.
   * 
   * @param filename
   *          target filename to save
   * @param width
   *          the width of the image to save
   * @param height
   *          the height of the image to save
   */
  public void color(final String filename, final int width, final int height) {
    try {
      final BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
      final ByteBuffer pixels = gl.getUtil().createByteBuffer(width * height * 3);
      gl.glPixelStorei(gl.GL_PACK_ALIGNMENT(), 1);
      gl.glReadPixels(0, 0, width, height, gl.GL_RGB(), gl.GL_UNSIGNED_BYTE(), pixels);
      gl.checkGLError("glReadPixels");

      final WritableRaster raster = bi.getRaster();
      final int[] buffer = new int[width * height * 3];
      for (int i = 0; i < width * height * 3; i++) {
        buffer[i] = pixels.get(i);
      }
      raster.setPixels(0, 0, width, height, buffer);

      final File outputfile = new File(filename);
      ImageIO.write(bi, "png", outputfile);
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Save the stencil buffer to a disk file. Since this is really meant for
   * development purpose it will catch any IOExceptions and throws a
   * RuntimeException if it fails for easy drop-in use.
   * 
   * @param filename
   *          target filename to save
   * @param width
   *          the width of the image to save
   * @param height
   *          the height of the image to save
   */
  public void stencil(final String filename, final int width, final int height) {
    try {
      final BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
      final ByteBuffer pixels = gl.getUtil().createByteBuffer(width * height);
      gl.glPixelStorei(gl.GL_PACK_ALIGNMENT(), 1);
      gl.glReadPixels(0, 0, width, height, gl.GL_STENCIL_INDEX(), gl.GL_UNSIGNED_BYTE(), pixels);
      gl.checkGLError("glReadPixels");

      final WritableRaster raster = bi.getRaster();
      final int[] pixel = new int[1];
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          pixel[0] = pixels.get(y * width + x) * 255;
          raster.setPixel(x, y, pixel);
        }
      }
      final File outputfile = new File(filename);
      ImageIO.write(bi, "png", outputfile);
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }
}
