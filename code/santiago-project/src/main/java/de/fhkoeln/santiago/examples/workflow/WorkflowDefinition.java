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
package de.fhkoeln.santiago.examples.workflow;

import java.util.NoSuchElementException;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Jul 3, 2008
 *
 */
public interface WorkflowDefinition {
  
  /**
   * Returns the next Element in this Workflow Definition which should
   * be processed.
   * 
   * @return An Array with two elements. The first Element is the
   *         number of the workflow item. The second is the Class of
   *         the item which needs to be run.
   * @throws NoSuchElementException
   *           If there are no more Elements to fetch from the
   *           definition list.
   */
  public Object[] getNextWorkflowElement() throws NoSuchElementException;

  /**
   * @return The amount of Workflow Elements.
   */
  public int size();

  /**
   * Rewinds the definition list, so it can be read again.
   */
  public void rewind();

  /**
   * @return If there are any more elements to fetch or not.
   */
  public boolean hasNextElement();

}
