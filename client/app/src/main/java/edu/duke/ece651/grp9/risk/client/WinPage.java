package edu.duke.ece651.grp9.risk.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class WinPage {
    private Stage Window;
    public ObjectOutputStream objectOutputStream;
    public ObjectInputStream objectInputStream;

    public WinPage(Stage Window, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.Window = Window;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }

    @FXML
    public void back2room() throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/FXML/StartView.fxml"));
        loaderStart.setControllerFactory(c->{
            //debug 4.9
            return new StartGameController(Window,objectInputStream,objectOutputStream);
        });
        Scene scene = new Scene(loaderStart.load());
        Window.setScene(scene);
        Window.show();
    }

}
