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
   * Performs mockActions to make sure Territories have enough units after all moves are made
   *
   * @param player is the Player performing the Action
   * @param source is the Territory we are moving units from
   * @param destination is the Territory we are moving units to
   * @param numUnits is the number of units we are moving from source to destination
   * @param unitLevel is the Unit level we are moving
   * @return String description of error if invalid move, or null if okay
   */
  @Override
  protected String checkMyRule(Player player, Territory source, Territory destination, int numUnits, int unitLevel) {
    if (numUnits < 0) {
      return "This action is invalid: number of units must be positive int.";
    }

    source.mockActions(destination, numUnits);
    //TODO perform mock actions for each unitLevel?
    return null;
  }
}

