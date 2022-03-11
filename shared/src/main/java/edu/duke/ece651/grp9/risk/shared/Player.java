package edu.duke.ece651.grp9.risk.shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

public class Player implements Serializable{
  private String color;
  private HashSet<Territory> territoryList;
  
  public Player(String color){
    this.color = color;
    this.territoryList = new HashSet<Territory>();
  }
  public void addTerritory(Territory ter){
    this.territoryList.add(ter);
  }
  public HashSet<Territory> getTerritoryList(){
    return this.territoryList;
  }

  public String getName() {
    return color;
  }
  public HashMap<Territory, HashSet<String>> getAdjacency(){
    HashMap<Territory, HashSet<String>> ans = new HashMap<>();
    for(Territory t : getTerritoryList()){
      HashSet<String> tmp = new HashSet<String>();
      tmp = t.getNeighbors();
      ans.put(t,tmp);
    }
    return ans;
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
      return color == p.color;
    }
    return false;
  }
}
