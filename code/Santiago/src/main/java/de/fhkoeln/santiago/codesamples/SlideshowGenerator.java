/*
 * SlideshowGenerator.java
 *
 * Version 1.0  Dec 27, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.codesamples;

import java.io.FileNotFoundException;

import de.fhkoeln.santiago.components.jmf.JMFImages2Movie;

public class SlideshowGenerator extends AbstractComponent {

  public static String generateSlideshowForImagesInPath(String imagePath) {
    String out = "/tmp/tmp_movie.mov";
    
    try {
      JMFImages2Movie jmfCreator = new JMFImages2Movie(imagePath, out);
      jmfCreator.performAction();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return out;
  }

  protected String _execute() {
    return SlideshowGenerator.generateSlideshowForImagesInPath(getInput()[0]);
  }

}
