package de.fhkoeln.santiago.components;

import de.fhkoeln.santiago.messaging.JMSMessageQueue;

public class PlayMovieFileRunner {

  public static void main(String[] args) {
    AbstractComponent component =
        new PlayMovieFile(
                            new JMSMessageQueue(),
                            new String[] {
                                          "http://santiago-project.fh-koeln.de/components/AddMusicToMovie/output" });
    component.run();
  }

}
