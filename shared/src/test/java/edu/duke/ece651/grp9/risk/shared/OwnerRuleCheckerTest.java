package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class OwnerRuleCheckerTest {

  @Test
  public void test_checkMyRule() {
    Player p1 = new Player('r');
    Player p2 = new Player('g');
    Territory t1 = new Territory("Two Rivers", 5, p1);
    Territory t2 = new Territory("Tar Valon", 3, p2);
    String error = "This action is invalid: you do not own Tar Valon.";

    RuleChecker ruleChecker = new OwnerRuleChecker(null);

    assertEquals(ruleChecker.checkMyRule(p1, t1, t2, 4), null);
    assertEquals(ruleChecker.checkMyRule(p1, t2, t1, 4), error);
  }
}
