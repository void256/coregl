/**
 * Copyright (c) 2017, Jens Hohmuth
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
package com.lessvoid.coregl.ext;

import com.lessvoid.coregl.CoreBufferUtil;
import com.lessvoid.coregl.spi.CoreGL;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by void on 09.04.17.
 */
public class CoreTexture2DExt {
  private final CoreGL gl;

  public CoreTexture2DExt(final CoreGL gl) {
    this.gl = gl;
  }

  /**
   * Save the current bound texture as a png file with the given filename.
   *  @param filename
   * @param width
   * @param height
   */
  public void saveAsPNG(final String filename, final int width, final int height) {
    try {
      final BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
      final ByteBuffer pixels = CoreBufferUtil.createByteBuffer(width * height * 4);
      gl.glGetTexImage(gl.GL_TEXTURE_2D(), 0, gl.GL_RGBA(), gl.GL_UNSIGNED_BYTE(), pixels);
      gl.checkGLError("glGetTexImage");

      final WritableRaster raster = bi.getRaster();
      final int[] buffer = new int[width * height * 4];
      for (int i = 0; i < width * height * 4; i++) {
        buffer[i] = pixels.get(i);
      }
      raster.setPixels(0, 0, width, height, buffer);

      final File outputfile = new File(filename);
      ImageIO.write(bi, "png", outputfile);
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }
}
