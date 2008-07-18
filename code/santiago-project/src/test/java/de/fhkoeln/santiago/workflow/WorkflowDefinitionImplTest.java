/*
 * WorkflowDefinitionImplTest.java
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

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import de.fhkoeln.santiago.components.AddMusicToMovie;
import de.fhkoeln.santiago.components.PlayMovieFile;
import de.fhkoeln.santiago.workflow.WorkflowDefinition;
import de.fhkoeln.santiago.workflow.WorkflowDefinitionImpl;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Jul 3, 2008
 *
 */
public class WorkflowDefinitionImplTest {
  
  String abstractDefinition;
  
  @Before
  public void setUp() {
    abstractDefinition = "1,de.fhkoeln.santiago.components.PlayMovieFile";
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void testShouldNotAcceptEmptyAbstractDefinition() throws Exception {
    WorkflowDefinition definition = new WorkflowDefinitionImpl("");
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void testShouldVerifyFormatOfAbstractDefinition() throws Exception {
    WorkflowDefinition definition = new WorkflowDefinitionImpl("t,34");
  }
  
  @Test
  public void testShouldAcceptAbstractDefinitionInCorrectFormat() throws Exception {
    WorkflowDefinition definition = new WorkflowDefinitionImpl("1,de.fhkoeln.santiago.components.PlayMovieFile");
    assertNotNull(definition);
  }
  
  @Test(expected=ClassNotFoundException.class)
  public void testShouldThrowExceptionIfClassInDefinitionWasNotFound() throws Exception {
    WorkflowDefinition definition = new WorkflowDefinitionImpl("1,de.fhkoeln.santiago.NonExistingClass");
  }
  
  @Test(expected=NoSuchElementException.class)
  public void testShouldRaiseNoSuchElementExceptionIfAllElementsHaveBeenRead() throws Exception {
    WorkflowDefinition definition = new WorkflowDefinitionImpl(abstractDefinition);
    definition.getNextWorkflowElement();
    definition.getNextWorkflowElement();
  }
  
  @Test
  public void testShouldBeAskIfAnyElementsLeft() throws Exception {
    WorkflowDefinition definition = new WorkflowDefinitionImpl(abstractDefinition);
    assertTrue(definition.hasNextElements());
    definition.getNextWorkflowElement();
    assertFalse(definition.hasNextElements());
  }
  
  @Test
  public void testShouldRewindDefinition() throws Exception {
    WorkflowDefinition definition = new WorkflowDefinitionImpl(abstractDefinition);
    assertNotNull(definition.getNextWorkflowElement());
    definition.rewind();
    assertNotNull(definition.getNextWorkflowElement());
  }

  /**
   * Test method for {@link de.fhkoeln.santiago.workflow.WorkflowDefinitionImpl#getNextWorkflowElement()}.
   */
  @Test
  public void testShouldGetNextWorkflowElement() {
    // First we add some more to the abstract definition.   
    abstractDefinition += ";2,de.fhkoeln.santiago.components.AddMusicToMovie";
    
    WorkflowDefinition definition;
    try {
      definition = new WorkflowDefinitionImpl(abstractDefinition);
      assertEquals(2, definition.size());
      Object[] workflowElement = definition.getNextWorkflowElement();
      assertNotNull(workflowElement);
      assertEquals(1, workflowElement[0]);
      assertEquals(PlayMovieFile.class, workflowElement[1]);
      workflowElement = definition.getNextWorkflowElement();
      assertNotNull(workflowElement);
      assertEquals(2, workflowElement[0]);
      assertEquals(AddMusicToMovie.class, workflowElement[1]);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  @Test
  public void testShouldInitializeWithAbstractRepresentationOfWorkflow() throws Exception {
    WorkflowDefinition definition = new WorkflowDefinitionImpl(abstractDefinition);
    assertNotNull(definition.getNextWorkflowElement());
  }
  
  @Test
  public void testShouldReturnRealWorkflowElementObjectEvenForSimpleWorkflowDefinition() throws Exception {
    WorkflowDefinition definition = new WorkflowDefinitionImpl(abstractDefinition);
    WorkflowElement element = definition.getNextElements().iterator().next();
    assertNotNull(element);
    assertEquals("1", element.getUri());
    assertEquals(PlayMovieFile.class, element.getElementClass());
  }

}
