package de.fhkoeln.santiago.codesamples;

import static org.mockito.Mockito.*;

import de.fhkoeln.cosima.media.MediaComponent;
import de.fhkoeln.cosima.media.Media;
import de.fhkoeln.santiago.components.ffmpeg.MPlayerPlayer;

public class VideoPlayer extends AbstractComponent {

  public static void playMovieFile(String pathToVideo) {
    MediaComponent video    = mock(Media.class);
    when(video.getPlayableData()).thenReturn(pathToVideo);
    
    MPlayerPlayer player = new MPlayerPlayer(video);
    player.performAction();
  }

  protected String _execute() {
    VideoPlayer.playMovieFile(getInput()[0]);
    return null;
  }

}
