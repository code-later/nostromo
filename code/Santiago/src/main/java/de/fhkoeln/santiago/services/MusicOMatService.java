/*
 * MusicOMatService.java
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

import de.fhkoeln.cosima.components.AbstractComponent;
import de.fhkoeln.cosima.media.Media;
import de.fhkoeln.cosima.media.MediaComponent;
import de.fhkoeln.cosima.media.mediabroker.MediaBroker;
import de.fhkoeln.cosima.services.IODescriptor;
import de.fhkoeln.cosima.services.registry.ServiceRegistry;
import de.fhkoeln.cosima.util.Logger;
import de.fhkoeln.santiago.components.ffmpeg.FFMpegMerger;
import de.fhkoeln.santiago.components.jmf.MediaAction;

public class MusicOMatService extends AbstractComponent {
  
  private final static String URI         = "http://localhost:8080/axis2/services/MusicOMatService";
  private final static String DESCRIPTION = "Transformer:MusicOMatService";
  
  public MusicOMatService(ServiceRegistry registry) {
    super(registry, URI, DESCRIPTION);
  }

  public IODescriptor execute() {
    return super.execute();
  }
  
  protected IODescriptor _execute() {
    IODescriptor output = new IODescriptor();
    
    MediaComponent outputMedia = new Media();
    outputMedia.setName("SlideshowWithMusic");
    
    Logger.info("--- Slideshow File @ " + this.getInput().getDescriptorElements()[0]);
    Logger.info("--- Audio File @ " + this.getInput().getDescriptorElements()[1]);
    
    MediaComponent movieFile = getBroker().retrieve(this.getInput().getDescriptorElements()[0]);
    MediaComponent audioFile = getBroker().retrieve(this.getInput().getDescriptorElements()[1]);
    
    Logger.info("--- MediaObject for Slideshow File: " + movieFile.getPlayableData());
    Logger.info("--- MediaObject for Audio File: " + audioFile.getPlayableData());
    
    MediaAction action = new FFMpegMerger(movieFile, audioFile, outputMedia);
    action.performAction();
    
    URI mediaUri = getBroker().store(outputMedia);
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
