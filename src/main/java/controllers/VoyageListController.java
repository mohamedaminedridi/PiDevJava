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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.ProprieteService;
import services.VoyageService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VoyageListController implements Initializable {
    @FXML
    private TableColumn<?, ?> dateDebCol;

    @FXML
    private TableColumn<?, ?> dateFinCol;

    @FXML
    private TableColumn<?, ?> descCol;

    @FXML
    private TableColumn<?, ?> nbrPerCol;

    @FXML
    private TableColumn<?, ?> prixCol;

    @FXML
    private TableView<Voyage> tabVoy;

    @FXML
    private TableColumn<?, ?> titreCol;

    private List<Voyage> voyageList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ListeVoyages();
        } catch (SQLException e) {
            Logger.getLogger(ListeProprieteController.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    @FXML
    void Create(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/createVoyage.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene scene = tabVoy.getScene();
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
    void GoToProp(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/listePropriete.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene scene = tabVoy.getScene();
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

    public void ListeVoyages() throws SQLException {
        VoyageService cs = new VoyageService();
        // Initialize table columns
        titreCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        nbrPerCol.setCellValueFactory(new PropertyValueFactory<>("nbr_personne"));
        dateFinCol.setCellValueFactory(new PropertyValueFactory<>("datefin"));
        dateDebCol.setCellValueFactory(new PropertyValueFactory<>("datedebut"));
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prixtotal"));

        boolean deleteColumnExists = false;
        boolean ModifyColumnExists = false;

        for (TableColumn column : tabVoy.getColumns()) {
            if (column.getText().equals("Action")) {
                deleteColumnExists = true;
                break;
            }
        }

        if (!deleteColumnExists) {
            TableColumn<Voyage, Void> deleteColumn = new TableColumn<>("Action");
            deleteColumn.setCellFactory(column -> {
                return new TableCell<Voyage, Void>() {
                    private final Button deleteButton = new Button("Delete");

                    {
                        deleteButton.setOnAction(event -> {
                            Voyage c = getTableView().getItems().get(getIndex());
                            VoyageService cs = new VoyageService();
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete Propriete");
                            alert.setHeaderText("Are you sure you want to delete this Propriete?");
                            alert.setContentText("This action cannot be undone.");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                try {
                                    System.out.println(c);
                                    cs.delete(c);

                                    refreshTable();
                                } catch (SQLException ex) {
                                    Logger.getLogger(ListeProprieteController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {

                                alert.close();
                            }

                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(deleteButton);
                        }
                    }
                };
            });

            tabVoy.getColumns().add(deleteColumn);
        }

        if (!ModifyColumnExists) {
            TableColumn<Voyage, Void> modifyColumn = new TableColumn<>("Update");
            modifyColumn.setCellFactory(column -> {
                return new TableCell<Voyage, Void>() {
                    private final Button modifyButton = new Button("Modify");

                    {
                        modifyButton.setOnAction(event -> {
                            Voyage selectedPropriete = getTableView().getItems().get(getIndex());
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifyVoyage.fxml"));
                            Parent root;
                            try {
                                root = loader.load();
                                ModifyVoyageController controller = loader.getController();
                                controller.initData(selectedPropriete);
                                Scene scene = modifyButton.getScene();
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
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(modifyButton);
                        }
                    }
                };
            });

            tabVoy.getColumns().add(modifyColumn);
        }

        // Load voyages from the database
        List<Voyage> list = cs.read();
        System.out.println(list);
        ObservableList<Voyage> observableList = FXCollections.observableArrayList(list);
        tabVoy.setItems(observableList);


    }


    public void refreshTable() {
        try {
            voyageList = new VoyageService().read();
            tabVoy.setItems(FXCollections.observableArrayList(voyageList));
        } catch (SQLException ex) {
            Logger.getLogger(ListeProprieteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
