/*
 * SimpleWorkflowEngine.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.fhkoeln.cosima.components.AbstractComponent;
import de.fhkoeln.cosima.workflow.WorkflowElement.Input;

public class SimpleWorkflowEngine extends WorkflowEngine {

  public SimpleWorkflowEngine() {
    setWorkflowStore(new HashMap<String, String>());
  }
  
  @Override
  @SuppressWarnings("unchecked")
  public void execute() throws Exception {
    // iterate through the workflow definition elements
    Iterator<Set<WorkflowElement>> elementsIterator = getWorkflowDefinition().elementsIterator();

    while (elementsIterator.hasNext()) {
      for (WorkflowElement element : elementsIterator.next()) {

        List<String> inputList = new ArrayList<String>();

        if (element.needsInput()) {
          for (Input elementInput : element.getInput()) {
            if (elementInput.isExternal())
              inputList.add(elementInput.getData());
            if (elementInput.isInternal())
              inputList.add(getWorkflowStore().get(elementInput.getUri()));
          }
        }

        Class<AbstractComponent> elementClass = (Class<AbstractComponent>) Class.forName(element.getUri());
        AbstractComponent elementComponent = elementClass.newInstance();

        elementComponent.setInput(inputList.toArray(new String[] {}));
        String output = elementComponent.execute();

        if (output != null)
          getWorkflowStore().put(element.getOutputUri(), output);
      }
    }
  }
}
