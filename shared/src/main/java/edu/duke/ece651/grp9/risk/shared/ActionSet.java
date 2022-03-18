package edu.duke.ece651.grp9.risk.shared;

import java.io.Serializable;
import java.util.HashSet;

public class ActionSet implements Serializable{
  public HashSet<String> actionListMove;
  public HashSet<String> actionListAttack;
  public ActionSet(){
    this.actionListMove = new HashSet<String>();
    this.actionListAttack = new HashSet<String>();
  }

  public HashSet<String> getMoveList() {
    return actionListMove;
  }

  public HashSet<String> getAttackList() {
    return actionListAttack;
  }
}
