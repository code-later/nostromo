/*
 * ComponentFactory.java
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
public class ComponentFactory {


  /**
   * Constructor documentation comment.
   *
   */
  private ComponentFactory() {
    // TODO Auto-generated constructor stub
  }

  public static CoreService createService(ServiceDefinition serviceDefinition) {

    try {
      Class serviceClass = Class.forName(serviceDefinition.getClassFullName());
      CoreService coreService = (CoreService) serviceClass.newInstance();
      return coreService;
    } catch (ClassNotFoundException e) {
      System.out.println("Class " + serviceDefinition.getClassFullName() + " could not be found.");
      e.printStackTrace();
    } catch (InstantiationException e) {
      System.out.println("Class " + serviceDefinition.getClassFullName() + " could not be instantiated.");
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      System.out.println("There was an illigal access with class " + serviceDefinition.getClassFullName() + ".");
      e.printStackTrace();
    }
    return null;
  }

}
