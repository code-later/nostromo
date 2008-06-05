/*
 * MediaMetadataKeysTest.java
 *
 * Version 1.0  Jun 5, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.media;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Jun 5, 2008
 *
 */
public class MediaMetadataKeysTest {

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {}

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {}

  /**
   * Test method for {@link de.fhkoeln.cosima.media.MediaMetadataKeys#getMessage()}.
   */
  @Test
  public void testShouldHaveVideoMessageForVideoEnum() {
    MetadataKeys keyForVideo = MediaMetadataKeys.VIDEO;
    assertEquals("Video", keyForVideo.getMessage());
  }

  @Test
  public void testShouldHaveAudioMessageForAudioEnum() {
    MetadataKeys keyForAudio = MediaMetadataKeys.AUDIO;
    assertEquals("Audio", keyForAudio.getMessage());
  }

  @Test
  public void testShouldHaveImageMessageForImageEnum() {
    MetadataKeys keyForImage = MediaMetadataKeys.IMAGE;
    assertEquals("Image", keyForImage.getMessage());
  }

  @Test
  public void testShouldHaveTextMessageForTextEnum() {
    MetadataKeys keyForText = MediaMetadataKeys.TEXT;
    assertEquals("Text", keyForText.getMessage());
  }
}
