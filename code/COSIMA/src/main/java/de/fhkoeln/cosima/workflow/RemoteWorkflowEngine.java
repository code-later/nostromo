/*
 * RemoteWorkflowEngine.java
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

import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import de.fhkoeln.cosima.services.CoreService;
import de.fhkoeln.cosima.services.IODescriptor;
import de.fhkoeln.cosima.services.OutputDescriptor;
import de.fhkoeln.cosima.services.registry.ServiceRegistry;
import de.fhkoeln.cosima.workflow.WorkflowElement.Input;
import de.fhkoeln.cosima.workflow.storage.ProcessStore;

/**
 * A {@link RemoteWorkflowEngine} instance runs a workflow specified by its
 * {@link WorkflowDefinition}. The single workflow elements are web
 * services which have to be invoked remotely. Their URI is specified
 * in the WorkflowDefinition. Every runner instance needs, in addition
 * to a WorkflowDefinition reference, also a reference to a
 * ProcessStore. In a ProcessStore the workflow can store the return
 * values of a component, so it can be assinged later to another
 * component.
 * 
 * @author dbreuer
 * @version 1.0 Sep 25, 2008
 */
public class RemoteWorkflowEngine extends WorkflowEngine {
  
  /**
   * The ProcessStore reference of this runner instance.
   */
  private ProcessStore processStore;
  /**
   * The Service Registry instance to query for service URIs.
   */
  private ServiceRegistry registry;

  /**
   * This is the main method of every runner. After successful
   * initialization of the runner instance the run methods starts the
   * process. Every element will be invoked as described in the
   * definition object. While working through the definition elements
   * certain the following steps are performed by the run method:
   * 
   * <ul>
   *   <li>It is asked if the element needs any input (in most of 
   *       the cases this is true)</li>
   *       <ul>
   *         <li>If yes and the input is external add the reference
   *             to the external content IODescriptor instance for
   *             the current element.</li>
   *         <li>If yes and the input is internal get the reference
   *             from the {@link ProcessStore} reference and add it
   *             to the IODescriptor instance.</li>
   *         <li>If no, which means, there is no more input required
   *             for this component, proceed with execution</li>
   *       </ul>
   *       <li>Setup the WS client.</li>
   *       <li>Set the IODescriptor instance at the remote service.</li>
   *       <li>Call the <code>execute()</code> method of the service.</li>
   *       <li>Retrieve the result which is an {@link IODescriptor}</li>
   *       <li>If the IODescriptor contains any elements, they represent
   *           the output of the service. Store the reference of the output
   *           in the ProcessStore.</li>
   *       <li>Proceed to the next element in the workflow definition</li>
   * </ul>
   * 
   * (Further information is provided in the 'RemoteWorkflowEngine
   * Flowchart.graffle' Document.
   */
  public void execute() {
    
    // iterate through the workflow definition elements
    Iterator<Set<WorkflowElement>> elementsIterator = getWorkflowDefinition().elementsIterator();
    
    while (elementsIterator.hasNext()) {
       for (WorkflowElement element : elementsIterator.next()) {
        // define a default inputDescriptor
        IODescriptor inputDescriptor = new IODescriptor();

        if (element.needsInput()) {

          for (Input elementInput : element.getInput()) {
            if (elementInput.isExternal()) {
              inputDescriptor.add(elementInput.getData());
            } else if (elementInput.isInternal()) {
              inputDescriptor.add(getStorage().getInputByKey(
                  elementInput.getUri()));
            }
          }

        } 

        IODescriptor outputDescriptor = invokeServiceForElementWithInputDescriptor(element, inputDescriptor);
        
        if (!outputDescriptor.isEmpty()) {
          getStorage().putDataForKey(outputDescriptor.first(), element.getOutputUri());
        }
      }
    }
    
    System.out.println("-> Workflow successfully executed!");
  }
  
  /**
   * @return The store which holds temporary information of the process
   */
  public ProcessStore getStorage() {
    return processStore;
  }

  /**
   * @param processStore
   *          The ProcessStore instance which should be used during
   *          the runtime of this workflow.
   */
  public void setProcessStore(ProcessStore processStore) {
    this.processStore = processStore;
  }

  /**
   * Sets the concrete Service Registry implementation. 
   * 
   * @param registry A service registry implementation.
   */
  public void setRegistry(ServiceRegistry registry) {
    this.registry = registry;
  }

  /**
   * This method encapsulated the call of the remote service. So far
   * it only can handle SOAP services. All information of connecting
   * to the service component are encapsulated in the workflow
   * element.
   * 
   * @param element
   *          The WorkflowElement of which we call its service
   * @param inputDescriptor
   *          The InputDescriptor for the service
   * @return An OutputDescriptor which holds the result of the service
   *         invocation.
   */
  @SuppressWarnings("unchecked")
  private IODescriptor invokeServiceForElementWithInputDescriptor(
      WorkflowElement element, IODescriptor inputDescriptor) {

    IODescriptor output = new OutputDescriptor();
    
    try {
      RPCServiceClient client = new RPCServiceClient();
      
      Options options = client.getOptions();
      
      String serviceUri = registry.query(element.getDescription());
      System.out.println("Setting EPR to: " + serviceUri);
      EndpointReference targetEPR = new EndpointReference(serviceUri);
      
      options.setTo(targetEPR);
      // TODO: Wenn die Operationen auf der Service Seite zu lange
      // dauern, dann gibt es einen Timeout! Asynchrone Prozessierung
      // notwendig. Dabei tritt allerdings das Problem auf, dass man
      // nicht weiss, wann die Gegenseite fertig ist. Messagaging
      // System FTW!
      System.out.println("--- Service Timeout: " + options.getTimeOutInMilliSeconds());
      options.setTimeOutInMilliSeconds(500000);

      // Setting Input Params first
      QName setInputOperation = new QName(element.getNamespace(), CoreService.SERVICE_SET_INPUT_OPERATION);
      
      Object[] setInputOperationArgs = new Object[] { inputDescriptor };
      
      client.invokeRobust(setInputOperation, setInputOperationArgs);
      
      // Run the service action and get back the output params
      QName executeOperation = new QName(element.getNamespace(), CoreService.SERVICE_EXECUTE_OPERATION);
      
      Object[] executeOperationArgs = new Object[] {};
      Class[] returnTypes = new Class[] { IODescriptor.class };
      
      Object[] response = client.invokeBlocking(executeOperation, executeOperationArgs, returnTypes);
      
      output = (IODescriptor) response[0];
    } catch (AxisFault e) {
      e.printStackTrace();
    }
    
    return output;
  }

}
