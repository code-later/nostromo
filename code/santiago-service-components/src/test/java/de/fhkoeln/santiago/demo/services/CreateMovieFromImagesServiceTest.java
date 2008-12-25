/*
 * CreateMovieFromImagesServiceTest.java
 *
 * Version 1.0  Sep 19, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.demo.services;

import static org.mockito.Mockito.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import de.fhkoeln.santiago.media.mediabroker.MediaBroker;
import de.fhkoeln.santiago.media.mediabroker.MemcachedMediaBroker;
import de.fhkoeln.santiago.media.mediabroker.storage.FileSystemStore;
import de.fhkoeln.santiago.services.IODescriptor;
import de.fhkoeln.santiago.services.registry.ServiceRegistry;

/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Sep 19, 2008
 *
 */
public class CreateMovieFromImagesServiceTest {

  @Test
  public void testShouldExecuteWithInputDescriptor() {
    IODescriptor input = new IODescriptor();
//    input.add("/Users/dbreuer/Documents/Work/_FH/_Master/master_thesis/code/santiago-project/res/");
    input.setDescriptorElements(new String[] {"/Users/dbreuer/Documents/Work/_FH/_Master/master_thesis/code/santiago-project/res/"});
    
    CreateMovieFromImagesService service = new CreateMovieFromImagesService(mock(ServiceRegistry.class));
    
    MediaBroker mBroker = new MemcachedMediaBroker();
    mBroker.setMediaStore(new FileSystemStore());
    
    service.setBroker(mBroker);
    
//    IODescriptor output = service.execute(input);
    service.setInput(input);
    IODescriptor output = service.execute();
    assertNotNull(output);
    assertEquals(1, output.size());
    assertEquals("cosima://santiago.fh-koeln.de/media/MovieFromJPEGs", output.first());
    assertTrue(new File("/tmp/output.mov").exists());
  }
  
}
