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

import de.fhkoeln.cosima.media.AbstractMedia;
import de.fhkoeln.cosima.media.MediaData;
import de.fhkoeln.santiago.components.ffmpeg.FFMpegMerger;

public class MusicOMat extends AbstractComponent {

  public static String addMusicTrackToSlideshow(String audioPath, String videoPath) {
    AbstractMedia video    = mock(MediaData.class);
    AbstractMedia audio    = mock(MediaData.class);
    AbstractMedia outMedia = new MediaData();
    
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
