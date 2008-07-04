/*
 * WorkflowWithoutCosima.java
 *
 * Version 1.0  Jun 17, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.examples;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Jun 17, 2008
 *
 */
public class WorkflowWithoutCosima {
  
  private final WorkflowDefinition workflowDefinition;

  /**
   * Constructor documentation comment.
   *
   * @param workflowDefinition
   */
  public WorkflowWithoutCosima(WorkflowDefinition workflowDefinition) {
    this.workflowDefinition = workflowDefinition;
  }

  /**
   * Runs the Workflow according to its definition. 
   */
  @SuppressWarnings("unchecked")
  public void run() {
    while (workflowDefinition.hasNextElement()) {
      Object[] element = workflowDefinition.getNextWorkflowElement();
      Class<WorkflowElement> workflowElementClass = (Class<WorkflowElement>) element[1];
      try {
        WorkflowElement workflowElement = workflowElementClass.newInstance();
        workflowElement.run();
      } catch (InstantiationException e) {
        System.err.println("Class " + workflowElementClass.getName() + " could not be instantiated.");
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        System.err.println("There was an illigal access with class " + workflowElementClass.getName() + ".");
        e.printStackTrace();
      }
    }
  }

}
