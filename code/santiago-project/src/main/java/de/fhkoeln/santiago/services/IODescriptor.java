/*
 * IODescriptor.java
 *
 * Version 1.0  Sep 21, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Sep 21, 2008
 *
 */
public class IODescriptor {
  
  private List<String> descriptorElements;
//  private String[] descriptorElements;

  /**
   * Constructor documentation comment.
   *
   */
  public IODescriptor() {
    this.descriptorElements = new ArrayList<String>();
//    this.setDescriptorElements(new String[10]);
  }

  /**
   * Adds an element to this descriptor which describes an output
   * location.
   */
  public void add(String element) {
    this.descriptorElements.add(element);
//    getDescriptorElements().add(element);
//    Arrays.asList(descriptorElements).add(element);
  }

  /**
   * @return The size of the descriptorElements in this descriptor.
   */
  public int size() {
    return this.getDescriptorElements().length;
  }

  /**
   * @return The first element in this descriptor.
   */
  public String first() {
    System.err.println("Current descriptorElements object:");
    System.err.println("  Class  : " + this.descriptorElements.getClass());
    System.err.println("  Content: " + this.descriptorElements.toString());
    return getDescriptorElements()[0];
  }

  public void setDescriptorElements(String[] strings) {
    for (String string : strings) {
      this.descriptorElements.add(string);
    }
  }

  public String[] getDescriptorElements() {
    return descriptorElements.toArray(new String[] {});
  }

  /**
   * @return
   */
  public boolean isEmpty() {
    return descriptorElements.isEmpty();
  }

}