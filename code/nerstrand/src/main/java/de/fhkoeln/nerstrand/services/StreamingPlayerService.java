/*
 * StreamingPlayerService.java
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
import java.util.ArrayList;
import java.util.List;

import de.fhkoeln.cosima.components.AbstractComponent;
import de.fhkoeln.cosima.media.MediaComponent;
import de.fhkoeln.cosima.media.mediabroker.MediaBroker;
import de.fhkoeln.cosima.services.IODescriptor;
import de.fhkoeln.cosima.services.registry.ServiceRegistry;
import de.fhkoeln.santiago.components.ffmpeg.MPlayerPlayer;
import de.fhkoeln.santiago.components.jmf.MediaAction;

public class StreamingPlayerService extends AbstractComponent {

  // TODO: Should be externalized into config file!
  private static final String URI         = "http://localhost:8080/axis2/services/StreamingPlayerService";
  private static final String DESCRIPTION = "Consumer:StreamingPlayerService";

  public StreamingPlayerService(ServiceRegistry registry, String uri, String description) {
    super(registry, URI, DESCRIPTION);
  }
  
  public IODescriptor execute() {
    return super.execute();
  }

  @Override
  protected IODescriptor _execute() {
    IODescriptor output = new IODescriptor();
    
    MediaComponent video = getBroker().retrieve(getInput().first());
    
    List<String> command = new ArrayList<String>();
    command.add("mplayer");
    command.add((String) video.getPlayableData().toString());
    
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
