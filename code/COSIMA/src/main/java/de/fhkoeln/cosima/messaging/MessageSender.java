/*
 * MessageSender.java
 *
 * Version 1.0  Sep 14, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.messaging;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

/**
 * Implements the functionality of sending a message based on the {@link MessageFeatures} class.
 *
 * @author Dirk Breuer
 * @version 1.0  Sep 14, 2008
 *
 */
public class MessageSender extends MessageFeatures {

  public MessageSender(String topicName) {
    super(topicName);
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.cosima.messaging.MessageFeatures#executeFeature()
   */
  protected void executeFeature() {
    // Create a message producer for the Destination. This producer will
    // send our messages to that Destination.
    MessageProducer producer;

    try {
      producer = getSession().createProducer(getDestination());
      producer.setDeliveryMode(DeliveryMode.PERSISTENT);
      // create an actual message ...
      TextMessage message = getSession().createTextMessage(getMessageContent());

      // ... and tell the producer to send it
      System.out.println("> Sending message ...");
      producer.send(message);
      System.out.println("> Message has been sent.");
      producer.close();
    } catch (JMSException e) {
      System.err.println("JMS Error occured while sending a message.");
      e.printStackTrace();
    }
  }

}
