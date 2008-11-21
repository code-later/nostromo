/*
 * YamlWorkflowDefinitionTest.java
 *
 * Version 1.0  Jul 11, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.workflow;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Jul 11, 2008
 *
 */
public class YamlWorkflowDefinitionTest {
  
  String pathToYamlDefinition;
  
  @Before
  public void setup() {
    pathToYamlDefinition = "res/workflow_definition/abstract_workflow_definition.yml";
  }
  
  @Test
  public void testShouldGenerateWorkflowDefinitionFromYamlFile() throws Exception {
    WorkflowDefinition definition = new YamlWorkflowDefinition(pathToYamlDefinition);
    assertEquals(4, definition.size());
    Set<WorkflowElement> elements;
    WorkflowElement[] elementsAsArray = new WorkflowElement[] {};

    assertTrue(definition.hasNextElements());
    elements = definition.getNextElements();
    assertEquals(2, elements.size());
    
    for (WorkflowElement element : elements) {
      assertTrue(element.getUri().equals("http://localhost:8080/axis2/services/ProvideMusicFileService") ||
          element.getUri().equals("http://localhost:8080/axis2/services/CreateMovieFromImagesService"));
    }

    assertTrue(definition.hasNextElements());
    elements = definition.getNextElements();
    assertEquals(1, elements.size());
    elementsAsArray = elements.toArray(elementsAsArray);
    assertEquals("http://localhost:8080/axis2/services/AddMusicToMovieService", elementsAsArray[0].getUri());

    assertTrue(definition.hasNextElements());
    elements = definition.getNextElements();
    assertEquals(1, elements.size());
    elementsAsArray = elements.toArray(elementsAsArray);
    assertEquals("http://localhost:8080/axis2/services/PlayMovieService", elementsAsArray[0].getUri());
    
    assertFalse(definition.hasNextElements());
  }
  
  @Test
  public void testElementsShouldHaveInputAfterCreation() throws Exception {
    WorkflowDefinition definition = new YamlWorkflowDefinition(pathToYamlDefinition);
    Set<WorkflowElement> elements = definition.getNextElements();
    WorkflowElement element = elements.iterator().next();
    
    assertEquals("http://localhost:8080/axis2/services/ProvideMusicFileService/input", element.getInput().get(0).getUri());
    assertEquals("file:///Users/dbreuer/Documents/Work/_FH/_Master/master_thesis/code/santiago-project/res/L70ETC.mp3", element.getInput().get(0).getData());
//    assertEquals("/Users/dbreuer/Documents/Work/_FH/_Master/master_thesis/code/santiago-project/res/", element.getInput().get(0).getData());
  }

}
