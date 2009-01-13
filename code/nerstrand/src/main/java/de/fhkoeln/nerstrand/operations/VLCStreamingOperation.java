/*
 * VLCStreamingOperation.java
 *
 * Version 1.0  Jan 13, 2009
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2009 by dbreuer
 */
package de.fhkoeln.nerstrand.operations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import de.fhkoeln.cosima.media.MediaComponent;

public class VLCStreamingOperation implements MediaOperation {
  
  private final String uriToCaptureDevice;
  private final MediaComponent stream;

  public VLCStreamingOperation(String uriToCaptureDevice, MediaComponent stream) {
    this.uriToCaptureDevice = uriToCaptureDevice;
    this.stream = stream;
    stream.setReferenceToRealData("file:///tmp/stream.sdp");
  }

  public void run() {
    List<String> command = new ArrayList<String>();
    command.add("/Users/dbreuer/vlc_runner");
    command.add(uriToCaptureDevice);
    command.add(stream.getReferenceToRealData().toString());
//    command.add("/Applications/VLC.app/Contents/MacOS/VLC");
//    command.add(uriToCaptureDevice);
//    command.add("--sout='#transcode{vcodec=mp4v,acodec=mpga,vb=800,ab=128,deinterlace}:rtp{dst=192.168.1.2,port=1234,sdp=" + stream.getReferenceToRealData().toString() + "}'");
    
    try {
      ProcessBuilder pBuilder = new ProcessBuilder(command);
      Process vlc = pBuilder.start();

      // Output
      InputStream is = vlc.getErrorStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      String line;

      System.out.printf("\nOutput of running %s is:\n\n", command.get(0)); 

      while ((line = br.readLine()) != null)
        System.out.println(line);
        
      vlc.waitFor();
      System.out.printf("\nCommand %s exited with status: %d\n\n", command.get(0), vlc.exitValue());
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void perform() {
    run();
  }

}
