package controllers;

import entities.Voyage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import services.ProprieteService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VoyageDetailsController {

    Voyage voyage;

    @FXML
    private ImageView PropImage;

    @FXML
    private Label PropriteLB;

    @FXML
    private Label descLB;

    @FXML
    private Label nbrPerLB;

    @FXML
    private Label prixLB;

    @FXML
    private Label titleLB;

    @FXML
    private ImageView voyImage;

    ProprieteService ps = new ProprieteService();



    @FXML
    void Reserver(ActionEvent event) throws IOException {
        // add reservation logique here (user integration)

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reservé");
        alert.setHeaderText(null);
        alert.setContentText("Voyage Reservé !");
        alert.getButtonTypes().clear(); // Remove existing button types
        alert.getButtonTypes().add(ButtonType.OK); // Add only OK button type
        alert.showAndWait();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/VoyageListeFront.fxml"));
        Parent root = loader.load();
        Scene scene = titleLB.getScene();
        if (scene != null) {
            Stage currentStage = (Stage) scene.getWindow();
            currentStage.close();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void goBack(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/VoyageListeFront.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene scene = prixLB.getScene();
            if (scene != null) {
                Stage currentStage = (Stage) scene.getWindow();
                currentStage.close();
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(ListeProprieteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setVoyage(Voyage voy) throws SQLException {
        this.voyage = voy;
        titleLB.setText(voy.getTitle());
        descLB.setText(voy.getDescription());
        prixLB.setText(String.valueOf(voy.getPrixtotal()));
        nbrPerLB.setText(String.valueOf(voy.getNbr_personne()));
        String name = ps.NameFromId(voy.getPropriete_id());
        PropriteLB.setText(name);
        if (voyage.getImage() != null){
            voyImage.setImage(new Image(voyage.getImage()));
        }
        voyImage.setImage(new Image(voyage.getImage()));
    }
}
