/**
 * 
 */
package de.fhkoeln.cosima.media;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * This class represents any media within the COSIMA framework in an
 * abstract manner and is not an abstract class itself. The access to
 * the real media is encapsulated through the {@link MediaIO} object
 * which is associated with this abstract media object. An
 * {@link AbstractMedia} object is further described through a
 * collection of {@link Metadata} objects. Additionally every
 * {@link AbstractMedia} object must have a URI which identifies the
 * media object and let it be addressed by other components.
 * 
 * @author dbreuer
 * @version $Id: AbstractMedia.java,v1.0 2008/06/10 11:39:15 AM
 *          dbreuer Exp $
 * @generated "UML to Java
 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class AbstractMedia {

  /**
   * The map which holds the collection of {@link Metadata} objects
   * which describe the {@link AbstractMedia} instance further. The
   * map's keys are the keys of the metadata.
   */
  public Map<MetadataKeys, Metadata> metadata;

  /**
   * The string which holds the URI of this media object.
   */
  private String uri;

  /**
   * Every media object in the COSIMA Framework must have an URI. You
   * cannot change it afterwards. Additionally the constructor
   * initializes the map to store the metadata.
   * 
   * @param uri
   *          The unique URI of the media object.
   */
  public AbstractMedia(String uri) {
    setUri(uri);
    this.metadata = new HashMap<MetadataKeys, Metadata>();
  }

	/**
   * Returns a {@link Set} of all {@link MetadataKeys} objects this
   * media objects is further described by. We return a Set here,
   * because it is not allowed to have ambigious metadata description.
   * So every key can only be used once.
   * 
   * TODO This may be subject to change in further versions.
   * 
   * @return A {@link Set} of keys.
   */
	public Set<MetadataKeys> getListOfMetadataKeys() {
	  return this.metadata.keySet();
	}

	/**
   * Adds a new Metadata object to the set of metadata of this media
   * object. If there is already a metadata object with the same key
   * the old one will be replaced.
   * 
   * TODO Maybe it will be useful to return the previous metadata object in a replacement case.
   * 
   * @param metadata
   *          The metadata Object which describes this media object
   *          further.
   */
	public void addMetadata(Metadata metadata) {
	  this.metadata.put(metadata.getKey(), metadata);
	}

	/**
   * Returns a metadata object of this media object by specifying the
   * key of that metadata object. If there is no {@link Metadata}
   * object for the given key a {@link NoSuchElementException} will be
   * thrown.
   * 
   * @param key
   *          The key of the metadata object which should be
   *          retrieved.
   * @return The desired metadata object.
   * @throws NoSuchElementException
   *           If no metadata object for the given key was found.
   */
	public Metadata getMetadata(MetadataKeys key) throws NoSuchElementException {
	  if(this.metadata.containsKey(key)) {
	    return this.metadata.get(key);
	  } else {
	    throw new NoSuchElementException("This Media Object does not have any metadata for key: '" + key + "'.");
	  }
	}
	
  /**
   * Gets the URI of this media object. The URI can be used for
   * addressing the media within the framework.
   * 
   * @return The URI of this AbstractMedia Object
   */
  public String getUri() {
    return this.uri;
  }
  
  private void setMediaIO(MediaIO mediaIO) {
  
  }
  
  /**
   * Private Setter for the URI of this AbstractMedia Object. It is a
   * setter to allow encapsulation of URI pre-verification.
   * 
   * @param uri
   *          The URI to set
   */
  private void setUri(String uri) {
    this.uri = uri;
  }

}