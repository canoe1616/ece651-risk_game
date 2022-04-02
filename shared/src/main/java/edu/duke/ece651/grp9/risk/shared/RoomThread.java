package edu.duke.ece651.grp9.risk.shared;

<<<<<<< HEAD

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;

public class RoomThread extends Thread{
    private static ArrayList<String> remainingColors;
    //private static HashMap<String, String> userPassPairs;
    private static ArrayList<Socket> socketList;
   

  public RoomThread(Map m, ArrayList<Socket> socketList){
    this.remainingColors = new ArrayList<>();
    Iterator<Player> it = m.getPlayer().iterator();
    while (it.hasNext()) {
      remainingColors.add(it.next().getName());
    }
    this.socketList = socketList;
  }

  @Override
    public void run() {
    GamePlay gamePlay =new GamePlay();
      try{
          int i = 0;

            //for part 1 - initial placement
            while (i < player_num) {
                //add the checker
                socket = socketList.get(i);
                OutputStream outputStream = socket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(m); // send #001
                OutputList.add(objectOutputStream);

                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                InputList.add(objectInputStream);

                ServerThread serverThread = new ServerThread(socket, serverThreadList, m, objectInputStream, objectOutputStream, remainingColors.get(i));
                serverThreadList.add(serverThread);
                serverThread.start();
                m = serverThread.m;
                i++;
                System.out.print("i is " + i);
            }

            System.out.println("第一个unit setting 的部分结束了");

            for (int n = 0; n < serverThreadList.size(); ++n) {
                System.out.println("server thread join");
                serverThreadList.get(n).join();
            }

      //for part 2 - for action part
      //while the game is not over?

      int j = 0;
      while (m.getGameWinner() == null) {

        while (j < player_num) {
          //how to update
          OutputList.get(j).reset();
          OutputList.get(j).writeObject(m);
          OutputList.get(j).reset();
          OutputList.get(j).writeObject("keep going");

          String action = (String) InputList.get(j).readObject();
          gamePlay.findPlayer(remainingColors.get(j), m).setLoseStatus(action);
          Player tmp = gamePlay.findPlayer(remainingColors.get(j), m);

          /*************adding new parts***/
          if (tmp.isLose()) {
            if (tmp.getLoseStatus().equals("quit") && m.getPlayer().contains(tmp)) {
              //remove it from player list
              //auto set empty actionSet
              System.out.println("Bye bye I quit");
              InputList.remove(j);
              OutputList.remove(j);
              remainingColors.remove(j);
              player_num--;
              j--;
            }
            if (tmp.getLoseStatus().equals("continue")) {
            }
          } else {
            ActionThread actionThread = new ActionThread(m, InputList.get(j), OutputList.get(j), tmp, allMoves, allAttacks, allUpgrades);
            ActionThreadList.add(actionThread);
            actionThread.start();
            allMoves.addAll(actionThread.allMove);
            allAttacks.addAll(actionThread.allAttack);
            allUpgrades.addAll(actionThread.allUpgrade);
            j++;
          }
        }

        //after all the actions, they should be merged
        for (int k = 0; k < ActionThreadList.size(); ++k) {
          ActionThreadList.get(k).join();
        }

        j = 0;
        System.out.println("perform all actions");
        gamePlay.playMoves(allMoves);
        gamePlay.playAttacks(m, allAttacks);
        gamePlay.playUpgrades(allUpgrades);
        // increase the basic unit per terr, produce resource
        m.upgradeMapPerRound();
        allMoves.clear();
        allAttacks.clear();
        allUpgrades.clear();
      }

      //for part 3 - for game winner and end this game
      for (int t = 0; t < player_num; t++) {
        System.out.println("已经进入到winner阶段");
        gamePlay.gameWinner(OutputList.get(t), remainingColors.get(t), m);
      }
      System.out.println("Final point");
      TimeUnit.SECONDS.sleep(20);
      ss.close();

        }
      }
      catch(Exception e){
        System.out.println("Error occured " + e.getMessage());
      }
  }
=======
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class RoomThread {

    MapFactory f = new MapFactory();
    Map m = f.makeMap(player_num);
    App app = new App(m);

    remainingColors = new ArrayList<>();
    Iterator<Player> it = m.getPlayer().iterator();
    while (it.hasNext()) {
        remainingColors.add(it.next().getName());
    }

    /******************************************************************************************************/
    ArrayList<ServerThread> serverThreadList = new ArrayList<>();
    ArrayList<ActionThread> ActionThreadList = new ArrayList<>();
    Socket socket = null;
    GamePlay gamePlay = new GamePlay();

    try (
    ServerSocket ss = new ServerSocket(6666)) {
        for (int i = 0; i < player_num; i++) {
            Socket s = ss.accept();
            socketList.add(s);
        }

        //socket 是固定的一个
        //Q: 一个client 对应一个socket & multi-threads
//        Socket socket = ss.accept();
//        //input outputStream, inputStream,
//        OutputStream outputStream = socket.getOutputStream();
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        int i = 0;

//for part 1 - initial placement
        while (i < player_num) {
            //add the checker
            socket = socketList.get(i);
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(m); // send #001
            OutputList.add(objectOutputStream);

            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            InputList.add(objectInputStream);

            ServerThread serverThread = new ServerThread(socket, serverThreadList, m, objectInputStream, objectOutputStream, remainingColors.get(i));
            serverThreadList.add(serverThread);
            serverThread.start();
            m = serverThread.m;
            i++;
            System.out.print("i is " + i);
        }

        System.out.println("第一个unit setting 的部分结束了");

        for (int n = 0; n < serverThreadList.size(); ++n) {
            System.out.println("server thread join");
            serverThreadList.get(n).join();
        }

        //for part 2 - for action part
        //while the game is not over?

        int j = 0;
        while (m.getGameWinner() == null) {

            while (j < player_num) {
                //how to update
                OutputList.get(j).reset();
                OutputList.get(j).writeObject(m);
                OutputList.get(j).reset();
                OutputList.get(j).writeObject("keep going");

                String action = (String) InputList.get(j).readObject();
                gamePlay.findPlayer(remainingColors.get(j), m).setLoseStatus(action);
                Player tmp = gamePlay.findPlayer(remainingColors.get(j), m);

                /*************adding new parts***/
                if (tmp.isLose()) {
                    if (tmp.getLoseStatus().equals("quit") && m.getPlayer().contains(tmp)) {
                        //remove it from player list
                        //auto set empty actionSet
                        System.out.println("Bye bye I quit");
                        InputList.remove(j);
                        OutputList.remove(j);
                        remainingColors.remove(j);
                        player_num--;
                        j--;
                    }
                    if (tmp.getLoseStatus().equals("continue")) {
                    }
                } else {
                    ActionThread actionThread = new ActionThread(m, InputList.get(j), OutputList.get(j), tmp, allMoves, allAttacks, allUpgrades);
                    ActionThreadList.add(actionThread);
                    actionThread.start();
                    allMoves.addAll(actionThread.allMove);
                    allAttacks.addAll(actionThread.allAttack);
                    allUpgrades.addAll(actionThread.allUpgrade);
                    j++;
                }
            }

            //after all the actions, they should be merged
            for (int k = 0; k < ActionThreadList.size(); ++k) {
                ActionThreadList.get(k).join();
            }

            j = 0;
            System.out.println("perform all actions");
            gamePlay.playMoves(allMoves);
            gamePlay.playAttacks(m, allAttacks);
            gamePlay.playUpgrades(allUpgrades);
            // increase the basic unit per terr, produce resource
            m.upgradeMapPerRound();
            allMoves.clear();
            allAttacks.clear();
            allUpgrades.clear();
        }

        //for part 3 - for game winner and end this game
        for (int t = 0; t < player_num; t++) {
            System.out.println("已经进入到winner阶段");
            gamePlay.gameWinner(OutputList.get(t), remainingColors.get(t), m);
        }
        System.out.println("Final point");
        TimeUnit.SECONDS.sleep(20);
        ss.close();


    } catch (Exception e) {
        System.out.println(e);
    }
>>>>>>> d31c0661dccdd446c916ca2bd88498ec206d121d
}
