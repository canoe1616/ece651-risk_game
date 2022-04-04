package edu.duke.ece651.grp9.risk.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Client extends Application {

    private Stage Window;

    @Override
    public void start(Stage stage) throws IOException {
        this.Window = stage;
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/FXML/StartView.fxml"));
        loaderStart.setControllerFactory(c -> {
            return new StartGameController(this.Window);
        });
        Scene scene = new Scene(loaderStart.load());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

