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

//  private static ArrayList<String> remainingColors;
//  private static HashMap<String, String> userPassPairs;
//  private static ArrayList<Socket> socketList;
//  private static ArrayList<String> playerList;
//  private static ArrayList<ObjectInputStream> InputList;
//  private static ArrayList<ObjectOutputStream> OutputList;
//  private static HashSet<MoveAction> allMoves = new HashSet<>();
//  private static HashSet<AttackAction> allAttacks = new HashSet<>();
//  private static HashSet<UpgradeAction> allUpgrades = new HashSet<>();

  //  four rooms need to be shared by all players
  public static Room room_1 = new Room(2);
  public static Room room_2 = new Room(3);
  public static Room room_3 = new Room(4);
  public static Room room_4 = new Room(5);

  // four room thread.

  public static RoomThread roomThread1 = new RoomThread(room_1);
  public static RoomThread roomThread2 = new RoomThread(room_2);
  public static RoomThread roomThread3 = new RoomThread(room_3);
  public static RoomThread roomThread4 = new RoomThread(room_4);


  public static <objectInputStream> void main(String[] args) {
    try {
      InputStream inputStream;
      ObjectInputStream objectInputStream;
      OutputStream outputStream;
      ObjectOutputStream objectOutputStream;
      int room_id;
      RoomThread roomThread;
      ArrayList<RoomThread> AllThreadList = new ArrayList<>();
      GamePlay gameplay = new GamePlay();



      //对于AllThreadList 来说


      //最外层的部分是
      //如果所有的room 都满了
      try (ServerSocket ss = new ServerSocket(8080)) {
        //这个是Server 的socket


        while (!(room_1.isFull() && room_2.isFull() && room_3.isFull() && room_4.isFull())) {
          //当有新的player连接起来的时候
          //设置一个窗口 -> 告诉我们所有的user 说所有的房间已经满了
          //4.3 仍旧需要建立一个socket 传到user_Thread 里面去
          //对于每一个user 来说，他们有一个单独的room thread
          Socket socket = ss.accept();

          UserThread userThread = new UserThread(socket,room_1, room_2, room_3, room_4, roomThread1, roomThread2, roomThread3, roomThread4);
          System.out.println("Enter userThread!!");
          userThread.start();
        }

      }
    }
     catch (Exception e) {
      System.out.println(e);
    }
  }
  public ArrayList<Room> availableRoom(){
    ArrayList<Room> availableRoomList = new ArrayList<Room>();
    if(!room_1.isFull()){
      availableRoomList.add(room_1);
    }
    if(!room_2.isFull()){
      availableRoomList.add(room_2);
    }
    if(!room_3.isFull()){
      availableRoomList.add(room_3);
    }
    if(!room_4.isFull()){
      availableRoomList.add(room_4);
    }
    return availableRoomList;
  }

}









