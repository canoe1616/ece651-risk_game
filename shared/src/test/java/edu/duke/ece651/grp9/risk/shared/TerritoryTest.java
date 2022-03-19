package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class TerritoryTest {

  @Test
  public void test_setgetUnit(){
    Territory ter = new Territory("Test");
    ter.setUnit(10);
    int test_unit = ter.getUnit();
    assertEquals(test_unit, 10);
  }
  
  @Test
  public void test_getName() {
    Territory ter = new Territory("Test");
    assertEquals("Test",ter.getName());
  }

  @Test
  public void test_setOwner(){
    Player p1 = new Player("p1");
    Territory ter = new Territory("Test");
    ter.setOwner(p1);
    Player p2 = ter.getOwner();
    assertEquals(p1, p2);
  }

  @Test
  public void test_Neighbors(){
    Territory ter1 = new Territory("Test1");
    Territory ter2 = new Territory("Test2");
    ter1.addNeighbors(ter2);
    HashSet<Territory> neighbors = new HashSet<Territory>();
    neighbors.add(ter2);
    assertEquals(neighbors, ter1.getNeighbors());
  }

  @Test
  public void test_equals(){
    Territory ter1 = new Territory("Test1");
    String a = "Hello";
    assertFalse(ter1.equals(a));
  }

  @Test
  public void test_addUnit() {
    Territory ter1 = new Territory("Test1");
    ter1.setUnit(3);

    assertEquals(ter1.getUnit(), 3);
    ter1.addUnit();
    assertEquals(ter1.getUnit(), 4);
  }

}
