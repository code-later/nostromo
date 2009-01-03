/*
 * IODescriptorTest.java
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

import static org.junit.Assert.*;

import java.util.List;
import java.util.Vector;

import org.junit.Test;

import de.fhkoeln.cosima.services.IODescriptor;



/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Sep 21, 2008
 *
 */
public class IODescriptorTest {
  
  @Test
  public void testShouldReturnFirstElementOfCollection() {
    IODescriptor descriptor = new IODescriptor();
    List<String> elements = new Vector<String>();
    elements.add("first");
    elements.add("second");
    descriptor.setDescriptorElements(elements.toArray(new String[] {}));
    assertEquals("first", descriptor.first());
  }
  
  @Test
  public void testShouldReturnNullIfDescriptorHasNoElements() {
    IODescriptor descriptor = new IODescriptor();
    assertNull(descriptor.first());
  }
  
  @Test
  public void testShouldAddElementsToDescriptor() {
    IODescriptor descriptor = new IODescriptor();
    descriptor.add("an_element_to_add");
    assertEquals("an_element_to_add", descriptor.first());
  }

}
