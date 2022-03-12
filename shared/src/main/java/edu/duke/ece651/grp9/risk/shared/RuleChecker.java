package edu.duke.ece651.grp9.risk.shared;

/**
 * This abstract class helps check is an Action is valid in a chain of rules
 *
 * @author PROY
 * @since 10 March 2022
 */
public abstract class RuleChecker {
  private final RuleChecker next;

  /**
   * Adds the next move rule to our list
   *
   * @param next move rule in list
   */
  public RuleChecker(RuleChecker next) {
    this.next = next;
  }

  /**
   * Recursively checks if chain of rules are valid
   *
   * @param player is the Player performing the Action
   * @param source is the Territory we are moving units from
   * @param destination is the Territory we are moving units to
   * @param numUnits is the number of units we are moving from source to desination
   * @return String description of error if invalid move, or null if okay
   */
  public String checkAction(Player player, Territory source, Territory destination, int numUnits) {
    //if we fail our own rule: stop the move is not legal
    String actionProblem = checkMyRule(player, source, destination, numUnits);
    if (actionProblem != null) {
      return actionProblem;
    }
    //otherwise, ask the rest of the chain.
    if (next != null) {
      return next.checkAction(player, source, destination, numUnits);
    }
    //if there are no more rules, then the action is legal
    return null;
  }

  /**
   * Checks if this rule is valid
   *
   * @param player is the Player performing the Action
   * @param source is the Territory we are moving units from
   * @param destination is the Territory we are moving units to
   * @param numUnits is the number of units we are moving from source to desination
   * @return String description of error if invalid move, or null if okay
   */
  protected abstract String checkMyRule(Player player, Territory source, Territory destination, int numUnits);
}


