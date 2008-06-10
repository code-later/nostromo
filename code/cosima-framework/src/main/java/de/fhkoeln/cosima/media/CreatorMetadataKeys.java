/**
 * 
 */
package de.fhkoeln.cosima.media;

/**
 * This Enum Type holds the keys for identifying metadata abour the
 * creator of the media object.
 * 
 * @author dbreuer
 * @version $Id: CreatorMetadataKeys.java,v1.0 2008/06/10 11:29:51 AM
 *          dbreuer Exp $
 * @generated "UML to Java
 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public enum CreatorMetadataKeys implements MetadataKeys {
  
  AUTHOR("Author");
  
  /**
   * The value of the current constant in the Enum object.
   */
  private String message;

  /**
   * The private constructor to store the value of the enum constant
   * in an attribute.
   * 
   * @param message
   *          The message of the enum object.
   */
  private CreatorMetadataKeys(String message) {
    this.message = message;
  }
   
  /* (non-Javadoc)
   * @see de.fhkoeln.cosima.media.MetadataKeys#getMessage()
   */
  public String getMessage() {
    return message;
  }
}