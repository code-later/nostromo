/*
 * WebcamStreamingService.java
 *
 * Version 1.0  Jan 12, 2009
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2009 by dbreuer
 */
package de.fhkoeln.nerstrand.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import de.fhkoeln.cosima.components.AbstractComponent;
import de.fhkoeln.cosima.media.Media;
import de.fhkoeln.cosima.media.MediaComponent;
import de.fhkoeln.cosima.media.mediabroker.MediaBroker;
import de.fhkoeln.cosima.services.IODescriptor;
import de.fhkoeln.cosima.services.registry.ServiceRegistry;

public class WebcamStreamingService extends AbstractComponent {

  private static final String URI         = "http://localhost:8080/axis2/services/WebcamStreamingService";
  private static final String DESCRIPTION = "Producer:WebcamStreamingService";

  public WebcamStreamingService(ServiceRegistry registry, String uri, String description) {
    super(registry, URI, DESCRIPTION);
  }

  public IODescriptor execute() {
    return super.execute();
  }

  @Override
  protected IODescriptor _execute() {
    
    IODescriptor output = new IODescriptor();
    
    // Building the VLC command
    String targetHost = "192.168.178.195";
    String targetPort = "1234";

    List<String> command = new ArrayList<String>();
    command.add("vlc");
    command.add("qtcapture://");
    command.add("--sout='#transcode{vcodec=mp4v,acodec=mpga,vb=800,ab=128,deinterlace}:standard{access=http,dst=" + targetHost + ",port=" + targetPort + "}'");

    MediaComponent stream = new Media();
    stream.setName("Webcam Stream");
    stream.setNamespace("Nerstrand::StreamingService");
    
    try {
      ProcessBuilder pBuilder = new ProcessBuilder(command);
      Process mplayer = pBuilder.start();

      // Output
      InputStream is = mplayer.getErrorStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      String line;

      System.out.printf("Output of running %s is:\n\n", command.get(0)); 

      while ((line = br.readLine()) != null)
        System.out.println(line);
        
      mplayer.waitFor();
      System.out.printf("\nCommand %s exited with status: %d\n\n", command.get(0), mplayer.exitValue());
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    URI mediaUri = getBroker().store(stream);
    output.add(mediaUri.toString());
    
    return output;
  }

  public void setInput(IODescriptor descriptor) {
    super.setInput(descriptor);
  }
  
  public IODescriptor getInput() {
    return super.getInput();
  }

  public MediaBroker getBroker() {
    return super.getBroker();
  }

  public void setBroker(MediaBroker broker) {
    super.setBroker(broker);
  }

  public String getDescription() {
    return super.getDescription();
  }

  public String getUri() {
    return super.getUri();
  }

}
