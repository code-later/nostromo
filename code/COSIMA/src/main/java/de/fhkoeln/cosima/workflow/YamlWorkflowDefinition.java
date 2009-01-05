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
package de.fhkoeln.cosima.workflow;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.ho.yaml.YamlDecoder;


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

  /* (non-Javadoc)
   * @see de.fhkoeln.cosima.workflow.WorkflowDefinition#size()
   */
  public int size() {
    return elements.size();
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.cosima.workflow.WorkflowDefinition#elementsIterator()
   */
  public Iterator<Set<WorkflowElement>> elementsIterator() {
    return new WorkflowDefinitionIterator(elements);
  }

}
