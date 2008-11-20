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
package de.fhkoeln.santiago.media;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



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

  @Before
  public void setup() {
    broker = new MemCachedMediaBroker();
    broker.clearAll();
  }
  
  @After
  public void teardown() {
    broker.clearAll();
  }

  @Test
  public void testShouldStoreMediaObjectsInMemcache() {
    AbstractMedia media = new MediaData();
    media.setName("TestMedia");
    media.setUri("http://media.santiago.fh-koeln.de/test_media.mp4");
    
    broker.store(media);
    AbstractMedia storedMedia = broker.retrieve(media.getName());
    assertEquals(media, storedMedia);
  }
  
  @Test
  public void testShouldKnowHowManyElementsThereAre() {
    AbstractMedia media = new MediaData();
    media.setName("TestMedia");
    media.setUri("http://media.santiago.fh-koeln.de/test_media.mp4");
    
    broker.store(media);
    assertEquals(1, broker.knownElements());
  }
  
  @Test
  public void testShouldKnowIfThereAreElementsAtAll() {
    assertTrue(broker.isEmtpy());
  }

}
