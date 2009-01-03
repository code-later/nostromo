/*
 * FFMpegMergerTest.java
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

import static org.mockito.Mockito.*;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import de.fhkoeln.cosima.media.AbstractMedia;
import de.fhkoeln.cosima.media.MediaData;
import de.fhkoeln.santiago.components.ffmpeg.FFMpegMerger;
import de.fhkoeln.santiago.components.jmf.MediaAction;



/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Nov 21, 2008
 *
 */
public class FFMpegMergerTest {

  @Test
  public void testShouldMergeAudioAndVideo() {
    AbstractMedia audio = spy(new MediaData());
    audio.setName("L70ECT.mp3");
    doReturn(new File("file:///Users/dbreuer/Documents/Work/_FH/_Master/master_thesis/code/santiago-project/res/L70ETC.mp3")).when(audio).getPlayableData();
    
    AbstractMedia video = spy(new MediaData());
    video.setName("2nd_movie.mov");
    doReturn(new File("file:///Users/dbreuer/Documents/Work/_FH/_Master/master_thesis/code/santiago-project/res/2nd_movie.mov")).when(video).getPlayableData();
    
    AbstractMedia output = spy(new MediaData());
    output.setName("Merged_movie.mov");
    
    MediaAction merger = new FFMpegMerger(audio, video, output);
    long currentMillesecs = System.currentTimeMillis();
    merger.performAction();
    
    verify(output).setReferenceToRealData("/tmp/" + currentMillesecs + "-merged.mov");
    
    assertTrue(new File("/tmp/" + currentMillesecs + "-merged.mov").exists());
  }
  
}
