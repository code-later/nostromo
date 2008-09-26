/*
 * WeatherClient.java
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

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import de.fhkoeln.santiago.demo.data.Weather;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Sep 20, 2008
 *
 */
public class WeatherClient {
  
  public static final String SERVICE_URI = "http://localhost:8080/axis2/services/WeatherService";

  public static void main(String[] args) throws AxisFault {
  
    RPCServiceClient serviceClient = new RPCServiceClient();
    
    Options options = serviceClient.getOptions();
    
    EndpointReference targetEPR = new EndpointReference(SERVICE_URI);
    
    options.setTo(targetEPR);
    
    // Setting the weather
    QName setWeaterOperation = new QName("http://services.demo.santiago.fhkoeln.de", "setWeather");
    
    Weather weather = new Weather();
    
    weather.setForecast("sunny");
    weather.setHumidity((float) 45.9);
    weather.setRain(false);
    weather.setTemprature((float) 22.6);
    
    Object[] setWeatherOperationArgs = new Object[] { weather };
    
    serviceClient.invokeRobust(setWeaterOperation, setWeatherOperationArgs);
    
    // Getting the weather
    QName getWeatherOperation = new QName("http://services.demo.santiago.fhkoeln.de", "getWeather");
    
    Object[] getWeatherOperationArgs = new Object[] {};
    
    Class[] returnTypes = new Class[] { Weather.class };
    
    Object[] response = serviceClient.invokeBlocking(getWeatherOperation, getWeatherOperationArgs, returnTypes);
    
    Weather result = (Weather) response[0];
    
    if (result == null) {
      System.err.println("Weather didn't init!");
      return;
    }
    
    System.out.println("The current Weather:");
    System.out.println("Forecast:         " + result.getForecast());
    System.out.println("Temprature:       " + result.getTemprature());
    System.out.println("Humidity:         " + result.getHumidity());
    System.out.println("Raining?:         " + result.isRain());
  }

}
