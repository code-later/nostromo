/*
 * Workflow.java
 *
 * Version 1.0  Mar 4, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.core;

import java.util.Iterator;
import java.util.List;

import de.fhkoeln.cosima.registry.ServiceRepository;

/**
 * Documentation comment without implementation details.
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Mar 4, 2008
 *
 */
public class Workflow {

  private LifecycleManager lifecycleManager;

  /**
   * Constructor documentation comment.
   *
   */
  public Workflow() {
    lifecycleManager = new LifecycleManager();
  }

  public void readWorkflowDefinition(List<String> workflowDefinition) {

    ServiceRepository registry = ServiceRepository.getInstance();

    Iterator<String> workflowDefIter = workflowDefinition.iterator();

    while (workflowDefIter.hasNext()) {
      String[] wfdef = workflowDefIter.next().split(":");

      ServiceDefinition serviceDefinition = new ServiceDefinition(wfdef[0], wfdef[1]);

      registry.registerService(wfdef[2], ComponentFactory.createService(serviceDefinition));
    }
  }

  public void runWorkflow() {
    this.lifecycleManager.start();
  }

}
