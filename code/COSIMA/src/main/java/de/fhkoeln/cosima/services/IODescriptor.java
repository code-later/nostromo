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
package de.fhkoeln.cosima.services;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the Value-Object-Pattern for encapsulating the
 * input and output parameters of a service.
 *
 * @author Dirk Breuer
 * @version 1.0  Sep 21, 2008
 *
 */
public class IODescriptor {
  
  private List<String> descriptorElements;

  public IODescriptor() {
    this.descriptorElements = new ArrayList<String>();
  }

  /**
   * Adds an element to this descriptor which describes an output
   * location.
   */
  public void add(String element) {
    this.descriptorElements.add(element);
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
    try {
      return getDescriptorElements()[0];
    } catch (ArrayIndexOutOfBoundsException e) {
      // if there are no elements just return null     
      return null;
    }
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
   * @return If the Descriptor is empty.
   */
  public boolean isEmpty() {
    return descriptorElements.isEmpty();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + ((this.descriptorElements == null) ? 0 : this.descriptorElements.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof IODescriptor))
      return false;
    IODescriptor other = (IODescriptor) obj;
    if (this.descriptorElements == null) {
      if (other.descriptorElements != null)
        return false;
    } else if (!this.descriptorElements.equals(other.descriptorElements))
      return false;
    return true;
  }
}