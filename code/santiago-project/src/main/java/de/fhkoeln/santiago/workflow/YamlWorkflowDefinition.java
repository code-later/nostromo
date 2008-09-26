/*
 * YamlWorkflowDefinition.java
 *
 * Version 1.0  Jul 17, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.workflow;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.ho.yaml.YamlDecoder;

import de.fhkoeln.santiago.workflow.WorkflowElement.Successor;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Jul 17, 2008
 *
 */
public class YamlWorkflowDefinition implements WorkflowDefinition {
  
  private Map<String, WorkflowElement> elements;
  private Set<WorkflowElement> currentElements;

  /**
   * Build a new YamlWorkflowDefinition object which generates
   * WorkflowElements from the YAML Definition file.
   * 
   * @param pathToYamlDefinition
   *          The path to the YAML definition file which holds all
   *          information about the workflow elements.
   * @throws IOException
   *           If the given pathname doesn't exists or any other error
   *           occurs while processing the file.
   */
  public YamlWorkflowDefinition(String pathToYamlDefinition)
    throws IOException {
    elements = new HashMap<String, WorkflowElement>();
    FileInputStream stream = new FileInputStream(new File(pathToYamlDefinition));
    YamlDecoder yamlDecoder = new YamlDecoder(stream);
    try {
      while (true) {
        WorkflowElement element = yamlDecoder
            .readObjectOfType(WorkflowElement.class);
        elements.put(element.getUri(), element);
      }
    } catch (EOFException e) {
      System.err.println("Finished reading stream.");
    } finally {
      yamlDecoder.close();
      stream.close();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.fhkoeln.santiago.workflow.WorkflowDefinition#getNextElement()
   */
  @SuppressWarnings("unchecked")
  
  public Set<WorkflowElement> getNextElements() throws NoSuchElementException {
    if (currentElements == null) {
      currentElements = new HashSet<WorkflowElement>();
      for (Iterator iterator = elements.values().iterator(); iterator.hasNext();) {
        WorkflowElement element = (WorkflowElement) iterator.next();
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
          for (Iterator<Successor> iter = nextElement.getSuccessors()
              .iterator(); iter.hasNext();) {
            WorkflowElement workflowElement = elements
                .get(iter.next().getUri());
            nextElements.add(workflowElement);
          }
      }
      currentElements = nextElements;
      if (currentElements.isEmpty())
        throw new NoSuchElementException("There are no more Elements");
    }
    return currentElements;
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.fhkoeln.santiago.workflow.WorkflowDefinition#getNextWorkflowElement()
   */
  
  public Object[] getNextWorkflowElement() throws NoSuchElementException {
    return null;
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.workflow.WorkflowDefinition#hasNextElement()
   */
  
  public boolean hasNextElements() {
    if (currentElements == null)
      return true;
    if (currentElements.isEmpty())
      return false;

    for (Iterator<WorkflowElement> iterator = currentElements.iterator(); iterator.hasNext();) {
      WorkflowElement element = iterator.next();
      if (element.hasSuccessors())
        return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.workflow.WorkflowDefinition#rewind()
   */
  
  public void rewind() {
  // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.workflow.WorkflowDefinition#size()
   */
  
  public int size() {
    return elements.size();
  }

}
