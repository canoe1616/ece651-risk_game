package edu.duke.ece651.grp9.risk.shared;

/**
 * This class makes sure a Territory has enough units in it for the Action
 *
 * @author PROY
 * @since 10 March 2022
 */
public class UnitsRuleChecker extends RuleChecker {
 
  /**
   * Adds the next Action rule to our list
   *
   * @param next Action rule in list
   */
  public UnitsRuleChecker(RuleChecker next) {
    super(next);
  }
  
  /**
   * Checks if source Territory has enough units in it
   *
   * @param player is the Player performing the Action
   * @param source is the Territory we are moving units from
   * @param destination is the Territory we are moving units to
   * @param numUnits is the number of units we are moving from source to desination
   * @return String description of error if invalid move, or null if okay
   */
  @Override
  protected String checkMyRule(Player player, Territory source, Territory destination, int numUnits) {
    if (source.getUnit() < numUnits) {
      return "This action is invalid: " + source.getName() + " does not have enough units.";
    }
    return null;
  }
}
