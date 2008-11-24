

package twiedow.videocam.filter;


import java.awt.image.BufferedImage;


public class InvertFilter implements ImagePanelFilter {


  public void calculateFilter(BufferedImage img) {
    int width = img.getWidth();
    int height = img.getHeight();
    int rgb;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        rgb = img.getRGB(i, j);
        img.setRGB(i, j, ~rgb);
      }
    }
  }

}
