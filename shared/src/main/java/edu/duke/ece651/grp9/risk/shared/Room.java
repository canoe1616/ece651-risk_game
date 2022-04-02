package edu.duke.ece651.grp9.risk.shared;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;


public class Room {
    public String name;
    public Map map;


//    public Room(String input) {
//
//    }

    public Room(String name, Map map) {
        this.name = name;
        this.map = map;


    }
}