package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class ActionRuleCheckerTest {
  @Test
  public void test_checkAction() {
    ActionRuleChecker tmp = new ActionRuleChecker();
    assertNull(tmp.checkAction("M"));
    assertEquals("the input character is invalid, please enter again!", tmp.checkAction("t"));
    assertEquals("the input length is invalid, please enter again!", tmp.checkAction("Mt"));
  }

  @Test
  public void test_checkColor(){
    ActionRuleChecker tmp = new ActionRuleChecker();
    HashSet<String> remainingColors = new HashSet<>();
    remainingColors.add("red");
    assertNull(tmp.checkColor("red",remainingColors ));
    assertEquals("The color you input is invalid, please enter again",tmp.checkColor("blue",remainingColors ));



  }



}
