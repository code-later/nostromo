/*
 * CreationMetadataKeysTest.java
 *
 * Version 1.0  Jun 5, 2008
 *
 * Test Class for the CreationMetadataKeys Enum.
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.media;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CreationMetadataKeysTest {

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}
  
  /**
   * Test method for {@link de.fhkoeln.cosima.media.CreationMetadataKeys#getMessage()}.
   */
  @Test
  public void testShouldHaveLocationMessageForLocationEnum() {
    MetadataKeys keyForLocation = CreationMetadataKeys.LOCATION;
    assertEquals("Location", keyForLocation.getMessage());
  }

}
