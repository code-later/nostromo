/*
 * WorkflowWithoutCosima.java
 *
 * Version 1.0  Jun 17, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.examples.workflow;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import de.fhkoeln.santiago.examples.components.AbstractComponent;
import de.fhkoeln.santiago.examples.messaging.MessageQueue;
import de.fhkoeln.santiago.examples.messaging.SimpleMessageQueue;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Jun 17, 2008
 *
 */
public class WorkflowWithoutCosima {
  
  private final WorkflowDefinition workflowDefinition;
  private MessageQueue messageQueue;

  /**
   * Constructor documentation comment.
   *  
   * @param workflowDefinition
   */
  public WorkflowWithoutCosima(WorkflowDefinition workflowDefinition, MessageQueue messageQueue) {
    this.workflowDefinition = workflowDefinition;
    this.messageQueue = messageQueue;
  }

  /**
   * Runs the Workflow according to its definition. 
   */
  @SuppressWarnings("unchecked")
  public void run() {
    while (workflowDefinition.hasNextElement()) {
      Object[] element = workflowDefinition.getNextWorkflowElement();
      Class<AbstractComponent> workflowElementClass = (Class<AbstractComponent>) element[1];
      try {
        Constructor abstractComponentConstructor = workflowElementClass.getConstructor(MessageQueue.class);
        AbstractComponent abstractComponent = (AbstractComponent) abstractComponentConstructor.newInstance(this.messageQueue);
        abstractComponent.run();
      } catch (InstantiationException e) {
        System.err.println("Class " + workflowElementClass.getName() + " could not be instantiated.");
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        System.err.println("There was an illigal access with class " + workflowElementClass.getName() + ".");
        e.printStackTrace();
      } catch (SecurityException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }

}
