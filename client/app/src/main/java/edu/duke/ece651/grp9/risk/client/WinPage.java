package edu.duke.ece651.grp9.risk.client;

import javafx.stage.Stage;

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
}
