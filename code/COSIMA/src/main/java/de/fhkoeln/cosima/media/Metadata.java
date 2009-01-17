package de.fhkoeln.cosima.media;

import java.net.URI;

public interface Metadata {
  
  public URI getUri();
  
  public String getNamespace();
  public void setNamespace(String namespace);
  
  public String getKey();
  public void setKey(String key);
  
  public String getValue();
  public void setValue(String value);

}
