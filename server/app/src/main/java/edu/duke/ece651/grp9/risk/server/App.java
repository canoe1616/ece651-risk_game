/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.grp9.risk.server;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.io.IOException;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import edu.duke.ece651.grp9.risk.shared.*;
import edu.duke.ece651.grp9.risk.shared.Map;

public class App {

  private static ArrayList<String> remainingColors;
  private static HashMap<String, String> userPassPairs;
  private static ArrayList<Socket> socketList;
  private static ArrayList<String> playerList;
  private static ArrayList<ObjectInputStream> InputList;
  private static ArrayList<ObjectOutputStream> OutputList;
  private static HashSet<MoveAction> allMoves = new HashSet<>();
  private static HashSet<AttackAction> allAttacks = new HashSet<>();
  private static HashSet<UpgradeAction> allUpgrades = new HashSet<>();



  public static <objectInputStream> void main(String[] args) {
    try{
      InputStream inputStream;
      ObjectInputStream objectInputStream;
      OutputStream outputStream;
      ObjectOutputStream objectOutputStream;
      int room_id;
      RoomThread roomThread;
      ArrayList<RoomThread> AllThreadList = new ArrayList<>();
      Socket socket;
      GamePlay gameplay = new GamePlay();
      //  four rooms need to be shared by all players
      Room room_1 = new Room(2);
      Room room_2 = new Room(3);
      Room room_3 = new Room(4);
      Room room_4 = new Room(5);

      // four room thread.

      RoomThread roomThread1 = new RoomThread(room_1);
      RoomThread roomThread2 = new RoomThread(room_2);
      RoomThread roomThread3 = new RoomThread(room_3);
      RoomThread roomThread4 = new RoomThread(room_4);


      //对于AllThreadList 来说


      //最外层的部分是
      //如果所有的room 都满了

      while (room_1.isFull() && room_2.isFull() && room_3.isFull() && room_4.isFull())) {
          //当有新的player连接起来的时候
          //设置一个窗口 -> 告诉我们所有的user 说所有的房间已经满了
          UserThread userThread = new UserThread(room_1, room_2, room_3, room_4, roomThread1, roomThread2, roomThread3, roomThread4);
          userThread.start();
        }

      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }}









