package controllers;

import entities.Voyage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import services.VoyageService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class VoyageListeFrontController implements Initializable {


    @FXML
    private HBox cardLayout;


    private List<Voyage> voyageList; // Define an ObservableList to hold voyages

    VoyageService vs = new VoyageService();



    public void refresh() {


        voyageList = new ArrayList<>(allVoyages());

        // Create a GridPane for arranging cards
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10); // Adjust spacing between cards as needed
        gridPane.setVgap(10);

        // Counter to track the number of cards added in the current row
        int colIndex = 0;
        int rowIndex = 0;

        for (Voyage voyage : voyageList) {
            try {
                // Load the card layout with the product
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/card.fxml"));
                AnchorPane box = fxmlLoader.load();

                CardController cardController = fxmlLoader.getController();

                cardController.setData(voyage);

                // Add the card to the GridPane, considering max 3 elements per row
                gridPane.add(box, colIndex, rowIndex);
                colIndex++;

                // Move to the next row if the maximum number of elements per row is reached
                if (colIndex >= 2) {
                    colIndex = 0;
                    rowIndex++;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                // Handle exception
            }
        }

        // Clear the existing cards and add the GridPane for rendering
        cardLayout.getChildren().clear();
        cardLayout.getChildren().add(gridPane);
    }

    private List<Voyage> allVoyages() {
        List<Voyage> voyageList = new ArrayList<>();
        try {
            voyageList = vs.read();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle exception
        }
        return voyageList;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            refresh();
    }
}
