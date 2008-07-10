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
package de.fhkoeln.santiago.examples;

import org.junit.Before;
import org.junit.Test;

import de.fhkoeln.santiago.examples.messaging.MessageQueue;
import de.fhkoeln.santiago.examples.messaging.SimpleMessageQueue;
import de.fhkoeln.santiago.examples.workflow.WorkflowDefinition;
import de.fhkoeln.santiago.examples.workflow.WorkflowDefinitionImpl;
import de.fhkoeln.santiago.examples.workflow.WorkflowWithoutCosima;

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
  public void setUp() throws ClassNotFoundException {
    String abstractDefinition = "1,de.fhkoeln.santiago.examples.components.CreateMovieFromImages;" +
    		"2,de.fhkoeln.santiago.examples.components.AddMusicToMovie;" +
    		"3,de.fhkoeln.santiago.examples.components.PlayMovieFile";
    workflowDefinition = new WorkflowDefinitionImpl(abstractDefinition);
    
    messageQueue = new SimpleMessageQueue();
    messageQueue.pushMessage(new String[] { "/Users/dbreuer/Documents/Work/_FH/_Master/master_thesis/code/santiago-project/res/" } );
  }
  
  @Test
  public void testShouldBeCreatedWithWorkflowDefinition() throws Exception {
    WorkflowWithoutCosima workflow = new WorkflowWithoutCosima(workflowDefinition, messageQueue);
    workflow.run();
  }
}
