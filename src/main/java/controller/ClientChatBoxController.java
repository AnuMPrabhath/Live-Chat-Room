package controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientChatBoxController {

    @FXML
    private JFXTextArea chatTextArea;

    @FXML
    private JFXTextField messageTextField;

    @FXML
    private ImageView emojiImageView;

    @FXML
    private ImageView sendImageImageView;

    @FXML
    private ImageView sendVoiceImageView;

    @FXML
    private Text titleText;

    @FXML
    private ImageView showNewLoginWindow;

    @FXML
    private ImageView sendMessageImageView;

    @FXML
    void pressEnterOnAction(ActionEvent event) {

    }

    @FXML
    void sendImageOnAction(MouseEvent event) {

    }

    @FXML
    void sendMessageOnMouseClicked(MouseEvent event) {

    }

    @FXML
    void sendVoiceOnAction(MouseEvent event) {

    }

    @FXML
    void showEmojisOnMouseClicked(MouseEvent event) {

    }

    @FXML
    void showNewLoginWindowOnAction(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginWindowForm.fxml"))));
        stage.show();
    }

}
