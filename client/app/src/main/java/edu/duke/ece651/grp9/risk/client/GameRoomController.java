package edu.duke.ece651.grp9.risk.client;

import edu.duke.ece651.grp9.risk.shared.Territory;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class GameRoomController {
    private Stage Window;
    private int playNum;

    @FXML private Button A;
    @FXML private Button B;
    @FXML private Button C;
    @FXML private Button D;
    @FXML private Button E;
    @FXML private Button F;
    @FXML private Button G;
    @FXML private Button H;
    @FXML private Button I;
    @FXML private Button J;

    private HashMap<String, Button> ButtonMap;
    private HashMap<Integer, ArrayList<Territory>> TerrMap;

    private void InitButtonMap(){
        ButtonMap = new HashMap<>();
        ButtonMap.put("A", A);
        ButtonMap.put("B", B);
        ButtonMap.put("C", C);
        ButtonMap.put("D", D);
        ButtonMap.put("E", E);
        ButtonMap.put("F", F);
        ButtonMap.put("G", G);
        ButtonMap.put("H", H);
        ButtonMap.put("I", I);
        ButtonMap.put("J", J);
    }

    public GameRoomController(int playerNum, Stage Window) {
        HashMap<Integer, ArrayList<Territory>> map = new HashMap<>();
        ArrayList<Territory> terrs = new ArrayList<>();
        terrs.add(new Territory("A"));
        terrs.add(new Territory("B"));
        terrs.add(new Territory("C"));
        terrs.add(new Territory("D"));
        terrs.add(new Territory("E"));
        terrs.add(new Territory("F"));
        terrs.add(new Territory("G"));
        terrs.add(new Territory("H"));
        terrs.add(new Territory("I"));
        terrs.add(new Territory("J"));
        map.put(1, terrs);

        this.TerrMap = map;
        this.playNum = playerNum;
        this.Window = Window;
        System.out.println("input name and password.\n click join");
    }

    public void initialize() {
        //Set the profile photo of player
        Pair<Integer, String> playerInfo = new Pair<>(1, "red");
        InitButtonMap();
        showMap(TerrMap, playerInfo, this.ButtonMap);
    }

    public void showMap(HashMap<Integer, ArrayList<Territory>> CurrentMap, Pair<Integer, String> playerInfo, HashMap<String, Button> ButtonMap) {
        //set each button's color and shape in buttonMap
        for (HashMap.Entry<Integer, ArrayList<Territory>> entry : CurrentMap.entrySet()){
            //iterate each player color to set the button territory to its color
            String color = playerInfo.getValue();
            String background = color.toLowerCase();
            ArrayList<Territory> TerrList = entry.getValue();
            for(int i = 0; i < TerrList.size(); i++){
                Territory OneTerr = TerrList.get(i);
                String TerrName = OneTerr.getName();
                String styleStr = getStyle(background, TerrName);
                Button Btn = ButtonMap.get(TerrName);
                Btn.setStyle(styleStr);
                Btn.setCursor(Cursor.HAND);
            }
        }
        System.out.println("Already paint color");
    }

    public String getStyle(String color, String territoryName) {
        //set each button's shape and color
        String style = "-fx-background-color: " + color + ";";
        if (territoryName.equals("A")) { style += "-fx-shape: \"M 400 150 Q 450 400 350 400 Q 200 400 50 400 Q 50 200 250 200 Q 350 200 400 150\""; }
        else if (territoryName.equals("B")) { style += "-fx-shape: \"M 400 300 Q 450 300 600 300 L 600 100 L 400 100 Q 350 200 400 300\""; }
        else if (territoryName.equals("C")) { style += "-fx-shape: \"M 350 300 C 425 300 475 300 550 300 Q 650 200 600 50 L 350 50 Q 350 200 350 300\""; }
        else if (territoryName.equals("D")) { style += "-fx-shape: \"M 350 300 C 425 300 475 300 600 300 Q 650 200 600 100 Q 450 50 350 150 Q 350 200 350 300\""; }
        else if (territoryName.equals("E")) { style += "-fx-shape: \"M 400 200 Q 450 300 550 300 Q 650 300 750 250 Q 750 0 450 50 Q 450 100 400 200\""; }
        else if (territoryName.equals("F")) { style += "-fx-shape: \"M 350 350 C 450 350 450 350 550 350 Q 600 200 600 100 Q 450 0 350 100 Q 350 200 350 350\""; }
        else if (territoryName.equals("G")) { style += "-fx-shape: \"M 350 350 C 450 350 450 350 600 350 Q 650 250 600 150 Q 500 150 350 200 Q 350 200 350 350\""; }
        else if (territoryName.equals("H")) { style += "-fx-shape: \"M 550 350 Q 600 400 700 400 Q 800 250 750 100 L 550 100 Q 500 200 550 350\""; }
        else if (territoryName.equals("I")) { style += "-fx-shape: \"M 350 450 C 500 400 550 425 700 400 Q 650 250 700 100 L 350 100 Q 300 250 350 450\""; }
        else if (territoryName.equals("J")) { style += "-fx-shape: \"M 350 400 Q 500 450 700 400 Q 800 300 800 200 C 450 200 500 200 350 200 Q 300 250 350 400\""; }
        style += ";" + "-fx-border-color: black;";
        return style;
    }

}
