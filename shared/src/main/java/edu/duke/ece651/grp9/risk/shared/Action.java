package edu.duke.ece651.grp9.risk.shared;

/**
 * Interface to handle different types of Action's that a player can perform
 *
 * @author PROY
 * @since 10 March 2022
 */
public interface class Action {

  /**
   * Perform action on source and destination Territories
   *
   * @param source is the Territory we are moving units from
   * @param destination is the Territory we are moving units to
   * @param numUnits is the number of units we are moving from source to desination
   * @return String description of error if invalid move, or null if Action performed
   */
  public String performAction(Territory source, Territory destination, int numUnits);
}
