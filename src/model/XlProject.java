package model;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class XlProject extends Application {
    public static ArrayList<Player> players;
    
    @Override
    public void start(Stage stage) throws Exception {
        players = new ArrayList<>();
        Parent root = FXMLLoader.load(getClass().getResource("/view/Apresentation.fxml"));
        
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Xedrez Leveling");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
