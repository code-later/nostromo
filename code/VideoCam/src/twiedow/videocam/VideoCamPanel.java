

package twiedow.videocam;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import twiedow.videocam.filter.ImagePanelFilter;


public class VideoCamPanel extends JPanel implements Runnable {


  private int                    width;
  private int                    height;

  private VideoGrabber           videoGrabber;
  private int[]                  pixelData;
  private BufferedImage          image;

  private boolean                cameraActive = true;
  private int                    taskingDelay = 10;
  private Thread                 imageUpdater;

  private List<ImagePanelFilter> filters;


  public VideoCamPanel(int x, int y) throws Exception {
    super();
    width = VideoGrabber.CAMERA_WIDTH;
    height = VideoGrabber.CAMERA_HEIGHT;
    setBounds(x, y, width, height);

    videoGrabber = VideoGrabber.instance();
    image = getNewBufferedImage();
    pixelData = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    filters = new ArrayList<ImagePanelFilter>();
  }


  public BufferedImage getNewBufferedImage() throws Exception {
    int intsPerRow = videoGrabber.getIntsPerRow();
    int size = intsPerRow * height;
    int[] data = new int[size];
    DataBuffer db = new DataBufferInt(data, size);

    ColorModel colorModel = new DirectColorModel(32, 0x0000ff00, 0x00ff0000, 0xff000000);
    int[] masks = { 0x0000ff00, 0x00ff0000, 0xff000000};
    WritableRaster raster = Raster.createPackedRaster(db, width, height, intsPerRow, masks, null);
    return new BufferedImage(colorModel, raster, false, null);
  }


  public void startCamera() throws Exception {
    imageUpdater = new Thread(this);
    imageUpdater.start();
  }


  public void run() {
    try {
      while (cameraActive) {
        Thread.sleep(taskingDelay);
        synchronized (videoGrabber.getSg()) {
          videoGrabber.copyPixelDataToArray(pixelData);
          if (filters.size() > 0) {
            for (ImagePanelFilter filter : filters) {
              filter.calculateFilter(image);
            }
          }
          this.repaint();
        }
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  public void paint(Graphics g) {
    super.paint(g);
    g.drawImage(image, 0, 0, this);
  }


  public synchronized BufferedImage getClonedCopyOfCurrentImage() throws Exception {
    int w = image.getWidth();
    int h = image.getHeight();
    BufferedImage out = getNewBufferedImage();
    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++)
        out.setRGB(x, y, image.getRGB(x, y));
    }

    return out;
  }


  public void addFilter(ImagePanelFilter filter) {
    filters.add(filter);
  }


  public void removeAllFilters() {
    filters.clear();
  }
}