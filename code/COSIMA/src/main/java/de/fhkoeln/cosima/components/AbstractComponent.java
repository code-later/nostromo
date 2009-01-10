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
package de.fhkoeln.cosima.components;

import de.fhkoeln.cosima.media.mediabroker.MediaBroker;
import de.fhkoeln.cosima.services.CoreService;
import de.fhkoeln.cosima.services.IODescriptor;
import de.fhkoeln.cosima.services.registry.ServiceRegistry;
import de.fhkoeln.cosima.util.Logger;

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
  
  private final ServiceRegistry registry;
  private final String description;
  private final String uri;
  
  public AbstractComponent(ServiceRegistry registry, String uri, String description) {
    Logger.info("Booting Service: " + getClass().getName());
    this.registry    = registry;
    this.uri         = uri;
    this.description = description;
    
    this.registry.publish(this);
  }

  /*
   * This method should be final. Due to issues with Axis at the time
   * of coding it was not able that this method was cleanly inherited
   * and so could be final.
   */
  public IODescriptor execute() {
    IODescriptor output = _execute();
    return output;
  }

  protected abstract IODescriptor _execute();
  
  public void setInput(IODescriptor descriptor) {
    this.input = descriptor;
  }
  
  public IODescriptor getInput() {
    return this.input;
  }

  public MediaBroker getBroker() {
    return this.broker;
  }

  public void setBroker(MediaBroker broker) {
    this.broker = broker;
  }

  public String getDescription() {
    return this.description;
  }

  public String getUri() {
    return this.uri;
  }

}