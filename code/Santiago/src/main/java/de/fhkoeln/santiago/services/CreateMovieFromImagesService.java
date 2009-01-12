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
package de.fhkoeln.santiago.services;

import java.io.FileNotFoundException;
import java.net.URI;

import de.fhkoeln.cosima.components.AbstractComponent;
import de.fhkoeln.cosima.media.MediaComponent;
import de.fhkoeln.cosima.media.Media;
import de.fhkoeln.cosima.media.mediabroker.MediaBroker;
import de.fhkoeln.cosima.services.IODescriptor;
import de.fhkoeln.cosima.services.registry.ServiceRegistry;
import de.fhkoeln.cosima.util.Logger;
import de.fhkoeln.santiago.components.jmf.JMFImages2Movie;
import de.fhkoeln.santiago.components.jmf.MediaAction;

/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Sep 19, 2008
 *
 */
public class CreateMovieFromImagesService extends AbstractComponent {

  // TODO: Should be externalized into config file!
  private static final String URI         = "http://localhost:8080/axis2/services/CreateMovieFromImagesService";
  private static final String DESCRIPTION = "Producer:CreateSlideshow";

  private MediaAction mediaAction;
  
  public CreateMovieFromImagesService(ServiceRegistry registry) {
    super(registry, URI, DESCRIPTION);
  }

  public IODescriptor execute() {
    return super.execute();
  }
  
  protected IODescriptor _execute() {
    Logger.info("Booting Service: " + getClass().getName());

    IODescriptor output;
    
    try {
      String outputFileName = "file:///tmp/output.mov";
      
      output = new IODescriptor();
      
      MediaComponent outputMedia = new Media();
      outputMedia.setName("MovieFromJPEGs");
      
      mediaAction = new JMFImages2Movie(getInput().first(), outputFileName);
      mediaAction.performAction();

      outputMedia.setReferenceToRealData("/tmp/output.mov");
      
      URI mediaUri = getBroker().store(outputMedia);
      output.add(mediaUri.toString());
      
      return output;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    
    return null;
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.cosima.services.CoreService#setInput(de.fhkoeln.cosima.services.IODescriptor)
   */
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

  /* (non-Javadoc)
   * @see de.fhkoeln.cosima.services.CoreService#getDescription()
   */
  public String getDescription() {
    return super.getDescription();
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.cosima.services.CoreService#getUri()
   */
  public String getUri() {
    return super.getUri();
  }

}
