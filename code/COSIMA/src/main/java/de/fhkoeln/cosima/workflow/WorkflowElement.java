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
package de.fhkoeln.cosima.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.fhkoeln.cosima.components.AbstractComponent;

/**
 * This class represents a single workflow element within a workflow
 * definition. The {@link WorkflowDefinition} is responsible for
 * creating {@link WorkflowElement} instances and hold them through
 * out the life cycle of a workflow definition. A single workflow
 * element holds all information to find, allocate and invoke a
 * service component. Furthermore it has information about what its
 * position is in the overall workflow.
 * 
 * @author dbreuer
 * @version 1.0 Jul 11, 2008
 */
public class WorkflowElement {

  /**
   * A simple inner class to represent a successor of the current
   * element. The Successor class just wraps the URI of the successing
   * element to have a better semantic.
   *
   * @author dbreuer
   * @version 1.0  Sep 29, 2008
   *
   */
  class Successor {

    /**
     * uri The URI of the successor. A URI of the same successor can
     * never change during runtime.
     */
    private final String uri;

    /**
     * The default constructor which sets the URI of the successor.
     *
     * @param uri
     */
    public Successor(String uri) {
      this.uri = uri;
    }

    /**
     * @return The URI of this successor.
     */
    public String getUri() {
      return this.uri;
    }
  }

  /**
   * A simple wrapper class describing the input of an element. The
   * input for an element be have one out of two characteristics:
   * 
   * <ul>
   *   <li>
   *     The input is internal. Due to this the URI is a reference
   *     to some internal media storage system (like a media broker)
   *   </li>
   *   <li>
   *     The input is external. In this case the URI points to file
   *     or something else which is not stored in the system so far.
   *     This type of input is only acceptable for "producer" components.
   *   </li>
   * </ul>
   *
   * @author dbreuer
   * @version 1.0  Sep 29, 2008
   *
   */
  public class Input {

    /**
     * uri The URI of the input. 
     */
    private final String uri;
    private final String data;

    public Input(String uri, String data) {
      this.uri = uri;
      this.data = data;
    }

    public String getUri() {
      return this.uri;
    }

    public String getData() {
      return this.data;
    }

    /**
     * TODO: This should be more explicit!
     * 
     * @return If the input is external or not
     */
    public boolean isExternal() {
      return (data != null);
    }

    /**
     * TODO: This should be more explicit!
     * 
     * @return If the input is internal or not
     */
    public boolean isInternal() {
      return (data == null);
    }
  }

  private String type;
  private String uri;
  private String description;
  private String className;
  private String namespace;
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
    for (Iterator<HashMap<String, String>> iterator = successors.iterator(); iterator
        .hasNext();) {
      Successor successor = new Successor(iterator.next().get("uri"));
      this.successors.add(successor);
    }
  }

  /**
   * @return
   * @throws ClassNotFoundException
   */
  @SuppressWarnings("unchecked")
  @Deprecated
  public Class<AbstractComponent> getElementClass()
                                                   throws ClassNotFoundException {
    if (this.elementClass != null)
      return this.elementClass;

    try {
      this.elementClass =
          (Class<AbstractComponent>) Class.forName(getClassName());
    } catch (ClassNotFoundException e) {
      throw new ClassNotFoundException("Class " + getClassName()
          + " could not be found.");
    }
    return this.elementClass;
  }

  /**
   * @return Either this element needs input or not.
   */
  public boolean needsInput() {
    return !input.isEmpty();
  }

  /**
   * TODO: This is not so nice. Every element can only have one output
   * port anyway. So we can either - store it into the workflow
   * definition file or - make this implicit
   * 
   * @return The URI of the output of this element
   */
  public String getOutputUri() {
    return output.get(0).get("uri");
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public String getNamespace() {
    return namespace;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}