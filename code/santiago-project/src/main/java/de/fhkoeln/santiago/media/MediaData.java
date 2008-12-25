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

import java.net.URI;
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
  
  private Object realMediaData;
  
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
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof MediaData))
      return false;
    MediaData other = (MediaData) obj;
    if (this.getUri() == null) {
      if (other.getUri() != null)
        return false;
    } else if (!this.getUri().equals(other.getUri()))
      return false;
    return true;
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.media.AbstractMedia#getReferenceToRealData()
   */
  @Override
  public URI getReferenceToRealData() throws UnsupportedOperationException {
    return URI.create((String) realMediaData);
  }
  
  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.media.AbstractMedia#setReferenceToRealData(java.lang.Object)
   */
  @Override
  public void setReferenceToRealData(Object realMediaData) throws UnsupportedOperationException {
    this.realMediaData = realMediaData;
  }
  
  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.media.AbstractMedia#getPlayableData()
   */
  @Override
  public Object getPlayableData() {
    return getStore().read(storageKey());
  }

}
