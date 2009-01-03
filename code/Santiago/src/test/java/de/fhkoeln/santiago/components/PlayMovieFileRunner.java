package de.fhkoeln.santiago.components;

import de.fhkoeln.cosima.messaging.JMSMessageQueue;
import de.fhkoeln.santiago.components.PlayMovieFile;

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
