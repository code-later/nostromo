/*
 * JMFImages2Movie.java
 *
 * Version 1.0  Sep 18, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.components.jmf;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.media.Buffer;
import javax.media.ConfigureCompleteEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.DataSink;
import javax.media.EndOfMediaEvent;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoDataSinkException;
import javax.media.NoProcessorException;
import javax.media.NotConfiguredError;
import javax.media.NotRealizedError;
import javax.media.PrefetchCompleteEvent;
import javax.media.Processor;
import javax.media.RealizeCompleteEvent;
import javax.media.ResourceUnavailableEvent;
import javax.media.Time;
import javax.media.control.TrackControl;
import javax.media.datasink.DataSinkErrorEvent;
import javax.media.datasink.DataSinkEvent;
import javax.media.datasink.DataSinkListener;
import javax.media.datasink.EndOfStreamEvent;
import javax.media.format.UnsupportedFormatException;
import javax.media.format.VideoFormat;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.DataSource;
import javax.media.protocol.FileTypeDescriptor;
import javax.media.protocol.PullBufferDataSource;
import javax.media.protocol.PullBufferStream;

public class JMFImages2Movie implements ControllerListener, DataSinkListener {
  
  private static final int DEFAULT_FRAME_RATE = 2;
  private static final int DEFAULT_MOVIE_HEIGHT = 480;
  private static final int DEFAULT_MOVIE_WIDTH = 640;
  private static final String JPEG_FILTER = ".jpg";
  
  private final List<String> inputFiles;
  private final MediaLocator uriToOutput;
  
  // For the JMF Code
  private Object waitSync = new Object();
  private Object waitFileSync = new Object();
  private boolean stateTransitionOK = true;
  private boolean fileDone = false;
  private boolean fileSuccess = true;
  
  public JMFImages2Movie(String pathToImages, String pathToOutput)
    throws FileNotFoundException {
    this.uriToOutput = createMediaLocator(pathToOutput);
    inputFiles = new Vector<String>();
    inputFiles.addAll(getFilesInDirectory(pathToImages));
  }
  
  /* (non-Javadoc)
   * @see javax.media.ControllerListener#controllerUpdate(javax.media.ControllerEvent)
   */
  @Override
  public void controllerUpdate(ControllerEvent event) {
    if (event instanceof ConfigureCompleteEvent
        || event instanceof RealizeCompleteEvent
        || event instanceof PrefetchCompleteEvent) {
      synchronized (waitSync) {
        stateTransitionOK = true;
        waitSync.notifyAll();
      }
    } else if (event instanceof ResourceUnavailableEvent) {
      synchronized (waitSync) {
        stateTransitionOK = false;
        waitSync.notifyAll();
      }
    } else if (event instanceof EndOfMediaEvent) {
      event.getSourceController().stop();
      event.getSourceController().close();
    }
  }

  /* (non-Javadoc)
   * @see javax.media.datasink.DataSinkListener#dataSinkUpdate(javax.media.datasink.DataSinkEvent)
   */
  @Override
  public void dataSinkUpdate(DataSinkEvent event) {
    if (event instanceof EndOfStreamEvent) {
      synchronized (waitFileSync) {
        fileDone = true;
        waitFileSync.notifyAll();
      }
    } else if (event instanceof DataSinkErrorEvent) {
      synchronized (waitFileSync) {
        fileDone = true;
        fileSuccess = false;
        waitFileSync.notifyAll();
      }
    }
  }

  /**
   * @return the inputFiles
   */
  protected List<String> getInputFiles() {
    return this.inputFiles;
  }
  
  /**
   * @return the pathToOutput
   */
  protected MediaLocator getOutputLocator() {
    return this.uriToOutput;
  }

  public void performAction() throws NoProcessorException, IOException,
                             UnsupportedFormatException, NoDataSinkException {
    ImageDataSource dataSource = new ImageDataSource(DEFAULT_MOVIE_WIDTH,
                                                     DEFAULT_MOVIE_HEIGHT,
                                                     DEFAULT_FRAME_RATE,
                                                     inputFiles);

    Processor processor;

    System.out.println("Trying to create processor ...");
    processor = Manager.createProcessor(dataSource);
    System.out.println("Processor was successfully created!");

    processor.addControllerListener(this);

    // Put the Processor into configured state so we can set
    // some processing options on the processor.
    processor.configure();
    if (!waitForState(processor, processor.Configured))
      throw new NotConfiguredError("Processor could not be configured.");

    // Set the output content descriptor to QuickTime.
    processor
        .setContentDescriptor(new ContentDescriptor(
                                                    FileTypeDescriptor.QUICKTIME));

    // Query for the processor for supported formats.
    // Then set it on the processor.
    TrackControl control[] = processor.getTrackControls();
    Format formats[] = control[0].getSupportedFormats();
    if (formats == null || formats.length <= 0)
      throw new UnsupportedFormatException("The mux doesn't support the input format.",
                                           control[0].getFormat());
    
    control[0].setFormat(formats[0]);
    System.out.println("Setting the track format to: " + formats[0]);
    
    // We are done with programming the processor. Let's just
    // realize it.
    processor.realize();
    if (!waitForState(processor, processor.Realized))
      throw new NotRealizedError("Processor could not be configured.");
    
    // Now, we'll need to create a DataSink.
    DataSink dataSink;
    try {
      if ((dataSink = createDataSink(processor, getOutputLocator())) == null)
        throw new NoDataSinkException("Failed to create a DataSink for the given output MediaLocator.");
    } catch (Exception e) {
      throw new NoDataSinkException("Failed to create a DataSink for the given output MediaLocator.");
    }
    
    dataSink.addDataSinkListener(this);
    fileDone = false;
    
    System.out.println("Start transcoding images into movie ...");
    
    // The transcoding process starts here ...
    processor.start();
    dataSink.start();
    
    // Wait for EndOfStream event.
    waitForFileDone();
    
    // Cleanup.
    dataSink.close();
    processor.removeControllerListener(this);
    
    System.out.println("Processing done!");
  }
  
  private boolean waitForFileDone() {
    synchronized (waitFileSync) {
      try {
        while (!fileDone)
          waitFileSync.wait();
      } catch (Exception e) {
      }
    }
    return fileSuccess;
  }

  /**
   * @param processor
   * @param outputLocator
   * @return
   * @throws Exception 
   */
  private DataSink createDataSink(Processor processor,
      MediaLocator outputLocator) throws Exception {
    
    DataSource dataSource;
    
    if ((dataSource = processor.getDataOutput()) == null)
      throw new Exception("Something is really wrong: the processor does not have an output DataSource");
    
    DataSink dataSink;
    
    try {
      System.out.println("Creating DataSink for: " + outputLocator);
      dataSink = Manager.createDataSink(dataSource, outputLocator);
      dataSink.open();
    } catch (NoDataSinkException e) {
      System.out.println("Cannot create the DataSink: " + e);
      return null;
    }
    
    return dataSink;
  }

  /**
   * Scans a given directory for all files matching the
   * JPEG_FILTER Constant and adds all full pathnames of those
   * files in a List Object.
   * 
   * @param pathToDirectory
   *          The path to the directory from which the images should
   *          be fetched
   * @return A List representing all full paths to any single image
   *         inside the directory.
   */
  private List<String> getFilesInDirectory(String pathToDirectory) {
    FilenameFilter filter = new FilenameFilter() {
      public boolean accept(File dir, String name) {
        return !name.startsWith(".") && name.endsWith(JPEG_FILTER);
      }
    };
    File directory = new File(pathToDirectory);
    File[] filesInDirectory = directory.listFiles(filter);
    
    List<String> pathnames = new ArrayList<String>();
    
    System.out.println("Reading files in directory " + directory.toString() + " ...");
    for (File file : filesInDirectory) {
      pathnames.add(file.toString());
      // logging output
      System.out.println(" -> File to process: " + file.toString());
    }
    
    return pathnames;
  }
  
  /**
   * Block until the processor has transitioned to the given state.
   * Return false if the transition failed.
   */
  private boolean waitForState(Processor p, int state) {
    synchronized (waitSync) {
      try {
        while (p.getState() < state && stateTransitionOK)
          waitSync.wait();
      } catch (Exception e) {
      }
    }
    return stateTransitionOK;
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

  public class ImageDataSource extends PullBufferDataSource {
    
    ImageSourceStream streams[];
  
    public ImageDataSource(int width,
        int height, int frameRate,
        List<String> images) {
      streams = new ImageSourceStream[1];
      streams[0] = new ImageSourceStream(width, height, frameRate, images);
    }
  
    @Override
    public PullBufferStream[] getStreams() {
      return streams;
    }
  
    @Override
    public void connect() throws IOException {}
  
    @Override
    public void disconnect() {}
  
    @Override
    public String getContentType() {
      return ContentDescriptor.RAW;
    }
  
    @Override
    public Object getControl(String type) {
      return null;
    }
  
    @Override
    public Object[] getControls() {
      return new Object[0];
    }
  
    @Override
    public Time getDuration() {
      return DURATION_UNKNOWN;
    }
  
    @Override
    public void start() throws IOException {}
  
    @Override
    public void stop() throws IOException {}
    
  }

  class ImageSourceStream implements PullBufferStream {
    
    Iterator<String> images;
    int height, width;
    VideoFormat format;

    public ImageSourceStream(int width, int height, int frameRate,
        List<String> images) {
      this.width = width;
      this.height = height;
      this.images = images.listIterator();
      
      format = new VideoFormat(VideoFormat.JPEG,
                               new Dimension(width, height),
                               Format.NOT_SPECIFIED, Format.byteArray,
                               (float) frameRate);
    }

    @Override
    public Format getFormat() {
      return format;
    }

    @Override
    public void read(Buffer buffer) throws IOException {
      if (images.hasNext()) {
        String imagePath = (String) images.next();
        System.out.println("Reading image '" + imagePath + "'");
        // Open a random access File for this image
        RandomAccessFile randomAccessFile;
        randomAccessFile = new RandomAccessFile(imagePath, "r");
        
        byte data[] = null;
        
        // check the input buffer type & size
        if (buffer.getData() instanceof byte[])
          data = (byte[]) buffer.getData();
        
        // Check to see the given buffer is big enough for the frame.
        if (data == null || data.length < randomAccessFile.length()) {
          data = new byte[(int) randomAccessFile.length()];
          buffer.setData(data);
        }
        
        // Read the entire JPEG image from the file.
        randomAccessFile.readFully(data, 0, (int) randomAccessFile.length());

        System.out.println("    read " + randomAccessFile.length() + " bytes.");

        buffer.setOffset(0);
        buffer.setLength((int) randomAccessFile.length());
        buffer.setFormat(format);
        buffer.setFlags(buffer.getFlags() | buffer.FLAG_KEY_FRAME);

        // Close the random access file.
        randomAccessFile.close();
      } else {
        // We are done. Set EndOfMedia.
        System.out.println("Done reading all images.");
        buffer.setEOM(true);
        buffer.setOffset(0);
        buffer.setLength(0);
      }
    }

    @Override
    public boolean willReadBlock() {
      return false;
    }

    @Override
    public boolean endOfStream() {
      return images.hasNext();
    }

    @Override
    public ContentDescriptor getContentDescriptor() {
      return new ContentDescriptor(ContentDescriptor.RAW);
    }

    @Override
    public long getContentLength() {
      return 0;
    }

    @Override
    public Object getControl(String type) {
      return null;
    }

    @Override
    public Object[] getControls() {
      return new Object[0];
    }
    
  }
}