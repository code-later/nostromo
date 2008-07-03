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

import static org.mockito.Mockito.*;

import org.junit.Test;

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

  
  @Test
  public void testShouldBeCreatedWithWorkflowDefinition() throws Exception {
    WorkflowDefinition workflowDefinition = mock(WorkflowDefinition.class);
    
    verify(workflowDefinition, times(3)).getNextWorkflowElement();

    WorkflowWithoutCosima workflow = new WorkflowWithoutCosima(workflowDefinition);
    
    workflow.run();
  }
}
