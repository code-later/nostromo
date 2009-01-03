/*
 * MessageFunctions.java
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
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Sep 11, 2008
 *
 */
public abstract class MessageFunctions implements Runnable {

  /**
   * DEFAULT_TOPIC_NAME documentation comment.
   */
  public static final String DEFAULT_TOPIC_NAME = "foobar";

  private String topicName;

  private Connection connection;

  public MessageFunctions(String topicName) {
    this.topicName = topicName;
    setUpConnection();
  }
  
  public abstract void run();

  public String getTopicName() {
    return topicName;
  }

  protected void setConnection(Connection connection) {
    this.connection = connection;
  }

  protected Connection getConnection() {
    return connection;
  }

  /**
   * Creates the JMS connection through an
   * ActiveMQConnectionFactory. TODO: Use JNDI Lookup to get a
   * connection instead of calling the factory.
   */
  protected void setUpConnection() {
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
    try {
      this.setConnection(connectionFactory.createConnection());
    } catch (JMSException e) {
      System.err.println("JMS Error occured during the creation of the connection.");
      e.printStackTrace();
    }
  }

}