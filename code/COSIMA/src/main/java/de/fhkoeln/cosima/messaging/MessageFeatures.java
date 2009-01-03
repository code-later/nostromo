/*
 * MessageFeatures.java
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

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;


/**
 * Documentation comment without implementation details. Use
 * implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 * 
 * @author dbreuer
 * @version 1.0 Sep 14, 2008
 */
public abstract class MessageFeatures implements Runnable {

  /**
   * BROKER_URL The URL to the broker.
   */
  private static final String BROKER_URL = "tcp://localhost:61616";

  public static final String DEFAULT_TOPIC_NAME = "santiago";

  private String topicName;
  private String messageContent;

  private Connection connection;
  private Session session;
  private Destination destination;

  public MessageFeatures(String topicName) {
    this.topicName = topicName;
  }

  public void run() {
    init();
    executeFeature();
    teardown();
  }

  protected abstract void executeFeature();

  protected void init() {
    try {
      establishConnection();

      // start the connection
      connection.start();

      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      destination = session.createTopic(getTopicName());
      System.err.println(">> Message Topic: " + getTopicName());
    } catch (JMSException e) {
      System.err
          .println("JMS Error occured while working with the connection.");
      e.printStackTrace();
    }
  }

  protected void teardown() {
    try {
      session.close();
      closeConnection();
    } catch (JMSException e) {
      System.err
          .println("JMS Error occured while closing session and connection.");
      e.printStackTrace();
    }
  }

  private void establishConnection() {
    ActiveMQConnectionFactory connectionFactory =
        new ActiveMQConnectionFactory(BROKER_URL);
    try {
      this.connection = connectionFactory.createConnection();
//      this.connection.setClientID("client");
    } catch (JMSException e) {
      System.err
          .println("JMS Error occured during the creation of the connection.");
      e.printStackTrace();
    }
  }

  private void closeConnection() {
    try {
      this.connection.close();
    } catch (JMSException e) {
      System.err.println("JMS Error occured while closing the connection.");
      e.printStackTrace();
    }
  }

  protected Destination getDestination() {
    return destination;
  }

  protected Session getSession() throws JMSException {
    return this.session;
  }

  public String getTopicName() {
    return this.topicName == null ? DEFAULT_TOPIC_NAME : this.topicName;
  }

  public void setTopicName(String topicName) {
    this.topicName = topicName;
  }

  public String getMessageContent() {
    return this.messageContent;
  }

  public void setMessageContent(String messageContent) {
    this.messageContent = messageContent;
  }

}
