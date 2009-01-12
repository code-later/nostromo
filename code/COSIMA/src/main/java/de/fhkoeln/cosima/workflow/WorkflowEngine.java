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

public abstract class WorkflowEngine {

  private Map<String, String> workflowStore;
  private WorkflowDefinition definition;

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