/*
 * AbstractComponent.java
 *
 * Version 1.0  Jul 4, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.examples.components;

import de.fhkoeln.santiago.examples.messaging.MessageQueue;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Jul 4, 2008
 *
 */
public abstract class AbstractComponent {
  
  private final MessageQueue messageQueue;
  private String[] input;
  private String[] output;
  
  public AbstractComponent(MessageQueue messageQueue) {
    this.messageQueue = messageQueue;
  }
  
  /**
   * This method is run by the Workflow System. This method ensures
   * the communication flow.
   */
  public final void run() {
    setInput(this.messageQueue.pullMessage());
    customRun();
    this.messageQueue.pushMessage(getOutput());
  }
  
  /**
   * This method must be implemented by the subclassing components.
   * Here is defined what should be performed in the component.
   */
  protected abstract void customRun();

  /**
   * @return the input
   */
  protected String[] getInput() {
    return this.input;
  }
  
  /**
   * @param input the input to set
   */
  protected void setInput(String[] input) {
    this.input = input;
  }

  /**
   * @return the output
   */
  protected String[] getOutput() {
    return this.output;
  }
  
  /**
   * @param output the output to set
   */
  protected void setOutput(String[] output) {
    this.output = output;
  }
  
}
