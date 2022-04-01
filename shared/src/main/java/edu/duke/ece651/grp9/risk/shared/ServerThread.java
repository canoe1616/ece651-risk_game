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
    private ArrayList<String> remainingColors;
    private Map m;
    public ServerThread(Socket socket, ArrayList<ServerThread> threads, ArrayList<String> tmp , Map m,
    ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.socket = socket;
        this.threadList = threads;
        remainingColors = tmp;
        this.m = m;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }
    @Override
    public void run() {
        GamePlay gamePlay =new GamePlay();
        try{
            ActionRuleChecker tmp = new ActionRuleChecker();
            //send map object
            
            //check if the color selection is valid -- [done]
            String color = "";
            for(int i = 0 ; i < remainingColors.size();++i){
                color = remainingColors.get(i);
                objectOutputStream.writeObject(color);
            }




            //read unit assignment

            gamePlay.unitSetting(objectOutputStream, gamePlay.findPlayer(color, m));
            String unitString = "";
            while(true){
                String unit_correct = "true";
                unitString = (String)objectInputStream.readObject();
                System.out.print("server端接收到的unitString是：" + unitString);
                // add the checker
                while(tmp.checkUnit(unitString, gamePlay.findPlayer(color, m)) != null){
                    //debug
                    System.out.print(unitString);
                    unit_correct = "false";
                    objectOutputStream.writeObject("false");
                    unitString = (String)objectInputStream.readObject();
                }
                //
                if(tmp.checkUnit(unitString, gamePlay.findPlayer(color, m)) == null) {
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
