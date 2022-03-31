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
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private static HashSet<String> remainingColors;
    private Map m;
    public ServerThread(Socket socket, ArrayList<ServerThread> threads, HashSet<String> tmp , Map m\
    ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.socket = socket;
        this.threadList = threads;
        remainingColors = tmp;
        this.m = m;
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
            ActionRuleChecker tmp = new ActionRuleChecker();
            //send map object
            
            //check if the color selection is valid -- [done]
            String color = "";
            color = remainingColors.iterator().next();
            objectOutputStream.writeObject(color);
            deleteColor(color);


            //read unit assignment

            unitSetting(objectOutputStream, findPlayer(color, m));
            String unitString = "";
            while(true){
                String unit_correct = "true";
                unitString = (String)objectInputStream.readObject();
                System.out.print("server端接收到的unitString是：" + unitString);
                // add the checker
                while(tmp.checkUnit(unitString, findPlayer(color, m)) != null){
                    //debug
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
            //send map from the client to the server


            socket.close();
        }
        catch(Exception e){
            System.out.println("Error occured " + e.getMessage());
        }
    }


}
