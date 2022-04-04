package edu.duke.ece651.grp9.risk.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class selectRoomController {
    private Stage Window;
    public selectRoomController(Stage Window) {
        this.Window = Window;
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
}
