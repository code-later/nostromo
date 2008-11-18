/*
 * SimpleMediaBroker.java
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

import java.util.HashMap;
import java.util.Map;

/**
 * Very simple implementation of the MediaBroker Interface. This
 * implementation is not thread-safe! Don't use it in any distributed
 * environment.
 * 
 * @author dbreuer
 * @version 1.0 Nov 16, 2008
 */
public class SimpleMediaBroker implements MediaBroker {

  Map<String, AbstractMedia> storage;

  public SimpleMediaBroker() {
    storage = new HashMap<String, AbstractMedia>();
  }

  /*
   * (non-Javadoc)
   * @see de.fhkoeln.santiago.media.MediaBroker#knownElements()
   */
  public int knownElements() {
    return storage.size();
  }

  /*
   * (non-Javadoc)
   * @see
   * de.fhkoeln.santiago.media.MediaBroker#retrieve(java.lang.String)
   */
  public AbstractMedia retrieve(String mediaId) {
    return storage.get(mediaId);
  }

  /*
   * (non-Javadoc)
   * @see
   * de.fhkoeln.santiago.media.MediaBroker#store(de.fhkoeln.santiago
   * .media.AbstractMedia)
   */
  public void store(AbstractMedia media) {
    storage.put(media.getName(), media);
  }

}
