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
package de.fhkoeln.santiago.components;

import java.util.ArrayList;
import java.util.List;

import javax.media.MediaException;

import de.fhkoeln.santiago.messaging.MessageQueue;


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
  private List<String> input;
  private String output;
  private final String[] inputKeys;
  
  public AbstractComponent(MessageQueue messageQueue, String[] inputKeys) {
    this.messageQueue = messageQueue;
    this.inputKeys = inputKeys;
    this.input = new ArrayList<String>();
  }
  
  /**
   * This method is run by the Workflow System. This method ensures
   * the communication flow.
   * @throws MediaException 
   */
  public final void run() {
    for (String inputKey : inputKeys)
      addInput(this.messageQueue.pullMessage(inputKey));
    
    try {
      customRun();
    } catch (MediaException e) {
      System.err.println("Media Exception: Something went wrong with this component.");
      e.printStackTrace();
    }
    
    this.messageQueue.pushMessage(getOutputKey(), getOutput());
  }
  
  /**
   * This method must be implemented by the subclassing components.
   * Here is defined what should be performed in the component.
   * @throws MediaException 
   */
  protected abstract void customRun() throws MediaException;
  
  public abstract String getOutputKey();

  /**
   * @return the input
   */
  protected final String[] getInput() {
    return this.input.toArray(new String[] {});
  }
  
  /**
   * @param input the input to set
   */
  protected final void addInput(String input) {
    this.input.add(input);
  }

  /**
   * @return the output
   */
  protected final String getOutput() {
    return this.output;
  }
  
  /**
   * @param output the output to set
   */
  protected final void setOutput(String output) {
    this.output = output;
  }
  
}
