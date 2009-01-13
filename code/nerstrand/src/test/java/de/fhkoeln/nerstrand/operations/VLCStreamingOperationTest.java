package de.fhkoeln.nerstrand.operations;

import static org.junit.Assert.*;

import org.junit.Test;

import de.fhkoeln.cosima.media.Media;
import de.fhkoeln.cosima.media.MediaComponent;


public class VLCStreamingOperationTest {

  @Test
  public void testPerform() {
    MediaComponent stream = new Media();
    MediaOperation streamingOp = new VLCStreamingOperation("qtcapture://", stream);
    
//    streamingOp.perform();
    thread(streamingOp, false);
    assertEquals("file:///tmp/stream.sdp", stream.getReferenceToRealData().toString());
  }
  
  private static Thread thread(Runnable runnable, boolean daemonize) {
    Thread brokerThread = new Thread(runnable);
    brokerThread.setDaemon(daemonize);
    brokerThread.start();
    return brokerThread;
  }

}
