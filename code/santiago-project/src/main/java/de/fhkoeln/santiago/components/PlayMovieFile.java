/*
 * PlayMovieFile.java
 *
 * Version 1.0  Jul 3, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.components;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.media.NoPlayerException;

import de.fhkoeln.santiago.components.jmf.JMFPlayer;
import de.fhkoeln.santiago.messaging.MessageQueue;



/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Jul 3, 2008
 *
 */
public class PlayMovieFile extends AbstractComponent {
  
  private final String uri = "http://santiago-project.fh-koeln.de/components/PlayMovieFile";
  
  public PlayMovieFile(MessageQueue messageQueue, String[] inputKeys) {
    super(messageQueue, inputKeys);
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.components.AbstractComponent#customRun()
   */
  @Override
  protected void customRun() {
//    String pathToVideo = "file:///Users/dbreuer/Documents/Work/_FH/_Master/master_thesis/code/santiago-project/target/with_audio.mov";
    try {
      JMFPlayer wrapperFunction = new JMFPlayer(getInput()[0]);
      wrapperFunction.startPlayer();
      Thread.currentThread().sleep(18000);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (NoPlayerException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
  public String getOutputKey() {
    return this.uri + "/output";
  }
}