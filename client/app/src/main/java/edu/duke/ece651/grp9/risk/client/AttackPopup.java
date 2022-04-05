package edu.duke.ece651.grp9.risk.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AttackPopup {

  private static Stage popupwindow;
  public static String action;

  @FXML
  TextField sourceTerritory;
  @FXML
  TextField destinationTerritory;
  @FXML
  TextField numUnits;
  @FXML
  Slider unitLevel;

  @FXML
  public static void display() throws IOException {
    popupwindow = new Stage();

    popupwindow.initModality(Modality.APPLICATION_MODAL);
    popupwindow.setTitle("Create Attack");

//    Label source = new Label("Source Territory");
//    Label destination = new Label("Destination Territory");
//    Label numUnits = new Label("Number of Unit's Attacking");
//    Label unitLevel = new Label("Level of Unit's Attacking");
//    Button submitButton = new Button("Submit");
//    Button cancelButton = new Button("Cancel");
//    submitButton.setOnAction(e -> popupwindow.close());
//    cancelButton.setOnAction(e -> popupwindow.close());
    //VBox layout = new VBox(10);
    //layout.getChildren().addAll(source, destination, numUnits, unitLevel, submitButton, cancelButton);
    //layout.setAlignment(Pos.CENTER);

    URL xmlRes = MapController.class.getResource("/fxml/AttackPopup.fxml");
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
      Button btn = (Button) source;
      popupwindow.close();
      this.action = null;
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
      String action = sourceTerritory.getText() + " " + destinationTerritory.getText() + " " +
          numUnits.getText() + " " + (int)unitLevel.getValue();
      this.action = action;
    } else {
      throw new IllegalArgumentException("Invalid source");
    }
  }
}
