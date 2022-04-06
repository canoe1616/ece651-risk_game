package edu.duke.ece651.grp9.risk.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UnitPopup {

    private static Stage popupwindow;
    public static String unitPlacement;
    public static ObjectOutputStream outputStream;
    public static ObjectInputStream inputStream;

    @FXML
    TextField unitPlacements;

    @FXML
    public static void display() throws Exception {
        popupwindow = new Stage();


        popupwindow.initModality(Modality.APPLICATION_MODAL);
        try{
            String unit_line = (String)inputStream.readObject();
            System.out.println("read unit_line: " + unit_line);
            popupwindow.setTitle(unit_line);
        }
        catch(Exception e){
            e.getStackTrace();
        }
      
        URL xmlRes = MapController.class.getResource("/fxml/UnitPopup.fxml");
        assert (xmlRes != null);
        GridPane gp = FXMLLoader.load(xmlRes);
        gp.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(gp, 150, 240);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }

    @FXML
    public void onPlacement(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source instanceof Button) {
            Button btn = (Button) source;
            popupwindow.close();
            this.unitPlacement = unitPlacements.getText();


            //send this unit initial placement

        } else {
            throw new IllegalArgumentException("Invalid source");
        }
    }

    
}
