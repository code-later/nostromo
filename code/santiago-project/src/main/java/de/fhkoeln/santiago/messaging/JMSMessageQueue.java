package de.fhkoeln.santiago.messaging;

/**
 * This class implements the MessageQueue interface and encapsulated
 * messaging through JMS.
 * 
 * TODO: Due to the fact, that we use JMS here, we this is not really
 *       a message queue. It is more a message feature wrapper. This is
 *       only the first step of development. The next steps will involve
 *       heavy refactoring.
 * 
 * @author dbreuer
 * @version 1.0 Sep 12, 2008
 */
public class JMSMessageQueue implements MessageQueue {

  @Override
  public String pullMessage(String topicName) {
    System.err.println(">> Pulling message from topic: " + topicName);
    MessageFeatures receiver = new MessageReceiver(topicName);
//    thread(receiver);
    receiver.run();
    
    return receiver.getMessageContent();
  }

  @Override
  public void pushMessage(String topicName, String message) {
    System.err.println(">> Pushing message to topic: " + topicName);
    MessageFeatures sender = new MessageSender(topicName);
    sender.setMessageContent(message);
    sender.run();
//    thread(sender);
  }
  
  private Thread thread(Runnable runnable) {
    return thread(runnable, false);
  }
  
  private Thread thread(Runnable runnable, boolean daemonize) {
    Thread brokerThread = new Thread(runnable);
    brokerThread.setDaemon(daemonize);
    brokerThread.start();
    return brokerThread;
  }

}
