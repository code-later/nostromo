/*
 * WorkflowDefinitionIterator.java
 *
 * Version 1.0  Jan 3, 2009
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2009 by dbreuer
 */
package de.fhkoeln.cosima.workflow;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import de.fhkoeln.cosima.workflow.WorkflowElement.Successor;

/**
 * An implementation of the {@link Iterator} interface to provide access to a
 * workflow's processing chain. The {@link Iterator#next()} method will return a
 * Set of {@link WorkflowElement} objects which should be processed next but
 * without any order.
 * 
 * @author Dirk Breuer
 * @version 1.0 Jan 3, 2009
 */
public class WorkflowDefinitionIterator implements
    Iterator<Set<WorkflowElement>> {

  private Map<String, WorkflowElement> elements;
  private Set<WorkflowElement> currentElements;

  /**
   * @param A Map with the {@link WorkflowElement} objects to iterate through.
   */
  public WorkflowDefinitionIterator(Map<String, WorkflowElement> elements) {
    this.elements = elements;
  }

  public boolean hasNext() {
    if (currentElements == null)
      return true;
    if (currentElements.isEmpty())
      return false;

    for (Iterator<WorkflowElement> iterator = currentElements.iterator(); iterator
        .hasNext();) {
      WorkflowElement element = iterator.next();
      if (element.hasSuccessors())
        return true;
    }
    return false;
  }

  public Set<WorkflowElement> next() {
    if (currentElements == null) {
      currentElements = new HashSet<WorkflowElement>();
      for (WorkflowElement element : elements.values()) {
        if (element.getPredecessors() == null)
          currentElements.add(element);
      }
    } else {
      // This works only because this is a Set, and due to this all
      // elements at this point with the same successor will store
      // only once that successor.
      Set<WorkflowElement> nextElements = new HashSet<WorkflowElement>();
      for (Iterator<WorkflowElement> iterator = currentElements.iterator(); iterator
          .hasNext();) {
        WorkflowElement nextElement = iterator.next();
        if (nextElement.getSuccessors() != null)
          for (Iterator<Successor> iter =
              nextElement.getSuccessors().iterator(); iter.hasNext();) {
            WorkflowElement workflowElement =
                elements.get(iter.next().getUri());
            nextElements.add(workflowElement);
          }
      }
      currentElements = nextElements;
      if (currentElements.isEmpty())
        throw new NoSuchElementException("There are no more Elements");
    }
    return currentElements;
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }
}
