package edu.duke.ece651.grp9.risk.shared;

import java.io.Serializable;
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
  private int unit;
  private HashSet<Territory> neighbors;
  private int mockUnits;

  /**
   * Constructor to create a Territory
   * @param name String - name of the Territory
   */
  public Territory(String name) {
    this.name = name;
    this.neighbors = new HashSet<Territory>();
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

  /**
   * Checks so that no Territory is left with negative units count before Actions executed
   *
   * @return boolean value if mockUnits is at least 0
   */
  public boolean mockIsValid() {
    return mockUnits >= 0;
  }

  /**
   * Sets mockUnits to unit so that they are synced for next round of Action's check
   */
  public void syncUnits() {
    mockUnits = unit;
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

}
