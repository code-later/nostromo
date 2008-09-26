/*
 * JmsMonitor.java
 *
 * Version 1.0  Sep 14, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.util;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import de.fhkoeln.santiago.messaging.MessageFeatures;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Sep 14, 2008
 *
 */
public class JmsMonitor {
  
  static class ReceiverMonitor extends MessageFeatures implements MessageListener {

    /**
     * Constructor documentation comment.
     *
     * @param topicName
     */
    public ReceiverMonitor(String topicName) {
      super(topicName);
    }

    public void run() {
      init();
      executeFeature();
    }
    
    /* (non-Javadoc)
     * @see de.fhkoeln.santiago.messaging.MessageFeatures#executeFeature()
     */
    
    protected void executeFeature() {
      try {
        MessageConsumer consumer = getSession().createConsumer(getDestination());
        consumer.setMessageListener(this);
//        Message message = consumer.receive();
//        onMessage(message);
        System.out.println(">> Listening for messages ...");
      } catch (JMSException e) {
        e.printStackTrace();
      }
    }

    /* (non-Javadoc)
     * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
     */
    
    public void onMessage(Message message) {
      TextMessage textMessage = (TextMessage) message;
      System.out.println("");
      System.out.println("------------------------------------------------------");
      System.out.println("> Message received! (On: " + new Date(System.currentTimeMillis()) + ")");
      System.out.println("");
      System.out.println("Message Details:");
      try {
        System.out.println("  Message ID         : " + textMessage.getJMSMessageID());
        System.out.println("  Message Timestamp  : " + new Date(textMessage.getJMSTimestamp()));
        System.out.println("  Message Type       : " + textMessage.getJMSType());
        System.out.println("  Message Destination: " + textMessage.getJMSDestination());
        System.out.println("  Message            : " + textMessage.getText());
        System.out.println("");
        System.out.println("------------------------------------------------------");
        System.out.println("");
      } catch (JMSException e) {
        e.printStackTrace();
      }
    }
    
  }

  private static final String TOPIC_NAME = "http://santiago-project.fh-koeln.de/components/CreateMovieFromImages/input";
  
  public static void main(String[] args) {
    ReceiverMonitor monitor = new ReceiverMonitor(TOPIC_NAME);
    
    thread(monitor, false);
  }
  
  private static Thread thread(Runnable runnable, boolean daemonize) {
    Thread brokerThread = new Thread(runnable);
    brokerThread.setDaemon(daemonize);
    brokerThread.start();
    return brokerThread;
  }

}
