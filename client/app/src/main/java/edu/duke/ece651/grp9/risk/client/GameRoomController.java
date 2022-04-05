package edu.duke.ece651.grp9.risk.client;

import javafx.stage.Stage;

public class GameRoomController {
    private Stage Window;
    private int playNum;
    public GameRoomController(int playerNum, Stage Window) {
        this.playNum = playerNum;
        this.Window = Window;
        System.out.println("input name and password.\n click join");
    }

}
