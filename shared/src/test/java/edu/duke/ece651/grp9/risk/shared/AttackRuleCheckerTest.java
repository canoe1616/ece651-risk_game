package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AttackRuleCheckerTest {

  @Test
  public void test_checkMyRule() {
    Player p1 = new Player('r');
    Player p2 = new Player('g');
    Territory t1 = new Territory("Two Rivers", 5, p1);
    Territory t2 = new Territory("Tar Valon", 3, p2);
    Territory t3 = new Territory("Falme", 5, p1);
    String error1 = "This action is invalid: Two Rivers is not adjacent to Tar Valon."
    String error1 = "This action is invalid: you cannot attack your own Territory."

    RuleChecker ruleChecker = new RuleChecker(new AttackRuleChecker(null));

    assertEquals(ruleChecker.checkMyRule(p1, t1, t2, 4), error1);

    t1.addNeighbors(t2);
    assertEquals(ruleChecker.checkMyRule(p1, t1, t2, 4), null);
    assertEquals(ruleChecker.checkMyRule(p1, t1, t3, 4), error2);
  }
}
