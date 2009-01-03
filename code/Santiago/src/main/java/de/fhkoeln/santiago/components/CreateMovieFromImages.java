/*
 * CreateMovieFromImages.java
 *
 * Version 1.0  Jul 3, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.components;



import java.io.FileNotFoundException;

import de.fhkoeln.cosima.messaging.MessageQueue;
import de.fhkoeln.santiago.components.jmf.JMFImages2Movie;
import de.fhkoeln.santiago.components.jmf.MediaAction;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Jul 3, 2008
 *
 */
public class CreateMovieFromImages extends AbstractComponent {
  
  private final String uri = "http://santiago-project.fh-koeln.de/components/CreateMovieFromImages";
  
  public CreateMovieFromImages(MessageQueue messageQueue, String[] inputKeys) {
    super(messageQueue, inputKeys);
    setOutput("file:///Users/dbreuer/Documents/Work/_FH/_Master/master_thesis/code/santiago-project/target/output.mov");
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.cosima.WorkflowElement#run()
   */
  
  protected void customRun() {
    MediaAction functionWrapper;
    try {
      functionWrapper = new JMFImages2Movie(getInput()[0], getOutput());
      functionWrapper.performAction();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  /* (non-Javadoc)
   * @see de.fhkoeln.cosima.components.AbstractComponent#getOutputKey()
   */
  
  public String getOutputKey() {
    return this.uri + "/output";
  }
}
