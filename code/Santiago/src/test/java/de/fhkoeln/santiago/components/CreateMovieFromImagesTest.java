/*
 * CreateMovieFromImagesTest.java
 *
 * Version 1.0  Jul 4, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.components;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import de.fhkoeln.cosima.messaging.MessageQueue;
import de.fhkoeln.cosima.services.CoreService;
import de.fhkoeln.santiago.components.CreateMovieFromImages;



/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Jul 4, 2008
 *
 */
public class CreateMovieFromImagesTest {

  @Test
  public void testShouldImplementWorkflowElementInterface() throws Exception {
    MessageQueue queue = mock(MessageQueue.class);
    
    AbstractComponent component = new CreateMovieFromImages(queue, new String[] {});
    assertNotNull(component);
  }

}
