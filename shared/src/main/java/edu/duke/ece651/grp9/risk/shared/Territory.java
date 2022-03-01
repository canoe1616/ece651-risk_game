package edu.duke.ece651.grp9.risk.shared;
import java.io.Serializable;
import java.util.HashSet;

public class Territory implements Serializable{
  private String name;
  private int unit;
  private Player owner;
  private HashSet<Territory> neighbors;
  
  public Territory(String name, int unit, Player owner){
    this.name = name;
    this.unit = unit;
    this.owner = owner;
    this.neighbors = new HashSet<Territory>();
  }

  public void addNeighbors(Territory ter){
    this.neighbors.add(ter);
  }

  public String getName(){
    return name;
  }
}
