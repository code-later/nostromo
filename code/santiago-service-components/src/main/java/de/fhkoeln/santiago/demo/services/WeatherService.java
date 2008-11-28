/*
 * WeatherService.java
 *
 * Version 1.0  Sep 20, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.demo.services;

import de.fhkoeln.santiago.demo.data.Weather;
import de.fhkoeln.santiago.demo.util.Logger;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Sep 20, 2008
 *
 */
public class WeatherService extends AbstractWeatherService {
  
  Weather weather;
  
  public Weather getWeather() {
    Logger.info("getWeather Action");
    return this.weather;
  }
  
  public void setWeather(Weather weather) {
    this.weather = weather;
  }
  
  public void say(String msg) {
    Logger.info("Said: " + msg);
  }
  
}
