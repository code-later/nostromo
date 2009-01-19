/*
 * AbstractComponent.java
 *
 * A simple abstraction of the single media processing components.
 *
 * (c) 2009 by Dirk Breuer
 */
package de.fhkoeln.cosima.codesamples;

/**
 * This class is just for didactic reasons and is only useful for the Santiago
 * application codesamples. The real AbstractComponent class can be found here
 * {@link de.fhkoeln.cosima.components.AbstractComponent}.
 * 
 * @author Dirk Breuer
 * @version 1.0 Jan 19, 2009
 */
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