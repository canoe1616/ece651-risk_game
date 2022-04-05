package edu.duke.ece651.grp9.risk.client;

import edu.duke.ece651.grp9.risk.shared.Map;
import edu.duke.ece651.grp9.risk.shared.Player;
import edu.duke.ece651.grp9.risk.shared.Territory;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.HashSet;


public class GameRoomController {
    private Stage Window;

    @FXML private Text foodQuantity;
    @FXML private Text moneyQuantity;


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
    private Map myMap;
    private Player player;


    /**
     * this method to get all buttons for the default map (containing all territories
     */
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


    public GameRoomController(Stage Window, Map map, Player player) {
        this.myMap = map;
        this.Window = Window;
        this.player = player;
        System.out.println("input name and password.\n click join");
    }

    // initial game room according to the given map
    public void initialize() {
        InitButtonMap();
        // display different map to different players
        // TODO: how to indicate the player's name
        showMap(player, myMap, this.ButtonMap);
    }

    public void showMap(Player player, Map myMap, HashMap<String, Button> ButtonMap) {
        //set each button's color and shape in buttonMap
        HashSet<Player> players = myMap.getPlayer();
        for (Player p: players) {
            String color = p.getName();
            for (Territory ter: p.getTerritoryList()) {
                String style = getStyle(color);
                Button button =  ButtonMap.get(ter.getName());
                button.setStyle(style);
                button.setCursor(Cursor.HAND);
            }
        }

        // set food and money value
        foodQuantity.setText(Integer.toString(player.getFoodQuantity()));
        moneyQuantity.setText(Integer.toString(player.getMoneyQuantity()));
        System.out.println("Already paint color");
    }

    public String getStyle(String color) {
        //set each button's shape and color
        String style = "-fx-background-color: " + color + ";";
        style += ";" + "-fx-border-color: black;";
        return style;
    }

}
