/*
 * ServiceRegistry.java
 *
 * Version 1.0  Nov 30, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.services.registry;

import de.fhkoeln.cosima.services.CoreService;

/**
 * The service registry interface for accessing and publishing {@link CoreService} instances.
 *
 * @author Dirk Breuer
 * @version 1.0  Nov 30, 2008
 *
 */
public interface ServiceRegistry {

  /**
   * Publishes the given service in the Service Registry.
   * 
   * @param service The Service which should be published.
   */
  public void publish(CoreService service);

  /**
   * Queries for a service by the given description.
   * 
   * @param description A Description of the Service.
   * @return The URI of the Service.
   */
  public String query(String description);

}