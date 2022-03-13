package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class MapFactoryTest {
  @Test
  public void test_twomap() {
    MapFactory f = new MapFactory();
    Map m = f.makeMapForTwo();
    HashSet<Player> pyr_list = new HashSet<Player>();
    pyr_list.add(new Player("blue"));
    pyr_list.add(new Player("red"));
    
    assertEquals(2, m.getPlayerNum());
  }


  @Test
  public void test_threemap(){
    MapFactory f = new MapFactory();
    Map m = f.makeMapForThree();
    assertEquals(3, m.getPlayerNum());
  }
}
