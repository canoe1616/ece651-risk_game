package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class MapTest {
  @Test
  public void test_getList() {
    HashSet<Territory> list = new HashSet<Territory>();
    Territory ter1 = new Territory("test1");
    Territory ter2 = new Territory("test2");
    Map m = new Map();
    m.addTerritory(ter1);
    m.addTerritory(ter2);
    list.add(ter1);
    list.add(ter2);
    assertEquals(m.getList(), list);
  }
  @Test
  public void test_getplayer(){
    HashSet<Player> list = new HashSet<Player>();
    Player p1 = new Player("blue");
    Map m = new Map();
    m.addPlayer(p1);
    list.add(p1);
    assertEquals(m.getPlayer(), list);    
  }

}
