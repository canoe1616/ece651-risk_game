package edu.duke.ece651.grp9.risk.client;

import edu.duke.ece651.grp9.risk.shared.Map;
import edu.duke.ece651.grp9.risk.shared.MapFactory;
import edu.duke.ece651.grp9.risk.shared.Player;
import edu.duke.ece651.grp9.risk.shared.Room;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;

public class selectRoomController {
    private Stage Window;

    @FXML Button room1;
    @FXML Button room2;
    @FXML Button room3;
    @FXML Button room4;
    @FXML private Text color_text;

    public  ObjectOutputStream objectOutputStream;
    public  ObjectInputStream objectInputStream;
    private HashMap<String, Button> ButtonMap;

    private void InitButtonMap(){
        ButtonMap = new HashMap<>();
        ButtonMap.put("room1", room1);
        ButtonMap.put("room2", room2);
        ButtonMap.put("room3", room3);
        ButtonMap.put("room4", room4);
    }

    // initial game room according to the given map
    public void initialize() {
        InitButtonMap();
        try {

            String startOrnot1 = (String) objectInputStream.readObject();
            String startOrnot2 = (String) objectInputStream.readObject();
            String startOrnot3 = (String) objectInputStream.readObject();
            String startOrnot4 = (String) objectInputStream.readObject();

            if (startOrnot1.equals("true")) {
                System.out.println("ROOM 1 FULL");
                ButtonMap.get("room1").setDisable(true);
            }
            if (startOrnot2.equals("true")) {
                ButtonMap.get("room2").setDisable(true);
            }
            if (startOrnot3.equals("true")) {
                ButtonMap.get("room3").setDisable(true);
            }
            if (startOrnot4.equals("true")) {
                ButtonMap.get("room4").setDisable(true);
            }
        }
        catch(Exception e){
            e.getStackTrace();
        }
    }


    public selectRoomController(Stage Window, ObjectInputStream objectInputStream,ObjectOutputStream objectOutputStream ) {
        this.Window = Window;
        System.out.println("input name and password.\n click join");
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;

    }

    private void joinRoomHelper(int playerNum, ActionEvent actionEvent) throws Exception {

        System.out.println("enter the joining room helper");
        objectOutputStream.reset();
        objectOutputStream.writeObject(playerNum-1);

        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/FXML/MapView.fxml"));
        loaderStart.setControllerFactory(c -> {
           //  make a map for n players
            MapFactory mapFactory = new MapFactory();
            Map map = mapFactory.makeMap(playerNum);
            HashSet<Player> players = map.getPlayer();
            Player player = players.iterator().next();
            MapController mc = new MapController(this.Window, map,player,objectInputStream,objectOutputStream);
            return mc;
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
        updateColorText();
        unitChecking();
    }

    @FXML
    public void JoinRoom1(ActionEvent actionEvent) throws Exception {
        joinRoomHelper(2, actionEvent);
    }

    @FXML
    public void JoinRoom2(ActionEvent actionEvent) throws Exception {
        joinRoomHelper(3, actionEvent);
    }

    @FXML
    public void JoinRoom3(ActionEvent actionEvent) throws Exception {
        joinRoomHelper(4, actionEvent);
    }

    @FXML
    public void JoinRoom4(ActionEvent actionEvent) throws Exception {
        joinRoomHelper(5, actionEvent);
    }

    public String unitPopup() throws Exception{
        try {
            UnitPopup popup = new UnitPopup();
            popup.display();
            return popup.unitPlacement;
        } catch (IOException e) {
            System.out.println("Could not display Unit Popup");
        }
        return null;
    }

    public void unitChecking() throws Exception {
            System.out.println("******In unit checking");
            String unitChecking = "";
            String unitPlacement = "";
            try {
            unitPlacement = unitPopup();
            System.out.println(unitPlacement);
            objectOutputStream.reset();
            objectOutputStream.writeObject(unitPlacement);
            unitChecking = (String) objectInputStream.readObject();
            }
            catch (IOException e) {
                System.out.println(e);
            }

            while (unitChecking.equals("false")){
                try{
                unitPlacement = unitPopup();
                objectOutputStream.reset();
                objectOutputStream.writeObject(unitPlacement);
                unitChecking = (String) objectInputStream.readObject();
                }
                catch (IOException e) {
                System.out.println(e);
                }
            }
    }

    public void updateColorText() {
        try{
            String color = (String)objectInputStream.readObject();
            color_text.setText(color);
            System.out.println("Get color:::" + color);
        }
        catch(Exception e){
            e.getStackTrace();
        }
    }



    
}
