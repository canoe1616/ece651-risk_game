package edu.duke.ece651.grp9.risk.shared;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class GamePlayTest {

  @Test
  public void test_findPlayer() {
    MapFactory factory = new MapFactory();
    Map map = factory.makeMapForTest();
    GamePlay app = new GamePlay();

    assertEquals(app.findPlayer("red", map), new Player("red"));
    assertEquals(app.findPlayer("blue", map), new Player("blue"));
    assertEquals(app.findPlayer("yellow", map), null);
  }


  @Test
  public void test_playAttacks() {
    MapFactory factory = new MapFactory();
    Map map = factory.makeMapForTest();

    Player p1 = map.findPlayer("red");
    Territory t1 = map.findTerritory("A");
    Territory t2 = map.findTerritory("C");

    GamePlay app1 = new GamePlay();
    app1.playerUnitSetting("10 10", p1);

    HashSet<AttackAction> attacks = new HashSet<>();
    attacks.add(new AttackAction(p1, t1, t2, 3));
    assertEquals(t1.getUnit(), 10);

    app1.playAttacks(map, attacks);

    assertEquals(t1.getUnit(), 7);
  }

  @Test
  @Timeout(5)
  public void test_gameWinner() throws IOException, InterruptedException {
    MapFactory factory = new MapFactory();
    Map map = new Map();
    Territory t1 = new Territory("Two Rivers");
    Player p1 = new Player("red");
    Player p2 = new Player("blue");
    p1.addTerritory(t1);
    map.addTerritory(t1);
    map.addPlayer(p1);
    map.addPlayer(p2);
    GamePlay app1 = new GamePlay();
    ServerSocket ss = new ServerSocket(6666);

    Thread th = new Thread() {
      @Override()
      public void run() {
        try {
          Socket client = new Socket("localhost", 6666);

          InputStream inputStream = client.getInputStream();
          ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

          Map map = (Map) objectInputStream.readObject();
          String winner = (String) objectInputStream.readObject();

          assertEquals(winner, "win");

        } catch (Exception e) {
          System.out.println("Connection error.");
        }
      }
    };
    th.start();
    Thread.sleep(1000);

    Socket s = ss.accept();
    System.out.println("connection");

    OutputStream outputStream = s.getOutputStream();
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
    app1.gameWinner(objectOutputStream, p1.getName(), map);

    Thread th2 = new Thread() {
      @Override()
      public void run() {
        try {
          Socket client = new Socket("localhost", 6666);

          InputStream inputStream = client.getInputStream();
          ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

          Map map = (Map) objectInputStream.readObject();
          String loser = (String) objectInputStream.readObject();

          assertEquals(loser, "game over");

        } catch (Exception e) {
          System.out.println("Connection error.");
        }
      }
    };
    th2.start();
    Thread.sleep(1000);

    Socket s2 = ss.accept();
    System.out.println("connection");

    OutputStream outputStream2 = s2.getOutputStream();
    ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(outputStream2);
    app1.gameWinner(objectOutputStream2, "blue", map);

    th.interrupt();
    th.join();

    th2.interrupt();
    th2.join();

    ss.close();
  }

  @Test
  public void test_validActionSet() {
    MapFactory factory = new MapFactory();
    Map map = factory.makeMapForTest();
    GamePlay app1 = new GamePlay();
    Player p1 = app1.findPlayer("red", map);

    HashSet<MoveAction> moves = new HashSet<>();
    HashSet<AttackAction> attacks = new HashSet<>();
    HashSet<UpgradeAction> upgrades = new HashSet<>();
    moves.add((MoveAction) app1.createAction(map, "red", "A B 5 0", true));
    String error1 = "These actions are invalid: A territory ends with negative units";
    assertEquals(app1.validActionSet(p1, moves, attacks, upgrades, false), error1);
    moves.clear();

    moves.add((MoveAction) app1.createAction(map, "red", "A F 5 0", true));
    String error2 = "This action is invalid: Territory does not exist";
    assertEquals(app1.validActionSet(p1, moves, attacks, upgrades, false), error2);
    moves.clear();

    attacks.add((AttackAction) app1.createAction(map, "red", "A F 5 0", false));
    assertEquals(app1.validActionSet(p1, moves, attacks, upgrades, false), error2);
    attacks.clear();

    moves.add((MoveAction) app1.createAction(map, "red", "A C 3 0", true));
    String error3 = "This action is invalid: A is not connected to C.";
    assertEquals(app1.validActionSet(p1, moves, attacks, upgrades, false), error3);
    moves.clear();

    attacks.add((AttackAction) app1.createAction(map, "red", "A B 5 0", false));
    String error4 = "This action is invalid: you cannot attack your own Territory.";
    assertEquals(app1.validActionSet(p1, moves, attacks, upgrades, false), error4);
    moves.clear();
    attacks.clear();

    for (Territory t : p1.getTerritoryList()) {
      t.syncUnits();
    }
    moves.add((MoveAction) app1.createAction(map, "red", "A B 0 0", true));
    attacks.add((AttackAction) app1.createAction(map, "red", "A C 0 0", false));
    assertEquals(app1.validActionSet(p1, moves, attacks, upgrades, false), null);

    for (Territory t : p1.getTerritoryList()) {
      t.setUnits(100, 0);
    }

    upgrades.add((UpgradeAction) app1.createUpgrade(map, "red", "A 5 0 2"));
    assertEquals("This action is invalid: Your technology level is not yet at level 2.",
        app1.validActionSet(p1, moves, attacks, upgrades,false));
    p1.upgradeTechLevel();
    assertEquals(null, app1.validActionSet(p1, moves, attacks, upgrades, false));

    // check food resource
    moves.add((MoveAction) app1.createAction(map, "red", "A B 20 0", true));
    moves.add((MoveAction) app1.createAction(map, "red", "A B 20 0", true));
    moves.add((MoveAction) app1.createAction(map, "red", "A B 20 0", true));
    String exp = "Do not have enough food to do move or attack orders";
    assertEquals(exp, app1.validActionSet(p1, moves, attacks, upgrades, false));

    moves.clear();
    // check money resource
    upgrades.add((UpgradeAction) app1.createUpgrade(map, "red", "A 100 0 4"));
    p1.upgradeTechLevel();
    p1.upgradeTechLevel();
    p1.upgradeTechLevel();
    exp = "Do not have enough money to do upgrade orders";
    assertEquals(exp, app1.validActionSet(p1, moves, attacks, upgrades, false));
  }

  @Test
  public void test_validActionSet_withTechLevel() {
    MapFactory factory = new MapFactory();
    Map map = factory.makeMapForTwo();
    GamePlay app1 = new GamePlay();
    Player p1 = app1.findPlayer("red", map);
    p1.setMoneyQuantity(500);

    HashSet<MoveAction> moves = new HashSet<>();
    HashSet<AttackAction> attacks = new HashSet<>();
    HashSet<UpgradeAction> upgrades = new HashSet<>();

    assertEquals(p1.getTechLevel(), 1);
    assertEquals(p1.getMoneyQuantity(), 500);

    assertEquals(null, app1.validActionSet(p1, moves, attacks, upgrades, true));

    Action action = new TechAction(p1);
    action.performAction();

    assertEquals(p1.getTechLevel(), 2);
    assertEquals(p1.getMoneyQuantity(), 450);

    p1.upgradeTechLevel();
    p1.upgradeTechLevel();
    p1.upgradeTechLevel();
    p1.upgradeTechLevel();

    assertEquals(p1.getTechLevel(), 6);

    String error = "This action is invalid: You are already at the max tech level.";

    assertEquals(error, app1.validActionSet(p1, moves, attacks, upgrades, true));
  }


  @Disabled
  @Test
  //TODO THIS WAS GOING ON FOREVER
  public void test_unitSetting() throws InterruptedException, IOException {
    ServerSocket ss = new ServerSocket(6666);
    MapFactory factory = new MapFactory();
    Map map = factory.makeMapForTest();
    GamePlay app1 = new GamePlay();

    Thread th = new Thread() {
      @Override()
      public void run() {
        try {
          Socket client = new Socket("localhost", 6666);

          InputStream inputStream = client.getInputStream();
          ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

          String receive = (String) objectInputStream.readObject();

          String expected = "You have 2 territories: A B \nYou have 30 total units, how do you want to place the units?";
          assertEquals(receive, expected);

        } catch (Exception e) {
          System.out.println("Connection error.");
        }
      }
    };
    th.start();
    Thread.sleep(1000);

    //create new socket
    Socket s = ss.accept();
    System.out.println("connection");

    OutputStream outputStream = s.getOutputStream();
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

    app1.unitSetting(objectOutputStream, map.findPlayer("red"));
    System.out.println("Receiving red");

    th.interrupt();
    th.join();
    ss.close();
  }


  @Test
  void playMoves() {
    MapFactory factory = new MapFactory();
    Map map = factory.makeMapForTest();

    Player p1 = map.findPlayer("red");
    Territory t1 = map.findTerritory("A");
    Territory t2 = map.findTerritory("B");

    GamePlay app1 = new GamePlay();
    app1.playerUnitSetting("10 10", p1);

    HashSet<MoveAction> moves = new HashSet<>();
    moves.add(new MoveAction(p1, t1, t2, 3, 0));
    assertEquals(t1.getUnits(0), 10);

    app1.playMoves(moves);

    assertEquals(t1.getUnits(0), 7);
    assertEquals(t2.getUnits(0), 13);
  }

  @Test
  void playUpgrades() {
    MapFactory factory = new MapFactory();
    Map map = factory.makeMapForTest();

    Player p1 = map.findPlayer("red");
    Territory t1 = map.findTerritory("A");
    Territory t2 = map.findTerritory("B");

    GamePlay app1 = new GamePlay();
    app1.playerUnitSetting("10 10", p1);

    HashSet<UpgradeAction> upgrades = new HashSet<>();
    upgrades.add(new UpgradeAction(p1, t1, 5, 0, 1));
    assertEquals(t1.getUnits(0), 10);

    app1.playUpgrades(upgrades);

    assertEquals(5, t1.getUnits(0));
    assertEquals(5, t1.getUnits(1));
  }


  public void playTechLevels(HashSet<TechAction> allTechLevels) {
    for (TechAction techAction: allTechLevels) {
      if (techAction != null) {
        techAction.performAction();
      }
    }
  }

    @Test
    void playTechLevels() {
      MapFactory factory = new MapFactory();
      Map map = factory.makeMapForTest();

      Player p1 = map.findPlayer("red");

      GamePlay app = new GamePlay();
      HashSet<TechAction> allTechLevels = new HashSet<>();
      TechAction action = new TechAction(p1);
      allTechLevels.add(action);
      app.playTechLevels(allTechLevels);
      assertEquals(2, p1.getTechLevel());


    }
}
