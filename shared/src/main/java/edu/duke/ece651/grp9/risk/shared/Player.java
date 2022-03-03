package edu.duke.ece651.grp9.risk.shared;

import java.io.Serializable;
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
}
