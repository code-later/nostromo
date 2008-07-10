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
import static org.mockito.Mockito.*;


import java.util.Arrays;

import org.junit.Test;

import de.fhkoeln.santiago.examples.components.AbstractComponent;
import de.fhkoeln.santiago.examples.components.PlayMovieFile;
import de.fhkoeln.santiago.examples.messaging.MessageQueue;



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
    MessageQueue queue = mock(MessageQueue.class);
    
    AbstractComponent component = new PlayMovieFile(queue);
    assertNotNull(component);
  }
  
}
