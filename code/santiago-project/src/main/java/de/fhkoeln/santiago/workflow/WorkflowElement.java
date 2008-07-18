/*
 * WorkflowElement.java
 *
 * Version 1.0  Jul 11, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.fhkoeln.santiago.components.AbstractComponent;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Jul 11, 2008
 *
 */
public class WorkflowElement {
  
  class Successor {
    private final String uri;
    public Successor(String uri) { this.uri = uri; }
    public String getUri() { return this.uri; }
  }
  
  class Input {
    private final String uri;
    private final String data;
    
    public Input(String uri, String data) {
      this.uri = uri;
      this.data = data;
    }
    
    public String getUri() { return this.uri; }
    public String getData() { return this.data; }
  }
  
  private String type;
  private String uri;
  private String className;
  private Class<AbstractComponent> elementClass;
  private List<Input> input;
  private List<Map<String, String>> output;
  private List<String> predecessors;
  private List<Successor> successors;
  
  public String getType() {
    return this.type;
  }
  
  public void setType(String type) {
    this.type = type;
  }
  
  public String getUri() {
    return this.uri;
  }
  
  public void setUri(String uri) {
    this.uri = uri;
  }
  
  public String getClassName() {
    return this.className;
  }
  
  public void setClassName(String className) {
    this.className = className;
  }
  
  public String[] getInputKeys() {
    String[] inputKeys = new String[this.input.size()];
    int i = 0;
    for (Input input : this.input) {
      inputKeys[i] = input.getUri();
      i++;
    }
    return inputKeys;
  }
  
  public List<Input> getInput() {
    return this.input;
  }
  
  public void setInput(List<Map<String, String>> rawInput) {
    this.input = new ArrayList<Input>();
    Input input;
    
    for (Map<String, String> singleRawInput : rawInput) {
      if (singleRawInput.get("type").equalsIgnoreCase("external")) {
        input = new Input(getUri() + "/input", singleRawInput.get("uri"));
      } else {
        input = new Input(singleRawInput.get("uri"), null);
      }
      this.input.add(input);
    }
  }
  
  public List<Map<String, String>> getOutput() {
    return this.output;
  }
  
  public void setOutput(List<Map<String, String>> output) {
    this.output = output;
  }
  
  public List<String> getPredecessors() {
    return this.predecessors;
  }
  
  public void setPredecessors(List<String> predecessors) {
    this.predecessors = predecessors;
  }
  
  public List<Successor> getSuccessors() {
    return this.successors;
  }
  
  public boolean hasSuccessors() {
    if (this.successors != null && !this.successors.isEmpty()) {
      return true;
    } else {
      return false;
    }
  }
  
  public void setSuccessors(List<HashMap<String, String>> successors) {
    this.successors = new ArrayList<Successor>();
    for (Iterator<HashMap<String, String>> iterator = successors.iterator(); iterator.hasNext();) {
      Successor successor = new Successor(iterator.next().get("uri"));
      this.successors.add(successor);
    }
  }
  
  @SuppressWarnings("unchecked")
  public Class<AbstractComponent> getElementClass() throws ClassNotFoundException {
    if (this.elementClass != null)
      return this.elementClass;
    
    try {
      this.elementClass = (Class<AbstractComponent>) Class.forName(getClassName());
    } catch (ClassNotFoundException e) {
      throw new ClassNotFoundException("Class " + getClassName() + " could not be found.");
    }
    return this.elementClass;
  }

}
