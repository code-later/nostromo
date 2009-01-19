/*
 * WorkflowEngine.java
 *
 * Version 1.0  Jan 10, 2009
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2009 by dbreuer
 */
package de.fhkoeln.cosima.workflow;

import java.util.Map;

/**
 * The abstract {@link WorkflowEngine} class which defines the essential
 * <code>execute</code> method and implements the setters for the dependencies
 * of a {@link WorkflowEngine} instance.
 * 
 * @author Dirk Breuer
 * @version 1.0 Jan 19, 2009
 */
public abstract class WorkflowEngine {

  private Map<String, String> workflowStore;
  private WorkflowDefinition definition;

  /**
   * This method must be implemented by its subclasses to execute the workflow
   * defined in the {@link WorkflowDefinition} instance.
   * 
   * @throws Exception If something went wrong during execution.
   */
  public abstract void execute() throws Exception;

  public void setWorkflowDefinition(WorkflowDefinition definition) {
    this.definition = definition;
  }

  public WorkflowDefinition getWorkflowDefinition() {
    return this.definition;
  }

  public void setWorkflowStore(Map<String, String> workflowStore) {
    this.workflowStore = workflowStore;
  }

  public Map<String, String> getWorkflowStore() {
    return workflowStore;
  }
}