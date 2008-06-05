/*
 * CreatorMetadataKeysTest.java
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
public class CreatorMetadataKeysTest {

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
  
  @Test
  public void testShouldAuthorMessageForAuthorEnum() {
    MetadataKeys keyForAuthor = CreatorMetadataKeys.AUTHOR;
    assertEquals("Author", keyForAuthor.getMessage());
  }

}
