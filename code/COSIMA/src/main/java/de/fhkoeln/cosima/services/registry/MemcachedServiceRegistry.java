/*
 * MemcachedServiceRegistry.java
 *
 * Version 1.0  Dec 1, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.services.registry;

import java.io.IOException;
import java.net.InetSocketAddress;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.transcoders.SerializingTranscoder;
import de.fhkoeln.cosima.services.CoreService;

/**
 * A simple implementation of the {@link ServiceRegistry} interface using a
 * memcached server as storage strategy. The connection information for the
 * memcached server is set statically in the constructor.
 * 
 * @author Dirk Breuer
 * @version 1.0 Dec 1, 2008
 */
public class MemcachedServiceRegistry implements ServiceRegistry {

  private MemcachedClient client;

  public MemcachedServiceRegistry() {
    try {
      client = new MemcachedClient(new InetSocketAddress("localhost", 11211));
      client.setTranscoder(new SerializingTranscoder());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /*
   * (non-Javadoc)
   * @see
   * de.fhkoeln.cosima.services.registry.ServiceRegistry#publish(de.fhkoeln.
   * cosima.services.CoreService)
   */
  public void publish(CoreService service) {
    client.set(service.getDescription(), 0, service.getUri());
  }

  /*
   * (non-Javadoc)
   * @see
   * de.fhkoeln.cosima.services.registry.ServiceRegistry#query(java.lang.String)
   */
  public String query(String description) {
    return (String) client.get(description);
  }

}
