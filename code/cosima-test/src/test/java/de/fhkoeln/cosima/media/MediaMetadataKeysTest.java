/*
 * MediaMetadataKeysTest.java
 *
 * Version 1.0  Jun 5, 2008
 *
 * Test Class for the MediaMetadataKeys Enum.
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.media;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MediaMetadataKeysTest {

  @Before
  public void setUp() throws Exception {}

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

  /**
   * Test method for {@link de.fhkoeln.cosima.media.MediaMetadataKeys#getMessage()}.
   */
  @Test
  public void testShouldHaveAudioMessageForAudioEnum() {
    MetadataKeys keyForAudio = MediaMetadataKeys.AUDIO;
    assertEquals("Audio", keyForAudio.getMessage());
  }

  /**
   * Test method for {@link de.fhkoeln.cosima.media.MediaMetadataKeys#getMessage()}.
   */
  @Test
  public void testShouldHaveImageMessageForImageEnum() {
    MetadataKeys keyForImage = MediaMetadataKeys.IMAGE;
    assertEquals("Image", keyForImage.getMessage());
  }

  /**
   * Test method for {@link de.fhkoeln.cosima.media.MediaMetadataKeys#getMessage()}.
   */
  @Test
  public void testShouldHaveTextMessageForTextEnum() {
    MetadataKeys keyForText = MediaMetadataKeys.TEXT;
    assertEquals("Text", keyForText.getMessage());
  }
}
