/*
 * MPlayerPlayerTest.java
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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.fhkoeln.santiago.components.jmf.MediaAction;
import de.fhkoeln.santiago.media.AbstractMedia;
import de.fhkoeln.santiago.media.MediaData;



/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Nov 21, 2008
 *
 */
public class MPlayerPlayerTest {

  @Test
  public void testShouldPlayVideoViaMPlayer() {
    AbstractMedia video = new MediaData();
    video.setName("2nd_movie.mov");
    video.setUri("file:///Users/dbreuer/Documents/Work/_FH/_Master/master_thesis/code/santiago-project/res/2nd_movie.mov");
    
    MediaAction player = new MPlayerPlayer(video);
    player.performAction();
    assertTrue(true);
  }
}
