package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class TerritoryTest {
  @Test
  public void test_getName() {
    Player p1 = new Player("blue");
    Territory ter = new Territory("Test");
    assertEquals("Test",ter.getName());
  }

  @Test
  public void test_addNeighbors(){
    Player p1 = new Player("blue");
    Territory ter1 = new Territory("Test1");
    Territory ter2 = new Territory("Test2");
    ter1.addNeighbors("Test2");
    HashSet<String> neighbors = new HashSet<String>();
    neighbors.add("Test2");
    assertEquals(neighbors, ter1.getNeighbors());
  
}
}
