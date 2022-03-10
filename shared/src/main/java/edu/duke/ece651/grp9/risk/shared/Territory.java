package edu.duke.ece651.grp9.risk.shared;
import java.io.Serializable;
import java.util.HashSet;

public class Territory implements Serializable{
  private String name;
  private int unit;
  private String color;
  private HashSet<String> neighbors;
  
  public Territory(String name){
    this.name = name;
    this.neighbors = new HashSet<String>();
  }

  public void setUnit(int unit){
    this.unit = unit;
  }

  public void setColor(String color){
    this.color = color;
  }
  
  
  public void addNeighbors(String name){
    this.neighbors.add(name);
  }

  public HashSet<String> getNeighbors(){
    return this.neighbors;
  }

  public String getName(){
    return name;
  }

  public int getUnit() {
    return unit;
  }

}
