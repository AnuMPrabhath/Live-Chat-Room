package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import server.Server;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerFormController implements Initializable {

    @FXML
    private JFXTextArea areaServer;

    @FXML
    private AnchorPane root;

    @FXML
    private JFXButton closeButton;

    @FXML
    void closeServer(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Server server = Server.getServerSocket();
            Thread thread = new Thread(server);
            thread.start();

            areaServer.appendText("Server Started..!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     /*static void appendText(String msg){
        areaServer.appendText(msg);
    }*/
}
