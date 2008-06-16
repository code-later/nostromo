/*
 * MediaContainer.java
 *
 * Version 1.0  Jun 16, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.media;

import java.util.ArrayList;
import java.util.List;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Jun 16, 2008
 *
 */
public class MediaContainer extends AbstractMedia {

  private List<AbstractMedia> container;
  
  /**
   * Constructor documentation comment.
   *
   * @param uri
   */
  public MediaContainer(String uri) {
    super(uri);
    this.container = new ArrayList<AbstractMedia>();
  }

  /**
   * @return
   */
  public List<AbstractMedia> getElements() {
    // TODO Auto-generated method stub
    return container;
  }

}
