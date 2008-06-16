/**
 * 
 */
package de.fhkoeln.cosima.media;

public interface MediaIO {
  public MediaLocator getMediaLocator();
  
  public void setMediaLocator(MediaLocator mediaLocator);
  
  public MediaControls getMediaControls();
}