package edu.duke.ece651.grp9.risk.shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

public class Player implements Serializable{
  private char color;
  private HashSet<Territory> territoryList;
  
  public Player(char color){
    this.color = color;
    this.territoryList = new HashSet<Territory>();
  }
  public void addTerritory(Territory ter){
    this.territoryList.add(ter);
  }
  public HashSet<Territory> getTerritoryList(){
    return this.territoryList;
  }

  public char getName() {
    return color;
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
}
