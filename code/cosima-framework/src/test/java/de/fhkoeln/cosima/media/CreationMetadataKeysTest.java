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
  
  @Test
  public void testShouldHaveLocationMessageForLocationEnum() {
    MetadataKeys keyForLocation = CreationMetadataKeys.LOCATION;
    assertEquals("Location", keyForLocation.getMessage());
  }

}
