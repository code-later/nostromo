/*
 * AbstractWeatherService.java
 *
 * Version 1.0  Nov 27, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.services;

import org.apache.axiom.om.OMElement;

import de.fhkoeln.cosima.util.Logger;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Nov 27, 2008
 *
 */
public class AbstractWeatherService {
  
  public void setInfo(OMElement info) {
    Logger.info("Calling AbstractWeatherService with message: " + info);
  }

}
