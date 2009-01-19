package de.fhkoeln.cosima.media;

import java.net.URI;

/**
 * A simple interface for defining the essential elements of any metadata
 * object associated with a media object.
 * 
 * @author Dirk Breuer
 * @version 1.0 Jan 19, 2009
 */
public interface Metadata {

  public URI getUri();

  public String getNamespace();

  public void setNamespace(String namespace);

  public String getKey();

  public void setKey(String key);

  public String getValue();

  public void setValue(String value);

}
