/*
 * AbstractMedia.java
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
public abstract class AbstractMedia {
  
  private List<Metadata> metadatas;
  private String name;
  private String uri;
  
  /**
   * Constructor to set the metadata object on creation.
   * 
   * @param metadata The Metadata object to set.
   */
  public AbstractMedia(List<Metadata> metadatas) {
    this.metadatas = metadatas;
  }
  
  public List<Metadata> getMetadata() {
    return this.metadatas;
  }
  
  public void addMetadata(Metadata metadata) {
    this.metadatas.add(metadata);
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getUri() {
    return this.uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

}
