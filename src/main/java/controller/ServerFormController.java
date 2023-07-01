package controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ServerFormController implements Initializable {

    @FXML
    private JFXTextArea areaServer;

    private ServerSocket serverSocket;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private BufferedReader bufferedReader;
    private String message;
    //private ArrayList<> arrayList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(3002);
                socket = serverSocket.accept();
                areaServer.appendText("\tServer Started..!\n\n");
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                while (bufferedReader.equals("finish")){
                    message = dataInputStream.readUTF();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
