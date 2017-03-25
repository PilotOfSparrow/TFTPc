package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/sample.fxml"));
        Parent root = loader.load();


        primaryStage.setTitle("TFTP client");
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }


}
