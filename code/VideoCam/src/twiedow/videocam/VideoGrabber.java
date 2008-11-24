

package twiedow.videocam;


import quicktime.QTSession;
import quicktime.qd.QDGraphics;
import quicktime.qd.QDRect;
import quicktime.std.sg.SGVideoChannel;
import quicktime.std.sg.SequenceGrabber;


public class VideoGrabber implements Runnable {


  private static VideoGrabber videoGrabber;
  public static int           CAMERA_WIDTH  = 320;
  public static int           CAMERA_HEIGHT = 240;

  private SequenceGrabber     sg;
  private QDRect              cameraImageSize;
  private QDGraphics          gWorld;
  private int                 pixelDataSize;
  private int                 intsPerRow;

  private boolean             cameraActive  = true;
  private int                 taskingDelay  = 10;
  private Thread              grabThread;


  private VideoGrabber() throws Exception {
    QTSession.open();
    initSequenceGrabber();
  }


  public static VideoGrabber instance() throws Exception {
    if (videoGrabber == null) {
      videoGrabber = new VideoGrabber();
      videoGrabber.startGrab();
    }
    return videoGrabber;
  }


  private void initSequenceGrabber() throws Exception {
    sg = new SequenceGrabber();
    SGVideoChannel vc = new SGVideoChannel(sg);
    cameraImageSize = new QDRect(CAMERA_WIDTH, CAMERA_HEIGHT);

    gWorld = new QDGraphics(cameraImageSize);
    sg.setGWorld(gWorld, null);

    vc.setBounds(cameraImageSize);
    vc.setUsage(quicktime.std.StdQTConstants.seqGrabRecord | quicktime.std.StdQTConstants.seqGrabPreview | quicktime.std.StdQTConstants.seqGrabPlayDuringRecord);
    vc.setFrameRate(0);
    vc.setCompressorType(quicktime.std.StdQTConstants.kComponentVideoCodecType);

    pixelDataSize = gWorld.getPixMap().getPixelData().getSize();
    intsPerRow = gWorld.getPixMap().getPixelData().getRowBytes() / 4;
  }


  private void startGrab() throws Exception {
    sg.setDataOutput(null, quicktime.std.StdQTConstants.seqGrabDontMakeMovie);
    sg.prepare(true, true);
    sg.startRecord();
    grabThread = new Thread(this);
    grabThread.start();
  }


  public void run() {
    try {
      while (cameraActive) {
        Thread.sleep(taskingDelay);
        synchronized (sg) {
          sg.idleMore();
          sg.update(null);
        }
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  public int getSize() {
    return pixelDataSize;
  }


  public int getIntsPerRow() {
    return intsPerRow;
  }


  public void copyPixelDataToArray (int [] array) {
    gWorld.getPixMap().getPixelData().copyToArray(0, array, 0, array.length);
  }


  public SequenceGrabber getSg() {
    return sg;
  }
}
