/*
 * SimpleWorkflowEngineWithIO.java
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import de.fhkoeln.cosima.components.AbstractComponent;
import de.fhkoeln.cosima.services.IODescriptor;
import de.fhkoeln.cosima.workflow.WorkflowElement.Input;

/**
 * A simple implementation of a {@link WorkflowEngine} which uses {@link IODescriptor} objects.
 * This is only used for demonstration.
 *
 * @author Dirk Breuer
 * @version 1.0  Jan 19, 2009
 *
 */
public class SimpleWorkflowEngineWithIO extends WorkflowEngine {
  
  public SimpleWorkflowEngineWithIO() {
    setWorkflowStore(new HashMap<String, String>());
  }

  @SuppressWarnings("unchecked")
  public void execute() throws Exception {
    // iterate through the workflow definition elements
    Iterator<Set<WorkflowElement>> elementsIterator = getWorkflowDefinition().elementsIterator();

    while (elementsIterator.hasNext()) {
      for (WorkflowElement element : elementsIterator.next()) {

        IODescriptor input = new IODescriptor();

        if (element.needsInput()) {
          for (Input elementInput : element.getInput()) {
            if (elementInput.isExternal())
              input.add(elementInput.getData());
            if (elementInput.isInternal())
              input.add(getWorkflowStore().get(elementInput.getUri()));
          }
        }

        Class<AbstractComponent> elementClass = (Class<AbstractComponent>) Class.forName(element.getUri());
        AbstractComponent elementComponent = elementClass.newInstance();

        elementComponent.setInput(input);
        IODescriptor output = elementComponent.execute();

        if (output != null)
          getWorkflowStore().put(element.getOutputUri(), output.first());
      }
    }
  }
}
