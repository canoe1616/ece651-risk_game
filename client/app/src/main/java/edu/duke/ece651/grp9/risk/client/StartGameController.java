package edu.duke.ece651.grp9.risk.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class StartGameController implements Initializable {
    private Stage Window;
    public ObjectOutputStream objectOutputStream;
    public ObjectInputStream objectInputStream;

    public StartGameController(Stage Window, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.Window = Window;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }

    @FXML
    public void login() throws IOException {
        System.out.println("click on log in");
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/FXML/LoginView.fxml"));
        loaderStart.setControllerFactory(c->{
            return new logInPageController(Window,objectInputStream,objectOutputStream);
        });
        Scene scene = new Scene(loaderStart.load());
        Window.setScene(scene);
        Window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
