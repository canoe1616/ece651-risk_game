package edu.duke.ece651.grp9.risk.client;

import edu.duke.ece651.grp9.risk.shared.Map;
import edu.duke.ece651.grp9.risk.shared.MapFactory;
import edu.duke.ece651.grp9.risk.shared.Player;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.TextInputControlMatchers;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class ClientControllerTest extends ApplicationTest {
    Button button;
    StartGameController cont;
    Client client;
    Stage stage;
    public ObjectOutputStream objectOutputStream;
    public ObjectInputStream objectInputStream;

    @Override
    public void start(Stage stage) throws Exception{
        client = new Client();
        new Thread(() -> {
            try {
                ServerSocket socket = new ServerSocket(12345);
                Socket clientSocket = socket.accept();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                objectOutputStream.writeObject(null);
                objectOutputStream.reset();

            } catch (Exception e) {

            }
        }).start();

        Thread.sleep(1000);
        client.start(stage);

//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StartView.fxml")));
//        stage.setScene(new Scene(root));
        stage.show();


    }


    @Test
    void getStyle() {
        MapController cont = new MapController(stage, new Map(), new Player("red"), null, null);
        String s = cont.getStyle("red");
        assertEquals("-fx-background-color: red;;-fx-border-color: black;", s);
    }


    @Test
    void test_login(FxRobot robot) throws IOException {
        MapFactory mapFactory = new MapFactory();
        Map map = mapFactory.makeMap(2);
        cont = new StartGameController(stage,objectInputStream, objectOutputStream);
        //stage.show();
       try {
           robot.clickOn("#login");
           cont.login();
//
//           robot.doubleClickOn("#username").write("ryu");
//           robot.doubleClickOn("#password").write("ryu");
//           FxAssert.verifyThat("#username", TextInputControlMatchers.hasText("ryu"));
//           FxAssert.verifyThat("#password", TextInputControlMatchers.hasText("ryu"));
//
//           robot.clickOn("#join");
       } catch (Exception e){

       }

        WaitForAsyncUtils.waitForFxEvents();

    }

}
