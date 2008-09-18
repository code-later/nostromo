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



import de.fhkoeln.santiago.components.jmf.JMFImages2Movie;
import de.fhkoeln.santiago.messaging.MessageQueue;


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
   * @see de.fhkoeln.santiago.WorkflowElement#run()
   */
  @Override
  protected void customRun() {
    try {
      JMFImages2Movie functionWrapper = new JMFImages2Movie(getInput()[0], getOutput());
      functionWrapper.performAction();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.components.AbstractComponent#getOutputKey()
   */
  @Override
  public String getOutputKey() {
    return this.uri + "/output";
  }
}
