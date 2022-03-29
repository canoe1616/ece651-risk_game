package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class UpgradeRuleCheckerTest {

  @Test
  public void test_checkMyRule() {
    Player p1 = new Player("red");
    Territory t1 = new Territory("Two Rivers");
    t1.setOwner(p1);
    t1.setUnits(5, 0);

    assertEquals(t1.getUnits(0), 5);

    RuleChecker ruleChecker = new UpgradeRuleChecker(null);

    String error1 = "This action is invalid: Your technology level is not yet at level 1.";

    UpgradeAction u1 = new UpgradeAction(p1, t1, 3, 0 ,1);

    assertEquals(error1, ruleChecker.checkMyRule(u1));

    p1.upgradeTechLevel();

    assertEquals(null, ruleChecker.checkMyRule(u1));

    u1.performAction();

    assertEquals(t1.getUnits(0), 2);
    assertEquals(t1.getUnits(1), 3);
  }
}
