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
package de.fhkoeln.cosima.media.mediabroker;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;

import de.fhkoeln.cosima.media.MediaComponent;
import de.fhkoeln.cosima.media.mediabroker.storage.MediaStore;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.transcoders.SerializingTranscoder;

/**
 * An implementation of the {@link MediaBroker} interface based on a memcached
 * storage solution. The connection to the memcached server is build statically
 * in the constructor.
 * 
 * @author Dirk Breuer
 * @version 1.0 Nov 20, 2008
 */
public class MemcachedMediaBroker implements MediaBroker {

  public final static String BROKER_URI = "cosima://santiago.fh-koeln.de/media";

  private MemcachedClient client;
  private MediaStore mediaStore;

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
   * @see de.fhkoeln.cosima.media.MediaBroker#knownElements()
   */
  public int knownElements() {
    return Integer.parseInt(client.getStats().get(
        new InetSocketAddress("localhost", 11211)).get("curr_items"));
  }

  /*
   * (non-Javadoc)
   * @see de.fhkoeln.cosima.media.MediaBroker#retrieve(java.lang.String)
   */
  public MediaComponent retrieve(String mediaUri) {
    MediaComponent storedMedia = (MediaComponent) client.get(mediaUri);
    storedMedia.setStore(mediaStore);
    return storedMedia;
  }

  /*
   * (non-Javadoc)
   * @see de.fhkoeln.cosima.media.MediaBroker#store(de.fhkoeln.cosima
   * .media.AbstractMedia)
   */
  public URI store(MediaComponent media) {
    URI realUri = URI.create(BROKER_URI + media.getUri());

    try {
      client.set(realUri.toString(), 0, media);
      mediaStore.write(media);
      // Ensure that the reference is no longer available, because it is not intended to be
      // used by some one else then the MediaStore instances
      media.setReferenceToRealData(null);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return realUri;
  }

  /*
   * (non-Javadoc)
   * @see de.fhkoeln.cosima.media.MediaBroker#clearAll()
   */
  public void clearAll() {
    client.flush();
  }

  /*
   * (non-Javadoc)
   * @see de.fhkoeln.cosima.media.MediaBroker#isEmtpy()
   */
  public boolean isEmtpy() {
    return knownElements() > 0 ? false : true;
  }

  /*
   * (non-Javadoc)
   * @see
   * de.fhkoeln.cosima.media.mediabroker.MediaBroker#setMediaStore(de.fhkoeln
   * .cosima.media.mediabroker.storage.MediaStore)
   */
  public void setMediaStore(MediaStore store) {
    this.mediaStore = store;
  }

}
