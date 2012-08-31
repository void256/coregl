package de.lessvoid.coregl;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;

/**
 * This class takes a List of CoreTextures and uses a CoreRenderToTexture instance to pack all of the CoreTextures
 * into the CoreRenderToTexture.
 *
 * @author void
 */
public class CoreTextureAtlas {


  private class Node {
    public Rectangle rect;
    public Node child[];
    public CoreTexture image;

    public Node(int x, int y, int width, int height) {
      rect = new Rectangle(x, y, width, height);
      child = new Node[2];
      child[0] = null;
      child[1] = null;
      image = null;
    }

    public boolean isLeaf() {
      return child[0] == null && child[1] == null;
    }

    // Algorithm from http://www.blackpawn.com/texts/lightmaps/
    public Node insert(final CoreTexture image, int padding) {
      if (!isLeaf()) {
        Node newNode = child[0].insert(image, padding);
        if (newNode != null) {
          return newNode;
        }
        return child[1].insert(image, padding);
      } else {
        if (this.image != null) {
          return null; // occupied
        }

        if (image.getWidth() > rect.width || image.getHeight() > rect.height) {
          return null; // does not fit
        }

        if (image.getWidth() == rect.width && image.getHeight() == rect.height) {
          this.image = image; // perfect fit
          return this;
        }

        int dw = rect.width - image.getWidth();
        int dh = rect.height - image.getHeight();

        if (dw > dh) {
          child[0] = new Node(rect.x, rect.y, image.getWidth(), rect.height);
          child[1] = new Node(padding + rect.x + image.getWidth(), rect.y, rect.width - image.getWidth() - padding,
              rect.height);
        } else {
          child[0] = new Node(rect.x, rect.y, rect.width, image.getHeight());
          child[1] = new Node(rect.x, padding + rect.y + image.getHeight(), rect.width, rect.height
              - image.getHeight() - padding);
        }
        return child[0].insert(image, padding);
      }
    }
  }
  
  private class Texture {
    private BufferedImage image;
    private Node root;
    private Map<String, Rectangle> rectangleMap;

    public Texture(int width, int height) {
      image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

      root = new Node(0, 0, width, height);
      rectangleMap = new TreeMap<String, Rectangle>();
    }

    public boolean addImage(final CoreTexture image, String name, int padding) {
      Node node = root.insert(image, padding);
      if (node == null) {
        return false;
      }

      rectangleMap.put(name, node.rect);
      //graphics.drawImage(null, null, node.rect.x, node.rect.y);

      return true;
    }

    public void Write(String name, boolean fileNameOnly, boolean unitCoordinates, int width, int height) {
      try
      {
        ImageIO.write(image, "png", new File(name + ".png"));

        BufferedWriter atlas = new BufferedWriter(new FileWriter(name + ".txt"));

        for (Map.Entry<String, Rectangle> e : rectangleMap.entrySet())
        {
          Rectangle r = e.getValue();
          String keyVal = e.getKey();
          if (fileNameOnly)
            keyVal = keyVal.substring(keyVal.lastIndexOf('/') + 1);
          if (unitCoordinates)
          {
            atlas.write(keyVal + " " + r.x / (float) width + " " + r.y / (float) height + " " + r.width / (float) width
                + " " + r.height / (float) height);
          }
          else
            atlas.write(keyVal + " " + r.x + " " + r.y + " " + r.width + " " + r.height);
          atlas.newLine();
        }

        atlas.close();
      } catch (IOException e)
      {

      }
    }
  }

}
