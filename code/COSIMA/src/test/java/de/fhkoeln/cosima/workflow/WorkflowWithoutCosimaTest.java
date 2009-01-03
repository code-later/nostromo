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
package de.fhkoeln.cosima.workflow;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import de.fhkoeln.cosima.messaging.MessageQueue;
import de.fhkoeln.cosima.workflow.WorkflowDefinition;
import de.fhkoeln.cosima.workflow.WorkflowWithoutCosima;
import de.fhkoeln.cosima.workflow.YamlWorkflowDefinition;

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
    messageQueue = mock(MessageQueue.class);
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
