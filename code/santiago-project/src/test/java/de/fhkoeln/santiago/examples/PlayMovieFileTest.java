/*
 * PlayMovieFileTest.java
 *
 * Version 1.0  Jul 4, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.examples;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;



/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Jul 4, 2008
 *
 */
public class PlayMovieFileTest {

  @Test
  public void testShouldImplementWorkflowElementInterface() throws Exception {
    assertTrue(java.util.Arrays.asList(PlayMovieFile.class.getInterfaces())
        .contains(WorkflowElement.class));
  }
  
}
