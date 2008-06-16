/**
 * Interface for Metadata Objects.
 * 
 * @author Dirk Breuer
 * @version $Id: Metadata.java,v1.0 2008/06/05 5:35:07 PM dbreuer Exp $
 */
package de.fhkoeln.cosima.media;

/**
 * Interface for specifing a generic metadata object within the
 * framework. The implementing class have to set the key of this
 * metadata object in its constructor.
 * 
 * @author Dirk Breuer
 * @version $Id: Metadata.java,v1.0 2008/06/10 10:23:03 AM dbreuer Exp $
 * @generated "UML to Java
 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface Metadata {

  /**
   * This method will set the value of the metadata object. The
   * metadata value is itself just another AbstractMedia object. Due
   * to this the metadata of the media object can be as simple as a
   * single line of text or as complex as a MPEG-4 video stream.
   * 
   * @param value
   *          The value to be set.
   * @generated "UML to Java
   *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
   */
  public void setValue(AbstractMedia value);

  /**
   * This method will return the key of the metadata object which has
   * been used.
   * 
   * @return The Enum Key of this metadata object.
   * @generated "UML to Java
   *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
   */
  public MetadataKeys getKey();

  /**
   * This method will return the value of the this metadata object.
   * 
   * @return The stored value of the metadata object.
   * @generated "UML to Java
   *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
   */
  public AbstractMedia getValue();
}