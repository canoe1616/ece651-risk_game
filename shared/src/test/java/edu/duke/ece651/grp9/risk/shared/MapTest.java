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

  @Test
  public void test_findPlayer() {
    Player p1 = new Player("blue");
    Map map = new Map();
    map.addPlayer(p1);
    assertEquals(map.findPlayer("blue"), p1);
    assertEquals(map.findPlayer("yellow"), null);
  }

  @Test
  public void test_findTerritory() {
    MapFactory mapFactory = new MapFactory();
    Map map = mapFactory.makeMapForTwo();
    assertEquals(map.findTerritory("C"), new Territory("C"));
    assertEquals(map.findTerritory("Z"), null);
  }

  @Test
  public void test_removePlayer() {
    Map map = new Map();
    Player p1 = new Player("blue");
    map.addPlayer(p1);
    Player p2 = new Player("red");
    map.addPlayer(p2);

    assertEquals(map.findPlayer("blue"), p1);
    assertEquals(map.findPlayer("red"), p2);

    map.removePlayer(p1);
    assertEquals(map.findPlayer("blue"), null);
    map.removePlayer(p2);
    assertEquals(map.findPlayer("red"), null);
  }

  @Test
  void getGameWinner_true() {
    MapFactory mapFactory = new MapFactory();
    Map map = mapFactory.makeMapForTwo();
    Player playerRed = map.findPlayer("red");
    Player playerBlue = map.findPlayer("blue");
    map.findTerritory("C").setOwner(playerRed);
    map.findTerritory("D").setOwner(playerRed);
    playerRed.addTerritory(map.findTerritory("C"));
    playerRed.addTerritory(map.findTerritory("D"));
    playerBlue.removeTerritory(map.findTerritory("C"));
    playerBlue.removeTerritory(map.findTerritory("D"));
    assertEquals(playerRed, map.getGameWinner());
  }

  @Test
  void getGameWinner_false() {
    MapFactory mapFactory = new MapFactory();
    Map map = mapFactory.makeMapForTwo();
    Player playerRed = map.findPlayer("red");
    Player playerBlue = map.findPlayer("blue");
    map.findTerritory("C").setOwner(playerRed);
    playerRed.addTerritory(map.findTerritory("C"));
    playerBlue.removeTerritory(map.findTerritory("C"));
    assertEquals(null, map.getGameWinner());
  }

}
