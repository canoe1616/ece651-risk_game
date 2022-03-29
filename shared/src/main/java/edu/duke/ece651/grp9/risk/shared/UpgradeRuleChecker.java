package edu.duke.ece651.grp9.risk.shared;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class makes sure the Upgrade Action has enough Money to upgrade
 *
 * @author PROY
 * @since 29 March 2022
 */
public class UpgradeRuleChecker extends RuleChecker {

  /**
   * Adds the next Action rule to our list
   *
   * @param next Action rule in list
   */
  public UpgradeRuleChecker(RuleChecker next) {
    super(next);
  }

  /**
   * Checks if source Territory is connected to destination Territory
   *
   * @param player is the Player performing the Action
   * @param source is the Territory we are upgrading Units in
   * @param numUnits is the number of units we are upgrading
   * @param unitLevel is the Unit level we are upgrading
   * @return String description of error if invalid move, or null if okay
   */
  @Override
  protected String checkMyRule(Player player, Territory source, Territory destination, int numUnits,
      int unitLevel) {
    /*if (player.getTechLevel() < endLevel) { //TODO change to endLevel
      return "This action is invalid: Your technology level is not yet at level " + endLevel + ".";
    }
    if (startLevel >= endLevel) {
      return "This action is invalid: Unit can only increase in level.";
    }
    //Enough Money to upgrade - mock money?
//    if (action.computeCost() < player.getMoney()) {
//      return null;
//    }
    player.mockMoney(action.computeCost());*/

    return null;
  }
}

