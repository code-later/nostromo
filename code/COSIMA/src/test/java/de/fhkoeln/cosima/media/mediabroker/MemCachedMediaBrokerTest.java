/*
 * MemCachedMediaBrokerTest.java
 *
 * Version 1.0  Nov 20, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.media.mediabroker;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.fhkoeln.cosima.media.AbstractMedia;
import de.fhkoeln.cosima.media.MediaData;
import de.fhkoeln.cosima.media.mediabroker.MediaBroker;
import de.fhkoeln.cosima.media.mediabroker.MemcachedMediaBroker;
import de.fhkoeln.cosima.media.mediabroker.storage.MediaStore;



/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Nov 20, 2008
 *
 */
public class MemCachedMediaBrokerTest {
  
  MediaBroker broker;
  MediaStore store = mock(MediaStore.class); 

  @Before
  public void setup() {
    broker = spy(new MemcachedMediaBroker());
    broker.setMediaStore(store);
    broker.clearAll();
  }
  
  @After
  public void teardown() {
    broker.clearAll();
  }

  @Test
  public void testShouldStoreMediaObjectsInMemcacheAndReturnUriToMediaObject() {
    AbstractMedia media = new MediaData();
    media.setName("Test Media");
    
    URI uriOfMedia = broker.store(media);
    assertEquals(URI.create("cosima://santiago.fh-koeln.de/media/Test+Media"), uriOfMedia);
    AbstractMedia storedMedia = spy(broker.retrieve("cosima://santiago.fh-koeln.de/media/Test+Media"));
    assertEquals(media, storedMedia);
    assertEquals(store, storedMedia.getStore());
  }
  
  @Test
  public void testShouldKnowHowManyElementsThereAre() {
    AbstractMedia media = new MediaData();
    media.setName("Test Media");
    
    broker.store(media);
    assertEquals(1, broker.knownElements());
  }
  
  @Test
  public void testShouldKnowIfThereAreElementsAtAll() {
    assertTrue(broker.isEmtpy());
  }
  
  @Test
  public void testShouldHaveMediaStoreAssociatedAndUseItToStoreRealMediaData() throws IOException {
    AbstractMedia media = new MediaData();
    media.setName("Test Media");
    
    broker.store(media);
    
    verify(store).write(media);
  }
  
}
