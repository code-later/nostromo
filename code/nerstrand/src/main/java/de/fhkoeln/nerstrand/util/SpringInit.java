/*
 * SpringInit.java
 *
 * Version 1.0  Nov 28, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.nerstrand.util;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.engine.ServiceLifeCycle;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.OperationContext;
import org.apache.axis2.context.ServiceContext;
import org.apache.axis2.description.AxisService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.fhkoeln.cosima.util.Logger;

public class SpringInit implements ServiceLifeCycle {

  public OMElement springInit(OMElement ignore) {
    return null;
  }

  public void init(ServiceContext serviceContext) {}

  public void setOperationContext(OperationContext operationContext) {}

  public void destroy(ServiceContext serviceContext) {}

  /**
   * this will be called during the deployement time of the service.
   * irrespective of the service scope this method will be called
   */
  public void startUp(ConfigurationContext ignore, AxisService service) {
    ClassLoader classLoader = service.getClassLoader();
    ClassPathXmlApplicationContext context =
        new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" },
                                           false);
    context.setClassLoader(classLoader);
    context.refresh();

    Logger.debug("\n\nstartUp() set spring classloader via axisService.getClassLoader() ... ");
  }

  /**
   * this will be called during the deployement time of the service.
   * irrespective of the service scope this method will be called
   */
  public void shutDown(ConfigurationContext ignore, AxisService service) {}
}