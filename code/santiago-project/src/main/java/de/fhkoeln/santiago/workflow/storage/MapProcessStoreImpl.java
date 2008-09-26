/*
 * MapProcessStoreImpl.java
 *
 * Version 1.0  Sep 25, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.workflow.storage;

import java.util.HashMap;
import java.util.Map;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Sep 25, 2008
 *
 */
public class MapProcessStoreImpl implements ProcessStore {
  
  private Map<String, String> storage;
  
  /**
   * Constructor documentation comment.
   *
   */
  public MapProcessStoreImpl() {
    storage = new HashMap<String, String>();
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.workflow.storage.ProcessStore#getInputByKey(java.lang.String)
   */
  public String getInputByKey(String key) {
    return storage.remove(key);
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.workflow.storage.ProcessStore#putDataForKey(java.lang.String, java.lang.String)
   */
  public void putDataForKey(String data, String key) {
    storage.put(key, data);
  }

}
