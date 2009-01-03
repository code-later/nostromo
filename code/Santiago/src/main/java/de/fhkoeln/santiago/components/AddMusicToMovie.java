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

import javax.media.MediaException;

import de.fhkoeln.cosima.messaging.MessageQueue;
import de.fhkoeln.santiago.components.jmf.JMFMerger;
import de.fhkoeln.santiago.components.jmf.MediaAction;


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
   * @see de.fhkoeln.cosima.WorkflowElement#run()
   */
  
  protected void customRun() {
    System.out.println("Input is:");
    System.out.println(" - " + getInput()[0]);
    System.out.println(" - " + getInput()[1]);
    
    MediaAction action = new JMFMerger(getInput()[0], getInput()[1], getOutput());
    action.performAction();
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.cosima.components.AbstractComponent#getOutputKey()
   */
  
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
