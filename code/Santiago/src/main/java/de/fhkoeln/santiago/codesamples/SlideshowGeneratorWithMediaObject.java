/*
 * SlideshowGeneratorWithMediaObject.java
 *
 * Version 1.0  Jan 10, 2009
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2009 by dbreuer
 */
package de.fhkoeln.santiago.codesamples;

import java.io.FileNotFoundException;
import java.net.URI;

import de.fhkoeln.cosima.media.AbstractMedia;
import de.fhkoeln.cosima.media.MediaData;
import de.fhkoeln.cosima.services.IODescriptor;
import de.fhkoeln.cosima.util.Logger;
import de.fhkoeln.santiago.components.jmf.JMFImages2Movie;

public class SlideshowGeneratorWithMediaObject extends AbstractComponent {

  @Override
  protected IODescriptor _execute() {
    IODescriptor output;
    
    try {
      String tempOutputFileName = "file:///tmp/output.mov";
      
      output = new IODescriptor();
      
      AbstractMedia outputMedia = new MediaData();
      outputMedia.setName("Slidehow");
      
      mediaAction = new JMFImages2Movie(getInput().first(), tempOutputFileName);
      mediaAction.performAction();

      outputMedia.setReferenceToRealData(tempOutputFileName);
      
      URI mediaUri = getBroker().store(outputMedia);
      output.add(mediaUri.toString());
      
      return output;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    
    return null;
  }
}