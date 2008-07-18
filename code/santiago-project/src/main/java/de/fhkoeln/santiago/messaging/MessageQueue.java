/*
 * MessageQueue.java
 *
 * Version 1.0  Jul 10, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.messaging;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Jul 10, 2008
 *
 */
public interface MessageQueue {

  /**
   * @param messageKey TODO
   * @param message
   */
  public void pushMessage(String messageKey, String message);

  /**
   * @param messageKey TODO
   * @return
   */
  public String pullMessage(String messageKey);

}
