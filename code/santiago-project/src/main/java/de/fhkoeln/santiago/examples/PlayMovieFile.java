/*
 * PlayMovieFile.java
 *
 * Version 1.0  Jul 3, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.examples;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.media.ControllerClosedEvent;
import javax.media.ControllerErrorEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.RealizeCompleteEvent;
import javax.media.Time;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Jul 3, 2008
 *
 */
public class PlayMovieFile implements WorkflowElement {
  
  class JMFWrapperFunction implements ControllerListener {

    // Media Player
    private Player player = null;
      
    // For GUI
    // component in which video is playing
    private Component visualComponent;
    // controls gain, position, start, stop
    private Component controlComponent;
    private JFrame frame;
    private JPanel panel;

    // For setup
    private final MediaLocator videoMRL;
    private boolean firstTime = true;
    private int controlPanelHeight = 0;
    private int videoWidth = 0;
    private int videoHeight = 0;
    
    
    public JMFWrapperFunction(String pathToVideo) throws NoPlayerException, IOException {
      this.videoMRL = createMediaLocator(pathToVideo);
      setupPlayer();
    }
    
    public void startPlayer() {
      if (player != null)
        player.start();
    }
    
    public void stopPlayer() {
      if (player != null) {
        player.stop();
        player.deallocate();
      }
    }

    public void destroyPlayer() {
      player.close();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.media.ControllerListener#controllerUpdate(javax.media.ControllerEvent)
     */
    @Override
    public synchronized void controllerUpdate(ControllerEvent event) {
      if (player == null)
        return;

      if (event instanceof RealizeCompleteEvent) {
        int width = 640;
        int height = 0;
        if (visualComponent == null)
          if ((visualComponent = player.getVisualComponent()) != null) {
            Dimension videoSize = visualComponent.getPreferredSize();
            videoWidth = videoSize.width;
            videoHeight = videoSize.height;
            width = videoWidth;
            height += videoHeight;
            visualComponent.setBounds(0, 0, videoWidth, videoHeight);
          }

        if (controlComponent == null)
          if ((controlComponent = player
              .getControlPanelComponent()) != null) {
            
            controlPanelHeight = controlComponent.getPreferredSize().height;
            height += controlPanelHeight;
          }

        if (controlComponent != null) {
          controlComponent.setBounds(0, videoHeight, width, controlPanelHeight);
          controlComponent.invalidate();
        }
        System.out.println("Width: " + width);
        System.out.println("Heigth: " + height);
        setupGUI(width, height);

      } else if (event instanceof EndOfMediaEvent) {
        // We've reached the end of the media; rewind and
        // start over
        player.setMediaTime(new Time(0));
        player.start();
      } else if (event instanceof ControllerErrorEvent) {
        // Tell TypicalPlayerApplet.start() to call it a day
        player = null;
      } else if (event instanceof ControllerClosedEvent) {
        panel.removeAll();
      }

    }
    
    
    private MediaLocator createMediaLocator(String uri) throws FileNotFoundException {
      MediaLocator locator = null;
      
      if (uri.indexOf(":") > 0)
        locator = new MediaLocator(uri);
      
      if (uri.startsWith(File.separator))
        locator = new MediaLocator("file:" + uri);
      
      if (locator == null) {
        throw new FileNotFoundException("The output file could not be realized with URI " + uri);
      } else {
        return locator;
      }
    }

    private void setupGUI(int width, int height) {
      JFrame.setDefaultLookAndFeelDecorated(true);
      
      // Setup the frame
      frame = new JFrame("Demo Player");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // Setup the panel
      panel = new JPanel(new BorderLayout());
      panel.add(visualComponent, BorderLayout.PAGE_START);
      panel.add(controlComponent, BorderLayout.PAGE_END);
      
      // Add all Components to the frame and pack it
      frame.getContentPane().add(panel);
      frame.pack();
      
      // make the frame visible
      frame.setVisible(true);
      // and set the size
      frame.setSize(width, height);
    }
    
    private void setupPlayer() throws NoPlayerException, IOException {
      player = Manager.createPlayer(videoMRL);
      player.addControllerListener(this);
    }
    
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.examples.WorkflowElement#run()
   */
  @Override
  public void run() {
    String pathToVideo = "file:///Users/dbreuer/Documents/Work/_FH/_Master/master_thesis/code/santiago-project/target/with_audio.mov";
    try {
      JMFWrapperFunction wrapperFunction = new JMFWrapperFunction(pathToVideo);
      wrapperFunction.startPlayer();
      Thread.currentThread().sleep(18000);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (NoPlayerException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}