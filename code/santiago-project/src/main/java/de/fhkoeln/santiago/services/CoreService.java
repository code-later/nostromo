/*
 * CoreService.java
 *
 * Version 1.0  Sep 18, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.services;


/**
 * This is the service interface which every service component must
 * implement to be available via web service access. Every service
 * needs to be executable and must have a setter for its
 * {@link IODescriptor}.
 * 
 * In addition this interface defines the string literals of both
 * the operation to set the {@link IODescriptor} and to execute
 * the service.
 * 
 * @author dbreuer
 * @version 1.0 Sep 18, 2008
 */
public interface CoreService {
  
  /**
   * SERVICE_SET_INPUT_OPERATION The string literal of the operation
   * which sets the required input of a service component.
   */
  public static final String SERVICE_SET_INPUT_OPERATION = "setInput";
  
  /**
   * SERVICE_EXECUTE_OPERATION The string literal of the operation
   * which executes the service itself.
   */
  public static final String SERVICE_EXECUTE_OPERATION = "execute";

  public void setInput(IODescriptor descriptor);
  
  public IODescriptor execute();

  public String getUri();

  public String getDescription();

}