/*
 * ServiceDefinition.java
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


/**
 * Documentation comment without implementation details.
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Mar 4, 2008
 *
 */
public class ServiceDefinition {

  private String classFullName;
  private String componentType;

  /**
   * Constructor documentation comment.
   *
   * @param string
   * @param string2
   */
  public ServiceDefinition(String componentType, String classFullName) {
    this.componentType = componentType;
    this.classFullName = classFullName;
  }

  /**
   * @return
   */
  public String getClassFullName() {
    return this.classFullName;
  }

  /**
   * @param classFullName the classFullName to set
   */
  public void setClassFullName(String classFullName) {
    this.classFullName = classFullName;
  }

  /**
   * @return the componentType
   */
  public String getComponentType() {
    return this.componentType;
  }

  /**
   * @param componentType the componentType to set
   */
  public void setComponentType(String componentType) {
    this.componentType = componentType;
  }

}
