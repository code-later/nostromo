/*
 * AbstractComponent.java
 *
 * Version 1.0  Nov 24, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.demo.services;

import de.fhkoeln.santiago.demo.util.Logger;
import de.fhkoeln.santiago.media.MediaBroker;
import de.fhkoeln.santiago.media.MemcachedMediaBroker;
import de.fhkoeln.santiago.services.CoreService;
import de.fhkoeln.santiago.services.IODescriptor;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Nov 24, 2008
 *
 */
public abstract class AbstractComponent implements CoreService {

  private MediaBroker broker;
  private IODescriptor input;
  
  public AbstractComponent() {
    Logger.info("Booting Service: " + getClass().getName());
    broker = new MemcachedMediaBroker();
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.services.CoreService#execute()
   */
  public IODescriptor execute() {
    Logger.info("Executing Service: " + getClass().getName());
    IODescriptor output = _execute();
    return output;
  }
  
  abstract protected IODescriptor _execute();

  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.services.CoreService#setInput(de.fhkoeln.santiago.services.IODescriptor)
   */
  public void setInput(IODescriptor input) {
    this.input = input;
  }
  
  public IODescriptor getInput() {
    return this.input;
  }

  protected MediaBroker getBroker() {
    return this.broker;
  }
  
}
