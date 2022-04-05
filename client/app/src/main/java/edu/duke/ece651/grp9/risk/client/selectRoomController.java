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

    public static ObjectOutputStream outputStream;
    public static ObjectInputStream inputStream;

    public selectRoomController(Stage Window, ObjectInputStream inputStream,ObjectOutputStream outputStream ) {
        this.Window = Window;
        System.out.println("input name and password.\n click join");
    }

    private void joinRoomHelper(Text roomNum, int playerNum, ActionEvent actionEvent) throws IOException {
        Object source = actionEvent.getSource();
        if (source instanceof Button) {
            Button btn = (Button) source;
            if (Integer.parseInt(roomNum.getText()) >= playerNum-1) {
                btn.setDisable(true);
            }
            roomNum.setText(Integer.toString(Integer.parseInt(roomNum.getText()) + 1));
            FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/FXML/MapView.fxml"));
            loaderStart.setControllerFactory(c -> {
                // make a map for n players
                MapFactory mapFactory = new MapFactory();
                Map map = mapFactory.makeMap(playerNum);
                HashSet<Player> players = map.getPlayer();
                Player player = players.iterator().next();
                return new MapController(this.Window, map,player);
            });
            Scene scene = new Scene(loaderStart.load());
            this.Window.setScene(scene);
            this.Window.show();
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
}
