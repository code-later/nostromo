/*
 * CreateMovieFromImagesClient.java
 *
 * Version 1.0  Sep 20, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.demo.clients;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import de.fhkoeln.santiago.services.IODescriptor;

/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Sep 20, 2008
 *
 */
public class CreateMovieFromImagesClient {
  
  public static final String VERSION_URI = "http://localhost:8080/axis2/services/Version";
  public static final String SERVICE_URI = "http://localhost:8080/axis2/services/CreateMovieFromImagesService";

  public static void main(String[] args) {
    
    // First of all check if we can get any connection
    pingServiceProvider();

    // Set the input files
    List inputFiles = new ArrayList<String>();
    inputFiles.add("/Users/dbreuer/Documents/Work/_FH/_Master/master_thesis/code/santiago-project/res/");
    IODescriptor input = new  IODescriptor();
    input.setDescriptorElements(new String[] {"/Users/dbreuer/Documents/Work/_FH/_Master/master_thesis/code/santiago-project/res/"});
    
//    192.168.19.1
//    192.168.92.1

    try {
      RPCServiceClient serviceClient = new RPCServiceClient();
      
      Options options = serviceClient.getOptions();
      
      EndpointReference targetEPR = new EndpointReference(SERVICE_URI);
      
      options.setTo(targetEPR);
      
      // Setting Input Params first
      QName setInputOperation = new QName("http://services.demo.santiago.fhkoeln.de", "setInput");
      
      Object[] setInputOperationArgs = new Object[] { input };
      
      serviceClient.invokeRobust(setInputOperation, setInputOperationArgs);
      
      // Run the service action and get back the output params
      QName executeOperation = new QName("http://services.demo.santiago.fhkoeln.de", "execute");
      
      Object[] executeOperationArgs = new Object[] {};
      Class[] returnTypes = new Class[] { IODescriptor.class };
      
      Object[] response = serviceClient.invokeBlocking(executeOperation, executeOperationArgs, returnTypes);
      
      IODescriptor result = (IODescriptor) response[0];
      
      System.out.println("Result: " + result.first());
    } catch(AxisFault af) {
      System.err.println("Something went wrong on the remote site!");
      af.printStackTrace();
    }
    
  }

  private static void pingServiceProvider() {
    
    try {
      RPCServiceClient client = new RPCServiceClient();
      Options options = client.getOptions();
      EndpointReference targetEPR = new EndpointReference(VERSION_URI);
      options.setTo(targetEPR);
      
      QName getVersonOperation = new QName("http://axisversion.sample", "getVersion");
      
      Object[] args = new Object[] {};
      Class[] returnTypes = new Class[] { String.class };
      Object[] responses = client.invokeBlocking(getVersonOperation, args, returnTypes);
      
      System.out.println(responses[0]);
    } catch (AxisFault e) {
      e.printStackTrace();
    }
    
  }

}
