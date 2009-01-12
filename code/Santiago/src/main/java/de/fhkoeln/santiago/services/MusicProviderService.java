/*
 * MusicProviderService.java
 *
 * Version 1.0  Sep 25, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.services;

import java.net.URI;

import de.fhkoeln.cosima.media.MediaComponent;
import de.fhkoeln.cosima.media.Media;
import de.fhkoeln.cosima.media.mediabroker.MediaBroker;
import de.fhkoeln.cosima.media.mediabroker.MemcachedMediaBroker;
import de.fhkoeln.cosima.services.CoreService;
import de.fhkoeln.cosima.services.IODescriptor;
import de.fhkoeln.cosima.services.registry.ServiceRegistry;
import de.fhkoeln.cosima.util.Logger;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Sep 25, 2008
 *
 */
public class MusicProviderService implements CoreService {
  
  private final String URI         = "http://localhost:8080/axis2/services/MusicProviderService";
  private final String DESCRIPTION = "Producer:MusicProvider";
  
  private IODescriptor input;
  private MediaBroker broker;
  private ServiceRegistry registry;
  
  public MusicProviderService(ServiceRegistry registry) {
    Logger.info("Booting Service: " + getClass().getName());
    this.registry = registry;
    this.registry.publish(this);
  }
  
  public IODescriptor execute() {
    IODescriptor output = new IODescriptor();
    
    MediaComponent outputMedia = new Media();
    outputMedia.setName("MetalMusicFile");
    outputMedia.setReferenceToRealData(input.first());
    
    URI mediaUri = getBroker().store(outputMedia);
    output.add(mediaUri.toString());
    
    return output;
  }

  public void setInput(IODescriptor input) {
    this.input = input;
  }

  public IODescriptor getInput() {
    return input;
  }

  public void setBroker(MediaBroker broker) {
    this.broker = broker;
  }

  public MediaBroker getBroker() {
    return broker;
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.cosima.services.CoreService#getDescription()
   */
  public String getDescription() {
    return this.DESCRIPTION;
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.cosima.services.CoreService#getUri()
   */
  public String getUri() {
    return this.URI;
  }

}
