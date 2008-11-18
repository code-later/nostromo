/*
 * SimpleMediaBrokerTest.java
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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

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
public class SimpleMediaBrokerTest {
  
  @Test
  public void testShouldGetMediaOutOfStorageStrategy() {
    AbstractMedia media = mock(MediaData.class);
    
    MediaBroker mBroker = new SimpleMediaBroker();
    mBroker.store(media);
    assertTrue(mBroker.knownElements() > 0);
  }
  
  @Test
  public void testShouldStoreMediaIntoStorageStrategy() {
    MediaBroker mBroker = new SimpleMediaBroker();
    
    AbstractMedia testMedia = mock(AbstractMedia.class);
    when(testMedia.getName()).thenReturn("stored Media");
    
    mBroker.store(testMedia);
    
    AbstractMedia media = mBroker.retrieve("stored Media");
    assertNotNull(media);
  }
  
  @Test
  public void testShouldKnowHowManyElementsAreAtTheBroker() {
    MediaBroker mBroker = new SimpleMediaBroker();
    mBroker.store(mock(AbstractMedia.class));
    assertTrue(mBroker.knownElements() > 0);
  }

}
