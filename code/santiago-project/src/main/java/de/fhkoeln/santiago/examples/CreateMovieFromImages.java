/*
 * CreateMovieFromImages.java
 *
 * Version 1.0  Jul 3, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.santiago.examples;


/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Jul 3, 2008
 *
 */
public class CreateMovieFromImages implements WorkflowElement {

  /* (non-Javadoc)
   * @see de.fhkoeln.santiago.examples.WorkflowElement#run()
   */
  @Override
  public void run() {
    System.out.println(this.getClass() + " is doing Stuff ...");
  }

}
