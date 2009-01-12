/*
 * FileSystemStoreTest.java
 *
 * Version 1.0  Dec 25, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.media.mediabroker.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.junit.Test;

import de.fhkoeln.cosima.media.MediaComponent;
import de.fhkoeln.cosima.media.mediabroker.storage.FileSystemStore;
import de.fhkoeln.cosima.media.mediabroker.storage.MediaStore;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Dec 25, 2008
 *
 */
public class FileSystemStoreTest {

  /**
   * Test method for {@link de.fhkoeln.cosima.media.mediabroker.storage.FileSystemStore#read(java.lang.String)}.
   */
  @Test
  public void testReadAndWrite() {
    MediaStore fsStore = new FileSystemStore();
    fsStore.setStoreLocation("/tmp");

    MediaComponent media = mock(MediaComponent.class);
    System.out.println("--- current path: " + new File("res").getAbsolutePath());
    when(media.getReferenceToRealData()).thenReturn(URI.create("file:///tmp/1297336464"));
    
    try {
      String key = new Integer(media.hashCode()).toString();
      fsStore.write(media);

      assertTrue(new File("/tmp/"  + key).exists());
      assertEquals(new File("/tmp/" + key), fsStore.read(key));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
