/*
 * ServiceRegistry.java
 *
 * Version 1.0  Mar 4, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.registry;

import java.util.HashMap;
import java.util.Map;


/**
 * Documentation comment without implementation details.
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Mar 4, 2008
 *
 */
public class ServiceRegistry {

  private Map<String, CoreService> services;

  private static ServiceRegistry instance = null;

  private ServiceRegistry() {
    services = new HashMap<String, CoreService>();
  }

  public static ServiceRegistry getInstance() {
    if (instance == null) {
      instance = new ServiceRegistry();
    }
    return instance;
  }

  public void registerService(String withId, CoreService service) {
    services.put(withId, service);
  }

  public CoreService getService(String id) {
    return services.get(id);
  }

}
