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
package com.lessvoid.coregl.examples.util;

import com.lessvoid.coregl.spi.CoreGL;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * An image data provider that uses ImageIO to retrieve image data in a format suitable for creating OpenGL textures.
 * This implementation is used when formats not natively supported by the library are required.
 *
 * {@inheritDoc}
 *
 * @author Kevin Glass
 * @author Aaron Mahan &lt;aaron@forerunnergames.com&gt;
 */
public class ImageLoaderImageIO {
  private static final ColorModel GL_ALPHA_COLOR_MODEL = new ComponentColorModel(ColorSpace.getInstance(ColorSpace
          .CS_sRGB), new int[] { 8, 8, 8, 8 }, true, false, ComponentColorModel.TRANSLUCENT, DataBuffer.TYPE_BYTE);
  private ImageProperties imageProperties;

  public int getImageBitDepth() {
    if (imageProperties == null) {
      throw new IllegalStateException("Image bit depth is not set!");
    }
    return imageProperties.getBitDepth();
  }

  public int getImageWidth() {
    if (imageProperties == null) {
      throw new IllegalStateException("Image width is not set!");
    }
    return imageProperties.getWidth();
  }

  public int getImageHeight() {
    if (imageProperties == null) {
      throw new IllegalStateException("Image height is not set!");
    }
    return imageProperties.getHeight();
  }

  public int getTextureWidth() {
    if (imageProperties == null) {
      throw new IllegalStateException("Texture width is not set!");
    }
    return imageProperties.getWidth();
  }

  public int getTextureHeight() {
    if (imageProperties == null) {
      throw new IllegalStateException("Texture height is not set!");
    }
    return imageProperties.getHeight();
  }

  public ByteBuffer loadAsByteBufferRGBA(final CoreGL gl, final InputStream imageStream) throws IOException {
    return convertToOpenGlFormat(gl, loadImageFromStream(imageStream), false, false);
  }

  // Internal implementations

  private BufferedImage loadImageFromStream(final InputStream imageStream) throws IOException {
    BufferedImage image;
    try {
      image = ImageIO.read(imageStream);
    } catch (IOException e) {
      throw new IOException("Could not load image from stream as a buffered image!", e);
    }
    if (image == null) {
      throw new IOException("Could not load image from stream as a buffered image!");
    }
    return image;
  }

  private ByteBuffer convertToOpenGlFormat(
          final CoreGL gl,
          final BufferedImage originalImage,
          final boolean shouldFlipVertically,
          final boolean shouldUseARGB) {
    long currentContext = gl.getCurrentContext();
    ImageProperties originalImageProperties = new ImageProperties(
            originalImage.getWidth(),
            originalImage.getHeight(),
            shouldFlipVertically);
    imageProperties = originalImageProperties;
    BufferedImage openGlImage = createImageWithProperties(originalImageProperties);
    Graphics2D openGlImageGraphics = (Graphics2D) openGlImage.getGraphics();
    blankImageForMacOsXCompatibility(openGlImageGraphics, originalImageProperties);
    copyImage(originalImage, openGlImageGraphics, originalImageProperties);
    byte[] rawOpenGlImageData = getRawImageData(openGlImage);
    if (shouldUseARGB) {
      convertImageToARGB(rawOpenGlImageData);
    }

    ByteBuffer openGlImageByteBuffer = createByteBuffer(rawOpenGlImageData);

    // We can't dispose of openGlImage any earlier because it would destroy rawOpenGlImageData.
    disposeImage(openGlImageGraphics);
    gl.makeContextCurrent(currentContext);
    return openGlImageByteBuffer;
  }

  private void blankImageForMacOsXCompatibility(
          final Graphics2D imageGraphics,
          final ImageProperties imageProperties) {
    imageGraphics.setColor(new Color(0f, 0f, 0f, 0f));
    imageGraphics.fillRect(0, 0, imageProperties.getWidth(), imageProperties.getHeight());
  }

  private void copyImage(
          final BufferedImage sourceImage,
          final Graphics2D destinationGraphics,
          final ImageProperties sourceImageProperties) {
    if (sourceImageProperties.isFlipped()) {
      destinationGraphics.scale(1, -1);
      destinationGraphics.drawImage(sourceImage, 0, -sourceImageProperties.getHeight(), null);
    } else {
      destinationGraphics.drawImage(sourceImage, 0, 0, null);
    }
  }

  private BufferedImage createImageWithProperties(final ImageProperties imageProperties) {
    return new BufferedImage(
            imageProperties.getColorModel(),
            createRasterWithProperties(imageProperties),
            false,
            null);
  }

  private WritableRaster createRasterWithProperties(final ImageProperties imageProperties) {
    return Raster.createInterleavedRaster(
            DataBuffer.TYPE_BYTE,
            imageProperties.getWidth(),
            imageProperties.getHeight(),
            imageProperties.getColorBands(),
            null);
  }

  private ByteBuffer createByteBuffer(final byte[] data) {
    return (ByteBuffer) ByteBuffer.allocateDirect(data.length)
            .order(ByteOrder.nativeOrder())
            .put(data, 0, data.length)
            .flip();
  }

  private void convertImageToARGB(final byte[] imageData) {
    for (int i = 0; i < imageData.length; i += 4) {
      byte rr = imageData[i];
      byte gg = imageData[i + 1];
      byte bb = imageData[i + 2];
      byte aa = imageData[i + 3];
      imageData[i] = bb;
      imageData[i + 1] = gg;
      imageData[i + 2] = rr;
      imageData[i + 3] = aa;
    }
  }

  private byte[] getRawImageData(final BufferedImage image) {
    return ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
  }

  private void disposeImage(final Graphics2D graphics2D) {
    graphics2D.dispose();
  }

  private static class ImageProperties {
    private final int width;
    private final int height;
    private final boolean isFlippedVertically;

    private ImageProperties(
            final int width,
            final int height,
            final boolean isFlippedVertically) {
      this.width = width;
      this.height = height;
      this.isFlippedVertically = isFlippedVertically;
    }

    private int getWidth() {
      return width;
    }

    private int getHeight() {
      return height;
    }

    private boolean isFlipped() {
      return isFlippedVertically;
    }

    private ColorModel getColorModel() {
      return GL_ALPHA_COLOR_MODEL;
    }

    private int getBitDepth() {
      return 32;
    }

    private int getColorBands() {
      return 4;
    }
  }
}
