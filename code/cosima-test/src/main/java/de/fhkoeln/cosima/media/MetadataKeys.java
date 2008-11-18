/**
 * Interface to access Metadata Keys in a convinient way.
 * 
 * @author Dirk Breuer
 * @version $Id: MetadataKeys.java,v1.0 2008/06/05 4:17:11 PM dbreuer Exp $
 */
package de.fhkoeln.cosima.media;

/**
 * This interface must be implemented by all enums holding Metadata
 * keys. So we can use this interface as a reference in other methods.
 * 
 * @author Dirk Breuer
 * @version $Id: MetadataKeys.java,v1.0 2008/06/10 10:19:56 AM dbreuer Exp $
 * @generated "UML to Java
 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface MetadataKeys {
  /**
   * Returns the message of the Metadata Enum object. This is will be
   * a String which can be easily used for output.
   * 
   * @return The message of the Enum object.
   */
  public String getMessage();
}