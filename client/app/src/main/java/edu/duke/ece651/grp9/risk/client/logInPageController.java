package edu.duke.ece651.grp9.risk.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.LinkedBlockingQueue;

public class logInPageController {
    public ObjectOutputStream objectOutputStream;
    public ObjectInputStream objectInputStream;
    private Stage Window;
    public String name;
    public  String pwd;
    private String errMsg;
    private LinkedBlockingQueue<Pair<String, String>> loginList;






    @FXML TextField username;

    @FXML
    TextField password;

    @FXML
    Text errorMessage;

    public logInPageController(Stage Window, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.Window = Window;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        System.out.println("input name and password.\n click join");
    }


    @FXML
    public void join() throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/FXML/SelectRoomView.fxml"));
        this.name = username.getText();
        this.pwd = password.getText();
//        username.setText("");
//        password.setText("");

        if (name != null & pwd != null){
            objectOutputStream.reset();
            objectOutputStream.writeObject(name);
            objectOutputStream.reset();
            objectOutputStream.writeObject(pwd);
        }



        // TODO: need to reader info from controller to client

        loaderStart.setControllerFactory(c->{
            return new selectRoomController(this.Window);
        });

        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
    }

}
