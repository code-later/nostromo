/*
 * RemoteWorkflowEngineTest.java
 *
 * Version 1.0  Sep 25, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.workflow;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import de.fhkoeln.cosima.services.registry.MemcachedServiceRegistry;
import de.fhkoeln.cosima.workflow.RemoteWorkflowEngine;
import de.fhkoeln.cosima.workflow.YamlWorkflowDefinition;
import de.fhkoeln.cosima.workflow.storage.MapProcessStoreImpl;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Sep 25, 2008
 *
 */
public class RemoteWorkflowEngineTest {
  
  private YamlWorkflowDefinition workflowDefinition;
  private MapProcessStoreImpl processStore;

  /**
   * Test method for {@link de.fhkoeln.cosima.workflow.RemoteWorkflowEngine#execute()}.
   * @throws Exception 
   */
  @Test
  public void testExecute() throws Exception {
    try {
      workflowDefinition = new YamlWorkflowDefinition("res/workflow_definition/abstract_workflow_definition.yml");
      processStore = new MapProcessStoreImpl();
    
      RemoteWorkflowEngine engine = new RemoteWorkflowEngine();
      engine.setWorkflowDefinition(workflowDefinition);
      engine.setProcessStore(processStore);
      engine.setRegistry(new MemcachedServiceRegistry());
      
      engine.execute();
    
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
