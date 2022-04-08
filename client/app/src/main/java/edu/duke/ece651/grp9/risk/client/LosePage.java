package edu.duke.ece651.grp9.risk.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.*;
import java.net.Socket;

public class LosePage {
    private Stage Window;
    public ObjectOutputStream objectOutputStream;
    public ObjectInputStream objectInputStream;

    public LosePage(Stage Window, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.Window = Window;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }

    @FXML
    public void back2room() throws IOException {
        Socket socket = new Socket("localhost", 8080);
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            this.Window = Window;
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/FXML/StartView.fxml"));
        loaderStart.setControllerFactory(c->{
            return new StartGameController(Window,objectInputStream,objectOutputStream);
        });
        Scene scene = new Scene(loaderStart.load());
        Window.setScene(scene);
        Window.show();
    }

}
