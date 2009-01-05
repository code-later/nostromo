/*
 * WorkflowDefinition.java
 *
 * Version 1.0  Jul 3, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.workflow;

import java.util.Iterator;
import java.util.Set;

/**
 * The Interface for accessing a WorkflowDefinition instance from a client.
 * 
 * @author Dirk Breuer
 * @version 1.0 Jul 3, 2008
 */
public interface WorkflowDefinition {

  /**
   * @return The amount of containing Workflow Elements.
   */
  public int size();

  /**
   * Every workflow consists of a certain number of elements, which must be
   * processed in a certain order. In addition to this on every step in that
   * processing chain there could be elements which do not depend on each other
   * and so are not required to be run in a certain order, but must be run
   * within that very step. This method will return an iterator of the type
   * {@link WorkflowDefinitionIterator} to provide the described functionality
   * to the using client.
   * 
   * @return The Iterator for all the containing elements.
   */
  public Iterator<Set<WorkflowElement>> elementsIterator();
}
