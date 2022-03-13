package edu.duke.ece651.grp9.risk.shared;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

public class Territory implements Serializable{
  private String name;
  private Player owner;
  private int unit;
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
    ter.neighbors.add(this);
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
   *
   * @param destination Territory that we are moving units to
   * @param numUnits number of units being moved
   */
  public void moveUnits(Territory destination, int numUnits) {
    this.unit -= numUnits;
    destination.unit += numUnits;
  }

  /** Overrides the equals method to check if two Territories are the same
   *
   * @param o is the input object to be compared with this
   * @return boolean indicating if the two objects are equal
   */
  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Territory t = (Territory) o;
      return name.equals(t.name);
    }
    return false;
  }
}
