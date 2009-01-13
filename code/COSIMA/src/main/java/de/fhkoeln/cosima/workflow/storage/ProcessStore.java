/*
 * ProcessStore.java
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

public interface ProcessStore {

  /**
   * @param data The data which should be stored under a certain key
   * @param key The key under which the data is stored
   */
  public void putDataForKey(String data, String key);

  /**
   * @param key The key under which the data was stored
   * @return The data for the given key
   */
  public String getInputByKey(String key);

}