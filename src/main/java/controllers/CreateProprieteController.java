package controllers;

import entities.Propriete;
import entities.Voyage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ProprieteService;
import services.VoyageService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

public class CreateProprieteController  {
    private File selectedFile; // Field to store the selected file


    @FXML
    private TextField NbrChbrTF;

    @FXML
    private TextField adresseTF;

    @FXML
    private TextField capatiteTF;

    @FXML
    private TextField descTF;

    @FXML
    private TextField nbrSalleTF;

    @FXML
    private TextField prixTF;

    @FXML
    private TextField titreTF;



    ProprieteService ps = new ProprieteService();
    VoyageService vs = new VoyageService();



    @FXML
    void Create(ActionEvent event) throws SQLException, IOException {

        String nbrChbr = NbrChbrTF.getText();
        String adresse = adresseTF.getText();
        String capatite = capatiteTF.getText();
        String desc = descTF.getText();
        String nbrSalle = nbrSalleTF.getText();
        String prix = prixTF.getText();
        String titre = titreTF.getText();
        String profilePic = "";
        profilePic = selectedFile != null ? selectedFile.toURI().toString() : "";

        if (nbrChbr.isEmpty() || adresse.isEmpty() || capatite.isEmpty() ||  desc.isEmpty() || nbrSalle.isEmpty() || prix.isEmpty() || titre.isEmpty() || Objects.equals(profilePic, "")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        } else if (!isValidString(desc) || !isValidString(titre) ||!isValidString(adresse) || !isValidInt(nbrChbr)|| !isValidInt(capatite)|| !isValidInt(nbrSalle)|| !isValidInt(prix)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter valid information.");
            alert.showAndWait();
        } else {


            int nbrChbrint = parseInt(nbrChbr);
            int nbrSalleint = parseInt(nbrSalle);
            int capatiteint = parseInt(capatite);
            int prixint = parseInt(prix);
            Propriete prop = new Propriete(1,nbrChbrint,nbrSalleint,capatiteint,titre,desc,adresse,profilePic,prixint);
            ps.add(prop);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Crée");
            alert.setHeaderText(null);
            alert.setContentText("Propriete Crée !");
            alert.getButtonTypes().clear(); // Remove existing button types
            alert.getButtonTypes().add(ButtonType.OK); // Add only OK button type
            alert.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/listePropriete.fxml"));
            Parent root = loader.load();
            Scene scene = titreTF.getScene();
            if (scene != null) {
                Stage currentStage = (Stage) scene.getWindow();
                currentStage.close();
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }

    }

    @FXML
    void chooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        // Show open file dialog
        selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String imagePath = selectedFile.toURI().toString();
            System.out.println("Selected file: " + imagePath);
            // Now you can save the imagePath to the database or perform any other operation
        }
    }


    private boolean isValidInt(String value) {
        // Check if the value is a valid integer
        return value.matches("-?\\d+");
    }

    private boolean isValidString(String name) {
        // Check if the name contains only letters and has length between 2 and 50
        return name.matches("^[a-zA-Z\\s]{2,50}$");
    }

    @FXML
    void Cancel(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/listePropriete.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene scene = titreTF.getScene();
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


}
