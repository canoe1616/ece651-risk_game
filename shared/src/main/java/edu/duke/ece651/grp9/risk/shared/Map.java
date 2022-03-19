package edu.duke.ece651.grp9.risk.shared;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;

/**
 * The class is to define a map for a game.
 */

public class Map implements Serializable{
  private HashSet<Territory> territoryList;
  private HashSet<Player> players;
  private int player_num;

  /*
   * Constructor to create map.
   * A map contains a hashset of territories and a set of players
   */
  public Map(){
    this.territoryList = new HashSet<Territory>();
    this.players = new HashSet<Player>();
  }

  /**
   * this method is to set the total player numbers
   * @param n is the given player numbers
   */
  public void setPlayerNum(int n){
    this.player_num = n;
  }

  /**
   * this method is to get the total player numbers
   * @return the player numbers
   */
  public int getPlayerNum(){
    return this.player_num;
  }

  /**
   * this method is to get all the map's territories
   * @return a hashset of territories
   */
  public HashSet<Territory> getList() {
    return territoryList;
  }

  /**
   * this method is to get all players
   * @return a hashset of players
   */
  public HashSet<Player> getPlayer() {
    return players;
  }

  /**
   * given a player, this method is to add a player to the player list
   * @param p is the given player
   */
  public void addPlayer(Player p){
    players.add(p);
  }

  /**
   * given player, this method is to remove a player from the player list
   * @param p is the given player
   */
  public void removePlayer(Player p) {
    players.remove(p);
  }

  /**
   * given a territory, this method is to add an territory to the territory list
   * @param ter is the given territory
   */
  public void addTerritory(Territory ter){
    territoryList.add(ter);
  }

  /**
   * given a color, this method is to find a player corresponding to the color
   * @param color is the given color, eg: "red", "blue"
   * @return the Player
   */
  public Player findPlayer(String color){
    for (Player p: players){
      if (p.getName().equalsIgnoreCase(color))
        return p;
    }
    return null;
  }

  /**
   * given a name, this method is to find territory
   * @param name is the territory's name
   * @return a territory
   */
  public Territory findTerritory(String name){
    for (Territory t: territoryList){
      if (t.getName().equalsIgnoreCase(name))
        return t;
    }
    return null;
  }

  /**
   * This method gets the game winner.
   * If a player controls all territories, then she wins the game
   * @return the winner if some player wins the game, null if no one wins the game
   */
  public Player getGameWinner() {
    for (Player p: players) {
      if (p.getTerritoryList().size() == territoryList.size()) {
        return p;
      }
    }
    return null;
  }
}
