package edu.duke.ece651.grp9.risk.shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Territories are connected to other Territories and make up a Map
 *
 * @author PROY
 * @since 10 March 2022
 */
public class Territory implements Serializable {

  private String name;
  private Player owner;
  private HashMap<Integer, Unit> units;
  private int unit;
  private HashSet<Territory> neighbors;
  private int mockUnits;
  private int size;

  /**
   * Constructor to create a Territory
   * @param name String - name of the Territory
   */
  public Territory(String name) {
    this.name = name;
    this.neighbors = new HashSet<Territory>();
    //Add factory to create HashMap of units
    this.units = new HashMap<>();
    units.put(0, new Level0Unit());
    units.put(1, new Level1Unit());
    units.put(2, new Level2Unit());
    units.put(3, new Level3Unit());
    units.put(4, new Level4Unit());
    units.put(5, new Level5Unit());
    units.put(6, new Level6Unit());
    // size of territory default as 10
    this.size = 10;
  }

  /**
   * Setter for unit
   * @param unit int - number of units in Territory
   */
  public void setUnit(int unit) {
    this.unit = unit;
    syncUnits();
  }

  /**
   * Setter for owner
   * @param owner Player who owns this Territory
   */
  public void setOwner(Player owner) {
    this.owner = owner;
  }

  /**
   * Getter for owner
   * @return Player who owns this Territory
   */
  public Player getOwner() {
    return this.owner;
  }

  /**
   *
   * @param ter
   */
  public void addNeighbors(Territory ter) {
    this.neighbors.add(ter);
    ter.neighbors.add(this);
  }

  /**
   * Getter for neighbors of this Territory
   * @return HashSet of this Territory's neighbors
   */
  public HashSet<Territory> getNeighbors() {
    return this.neighbors;
  }

  /**
   * Getter for name of this Territory
   * @return String name of this territory
   */
  public String getName() {
    return name;
  }

  /**
   * Getter for unit
   * @return int units in this Territory
   */
  public int getUnit() {
    return unit;
  }

  /**
   * Move units from this Territory to destination Territory
   *
   * @param destination Territory that we are moving units to
   * @param numUnits number of units being moved
   */
  public void moveUnits(Territory destination, int numUnits) {
    this.unit -= numUnits;
    destination.unit += numUnits;

    syncUnits();
    destination.syncUnits();
  }

  //EVOLUTION 2
  /**
   * Getter for units
   *
   * @param unitLevel int level we want number for
   * @return int units in this Territory and this unitLevel
   */
  public int getUnits(int unitLevel) {
    return units.get(unitLevel).getNumUnits();
  }

  /**
   * Setter for Level 0 Units
   *
   * @param numUnits int number of Unit's of level 0
   */
  public void setUnits(int numUnits) {
    units.get(0).setNumUnits(numUnits);
  }

  /**
   * Setter for all level of Units
   *
   * @param numUnits int number of Unit's of level 0
   * @param level int Unit level
   */
  public void setUnits(int numUnits, int level) {
    units.get(level).setNumUnits(numUnits);
  }

  public Unit getUnitClass(int level) {
    return units.get(level);
  }

  //EVOLUTION 2
  /**
   * Move Units from this Territory to destination Territory
   *
   * @param destination Territory that we are moving units to
   * @param numUnits number of units being moved
   * @param unitLevel int level being moved between Territories
   */
  public void moveUnits(Territory destination, int numUnits, int unitLevel) {
    this.units.get(unitLevel).addUnits(-numUnits);
    destination.units.get(unitLevel).addUnits(numUnits);
  }

  /**
   * Checks if this Unit can be upgraded to the next Unit
   *
   * @param beginLevel which Unit level to upgrade
   * @param endLevel what Unit level to upgrade to
   * @param numUnits number of units to upgrade
   * @return null if upgrade is allowed, String description if upgrade is invalid
   */
  public String canUpgradeUnits(int beginLevel, int endLevel, int numUnits) {
    if (endLevel > owner.getTechLevel()) {
      return "Invalid upgrade: Must upgrade tech level before upgrading Unit level.";
    }

    if (beginLevel >= endLevel) {
      return "Invalid upgrade: You can only increase a Unit's level.";
    }

    /*if ((targetLevel.upgradeCost - this.upgradeCost) * numUnits > player.getMoney()) {
      return "Invalid upgrade: You do not have enough Money to upgrade this Unit";
    }*/
    //TODO Need to add checking for money resources - mock Money again?

    return null;
  }

  //EVOLUTION 2
  /**
   * Upgrade Unit's using Money Resource
   *
   * @param numUnits number of units to upgrade
   * @param beginLevel which Unit level to upgrade
   * @param endLevel what Unit level to upgrade to
   */
  public void upgradeUnits(int numUnits, int startLevel, int endLevel) {
    //TODO Need to add rule checking and compute cost of action
    units.get(startLevel).addUnits(-numUnits);
    units.get(endLevel).addUnits(numUnits);
  }

  /**
   * Mock the Actions (Move and Attack). Updates mockUnits to ensure every Territory does not 0 or
   * more units after Player executes all set of moves
   *
   * @param unitMovement number of units moving into destination Territory
   */
  public void mockActions(Territory destination, int unitMovement) {
    this.mockUnits -= unitMovement;
    destination.mockUnits += unitMovement;
  }

  //EVOLUTION 2
  /**
   * Mock the Actions (Move and Attack). Updates mockUnits to ensure every Territory does not 0 or
   * more units after Player executes all set of moves
   *
   * @param unitMovement number of units moving into destination Territory
   */
  public void mockActions(Territory destination, int unitMovement, int unitLevel) {
    units.get(unitLevel).mockAction(-unitMovement);
    //If an upgrade action these units are not available for another Territory
    if (!this.equals(destination)) {// && !destination.getOwner().equals(owner)) {
      destination.units.get(unitLevel).mockAction(unitMovement);
    }
  }

  /**
   * Checks so that no Territory is left with negative units count before Actions executed
   *
   * @return boolean value if mockUnits is at least 0
   */
  public boolean mockIsValid() {
    for (Unit unit : units.values()) {
      if (unit.getMockUnits() < 0) {
        return false;
      }
    }
    return true;
  }

  /**
   * Sets mockUnits to unit so that they are synced for next round of Action's check
   */
  public void syncUnits() {
    mockUnits = unit;
  }

  /**
   * Sets this level Unit's mockUnits equal to this level Unit's units so
   * that they are synced for next round of Action's check
   */
  public void syncUnits(int level) {
    units.get(level).syncUnits();
  }

  /**
   * Overrides the equals method to check if two Territories are the same
   *
   * @param o is the input object to be compared with this
   * @return boolean indicating if the two objects are equal
   */
  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Territory t = (Territory) o;
      return name.equalsIgnoreCase(t.name);
    }
    return false;
  }

  /**
   * Adds one unit to this Territory - used at the end of each round
   */
  public void addUnit() {
    unit++;
    syncUnits();
  }

  //EVOLUTION 2
  /**
   * Adds one level 0 to this Territory - used at the end of each round
   */
  public void addUnit(int level) {
    units.get(level).addUnits(1);
  }

  /**
   * each territory can produce 100 Food after each game round
   * @return Food that values 100
   */
  public Food produceFood() {
    return new Food(100);
  }

  /**
   * each territory can produce 50 Money after each game round
   * @return Food that values 50
   */
  public Money productMoney() {
    return new Money(50);
  }


  public HashMap<Integer, Unit> getAllUnits() {
    return units;
  }
}
