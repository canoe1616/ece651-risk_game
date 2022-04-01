package edu.duke.ece651.grp9.risk.shared;


import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class ActionThread extends Thread{
  private ObjectInputStream objectInputStream;
  private ObjectOutputStream objectOutputStream;
  private Map m;
  private Player player;
  public ActionThread(Map m, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, Player player) {
    this.m = m;
    this.objectInputStream = objectInputStream;
    this.objectOutputStream = objectOutputStream;
    this.player = player;
  }

  @Override
    public void run() {
      try{
        HashSet<MoveAction> correctMoveActions = new HashSet<>();
        HashSet<AttackAction> correctAttackActions = new HashSet<>();
        HashSet<MoveAction> moveActions = new HashSet<>();
        HashSet<AttackAction> attackActions = new HashSet<>();

        ActionSet actionSet = (ActionSet) objectInputStream.readObject();
        System.out.println("Get action...");
        HashSet<String> actionListMove = actionSet.getMoveList();

        for (String move : actionListMove) {
          moveActions.add((MoveAction) GamePlay.createAction(m, player, move, true));
        }

        HashSet<String> actionListAttack = actionSet.getAttackList();
        for (String attack : actionListAttack) {
          attackActions.add((AttackAction) GamePlay.createAction(m, player, attack, false));
        }

        //moveActions  attackActions need to be reset in the next round.
        String actionProblem = GamePlay.validActionSet(player, moveActions, attackActions);

        //debug：here should be reset
        objectOutputStream.reset();
        objectOutputStream.writeObject(actionProblem);
        if (actionProblem == null) {
          correctMoveActions.addAll(moveActions);
          correctAttackActions.addAll(attackActions);
          break;
        }
        System.out.println("There are problems with this client's setting, send information back to the server");
      }
      catch(Exception e){
        System.out.println("Error occured " + e.getMessage());
      }
  }
}
