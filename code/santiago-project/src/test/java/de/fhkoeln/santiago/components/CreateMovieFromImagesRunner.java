/*
 * CreateMovieFromImagesRunner.java
 *
 * Version 1.0  Sep 15, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.components;

import de.fhkoeln.santiago.messaging.JMSMessageQueue;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Sep 15, 2008
 *
 */
public class CreateMovieFromImagesRunner {

  public static void main(String[] args) {
    AbstractComponent component =
        new CreateMovieFromImages(
                            new JMSMessageQueue(),
                            new String[] {
                                          "http://santiago-project.fh-koeln.de/components/CreateMovieFromImages/input"});
    component.run();
  }

}
