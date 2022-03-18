package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class ActionSetTest {
  @Test
  public void test_actionSet() {
    HashSet<String> a = new HashSet<>();
    HashSet<String> b = new HashSet<>();
    ActionSet s = new ActionSet();
    s.actionListMove = a;
    s.actionListAttack = b;
    assertEquals(s.actionListMove, a);
    assertEquals(s.actionListAttack, b); 
  }

}
