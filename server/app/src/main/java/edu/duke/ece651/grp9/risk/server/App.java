/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.grp9.risk.server;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import edu.duke.ece651.grp9.risk.shared.*;



public class App {

  private static HashSet<String> remainingColors;

  public App() {
    remainingColors = new HashSet<>();
  }

  public App(Map m) {
    remainingColors = new HashSet<>();
    Iterator<Player> it = m.getPlayer().iterator();
    while (it.hasNext()) {
      remainingColors.add(it.next().getName());
    }
  }


  public void selectColor(ObjectOutputStream stream) {
    StringBuilder sb = new StringBuilder();
    sb.append("Please select what color you would like to play as: ");
    for (String color : remainingColors) {
      sb.append(color + " ");
    }
    try {
      stream.writeObject(sb.toString());
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public boolean deleteColor(String color) {
    for (String c : remainingColors) {
      if (c.equals(color)) {
        remainingColors.remove(color);
        return true;
      }
    }
    return false;
  }


  public void unitSetting(ObjectOutputStream stream, Player player) {

    StringBuilder sb = new StringBuilder();
    sb.append("You have " + player.getTerritoryNumber() + " territories: ");
    for (Territory ter : player.getTerritoryList()) {
      sb.append(ter.getName() + " ");
    }
    sb.append("\n");
    sb.append("You have 30 total units, how do you want to place the units?");
    try {
      stream.writeObject(sb.toString());
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public Player findPlayer(String color, Map m) {
    HashSet<Player> list = m.getPlayer();
    Iterator<Player> it = list.iterator();
    while (it.hasNext()) {
      Player pyr = it.next();
      if (pyr.getName().equals(color)) {
        return pyr;
      }
    }
    return null;
  }


  public void playerUnitSetting(String unitString, Player player) {

    ArrayList<Integer> unitList = new ArrayList<>();
    char[] tmp = unitString.toCharArray();

    //unitString -> array
    int k = 0;
    int digit = 0;
    while (k < tmp.length) {

      if (Character.isDigit(tmp[k])) {
        digit = 0;
        while (k < tmp.length && Character.isDigit(tmp[k])) {
          digit = digit * 10 + Character.getNumericValue(tmp[k]);
          k++;
        }
      }
      unitList.add(digit);
      k++;
    }
    int i = 0;
    for (Territory ter : player.getTerritoryList()) {
      ter.setUnit(unitList.get(i));
      i++;
    }
  }

  /**
   * Creates an Action given String input from client
   *
   * @param map Map we are checking to see if Territory and Player exists
   * @param action String input from client to be converted to Action
   * @param isMove boolean to indicate if creating MoveAction or AttackAction
   * @return Action as indicated by client
   */
  public Action createAction(Map map, String color, String action, boolean isMove) {
    int numUnits = -1;

    String[] words = action.split(" ");
    Player player = map.findPlayer(color);
    Territory source = map.findTerritory(words[0]);
    Territory destination = map.findTerritory(words[1]);
    try {
      numUnits = Integer.parseInt(words[2]);
    } catch (NumberFormatException e) {
    }

    if (source == null || destination == null) {
      return null;
    }

    if (isMove) {
      return new MoveAction(player, source, destination, numUnits);
    } else {
      return new AttackAction(player, source, destination, numUnits);
    }
  }

  public String getPlayer(String action) {
    String[] words = action.split(" ");
    return words[0];
  }

  public String validActionSet(Player player, HashSet<MoveAction> moves, HashSet<AttackAction> attacks) {
    //Once we first meet the problem, then reenter with "Done", moves and attacks would be "NULL"
    if(moves.isEmpty() && attacks.isEmpty()){
      return null;
    }
    for (MoveAction move : moves) {
      if (move == null) {
        return "This action is invalid: Territory does not exist";
      }
      String error = move.canPerformAction();
      if (error != null) {
        return error;
      }
    }

    for (AttackAction attack : attacks) {
      if (attack == null) {
        return "This action is invalid: Territory does not exist";
      }
      String error = attack.canPerformAction();
      if (error != null) {
        return error;
      }
    }

    for (Territory territory : player.getTerritoryList()) {
      if (!territory.mockIsValid()) {
        return "These actions are invalid: " + territory.getName()
            + " territory ends with negative units";
      }
    }

    return null;
  }


  /**
   * play all received attacks
   *
   * @param attacks received attacks
   */
  private void playAttacks(Map map, HashSet<AttackAction> attacks) {
    Battle battle = new Battle(map);
    for (AttackAction att : attacks) {
      battle.addAttackAction(att);
    }
    battle.playBattlePhase();
  }

  //boolean
  public static <objectInputStream> void main(String[] args) {

    MapFactory f = new MapFactory();
    Map m = f.makeMapForThree();
    int player_num = 3;
    App app = new App(m);
    ArrayList<Socket> socketList = new ArrayList<Socket>();
    ArrayList<String> playerList = new ArrayList<String>();
    Socket socket = null;

    //debug
    ArrayList<ObjectInputStream> InputList = new ArrayList<ObjectInputStream>();
    ArrayList<ObjectOutputStream> OutputList = new ArrayList<ObjectOutputStream>();

    try (ServerSocket ss = new ServerSocket(6666)) {
      for (int i = 0; i < player_num; i++) {
        Socket s = ss.accept();
        socketList.add(s);
      }

      for (int i = 0; i < socketList.size(); i++) {
        //add the checker
        ActionRuleChecker tmp = new ActionRuleChecker();

        socket = socketList.get(i);

        //send map object
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(m);
        OutputList.add(objectOutputStream);

        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        InputList.add(objectInputStream);

        app.selectColor(objectOutputStream);
        //check if the color selection is valid
        String color = "";
        while (true) {

          //boolean for color checking

          String color_correct = "true";
          //InputStream inputStream = socket.getInputStream();
          //ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
          color = (String) objectInputStream.readObject();
          // add the checker
          //if everything is good, we will send "true" to the client

          //System.out.println(color);
          while (tmp.checkColor(color, remainingColors) != null) {

            //System.out.println(tmp.checkColor(color, remainingColors));
            color_correct = "false";

            objectOutputStream.writeObject("false");

            //read the new color from the client
            color = (String) objectInputStream.readObject();
          }

          if (tmp.checkColor(color, remainingColors) == null) {
            color_correct = "true";
            objectOutputStream.writeObject(color_correct);
            app.deleteColor(color);
            playerList.add(color);
            break;
          }
        }
        //read unit assignment
        app.unitSetting(objectOutputStream, app.findPlayer(color, m));
        String unitString = "";
        while (true) {
          String unit_correct = "true";
          //InputStream inputStream = socket.getInputStream();
          //ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

          unitString = (String) objectInputStream.readObject();

          // add the checker

          while (tmp.checkUnit(unitString, app.findPlayer(color, m)) != null) {
            //System.out.println(unitString);
            unit_correct = "false";
            objectOutputStream.writeObject("false");
            unitString = (String) objectInputStream.readObject();
            //objectOutputStream.reset();

          }

          if (tmp.checkUnit(unitString, app.findPlayer(color, m)) == null) {
            unit_correct = "true";
            objectOutputStream.writeObject(unit_correct);
            //objectOutputStream.reset();
            System.out.println(unitString);
            app.playerUnitSetting(unitString, app.findPlayer(color, m));
            break;
          }

        }
      }

//-------------------end of initial placement----------------//

      // initial sets for player actions
      //ActionSet actionSet = new ActionSet();
      HashSet<MoveAction> allMoves = new HashSet<>();
      HashSet<AttackAction> allAttack = new HashSet<>();

      // if we find the winner, notify the winner he wins the game;
      // notify the other players game over
      while (m.getGameWinner() == null) {
       // no winner detected, game continuing
        for (int i = 0; i < socketList.size(); i++) {
          OutputList.get(i).reset();
          OutputList.get(i).writeObject(m);
          System.out.println("Send map");
        }
        for(int i = 0 ; i < socketList.size(); i++) {
          // notify player game not over yet
          OutputList.get(i).writeObject("game continuing");
          System.out.println("Write end game flag to player");

          String action = (String)InputList.get(i).readObject();

          //debug
          System.out.println("the action is：" + action);


          Player player = app.findPlayer(playerList.get(i), m);
          player.setLoseStatus(action);

          //debug
          System.out.println("player's setLoseStatus:" + player.getLoseStatus());

          if (player.isLose()) {
            if (player.getLoseStatus().equals("quit") && m.getPlayer().contains(player)) {
              //remove it from player list
              //auto set empty actionSet
              //m.removePlayer(player);
              System.out.println("Bye bye I quit");
              //m.removePlayer(player);
              socketList.remove(i);
              InputList.remove(i);
              OutputList.remove(i);
              playerList.remove(i);
              
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
                //debug
                System.out.println("传回给server的move是" + move);
              }

              HashSet<String> actionListAttack = actionSet.getAttackList();
              for (String attack : actionListAttack) {
                attackActions.add((AttackAction) app.createAction(m, playerList.get(i), attack, false));
                //debug
                System.out.println("传回给server的move是" + attack);
              }

              //moveActions  attackActions need to be reset in the next round.
              String actionProblem = app.validActionSet(m.findPlayer(playerList.get(i)), moveActions, attackActions);

              //debug：here should be reset
              OutputList.get(i).reset();
              OutputList.get(i).writeObject(actionProblem);
              if (actionProblem == null) {
                allMoves.addAll(moveActions);
                allAttack.addAll(attackActions);
                break;
              }
              else{

              }
              System.out.println("There are problems with this client's setting, send information back to the server");
            }
          }

          // real execute for thr move action
          //
        }

        for (MoveAction act : allMoves) {
          act.performAction();
        }
        allMoves.clear();
        //real execute for te attack action
        app.playAttacks(m,allAttack);
        allAttack.clear();
        for (Territory territory : m.getList()) {
          territory.addUnit();
        }
      }


      //------------------------------------------Winner part-------------------------------//
      Player winner = m.getGameWinner();
      for (int i = 0; i < socketList.size(); i++) {
        OutputList.get(i).reset();
        OutputList.get(i).writeObject(m);
        System.out.println("Send map : there is a winner.");

        if (winner.equals(app.findPlayer(playerList.get(i), m))) {
          OutputList.get(i).reset();
          OutputList.get(i).writeObject("win");
          System.out.println("write win to player");
          } 
        else{  
          OutputList.get(i).reset();
          OutputList.get(i).writeObject("game over");
          System.out.println("write game over to player");
        }
        socketList.get(i).close();
      }
      System.out.println("Final point");
      TimeUnit.SECONDS.sleep(20);
      ss.close();

    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
;
