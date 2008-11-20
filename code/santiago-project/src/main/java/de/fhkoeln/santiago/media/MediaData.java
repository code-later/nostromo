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
  
  private static final long serialVersionUID = -7185178004655851316L;

  public MediaData() {
    super();
  }
  
  /**
   * Constructor to set the metadata object on creation.
   *
   * @param metadata
   */
  public MediaData(List<Metadata> metadatas) {
    super(metadatas);
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof MediaData))
      return false;
    
    MediaData otherMedia = (MediaData) obj;
    if ((this.getName().equals(otherMedia.getName())) && (this.getUri().equals(this.getUri())))
      return true;

    return false;
  }

}
