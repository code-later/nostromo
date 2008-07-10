/*
 * SimpleMessageQueue.java
 *
 * Version 1.0  Jul 10, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.examples.messaging;

import java.util.List;
import java.util.Vector;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Jul 10, 2008
 *
 */
public class SimpleMessageQueue implements MessageQueue {
  
  private List<String[]> queue;
  
  public SimpleMessageQueue() {
    this.queue = new Vector<String[]>();
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.examples.messaging.MessageQueue#pullMessage()
   */
  @Override
  public String[] pullMessage() {
    String[] message = this.queue.get(0);
    this.queue.remove(0);
    return message;
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.examples.messaging.MessageQueue#pushMessage(java.lang.String[])
   */
  @Override
  public void pushMessage(String[] output) {
    this.queue.add(output);
  }

}
