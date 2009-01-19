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
package de.fhkoeln.cosima.workflow.storage;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple implementation of the {@link ProcessStore} interface which uses a
 * regular {@link Map} as its storage strategy.
 * 
 * @author Dirk Breuer
 * @version 1.0 Sep 25, 2008
 */
public class MapProcessStoreImpl implements ProcessStore {

  private Map<String, String> storage;

  public MapProcessStoreImpl() {
    storage = new HashMap<String, String>();
  }

  /*
   * (non-Javadoc)
   * @see
   * de.fhkoeln.cosima.workflow.storage.ProcessStore#getInputByKey(java.lang
   * .String)
   */
  public String getInputByKey(String key) {
    return storage.remove(key);
  }

  /*
   * (non-Javadoc)
   * @see
   * de.fhkoeln.cosima.workflow.storage.ProcessStore#putDataForKey(java.lang
   * .String, java.lang.String)
   */
  public void putDataForKey(String data, String key) {
    storage.put(key, data);
  }

}
