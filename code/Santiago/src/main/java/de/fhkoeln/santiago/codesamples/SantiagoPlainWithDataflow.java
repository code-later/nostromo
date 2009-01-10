/*
 * SantiagoPlainWithDataflow.java
 *
 * Executes the Santiago application with the help of a dedicated data flow/.
 *
 * (c) 2009 by Dirk Breuer
 */
package de.fhkoeln.santiago.codesamples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.fhkoeln.cosima.services.IODescriptor;
import de.fhkoeln.cosima.workflow.WorkflowDefinition;
import de.fhkoeln.cosima.workflow.WorkflowElement;
import de.fhkoeln.cosima.workflow.YamlWorkflowDefinition;
import de.fhkoeln.cosima.workflow.WorkflowElement.Input;

public class SantiagoPlainWithDataflow {
  
  @SuppressWarnings("unchecked")
  public static void main(String[] args) throws Exception {
    Map<String, IODescriptor> workflowStore = new HashMap<String, IODescriptor>();
    String pathToWorkflowDefinition;

    if (args.length == 1) {
      pathToWorkflowDefinition = args[0];

      WorkflowDefinition definition = new YamlWorkflowDefinition(pathToWorkflowDefinition);

      // iterate through the workflow definition elements
      Iterator<Set<WorkflowElement>> elementsIterator = definition.elementsIterator();

      while (elementsIterator.hasNext()) {
        for (WorkflowElement element : elementsIterator.next()) {

          IODescriptor input = new IODescriptor();
          
          if (element.needsInput()) {
            for (Input elementInput : element.getInput()) {
              if (elementInput.isExternal())
                input.add(elementInput.getData());
              if (elementInput.isInternal())
                input.add(workflowStore.get(elementInput.getUri()).first());
            }
          }

          Class<AbstractComponent> elementClass = (Class<AbstractComponent>) Class.forName(element.getUri());
          AbstractComponent elementComponent = elementClass.newInstance();
          
          elementComponent.setInput(input);
          IODescriptor output = elementComponent.execute();
          
          if (output != null)
            workflowStore.put(element.getOutputUri(), output);
        }
      }

    } else {
      System.err.println("Path to the workflow definition is needed!");
      System.exit(-1);
    }
  }
}