/*
 * MediaContainerTest.java
 *
 * Version 1.0  Jun 16, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.media;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;



/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Jun 16, 2008
 *
 */
public class MediaContainerTest {
  
  MediaContainer mediaContainer;
  
  @Before
  public void setUp() {
    mediaContainer = new MediaContainer("http://cosima.test.org/container/42");
  }
  
  @Test
  public void testShouldHaveCollectionOfAbstractMediaObjects() {
    assertEquals(ArrayList.class, mediaContainer.getElements().getClass());
  }

}
