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

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import sun.security.provider.SHA;

import de.fhkoeln.santiago.media.mediabroker.storage.MediaStore;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Nov 16, 2008
 *
 */
public abstract class AbstractMedia implements Serializable {
  
  private static final long serialVersionUID = -5578809582939403020L;

  private List<Metadata> metadatas;
  private MediaStore store;

  private String name;
  private String namespace;
  
  public AbstractMedia() {
    metadatas = new ArrayList<Metadata>();
  }
  
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
    String uri = "/";
    
    try {
      if (getNamespace() != null) {
        for (int i = 0; i < getNamespace().length; i++) {
          uri += URLEncoder.encode(getNamespace()[i], "UTF-8");
          uri += "/";
        }
      }
      uri += URLEncoder.encode(getName(), "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    
    return uri;
  }

  public void setStore(MediaStore store) {
    this.store = store;
  }

  public MediaStore getStore() {
    return store;
  }
  
  public void setReferenceToRealData(Object realMediaData) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  public URI getReferenceToRealData() throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  /**
   * @return The namespace as String Array
   */
  public String[] getNamespace() {
    return namespace == null ? null : namespace.split("::");
  }

  /**
   * Lets on set a namespace in the form "Foo::Bar".
   * 
   * @param Namespace of this Media Object.
   */
  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public int size() throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }
  
  public AbstractMedia getChild(int atIndex) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }
  
  public void removeMedia(AbstractMedia media) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }
  
  public void addMedia(AbstractMedia media) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }
  
  public final String storageKey() {
    return new Integer(Math.abs(hashCode())).toString();
  }
  
  public abstract Object getPlayableData();

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
    result =
        prime * result
            + ((this.namespace == null) ? 0 : this.namespace.hashCode());
    return result;
  }

}
