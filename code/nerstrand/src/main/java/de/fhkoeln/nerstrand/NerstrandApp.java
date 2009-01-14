package de.fhkoeln.nerstrand;

import java.io.IOException;

import de.fhkoeln.cosima.services.registry.MemcachedServiceRegistry;
import de.fhkoeln.cosima.services.registry.ServiceRegistry;
import de.fhkoeln.cosima.workflow.RemoteWorkflowEngine;
import de.fhkoeln.cosima.workflow.WorkflowDefinition;
import de.fhkoeln.cosima.workflow.YamlWorkflowDefinition;
import de.fhkoeln.cosima.workflow.storage.MapProcessStoreImpl;
import de.fhkoeln.cosima.workflow.storage.ProcessStore;

public class NerstrandApp {
  
  private static String pathToWorkflowDefinition;

  public static void main(String[] args) throws IOException {
  
    if (args.length == 1) {
      pathToWorkflowDefinition = args[0];
      
      WorkflowDefinition   workflowDefinition = new YamlWorkflowDefinition(pathToWorkflowDefinition);
      ProcessStore         processStore       = new MapProcessStoreImpl();
      RemoteWorkflowEngine engine             = new RemoteWorkflowEngine();
      ServiceRegistry      registry           = new MemcachedServiceRegistry();

      engine.setWorkflowDefinition(workflowDefinition);
      engine.setProcessStore(processStore);
      engine.setRegistry(registry);
      
      engine.execute();
      
    } else {
      System.err.println("Path to the workflow definition is needed!");
      System.exit(-1);
    }
    
  }
}