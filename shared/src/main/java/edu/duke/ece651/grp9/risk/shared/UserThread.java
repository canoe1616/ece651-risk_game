package edu.duke.ece651.grp9.risk.shared;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class UserThread extends Thread{
    ArrayList<RoomThread> ActiveroomThreadList = new ArrayList<>();
   // private static HashMap<String, String> userPassPairs;
    User user;
    Room room_1;
    Room room_2;
    Room room_3;
    Room room_4;
    RoomThread roomThread1;
    RoomThread roomThread2;
    RoomThread roomThread3;
    RoomThread roomThread4;
    Socket socket;


    //  Socket socket;


    //user只选了一个room 去玩， 但是它可以选多个
    public UserThread(Socket socket,Room room_1,Room room_2,Room room_3,Room room_4,RoomThread roomThread1,RoomThread roomThread2,RoomThread roomThread3, RoomThread roomThread4) {
        ActiveroomThreadList = new ArrayList<>();
        GamePlay gameplay = new GamePlay();
        //this.socket = new Socket();
        this.roomThread1 = roomThread1;
        this.roomThread2 = roomThread2;
        this.roomThread3 = roomThread3;
        this.roomThread4 = roomThread4;
        this.room_1 = room_1;
        this.room_2 = room_2;
        this.room_3 = room_3;
        this.room_4 = room_4;
        this.socket = socket;



    }

    @Override
    public void run() {

//        try(ServerSocket ss = new ServerSocket(8080)) {
//          for (int i = 0; i < 2; i++) {
//            Socket s = ss.accept();
//          }
//
//          System.out.println("In the UserThread");
//          Socket socket = ss.accept();

        System.out.println("In the UserThread");

        try{
            
            //Step1: 请输入你的username 和 password --  回传给client的东西是 account_check
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            GamePlay gameplay = new GamePlay();

            String password_check = gameplay.storeUserNameAndPassword(objectInputStream);
            String password_correct = "true";
            // add the checker
            //if everything is good, we will send "true" to the client
            // 检验我们输入的内容或者password是否是对的
            while (password_check == null) {

                objectOutputStream.writeObject("false");
                //read the new username/password from the client
                password_check = gameplay.storeUserNameAndPassword(objectInputStream);
            }
            if (password_check != null) {
                password_correct = "true";
                objectOutputStream.writeObject(password_correct);
                //不管它之前有没有登陆过，我们都一定会返回这个user
                this.user = new User(password_check,gameplay.userPassPairs.get(password_check));

            }
            //questions 如何确定两个user 的相等 还需要写一个hashFunction

           if(gameplay.UserList.containsKey(user)) {
               this.socket = gameplay.UserList.get(user);
           }
           else {
               gameplay.UserList.put(user,this.socket);
           }


            /**
             *
             *if( gameplay.userPassPairs.containsKey(username){
             *  this.socket =
             * })
             * 在server 端新建一个global 的变量，userLIst
             * 如果这个user曾经有进来过，代表它存在在userlist 当中，应该放相同的Socket.
             * 如果这是一个新加入的user的话，我们就build一个新的userlist,然后把它放到userlist
             */

            /**********************************************************************************/
            int room_id = (int)objectInputStream.readObject();
            if (room_id == 1){
                System.out.println("Room_1 add one user");
                room_1.addUser(user);
                room_1.addSocket(socket);
                roomThread1.InputList.add(objectInputStream);
                roomThread1.OutputList.add(objectOutputStream);
                ActiveroomThreadList.add(roomThread1);
            }
            if (room_id == 2){
                room_2.addUser(user);
                room_2.addSocket(socket);
                roomThread2.InputList.add(objectInputStream);
                roomThread2.OutputList.add(objectOutputStream);
                ActiveroomThreadList.add(roomThread2);
            }
            if (room_id == 3){
                room_3.addUser(user);
                room_3.addSocket(socket);
                roomThread3.InputList.add(objectInputStream);
                roomThread3.OutputList.add(objectOutputStream);
                ActiveroomThreadList.add(roomThread3);
            }
            if (room_id == 4){
                room_4.addUser(user);
                room_4.addSocket(socket);
                roomThread4.InputList.add(objectInputStream);
                roomThread4.OutputList.add(objectOutputStream);
                ActiveroomThreadList.add(roomThread4);
            }

 

            /*********************************************************************************/
                 //run every room thread
            if (room_1.isFull() && room_1.startOrnot ==false){ // do we need a roomThreadList?
                room_1.startOrnot = true;
                roomThread1.start();
                System.out.println("Room_1 already start");
            }
            if (room_2.isFull() && room_2.startOrnot ==false){
                room_2.startOrnot = true;
                roomThread2.start();
                System.out.println("Room_2 already start");
            }
            if (room_3.isFull() && room_3.startOrnot ==false){
                room_3.startOrnot = true;
                roomThread3.start();
                System.out.println("Room_3 already start");
            }
            if (room_4.isFull() && room_4.startOrnot ==false ){
                room_4.startOrnot = true;
                roomThread4.start();
                System.out.println("Room_4 already start");

            }
        }
        catch (Exception e){
            System.out.println(e);

        }
    }







        //这里应该还有加一行 当user的action是 "switch room"
        //room 玩完之后













            

            //在每一轮的时候，我们switch room的，actionthread -》 switch room?
            //view - room_list  四个
            //room_t
            //如果它


        }


