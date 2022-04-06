package edu.duke.ece651.grp9.risk.client;

import edu.duke.ece651.grp9.risk.shared.ActionSet;
import edu.duke.ece651.grp9.risk.shared.Map;
import edu.duke.ece651.grp9.risk.shared.Player;
import edu.duke.ece651.grp9.risk.shared.Territory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ReadOnlyBufferException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class MapController {
    private Stage Window;
    
    @FXML private Label foodQuantity;
    @FXML private Label moneyQuantity;
   // @FXML private Text color_text;

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
    private String color;


    @FXML
    Label territoryStats;

    @FXML
    Label status;

    public static HashSet<String> attacks = new HashSet<>();
    public static HashSet<String> moves = new HashSet<>();
    public static HashSet<String> upgrades = new HashSet<>();
    public static boolean techAction = false;

    public ObjectOutputStream objectOutputStream;
    public ObjectInputStream objectInputStream;


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


    public MapController(Stage Window, Map map, Player player, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.myMap = map;
        this.Window = Window;
        this.player = player;
        this.color = player.getName();
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;

        System.out.println("input name and password.\n click join");
    }

    // initial game room according to the given map
    public void initialize() throws Exception{
        InitButtonMap();
        // display different map to different players
        // TODO: how to indicate the player's name
        showMap();
        //receive the map from the server
        //myMap =  (Map) objectInputStream.readObject();
    }

    public void showMap() throws Exception{
        //set each button's color and shape in buttonMap
        updateTerroteryText();
        updateButtonColors();

        // set food and money value
        updateResources();
        System.out.println("Already paint color");
       // updateMapafterInitiualization();

        
    }

    public void updateButtonColors() {
        HashSet<Player> players = myMap.getPlayer();
        // store useable buttons
        Set<String> allButtons = ButtonMap.keySet();
        for (Player p: players) {
            String color = p.getName();
            for (Territory ter: p.getTerritoryList()) {
                String style = getStyle(color);
                Button button =  ButtonMap.get(ter.getName());
                allButtons.remove(ter.getName());
                button.setStyle(style);
                button.setCursor(Cursor.HAND);
            }
        }
        for (String unusedButton: allButtons) {
            Button button = ButtonMap.get(unusedButton);
            button.setDisable(true);
        }
    }


    public void updateMapafterInitiualization() throws Exception {
        myMap =  (Map) objectInputStream.readObject();
    }

    public void updateResources() {
        player = myMap.findPlayer(color);
        foodQuantity.setText(Integer.toString(player.getFoodQuantity()));
        moneyQuantity.setText(Integer.toString(player.getMoneyQuantity()));
    }

    
    public void updateTerroteryText() {

            System.out.println("ready to Read text......");
            //String ter = (String)objectInputStream.readObject();
            String ter = "You have 30 total units, how do you want to place the units?";
            status.setText(ter);
            System.out.println("Get text:::" + ter);
    }
    
        
    
    public void updateMap()  {
        updateButtonColors();
        updateResources();
    }

    public String getStyle(String color) {
        //set each button's shape and color
        String style = "-fx-background-color: " + color + ";";
        style += ";" + "-fx-border-color: black;";
        return style;
    }

    @FXML
    public void onCreateAttack(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source instanceof Button) {
            Button btn = (Button) source;
            System.out.println(btn.getId());
        } else {
            throw new IllegalArgumentException("Invalid source");
        }

        try {
            AttackPopup popup = new AttackPopup();
            popup.display();
            String[] words = validAction(popup.action);
            if (words != null) {
                attacks.add(popup.action);
                statusLabel("Attack " + words[1] + " with " + words[2] + "(Level " + words[3] +
                        ") units from " + words[0]);
            } else {
                statusLabel("Invalid Action");
            }

        } catch (IOException e) {
            System.out.println("Could not display Attack Popup");
        }
    }

    @FXML
    public void onCreateMove(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source instanceof Button) {
            Button btn = (Button) source;
            System.out.println(btn.getId());
        } else {
            throw new IllegalArgumentException("Invalid source");
        }

        try {
            MovePopup popup = new MovePopup();
            popup.display();
            String[] words = validAction(popup.action);
            if (words != null) {
                moves.add(popup.action);
                statusLabel("Move " + words[2] + "(Level " + words[3] +
                        ") units from " + words[0] + " to " + words[1]);
            } else {
                statusLabel("Invalid Action");
            }
        } catch (IOException e) {
            System.out.println("Could not display Move Popup");
        }
    }

    @FXML
    public void onCreateUpgrade(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source instanceof Button) {
            Button btn = (Button) source;
            System.out.println(btn.getId());
        } else {
            throw new IllegalArgumentException("Invalid source");
        }

        try {
            UpgradePopup popup = new UpgradePopup();
            popup.display();
            String[] words = validAction(popup.upgrade);
            if (words != null) {
                attacks.add(popup.upgrade);
                statusLabel("Upgrade " + words[1] + " units in "
                        + words[0] + " from level" + words[2] + " to " + words[3]);
            } else {
                statusLabel("Invalid Action");
            }
        } catch (IOException e) {
            System.out.println("Could not display Upgrade Popup");
        }
    }

    @FXML
    public void onTerritory(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source instanceof Button) {
            Button btn = (Button) source;
            StringBuilder sb = new StringBuilder();
            sb.append("Territory " + btn.getText() + "\n");
            Territory t = myMap.findTerritory(btn.getText());
            //int[] unitCounts = new int[] {13, 0, 0, 0, 0, 0, 0};
            for (int i = 0; i < 6; i++) {
                sb.append("Level " + i + ": " + t.getUnits(i) + "\n");
            }
            sb.append("Food Production: 100\n");
            sb.append("Money Production: 50\n");
            sb.append("Size: " + t.getSize());

            territoryStats.setText(sb.toString());
        } else {
            throw new IllegalArgumentException("Invalid source");
        }
    }

    @FXML
    public void onLevelUp(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source instanceof Button) {
            Button btn = (Button) source;
            techAction = true;
            statusLabel("Upgrade Tech Level");
        } else {
            throw new IllegalArgumentException("Invalid source");
        }
    }

    @FXML
    public void onDone(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Object source = actionEvent.getSource();
        if (source instanceof Button) {
            Button btn = (Button) source;
            for (String move : moves) {
                System.out.println("Move : " + move);
            }
            for (String attack : attacks) {
                System.out.println("Attack : " + attack);
            }
            for (String upgrade : upgrades) {
                System.out.println("Upgrade : " + upgrade);
            }
            if (techAction) {
                System.out.println("Player tech level upgrade");
            }
            ActionSet actionSet = new ActionSet();
            actionSet.actionListAttack = attacks;
            actionSet.actionListMove = moves;
            actionSet.actionListUpgrade = upgrades;
//      actionSet.techLevelAction = techAction;
            objectOutputStream.reset();
            objectOutputStream.writeObject(actionSet); //write 001
            String actionProblem = (String) objectInputStream.readObject();//read 001
            if (actionProblem == null) {
                statusLabel("Actions submitted to server. Waiting for updated map.");
                btn.setStyle("-fx-background-color: Green");
                myMap = (Map) objectInputStream.readObject();//read 002
                statusLabel("Received updated Map.");
                String endGame = (String) objectInputStream.readObject();//read 003
                System.out.println("EndGame? : " + endGame);
                if (!checkWinner(endGame)) {
                    //What do we do here?
                    objectOutputStream.writeObject("no act"); //write 002
                }
            } else {
                status.setText(actionProblem);
                btn.setStyle("-fx-background-color: rgba(255,0,0,0.07)");
                resetActions();
                return;
            }

        } else {
            throw new IllegalArgumentException("Invalid Done Button");
        }
        // TODO: check results and triggle game end page

        resetActions();
        updateMap();
        
    }

    public boolean checkWinner(String endGame){

        if (endGame.equals("win")){
            System.out.println("You have won");
            status.setText("You have won!");
            return true;
        }
        else if (endGame.equals("game over")){
            System.out.println("Gameover, you have lost.");
            status.setText("Gameover, you have lost.");
            return true;
        }
        return false;
    }

    public void statusLabel(String message) {
        String label = "Last Action: " + message;
        status.setText(label);
    }

    public String[] validAction(String actionString) {
        if (actionString == null) {
            return null;
        }
        String[] words = actionString.split(" ");
        if (words.length != 4) {
            return null;
        }
        return words;
    }

    public static void resetActions() {
        attacks.clear();
        moves.clear();
        upgrades.clear();
        techAction = false;
    }
}

