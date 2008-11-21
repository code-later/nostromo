/*
 * FFMpegMerger.java
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
public class FFMpegMerger implements MediaAction {

  
  private final AbstractMedia outputMedia;
  private final AbstractMedia mediaOne;
  private final AbstractMedia mediaTwo;
  
  private Process ffmpeg;

  public FFMpegMerger(AbstractMedia mediaOne, AbstractMedia mediaTwo, AbstractMedia outputMedia) {
    this.mediaOne = mediaOne;
    this.mediaTwo = mediaTwo;
    this.outputMedia = outputMedia;
  }
  
  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.components.jmf.MediaAction#performAction()
   */
  public void performAction() {
    List<String> command = new ArrayList<String>();
    command.add("ffmpeg");
    command.add("-i");
    command.add(mediaOne.getUri());
    command.add("-i");
    command.add(mediaTwo.getUri());
    command.add("-s");
    command.add("vga");
    command.add("-aspect");
    command.add("1.6");
    command.add("-vcodec");
    command.add("mjpeg");
    command.add("-acodec");
    command.add("pcm_alaw");
    command.add("-y");
    command.add(outputMedia.getUri());
    
//    command.add("mplayer");
//    command.add("/tmp/Merged_movie.mov");

    try {
      ProcessBuilder pBuilder = new ProcessBuilder(command);
      ffmpeg = pBuilder.start();

      // Output
      InputStream is = ffmpeg.getErrorStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      String line;

      System.out.printf("Output of running %s is:\n\n", command.get(0)); 

      while ((line = br.readLine()) != null)
        System.out.println(line);
        
      ffmpeg.waitFor();
      System.out.printf("\nCommand %s exited with status: %d\n\n", command.get(0), ffmpeg.exitValue());
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
  }

}
