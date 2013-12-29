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
package de.lessvoid.coregl.lwjgl;

import static org.lwjgl.opengl.GL11.GL_PACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_RGB;
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
import de.lessvoid.coregl.CoreScreenshot;

public class CoreScreenshotLwjgl implements CoreScreenshot {
  private final CoreCheckGL checkGL;

  CoreScreenshotLwjgl(final CoreCheckGL checkGLParam) {
    this.checkGL = checkGLParam;
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreScreenshot#color(java.lang.String, int, int)
   */
  public void color(final String filename, final int width, final int height) {
    try {
      BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
      ByteBuffer pixels = BufferUtils.createByteBuffer(width*height*3);
      glPixelStorei(GL_PACK_ALIGNMENT, 1);
      glReadPixels(0, 0, width, height, GL_RGB, GL_UNSIGNED_BYTE, pixels);
      checkGL.checkGLError("glReadPixels");

      WritableRaster raster = bi.getRaster();
      int[] buffer = new int[width*height*3];
      for (int i=0; i<width*height*3; i++) {
        buffer[i] = pixels.get(i);
      }
      raster.setPixels(0, 0, width, height, buffer);

      File outputfile = new File(filename);
      ImageIO.write(bi, "png", outputfile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreScreenshot#stencil(java.lang.String, int, int)
   */
  public void stencil(final String filename, final int width, final int height) {
    try {
      BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
      ByteBuffer pixels = BufferUtils.createByteBuffer(width*height);
      glPixelStorei(GL_PACK_ALIGNMENT, 1);
      glReadPixels(0, 0, width, height, GL_STENCIL_INDEX, GL_UNSIGNED_BYTE, pixels);
      checkGL.checkGLError("glReadPixels");

      WritableRaster raster = bi.getRaster();
      int[] pixel = new int[1];
      for (int y=0; y<height; y++) {
        for (int x=0; x<width; x++) {
          pixel[0] = pixels.get(y*width+x)*255;
          raster.setPixel(x, y, pixel);
        }
      }
      File outputfile = new File(filename);
      ImageIO.write(bi, "png", outputfile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
