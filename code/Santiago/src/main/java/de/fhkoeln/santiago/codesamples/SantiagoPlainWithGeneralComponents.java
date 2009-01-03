/*
 * SantiagoPlainWithGeneralComponents.java
 *
 * This little more advanced version of the SantiagoPlain Application
 * uses generalized components for its media processing.
 *
 * (c) 2009 by Dirk Breuer
 */
package de.fhkoeln.santiago.codesamples;

public class SantiagoPlainWithGeneralComponents {
  
  public static void main(String[] args) {
    String imagePath, musicPath;
    String slideshow, slideshowWithMusic;

    if (args.length == 2) {
      imagePath = args[0];
      musicPath = args[1];
      
      // Generate the slideshow from the images in imagePath
      AbstractComponent slideshowGenerator = new SlideshowGenerator();
      slideshowGenerator.setInput(new String[] {imagePath});
      slideshow = slideshowGenerator.execute();

      //Add Music to the slideshow
      AbstractComponent musicOMat = new MusicOMat();
      musicOMat.setInput(new String[] {musicPath, slideshow});
      slideshowWithMusic = musicOMat.execute();
      
      // Play the slideshow
      AbstractComponent videoPlayer = new VideoPlayer();
      videoPlayer.setInput(new String[] {slideshowWithMusic});
      videoPlayer.execute();

    } else {
      System.err.println("Paths to the images and the music track are needed!");
      System.exit(-1);
    }
  }

}
