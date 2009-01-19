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
package de.fhkoeln.cosima.messaging;


/**
 * Defines two simple methods for pushing and pulling semantics from a message queue.
 *
 * @author Dirk Breuer
 * @version 1.0  Jul 10, 2008
 *
 */
public interface MessageQueue {

  public void pushMessage(String messageKey, String message);

  public String pullMessage(String messageKey);

}
