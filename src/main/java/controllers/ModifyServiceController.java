package controllers;

import entities.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

public class ModifyServiceController {

    Service selectedService;
    @FXML
    private TextField descTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField prixTF;
    ServiceService ss = new ServiceService();


    @FXML
    void Cancel(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/servicesListe.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene scene = prixTF.getScene();
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

    @FXML
    void Modify(ActionEvent event) throws SQLException, IOException {
        String nom = nomTF.getText();
        String desc = descTF.getText();
        String prix = prixTF.getText();





        if (prix.isEmpty() || desc.isEmpty() || nom.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        } else if (!isValidString(nom) || !isValidString(desc) ||!isValidInt(prix) ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter valid information.");
            alert.showAndWait();
        } else {
            int prixint = parseInt(prix);
            Service ser = new Service(selectedService.getId(),prixint,nom,desc);
            ss.update(ser);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Modifié");
            alert.setHeaderText(null);
            alert.setContentText("Service Modifié !");
            alert.getButtonTypes().clear(); // Remove existing button types
            alert.getButtonTypes().add(ButtonType.OK); // Add only OK button type
            alert.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/servicesListe.fxml"));
            Parent root = loader.load();
            Scene scene = prixTF.getScene();
            if (scene != null) {
                Stage currentStage = (Stage) scene.getWindow();
                currentStage.close();
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    void initData(Service service) throws IOException, SQLException {
        selectedService = service;
        nomTF.setText(service.getNom());
        descTF.setText(service.getDescription());
        prixTF.setText(String.valueOf(service.getPrix()));


    }

    private boolean isValidInt(String value) {
        // Check if the value is a valid integer
        return value.matches("-?\\d+");
    }

    private boolean isValidString(String name) {
        // Check if the name contains only letters and has length between 2 and 50
        return name.matches("^[a-zA-Z\\s]{2,50}$");
    }
}
