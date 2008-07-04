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

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import de.fhkoeln.cosima.media.AbstractMedia;

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
  
  @Before
  public void setUp() throws ClassNotFoundException {
    String abstractDefinition = "1,de.fhkoeln.santiago.examples.CreateMovieFromImages;" +
    		"2,de.fhkoeln.santiago.examples.AddMusicToMovie;" +
    		"3,de.fhkoeln.santiago.examples.PlayMovieFile";
    workflowDefinition = new WorkflowDefinitionImpl(abstractDefinition);
  }
  
  @Test
  public void testShouldBeCreatedWithWorkflowDefinition() throws Exception {
    WorkflowWithoutCosima workflow = new WorkflowWithoutCosima(workflowDefinition);
    workflow.run();
  }
}
