package edu.duke.ece651.grp9.risk.shared;


import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class ServerThread extends Thread{
    private Socket socket;
    private ArrayList<ServerThread> threadList;
    private PrintWriter output;
    private static HashSet<String> remainingColors;
    public ServerThread(Socket socket, ArrayList<ServerThread> threads, Map m) {
        this.socket = socket;
        this.threadList = threads;
        remainingColors = new HashSet<>();
        Iterator<Player> it = m.getPlayer().iterator();
        while(it.hasNext()){
            remainingColors.add(it.next().getName());
        }
    }
    public void selectColor(ObjectOutputStream stream) {
        StringBuilder sb = new StringBuilder();
        sb.append("Please select what color you would like to play as: ");
        for (String color : remainingColors) {
            sb.append(color + " ");
        }
        try {
            stream.writeObject(sb.toString());
        } catch(Exception e) {
            System.out.println(e);
        }
    }
    public boolean deleteColor(String color){
        for (String c : remainingColors) {
            if(c.equals(color)){
                remainingColors.remove(color);
                return true;
            }
        }
        return false;
    }
    public void unitSetting(ObjectOutputStream stream, Player player){
        StringBuilder sb = new StringBuilder();
        sb.append("You have " + player.getTerritoryNumber()+ " territories: ");
        for(Territory ter : player.getTerritoryList()){
            sb.append(ter.getName()+ " ");
        }
        sb.append("\n");
        sb.append("You have 30 total units, how do you want to place the units?");
        try {
            stream.writeObject(sb.toString());
        } catch(Exception e) {
            System.out.println(e);
        }
    }
    public Player findPlayer(String color, Map m){
        HashSet<Player> list = m.getPlayer();
        Iterator<Player> it = list.iterator();
        while(it.hasNext()){
            Player pyr = it.next();
            if(pyr.getName().equals(color)){
                return pyr;
            }
        }
        return null;
    }
    @Override
    public void run() {
        try{
            MapFactory f = new MapFactory();
            Map m = f.makeMapForThree();
            int player_num = 3;
            ActionRuleChecker tmp = new ActionRuleChecker();
            //send map object
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(m);
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            selectColor(objectOutputStream);
            //check if the color selection is valid
            String color = "";
            while(true){
                //boolean for color checking
                String color_correct = "true";
                //InputStream inputStream = socket.getInputStream();
                //ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                color = (String)objectInputStream.readObject();
                // add the checker
                //if everything is good, we will send "true" to the client
                //System.out.println(color);
                while(tmp.checkColor(color, remainingColors) != null){
                    //System.out.println(tmp.checkColor(color, remainingColors));
                    color_correct = "false";
                    objectOutputStream.writeObject("false");
                    //read the new color from the client
                    color = (String)objectInputStream.readObject();
                }
                if(tmp.checkColor(color, remainingColors) == null) {
                    color_correct = "true";
                    objectOutputStream.writeObject(color_correct);
                    deleteColor(color);
                    break;
                }
            }
            //read unit assignment
            unitSetting(objectOutputStream, findPlayer(color, m));
            String unitString = "";
            while(true){
                String unit_correct = "true";
                //InputStream inputStream = socket.getInputStream();
                //ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                unitString = (String)objectInputStream.readObject();
                System.out.print(unitString);
                // add the checker
                while(tmp.checkUnit(unitString, findPlayer(color, m)) != null){
                    System.out.print(unitString);
                    unit_correct = "false";
                    objectOutputStream.writeObject("false");
                    unitString = (String)objectInputStream.readObject();
                }
                //
                if(tmp.checkUnit(unitString, findPlayer(color, m)) == null) {
                    unit_correct = "true";
                    objectOutputStream.writeObject(unit_correct);
                    break;
                }
            }
            socket.close();
        }
        catch(Exception e){
            System.out.println("Error occured " + e.getMessage());
        }
    }


}
