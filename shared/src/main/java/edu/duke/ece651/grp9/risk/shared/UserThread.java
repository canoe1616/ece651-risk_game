package edu.duke.ece651.grp9.risk.shared;

import java.util.ArrayList;

public class UserThread {
    ArrayList<RoomThread> ActiveroomThreadList = new ArrayList<>();
    User user;


    //user只选了一个room 去玩， 但是它可以选多个
    public UserThread(int num, ArrayList<RoomThread> AllThreadList,User user) {

        while (user.activeUser()) {
            Room room = new Room(num);
            ActiveroomThreadList.add(room);


            //在每一轮的时候，我们switch room的，actionthread -》 switch room?
            //view - room_list  四个
            //room_t
            //如果它


        }
    }
}
