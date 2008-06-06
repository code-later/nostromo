package de.fhkoeln.cosima.media;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.MockitoAnnotations.Mock;


public class AbstractMediaTest {
  
  @Mock private Metadata metadataMock;
  @Mock private MediaIO mediaIOMock;

  private AbstractMedia abstractMedia;

  @Before
  public void setUp() throws Exception {
    this.abstractMedia = new AbstractMedia("cosima://test.cosima-framework.com/media/23");
  }
  
  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testShouldHaveListOfMetadataKeys() {
    assertTrue(abstractMedia.getListOfMetadataKeys() != null);
  }

  @Test
  public void testShouldHaveMetadata() {
    stub(metadataMock.getKey()).toReturn(MediaMetadataKeys.VIDEO);
    
    abstractMedia.addMetadata(metadataMock);
    assertTrue(abstractMedia.getListOfMetadataKeys().contains(MediaMetadataKeys.VIDEO));
  }
  
  @Test(expected=NoSuchElementException.class)
  public void testShouldThrowNoSuchElementExceptionIfKeyWasNotFoundInMetadataMap() {
    abstractMedia.getMetadata(MediaMetadataKeys.TEXT);
  }
  
  @Test
  public void testShouldHaveAnMediaIOImplementation() throws Exception {
    AbstractMedia abstractMedia = mock(AbstractMedia.class);
    
//    verify(mediaIOMock).getMediaLocator();
//    assertEquals(new MediaLocator(), abstractMedia.getMediaLocator());
  }
  
  @Test
  public void testShouldReturnMetadataObjectForGivenMetadataKey() {
    stub(metadataMock.getKey()).toReturn(MediaMetadataKeys.VIDEO);
    abstractMedia.addMetadata(metadataMock);
    assertEquals(metadataMock, abstractMedia.getMetadata(MediaMetadataKeys.VIDEO));
  }

  @Test
  public void testShouldSetUriThroughConstructor() {
    AbstractMedia abstractMedia = new AbstractMedia("cosima://test.cosima-framework.com/media/42");
    assertEquals("cosima://test.cosima-framework.com/media/42", abstractMedia.getUri());
  }

}
