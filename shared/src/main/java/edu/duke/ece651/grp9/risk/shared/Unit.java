package edu.duke.ece651.grp9.risk.shared;

import java.io.Serializable;

/**
 * Class to handle different types of Unit's that each Territory contains
 *
 * @author PROY
 * @since 26 March 2022
 */
public class Unit implements Serializable {

  private String name;
  private int numUnits;
  private int level;
  private int bonus;
  private int upgradeCost;

  /**
   * Constructor for Unit class
   *
   * @param name String description of Unit
   * @param bonus int number added to roll value
   * @param upgradeCost int cost to upgrade to the next Unit level
   */
  public Unit(String name, int level, int bonus, int upgradeCost) {
    this.name = name;
    this.numUnits = 0;
    this.level = level;
    this.bonus = bonus;
    this.upgradeCost = upgradeCost;
  }

  /**
   * Getter for count of this Unit level
   *
   * @return count for this Unit level
   */
  public int getNumUnits() {
    return numUnits;
  }

  /**
   * Setter for count of this Unit level
   *
   * @param numUnits int indicating amount of units of this Unit level
   */
  public void setNumUnits(int numUnits) {
    this.numUnits = numUnits;
  }

  /**
   * Add count to this Unit level
   *
   * @param newUnits int for number of Unit's being added
   */
  public void addUnits(int newUnits) {
    numUnits += newUnits;
  }

  /**
   * Applies this Unit's bonus to the rolled value
   *
   * @param roll number rolled by the die
   * @return roll + bonus
   */
  public int applyBonus(int roll) {
    return roll + bonus;
  }
}
