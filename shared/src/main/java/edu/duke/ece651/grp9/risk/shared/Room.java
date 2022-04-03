package edu.duke.ece651.grp9.risk.shared;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class Room {

    private String name;
    private int numPlayers;
    //private ArrayList<Socket> socketList;
    private ArrayList<User> userList;
    public Map map;


    public Room(int numPlayers) {
        RoomFactory roomFactory = new RoomFactory();
        //应该调用roomfactory
        this.map = roomFactory.makeRoom(numPlayers).map;
        this.numPlayers = numPlayers;
    }

    public boolean isFull(){
        if (userList.size()>=numPlayers){
            return true;
        }
        else{
            return false;
        }
    }

//   // void addSocket(Socket socket){
//        socketList.add(socket);
//    }






    public Room(String name, Map map) {
        this.name = name;
        this.map = map;



    }
    public void addUser( User user){
        userList.add(user);
    }
}