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


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Sep 20, 2008
 *
 */
public class WeatherService {
  
  Weather weather;
  
  public Weather getWeather() {
    return this.weather;
  }
  
  public void setWeather(Weather weather) {
    this.weather = weather;
  }
  
}
