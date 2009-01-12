/*
 * Metadata.java
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

public interface Metadata {
  
  public String getNamespace();
  public void setNamespace(String namespace);
  
  public String getKey();
  public void setKey(String key);
  
  public String getValue();
  public void setValue(String value);

}
