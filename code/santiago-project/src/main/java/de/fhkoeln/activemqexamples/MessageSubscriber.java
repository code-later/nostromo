package de.fhkoeln.activemqexamples;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;


public class MessageSubscriber extends MessageFunctions implements Runnable, MessageListener {
  
  private Destination destination;
  private Session session;

  public MessageSubscriber(String topicName) {
    super(topicName);
  }
  
  public MessageSubscriber() {
    super(DEFAULT_TOPIC_NAME);
  }
  
  public void init() throws JMSException {
    // start the connection
    getConnection().setClientID("TestClient");
    getConnection().start();
    
    // open session and register MessageListener object
    this.session = getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
//    this.session.setMessageListener(this);
    this.destination = session.createTopic(getTopicName());
    MessageConsumer consumer = session.createConsumer(destination);
    consumer.setMessageListener(this);
  }
  
  public void shutdown() throws JMSException {
    // close the session and connection
    session.close();
    getConnection().close();
  }

  @Override
  public void run() {
    System.out.println("-- Listening ...");
//    try {
//      TopicSubscriber topicSubscriber = session.createDurableSubscriber(
//          (Topic) destination, "topic_subscription");
//      
//      while (true) {
//        Message message = topicSubscriber.receive(1000);
//        onMessage(message);
//        Thread.sleep(2000);
//      }
//    } catch (JMSException e) {
//      e.printStackTrace();
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
  }

  @Override
  public void onMessage(Message message) {
      try {
        if (message instanceof TextMessage) {
          TextMessage textMessage = (TextMessage) message;
          String receivedText = textMessage.getText();
          System.out.println("Received text: " + receivedText);
        } else {
          System.out.println("Received message: " + message);
        }
      } catch (JMSException e) {
        System.err.println("JMS Exception while receiving message!");
        e.printStackTrace();
      }
  }

}
