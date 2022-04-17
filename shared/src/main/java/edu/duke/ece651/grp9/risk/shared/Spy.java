package edu.duke.ece651.grp9.risk.shared;

import java.io.Serializable;

/**
 * Class for Spy Unit
 *
 * @author PROY
 * @since 26 March 2022
 */
public class Spy implements Serializable {
  //private Player owner;
  private int numUnits;
  private int upgradeCost;
  //private int numUnits;
  //private Territory location;

  public Spy () {
    this(1);
  }

  public Spy(int numUnits) {
    this.upgradeCost = 20;
    this.numUnits = numUnits;

    //this.location = location;
    //this.owner = owner;
  }

  /**
   * Getter for number of Units
   *
   * @return int number of units
   */
  public int getNumUnits() {
    return numUnits;
  }

  /**
   * Move spy between Territories
   *
   * @param destination Territory Spy is moving to
   * @return String to indicate if move is allowed
   */
  //TODO incorporate cost of move, only allow 1 move per round
  //TODO can move more than 1 within own Territory
//  public String move(Territory destination) {
//    if (location.getNeighbors().contains(destination)) {
//      location = destination;
//      return null;
//    }
//    return "Invalid move: Spy can only move one Territory at a time.";
//  }

  /**
   * Getter for this Spy's location
   *
   * @return Territory to indicate this Spies location
   */
//  public Territory getLocation() {
//    return location;
//  }

  /**
   * Add units to this Spy instance
   * @param addedUnits number of units to add
   */
  public void addUnits(int addedUnits) {
    numUnits += addedUnits;
  }
}
