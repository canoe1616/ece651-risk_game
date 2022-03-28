package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UnitTest {
  @Test
  public void test_applyBonus() {
    Unit unit2 = new Level2Unit();
    assertEquals(unit2.applyBonus(5), 8);

    Unit unit6 = new Level6Unit();
    assertEquals(unit6.applyBonus(19), 34);
  }
}
