/*
 * MessageReceiver.java
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

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Sep 14, 2008
 *
 */
public class MessageReceiver extends MessageFeatures implements MessageListener {

  public MessageReceiver(String topicName) {
    super(topicName);
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see de.fhkoeln.cosima.messaging.MessageFeatures#executeFeature()
   */
  
  protected void executeFeature() {

    try {
      // create a MessageConsumer from the Session
      MessageConsumer consumer = getSession().createConsumer(getDestination());
      System.out.println("> Receiving messages from destination '" + getDestination() + "' ...");
//      consumer.setMessageListener(this);
      Message message = consumer.receive();
      onMessage(message);
      consumer.close();
    } catch (JMSException e) {
      System.err.println("JMS Error occured while receiving a message.");
      e.printStackTrace();
    }

  }

  /* (non-Javadoc)
   * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
   */
  
  public void onMessage(Message message) {
    try {
      if (message instanceof TextMessage) {
        TextMessage textMessage = (TextMessage) message;
        setMessageContent(textMessage.getText());
        System.err.println(">> Current Message: " + textMessage.getText());
      } else {
        setMessageContent(message.toString());
      }
    } catch (JMSException e) {
      System.err.println("JMS Error occured while processing a message.");
      e.printStackTrace();
    }
  }
  
}
