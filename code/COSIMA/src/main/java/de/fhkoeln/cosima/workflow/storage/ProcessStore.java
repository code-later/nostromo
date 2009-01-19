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

import de.fhkoeln.cosima.workflow.WorkflowEngine;

/**
 * This interface defines a unified storage solution for a {@link WorkflowEngine} instance to store its processing.
 * 
 * @author Dirk Breuer
 * @version 1.0 Jan 19, 2009
 */
public interface ProcessStore {

  /**
   * @param data
   *          The data which should be stored under a certain key
   * @param key
   *          The key under which the data is stored
   */
  public void putDataForKey(String data, String key);

  /**
   * @param key
   *          The key under which the data was stored
   * @return The data for the given key
   */
  public String getInputByKey(String key);

}