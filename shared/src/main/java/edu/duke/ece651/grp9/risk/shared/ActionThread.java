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
    private Socket socket;
    private ArrayList<ActionThread> threadList;
    
    private static HashSet<String> remainingColors;
    private Map m;
    public ActionThread(Socket socket, ArrayList<ActionThread> threads , Map m) {
        this.socket = socket;
        this.threadList = threads;
        this.m = m;
    }

    @Override
    public void run() {
        try{
            // initial sets for player actions
            HashSet<MoveAction> allMoves = new HashSet<>();
            HashSet<AttackAction> allAttack = new HashSet<>();

      // if we find the winner, notify the winner he wins the game;
      // notify the other players game over
        if (m.getGameWinner() == null) {
            // no winner detected, game continuing
            for (int i = 0; i < socketList.size(); i++) {
            OutputList.get(i).reset();
            OutputList.get(i).writeObject(m);
            System.out.println("Send map");
            }
            int socketNum =  socketList.size();
            for(int i = 0 ; i < socketNum; i++) {
            if(i>=socketList.size()){
                break;
            }

          // notify player game not over yet
          OutputList.get(i).writeObject("game continuing");
          System.out.println("Write end game flag to player");

          String action = (String) InputList.get(i).readObject();


          Player player = app.findPlayer(playerList.get(i), m);
          player.setLoseStatus(action);

          if (player.isLose()) {
            if (player.getLoseStatus().equals("quit") && m.getPlayer().contains(player)) {
              //remove it from player list
              //auto set empty actionSet
              System.out.println("Bye bye I quit");
              socketList.remove(i);
              InputList.remove(i);
              OutputList.remove(i);
              playerList.remove(i);
              i--;
              
            }
            if (player.getLoseStatus().equals("continue")) {
            }
          } else { // if the player still alive
            while (true) {
              HashSet<MoveAction> moveActions = new HashSet<>();
              HashSet<AttackAction> attackActions = new HashSet<>();

              ActionSet actionSet = (ActionSet) InputList.get(i).readObject();
              System.out.println("Get action...");
              HashSet<String> actionListMove = actionSet.getMoveList();

              for (String move : actionListMove) {
                moveActions.add((MoveAction) app.createAction(m, playerList.get(i), move, true));
              }

              HashSet<String> actionListAttack = actionSet.getAttackList();
              for (String attack : actionListAttack) {
                attackActions.add((AttackAction) app.createAction(m, playerList.get(i), attack, false));
              }

              //moveActions  attackActions need to be reset in the next round.
              String actionProblem = app.validActionSet(m.findPlayer(playerList.get(i)),
                  moveActions, attackActions);

              //debug：here should be reset
              OutputList.get(i).reset();
              OutputList.get(i).writeObject(actionProblem);
              if (actionProblem == null) {
                allMoves.addAll(moveActions);
                allAttack.addAll(attackActions);
                break;
              }
              System.out.println("There are problems with this client's setting, send information back to the server");
            }
          }


        }

        for (MoveAction act : allMoves) {
          act.performAction();
        }
        allMoves.clear();
        //real execute for te attack action
        app.playAttacks(m, allAttack);
        allAttack.clear();
        for (Territory territory : m.getList()) {
          territory.addUnit();
        }
      }

      //------------------------------------------Winner part-------------------------------//
      for (int i = 0; i < socketList.size(); i++) {
        app.gameWinner(OutputList.get(i), playerList.get(i), m);
      }
      System.out.println("Final point");
      TimeUnit.SECONDS.sleep(20);
      ss.close();


            
           


            socket.close();
        }
        catch(Exception e){
            System.out.println("Error occured " + e.getMessage());
        }
    }


}
