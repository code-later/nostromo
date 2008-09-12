/*
 * DemoMessenger.java
 *
 * Version 1.0  Sep 11, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.activemqexamples;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Sep 11, 2008
 *
 */
public class DemoMessenger {
  
  static class MessageSender extends 
MessageFunctions {
    
    private String messageContent;
    
    public MessageSender(String topicName, String messageContent) {
      super(topicName);
      setMessageContent(messageContent);
    }
    
    public MessageSender(String messageContent) {
      super(DEFAULT_TOPIC_NAME);
      setMessageContent(messageContent);
    }

    public MessageSender() {
      super(DEFAULT_TOPIC_NAME);
      setMessageContent("_no content given_");
    }

    @Override
    public void run() {
      try {
        // start the connection
        getConnection().start();
        
        // create a session to work with
        Session session = getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        // create the Destination object where the messages should be send to
//        Destination destination = session.createTopic(getTopicName());
        Destination destination = session.createTopic(getTopicName());
        
        // Create a message producer for the Destination. This producer will
        // send our messages to that Destination.
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        
        // create an actual message ...
        TextMessage message = session.createTextMessage(getMessageContent());
        
        // ... and tell the producer to send it
        System.out.println("Sending message ...");
        producer.send(message);
        System.out.println("Message has been sent.");
        
        // clean up
        session.close();
        getConnection().close();
      } catch (JMSException e) {
        System.err.println("JMS Error occured while working with the connection.");
        e.printStackTrace();
      }
    }
    
    public void setMessageContent(String messageContent) {
      this.messageContent = messageContent + "\n" +
                              Thread.currentThread().getName() + "\n" +
                              this.hashCode();
    }
    
    public String getMessageContent() {
      return this.messageContent;
    }
  }
  
  static class MessageReceiver extends MessageFunctions implements Runnable, ExceptionListener {
    
    private Destination destination;
    private Session session;
    private boolean messagesLeft;

    public MessageReceiver(String topicName) {
      super(topicName);
    }

    public MessageReceiver() {
      super(DEFAULT_TOPIC_NAME);
    }
    
    public void init() throws JMSException {
      // start the connection
      getConnection().start();
      getConnection().setExceptionListener(this);
      
      this.session = getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
      this.destination = session.createTopic(getTopicName());
      this.messagesLeft = true;
    }
    
    public void shutdown() throws JMSException {
      session.close();
      getConnection().close();
    }

    @Override
    public void run() {
      try {
        // create a MessageConsumer from the Session
        MessageConsumer consumer = session.createConsumer(destination);

        System.out.println("Receiving messages ...");

        Message message = consumer.receive(2000);

        this.messagesLeft = (boolean) (message == null);

        if (message instanceof TextMessage) {
          TextMessage textMessage = (TextMessage) message;
          String receivedText = textMessage.getText();
          System.out.println("Received text: " + receivedText);
        } else {
          System.out.println("Received message: " + message);
        }

        // clean up
        consumer.close();
        shutdown();
      } catch (JMSException e) {
        System.err
            .println("JMS Error occured while receiving data from the connection.");
        e.printStackTrace();
      }
    }

    public boolean hasMessagesLeft() {
      return messagesLeft;
    }
    
    @Override
    public synchronized void onException(JMSException e) {
      System.err.println("JMSException occured: " + e);
    }
  }
  
  public static void main(String[] args) {
    thread(new MessageSender("CustomTopic", "Different Channels, different customs"));
    thread(new MessageSender("CustomTopic", "What's going on here?"));
    
    MessageSubscriber subscriber = new MessageSubscriber("CustomTopic");
    
    // init the subscriber
    System.out.println("-- Init the subscriber ...");
    try {
      subscriber.init();
    } catch (JMSException e) {
      System.err.println("JMS Error occured during subscriber initialization.");
      e.printStackTrace();
    }
    
    thread(subscriber);
    
//    MessageReceiver receiverDefaultOne = new MessageReceiver();
//    MessageReceiver receiverDefaultTwo = new MessageReceiver();
//    MessageReceiver receiverCustomOne = new MessageReceiver("CustomTopic");
//    MessageReceiver receiverCustomTwo = new MessageReceiver("CustomTopic");
//
//    // init all receivers
//    try {
//      System.out.println("Init receivers ...");
//      receiverCustomOne.init();
//      receiverCustomTwo.init();
//      receiverDefaultOne.init();
//      receiverDefaultTwo.init();
//    } catch (JMSException e) {
//      System.err.println("JMS Error occured while receiver initialization.");
//      e.printStackTrace();
//    } 
//    
//    thread(new MessageSender("Hello from Sender"));
//    thread(new MessageSender("Hello from Sender, again!"));
//    thread(new MessageSender("CustomTopic", "Different Channels, different customs"));
//    thread(new MessageSender("CustomTopic", "What's going on here?"));
//    
//    thread(receiverDefaultOne);
//    thread(receiverDefaultTwo);
//    thread(receiverCustomOne);
//    thread(receiverCustomTwo);
    
//    // shutting down all receivers
//    try {
//      if (!receiverCustomThreadOne.isAlive()) {
//        System.out.println("Shutting down receiverCustom ...");
//        receiverCustomOne.shutdown();
//      }
//      
//      if (!receiverCustomThreadTwo.isAlive()) {
//        System.out.println("Shutting down receiverCustom ...");
//        receiverCustomTwo.shutdown();
//      }
//      
//      if (!receiverDefaultThreadOne.isAlive()) {
//        System.out.println("Shutting down receiverDefault ...");
//        receiverDefaultOne.shutdown();
//      }
//      
//      if (!receiverDefaultThreadTwo.isAlive()) {
//        System.out.println("Shutting down receiverDefault ...");
//        receiverDefaultTwo.shutdown();
//      }
//    } catch (JMSException e) {
//      System.err.println("JMS Error occured while shutting down receivers.");
//      e.printStackTrace();
//    }
  }
  
  private static Thread thread(Runnable runnable) {
    return thread(runnable, false);
  }
  
  private static Thread thread(Runnable runnable, boolean daemonize) {
    Thread brokerThread = new Thread(runnable);
    brokerThread.setDaemon(daemonize);
    brokerThread.start();
    return brokerThread;
  }

}
