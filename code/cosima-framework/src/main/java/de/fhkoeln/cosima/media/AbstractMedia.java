/**
 * 
 */
package de.fhkoeln.cosima.media;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author user
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class AbstractMedia {

  public Map<MetadataKeys, Metadata> metadata;

  private String uri;

  public AbstractMedia(String uri) {
    setUri(uri);
    this.metadata = new HashMap<MetadataKeys, Metadata>();
  }

	public Set<MetadataKeys> getListOfMetadataKeys() {
	  return this.metadata.keySet();
	}

	public void addMetadata(Metadata metadata) {
	  this.metadata.put(metadata.getKey(), metadata);
	}

	public Metadata getMetadata(MetadataKeys key) {
	  if(this.metadata.containsKey(key)) {
	    return this.metadata.get(key);
	  } else {
	    throw new NoSuchElementException("This Media Object does not have any metadata for key: '" + key + "'.");
	  }
	}
	
  /**
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
   * @param uri - The URI to set
   */
  private void setUri(String uri) {
    this.uri = uri;
  }

}