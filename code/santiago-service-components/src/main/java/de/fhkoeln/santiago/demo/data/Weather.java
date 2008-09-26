/*
 * Weather.java
 *
 * Version 1.0  Sep 20, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.demo.data;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Sep 20, 2008
 *
 */
public class Weather {
  
  private float temprature;
  private float humidity;
  private boolean rain;
  private String forecast;
  
  public float getTemprature() {
    return this.temprature;
  }
  
  public void setTemprature(float temprature) {
    this.temprature = temprature;
  }
  
  public float getHumidity() {
    return this.humidity;
  }
  
  public void setHumidity(float humidity) {
    this.humidity = humidity;
  }
  
  public boolean isRain() {
    return this.rain;
  }
  
  public void setRain(boolean rain) {
    this.rain = rain;
  }
  
  public String getForecast() {
    return this.forecast;
  }
  
  public void setForecast(String forecast) {
    this.forecast = forecast;
  }
  
}
