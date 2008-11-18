/*
 * MediaData.java
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

import java.util.List;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Nov 16, 2008
 *
 */
public class MediaData extends AbstractMedia {
  
  /**
   * Constructor to set the metadata object on creation.
   *
   * @param metadata
   */
  public MediaData(List<Metadata> metadatas) {
    super(metadatas);
  }

}
