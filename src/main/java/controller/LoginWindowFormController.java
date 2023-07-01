package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginWindowFormController {

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXButton joinBtn;

    @FXML
    void enterUserName(ActionEvent event) {
        txtUserName.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER){
                joinBtn.fire();
            }
        });
    }

    @FXML
    void userJoinProcess(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ClientChatBoxForm.fxml"))));
        stage.show();
    }

}
