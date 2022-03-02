package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class PlayerTest {
  @Test
  public void test_addTerritory() {
    Player p1 = new Player('b');
    Territory ter = new Territory("Test", 10, p1);
    p1.addTerritory(ter);
    HashSet<Territory> territoryList = new HashSet<Territory>();
    territoryList.add(ter);
    assertEquals(territoryList, p1.getTerritoryList());
  }

}
