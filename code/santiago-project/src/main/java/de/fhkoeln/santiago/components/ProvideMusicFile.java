/*
 * ProvideMusicFile.java
 *
 * Version 1.0  Jul 17, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.components;

import de.fhkoeln.santiago.messaging.MessageQueue;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Jul 17, 2008
 *
 */
public class ProvideMusicFile extends AbstractComponent {

  private final String uri = "http://santiago-project.fh-koeln.de/components/ProvideMusicFile";
  
  /**
   * Constructor documentation comment.
   *
   * @param messageQueue
   * @param inputKeys
   */
  public ProvideMusicFile(MessageQueue messageQueue, String[] inputKeys) {
    super(messageQueue, inputKeys);
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.components.AbstractComponent#customRun()
   */
  
  protected void customRun() {
    System.out.println("Setting output to: " + getInput()[0]);
    setOutput(getInput()[0]);
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.components.AbstractComponent#getOutputKey()
   */
  
  public String getOutputKey() {
    return uri + "/output";
  }

}
