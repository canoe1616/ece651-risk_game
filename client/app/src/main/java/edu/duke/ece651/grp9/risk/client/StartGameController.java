package edu.duke.ece651.grp9.risk.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartGameController implements Initializable {
    private Stage Window;
    public StartGameController(Stage Window) {
        this.Window = Window;
    }

    @FXML
    public void login() throws IOException {
        System.out.println("click on log in");
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/FXML/LoginView.fxml"));
        loaderStart.setControllerFactory(c->{
            return new logInPageController(Window);
        });
        Scene scene = new Scene(loaderStart.load());
        Window.setScene(scene);
        Window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
