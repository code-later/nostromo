/*
 * SantiagoPlain.java
 *
 * Simple Application to create an Image slide show with a music track
 * attached and play the resulting movie. The program takes two
 * arguments: The path to the images which should be created as
 * slide show and the path to the music track.
 *
 * (c) 2008 by Dirk Breuer
 */
package de.fhkoeln.santiago.codesamples;

public class SantiagoPlain {

  public static void main(String[] args) {
    String imagePath, musicPath;
    String slideshow, slideshowWithMusic;

    if (args.length == 2) {
      imagePath = args[0];
      musicPath = args[1];

      slideshow = SlideshowGenerator.generateSlideshowForImagesInPath(imagePath);
      slideshowWithMusic = MusicOMat.addMusicTrackToSlideshow(musicPath, slideshow);
      
      VideoPlayer.playMovieFile(slideshowWithMusic);

    } else {
      System.err.println("Paths to the images and the music track are needed!");
      System.exit(-1);
    }
  }
}
