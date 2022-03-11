package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MoveRuleCheckerTest {

  @Test
  public void test_checkMyRule() {
    Player p1 = new Player('r');
    Player p2 = new Player('g');
    Territory t1 = new Territory("Two Rivers", 5, p1);
    Territory t2 = new Territory("Tar Valon", 3, p2);
    Territory t3 = new Territory("Falme", 5, p1);
    Territory t4 = new Territory("Tear", 5, p1);
    Territory t5 = new Territory("Andor", 5, p1);
    String error1 = "This action is invalid: Two Rivers is not connected to Tar Valon.";
    String error2 = "This action is invalid: Two Rivers is not connected to Andor.";

    RuleChecker ruleChecker = new RuleChecker(new MoveRuleChecker(null));
    t1.addNeighbors(t2);
    t1.addNeighbors(t3);
    t2.addNeighbors(t4);
    t4.addNeighbors(t5);

    assertEquals(ruleChecker.checkMyRule(p1, t1, t2, 4), error1);
    assertEquals(ruleChecker.checkMyRule(p1, t1, t3, 4), null);
    assertEquals(ruleChecker.checkMyRule(p1, t1, t5, 4), error2);

    t3.addNeighbors(t4);
    assertEquals(ruleChecker.checkMyRule(p1, t1, t5, 4), null);
  }
}
