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

  public IODescriptor execute() {
    IODescriptor output = new IODescriptor();
    
    // TODO: Path should be abstract enough for all components
    output.add("file:///tmp/merged.mov");
    
    String movieFile = this.input.getDescriptorElements()[0];
    String audioFile = this.input.getDescriptorElements()[1];
    
    MediaAction action = new JMFMerger(movieFile, audioFile, output.first());
    action.performAction();
    
    return output;
  }
  
  public void setInput(IODescriptor input) {
    this.input = input;
  }

  public IODescriptor getInput() {
    return input;
  }

}
