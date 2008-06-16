/**
 * 
 */
package de.fhkoeln.cosima.media;

/**
 * This is a really simple implementation of the {@link Metadata}
 * Interface. It has just a field to store the key which identifies
 * the type of metadata information and the value as a simple string.
 * The key of the Metadata can not be changed after its creation and
 * must be set while its construction process.
 * 
 * @author Dirk Breuer
 * @version $Id: CosimaMetadataImpl.java,v1.0 2008/06/10 11:32:53 AM
 *          dbreuer Exp $
 * @generated "UML to Java
 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class CosimaMetadataImpl implements Metadata {

  /**
   * The field where the key is stored.
   */
  private MetadataKeys key;

  /**
   * The field where the value is stored.
   */
  private AbstractMedia value;
	
  /**
   * One have to specify a Key for this metadata object. The Key
   * object must implement the {@link MetadataKeys} Interface.
   * 
   * @param key
   *          The {@link MetadataKeys} key object.
   */
  public CosimaMetadataImpl(MetadataKeys key) {
    this.key = key;
  }
  
  /**
   * One can set the value of the metadata object directly while its
   * creation.
   * 
   * @param key
   *          The {@link MetadataKeys} key object.
   * @param value
   *          The value String for this metadata object.
   */
  public CosimaMetadataImpl(MetadataKeys key, AbstractMedia value) {
    this.key = key;
    this.value = value;
  }

	/** 
	 * (non-Javadoc)
	 * @see Metadata#setValue(Object value)
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setValue(AbstractMedia value) {
	  this.value = value;
	}

	/** 
	 * (non-Javadoc)
	 * @see Metadata#getKey()
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public MetadataKeys getKey() {
		return this.key;
	}

	/** 
	 * (non-Javadoc)
	 * @see Metadata#getValue()
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public AbstractMedia getValue() {
		return this.value;
	}
}