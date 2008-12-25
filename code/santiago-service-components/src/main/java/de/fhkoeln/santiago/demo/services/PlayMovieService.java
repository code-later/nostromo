/*
 * PlayMovieService.java
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

import de.fhkoeln.santiago.components.ffmpeg.MPlayerPlayer;
import de.fhkoeln.santiago.components.jmf.MediaAction;
import de.fhkoeln.santiago.demo.util.Logger;
import de.fhkoeln.santiago.media.AbstractMedia;
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
public class PlayMovieService implements CoreService {
  
  private final String URI         = "http://localhost:8080/axis2/services/PlayMovieService";
  private final String DESCRIPTION = "Consumer:VideoPlayer";
  
  private IODescriptor input;
  private MediaBroker broker;
  private ServiceRegistry registry;
  
  public PlayMovieService(ServiceRegistry registry) {
    Logger.info("Booting Service: " + getClass().getName());
    this.registry = registry;
    this.registry.publish(this);
  }
  
  public IODescriptor execute() {
    IODescriptor output = new IODescriptor();
    
    AbstractMedia videoFile = getBroker().retrieve(input.first()); 
    
    MediaAction player = new MPlayerPlayer(videoFile);
    player.performAction();
    
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
