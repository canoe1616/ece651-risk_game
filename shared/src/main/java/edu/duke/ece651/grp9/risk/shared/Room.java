package edu.duke.ece651.grp9.risk.shared;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class Room {

    private String name;
    private int numPlayers;
    private ArrayList<Socket> socketList;
    private ArrayList<User> userList;
    public Map map;
    public ArrayList<User> myCurrentUser;
    public boolean startOrnot;

    //debugging 4.2 night


    public Room(int numPlayers) {
        RoomFactory roomFactory = new RoomFactory();
        //应该调用roomfactory
        this.map = roomFactory.makeRoom(numPlayers).map;
        this.numPlayers = numPlayers;
        this.socketList = new ArrayList<>();
        this.userList = new ArrayList<>();
        this.startOrnot = false;
    }

    public boolean isFull(){
        if (userList.size()>=numPlayers){
            return true;
        }
        else{
            return false;
        }
    }

    


    public Room(String name, Map map) {
        this.name = name;
        this.map = map;
    }

    public void addSocket(Socket socket){
       socketList.add(socket);
    } 

    public ArrayList<Socket> getSocketList(){
      return socketList;

    }
    public void addUser( User user){
        userList.add(user);
    }

    /*
    For UI part  priting
     */
    public String roomFull(){
        String a = String.valueOf(userList.size()) + "/" + String.valueOf(numPlayers);
        System.out.println(a);
        return a;
    }
}
