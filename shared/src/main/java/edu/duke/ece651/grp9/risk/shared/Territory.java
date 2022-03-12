package edu.duke.ece651.grp9.risk.shared;
import java.io.Serializable;
import java.util.HashSet;

public class Territory implements Serializable{
  private String name;
  private int unit;
  private Player owner;
  private HashSet<Territory> neighbors;
  //Constructor
  public Territory(String name){
    this.name = name;
    this.neighbors = new HashSet<Territory>();
  }

  public void setUnit(int unit){
    this.unit = unit;
  }

  public void setOwner(Player owner){
    this.owner = owner;
  }

  public Player getOwner(){
    return this.owner;
  }
  
  public void addNeighbors(Territory ter){
    this.neighbors.add(ter);
  }

  public HashSet<Territory> getNeighbors(){
    return this.neighbors;
  }

  public String getName(){
    return name;
  }

  public int getUnit() {
    return unit;
  }

  /**
   * Move units from this Territory to destination Territory
   */
  public void moveUnits(Territory destination, int numUnits) {
    
  }

}
