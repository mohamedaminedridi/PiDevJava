package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.MyDatabase;

import java.io.IOException;
import java.sql.Connection;


public class FXMain extends Application {

    @Override
    public void start(Stage primaryStage) {

        try {

            Connection cnx;
            cnx = MyDatabase.getInstance().getConnection();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/voyageList.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("sami");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

