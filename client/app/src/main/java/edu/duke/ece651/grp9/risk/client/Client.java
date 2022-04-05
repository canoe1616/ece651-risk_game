package edu.duke.ece651.grp9.risk.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Client extends Application {

    private Stage Window;
    private Socket socket;



    @Override
    public void start(Stage stage) throws IOException {
        try{
            this.socket = new Socket("localhost", 8080);
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            this.Window = stage;

            FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/FXML/StartView.fxml"));
            loaderStart.setControllerFactory(c -> {
                return new StartGameController(Window,objectInputStream, objectOutputStream);
            });
            Scene scene = new Scene(loaderStart.load());
            stage.setScene(scene);
            stage.show();

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


