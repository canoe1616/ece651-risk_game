package edu.duke.ece651.grp9.risk.client;

import javafx.stage.Stage;

public class logInPageController {
    private Stage Window;
    public logInPageController(Stage Window) {
        this.Window = Window;
        System.out.println("input name and password.\n click join");
    }

//    @FXML
//    public void control() throws IOException {
//        System.out.println("log in page");
//        showChooseView();
//    }

//    @FXML
//    public void join() throws IOException {
//        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/FXML/LoginView.fxml"));
//
//        Scene scene = new Scene(loaderStart.load());
//        this.Window.setScene(scene);
//        this.Window.show();
//    }
}
