/*
 * MediaStore.java
 *
 * Version 1.0  Dec 23, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.media.mediabroker.storage;

import java.io.IOException;

import de.fhkoeln.cosima.media.AbstractMedia;

/**
 * This interface defines an abstracht way of reading and writing media objects
 * from any persistance solution that implements this interface.
 *
 * @author Dirk Breuer
 * @version 1.0  Dec 23, 2008
 *
 */
public interface MediaStore {
  
  /**
   * @param data The media object which should be persisted.
   * @return The key to find the persisted data again.
   * @throws IOException If there was an error while writing the media to the persistance layer.
   */
  public String write(AbstractMedia data) throws IOException;
  
  /**
   * @param key The key to find the persisted data.
   * @return The Reference to the persisted data.
   */
  public Object read(String key);
  
  /**
   * The location where to store all media.
   * 
   * @param Store specific location value.
   */
  public void setStoreLocation(String location);
}
