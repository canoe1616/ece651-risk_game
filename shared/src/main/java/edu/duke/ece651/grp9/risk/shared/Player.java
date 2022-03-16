package edu.duke.ece651.grp9.risk.shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

public class Player implements Serializable{
  private String color;
  private HashSet<Territory> territoryList;
  private int totalUnit;
  
  public Player(String color){
    this.color = color;
    this.territoryList = new HashSet<Territory>();
    this.totalUnit = 30;
  }
  public void addTerritory(Territory ter){
    this.territoryList.add(ter);
  }
  public void removeTerritory(Territory ter) {
    this.territoryList.remove(ter);
  }
  public HashSet<Territory> getTerritoryList(){
    return this.territoryList;
  }

  public String getName() {
    return color;
  }

  public int getUnit(){
    return this.totalUnit;
  }

  public int getTerritoryNumber(){
    return this.territoryList.size();
  }

  public HashMap<Territory, HashSet<Territory>> getAdjacency(){
    HashMap<Territory, HashSet<Territory>> ans = new HashMap<>();
    for(Territory t : getTerritoryList()){
      HashSet<Territory> tmp = new HashSet<Territory>();
      tmp = t.getNeighbors();
      ans.put(t,tmp);
    }
    return ans;
  }

  /**
   * check if the player lose the game, aka lose all territories
   * @return true if the player lose the game
   */
  public boolean isLose() {
    return territoryList.isEmpty();
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
}
