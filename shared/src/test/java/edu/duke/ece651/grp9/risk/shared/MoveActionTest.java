package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MoveActionTest {

  @Test
  public void test_performAction() {
    Player p1 = new Player("red");
    Territory t1 = new Territory("Two Rivers");
    t1.setOwner(p1);
    t1.setUnits(5);

    Territory t2 = new Territory("Tar Valon");
    t2.setOwner(p1);
    t2.setUnits(0);
    String error1 = "This action is invalid: Two Rivers is not connected to Tar Valon.";

    MoveAction m1 = new MoveAction(p1, t1, t2, 5);
    assertEquals(m1.canPerformAction(), error1);
    t2.addNeighbors(t1);
    assertEquals(m1.canPerformAction(), null);

    assertEquals(m1.getPlayer(), p1);

    assertEquals(t1.getUnits(0), 5);
    assertEquals(t2.getUnits(0), 0);
    m1.performAction();
    assertEquals(t1.getUnits(0), 0);
    assertEquals(t2.getUnits(0), 5);
  }
}

