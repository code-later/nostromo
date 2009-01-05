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
package de.fhkoeln.cosima.workflow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import de.fhkoeln.cosima.components.AddMusicToMovie;
import de.fhkoeln.cosima.components.PlayMovieFile;


public class WorkflowDefinitionImplTest {
  
  String abstractDefinition;
  
  @Before
  public void setUp() {
    abstractDefinition = "1,de.fhkoeln.cosima.components.PlayMovieFile";
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void testShouldNotAcceptEmptyAbstractDefinition() throws Exception {
    SimpleWorkflowDefinition definition = new SimpleWorkflowDefinition("");
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void testShouldVerifyFormatOfAbstractDefinition() throws Exception {
    SimpleWorkflowDefinition definition = new SimpleWorkflowDefinition("t,34");
  }
  
  @Test
  public void testShouldAcceptAbstractDefinitionInCorrectFormat() throws Exception {
    SimpleWorkflowDefinition definition = new SimpleWorkflowDefinition("1,de.fhkoeln.cosima.components.PlayMovieFile");
    assertNotNull(definition);
  }
  
  @Test(expected=ClassNotFoundException.class)
  public void testShouldThrowExceptionIfClassInDefinitionWasNotFound() throws Exception {
    SimpleWorkflowDefinition definition = new SimpleWorkflowDefinition("1,de.fhkoeln.cosima.NonExistingClass");
  }
  
  @Test(expected=NoSuchElementException.class)
  public void testShouldRaiseNoSuchElementExceptionIfAllElementsHaveBeenRead() throws Exception {
    SimpleWorkflowDefinition definition = new SimpleWorkflowDefinition(abstractDefinition);
    definition.getNextWorkflowElement();
    definition.getNextWorkflowElement();
  }
  
  @Test
  public void testShouldBeAskIfAnyElementsLeft() throws Exception {
    SimpleWorkflowDefinition definition = new SimpleWorkflowDefinition(abstractDefinition);
    assertTrue(definition.hasNextElements());
    definition.getNextWorkflowElement();
    assertFalse(definition.hasNextElements());
  }
  
  @Test
  public void testShouldRewindDefinition() throws Exception {
    SimpleWorkflowDefinition definition = new SimpleWorkflowDefinition(abstractDefinition);
    assertNotNull(definition.getNextWorkflowElement());
    definition.rewind();
    assertNotNull(definition.getNextWorkflowElement());
  }

  /**
   * Test method for {@link de.fhkoeln.cosima.workflow.SimpleWorkflowDefinition#getNextWorkflowElement()}.
   */
  @Test
  public void testShouldGetNextWorkflowElement() {
    // First we add some more to the abstract definition.   
    abstractDefinition += ";2,de.fhkoeln.cosima.components.AddMusicToMovie";
    
    SimpleWorkflowDefinition definition;
    try {
      definition = new SimpleWorkflowDefinition(abstractDefinition);
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
    SimpleWorkflowDefinition definition = new SimpleWorkflowDefinition(abstractDefinition);
    assertNotNull(definition.getNextWorkflowElement());
  }
  
  @Test
  public void testShouldReturnRealWorkflowElementObjectEvenForSimpleWorkflowDefinition() throws Exception {
    SimpleWorkflowDefinition definition = new SimpleWorkflowDefinition(abstractDefinition);
    WorkflowElement element = definition.getNextElements().iterator().next();
    assertNotNull(element);
    assertEquals("1", element.getUri());
    assertEquals(PlayMovieFile.class, element.getElementClass());
  }

}
