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
    String error1 = "This action is invalid: Two Rivers is not adjacent to Tar Valon.";
    String error2 = "This action is invalid: you cannot attack your own Territory.";

    RuleChecker ruleChecker = new AttackRuleChecker(null);

    assertEquals(ruleChecker.checkMyRule(p1, t1, t2, 4), error1);

    t1.addNeighbors(t2);
    assertEquals(ruleChecker.checkMyRule(p1, t1, t2, 4), null);
    t1.addNeighbors(t3);
    assertEquals(ruleChecker.checkMyRule(p1, t1, t3, 4), error2);
  }

  @Test
  public void test_chainedRules() {
    Player p1 = new Player('r');
    Player p2 = new Player('g');
    Territory t1 = new Territory("Two Rivers", 5, p1);
    Territory t2 = new Territory("Tar Valon", 3, p2);
    Territory t3 = new Territory("Falme", 5, p1);
    String error1 = "This action is invalid: Two Rivers is not adjacent to Tar Valon.";
    String error2 = "This action is invalid: you cannot attack your own Territory.";
    String error3 = "This action is invalid: you do not own Tar Valon.";
    String error4 = "This action is invalid: Two Rivers does not have enough units.";

    RuleChecker ruleChecker = new UnitsRuleChecker(new OwnerRuleChecker(new AttackRuleChecker(null)));

    t1.addNeighbors(t3);
    t3.addNeighbors(t1);
    assertEquals(ruleChecker.checkAction(p1, t1, t3, 4), error2);
    assertEquals(ruleChecker.checkAction(p1, t1, t2, 4), error1);

    t1.addNeighbors(t2);
    t2.addNeighbors(t1);
    assertEquals(ruleChecker.checkAction(p1, t1, t2, 6), error4);
    assertEquals(ruleChecker.checkAction(p1, t2, t1, 3), error3);

    assertEquals(ruleChecker.checkAction(p1, t1, t2, 5), null);
  }
}
