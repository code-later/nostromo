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

import de.fhkoeln.santiago.components.jmf.JMFMerger;
import de.fhkoeln.santiago.components.jmf.MediaAction;
import de.fhkoeln.santiago.media.AbstractMedia;
import de.fhkoeln.santiago.media.MediaBroker;
import de.fhkoeln.santiago.media.MediaData;
import de.fhkoeln.santiago.media.MemCachedMediaBroker;
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
public class AddMusicToMovieService {
  
  private IODescriptor input;
  
  private MediaBroker broker;
  
  public AddMusicToMovieService() {
    broker = new MemCachedMediaBroker();
  }

  public IODescriptor execute() {
    IODescriptor output = new IODescriptor();
    
    AbstractMedia outputMedia = new MediaData();
    outputMedia.setName("MovieWithMusic");
    outputMedia.setUri("file:///tmp/merged.mov");
    
    // TODO: Path should be abstract enough for all components
    output.add(outputMedia.getName());
    
    AbstractMedia movieFile = broker.retrieve(this.input.getDescriptorElements()[0]); 
    AbstractMedia audioFile = broker.retrieve(this.input.getDescriptorElements()[1]); 
    
    MediaAction action = new JMFMerger(movieFile.getUri(), audioFile.getUri(), outputMedia.getUri());
    action.performAction();
    
    broker.store(outputMedia);
    
    return output;
  }
  
  public void setInput(IODescriptor input) {
    this.input = input;
  }

  public IODescriptor getInput() {
    return input;
  }

}
