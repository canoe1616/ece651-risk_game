package edu.duke.ece651.grp9.risk.shared;

public class MapFactory implements AbstractMapFactory {
  
  /*2 player map
    |A|B|
    |C|D|
   */

  /** this method is to create a map for two
   * @return a map
   */

  @Override
  public Map makeMapForTwo(){
    
    Map mapForTwo = new Map();
    mapForTwo.setPlayerNum(2);
    //create player
    Player p1 = new Player("red");
    Player p2 = new Player("blue");
    //create territory
    Territory ter_1 = new Territory("A");
    Territory ter_2 = new Territory("B");
    Territory ter_3 = new Territory("C");
    Territory ter_4 = new Territory("D");

    //set player
    mapForTwo.addPlayer(p1);
    mapForTwo.addPlayer(p2);
    
    //set owner
    ter_1.setOwner(p1);
    ter_2.setOwner(p1);
    ter_3.setOwner(p2);
    ter_4.setOwner(p2);

    //set territories to player
    p1.addTerritory(ter_1);
    p1.addTerritory(ter_2);
    p2.addTerritory(ter_3);
    p2.addTerritory(ter_4);
    
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

    //add player to player list in map 
    mapForTwo.addPlayer(p1);
    mapForTwo.addPlayer(p2);

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
    mapForThree.setPlayerNum(3);
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

    //set territories to player
    p1.addTerritory(ter_A);
    p1.addTerritory(ter_B);
    p1.addTerritory(ter_C);
    p2.addTerritory(ter_D);
    p2.addTerritory(ter_E);
    p2.addTerritory(ter_F);
    p3.addTerritory(ter_G);
    p3.addTerritory(ter_H);
    p3.addTerritory(ter_I);

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

    //add player to player list in map
    mapForThree.addPlayer(p1);
    mapForThree.addPlayer(p2);
    mapForThree.addPlayer(p3);

    return mapForThree;
  }
  
  @Override
  public Map makeMapForFour(){
    Map mapForFour = new Map();
    mapForFour.setPlayerNum(4);
    //create player
    Player p1 = new Player("red");
    Player p2 = new Player("green");
    Player p3 = new Player("blue");
    Player p4 = new Player("orange");
    //create territory
    Territory ter_A = new Territory("A");
    Territory ter_B = new Territory("B");
    Territory ter_C = new Territory("C");
    Territory ter_D = new Territory("D");
    Territory ter_E = new Territory("E");
    Territory ter_F = new Territory("F");
    Territory ter_G = new Territory("G");
    Territory ter_H = new Territory("H");
    //set owner
    ter_A.setOwner(p1);
    ter_B.setOwner(p1);
    ter_C.setOwner(p2);
    ter_D.setOwner(p2);
    ter_E.setOwner(p3);
    ter_F.setOwner(p3);
    ter_G.setOwner(p4);
    ter_H.setOwner(p4);
    //set neighbors
    ter_A.addNeighbors(ter_B);
    ter_A.addNeighbors(ter_C);
    ter_B.addNeighbors(ter_A);
    ter_B.addNeighbors(ter_D);
    ter_C.addNeighbors(ter_A);
    ter_C.addNeighbors(ter_D);
    ter_C.addNeighbors(ter_E);
    ter_D.addNeighbors(ter_B);
    ter_D.addNeighbors(ter_C);
    ter_D.addNeighbors(ter_F);
    ter_E.addNeighbors(ter_C);
    ter_E.addNeighbors(ter_F);
    ter_E.addNeighbors(ter_G);
    ter_F.addNeighbors(ter_D);
    ter_F.addNeighbors(ter_E);
    ter_F.addNeighbors(ter_H);
    ter_G.addNeighbors(ter_E);
    ter_G.addNeighbors(ter_H);
    ter_H.addNeighbors(ter_F);
    ter_H.addNeighbors(ter_G);
    //set territories to player
    p1.addTerritory(ter_A);
    p1.addTerritory(ter_B);
    p2.addTerritory(ter_C);
    p2.addTerritory(ter_D);
    p3.addTerritory(ter_E);
    p3.addTerritory(ter_F);
    p4.addTerritory(ter_G);
    p4.addTerritory(ter_H);
    //add to territory list in map
    mapForFour.addTerritory(ter_A);
    mapForFour.addTerritory(ter_B);
    mapForFour.addTerritory(ter_C);
    mapForFour.addTerritory(ter_D);
    mapForFour.addTerritory(ter_E);
    mapForFour.addTerritory(ter_F);
    mapForFour.addTerritory(ter_G);
    mapForFour.addTerritory(ter_H);
    //add player to player list in map
    mapForFour.addPlayer(p1);
    mapForFour.addPlayer(p2);
    mapForFour.addPlayer(p3);
    mapForFour.addPlayer(p4);
    return mapForFour;
  }



     /* The map for five people looks like that:
                   A B
                   C D
                   E F
                   G H
                   I J
   */
  @Override
  public Map makeMapForFive(){
   Map mapForFive = new Map();
   mapForFive.setPlayerNum(5);

    //create player
    Player p1 = new Player("red");
    Player p2 = new Player("green");
    Player p3 = new Player("blue");
    Player p4 = new Player("orange");
    Player p5 = new Player("purple");

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
    Territory ter_J = new Territory("J");


    //set owner
    ter_A.setOwner(p1);
    ter_B.setOwner(p1);
    ter_C.setOwner(p2);
    ter_D.setOwner(p2);
    ter_E.setOwner(p3);
    ter_F.setOwner(p3);
    ter_G.setOwner(p4);
    ter_H.setOwner(p4);
    ter_I.setOwner(p5);
    ter_J.setOwner(p5);


    //set neighbors
    ter_A.addNeighbors(ter_B);
    ter_A.addNeighbors(ter_C);
    ter_B.addNeighbors(ter_A);
    ter_B.addNeighbors(ter_D);
    ter_C.addNeighbors(ter_A);
    ter_C.addNeighbors(ter_D);
    ter_C.addNeighbors(ter_E);
    ter_D.addNeighbors(ter_B);
    ter_D.addNeighbors(ter_C);
    ter_D.addNeighbors(ter_F);
    ter_E.addNeighbors(ter_C);
    ter_E.addNeighbors(ter_F);
    ter_E.addNeighbors(ter_G);
    ter_F.addNeighbors(ter_D);
    ter_F.addNeighbors(ter_E);
    ter_F.addNeighbors(ter_H);
    ter_G.addNeighbors(ter_E);
    ter_G.addNeighbors(ter_H);
    ter_H.addNeighbors(ter_E);
    ter_H.addNeighbors(ter_G);
    ter_I.addNeighbors(ter_G);
    ter_I.addNeighbors(ter_J);
    ter_J.addNeighbors(ter_I);
    ter_J.addNeighbors(ter_I);


    //set territories to player
      p1.addTerritory(ter_A);
      p1.addTerritory(ter_B);
      p2.addTerritory(ter_C);
      p2.addTerritory(ter_D);
      p3.addTerritory(ter_E);
      p3.addTerritory(ter_F);
      p4.addTerritory(ter_G);
      p4.addTerritory(ter_H);
      p5.addTerritory(ter_I);
      p5.addTerritory(ter_J);


      //add to territory list in map
      mapForFive.addTerritory(ter_A);
      mapForFive.addTerritory(ter_B);
      mapForFive.addTerritory(ter_C);
      mapForFive.addTerritory(ter_D);
      mapForFive.addTerritory(ter_E);
      mapForFive.addTerritory(ter_F);
      mapForFive.addTerritory(ter_G);
      mapForFive.addTerritory(ter_H);

      //add player to player list in map
      mapForFive.addPlayer(p1);
      mapForFive.addPlayer(p2);
      mapForFive.addPlayer(p3);
      mapForFive.addPlayer(p4);
      mapForFive.addPlayer(p5);
      return mapForFive;


  }
  
}
