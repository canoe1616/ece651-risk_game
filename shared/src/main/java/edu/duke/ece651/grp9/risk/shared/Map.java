package edu.duke.ece651.grp9.risk.shared;
import java.io.Serializable;
import java.util.HashSet;

public class Map implements Serializable{
  private HashSet<Territory> territoryList;
  //private List<List<Integer>> adjList = new ArrayList<List<Integer>>();
  public Map(){
    this.territoryList = new HashSet<Territory>();
    //this.territoryList = buildTestMap().territoryList;
  }
  public Map(HashSet<Territory> list) {
    this.territoryList = list;
  }

  public HashSet<Territory> getList() {
    return territoryList;
  }

  
  public void buildTestMap(){
    /* Test map look like this:
      |A|B|
      |C|D|
     */
    Player p1 = new Player('b');
    Player p2 = new Player('g');
    
    Territory a = new Territory("A", 10, p1);
    Territory b = new Territory("B", 10, p1);
    Territory c = new Territory("C", 10, p2);
    Territory d = new Territory("D", 10, p2);

    //set Territory of a player
    p1.addTerritory(a);
    p1.addTerritory(b);
    p2.addTerritory(c);
    p2.addTerritory(d);
    
    //set up territory's neighbors
    a.addNeighbors(d);
    a.addNeighbors(c);
    
    b.addNeighbors(a);
    b.addNeighbors(d);

    c.addNeighbors(a);
    c.addNeighbors(d);

    d.addNeighbors(b);
    d.addNeighbors(c);
    
    //save all territory into the list
    this.territoryList.add(a);
    this.territoryList.add(b);
    this.territoryList.add(c);
    this.territoryList.add(d);
  }
  //for testingg
  public HashSet<Territory> getTerritoryList(){
    return territoryList;
  }
}
