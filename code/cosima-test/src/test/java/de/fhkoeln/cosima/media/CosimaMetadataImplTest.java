/*
 * CosimaMetadataImplTest.java
 *
 * Version 1.0  Jun 5, 2008
 *
 * Test Class for the CosimaMetadataImpl Class.
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.media;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CosimaMetadataImplTest {

  private Metadata metadata;
  
  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}
  
  /**
   * Test method for {@link de.fhkoeln.cosima.media.CosimaMetadataImpl#CosimaMetadataImpl(MetadataKeys)}.
   */
  @Test
  public void testShouldSetKeyAlreadyInTheConstructor() {
    Metadata metadata = new CosimaMetadataImpl(MediaMetadataKeys.VIDEO);
    assertEquals(MediaMetadataKeys.VIDEO, metadata.getKey());
  }

  /**
   * Test method for {@link de.fhkoeln.cosima.media.CosimaMetadataImpl#CosimaMetadataImpl(MetadataKeys, String)}.
   */
  @Test
  public void testShouldBeAbleToSetValueInConstructor() throws Exception {
    Metadata metadata = new CosimaMetadataImpl(MediaMetadataKeys.VIDEO, "mpeg");
    assertEquals("mpeg", metadata.getValue());
  }
    
  /**
   * Test method for {@link de.fhkoeln.cosima.media.CosimaMetadataImpl#setValue(java.lang.Object)}.
   */
  @Test
  public void testShouldSetValue() {
    Metadata metadata = new CosimaMetadataImpl(MediaMetadataKeys.VIDEO);
    metadata.setValue("mpeg");
    assertEquals("mpeg", metadata.getValue());
  }

  /**
   * Test method for {@link de.fhkoeln.cosima.media.CosimaMetadataImpl#getKey()}.
   */
  @Test
  public void testShouldGetKey() {
    Metadata metadata = new CosimaMetadataImpl(MediaMetadataKeys.VIDEO);
    assertEquals(MediaMetadataKeys.VIDEO, metadata.getKey());
  }

  /**
   * Test method for {@link de.fhkoeln.cosima.media.CosimaMetadataImpl#getValue()}.
   */
  @Test
  public void testShouldGetValue() {
    Metadata metadata = new CosimaMetadataImpl(MediaMetadataKeys.VIDEO);
    metadata.setValue("mpeg");
    assertEquals("mpeg", metadata.getValue());
  }

}
