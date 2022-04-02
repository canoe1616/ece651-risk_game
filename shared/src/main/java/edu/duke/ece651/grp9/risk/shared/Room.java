package edu.duke.ece651.grp9.risk.shared;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;


public class Room {
<<<<<<< HEAD
    private String name;
    private int numPlayers;
    private ArrayList<Socket> socketList;


    public Room(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    boolean isFull(){
        if (socketList.size()>=numPlayers){
            return true;
        }
        else{
            return false;
        }
    }

    void addSocket(Socket socket){
        socketList.add(socket);
    }
=======
    public String name;
    public Map map;


//    public Room(String input) {
//
//    }

    public Room(String name, Map map) {
        this.name = name;
        this.map = map;
>>>>>>> d31c0661dccdd446c916ca2bd88498ec206d121d


    }
}