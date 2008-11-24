

package twiedow.videocam;


import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


public class ImagePanel extends JPanel {


  private BufferedImage image;


  public void paint(Graphics g) {
    super.paint(g);
    if (image != null) {
      g.drawImage(image, 0, 0, this);
    }
  }


  public BufferedImage getImage() {
    return image;
  }


  public void setImage(BufferedImage src) {
    image = null;
    if (src.getWidth() != getWidth() || src.getHeight() != getHeight()) {
      double scaleX = getWidth() / (double) src.getWidth();
      double scaleY = getHeight() / (double) src.getHeight();
      AffineTransform transform = new AffineTransform();
      transform.setToScale(scaleX, scaleY);
      image = new AffineTransformOp(transform, null).filter(src, image);
    }
    else {
      image = src;
    }
    repaint();
  }
}
