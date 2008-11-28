/*
 * ProvideMusicFileService.java
 *
 * Version 1.0  Sep 25, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.demo.services;

import de.fhkoeln.santiago.demo.util.Logger;
import de.fhkoeln.santiago.media.AbstractMedia;
import de.fhkoeln.santiago.media.MediaBroker;
import de.fhkoeln.santiago.media.MediaData;
import de.fhkoeln.santiago.media.MemCachedMediaBroker;
import de.fhkoeln.santiago.services.CoreService;
import de.fhkoeln.santiago.services.IODescriptor;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Sep 25, 2008
 *
 */
public class ProvideMusicFileService implements CoreService {
  
  private IODescriptor input;
  private MediaBroker broker;
  
  public ProvideMusicFileService() {
    Logger.info("Booting Service: " + getClass().getName());
  }
  
  public IODescriptor execute() {
    IODescriptor output = new IODescriptor();
    
    AbstractMedia outputMedia = new MediaData();
    outputMedia.setName("MetalMusicFile");
    outputMedia.setUri(input.first());
    
    getBroker().store(outputMedia);
    
    output.add(outputMedia.getName());
    
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
  
  

}
