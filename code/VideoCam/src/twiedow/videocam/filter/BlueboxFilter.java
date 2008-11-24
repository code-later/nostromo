

package twiedow.videocam.filter;


import java.awt.image.BufferedImage;

import javax.vecmath.Vector3d;

import twiedow.videocam.ImagePanel;


public class BlueboxFilter implements ImagePanelFilter {


  private ImagePanel bckgrdPanel;
  private ImagePanel blueboxPanel;


  public BlueboxFilter(ImagePanel bckgrdPanel, ImagePanel blueboxPanel) {
    this.bckgrdPanel = bckgrdPanel;
    this.blueboxPanel = blueboxPanel;
  }


  public void calculateFilter(BufferedImage img) {
    BufferedImage bckgrd = bckgrdPanel.getImage();
    BufferedImage bluebox = blueboxPanel.getImage();
    if (bckgrd == null || bluebox == null)
      return;

    int width = img.getWidth();
    int height = img.getHeight();
    int srcPixel, bckgrdPixel;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        srcPixel = img.getRGB(i, j);
        bckgrdPixel = bckgrd.getRGB(i, j);
//        if (Math.abs(cosineQuantity(srcPixel, bckgrdPixel)) > 0.95) {
        double s1 = scalarProduct(srcPixel, bckgrdPixel);
        double s2 = scalarProduct(srcPixel, srcPixel);
        if (Math.abs(s1 - s2) < s1 * 0.5) {
          img.setRGB(i, j, bluebox.getRGB(i, j));
        }
      }
    }
  }


  public double cosineQuantity(int srcPixel, int bckgrdPixel) {
    Vector3d src = new Vector3d((double) ((srcPixel & 0x00ff0000) >> 16), (double) ((srcPixel & 0x0000ff00) >> 8), (double) (srcPixel & 0x000000ff));
    Vector3d bckgrd = new Vector3d((double) ((bckgrdPixel & 0x00ff0000) >> 16), (double) ((bckgrdPixel & 0x0000ff00) >> 8), (double) (bckgrdPixel & 0x000000ff));
    return src.dot(bckgrd) / (src.length() * bckgrd.length());
  }


  public double scalarProduct(int srcPixel, int bckgrdPixel) {
    Vector3d src = new Vector3d((double) ((srcPixel & 0x00ff0000) >> 16), (double) ((srcPixel & 0x0000ff00) >> 8), (double) (srcPixel & 0x000000ff));
    Vector3d bckgrd = new Vector3d((double) ((bckgrdPixel & 0x00ff0000) >> 16), (double) ((bckgrdPixel & 0x0000ff00) >> 8), (double) (bckgrdPixel & 0x000000ff));
    return src.dot(bckgrd);
  }
}
