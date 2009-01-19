/*
 * MediaBroker.java
 *
 * Version 1.0  Nov 16, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.media.mediabroker;

import java.net.URI;

import de.fhkoeln.cosima.media.MediaComponent;
import de.fhkoeln.cosima.media.mediabroker.storage.MediaStore;

/**
 * Interface for a Media Broker. It defines methods to store and
 * receive MediaComponent Objects. Furthermore a method is provided to
 * query the overall objects known by the broker.
 * 
 * @author Dirk Breuer
 * @version 1.0 Nov 16, 2008
 */
public interface MediaBroker {

  /**
   * @param media The media object to store within the broker.
   */
  public URI store(MediaComponent media);

  /**
   * @param mediaUri The URI of the media object to retrieve from the broker.
   * @return The found media object (or null if none was found) with the
   *         MediaStore instance of this broker attached.
   */
  public MediaComponent retrieve(String mediaUri);
  
  /**
   * @return The amount of stored media objects
   */
  public int knownElements();

  /**
   * Clears all the media objects stored in the broker.
   */
  public void clearAll();

  /**
   * @return true if the Broker knows no elements
   */
  public boolean isEmtpy();

  /**
   * @param The media store instance to use
   */
  public void setMediaStore(MediaStore store);

}