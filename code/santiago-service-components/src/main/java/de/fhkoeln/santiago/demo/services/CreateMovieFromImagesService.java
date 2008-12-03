/*
 * CreateMovieFromImagesService.java
 *
 * Version 1.0  Sep 19, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.demo.services;

import java.io.FileNotFoundException;

import de.fhkoeln.santiago.components.jmf.JMFImages2Movie;
import de.fhkoeln.santiago.components.jmf.MediaAction;
import de.fhkoeln.santiago.demo.util.Logger;
import de.fhkoeln.santiago.media.AbstractMedia;
import de.fhkoeln.santiago.media.MediaBroker;
import de.fhkoeln.santiago.media.MediaData;
import de.fhkoeln.santiago.services.CoreService;
import de.fhkoeln.santiago.services.IODescriptor;
import de.fhkoeln.santiago.services.registry.ServiceRegistry;

/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Sep 19, 2008
 *
 */
public class CreateMovieFromImagesService implements CoreService {

  private final String URI         = "http://localhost:8080/axis2/services/CreateMovieFromImagesService";
  private final String DESCRIPTION = "Producer:CreateSlideshow";

  private MediaBroker broker;
  private IODescriptor input;
  private MediaAction mediaAction;
  private ServiceRegistry registry;
  
  public CreateMovieFromImagesService(ServiceRegistry registry) {
    Logger.info("Booting Service: " + getClass().getName());
    this.registry = registry;
    this.registry.publish(this);
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.services.CoreService#execute(de.fhkoeln.santiago.services.InputDescriptor)
   */
  public IODescriptor execute() {
    Logger.info("Booting Service: " + getClass().getName());

    IODescriptor output;
    
    try {
      output = new IODescriptor();
      
      AbstractMedia outputMedia = new MediaData();
      outputMedia.setName("MovieFromJPEGs");
      outputMedia.setUri("file:///tmp/output.mov");
      
      output.add(outputMedia.getName());
      
      mediaAction = new JMFImages2Movie(getInput().first(), outputMedia.getUri());
      mediaAction.performAction();

      getBroker().store(outputMedia);
      
      return output;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    
    return null;
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.services.CoreService#setInput(de.fhkoeln.santiago.services.IODescriptor)
   */
  public void setInput(IODescriptor descriptor) {
    this.input = descriptor;
  }
  
  public IODescriptor getInput() {
    return this.input;
  }

  public MediaBroker getBroker() {
    return this.broker;
  }

  public void setBroker(MediaBroker broker) {
    this.broker = broker;
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
