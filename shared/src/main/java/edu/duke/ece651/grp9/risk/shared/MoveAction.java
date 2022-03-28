package edu.duke.ece651.grp9.risk.shared;

/**
 * Class to handle Move Action
 *
 * @author PROY
 * @since 11 March 2022
 */
public class MoveAction implements Action {

  private final Player player;
  private final Territory source;
  private final Territory destination;
  private final int numUnits;
  private final RuleChecker moveChecker;
  private int unitLevel;

  /**
   * Constructor to create a Move
   * @param player is the Player performing the Action
   * @param source is the Territory we are moving units from
   * @param destination is the Territory we are moving units to
   * @param numUnits is the number of units we are moving from source to destination
   */
  public MoveAction(Player player, Territory source, Territory destination, int numUnits) {
    this.player = player;
    this.source = source;
    this.destination = destination;
    this.numUnits = numUnits;
    source.syncUnits();
    destination.syncUnits();
    this.moveChecker = new UnitsRuleChecker(new OwnerRuleChecker(new MoveRuleChecker(null)));
    this.unitLevel = 0;
  }

  /**
   * Constructor to create a Move
   * @param player is the Player performing the Action
   * @param source is the Territory we are moving units from
   * @param destination is the Territory we are moving units to
   * @param numUnits is the number of units we are moving from source to destination
   * @param unitLevel is the Unit level we are moving
   */
  public MoveAction(Player player, Territory source, Territory destination, int numUnits, int unitLevel) {
    this(player, source, destination, numUnits);
    this.unitLevel = unitLevel;
  }

  /**
   * Getter for Player
   *
   * @return Player who is making Action
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Checks chain of rules to ensure Move is valid
   *
   * @return null if valid, if invalid a String describing error is returned
   */
  public String canPerformAction() {
    return moveChecker.checkAction(player, source, destination, numUnits, unitLevel);
  }

  /**
   * Perform move on source and destination Territories
   */
  public void performAction() {
    source.moveUnits(destination, numUnits);
    source.moveUnits(destination, numUnits, unitLevel); //EVOLUTION 2
  }
}
