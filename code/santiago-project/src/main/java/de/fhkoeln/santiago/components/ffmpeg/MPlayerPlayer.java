/*
 * MPlayerPlayer.java
 *
 * Version 1.0  Nov 21, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.components.ffmpeg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import de.fhkoeln.santiago.components.jmf.MediaAction;
import de.fhkoeln.santiago.media.AbstractMedia;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Nov 21, 2008
 *
 */
public class MPlayerPlayer implements MediaAction {

  private final AbstractMedia video;
  
  private Process mplayer;

  public MPlayerPlayer(AbstractMedia video) {
    this.video = video;
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.components.jmf.MediaAction#performAction()
   */
  public void performAction() {
    List<String> command = new ArrayList<String>();
    command.add("mplayer");
    command.add(video.getUri());
    
    try {
      ProcessBuilder pBuilder = new ProcessBuilder(command);
      mplayer = pBuilder.start();

      // Output
      InputStream is = mplayer.getErrorStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      String line;

      System.out.printf("Output of running %s is:\n\n", command.get(0)); 

      while ((line = br.readLine()) != null)
        System.out.println(line);
        
      mplayer.waitFor();
      System.out.printf("\nCommand %s exited with status: %d\n\n", command.get(0), mplayer.exitValue());
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

}
