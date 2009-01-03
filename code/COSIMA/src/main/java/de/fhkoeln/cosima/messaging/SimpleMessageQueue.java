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
package de.fhkoeln.cosima.messaging;

import java.util.HashMap;
import java.util.Map;


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
  
  private Map<String, String> queue;
  
  public SimpleMessageQueue() {
    this.queue = new HashMap<String, String>();
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.cosima.messaging.MessageQueue#pullMessage()
   */
  
  public String pullMessage(String messageKey) {
    return this.queue.remove(messageKey);
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.cosima.messaging.MessageQueue#pushMessage(java.lang.String[])
   */
  
  public void pushMessage(String messageKey, String message) {
    this.queue.put(messageKey, message);
  }

}
