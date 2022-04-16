package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class ActionSetTest {
  @Test
  public void test_actionSet() {
    HashSet<String> a = new HashSet<>();
    HashSet<String> b = new HashSet<>();
    HashSet<String> c = new HashSet<>();
    HashSet<String> d = new HashSet<>();
    ActionSet s = new ActionSet();
    s.actionListMove = a;
    s.actionListAttack = b;
    s.actionListUpgrade = c;
    s.actionListCloak = d;
    assertEquals(s.getMoveList(), a);
    assertEquals(s.getAttackList(), b);
    assertEquals(s.getUpgradeList(), c);
    assertEquals(s.getCloakList(), d);

  }

}
