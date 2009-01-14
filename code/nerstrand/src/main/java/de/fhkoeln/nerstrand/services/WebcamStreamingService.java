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

import java.io.File;
import java.net.URI;

import de.fhkoeln.cosima.components.AbstractComponent;
import de.fhkoeln.cosima.media.Media;
import de.fhkoeln.cosima.media.MediaComponent;
import de.fhkoeln.cosima.media.mediabroker.MediaBroker;
import de.fhkoeln.cosima.services.IODescriptor;
import de.fhkoeln.cosima.services.registry.ServiceRegistry;
import de.fhkoeln.nerstrand.operations.MediaOperation;
import de.fhkoeln.nerstrand.operations.VLCStreamingOperation;

public class WebcamStreamingService extends AbstractComponent {

  private static final String URI         = "http://localhost:8080/axis2/services/WebcamStreamingService";
  private static final String DESCRIPTION = "Producer:WebcamStreamingService";

  public WebcamStreamingService(ServiceRegistry registry) {
    super(registry, URI, DESCRIPTION);
  }

  public IODescriptor execute() {
    return super.execute();
  }

  @Override
  protected IODescriptor _execute() {
    
    IODescriptor output = new IODescriptor();

    MediaComponent stream = new Media();
    stream.setName("Webcam Stream");
    stream.setNamespace("Nerstrand::StreamingService");
    
    MediaOperation streamingOp = new VLCStreamingOperation(getInput().first(), stream);
    thread(streamingOp, false);
    
    boolean wait = true;
    while(wait)
      wait = new File(stream.getReferenceToRealData().toString()).exists() ? true : false;
    
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
  
  private static Thread thread(Runnable runnable, boolean daemonize) {
    Thread brokerThread = new Thread(runnable);
    brokerThread.setDaemon(daemonize);
    brokerThread.start();
    return brokerThread;
  }

}
