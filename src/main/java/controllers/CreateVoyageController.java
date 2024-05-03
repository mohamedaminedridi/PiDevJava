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
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import services.ProprieteService;
import services.VoyageService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

public class CreateVoyageController implements Initializable {

    @FXML
    private DatePicker dateDeb;

    @FXML
    private DatePicker dateFin;

    @FXML
    private TextField descTF;

    @FXML
    private TextField nbrPersonneTF;

    @FXML
    private TextField prixTF;

    @FXML
    private TextField propTF;

    @FXML
    private TextField titreTF;

    private File selectedFile; // Field to store the selected file

    @FXML
    private ComboBox<Propriete> propCB;
    VoyageService vs = new VoyageService();
    ProprieteService ps = new ProprieteService();

    private ObservableList<Propriete> ProprieteList; // Define an ObservableList to hold voyages


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dateDeb.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now())); // Disable dates before today
            }
        });
        dateFin.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (dateDeb.getValue() != null) {
                    setDisable(date.isBefore(dateDeb.getValue())); // Disable dates before dated
                }
            }
        });
        try {
            ProprieteList = FXCollections.observableArrayList(ps.read()); // Fetch voyages and populate list
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        propCB.setItems(ProprieteList);
        propCB.setValue(null);
        propCB.setCellFactory(cell -> new CreateVoyageController.CategoryCellFactory());
        propCB.setConverter(new CreateVoyageController.CategoryStringConverter());
    }

    private static class CategoryCellFactory extends javafx.scene.control.ListCell<Propriete> {

        @Override
        protected void updateItem(Propriete propriete, boolean empty) {
            super.updateItem(propriete, empty);
            if (propriete != null) {
                setText(propriete.getTitre()); // Set the cell text to voyage title
            } else {
                setText(null);
            }
        }
    }
    private static class CategoryStringConverter extends StringConverter<Propriete> {

        @Override
        public String toString(Propriete propriete) {
            return propriete != null ? propriete.getTitre() : null; // Convert voyage to its title
        }

        @Override
        public Propriete fromString(String string) {
            // Not needed for this example
            return null;
        }
    }

    @FXML
    void Cancel(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/voyageList.fxml"));
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



    @FXML
    void Create(ActionEvent event) throws SQLException, IOException {

        LocalDate dateDebut = dateDeb.getValue();
        LocalDate dateeFin = dateFin.getValue();

// Check for null values before conversion
        Date datedebut = (dateDebut != null) ? java.sql.Date.valueOf(dateDebut) : null;
        Date datefin = (dateeFin != null) ? java.sql.Date.valueOf(dateeFin) : null;

        String titre = titreTF.getText();
        String desc = descTF.getText();
        String nbrPersonne = nbrPersonneTF.getText();
        String prix = prixTF.getText();

        String Pic = "";
        Pic = selectedFile != null ? selectedFile.toURI().toString() : "";


        Propriete selectedPropriete = (Propriete) propCB.getValue();

        if (selectedPropriete == null) {
            // Show an error message if no category is selected
            System.out.println("No voyage selected.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a propriete");
            alert.showAndWait();
            return; // Exit the method if no category is selected
        }

        if (titre.isEmpty() || desc.isEmpty() || nbrPersonne.isEmpty() || prix.isEmpty()  || titre.isEmpty() || Objects.equals(Pic, "")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        } else if (!isValidString(titre) || !isValidString(desc)  || !isValidDouble(prix)|| !isValidInt(nbrPersonne)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter valid information.");
            alert.showAndWait();
        } else {


            int prixint = parseInt(prix);
            int nbrPersonneint = parseInt(nbrPersonne);

            Voyage voy = new Voyage(selectedPropriete.getId(),nbrPersonneint,Pic,desc,titre,prixint,datedebut,datefin);
            vs.add(voy);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Crée");
            alert.setHeaderText(null);
            alert.setContentText("Voyage Crée !");
            alert.getButtonTypes().clear(); // Remove existing button types
            alert.getButtonTypes().add(ButtonType.OK); // Add only OK button type
            alert.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/voyageList.fxml"));
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

    private boolean isValidDouble(String value) {
        // Check if the value is a valid double
        return value.matches("-?\\d+(\\.\\d+)?");
    }

}
