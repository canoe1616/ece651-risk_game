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
import java.util.HashSet;
import java.util.ResourceBundle;

public class selectRoomController implements Initializable {
    private Stage Window;

    @FXML Button room1;
    @FXML Button room2;
    @FXML Button room3;
    @FXML Button room4;

    public  ObjectOutputStream objectOutputStream;
    public  ObjectInputStream objectInputStream;

    public selectRoomController(Stage Window, ObjectInputStream objectInputStream,ObjectOutputStream objectOutputStream ) {
        this.Window = Window;
        System.out.println("input name and password.\n click join");
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        try {
            //3
            String startOrnot1 = (String) objectInputStream.readObject();
            String startOrnot2 = (String) objectInputStream.readObject();
            String startOrnot3 = (String) objectInputStream.readObject();
            String startOrnot4 = (String) objectInputStream.readObject();


            System.out.println(startOrnot1 );
            System.out.println(startOrnot2);
            System.out.println(startOrnot3);
            System.out.println(startOrnot4);
            startOrnot2 = "true";

            if (startOrnot1.equals("true")) {
                System.out.println("ROOM 1 FULL");
                room1.setDisable(true);
            }
            if (startOrnot2.equals("true")) {
                room2.setDisable(true);
            }
            if (startOrnot3.equals("true")) {
                room3.setDisable(true);
            }
            if (startOrnot4.equals("true")) {
                room4.setDisable(true);
            }
        }
        catch(Exception e){
            e.getStackTrace();
        }
    }

    private void joinRoomHelper(int playerNum, ActionEvent actionEvent) throws IOException {

        System.out.println("enter the joining room helper");
        objectOutputStream.reset();
        objectOutputStream.writeObject(playerNum-1);

        Object source = actionEvent.getSource();

            //read something from the server

//                if (playerNum == 2) {
//                    //read something from the server
//
//                    if(startOrnot.equals(true)){
//                        btn.setDisable(true);
//                    }
//
//                }
//                if (playerNum == 3) {
//                    String startOrnot3 = (String) objectInputStream.readObject();
//                    if(startOrnot.equals(true)){
//                        btn.setDisable(true);
//                    }
//                }
//                if(playerNum ==4){
//                    String startOrnot = (String) objectInputStream.readObject();
//                    if(startOrnot.equals(true)){
//                        btn.setDisable(true);
//                    }
//                }
//                if(playerNum ==5){
//                    String startOrnot = (String) objectInputStream.readObject();
//                    if(startOrnot.equals(true)){
//                        btn.setDisable(true);
//                    }
//                }
 //           }
  //          catch(Exception e){
   //             System.out.println(e);
           // }
         //   roomNum.setText(Integer.toString(Integer.parseInt(roomNum.getText()) + 1));
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

            String unitChecking = unitPopup();
            try {
                objectOutputStream.reset();
                objectOutputStream.writeObject(unitChecking);
            }
            catch (IOException e) {
                System.out.println("cannot send the problem with unitchecking");
            }
        }

    @FXML
    public void JoinRoom1(ActionEvent actionEvent) throws IOException {
        joinRoomHelper(2, actionEvent);
    }

    @FXML
    public void JoinRoom2(ActionEvent actionEvent) throws IOException {
        joinRoomHelper(3, actionEvent);
    }

    @FXML
    public void JoinRoom3(ActionEvent actionEvent) throws IOException {
        joinRoomHelper(4, actionEvent);
    }

    @FXML
    public void JoinRoom4(ActionEvent actionEvent) throws IOException {
        joinRoomHelper(5, actionEvent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public String unitPopup() {
        try {
            UnitPopup popup = new UnitPopup();
            popup.display();
            return popup.unitPlacement;
        } catch (IOException e) {
            System.out.println("Could not display Unit Popup");
        }
        return null;
    }
}
