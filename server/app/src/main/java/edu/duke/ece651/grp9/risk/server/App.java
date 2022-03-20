/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.grp9.risk.server;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.io.IOException;

import edu.duke.ece651.grp9.risk.shared.*;


public class App {

  private static HashSet<String> remainingColors;
  private static ArrayList<Socket> socketList;
  private static ArrayList<String> playerList;
  private static ArrayList<ObjectInputStream> InputList;
  private static ArrayList<ObjectOutputStream> OutputList;

  public App(Map m) {
    remainingColors = new HashSet<>();
    Iterator<Player> it = m.getPlayer().iterator();
    while (it.hasNext()) {
      remainingColors.add(it.next().getName());
    }
    socketList = new ArrayList<Socket>();
    playerList = new ArrayList<String>();
    InputList = new ArrayList<ObjectInputStream>();
    OutputList = new ArrayList<ObjectOutputStream>();
  }

  /**
   * Allows client to select they color they want to play as
   *
   * @param stream socket connection to client from server
   */
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

  /**
   * Removes a color from remainingColors
   */
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

  /**
   * Find Player bin map based on color input
   *
   * @param color String color input
   * @param m Map we are searching for Player
   * @return returns null if no Player found, returns Player if found
   */
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

  /**
   * Set units for player's Territories based on input from Client
   *
   * @param unitString String of unit values from client
   * @param player Player whose units are being added to their Territories
   */
  public void playerUnitSetting(String unitString, Player player) {

    String[] words = unitString.split(" ");

    int i = 0;
    for (Territory ter : player.getTerritoryList()) {
      ter.setUnit(Integer.parseInt(words[i]));
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

  /**
   * Checks is a set of Actions from client is valid
   *
   * @param player Player who made actions
   * @param moves MoveActions that are checked
   * @param attacks AttackActions that are checked
   * @return null if no error, String describing problem if there is error
   */
  public String validActionSet(Player player, HashSet<MoveAction> moves,
      HashSet<AttackAction> attacks) {
    //Once we first meet the problem, then reenter with "Done", moves and attacks would be "NULL"
    if (moves.isEmpty() && attacks.isEmpty()) {
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
   * Sends message to client to indicate win or lose
   * @param stream OutputStream for client
   * @param color String indicating which Player
   * @param map Map
   */
  public void gameWinner(ObjectOutputStream stream, String color, Map map) throws IOException {
    stream.reset();
    stream.writeObject(map);
    System.out.println("Send map : there is a winner.");

    Player winner = map.getGameWinner();
    if (winner.equals(findPlayer(color, map))) {
      stream.reset();
      stream.writeObject("win");
      System.out.println("write win to player");
    } else {
      stream.reset();
      stream.writeObject("game over");
      System.out.println("write game over to player");
    }
    stream.close();
  }


  /**
   * play all received attacks
   *
   * @param attacks received attacks
   */
  public void playAttacks(Map map, HashSet<AttackAction> attacks) {
    Battle battle = new Battle(map);
    for (AttackAction att : attacks) {
      battle.addAttackAction(att);
    }
    battle.playBattlePhase();
  }

  //boolean
  public static <objectInputStream> void main(String[] args) {
    // BufferedReader inputSource = new BufferedReader(new InputStreamReader(System.in));
    // int player_num = Integer.parseInt(inputSource.readLine());
    // while(player_num<2 || player_num>5){
    //   System.out.println("Must be 2-5. Please enter again");
    //   player_num = Integer.parseInt(inputSource.readLine());
    // }

    // MapFactory f = new MapFactory();
    // Map m = f.makeMap(player_num);
    MapFactory f = new MapFactory();
  Map m = f.makeMapForFour();
  int player_num = 4;

    
    App app = new App(m);
    //ArrayList<Socket> socketList = new ArrayList<Socket>();
    //ArrayList<String> playerList = new ArrayList<String>();
    Socket socket = null;

    //debug
    //ArrayList<ObjectInputStream> InputList = new ArrayList<ObjectInputStream>();
    //ArrayList<ObjectOutputStream> OutputList = new ArrayList<ObjectOutputStream>();

    try (ServerSocket ss = new ServerSocket(6666)) {
      for (int i = 0; i < player_num; i++) {
        Socket s = ss.accept();
        socketList.add(s);
      }

      for (int i = 0; i < socketList.size(); i++) {
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
          color = (String) objectInputStream.readObject();
          // add the checker
          //if everything is good, we will send "true" to the client
          while (tmp.checkColor(color, remainingColors) != null) {

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
          unitString = (String) objectInputStream.readObject();

          // add the checker
          while (tmp.checkUnit(unitString, app.findPlayer(color, m)) != null) {
            objectOutputStream.writeObject("false");
            unitString = (String) objectInputStream.readObject();

          }

          if (tmp.checkUnit(unitString, app.findPlayer(color, m)) == null) {
            unit_correct = "true";
            objectOutputStream.writeObject(unit_correct);
            System.out.println(unitString);
            app.playerUnitSetting(unitString, app.findPlayer(color, m));
            break;
          }

        }
      }

//-------------------end of initial placement----------------//

      // initial sets for player actions
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

    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
;
