/*
 * AbstractComponent.java
 *
 * A simple abstraction of the single media processing components.
 *
 * (c) 2009 by Dirk Breuer
 */
package de.fhkoeln.cosima.codesamples;

public abstract class AbstractComponent {

  private String[] inputs;
  
  public final String execute() {
    return _execute();
  }

  protected abstract String _execute();
  
  public void setInput(String[] inputs) {
    this.inputs = inputs;
  }
  
  protected String[] getInput() {
    return this.inputs;
  }
}