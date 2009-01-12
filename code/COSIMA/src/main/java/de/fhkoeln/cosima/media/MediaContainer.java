/*
 * MediaContainer.java
 *
 * Version 1.0  Nov 16, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.media;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * This class implements the Composite part within the Composite-Pattern.
 *
 * @author dbreuer
 * @version 1.0  Nov 16, 2008
 *
 */
public class MediaContainer extends MediaComponent {

  private static final long serialVersionUID = -2032949448649491908L;

  private Map<String, MediaComponent> mediaElements;

  public MediaContainer() {
    super();
    setMediaElements(new HashMap<String, MediaComponent>());
  }
  
  /**
   * Constructor to set the metadata object on creation.
   *
   * @param metadata The metadata to set.
   */
  public MediaContainer(List<Metadata> metadatas) {
    super(metadatas);
    setMediaElements(new HashMap<String, MediaComponent>());
  }

  /**
   * @return The number of elements within this container.
   */
  public int size() {
    return this.getMediaElements().size();
  }

  /**
   * @param media
   *          The Media Object which should be added to the collection
   *          of containing elements.
   */
  public void addMedia(MediaComponent media) {
    this.getMediaElements().put(media.getName(), media);
  }

  /**
   * @param name
   *          The name of the Media Object which should be returned
   *          from the Collection of containing elements.
   * @return The Media Object with the given name
   */
  public MediaComponent getMedia(String name) {
    return getMediaElements().get(name);
  }

  /**
   * @return A Set of all the names of the containing elements.
   */
  public Set<String> elements() {
    return getMediaElements().keySet();
  }

  private void setMediaElements(Map<String, MediaComponent> mediaElements) {
    this.mediaElements = mediaElements;
  }

  private Map<String, MediaComponent> getMediaElements() {
    return mediaElements;
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.cosima.media.MediaComponent#getPlayableData()
   */
  @Override
  public Object getPlayableData() {
    // TODO Auto-generated method stub
    return null;
  }

}
