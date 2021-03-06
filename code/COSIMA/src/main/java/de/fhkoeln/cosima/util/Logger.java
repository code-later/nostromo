/*
 * Logger.java
 *
 * Version 1.0  Nov 24, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.util;


/**
 * This logging singleton could be used by any application that uses COSIMA.
 *
 * @author Dirk Breuer
 * @version 1.0  Nov 24, 2008
 *
 */
public class Logger {
  
  private static Logger logger;
  private org.apache.log4j.Logger log;
  
  private Logger() {
    this.log = org.apache.log4j.Logger.getLogger("COSIMA Logger");
  }
  
  private static Logger instance() {
    if (logger == null) {
      logger = new Logger();
    }
    return logger;
  }
  
  private org.apache.log4j.Logger getLogger() {
    return this.log;
  }
  
  public static void trace(String logMessage) {
    Logger.instance().getLogger().trace(logMessage);
  }

  public static void debug(String logMessage) {
    Logger.instance().getLogger().debug(logMessage);
  }
  
  public static void info(String logMessage) {
    Logger.instance().getLogger().info(logMessage);
  }
  
  public static void warn(String logMessage) {
    Logger.instance().getLogger().warn(logMessage);
  }
  
  public static void error(String logMessage) {
    Logger.instance().getLogger().error(logMessage);
  }

}
