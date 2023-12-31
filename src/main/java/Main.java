import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ServerForm.fxml"))));
        stage.show();
        stage.setOnCloseRequest(event -> {
            System.exit(0);
        });

        Stage loginStage = new Stage();
        loginStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginWindowForm.fxml"))));
        loginStage.show();
    }
}
