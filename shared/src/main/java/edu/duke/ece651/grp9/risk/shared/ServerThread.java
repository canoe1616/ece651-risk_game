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
    public Map m;
    private String color;
    public ServerThread(Socket socket, ArrayList<ServerThread> threads, Map m,
    ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, String color) {
        this.socket = socket;
        this.threadList = threads;
       // remainingColors = tmp;
        this.m = m;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        this.color = color;
    }


    @Override
    public void run() {
        GamePlay gamePlay =new GamePlay();
        try{
            ActionRuleChecker tmp = new ActionRuleChecker();
            //send map object
            
            



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
                    System.out.print("是否进入到了unit报错环节" + unitString);
                    unit_correct = "false";
                    objectOutputStream.reset();
                    objectOutputStream.writeObject("false");
                    unitString = (String)objectInputStream.readObject();
                }
                //
                if(tmp.checkUnit(unitString, gamePlay.findPlayer(color, m)) == null) {
                    unit_correct = "true";
                    objectOutputStream.reset();
                    objectOutputStream.writeObject(unit_correct);
                    System.out.print("server thread 结束了");
                    gamePlay.playerUnitSetting(unitString, gamePlay.findPlayer(color, m));
                    break;
                }
            }

            //check if the color selection is valid -- [done]
          //  objectOutputStream.writeObject(color);
           // System.out.println("send color: " + color);

        }
        catch(Exception e){
            System.out.println("Error occured " + e.getMessage());
        }
    }


}
