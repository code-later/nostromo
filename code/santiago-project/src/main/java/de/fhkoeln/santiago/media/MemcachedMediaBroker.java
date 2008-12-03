/*
 * MemcachedMediaBroker.java
 *
 * Version 1.0  Nov 20, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.media;

import java.io.IOException;
import java.net.InetSocketAddress;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.transcoders.SerializingTranscoder;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Nov 20, 2008
 *
 */
public class MemcachedMediaBroker implements MediaBroker {

  private MemcachedClient client;
  
  public MemcachedMediaBroker() {
    try {
      client = new MemcachedClient(new InetSocketAddress("localhost", 11211));
      client.setTranscoder(new SerializingTranscoder());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /*
   * (non-Javadoc)
   * @see de.fhkoeln.santiago.media.MediaBroker#knownElements()
   */
  public int knownElements() {
    return Integer.parseInt(client.getStats().get(
        new InetSocketAddress("localhost", 11211)).get("curr_items"));
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.media.MediaBroker#retrieve(java.lang.String)
   */
  public AbstractMedia retrieve(String mediaId) {
    return (AbstractMedia) client.get(mediaId);
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.media.MediaBroker#store(de.fhkoeln.santiago.media.AbstractMedia)
   */
  public void store(AbstractMedia media) {
    client.set(media.getName(), 0, media);
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.media.MediaBroker#clearAll()
   */
  public void clearAll() {
    client.flush();
  }

  /*
   * (non-Javadoc)
   * @see de.fhkoeln.santiago.media.MediaBroker#isEmtpy()
   */
  public boolean isEmtpy() {
    return knownElements() > 0 ? false : true;
  }

}
