package edu.duke.ece651.grp9.risk.shared;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class User {

    String userName;
    String password;
    //For each player, its room and its corresponding colors.
    HashMap<Room,String> myRoleList;
    //just one hashmap
    Room myCurrentRoom;
    String myCurrentColor;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;





}
