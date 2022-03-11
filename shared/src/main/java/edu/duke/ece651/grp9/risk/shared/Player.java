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
}
