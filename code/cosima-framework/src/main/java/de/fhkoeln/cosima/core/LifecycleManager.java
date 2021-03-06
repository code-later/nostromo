/*
 * LifecycleManager.java
 *
 * Version 1.0  Mar 4, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.core;

import de.fhkoeln.cosima.registry.ServiceRepository;


/**
 * Documentation comment without implementation details.
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Mar 4, 2008
 *
 */
public class LifecycleManager {

  public void start() {
    ServiceRepository registry = ServiceRepository.getInstance();

    registry.getService("hello_world").run();

  }

}
