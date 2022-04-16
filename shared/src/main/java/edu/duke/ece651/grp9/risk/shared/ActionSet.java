package edu.duke.ece651.grp9.risk.shared;

import java.io.Serializable;
import java.util.HashSet;

/**
 * The method is to store all user action inputs into hashset.
 */

public class ActionSet implements Serializable{
  public HashSet<String> actionListMove;
  public HashSet<String> actionListAttack;
  public HashSet<String> actionListUpgrade;
  public boolean techLevelUpgrade;
  public boolean doResearch;

  /**
   * A construct for an ActionSet that contains a set for moves and a set for attacks.
   */
  public ActionSet(){
    this.actionListMove = new HashSet<String>();
    this.actionListAttack = new HashSet<String>();
    this.actionListUpgrade= new HashSet<String>();
    this.techLevelUpgrade = false;
    this.doResearch = false;
  }

  public HashSet<String> getMoveList() {
    return actionListMove;
  }

  public HashSet<String> getAttackList() {
    return actionListAttack;
  }

  public HashSet<String> getUpgradeList() {
    return actionListUpgrade;
  }
}
