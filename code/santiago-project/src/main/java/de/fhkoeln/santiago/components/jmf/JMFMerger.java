/*
 * JMFMerger.java
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

import java.io.IOException;
import java.util.Vector;

import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.DataSink;
import javax.media.EndOfMediaEvent;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaException;
import javax.media.MediaLocator;
import javax.media.Processor;
import javax.media.ProcessorModel;
import javax.media.datasink.DataSinkErrorEvent;
import javax.media.datasink.DataSinkEvent;
import javax.media.datasink.DataSinkListener;
import javax.media.datasink.EndOfStreamEvent;
import javax.media.format.AudioFormat;
import javax.media.format.VideoFormat;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.DataSource;
import javax.media.protocol.FileTypeDescriptor;

public class JMFMerger implements ControllerListener, DataSinkListener {
  
  private final Vector sourceURIs = new Vector(1);
  private final String outputPath;
  
  private String videoEncoding = "JPEG";
  private String audioEncoding = "LINEAR";
  private String outputType = FileTypeDescriptor.QUICKTIME;
  
  private DataSource merger;
  private DataSource outputDataSource;
  private DataSource[] dataOutputs;
  
  private DataSink outputDataSink;
  
  private Processor outputProcessor;
  private Processor[] processors;
  private ProcessorModel outputModel;
  
  private MediaLocator outputLocator;
  
  private VideoFormat videoFormat;
  private AudioFormat audioFormat;

  private boolean done = false;

  public JMFMerger(String pathToMovie, String pathToAudio,
      String outputPath) {
    this.outputPath = outputPath;
    sourceURIs.add(pathToMovie);
    sourceURIs.add(pathToAudio);
  }

  @Override
  public void controllerUpdate(ControllerEvent event) {
    if (event instanceof EndOfMediaEvent) {
      synchronized (this) {
        outputProcessor.close();
        outputProcessor = null;
      }
    }
  }

  @Override
  public void dataSinkUpdate(DataSinkEvent event) {
    if (event instanceof EndOfStreamEvent) {
      done = true;
    } else if (event instanceof DataSinkErrorEvent) {
      done = true;
    }
  }
  
  public void performAction() throws IOException, MediaException {
    processors = new Processor[sourceURIs.size()];
    dataOutputs = new DataSource[sourceURIs.size()];

    for (int i = 0; i < sourceURIs.size(); i++) {
      String sourceURI = (String) sourceURIs.get(i);
      MediaLocator locator = new MediaLocator(sourceURI);
      ProcessorModel processorModel = new MergeProcessorModel(locator);
      processors[i] = Manager.createRealizedProcessor(processorModel);
      dataOutputs[i] = processors[i].getDataOutput();
      processors[i].start();
    }

    merger = Manager.createMergingDataSource(dataOutputs);
    merger.connect();
    merger.start();

    if (merger == null)
      throw new MediaException("Failed to merge data sources.");

    ProcessorModel outputProcessorModel = new OutputProcessorModel(merger);

    outputProcessor = Manager.createRealizedProcessor(outputProcessorModel);
    outputDataSource = outputProcessor.getDataOutput();

    outputLocator = new MediaLocator(outputPath);
    outputDataSink = Manager.createDataSink(outputDataSource, outputLocator);
    outputDataSink.open();

    outputProcessor.addControllerListener(this);
    outputDataSink.addDataSinkListener(this);
    System.out.println("Merging...");

    outputDataSink.start();
    outputProcessor.start();

    int count = 0;

    while (!done) {
      try {
        Thread.currentThread().sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      if (outputProcessor != null
          && (int) (outputProcessor.getMediaTime().getSeconds()) > count) {
        System.out.print(".");
        count = (int) (outputProcessor.getMediaTime().getSeconds());
      }

    }

    if (outputDataSink != null) {
      outputDataSink.close();
    }
    
    synchronized (this) {
      if (outputProcessor != null) {
        outputProcessor.close();
      }
    }

    System.out.println("Added Audio to Movie file!");
  }
  
  class MergeProcessorModel extends ProcessorModel {

    private MediaLocator inputLocator;
    
    public MergeProcessorModel(MediaLocator locator) {
      this.inputLocator = locator;
    }

    public ContentDescriptor getContentDescriptor() {
      return new ContentDescriptor(ContentDescriptor.RAW);
    }

    public DataSource getInputDataSource() {
      return null;
    }

    public MediaLocator getInputLocator() {
      return inputLocator;
    }

    public Format getOutputTrackFormat(int index) {
      return null;
    }

    public int getTrackCount(int n) {
      return n;
    }

    public boolean isFormatAcceptable(int index, Format format) {
      if (videoFormat == null) {
        videoFormat = new VideoFormat(videoEncoding);
      }
      if (audioFormat == null) {
        audioFormat = new AudioFormat(audioEncoding);
      }
      if (format.matches(videoFormat) || format.matches(audioFormat))
        return true;
      else
        return false;
    }
  }
  
  class OutputProcessorModel extends ProcessorModel {
    
    private final DataSource inputDataSource;

    public OutputProcessorModel(DataSource inputDataSource) {
      this.inputDataSource = merger;
    }
    
    public ContentDescriptor getContentDescriptor() {
      return new FileTypeDescriptor(outputType);
    }

    public DataSource getInputDataSource() {
      return inputDataSource;
    }

    public MediaLocator getInputLocator() {
      return null;
    }

    public Format getOutputTrackFormat(int index) {
      return null;
    }

    public int getTrackCount(int n) {
      return n;
    }

    public boolean isFormatAcceptable(int index, Format format) {
      if (videoFormat == null) {
        videoFormat = new VideoFormat(videoEncoding);
      }
      if (audioFormat == null) {
        audioFormat = new AudioFormat(audioEncoding);
      }
      if (format.matches(videoFormat) || format.matches(audioFormat))
        return true;
      else
        return false;
    }
  }
}