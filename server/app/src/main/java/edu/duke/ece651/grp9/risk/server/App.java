/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.grp9.risk.server;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashSet;

import java.net.ServerSocket;
import java.net.Socket;
import edu.duke.ece651.grp9.risk.shared.Map;
//import edu.duke.ece651.grp9.risk.shared.MapPackage;
//import edu.duke.ece651.grp9.risk.shared.Message;

public class App {
  private HashSet<String> remainingColors;

  public App() {
    remainingColors = new HashSet<>();
    remainingColors.add("red");
    remainingColors.add("blue");
    remainingColors.add("green");
  }

  public void selectColor(ObjectOutputStream stream) {
    StringBuilder sb = new StringBuilder();
    sb.append("Please select what color you would like to play as: ");
    for (String color : remainingColors) {
      sb.append(color + " ");
    }
    try {
      stream.writeObject(sb.toString());
    } catch(Exception e) {
      System.out.println(e);
    }
  }

  public static void main(String[] args) {
    App app = new App();

    Map myMap = new Map();
    myMap.buildTestMap();
    try { 
      ServerSocket ss = new ServerSocket(6666);
      Socket socket = ss.accept();

      OutputStream outputStream = socket.getOutputStream();
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
      objectOutputStream.writeObject(myMap);

      app.selectColor(objectOutputStream);

      InputStream inputStream = socket.getInputStream();
      ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
      String color = (String)objectInputStream.readObject();
      System.out.println(color);

      socket.close();
      ss.close();
    } catch(Exception e){
      System.out.println(e);
    }
  }
}
