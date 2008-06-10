/**
 * Keys for referencing Metadata which are associated with the Media itself.
 * 
 * @author dbreuer
 * @version $Id: MediaMetadataKeys.java,v1.0 2008/month/day 10:41:20 AM dbreuer Exp $
 */
package de.fhkoeln.cosima.media;

/**
 * This Enum holds Keys which are used to store information about the
 * Media itself. For instance if the media is a video one can use the
 * {@link MediaMetadataKeys#VIDEO} Key to store information about the
 * video itself, such as encoding and length.
 * 
 * This is highly work in
 * progress and the current implementation is just for prototyping
 * use.
 * 
 * @author dbreuer
 * @version $Id: MediaMetadataKeys.java,v1.0 2008/06/10 10:24:23 AM
 *          dbreuer Exp $
 * @generated "UML to Java
 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public enum MediaMetadataKeys implements MetadataKeys {

  VIDEO("Video"),
  AUDIO("Audio"),
  TEXT("Text"),
  IMAGE("Image");

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
  private MediaMetadataKeys(String message) {
    this.message = message;
  }

  /* (non-Javadoc)
   * @see de.fhkoeln.cosima.media.MetadataKeys#getMessage()
   */
  public String getMessage() {
    return message;
  }

}