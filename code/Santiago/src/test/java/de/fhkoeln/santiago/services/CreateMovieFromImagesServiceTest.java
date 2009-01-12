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
package de.fhkoeln.santiago.services;

import static org.mockito.Mockito.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import de.fhkoeln.cosima.media.mediabroker.MediaBroker;
import de.fhkoeln.cosima.media.mediabroker.MemcachedMediaBroker;
import de.fhkoeln.cosima.media.mediabroker.storage.FileSystemStore;
import de.fhkoeln.cosima.services.IODescriptor;
import de.fhkoeln.cosima.services.registry.ServiceRegistry;
import de.fhkoeln.santiago.services.SlideshowGeneratorService;

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
    
    SlideshowGeneratorService service = new SlideshowGeneratorService(mock(ServiceRegistry.class));
    
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
