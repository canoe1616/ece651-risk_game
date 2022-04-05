package edu.duke.ece651.grp9.risk.client;

import edu.duke.ece651.grp9.risk.shared.Action;
import java.io.IOException;
import java.util.HashSet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class MapController {//implements Initializable {

  Scene scene;
  @FXML
  Label territoryStats;

  @FXML
  Label status;

  public static HashSet<String> attacks = new HashSet<>();
  public static HashSet<String> moves = new HashSet<>();
  public static HashSet<String> upgrades = new HashSet<>();
  public static boolean techAction = false;

  public static Scene show() throws IOException {
    URL xmlRes = MapController.class.getResource("/fxml/Map.fxml");
    assert (xmlRes != null);

    GridPane gp = FXMLLoader.load(xmlRes);

    return new Scene(gp, 640, 480);
  }

  @FXML
  public void onCreateAttack(ActionEvent actionEvent) {
    Object source = actionEvent.getSource();
    if (source instanceof Button) {
      Button btn = (Button) source;
      System.out.println(btn.getId());
    } else {
      throw new IllegalArgumentException("Invalid source");
    }

    try {
      AttackPopup popup = new AttackPopup();
      popup.display();
      String[] words = validAction(popup.action);
      if (words != null) {
        attacks.add(popup.action);
        statusLabel("Attack " + words[1] + " with " + words[2] + "(" + words[3] +
            ") units from " + words[0]);
      } else {
        statusLabel("Invalid Action");
      }

    } catch (IOException e) {
      System.out.println("Could not display Attack Popup");
    }
  }

  @FXML
  public void onCreateMove(ActionEvent actionEvent) {
    Object source = actionEvent.getSource();
    if (source instanceof Button) {
      Button btn = (Button) source;
      System.out.println(btn.getId());
    } else {
      throw new IllegalArgumentException("Invalid source");
    }

    try {
      MovePopup popup = new MovePopup();
      popup.display();
      String[] words = validAction(popup.action);
      if (words != null) {
        attacks.add(popup.action);
        statusLabel("Move " + words[2] + "(" + words[3] +
            ") units from " + words[0] + " to " + words[1]);
      } else {
        statusLabel("Invalid Action");
      }
    } catch (IOException e) {
      System.out.println("Could not display Move Popup");
    }
  }

  @FXML
  public void onCreateUpgrade(ActionEvent actionEvent) {
    Object source = actionEvent.getSource();
    if (source instanceof Button) {
      Button btn = (Button) source;
      System.out.println(btn.getId());
    } else {
      throw new IllegalArgumentException("Invalid source");
    }

    try {
      UpgradePopup popup = new UpgradePopup();
      popup.display();
      String[] words = validAction(popup.upgrade);
      if (words != null) {
        attacks.add(popup.upgrade);
        statusLabel("Upgrade " + words[1] + " units in "
            + words[0] + " from level" + words[2] + " to " + words[3]);
      } else {
        statusLabel("Invalid Action");
      }
    } catch (IOException e) {
      System.out.println("Could not display Upgrade Popup");
    }
  }

  @FXML
  public void onTerritory(ActionEvent actionEvent) {
    Object source = actionEvent.getSource();
    if (source instanceof Button) {
      Button btn = (Button) source;
      StringBuilder sb = new StringBuilder();
      sb.append("Territory " + btn.getText() + "\n");
      int[] unitCounts = new int[] {13, 0, 0, 0, 0, 0, 0};
      for (int i = 0; i < 6; i++) {
        sb.append("Level " + i + ": " + unitCounts[i] + "\n");
      }
      territoryStats.setText(sb.toString());
    } else {
      throw new IllegalArgumentException("Invalid source");
    }
  }

  @FXML
  public void onLevelUp(ActionEvent actionEvent) {
    Object source = actionEvent.getSource();
    if (source instanceof Button) {
      Button btn = (Button) source;
      techAction = true;
      statusLabel("Upgrade Tech Level");
    } else {
      throw new IllegalArgumentException("Invalid source");
    }
  }

  @FXML
  public void onDone(ActionEvent actionEvent) {
    Object source = actionEvent.getSource();
    if (source instanceof Button) {
      Button btn = (Button) source;
      for (String move : moves) {
        System.out.println("Move : " + move);
      }
      for (String attack : attacks) {
        System.out.println("Attack : " + attack);
      }
      for (String upgrade : upgrades) {
        System.out.println("Upgrade : " + upgrade);
      }
      if (techAction) {
        System.out.println("Player tech level upgrade");
      }
      statusLabel("Actions submitted to server");
    } else {
      throw new IllegalArgumentException("Invalid Done Button");
    }
    resetActions();
  }

  public void statusLabel(String message) {
    String label = "Last Action: " + message;
    status.setText(label);
  }

  public String[] validAction(String actionString) {
    if (actionString == null) {
      return null;
    }
    String[] words = actionString.split(" ");
    if (words.length != 4) {
      return null;
    }
    return words;
  }

  public static void resetActions() {
    attacks.clear();
    moves.clear();
    upgrades.clear();
    techAction = false;
  }
}
