/**
 * 
 */
package de.fhkoeln.cosima.media;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author user
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class CosimaMetadataImpl implements Metadata {
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private MetadataKeys key;
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private String value;
	
  public CosimaMetadataImpl(MetadataKeys key) {
    this.key = key;
  }
  
  public CosimaMetadataImpl(MetadataKeys key, String value) {
    this.key = key;
    this.value = value;
  }

	/** 
	 * (non-Javadoc)
	 * @see Metadata#setValue(Object value)
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setValue(Object value) {
	  this.value = (String) value;
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
	public Object getValue() {
		return this.value;
	}
}