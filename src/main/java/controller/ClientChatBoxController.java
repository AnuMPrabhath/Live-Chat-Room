package controller;

import bo.BOFactory;
import bo.custom.LoginBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import client.Client;
import com.sun.glass.events.KeyEvent;
import dto.UserDTO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClientChatBoxController implements Initializable {

    @FXML
    public VBox vBox;
    @FXML
    private JFXTextArea chatTextArea;

    @FXML
    private JFXTextField messageTextField;

    @FXML
    private ImageView emojiImageView;

    @FXML
    private ImageView sendImageImageView;

    @FXML
    private Text titleText;

    @FXML
    private ImageView sendMessageImageView;

    @FXML
    private VBox optionVBox;

    @FXML
    private JFXButton addMemberButton;

    @FXML
    private JFXButton leaveGroupButton;

    @FXML
    private AnchorPane anchorPaneEmoji;

    @FXML
    private GridPane gridPaneEmoji;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane root;

    private Client client;

    LoginBO loginBO = (LoginBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LOGIN);

    private final String[] emojis = {
            "\uD83D\uDE00", // 😀
            "\uD83D\uDE01", // 😁
            "\uD83D\uDE02", // 😂
            "\uD83D\uDE03", // 🤣
            "\uD83D\uDE04", // 😄
            "\uD83D\uDE05", // 😅
            "\uD83D\uDE06", // 😆
            "\uD83D\uDE07", // 😇
            "\uD83D\uDE08", // 😈
            "\uD83D\uDE09", // 😉
            "\uD83D\uDE0A", // 😊
            "\uD83D\uDE0B", // 😋
            "\uD83D\uDE0C", // 😌
            "\uD83D\uDE0D", // 😍
            "\uD83D\uDE0E", // 😎
            "\uD83D\uDE0F", // 😏
            "\uD83D\uDE10", // 😐
            "\uD83D\uDE11", // 😑
            "\uD83D\uDE12", // 😒
            "\uD83D\uDE13"  // 😓
    };

    @FXML
    void addMemberButtonOnMouseClicked(MouseEvent event) throws IOException {
        Stage loginStage = new Stage();
        loginStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginWindowForm.fxml"))));
        loginStage.show();
    }

    @FXML
    void leaveGroupOnMouseClicked(MouseEvent event) {
        try {
            UserDTO userDTO = loginBO.getUser(client.getName());
            loginBO.updateUser(new UserDTO(userDTO.getUsername(), userDTO.getPassword(), 0));

     //       ServerFormController.appendText(client.getName() + " is Disconnected..!");

            Stage stage = (Stage) root.getScene().getWindow();
            stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void pressEnterOnAction(ActionEvent event) {

    }

    @FXML
    void sendImageOnAction(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("select Image File");
        FileChooser.ExtensionFilter imageFiles = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(imageFiles);
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null){
            try {
                byte[] bytes = Files.readAllBytes(file.toPath());
                HBox hBox = new HBox();
                hBox.setStyle("-fx-fill-height: true; -fx-min-height: 50; -fx-pref-width: 520; -fx-max-width: 520; -fx-padding: 30; -fx-alignment: center-right;");

                // Display the image in an ImageView or any other UI component
                ImageView imageView = new ImageView(new Image(new FileInputStream(file)));
                imageView.setStyle("-fx-padding: 10px;");
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);

                hBox.getChildren().addAll(imageView);
                vBox.getChildren().add(hBox);

                client.sendImage(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void sendMessageOnMouseClicked(MouseEvent event) {
        try {
            String text = messageTextField.getText();
            if (!text.equals("")) {
                appendText(text);
                client.sendMessage(text);
                messageTextField.clear();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Message is Empty").show();
            }
        } catch (IOException e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong : server down").show();
        }
    }


    @FXML
    void showEmojisOnMouseClicked(MouseEvent event) {
        anchorPaneEmoji.setVisible(!anchorPaneEmoji.isVisible());
    }

    @FXML
    void showOptionVBoxOnMouseClicked(MouseEvent event) {
        optionVBox.setVisible(true);
    }

    @FXML
    void onMouseExited(MouseEvent event) {
        optionVBox.setVisible(false);
    }

    public void setClient(Client client){
        this.client = client;
    }

    public void setTitle(String title){
        this.titleText.setText(title);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchorPaneEmoji.setVisible(false);
        int buttonIndex = 0;
        for (int row = 0; row < 4; row++){
            for (int column =0; column < 5; column++){
                if (buttonIndex < emojis.length){
                    String emoji = emojis[buttonIndex];
                    JFXButton emojiButton = createEmojiButton(emoji);
                    gridPaneEmoji.add(emojiButton, row, column);
                    buttonIndex++;
                }else {
                    break;
                }
            }
        }
    }

    private JFXButton createEmojiButton(String emoji) {
        JFXButton button = new JFXButton(emoji);
        button.getStyleClass().add("emoji-button");
        button.setOnAction(this::emojiButtonAction);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setFillWidth(button, true);
        GridPane.setFillHeight(button, true);
        button.setStyle("-fx-font-size: 15; -fx-text-fill: black; -fx-background-color: #F0F0F0; -fx-border-radius: 50");
        return button;
    }
    private void emojiButtonAction(ActionEvent event) {
        JFXButton button = (JFXButton) event.getSource();
        messageTextField.appendText(button.getText());
    }

    void appendText(String message) {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-right;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
        Label messageLbl = new Label(message);
        messageLbl.setStyle("-fx-background-color:  #008011;-fx-background-radius:15;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
        hBox.getChildren().add(messageLbl);
        vBox.getChildren().add(hBox);
        new Thread(() -> {
            playSound("media/Message_Sent.mp3");
        }).start();
    }
    private void playSound(String sound) {
        try {
            new Player(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream(sound))).play();
        } catch (JavaLayerException e) {
            new Alert(Alert.AlertType.ERROR, "Audio Problem.!!").show();
        }
    }

    public void setImage(byte[] bytes, String sender) {
        HBox hBox = new HBox();
        Label messageLbl = new Label(sender);
        messageLbl.setStyle("-fx-background-color:   #2980b9;-fx-background-radius:15;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");

        hBox.setStyle("-fx-fill-height: true; -fx-min-height: 50; -fx-pref-width: 520; -fx-max-width: 520; -fx-padding: 10; " + (sender.equals(client.getName()) ? "-fx-alignment: center-right;" : "-fx-alignment: center-left;"));
        // Display the image in an ImageView or any other UI component
        Platform.runLater(() -> {
            ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(bytes)));
            imageView.setStyle("-fx-padding: 10px;");
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);

            hBox.getChildren().addAll(messageLbl, imageView);
            vBox.getChildren().add(hBox);

        });
    }

    public void writeMessage(String message) {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-left;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
        Label messageLbl = new Label(message);
        messageLbl.setStyle("-fx-background-color:   #2980b9;-fx-background-radius:15;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
        hBox.getChildren().add(messageLbl);
        Platform.runLater(() -> {
            vBox.getChildren().add(hBox);
        });

    }
}
