package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
  public void test_Color(){
    Territory ter = new Territory("Test");
    ter.setColor("blue");
    String c = ter.getColor();
    assertEquals(c,"blue");
  }

  @Test
  public void test_Neighbors(){
    Territory ter1 = new Territory("Test1");
    ter1.addNeighbors("Test2");
    HashSet<String> neighbors = new HashSet<String>();
    neighbors.add("Test2");
    assertEquals(neighbors, ter1.getNeighbors());
  }
}
