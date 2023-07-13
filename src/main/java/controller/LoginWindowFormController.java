package controller;

import bo.BOFactory;
import bo.custom.LoginBO;
import client.Client;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.UserDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class LoginWindowFormController {
    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXButton joinBtn;

    @FXML
    private JFXTextField txtPassword;

    LoginBO loginBO = (LoginBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LOGIN);

    @FXML
    void enterPassword(ActionEvent event) {
        txtPassword.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER){
                joinBtn.fire();
            }
        });
    }

    @FXML
    void enterUserName(ActionEvent event) {
        txtPassword.requestFocus();
    }

    @FXML
    void userJoinProcess(ActionEvent event)  {
        try {
            UserDTO userDTO = loginBO.getUser(txtUserName.getText());
            if (userDTO != null) {
                if (userDTO.getPassword().equals(txtPassword.getText()) && userDTO.getStatus() == 0){
                    try {
                        Client client = new Client(txtUserName.getText());

                        Thread thread = new Thread(client);
                        thread.start();

                       // ServerFormController.appendText(txtUserName.getText() + " is Connected to Server..!");

                        Stage stage = (Stage) root.getScene().getWindow();
                        stage.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    new Alert(Alert.AlertType.INFORMATION, "Oops..User is in Fun Chat..!").show();
                }
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Enter Correct Details..!").show();
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }

    }

}