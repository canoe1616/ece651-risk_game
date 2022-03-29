/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.grp9.risk.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
//import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Iterator;
import edu.duke.ece651.grp9.risk.shared.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



public class App extends Application{
  private final BufferedReader inputReader;
  private String username;
  private String password;

  public App(BufferedReader inputSource) {
    this.inputReader = inputSource;
  }


  @Override
  public void start(Stage stage) {
    String javaVersion = System.getProperty("java.version");
    String javafxVersion = System.getProperty("javafx.version");
    Label l = new Label("Hello, JavaFX " + javafxVersion +
            ", running on Java " +
            javaVersion + ".");
    Scene scene = new Scene(new StackPane(l), 640, 480);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * this method is to ask player select a color
   * @param inputSource is the input buffer reader
   * @param inStream is the input stream
   * @param outStream is the output stream
   * @return the player selected color
   * @throws IOException
   */
  public String selectColor(BufferedReader inputSource, ObjectInputStream inStream, ObjectOutputStream outStream) throws IOException {
    String s = "";
    //get prompt and print it
    try {
      String colorPrompt = (String) inStream.readObject();
      System.out.println(colorPrompt);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    String color_correct = "false";

    while (!color_correct.equals("true")) {
      try {
        s = inputSource.readLine();
        System.out.println(s);
        //check color valid
        //From server -> color_correct
        outStream.writeObject(s);
        color_correct = (String) inStream.readObject();
        //System.out.println(color_correct);
        if (color_correct.equals("false")) {
          System.out.println("Invalid color selection, please enter again!");
        }
      } catch (Exception exception) {
        System.out.println(exception.getMessage());
      }
    }
    return s;
  }

  /**
   * this method is to ask player select unit numbers
   * @param inputSource is the input buffer reader
   * @param inStream is the input stream
   * @param outStream is the output stream
   * @return the player's input unit setting
   * @throws IOException
   */
  public String selectUnit(BufferedReader inputSource, ObjectInputStream inStream, ObjectOutputStream outStream) throws IOException {
    String s = "";
    //get prompt and print it
    try {
      String unitPrompt = (String) inStream.readObject();
      System.out.println(unitPrompt);
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
    }

    String unit_correct = "false";

    while (!unit_correct.equals("true")) {
      try {
        s = inputSource.readLine();
        System.out.println(s);
        outStream.writeObject(s);
        unit_correct = (String) inStream.readObject();
        if (unit_correct.equals("false")) {
          System.out.println("Invalid unit selection, please enter again!");
        }
      } catch (Exception exception) {
        System.out.println(exception.getMessage());
      }
    }
    return s;
  }

  /**
   * given the player color and the map, this method is to find the play
   * @param color is the player's color
   * @param m is the map of the game
   * @return the player in color on map m
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
   * this method is to ask the player select his option after losing the game
   */
  public String selectStateAfterLose(BufferedReader inputSource, String color) throws IOException {
    System.out.println(color + ", you lose the game!"
            + " What would you like to do?\n" +
            " (Q)uit\n" + " (C)ontinue watching game\n");
    String action = inputSource.readLine();
    String checker = getLoseActionString(action);
    while(checker != null){
      action = inputSource.readLine();
      checker = getLoseActionString(action);
      System.out.println("Not a valid input, type again:");
    }
    //send selection to server
    if (action.equals("Q") || action.equals("q")){
      action = "quit";
    }
    else if (action.equals("C") || action.equals("c")){
      action = "continue";
    }
    return action;
  }

  /**
   * get player's options when a player loses a game
   * @param action player's option, Q for quit, C for continue watching
   * @return a string for action
   */
   public String getLoseActionString(String action){
     ActionRuleChecker arc = new ActionRuleChecker();
     return arc.checkLoseAction(action);
   }

  /**
   * get player's actions for game
   * @param action player's actions, A, a, M, m, D, d are valid.
   * @return a string for action
   */
    public String getActionString(String action) {
      ActionRuleChecker arc = new ActionRuleChecker();
      return arc.checkAction(action);
    }



    /**
     * to record each player's username and password
    Q:是否需要考虑多个空格的情况
     */
    public String getUsername(BufferedReader inputSource,ObjectOutputStream outStream) throws IOException{
      try {
        System.out.println("please enter the username and the password (e.g Lucy 123456");
        String s = inputSource.readLine();
        String[] words = s.split(" ");
        this.username = words[0];
        this.password = words[1];
        while(username == null || password == null){
          System.out.println("input invalid, please enter again");
          s = inputSource.readLine();
          words = s.split(" ");
          this.username = words[0];
          this.password = words[1];
        }
      }
      catch (Exception exception) {
        System.out.println(exception.getMessage());
      }
      //sending username and password to the server
      outStream.reset();
      outStream.writeObject(username);
      outStream.reset();
      outStream.writeObject(password);


      //if the username and the password, they are not match with each other

      return null;

    }



  public static void main(String[] args) {

    BufferedReader inputSource = new BufferedReader(new InputStreamReader(System.in));
    App app = new App(inputSource);


    //build be hashset<string> for actions for the server?
    HashSet<String> actionListMove = new HashSet<>();
    HashSet<String> actionListAttack = new HashSet<>();
    ActionSet actionSet = new ActionSet();
    try {
      Socket socket = new Socket("localhost", 6666);
      //receive map from server
      InputStream inputStream = socket.getInputStream();
      ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
      Map myMap = (Map) objectInputStream.readObject();
      System.out.println("Receive Map form server.");

      //sent color
      OutputStream outputStream = socket.getOutputStream();
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

      String color = app.selectColor(inputSource, objectInputStream, objectOutputStream);
      String unitString = app.selectUnit(inputSource, objectInputStream, objectOutputStream);

      ///////////////////////end of initial placement/////////////

      System.out.println("Its next step...");

      while(true){//until you lose or you win.
        myMap = (Map) objectInputStream.readObject();//
        System.out.println("Receive Map form server.");
        MapTextView mtv = new MapTextView(myMap);
        String endGame = (String) objectInputStream.readObject();//
        System.out.println("Read state.");
        if (endGame.equals("win")){
          String res = mtv.sendInfoWinner(color, myMap);
          System.out.println(res);
          socket.close();
          break;
        }
        else if (endGame.equals("game over")){
          String res = mtv.sendInfoLoser(color, myMap);
          System.out.println(res);
          socket.close();
          break;
        }
        else{//countinue
          
          if(myMap.findPlayer(color).isLose() && myMap.findPlayer(color).getLoseStatus().equals("no act")){
            String action = app.selectStateAfterLose(inputSource, color);
            objectOutputStream.writeObject(action);
            //change it at local player.
            myMap.findPlayer(color).setLoseStatus(action);
          }
          else if(myMap.findPlayer(color).isLose() && myMap.findPlayer(color).getLoseStatus().equals("quit")){
            System.out.println("Bye bye I quit");
            objectOutputStream.writeObject("quit");
            socket.close();
            break; //close the game here
          }
          else if(myMap.findPlayer(color).isLose() && myMap.findPlayer(color).getLoseStatus().equals("continue")){
             String gameStateInitial = mtv.displayGameState(myMap.findPlayer(color));
             System.out.println(gameStateInitial);
             objectOutputStream.writeObject("continue");
          }
          else{
            String gameStateInitial = mtv.displayGameState(myMap.findPlayer(color));
            System.out.println(gameStateInitial);
            objectOutputStream.writeObject("no act");
            //allow client typing
            String action = null;
            ActionRuleChecker arc = new ActionRuleChecker();
            while(true) {//while loop until valid input
              System.out.println("You are the " + color + " Player, what would you like to do?\n  (M)ove\n  (A)ttack\n  (D)one");
              action = inputSource.readLine();
              String actionChoiceError;
              while ((actionChoiceError = app.getActionString(action)) != null) {
                System.out.println(actionChoiceError);
                action = inputSource.readLine();
              }

              //To ask user input the action until we meet the "D"
              if (action.equals("D") || action.equals("d")) {
                actionSet.actionListMove = actionListMove;
                actionSet.actionListAttack = actionListAttack;

                objectOutputStream.reset();
                objectOutputStream.writeObject(actionSet);
                System.out.println("Sent actionSet to the server.");
                String actionProblem = (String) objectInputStream.readObject();
                actionListMove.clear();
                actionListAttack.clear();
                if (actionProblem == null) {
                  break;
                } else {
                  System.out.println(actionProblem + "\nPlease reenter your Actions again.");
                }
              }
              else if (action.equals("m") || action.equals("M")) {
                //call the move function here
                System.out.println("Please enter as this following format: Source, Destination, MoveUnits(e.g A B 10");
                String action_input = inputSource.readLine();
                actionListMove.add(action_input);
              }
              else if (action.equals("a") || action.equals("A")) {
                //call the move function here
                System.out.println("Please enter as this following format: Source, Destination, AttackUnits(e.g A B 10");
                String action_input = inputSource.readLine();
                actionListAttack.add(action_input);
              }

            }
          }
        }
      }
    }
      catch (UnknownHostException e) {
      e.printStackTrace();
      }
      catch (IOException e) {
      e.printStackTrace();
      }
      catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
