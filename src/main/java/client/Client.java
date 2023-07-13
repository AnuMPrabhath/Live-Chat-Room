package client;

import bo.BOFactory;
import bo.custom.LoginBO;
import controller.ClientChatBoxController;
import controller.ServerFormController;
import dto.UserDTO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class Client implements Runnable, Serializable {
    private final String name;
    private final Socket socket;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    private ClientChatBoxController clientChatBoxController;

    LoginBO loginBO = (LoginBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LOGIN);

    public Client(String name) throws IOException {
        this.name = name;

        socket = new Socket("localhost", 3002);
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());

        outputStream.writeUTF(name);
        outputStream.flush();

        loadClientChat();
//        System.out.println("Sount");
        new Thread(() -> {
            playSound("media/Entrence_Sound.mp3");
        }).start();

        try {
            UserDTO userDTO = loginBO.getUser(name);
            loginBO.updateUser(new UserDTO(userDTO.getUsername(), userDTO.getPassword(), 1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void loadClientChat() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ClientChatBoxForm.fxml"));
        Parent parent = fxmlLoader.load();
        clientChatBoxController = fxmlLoader.getController();
        clientChatBoxController.setClient(this);
        stage.setResizable(false);
        stage.setScene(new Scene(parent));
        stage.setTitle("Play Tech(Pvt) Ltd");
        clientChatBoxController.setTitle(name + "'s Fun Chat");
        stage.show();

        stage.setOnCloseRequest(event -> {
            try {
                outputStream.writeUTF(" has left...");
                outputStream.flush();

                inputStream.close();
                outputStream.close();
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        new Alert(Alert.AlertType.INFORMATION, "Dear "+name+"..! Now You Entered Fun Chat!!").show();
    }

    private void playSound(String sound) {
        try {
            new Player(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream(sound))).play();
        } catch (JavaLayerException e) {
            new Alert(Alert.AlertType.ERROR, "Audio Problem.!!").show();
        }
    }

    @Override
    public void run() {
        try {
            outputStream.writeUTF("Joined to Fun Chat..!");
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true){
            try {
                String message = inputStream.readUTF();
                if (message.equals("*image*")){
                    receiveImage();
                }else {
                    clientChatBoxController.writeMessage(message);
                }
            } catch (IOException e) {
                try {
                    inputStream.close();
                    outputStream.close();
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void receiveImage() throws IOException {
        String utf = inputStream.readUTF();
        int size = inputStream.readInt();
        byte[] bytes = new byte[size];
        inputStream.readFully(bytes);
        clientChatBoxController.setImage(bytes, utf);
    }

    public void sendMessage(String msg) throws IOException {
        outputStream.writeUTF(msg);
        outputStream.flush();

    }

    public void sendImage(byte[] bytes) throws IOException {
     //   System.out.println(bytes);
        outputStream.writeUTF("*image*");
        outputStream.writeInt(bytes.length);
        outputStream.write(bytes);
        outputStream.flush();
    }

    public String getName() {
        return name;
    }
}
