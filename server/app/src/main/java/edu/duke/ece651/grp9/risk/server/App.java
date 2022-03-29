/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.grp9.risk.server;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.IOException;

import edu.duke.ece651.grp9.risk.shared.*;
import edu.duke.ece651.grp9.risk.shared.Map;

public class App {

  private static HashSet<String> remainingColors;
  private static HashMap<String, String> userPassPairs;

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
   * @param m     Map we are searching for Player
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
   * @param player     Player whose units are being added to their Territories
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
   * @param map    Map we are checking to see if Territory and Player exists
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
      return new MoveAction(player, source, destination, numUnits, 0);
    } else {
      return new AttackAction(player, source, destination, numUnits, 0);
    }
  }

  /**
   * Checks is a set of Actions from client is valid
   *
   * @param player  Player who made actions
   * @param moves   MoveActions that are checked
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
   *
   * @param stream OutputStream for client
   * @param color  String indicating which Player
   * @param map    Map
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


  /**
   * to store the user_name and password that sent by the client
   */
  public String storeUserNameAndPassword(ObjectInputStream objectInputStream) throws IOException {
    try {
      String username = (String) objectInputStream.readObject();
      String password = (String) objectInputStream.readObject();
      //to test whether we have this username before or not and the password are matched or not.
      if (userPassPairs.containsKey(username)) {
        if (!userPassPairs.get(username).equals(password)) {
          return "the password you enter is invalid, please enter again";
        }
      } else {
        userPassPairs.put(username, password);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }


  //boolean
  public static <objectInputStream> void main(String[] args) {
    //no multi-threads the server should enter the number of players
    System.out.println("Please input the number of players you want, and should be 2-5");
    BufferedReader inputSource = new BufferedReader(new InputStreamReader(System.in));
    int player_num = 0;
    try {
      player_num = Integer.parseInt(inputSource.readLine());
      while (player_num < 2 || player_num > 5) {
        System.out.println("Must be 2-5. Please enter again");
        player_num = Integer.parseInt(inputSource.readLine());
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    //remaining color 必须要是全局变量
    MapFactory f = new MapFactory();
    Map m = f.makeMap(player_num);
    remainingColors = new HashSet<>();
    Iterator<Player> it = m.getPlayer().iterator();
    while(it.hasNext()){
      remainingColors.add(it.next().getName());
    }


    ArrayList<ServerThread> threadList = new ArrayList<>();


      try (ServerSocket ss = new ServerSocket(6666)) {
        while (true) {
          //add the checker
          Socket socket = ss.accept();
          ServerThread serverThread = new ServerThread(socket, threadList, remainingColors,m);
          threadList.add(serverThread);
          serverThread.start();
        }
      } catch (Exception e) {
        System.out.println(e);
      }

  }
}





