/*
 * AbstractMediaTest.java
 *
 * Version 1.0  Nov 16, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.media;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.Test;



/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Nov 16, 2008
 *
 */
public class AbstractMediaTest {
  
  @Test
  public void testShouldHaveMetadata() throws Exception {
    MediaComponent media = new Media();
    media.addMetadata(mock(Metadata.class));
    assertNotNull(media.getMetadata());
  }
  
  @Test
  public void testShouldHaveName() {
    MediaComponent media = new Media();
    media.setName("Foo Media");
    assertEquals("Foo Media", media.getName());
  }
  
  @Test
  public void testShouldHaveAbilityToAddNamespaces() {
    MediaComponent media = new Media();
    media.setNamespace("Test::Media::Video");
    assertArrayEquals(new String[] {"Test", "Media", "Video"}, media.getNamespace());
  }
  
  @Test
  public void testShouldCreateUriPartOutOfNameAndNamespace() {
    MediaComponent media = new Media();
    media.setName("bar.mp4");
    media.setNamespace("Test::Media::Video");
    assertEquals("/Test/Media/Video/bar.mp4", media.getUri());
  }

  @Test
  public void testShouldCreateUriPartJustOutOfNameAndWithoutNamespace() {
    MediaComponent media = new Media();
    media.setName("bar.mp4");
    assertEquals("/bar.mp4", media.getUri());
  }
  
  @Test
  public void testShouldUrlEncodeUri() {
    MediaComponent media = new Media();
    media.setName("my media.mp4");
    media.setNamespace("Test::Media Storage::Video");
    assertEquals("/Test/Media+Storage/Video/my+media.mp4", media.getUri());
  }
}
