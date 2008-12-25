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
package de.fhkoeln.santiago.media.mediabroker.storage;

import java.io.IOException;

import de.fhkoeln.santiago.media.AbstractMedia;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Dec 23, 2008
 *
 */
public interface MediaStore {
  
  public String write(AbstractMedia data) throws IOException;
  public Object read(String key);
  
  /**
   * The location where to store all media.
   * 
   * @param Store specific location value.
   */
  public void setStoreLocation(String location);

}
