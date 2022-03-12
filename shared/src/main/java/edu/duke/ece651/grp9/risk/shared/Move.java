package edu.duke.ece651.grp9.risk.shared;

/**
 * Class to handle Move Action
 *
 * @author PROY
 * @since 11 March 2022
 */
public class Move implements Action {

  private final Player player;
  private final Territory source;
  private final Territory destination;
  private final int numUnits;
  private final RuleChecker moveChecker;


  /*
   * Constructor to create a Move
   * @param player is the Player performing the Action
   * @param source is the Territory we are moving units from
   * @param destination is the Territory we are moving units to
   * @param numUnits is the number of units we are moving from source to destination
   * @param moveChecker checks if a Move is valid
   */
  public Move (Player player, Territory source, Territory destination, int numUnits,
               RuleChecker moveChecker) {
    this.player = player;
    this.source = source;
    this.destination = destination;
    this.numUnits = numUnits;
    this.moveChecker = moveChecker;
  }

  /**
   * Perform move on source and destination Territories
   *
   * @param player is the Player performing the Action
   * @param source is the Territory we are moving units from
   * @param destination is the Territory we are moving units to
   * @param numUnits is the number of units we are moving from source to destination
   * @return String description of error if invalid move, or null if Action performed
   */
  public String performAction(Player player, Territory source, Territory destination, int numUnits) {
    /*String moveProblem = moveChecker.checkPlacement(player, source, destination, numUnits);
    if (moveProblem == null) {
      do something with the Move : add to a HashSet of moves?
      return null;
    }
    return moveProblem;*/
    source.moveUnits(destination, numUnits);

  }
}
