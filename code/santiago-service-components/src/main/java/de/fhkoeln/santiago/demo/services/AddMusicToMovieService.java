/*
 * AddMusicToMovieService.java
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

import java.net.URI;

import de.fhkoeln.santiago.components.ffmpeg.FFMpegMerger;
import de.fhkoeln.santiago.components.jmf.MediaAction;
import de.fhkoeln.santiago.demo.util.Logger;
import de.fhkoeln.santiago.media.AbstractMedia;
import de.fhkoeln.santiago.media.MediaData;
import de.fhkoeln.santiago.media.mediabroker.MediaBroker;
import de.fhkoeln.santiago.services.CoreService;
import de.fhkoeln.santiago.services.IODescriptor;
import de.fhkoeln.santiago.services.registry.ServiceRegistry;

/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Sep 25, 2008
 *
 */
public class AddMusicToMovieService implements CoreService {
  
  private final String URI         = "http://localhost:8080/axis2/services/AddMusicToMovieService";
  private final String DESCRIPTION = "Transformer:MergeMedia";
  
  private IODescriptor input;
  private MediaBroker broker;
  private ServiceRegistry registry;
  
  public AddMusicToMovieService(ServiceRegistry registry) {
    Logger.info("Booting Service: " + getClass().getName());
    this.registry = registry;
    this.registry.publish(this);
  }

  public IODescriptor execute() {
    IODescriptor output = new IODescriptor();
    
    AbstractMedia outputMedia = new MediaData();
    outputMedia.setName("MovieWithMusic");
    
    Logger.info("--- Movie File @ " + this.input.getDescriptorElements()[0]);
    Logger.info("--- Audio File @ " + this.input.getDescriptorElements()[1]);
    
    AbstractMedia movieFile = getBroker().retrieve(this.input.getDescriptorElements()[0]);
    AbstractMedia audioFile = getBroker().retrieve(this.input.getDescriptorElements()[1]);
    
    Logger.info("--- MediaObject for Movie File: " + movieFile.getPlayableData());
    Logger.info("--- MediaObject for Audio File: " + audioFile.getPlayableData());
    
    MediaAction action = new FFMpegMerger(movieFile, audioFile, outputMedia);
    action.performAction();
    
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
   * @see de.fhkoeln.santiago.services.CoreService#getDescription()
   */
  public String getDescription() {
    return this.DESCRIPTION;
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.services.CoreService#getUri()
   */
  public String getUri() {
    return this.URI;
  }

}
