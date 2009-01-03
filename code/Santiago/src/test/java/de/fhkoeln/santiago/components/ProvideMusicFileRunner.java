/*
 * ProvideMusicFileRunner.java
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

import de.fhkoeln.cosima.messaging.JMSMessageQueue;
import de.fhkoeln.santiago.components.ProvideMusicFile;

public class ProvideMusicFileRunner {

  public static void main(String[] args) {
    AbstractComponent component =
        new ProvideMusicFile(
                            new JMSMessageQueue(),
                            new String[] {
                                          "http://santiago-project.fh-koeln.de/components/ProvideMusicFile/input" });
    component.run();
  }

}
