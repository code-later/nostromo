/*
 * SantiagoPlainWithWorkflowDefinition.java
 *
 * Executes the Santiago application based on a declarative definition
 * file in YAML format.
 *
 * (c) 2009 by Dirk Breuer
 */
package de.fhkoeln.santiago.codesamples;

import de.fhkoeln.cosima.workflow.SimpleWorkflowEngine;
import de.fhkoeln.cosima.workflow.WorkflowDefinition;
import de.fhkoeln.cosima.workflow.WorkflowEngine;
import de.fhkoeln.cosima.workflow.YamlWorkflowDefinition;


public class SantiagoPlainWithWorkflowDefinition {

  public static void main(String[] args) throws Exception {
    if (args.length == 1) {
      String pathToWorkflowDefinition = args[0];
      WorkflowDefinition definition = new YamlWorkflowDefinition(pathToWorkflowDefinition);
      
      WorkflowEngine workflowInstance = new SimpleWorkflowEngine();
      workflowInstance.setWorkflowDefinition(definition);
      workflowInstance.execute();

    } else {
      System.err.println("Path to the workflow definition is needed!");
      System.exit(-1);
    }
  }
}