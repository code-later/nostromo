/*
 * AbstractMediaTest.java
 *
 * Version 1.0  Nov 16, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.media;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;



/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Nov 16, 2008
 *
 */
public class AbstractMediaTest {
  
  @Test
  public void testShouldHaveMetadata() throws Exception {
    List<Metadata> metadatas = new ArrayList<Metadata>();
    metadatas.add(mock(Metadata.class));
    
    AbstractMedia media = new MediaData(metadatas);
    assertNotNull(media.getMetadata());
  }
  
  @Test
  public void testShouldHaveName() {
    List<Metadata> metadatas = new ArrayList<Metadata>();
    metadatas.add(mock(Metadata.class));
    
    AbstractMedia media = new MediaData(metadatas);
    media.setName("Foo Media");
    assertEquals("Foo Media", media.getName());
  }

}
