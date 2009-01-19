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
package de.fhkoeln.cosima.workflow;

import java.util.Iterator;

import javax.media.MediaException;

import de.fhkoeln.cosima.messaging.MessageQueue;
import de.fhkoeln.cosima.workflow.WorkflowElement.Input;

/**
 * This a first try {@link WorkflowEngine} implementation. It shouldn't be used any more.
 *
 * @author Dirk Breuer
 * @version 1.0  Jun 17, 2008
 *
 */
@Deprecated
public class WorkflowWithoutCosima {
  
  private final SimpleWorkflowDefinition workflowDefinition;
  private final MessageQueue messageQueue;

  /**
   * Constructor documentation comment.
   *  
   * @param workflowDefinition
   */
  public WorkflowWithoutCosima(SimpleWorkflowDefinition workflowDefinition, MessageQueue messageQueue) {
    this.workflowDefinition = workflowDefinition;
    this.messageQueue = messageQueue;
  }
  
  public void run() throws InterruptedException, MediaException {
    while (workflowDefinition.hasNextElements()) {

      for (Iterator<WorkflowElement> iterator = workflowDefinition
          .getNextElements().iterator(); iterator.hasNext();) {
        WorkflowElement element = iterator.next();
        System.out.println("Current Workflow item: " + element.getUri());

//        Constructor<AbstractComponent> abstractComponentConstructor;
//        AbstractComponent abstractComponent;
//
//        try {
//
//          abstractComponentConstructor = element.getElementClass()
//              .getConstructor(MessageQueue.class, String[].class);
//          abstractComponent = (AbstractComponent) abstractComponentConstructor
//              .newInstance(this.messageQueue, element.getInputKeys());
          
          for (Input input : element.getInput()) {
            if (input.getData() != null)
              this.messageQueue.pushMessage(input.getUri(), input.getData());
          }
          
//          abstractComponent.run();
//        } catch (SecurityException e) {
//          e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//          e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//          e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//          e.printStackTrace();
//        } catch (InstantiationException e) {
//          e.printStackTrace();
//        } catch (IllegalAccessException e) {
//          e.printStackTrace();
//        } catch (InvocationTargetException e) {
//          e.printStackTrace();
//        }
      }

    }
  }
  
}
