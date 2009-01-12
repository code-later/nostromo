/*
 * MediaDataTest.java
 *
 * Version 1.0  Nov 18, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.media;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.fhkoeln.cosima.media.MediaComponent;
import de.fhkoeln.cosima.media.Media;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Nov 18, 2008
 *
 */
public class MediaDataTest {

  @Test
  public void testShouldBeAbleToCheckOnEquality() {
    MediaComponent media1 = new Media();
    media1.setName("Foo");
    media1.setNamespace("Test::Media");

    MediaComponent media2 = new Media();
    media2.setName("Foo");
    media2.setNamespace("Test::Media");

    assertEquals(media1, media2);
  }

}
