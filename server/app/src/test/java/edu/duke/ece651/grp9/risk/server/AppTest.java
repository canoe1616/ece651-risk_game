/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.grp9.risk.server;

import edu.duke.ece651.grp9.risk.shared.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void availableRoom() {

        App app1 = new App();
        ArrayList<Room> avail_1 = app1.availableRoom();
        assertEquals(4, avail_1.size());
    }

//  @Test
//  public void test_constructor() {
//    MapFactory factory = new MapFactory();
//    Map map = factory.makeMapForTwo();
//    App app1 = new App(map);
//  }

  //ss.accept() and send object flush close - client mimic server inside
  //only have to receive and make sure it is expected - don't worry about what is sent
  //server we mimic client side inside thread and create server socket first outside thread
  //set up byte array to receive outcome
//  @Test
//  @Timeout(5)
//  public void test_selectColor() throws IOException, InterruptedException, ClassNotFoundException {
//    ServerSocket ss = new ServerSocket(6666);
//    MapFactory factory = new MapFactory();
//    Map map = factory.makeMapForTwo();
//    App app1 = new App(map);
//
//    Thread th = new Thread() {
//      @Override()
//      public void run() {
//        try {
//          Socket client = new Socket("localhost", 6666);
//
//          OutputStream outputStream = client.getOutputStream();
//          ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
//
//          //String message = (String) objectInputStream.readObject();
//          System.out.println("Sending red");
//
//          objectOutputStream.writeObject("red");
//
//          InputStream inputStream = client.getInputStream();
//          ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
//
//          String receive = (String) objectInputStream.readObject();
//
//          String expected = "Please select what color you would like to play as: red blue ";
//          assertEquals(receive, expected);
//
//        } catch (Exception e) {
//          System.out.println("Connection error.");
//        }
//      }
//    };
//    th.start();
//    Thread.sleep(1000);
//
//    //create new socket
//    Socket s = ss.accept();
//    System.out.println("connection");
//
//    InputStream inputStream = s.getInputStream();
//    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
//    OutputStream outputStream = s.getOutputStream();
//    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
//
//
//    app1.selectColor(objectOutputStream);
//    System.out.println("Receiving red");
//    String color = (String)objectInputStream.readObject();
//
//    assertEquals(color, "red");
//
//    th.interrupt();
//    th.join();
//    ss.close();
//  }

//  @Test
//  public void test_unitSetting() throws InterruptedException, IOException {
//    ServerSocket ss = new ServerSocket(6666);
//    MapFactory factory = new MapFactory();
//    Map map = factory.makeMapForTwo();
//    App app1 = new App(map);
//
//    Thread th = new Thread() {
//      @Override()
//      public void run() {
//        try {
//          Socket client = new Socket("localhost", 6666);
//
//          InputStream inputStream = client.getInputStream();
//          ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
//
//          String receive = (String) objectInputStream.readObject();
//
//          String expected = "You have 2 territories: A B \nYou have 30 total units, how do you want to place the units?";
//          assertEquals(receive, expected);
//
//        } catch (Exception e) {
//          System.out.println("Connection error.");
//        }
//      }
//    };
//    th.start();
//    Thread.sleep(1000);
//
//    //create new socket
//    Socket s = ss.accept();
//    System.out.println("connection");
//
//    OutputStream outputStream = s.getOutputStream();
//    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
//
//
//    app1.unitSetting(objectOutputStream, map.findPlayer("red"));
//    System.out.println("Receiving red");
//
//    th.interrupt();
//    th.join();
//    ss.close();
//  }

//  @Test
//  public void test_findPlayer() {
//    MapFactory factory = new MapFactory();
//    Map map = factory.makeMapForTwo();
//    App app = new App(map);
//
//    assertEquals(app.findPlayer("red", map), new Player("red"));
//    assertEquals(app.findPlayer("blue", map), new Player("blue"));
//    assertEquals(app.findPlayer("yellow", map), null);
//  }
//
//
//
//  @Test
//  public void test_playerUnitSetting() {
//    MapFactory factory = new MapFactory();
//    Map map = factory.makeMapForTwo();
//
//    Player p1 = map.findPlayer("red");
//    App app1 = new App(map);
//
//    for (Territory t : p1.getTerritoryList()) {
//      assertEquals(t.getUnit(), 0);
//    }
//
//    app1.playerUnitSetting("10 10", p1);
//    for (Territory t : p1.getTerritoryList()) {
//      assertEquals(t.getUnit(), 10);
//    }
//  }
//
//  @Test
//  public void test_createAction() {
//    MapFactory factory = new MapFactory();
//    Map map = factory.makeMapForTwo();
//    Player p1 = map.findPlayer("red");
//    App app1 = new App(map);
//
//    MoveAction move1 = (MoveAction) app1.createAction(map, "red", "A B 5", true);
//
//    assertEquals(move1.getPlayer(), p1);
//    assertNotEquals(move1, null);
//
//    MoveAction move2 = (MoveAction) app1.createAction(map, "red", "A F 5", true);
//    assertEquals(move2, null);
//
//    MoveAction move3 = (MoveAction) app1.createAction(map, "red", "A F a", true);
//    assertEquals(move3, null);
//  }
//
//  @Test
//  public void test_validActionSet() {
//    MapFactory factory = new MapFactory();
//    Map map = factory.makeMapForTwo();
//    App app1 = new App(map);
//    Player p1 = app1.findPlayer("red", map);
//
//    HashSet<MoveAction> moves = new HashSet<>();
//    HashSet<AttackAction> attacks = new HashSet<>();
//    moves.add((MoveAction) app1.createAction(map, "red", "A B 5", true));
//    String error1 = "These actions are invalid: A territory ends with negative units";
//    assertEquals(app1.validActionSet(p1, moves, attacks), error1);
//    moves.clear();
//
//    moves.add((MoveAction) app1.createAction(map, "red", "A F 5", true));
//    String error2 = "This action is invalid: Territory does not exist";
//    assertEquals(app1.validActionSet(p1, moves, attacks), error2);
//    moves.clear();
//
//    attacks.add((AttackAction) app1.createAction(map, "red", "A F 5", false));
//    assertEquals(app1.validActionSet(p1, moves, attacks), error2);
//    attacks.clear();
//
//    moves.add((MoveAction) app1.createAction(map, "red", "A C 3", true));
//    String error3 = "This action is invalid: A is not connected to C.";
//    assertEquals(app1.validActionSet(p1, moves, attacks), error3);
//    moves.clear();
//
//    attacks.add((AttackAction) app1.createAction(map, "red", "A B 5", false));
//    String error4 = "This action is invalid: you cannot attack your own Territory.";
//    assertEquals(app1.validActionSet(p1, moves, attacks), error4);
//    moves.clear();
//    attacks.clear();
//
//    for (Territory t : p1.getTerritoryList()) {
//      t.syncUnits();
//    }
//    moves.add((MoveAction) app1.createAction(map, "red", "A B 0", true));
//    attacks.add((AttackAction) app1.createAction(map, "red", "A C 0", false));
//    assertEquals(app1.validActionSet(p1, moves, attacks), null);
//  }
//
//  /*@Test
//  public void test_receiveActions() {
//    MapFactory factory = new MapFactory();
//    Map map = factory.makeMapForTwo();
//    Player p1 = map.findPlayer("red");
//    App app1 = new App(map);
//
//    HashSet<String> moveList = new HashSet<>();
//    actionList.add("A B 10");
//
//    HashSet<MoveAction> moves = app1.receiveActions(moveList, true);
//  }*/
//
}
