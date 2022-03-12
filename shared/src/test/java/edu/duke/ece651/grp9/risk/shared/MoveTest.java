package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MoveTest {

  @Test
  public void test_performAction() {
    Player p1 = new Player("red");
    Territory t1 = new Territory("Two Rivers");
    t1.setOwner(p1);
    t1.setUnit(5);

    Territory t2 = new Territory("Tar Valon");
    t2.setOwner(p1);
    t2.setUnit(0);
    String error1 = "This action is invalid: Two Rivers is not connected to Tar Valon.";

    Move m1 = new Move(p1, t1, t2, 5);
    assertEquals(m1.canPerformAction(), error1);
    t2.addNeighbors(t1);
    assertEquals(m1.canPerformAction(), null);

    assertEquals(t1.getUnit(), 5);
    assertEquals(t2.getUnit(), 0);
    m1.performAction();
    assertEquals(t1.getUnit(), 0);
    assertEquals(t2.getUnit(), 5);
  }
}

