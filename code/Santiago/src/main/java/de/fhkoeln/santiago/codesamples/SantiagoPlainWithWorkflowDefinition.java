/*
 * SantiagoPlainWithWorkflowDefinition.java
 *
 * Executes the Santiago application based on a declarative definition
 * file in YAML format.
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

import de.fhkoeln.cosima.workflow.WorkflowDefinition;
import de.fhkoeln.cosima.workflow.WorkflowElement;
import de.fhkoeln.cosima.workflow.YamlWorkflowDefinition;
import de.fhkoeln.cosima.workflow.WorkflowElement.Input;

public class SantiagoPlainWithWorkflowDefinition {

  @SuppressWarnings("unchecked")
  public static void main(String[] args) throws Exception {
    Map<String, String> workflowStore = new HashMap<String, String>();
    String pathToWorkflowDefinition;

    if (args.length == 1) {
      pathToWorkflowDefinition = args[0];

      WorkflowDefinition definition = new YamlWorkflowDefinition(pathToWorkflowDefinition);

      // iterate through the workflow definition elements
      Iterator<Set<WorkflowElement>> elementsIterator = definition.elementsIterator();

      while (elementsIterator.hasNext()) {
        for (WorkflowElement element : elementsIterator.next()) {

          List<String> inputList = new ArrayList<String>();
          
          if (element.needsInput()) {
            for (Input elementInput : element.getInput()) {
              if (elementInput.isExternal())
                inputList.add(elementInput.getData());
              if (elementInput.isInternal())
                inputList.add(workflowStore.get(elementInput.getUri()));
            }
          }

          Class<AbstractComponent> elementClass = (Class<AbstractComponent>) Class.forName(element.getUri());
          AbstractComponent elementComponent = elementClass.newInstance();
          
          elementComponent.setInput(inputList.toArray(new String[] {}));
          String output = elementComponent.execute();
          
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
