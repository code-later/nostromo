/*
 * FileSystemStore.java
 *
 * Version 1.0  Dec 23, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.media.mediabroker.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import de.fhkoeln.cosima.media.AbstractMedia;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Dec 23, 2008
 *
 */
public class FileSystemStore implements MediaStore {

  private File location;

  public FileSystemStore() {
    this.location = new File("/tmp");
  }
  
  /* (non-Javadoc)
   * @see de.fhkoeln.cosima.media.mediabroker.storage.MediaStore#read(java.lang.String)
   */
  public Object read(String key) {
    File storedFile = new File(this.location.getAbsolutePath(), key);
    return storedFile.exists() ? storedFile : null;
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.cosima.media.mediabroker.storage.MediaStore#write(java.lang.String, de.fhkoeln.cosima.media.AbstractMedia)
   */
  public String write(AbstractMedia data) throws IOException {
    File outputFile = new File(this.location.getAbsolutePath(), data.storageKey());

    System.out.println("--- Start writing to path " + outputFile.getAbsolutePath() + " ...");

    File sourceFile;
    
    try {
      sourceFile = new File(data.getReferenceToRealData());
    } catch (IllegalArgumentException e) {
      sourceFile = new File(data.getReferenceToRealData().toString());
    }
    
    FileInputStream in = null;
    FileOutputStream out = null;
    try {
      // If the sourceFile is null we get a FileNotFoundException. This is
      // useful, if the data instance has no reference data.
      in = new FileInputStream(sourceFile);
      out = new FileOutputStream(outputFile.getAbsolutePath());
      int currentByte;

      while ((currentByte = in.read()) != -1) {
        out.write(currentByte);
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (in != null)
        in.close();
      if (out != null)
        out.close();
    }

    System.out.println("--- Finished!");
    
    return data.storageKey();
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.cosima.media.mediabroker.storage.MediaStore#setStoreLocation(java.lang.String)
   */
  public void setStoreLocation(String location) {
    this.location = new File(location);
  }

}
