package edu.duke.ece651.grp9.risk.shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Player's play the game of RISC
 *
 * @author PROY
 * @since 10 March 2022
 */
public class Player implements Serializable {

  private String color;
  private HashSet<Territory> territoryList;
  private int totalUnit;
  private String loseStatus;
  private Food food;
  private Money money;
  private int playerTechLevel;

  /**
   * Constructor to create a Player
   *
   * @param color String - what color this Player is playing as
   */
  public Player(String color) {
    this.color = color;
    this.territoryList = new HashSet<Territory>();
    this.totalUnit = 30;
    this.loseStatus = "no act";
    this.food = new Food(500);
    this.money = new Money(500);
  }

  /**
   * Adds a Territory to this Player's list of Territories
   *
   * @param ter Territory being added to Player's list
   */
  public void addTerritory(Territory ter) {
    this.territoryList.add(ter);
  }

  /**
   * Removes a Territory from this Player's list of Territories
   *
   * @param ter Territory being removed to Player's list
   */
  public void removeTerritory(Territory ter) {
    this.territoryList.remove(ter);
  }

  /**
   * Getter for this Player's territoryList
   *
   * @return HashSet of all Territories owned by this Player
   */
  public HashSet<Territory> getTerritoryList() {
    return this.territoryList;
  }

  /**
   * Getter for color this Player is playing as
   *
   * @return String - color this player is playing as
   */
  public String getName() {
    return color;
  }

  /**
   * Getter for number of units this Player owns
   *
   * @return int - number of units this Player owns
   */
  public int getUnit() {
    return this.totalUnit;
  }

  /**
   * Getter for number of Territories this Player owns
   *
   * @return number of Territories this Player owns
   */
  public int getTerritoryNumber() {
    return this.territoryList.size();
  }

  /**
   * Returns the adjacency list for each Territory that this Player owns
   *
   * @return HashMap : Key is each Territory owned by Player Value is HashSet of neighbors of Key
   */
  public HashMap<Territory, HashSet<Territory>> getAdjacency() {
    HashMap<Territory, HashSet<Territory>> ans = new HashMap<>();
    for (Territory t : getTerritoryList()) {
      HashSet<Territory> tmp = new HashSet<Territory>();
      tmp = t.getNeighbors();
      ans.put(t, tmp);
    }
    return ans;
  }

  /**
   * check if the player lose the game, aka lose all territories
   *
   * @return true if the player lose the game
   */
  public boolean isLose() {
    return territoryList.isEmpty();
  }

  /**
   * Setter for lose message
   *
   * @param message lose message if Player has lost
   */
  public void setLoseStatus(String message) {
    loseStatus = message;
  }

  /**
   * Getter for this Player's lose message
   *
   * @return String lose message for this Player
   */
  public String getLoseStatus() {
    return this.loseStatus;
  }

  /**
   * Overrides the equals method to check if two Players are the same
   *
   * @param o is the input object to be compared with this
   * @return boolean indicating if the two objects are equal
   */
  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Player p = (Player) o;
      return color.equals(p.color);
    }
    return false;
  }

  /**
   * this method is to update player's resources
   * each territory can produce a certain number of resources(food and money) each round
   */
  public void updatePlayerResource() {
    for (Territory terr: territoryList) {
      food.addResource(terr.produceFood().getQuantity());
      money.addResource(terr.productMoney().getQuantity());
    }
  }

  public int getFoodQuantity() {
    return food.getQuantity();
  }

  public int getMoneyQuantity() {
    return money.getQuantity();
  }

}
