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

import edu.duke.ece651.grp9.risk.shared.ActionRuleChecker;
import edu.duke.ece651.grp9.risk.shared.Map;
import edu.duke.ece651.grp9.risk.shared.MapTextView;
import edu.duke.ece651.grp9.risk.shared.Player;

public class App {
  private final BufferedReader inputReader;

  public App(BufferedReader inputSource) {
    this.inputReader = inputSource;
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


  public static void main(String[] args) {

    BufferedReader inputSource = new BufferedReader(new InputStreamReader(System.in));
    App app = new App(inputSource);

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

      ActionRuleChecker arc = new ActionRuleChecker();
      System.out.println("Its next step...");
      //while(true){
      myMap = (Map) objectInputStream.readObject();
      System.out.println("Receive Map form server.");

      MapTextView mtv = new MapTextView(myMap);
      String gameStateInitial = mtv.displayGameState(null, app.findPlayer(color, myMap));
      System.out.println(gameStateInitial);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}