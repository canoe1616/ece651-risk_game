package edu.duke.ece651.grp9.risk.client;

import edu.duke.ece651.grp9.risk.shared.Map;
import edu.duke.ece651.grp9.risk.shared.MapFactory;
import edu.duke.ece651.grp9.risk.shared.Player;
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

    @FXML Text room1num;
    @FXML Text room2num;
    @FXML Text room3num;
    @FXML Text room4num;

    public  ObjectOutputStream objectOutputStream;
    public  ObjectInputStream objectInputStream;

    public selectRoomController(Stage Window, ObjectInputStream objectInputStream,ObjectOutputStream objectOutputStream ) {
        this.Window = Window;
        System.out.println("input name and password.\n click join");
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }

    private void joinRoomHelper(Text roomNum, int playerNum, ActionEvent actionEvent) throws IOException {

        System.out.println("enter the joining room helper");
        objectOutputStream.reset();
        objectOutputStream.writeObject(playerNum-1);

        Object source = actionEvent.getSource();
        if (source instanceof Button) {
            Button btn = (Button) source;
            if (Integer.parseInt(roomNum.getText()) >= playerNum-1) {
                btn.setDisable(true);
            }
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
    }

    @FXML
    public void JoinRoom1(ActionEvent actionEvent) throws IOException {
        joinRoomHelper(room1num,2, actionEvent);
    }

    @FXML
    public void JoinRoom2(ActionEvent actionEvent) throws IOException {
        joinRoomHelper(room2num,3, actionEvent);
    }

    @FXML
    public void JoinRoom3(ActionEvent actionEvent) throws IOException {
        joinRoomHelper(room3num,4, actionEvent);
    }

    @FXML
    public void JoinRoom4(ActionEvent actionEvent) throws IOException {
        joinRoomHelper(room4num, 5, actionEvent);
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