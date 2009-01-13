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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

public class StreamingPlayerService extends AbstractComponent {

  // TODO: Should be externalized into config file!
  private static final String URI         = "http://localhost:8080/axis2/services/StreamingPlayerService";
  private static final String DESCRIPTION = "Consumer:StreamingPlayerService";

  public StreamingPlayerService(ServiceRegistry registry) {
    super(registry, URI, DESCRIPTION);
  }
  
  public IODescriptor execute() {
    return super.execute();
  }

  @Override
  protected IODescriptor _execute() {
    IODescriptor output = new IODescriptor();
    
    MediaComponent video = getBroker().retrieve(getInput().first());
    
    // copy playable data to a local working directory
    try {
      String pathToSdpFile = copyPlayableDataToLocalWorkingDir(video);
      startPlayingStream(pathToSdpFile);
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    return output;
  }
  
  private void startPlayingStream(String pathToSdpFile) {
    List<String> command = new ArrayList<String>();
    command.add("open");
    command.add(pathToSdpFile);
    
    try {
      ProcessBuilder pBuilder = new ProcessBuilder(command);
      Process quicktime = pBuilder.start();

      // Output
      InputStream is = quicktime.getErrorStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      String line;

      System.out.printf("Output of running %s is:\n\n", command.get(0)); 

      while ((line = br.readLine()) != null)
        System.out.println(line);
        
      quicktime.waitFor();
      System.out.printf("\nCommand %s exited with status: %d\n\n", command.get(0), quicktime.exitValue());
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private String copyPlayableDataToLocalWorkingDir(MediaComponent video) throws IOException {
    File outputFile = new File("/tmp", video.storageKey() + ".sdp");

    File sourceFile;
    
    try {
      sourceFile = new File(video.getReferenceToRealData());
    } catch (IllegalArgumentException e) {
      sourceFile = new File(video.getReferenceToRealData().toString());
    }
    
    FileInputStream in = null;
    FileOutputStream out = null;
    try {
      // If the sourceFile is null we get a FileNotFoundException. This is
      // useful, if the data instance has no reference data.
      in = new FileInputStream(sourceFile);
      out = new FileOutputStream(outputFile.getAbsolutePath());
      int currentByte;

      while ((currentByte = in.read()) != -1) {
        out.write(currentByte);
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      if (in != null)
        in.close();
      if (out != null)
        out.close();
    }
    
    return outputFile.toString();
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
