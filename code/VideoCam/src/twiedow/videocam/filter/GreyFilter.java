

package twiedow.videocam.filter;

import java.awt.image.BufferedImage;


public class GreyFilter implements ImagePanelFilter {


  public void calculateFilter(BufferedImage img) {
    int width = img.getWidth();
    int height = img.getHeight();
    int rgb, grey, elem;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        rgb = img.getRGB(i, j);
        grey = (((rgb & 0x00ff0000) >> 16) + ((rgb & 0x0000ff00) >> 8) + ((rgb & 0x000000ff))) / 3;
        elem = (grey << 16) | (grey << 8) | grey;
        img.setRGB(i, j, elem);
      }
    }
  }
}
