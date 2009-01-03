/*
 * ServiceRegistryTest.java
 *
 * Version 1.0  Nov 30, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.services.registry;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Test;

import de.fhkoeln.cosima.services.CoreService;
import de.fhkoeln.cosima.services.registry.MemcachedServiceRegistry;
import de.fhkoeln.cosima.services.registry.ServiceRegistry;



/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Nov 30, 2008
 *
 */
public class ServiceRegistryTest {
  
  @Test
  public void testShouldPublishServiceDescription() {
    ServiceRegistry registry = new MemcachedServiceRegistry();
    CoreService service = mock(CoreService.class);
    
    when(service.getUri()).thenReturn("http://santiago.fh-koeln.de/VideoPlayer");
    when(service.getDescription()).thenReturn("Consumer:VideoPlayer");
    
    registry.publish(service);

    verify(service).getUri();
    verify(service).getDescription();
  }
  
  @Test
  public void testShouldQueryForService() {
    ServiceRegistry registry = new MemcachedServiceRegistry();
    CoreService service = mock(CoreService.class);

    when(service.getUri()).thenReturn("http://santiago.fh-koeln.de/VideoPlayer");
    when(service.getDescription()).thenReturn("Consumer:VideoPlayer");
    
    registry.publish(service);
    
    String serviceEndpointUri = registry.query("Consumer:VideoPlayer");
    assertEquals("http://santiago.fh-koeln.de/VideoPlayer", serviceEndpointUri);
  }

}
