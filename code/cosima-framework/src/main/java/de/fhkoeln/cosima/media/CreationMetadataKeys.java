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
public enum CreationMetadataKeys implements MetadataKeys {
  
  LOCATION("Location");
  
  private String message;

  private CreationMetadataKeys(String message) {
    this.message = message;
  }
  
  public String getMessage() {
    return message;
  }
  
}