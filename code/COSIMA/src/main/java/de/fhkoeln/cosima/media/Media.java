/*
 * Media.java
 *
 * Version 1.0  Nov 16, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.media;

import java.net.URI;

/**
 * This class implements the Leaf part within the Composite-Pattern.
 *
 * @author Dirk Breuer
 * @version 1.0  Nov 16, 2008
 *
 */
public class Media extends MediaComponent {
  
  private static final long serialVersionUID = -7185178004655851316L;
  
  private Object realMediaData;
  
  @Override
  public URI getReferenceToRealData() throws UnsupportedOperationException {
    return URI.create((String) realMediaData);
  }
  
  @Override
  public void setReferenceToRealData(Object realMediaData) throws UnsupportedOperationException {
    this.realMediaData = realMediaData;
  }
  
  @Override
  public Object getPlayableData() {
    // Lazy loading of data. Real data is only requested if it is really needed.
    return getStore().read(storageKey());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof Media))
      return false;
    Media other = (Media) obj;
    if (this.getUri() == null) {
      if (other.getUri() != null)
        return false;
    } else if (!this.getUri().equals(other.getUri()))
      return false;
    return true;
  }
}