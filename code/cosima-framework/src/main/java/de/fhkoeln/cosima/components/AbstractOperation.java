/*
 * AbstractFunction.java
 *
 * Version 1.0  Mar 4, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.components;

import de.fhkoeln.cosima.registry.CoreService;


/**
 * Documentation comment without implementation details.
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Mar 4, 2008
 *
 */
public abstract class AbstractOperation implements CoreService {

  public AbstractOperation() {
    init();
  }

  /**
   *
   */
  private void init() {
    _setup();
    setup();
  }

  private void _setup() {
    System.out.println("--> SETUP!");
  }

  public abstract void setup();

  public abstract void run();

}
