/*
 * MediaAction.java
 *
 * Version 1.0  Sep 19, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.components.jmf;

import java.io.IOException;

import javax.media.NoDataSinkException;
import javax.media.NoProcessorException;
import javax.media.format.UnsupportedFormatException;

/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Sep 19, 2008
 *
 */
public interface MediaAction {

  public abstract void performAction();

}