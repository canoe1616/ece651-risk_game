package edu.duke.ece651.grp9.risk.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ProtectPopup {
    private static Stage popupwindow;
    public static String protect;

    @FXML
    TextField sourceTerritory;

    @FXML
    public static void display() throws IOException {
        popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Create Cloak");

        URL xmlRes = MapController.class.getResource("/FXML/ProtectPopup.fxml");
        assert (xmlRes != null);
        GridPane gp = FXMLLoader.load(xmlRes);
        gp.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(gp, 350, 300);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }

    @FXML
    public void onCancel(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source instanceof Button) {
            popupwindow.close();
            this.protect = null;
        } else {
            throw new IllegalArgumentException("Invalid source");
        }
    }

    @FXML
    public void onSubmit(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source instanceof Button) {
            Button btn = (Button) source;
            popupwindow.close();
            String action = sourceTerritory.getText();
            this.protect = action;
        } else {
            throw new IllegalArgumentException("Invalid source");
        }
    }
}
