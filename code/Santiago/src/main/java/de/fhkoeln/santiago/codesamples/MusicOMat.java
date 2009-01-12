/*
 * MusicOMat.java
 *
 * Version 1.0  Dec 27, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.codesamples;

import static org.mockito.Mockito.*;

import de.fhkoeln.cosima.codesamples.AbstractComponent;
import de.fhkoeln.cosima.media.MediaComponent;
import de.fhkoeln.cosima.media.Media;
import de.fhkoeln.santiago.components.ffmpeg.FFMpegMerger;

public class MusicOMat extends AbstractComponent {

  public static String addMusicTrackToSlideshow(String audioPath, String videoPath) {
    MediaComponent video    = mock(Media.class);
    MediaComponent audio    = mock(Media.class);
    MediaComponent outMedia = new Media();
    
    when(video.getPlayableData()).thenReturn(videoPath);    
    when(audio.getPlayableData()).thenReturn(audioPath);    
    
    FFMpegMerger merger = new FFMpegMerger(video, audio, outMedia);
    merger.performAction();
    
    return outMedia.getReferenceToRealData().toString();
  }

  protected String _execute() {
    return MusicOMat.addMusicTrackToSlideshow(getInput()[0], getInput()[1]);
  }

}
