package edu.duke.ece651.grp9.risk.shared;

/**
 * Class to handle Upgrade Action
 *
 * @author PROY
 * @since 29 March 2022
 */
public class UpgradeAction implements Action {

  private final Player player;
  private final Territory source;
  private final Territory destination;
  private final int numUnits;
  private final RuleChecker moveChecker;
  private int startLevel;
  private int endLevel;

  /**
   * Constructor to create a Move
   * @param player is the Player performing the Action
   * @param source is the Territory we are moving units from
   * @param destination is the Territory we are moving units to
   * @param numUnits is the number of units we are moving from source to destination
   */
  public UpgradeAction(Player player, Territory source, Territory destination, int numUnits) {
    this.player = player;
    this.source = source;
    this.destination = destination;
    this.numUnits = numUnits;
    this.moveChecker = new UnitsRuleChecker(new OwnerRuleChecker(new UpgradeRuleChecker(null)));
    this.startLevel = 0;
    this.endLevel = 0;
    source.syncUnits();
    destination.syncUnits();
  }

  /**
   * Constructor to create an Upgrade
   * @param player is the Player performing the Action
   * @param source is the Territory we are upgrading Units in
   * @param numUnits is the number of units we are upgrading
   * @param startLevel is the Unit level we are upgrading
   * @param endLevel is the Unit level we are upgrading to
   */
  public UpgradeAction(Player player, Territory source, int numUnits, int startLevel, int endLevel) {
    this(player, source, source, numUnits);
    this.startLevel = startLevel;
    this.endLevel = endLevel;
    source.syncUnits(startLevel);
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
    return moveChecker.checkAction(player, source, destination, numUnits, startLevel);
  }

  /**
   * Perform move on source and destination Territories
   */
  public void performAction() {
    source.moveUnits(destination, -numUnits, startLevel); //EVOLUTION 2
    source.moveUnits(destination, numUnits, endLevel);
  }

  public int computeCost() {
    //return (source.getCost(endLevel) - source.getCost(startLevel)) * numUnits;
    return 0;
  }
}
