package edu.duke.ece651.grp9.risk.shared;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;


public class Room {
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


}