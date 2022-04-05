package edu.duke.ece651.grp9.risk.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class selectRoomController implements Initializable {
    private Stage Window;

//    @FXML Text room1num;
//    @FXML Text room2num;
//    @FXML Text room3num;
//    @FXML Text room4num;
//
//    @FXML
//    private void initialize() {
//        room1num.setText("I'm a Label.");
//    }

    public selectRoomController(Stage Window) {
        this.Window = Window;
        //initialize();
        System.out.println("input name and password.\n click join");
    }


    @FXML
    public void JoinRoom1() throws IOException {
        int numPlayer = 2;
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/FXML/GameRoomView.fxml"));
        loaderStart.setControllerFactory(c->{
            return new GameRoomController(numPlayer, this.Window);
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
    }

    @FXML
    public void JoinRoom2() throws IOException {
        int numPlayer = 3;
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/FXML/GameRoomView.fxml"));
        loaderStart.setControllerFactory(c->{
            return new GameRoomController(numPlayer, this.Window);
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
    }

    @FXML
    public void JoinRoom3() throws IOException {
        int numPlayer = 4;
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/FXML/GameRoomView.fxml"));
        loaderStart.setControllerFactory(c->{
            return new GameRoomController(numPlayer, this.Window);
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
    }

    @FXML
    public void JoinRoom4() throws IOException {
        int numPlayer = 5;
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/FXML/GameRoomView.fxml"));
        loaderStart.setControllerFactory(c->{
            return new GameRoomController(numPlayer, this.Window);
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
