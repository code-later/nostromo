/*
 * WorkflowDefinitionImpl.java
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

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Vector;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Jul 3, 2008
 *
 */
public class WorkflowDefinitionImpl implements WorkflowDefinition {

  /**
   * ABSTRACT_DEFINITION_FORMAT documentation comment.
   */
  public static final String ABSTRACT_DEFINITION_PATTERN = "^((\\d,(\\w+.?)+);?)+$";
  private final Vector<Object[]> workflowElements;
  private Iterator<Object[]> elementsIterator;
  
  /**
   * Constructor documentation comment.
   * 
   * @param abstractDefinition
   * @throws ClassNotFoundException 
   */
  public WorkflowDefinitionImpl(String abstractDefinition) throws ClassNotFoundException {
    if (abstractDefinition == null || abstractDefinition == "") {
      throw new IllegalArgumentException("The abstract Workflow Definition is emtpy.");
    } else if(!abstractDefinition.matches(ABSTRACT_DEFINITION_PATTERN)) {
      throw new IllegalArgumentException("The abstract Workflow Definition is not in the right format. Pattern is: " + ABSTRACT_DEFINITION_PATTERN);
    }
    workflowElements = extractInformationFromAbstractDefinition(abstractDefinition);
    // rewind will also inits the elementsIterator field with the
    // Iterator of the Vector
    this.rewind();
  }

  /**
   * @param abstractDefinition
   * @return
   * @throws ClassNotFoundException 
   */
  private Vector<Object[]> extractInformationFromAbstractDefinition(String abstractDefinition) throws ClassNotFoundException {
    Vector<Object[]> elements = new Vector<Object[]>();
    
    String[] definitionElements = abstractDefinition.split(";");
    for (String string : definitionElements) {
      Object[] idAndClassName = string.split(",");
      Integer elementId = Integer.valueOf((String) idAndClassName[0]);
      try {
        Class elementClass = Class.forName((String) idAndClassName[1]);
        elements.add(new Object[] {elementId, elementClass});
      } catch (ClassNotFoundException e) {
        throw new ClassNotFoundException("Class " + idAndClassName[1] + " could not be found.");
      }
    }
    
    return elements;
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.cosima.WorkflowDefinition#getNextWorkflowElement()
   */
  
  public Object[] getNextWorkflowElement() throws NoSuchElementException {
    if(!elementsIterator.hasNext()) {
      throw new NoSuchElementException("There are no more Elements in the Workflow Definition.");
    }
    return elementsIterator.next();
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.cosima.WorkflowDefinition#size()
   */
  
  public int size() {
    return workflowElements.size();
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.cosima.WorkflowDefinition#rewind()
   */
  
  public void rewind() {
    elementsIterator = workflowElements.listIterator();
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.cosima.WorkflowDefinition#hasNextElement()
   */
  
  public boolean hasNextElements() {
    return elementsIterator.hasNext();
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.cosima.workflow.WorkflowDefinition#getNextElement()
   */
  
  public Set<WorkflowElement> getNextElements() throws NoSuchElementException {
    WorkflowElement element = new WorkflowElement();
    Object[] next = this.getNextWorkflowElement();
    element.setUri(next[0].toString());
    Class nextClass = (Class) next[1];
    element.setClassName(nextClass.getCanonicalName());
    HashSet<WorkflowElement> _set = new HashSet<WorkflowElement>();
    _set.add(element);
    return _set;
  }

}
