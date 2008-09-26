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
 * implement to be available via web service access.http://m2eclipse.sonatype.org/update/site.xml
 *
 * @author dbreuer
 * @version 1.0  Sep 18, 2008
 *
 */
public interface CoreService {
  
  public IODescriptor execute(IODescriptor inputDescriptor);

}