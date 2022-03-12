package edu.duke.ece651.grp9.risk.shared;

/**
 * This class makes sure the source Territory is adjacent to destination Territory
 *
 * @author PROY
 * @since 10 March 2022
 */
public class AttackRuleChecker extends RuleChecker {

  /**
   * Adds the next Action rule to our list
   *
   * @param next Action rule in list
   */
  public AttackRuleChecker(RuleChecker next) {
    super(next);
  }

  /**
   * Checks if source Territory is adjacent to destination Territory
   *
   * @param player is the Player performing the Action
   * @param source is the Territory we are moving units from
   * @param destination is the Territory we are moving units to
   * @param numUnits is the number of units we are moving from source to desination
   * @return String description of error if invalid move, or null if okay
   */
  @Override
  protected String checkMyRule(Player player, Territory source, Territory destination, int numUnits) {
    if (!source.getNeighbors().contains(destination)) {
      return "This action is invalid: " + source.getName() + " is not adjacent to " + destination.getName() + ".";
    } else if (destination.getOwner().equals(player)) {
      return "This action is invalid: you cannot attack your own Territory.";
    }
    return null;
  }
}

