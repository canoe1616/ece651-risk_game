package edu.duke.ece651.grp9.risk.shared;
import java.util.HashSet;
import java.util.Iterator;

public class MapFactory implements AbstractMapFactory {
  
  /**
   * Make a player list.
   * 
   * @param myMap specifies the location and orientation of the ship to make
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
    Iterator<String> territoryNameIterator = TerritoryNameList.iterator();
    while(territoryNameIterator.hasNext()){
      Territory t = new Territory(territoryNameIterator.next());
      myMap.addTerritory(t);
    }
  }

  public void setOwner(Territory ter, Player pyr){
    ter.setOwner(pyr);
    pyr.addTerritory(ter);
  }
  
  public void addNeighbors(Territory mainTerritory,Territory neighborTerritory){
    mainTerritory.addNeighbors(neighborTerritory);
  }
    
  /*2 player map
    |A|B|
    |C|D|
   */
  @Override
  public Map makeMapForTwo(){
    Map mapForTwo = new Map();
    //create player
    Player p1 = new Player("red");
    Player p2 = new Player("blue");
    //create territory
    Territory ter_1 = new Territory("A");
    Territory ter_2 = new Territory("B");
    Territory ter_3 = new Territory("C");
    Territory ter_4 = new Territory("D");

    //set owner
    ter_1.setOwner(p1);
    ter_2.setOwner(p1);
    ter_3.setOwner(p2);
    ter_4.setOwner(p2);
    
    //set neighbors
    ter_1.addNeighbors(ter_2);
    ter_1.addNeighbors(ter_3);
    ter_2.addNeighbors(ter_1);
    ter_2.addNeighbors(ter_4);
    ter_3.addNeighbors(ter_1);
    ter_3.addNeighbors(ter_4);
    ter_4.addNeighbors(ter_2);
    ter_4.addNeighbors(ter_3);

    //add to territory list in map
    mapForTwo.addTerritory(ter_1);
    mapForTwo.addTerritory(ter_2);
    mapForTwo.addTerritory(ter_3);
    mapForTwo.addTerritory(ter_4);
    
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
    //create player
    Player p1 = new Player("red");
    Player p2 = new Player("green");
    Player p3 = new Player("blue");

    //create territory
    Territory ter_A = new Territory("A");
    Territory ter_B = new Territory("B");
    Territory ter_C = new Territory("C");
    Territory ter_D = new Territory("D");
    Territory ter_E = new Territory("E");
    Territory ter_F = new Territory("F");
    Territory ter_G = new Territory("G");
    Territory ter_H = new Territory("H");
    Territory ter_I = new Territory("I");

   //set owner
    ter_A.setOwner(p1);
    ter_B.setOwner(p1);
    ter_C.setOwner(p1);
    ter_D.setOwner(p2);
    ter_E.setOwner(p2);
    ter_F.setOwner(p2);
    ter_G.setOwner(p3);
    ter_H.setOwner(p3);
    ter_I.setOwner(p3);

    //set neighbors
    ter_A.addNeighbors(ter_C);
    ter_B.addNeighbors(ter_C);
    ter_B.addNeighbors(ter_F);
    ter_C.addNeighbors(ter_A);
    ter_C.addNeighbors(ter_B);
    ter_C.addNeighbors(ter_D);
    ter_D.addNeighbors(ter_C);
    ter_D.addNeighbors(ter_H);
    ter_E.addNeighbors(ter_F);
    ter_F.addNeighbors(ter_B);
    ter_F.addNeighbors(ter_E);
    ter_F.addNeighbors(ter_G);
    ter_G.addNeighbors(ter_F);
    ter_G.addNeighbors(ter_H);
    ter_H.addNeighbors(ter_D);
    ter_H.addNeighbors(ter_G);
    ter_H.addNeighbors(ter_I);
    ter_I.addNeighbors(ter_H);

    //add to territory list in map
    mapForThree.addTerritory(ter_A);
    mapForThree.addTerritory(ter_B);
    mapForThree.addTerritory(ter_C);
    mapForThree.addTerritory(ter_D);
    mapForThree.addTerritory(ter_E);
    mapForThree.addTerritory(ter_F);
    mapForThree.addTerritory(ter_G);
    mapForThree.addTerritory(ter_H);
    mapForThree.addTerritory(ter_I);

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
