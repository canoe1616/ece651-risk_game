package edu.duke.ece651.grp9.risk.shared;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;

public class ActionThread extends Thread{
  private ObjectInputStream objectInputStream;
  private ObjectOutputStream objectOutputStream;
  private Map m;
  private Player player;
  public HashSet<MoveAction> allMove = new HashSet<>();
  public HashSet<AttackAction> allAttack = new HashSet<>();
  public HashSet<UpgradeAction> allUpgrade = new HashSet<>();

  public ActionThread(Map m, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, Player player, HashSet<MoveAction> allMoves, HashSet<AttackAction>allAttack, HashSet<UpgradeAction> allUpgrade) {
    this.m = m;
    this.objectInputStream = objectInputStream;
    this.objectOutputStream = objectOutputStream;
    this.player = player;
    this.allMove = allMoves;
    this.allAttack = allAttack;
    this.allUpgrade = allUpgrade;
  }

  @Override
    public void run() {
    GamePlay gamePlay =new GamePlay();
      try{
        while (true) {
          HashSet<MoveAction> moveActions = new HashSet<>();
          HashSet<AttackAction> attackActions = new HashSet<>();
          HashSet<UpgradeAction> upgradeActions = new HashSet<>();

          ActionSet actionSet = (ActionSet) objectInputStream.readObject();
          System.out.println("Get action...");

          HashSet<String> actionListMove = actionSet.getMoveList();
          for (String move : actionListMove) {
            moveActions.add((MoveAction) gamePlay.createAction(m, player.getName(), move, true));
          }

          HashSet<String> actionListAttack = actionSet.getAttackList();
          for (String attack : actionListAttack) {
            attackActions.add((AttackAction) gamePlay.createAction(m, player.getName(), attack, false));
          }

          HashSet<String> actionListUpgrade = actionSet.getUpgradeList();
          for (String upgrade : actionListUpgrade) {
            upgradeActions.add((UpgradeAction) gamePlay.createUpgrade(m, player.getName(), upgrade));
          }


          //moveActions  attackActions need to be reset in the next round.
          String actionProblem = gamePlay.validActionSet(player, moveActions, attackActions, upgradeActions);
          System.out.println("actionProblem: " +actionProblem );
          //debug：here should be reset
          objectOutputStream.reset();
          objectOutputStream.writeObject(actionProblem);
          if (actionProblem == null) {
            allMove.addAll(moveActions);
            allAttack.addAll(attackActions);
            allUpgrade.addAll(upgradeActions);


            // to be done
            // if readobject- >yes
            //thread stops

//            String switch_answer = (String) objectInputStream.readObject();
//            if(switch_answer.equals("true")){
//              suspend();
//            }






            break;
          }







          System.out.println("There are problems with this client's setting, send information back to the server");
        }
      }
      catch(Exception e){
        System.out.println("Error occured " + e.getMessage());
      }
  }
}
