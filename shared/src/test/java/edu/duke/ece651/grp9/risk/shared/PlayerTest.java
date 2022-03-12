package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class PlayerTest {
  @Test
  public void test_addTerritory() {
    Player p1 = new Player("blue");
    Territory ter = new Territory("Test");
    p1.addTerritory(ter);
    HashSet<Territory> territoryList = new HashSet<Territory>();
    territoryList.add(ter);
    assertEquals(territoryList, p1.getTerritoryList());
  }


  @Test
  public void test_getAdjacency(){
    Player p1 = new Player("blue");
    Territory ter_1 = new Territory("Test_1");
    Territory ter_2 = new Territory("Test_2");
    Territory ter_3 = new Territory("Test_3");
    ter_1.addNeighbors(ter_2);
    ter_1.addNeighbors(ter_3);
    ter_2.addNeighbors(ter_3);
    p1.addTerritory(ter_1);
    p1.addTerritory(ter_2);
    p1.addTerritory(ter_3);
    //For the ter_1, its neighbors become the ter_2 and ter_3.
    // So that, the hashmap looks like <ter_1, <ter_2,ter_3>> for the playerB
    HashSet<Territory> neighbors = new HashSet<Territory>();
    neighbors.add(ter_2);
    neighbors.add(ter_3);
    assertEquals(neighbors, p1.getAdjacency().get(ter_1));
  }
}
