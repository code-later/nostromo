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
import java.util.ArrayList;
import java.util.List;

import de.fhkoeln.santiago.components.jmf.JMFImages2Movie;
import de.fhkoeln.santiago.components.jmf.MediaAction;
import de.fhkoeln.santiago.services.CoreService;
import de.fhkoeln.santiago.services.IODescriptor;

/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Sep 19, 2008
 *
 */
public class CreateMovieFromImagesService {

  private IODescriptor input;
  
  
//  public CreateMovieFromImagesService() {
//    this.input = new IODescriptor();
//    List inputFiles = new ArrayList<String>();
//    inputFiles.add("/Users/dbreuer/Documents/Work/_FH/_Master/master_thesis/code/santiago-project/res/");
//    this.input.setDescriptorElements((String[]) inputFiles.toArray());
//  }
  
  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.services.CoreService#execute(de.fhkoeln.santiago.services.InputDescriptor)
   */
  public IODescriptor execute() {
    MediaAction mediaAction;
    IODescriptor output;
    
    try {
      output = new IODescriptor();
      output.add("file:///tmp/output.mov");
//      output.setDescriptorElements(new String[] { "file:///tmp/output.mov" });
      mediaAction = new JMFImages2Movie(getInput().first(), output.first());
      mediaAction.performAction();

      return output;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    
    return null;
  }

  public void setInput(IODescriptor input) {
    this.input = input;
  }

  public IODescriptor getInput() {
    return input;
  }

}
