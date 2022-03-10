package edu.duke.ece651.grp9.risk.shared;
import java.util.HashSet;
import java.util.Iterator;

public class MapFactory implements AbstractMapFactory {
  
  /**
   * Make a player list.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the player list.
   */

  public HashSet<Player> createPlayer(HashSet<String> colorList, Map myMap){
     Iterator<String> colorIterator = colorList.iterator();
     while(colorIterator.hasNext()){
       Player p = new Player(colorIterator.next());
       myMap.addPlayer(p);
     }
     return myMap.getPlayer();
  }

  public void createTerritory(HashSet<String> TerritoryNameList, Map myMap){
    Iterator<String> territoryIterator = TerritoryNameList.iterator();
    while(territoryIterator.hasNext()){
      Territory t = new Territory(territoryIterator.next());
      myMap.addTerritory(t);
    }
  }

  public void setOwner(String territoryName, String color, Map myMap){
    Territory ter = myMap.findTerritory(territoryName);
    Player pyr = myMap.findPlayer(color);
    ter.setColor(color);
    pyr.addTerritory(ter);
  }
  
  public void addNeighbors(String mainTerritory, String neighborTerritory, Map myMap){
    Territory target = myMap.findTerritory(mainTerritory);
    target.addNeighbors(neighborTerritory);
  }
    
  /*2 player map
    |A|B|
    |C|D|
   */
  @Override
  public Map makeMapForTwo(){
    Map mapForTwo = new Map();
    //create color list
    HashSet<String> colorList = new HashSet<>();
    colorList.add("red");
    colorList.add("blue");
    //create player list
    createPlayer(colorList, mapForTwo);
    
    //create territory
    HashSet<String> TerritoryNameList = new HashSet<>();
    TerritoryNameList.add("A");
    TerritoryNameList.add("B");
    TerritoryNameList.add("C");
    TerritoryNameList.add("D");

    createTerritory(TerritoryNameList, mapForTwo);
    //set Territory of a player
    setOwner("A","red",mapForTwo);
    setOwner("B","red",mapForTwo);
    setOwner("C","blue",mapForTwo);
    setOwner("D","blue",mapForTwo);

    //set neighbors
    addNeighbors("A","C",mapForTwo);
    addNeighbors("A","D",mapForTwo);
    addNeighbors("B","A",mapForTwo);
    addNeighbors("B","D",mapForTwo);
    addNeighbors("C","A",mapForTwo);
    addNeighbors("C","D",mapForTwo);
    addNeighbors("D","B",mapForTwo);
    addNeighbors("D","C",mapForTwo);
    
    return mapForTwo;
  }

   /* The map for three people looks like that:
                      /\
                     /A \
                    /\C /\
                   /B \/D \
                  /\F /\H /\
                 /E \/G \/I \
   */
  
  @Override
  public Map makeMapForThree(){
    Map mapForThree = new Map();
    //create color list
    HashSet<String> colorList = new HashSet<>();
    colorList.add("red");
    colorList.add("blue");
    colorList.add("green");

    //create territory
    HashSet<String> TerritoryNameList = new HashSet<>();
    TerritoryNameList.add("A");
    TerritoryNameList.add("B");
    TerritoryNameList.add("C");
    TerritoryNameList.add("D");
    TerritoryNameList.add("E");
    TerritoryNameList.add("F");
    TerritoryNameList.add("G");
    TerritoryNameList.add("H");
    TerritoryNameList.add("I");

    createTerritory(TerritoryNameList, mapForThree);

    //set Territory of a player      
    setOwner("A","red",mapForThree);
    setOwner("B","red",mapForThree);
    setOwner("C","red",mapForThree);
    setOwner("E","blue",mapForThree);
    setOwner("F","blue",mapForThree);
    setOwner("G","blue",mapForThree);
    setOwner("D","green",mapForThree);
    setOwner("H","green",mapForThree);
    setOwner("I","green",mapForThree);

    //set neighbors
    addNeighbors("A","C",mapForThree);
    addNeighbors("B","C",mapForThree);
    addNeighbors("B","F",mapForThree);
    addNeighbors("C","A",mapForThree);
    addNeighbors("C","B",mapForThree);
    addNeighbors("C","D",mapForThree);
    addNeighbors("D","C",mapForThree);
    addNeighbors("D","H",mapForThree);
    addNeighbors("E","F",mapForThree);
    addNeighbors("F","B",mapForThree);
    addNeighbors("F","E",mapForThree);
    addNeighbors("F","G",mapForThree);
    addNeighbors("G","F",mapForThree);
    addNeighbors("G","H",mapForThree);
    addNeighbors("H","D",mapForThree);
    addNeighbors("H","G",mapForThree);
    addNeighbors("H","I",mapForThree);
    addNeighbors("I","H",mapForThree);
    
    return mapForThree;
  }

  @Override
  public Map makeMapForFour(){
    Map mapForFour = new Map();
    return mapForFour;
  }

  @Override
  public Map makeMapForFive(){
   Map mapForFive = new Map();
   return mapForFive;
  }
}
