/*
 * WorkflowWithoutCosimaTest.java
 *
 * Version 1.0  Jul 3, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.workflow;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import de.fhkoeln.santiago.messaging.JMSMessageQueue;
import de.fhkoeln.santiago.messaging.MessageQueue;
import de.fhkoeln.santiago.messaging.SimpleMessageQueue;

/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Jul 3, 2008
 *
 */
public class WorkflowWithoutCosimaTest {
  
  WorkflowDefinition workflowDefinition;
  MessageQueue messageQueue;
  
  @Before
  public void setUp() throws ClassNotFoundException, IOException {
    workflowDefinition = new YamlWorkflowDefinition("res/workflow_definition/abstract_workflow_definition.yml");
    messageQueue = new JMSMessageQueue();
  }
  
  @Test
  public void testShouldBeCreatedWithWorkflowDefinition() throws Exception {
    WorkflowWithoutCosima workflow = new WorkflowWithoutCosima(workflowDefinition, messageQueue);
    try {
      workflow.run();
    } catch (Exception e) {
      e.printStackTrace();
      fail("No Exception was expected!");
    }
  }
}
