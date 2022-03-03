package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class TerritoryTest {
  @Test
  public void test_getName() {
    Player p1 = new Player('b');
    Territory ter = new Territory("Test", 10, p1);
    assertEquals("Test",ter.getName());
  }

  @Test
  public void test_addNeighbors(){
    Player p1 = new Player('b');
    Territory ter1 = new Territory("Test1", 10, p1);
    Territory ter2 = new Territory("Test2", 10, p1);
    ter1.addNeighbors(ter2);
    HashSet<Territory> neighbors = new HashSet<Territory>();
    neighbors.add(ter2);
    assertEquals(neighbors, ter1.getNeighbors());
  
}
}
