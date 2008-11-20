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
package de.fhkoeln.santiago.media;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


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

  /**
   * Test method for {@link de.fhkoeln.santiago.media.MediaData#setUri(java.lang.String)}.
   */
  @Test
  public void testShouldHaveUri() {
    List<Metadata> metadatas = new ArrayList<Metadata>();
    metadatas.add(mock(Metadata.class));

    AbstractMedia media = new MediaData(metadatas);
    media.setUri("http://media.santiago.fh-koeln.de/sample.wav");
    assertEquals("http://media.santiago.fh-koeln.de/sample.wav", media.getUri());
  }
  
  @Test
  public void testShouldBeAbleToCheckOnEquality() {
    AbstractMedia media1 = new MediaData();
    media1.setName("Foo");
    media1.setUri("http://media.santiago.fh-koeln.de/foo.mp4");

    AbstractMedia media2 = new MediaData();
    media2.setName("Foo");
    media2.setUri("http://media.santiago.fh-koeln.de/foo.mp4");
    
    assertEquals(media1, media2);
  }

}
