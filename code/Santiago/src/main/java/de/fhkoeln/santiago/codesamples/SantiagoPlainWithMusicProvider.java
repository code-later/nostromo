/*
 * SantiagoPlainWithMusicProvider.java
 *
 * Executes the Santiago application as usual, but with the music
 * track provided through a dedicated producer component.
 *
 * (c) 2009 by Dirk Breuer
 */
package de.fhkoeln.santiago.codesamples;

import de.fhkoeln.cosima.codesamples.AbstractComponent;

public class SantiagoPlainWithMusicProvider {

  public static void main(String[] args) {
    String imagePath, musicPath;
    String slideshow, slideshowWithMusic;

    if (args.length == 2) {
      imagePath = args[0];
      musicPath = args[1];

      // Make the music file known within the system borders
      AbstractComponent musicProvider = new MusicProvider();
      musicProvider.setInput(new String[] { musicPath });
      String music = musicProvider.execute();

      // Generate the slideshow from the images in imagePath
      AbstractComponent slideshowGenerator = new SlideshowGenerator();
      slideshowGenerator.setInput(new String[] { imagePath });
      slideshow = slideshowGenerator.execute();

      // Add Music to the slideshow
      AbstractComponent musicOMat = new MusicOMat();
      musicOMat.setInput(new String[] { music, slideshow });
      slideshowWithMusic = musicOMat.execute();

      // Play the slideshow
      AbstractComponent videoPlayer = new VideoPlayer();
      videoPlayer.setInput(new String[] { slideshowWithMusic });
      videoPlayer.execute();

    } else {
      System.err.println("Paths to the images and the music track are needed!");
      System.exit(-1);
    }
  }

}
