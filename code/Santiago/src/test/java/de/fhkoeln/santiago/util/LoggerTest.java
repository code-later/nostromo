/*
 * LoggerTest.java
 *
 * Version 1.0  Nov 24, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.util;

import org.junit.Test;

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
public class LoggerTest {
  
  @Test
  public void testShouldLogSomething() throws Exception {
    Logger.debug("This is a Debug Message");
  }
  
}
