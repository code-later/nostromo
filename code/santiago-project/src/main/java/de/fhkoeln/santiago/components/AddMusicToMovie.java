/*
 * AddMusicToMovie.java
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

import java.io.IOException;

import javax.media.MediaException;

import de.fhkoeln.santiago.components.jmf.JMFMerger;
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
public class AddMusicToMovie extends AbstractComponent {
  
  private final String uri = "http://santiago-project.fh-koeln.de/components/AddMusicToMovie";
  
  public AddMusicToMovie(MessageQueue messageQueue, String[] inputKeys) {
    super(messageQueue, inputKeys);
    setOutput("file:///Users/dbreuer/Documents/Work/_FH/_Master/master_thesis/code/santiago-project/target/with_audio.mov");
  }
  
  public AddMusicToMovie() {
    super(null, null);
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.WorkflowElement#run()
   */
  @Override
  protected void customRun() throws MediaException {
    System.out.println("Input is:");
    System.out.println(" - " + getInput()[0]);
    System.out.println(" - " + getInput()[1]);
    
    JMFMerger functionWrapper = new JMFMerger(getInput()[0], getInput()[1], getOutput());
    try {
      functionWrapper.performAction();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (MediaException e) {
      e.printStackTrace();
      throw e;
    }
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.components.AbstractComponent#getOutputKey()
   */
  @Override
  public String getOutputKey() {
    return uri + "/output";
  }
  
  public static void main(String[] args) throws MediaException {
    AddMusicToMovie addMusicToMovie = new AddMusicToMovie();
    addMusicToMovie.setOutput("file:///Users/dbreuer/Documents/Work/_FH/_Master/master_thesis/code/santiago-project/target/with_audio.mov");
    addMusicToMovie.addInput("file:///Users/dbreuer/Documents/Work/_FH/_Master/master_thesis/code/santiago-project/target/output.mov");
//    addMusicToMovie.addInput("file:///Users/dbreuer/Documents/Work/_FH/_Master/master_thesis/code/santiago-project/target/output.mov");
    addMusicToMovie.addInput("file:///Users/dbreuer/Documents/Work/_FH/_Master/master_thesis/code/santiago-project/res/L70ETC.mp3");
    addMusicToMovie.customRun();
  }
}
