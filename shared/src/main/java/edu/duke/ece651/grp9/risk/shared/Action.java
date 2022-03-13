package edu.duke.ece651.grp9.risk.shared;

import java.io.Serializable;

/**
 * Interface to handle different types of Action's that a player can perform
 *
 * @author PROY
 * @since 10 March 2022
 */
public interface Action extends Serializable {

  /**
   * Checks chain of rules to ensure Move is valid
   * @return null if valid, if invalid a String describing error is returned
   */
  public String canPerformAction();

  /**
   * Perform action on source and destination Territories
   */
  public void performAction();
}
