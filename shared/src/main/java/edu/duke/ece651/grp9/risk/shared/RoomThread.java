package edu.duke.ece651.grp9.risk.shared;


import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class RoomThread extends Thread {
    private ArrayList<String> remainingColors;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private ArrayList<Socket> socketList;
    private ArrayList<String> playerList;
    public ArrayList<ObjectInputStream> InputList;
    public ArrayList<ObjectOutputStream> OutputList;
    private HashSet<MoveAction> allMoves = new HashSet<>();
    private HashSet<AttackAction> allAttacks = new HashSet<>();
    private HashSet<UpgradeAction> allUpgrades = new HashSet<>();

    //new adding
    public Room room;
    public int player_num;


    public RoomThread(Room room) {
        this.room = room;
        this.player_num =room.map.getPlayerNum();
        playerList = new ArrayList<String>();
        InputList = new ArrayList<ObjectInputStream>();
        OutputList = new ArrayList<ObjectOutputStream>();
        allMoves = new HashSet<MoveAction>();
        allAttacks = new HashSet<AttackAction>();
        allUpgrades = new HashSet<UpgradeAction>();
        remainingColors = new ArrayList<String>();
    }

    @Override
    public void run() {
      try{

          System.out.println("Enter Room Thread");
        Map m = room.map;
        remainingColors = new ArrayList<>();
        Iterator<Player> it = m.getPlayer().iterator();
        while (it.hasNext()) {
            remainingColors.add(it.next().getName());
        }

        ArrayList<ServerThread> serverThreadList = new ArrayList<>();
        ArrayList<ActionThread> ActionThreadList = new ArrayList<>();
        Socket socket = null;
        GamePlay gamePlay = new GamePlay();

            int i = 0;

//for part 1 - initial placement
            while (i < player_num) {


                System.out.println("Enter Room Thread initial placement");
                System.out.println("This room's getSocketList size" + room.getSocketList().size());
                //add the checker


                /**
                Before unit setting, sending map to the client.
                 */
                ObjectOutputStream objectOutputStream =OutputList.get(i);
                ObjectInputStream objectInputStream = InputList.get(i);
                objectOutputStream.reset();
              //  objectOutputStream.writeObject(m);
                System.out.println("Already sent the map");


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

//                    String action = (String) InputList.get(j).readObject();
//                    gamePlay.findPlayer(remainingColors.get(j), m).setLoseStatus(action);
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


        } catch (Exception e) {
            System.out.println(e);
        }



    }

}

