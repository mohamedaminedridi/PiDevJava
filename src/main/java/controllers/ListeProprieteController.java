package controllers;

import entities.Propriete;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListeProprieteController implements Initializable {

    @FXML
    private TableColumn<?, ?> adresseCol;

    @FXML
    private TableColumn<?, ?> capaciteCol;

    @FXML
    private TableColumn<?, ?> descCol;

    @FXML
    private TableColumn<?, ?> nbrBainCol;

    @FXML
    private TableColumn<?, ?> nbrChambreCol;

    @FXML
    private TableColumn<?, ?> prixCol;

    @FXML
    private TableView<Propriete> tabProp;

    @FXML
    private TableColumn<?, ?> titreCol;

    private List<Propriete> proprieteList;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ListeProprietes();
        } catch (SQLException e) {
            Logger.getLogger(ListeProprieteController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void GoToVoy(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/voyageList.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene scene = tabProp.getScene();
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
    void Create(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/createPropriete.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene scene = tabProp.getScene();
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

    public void ListeProprietes() throws SQLException {

        ProprieteService cs = new ProprieteService();
        // Initialize table columns
        titreCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        adresseCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        nbrChambreCol.setCellValueFactory(new PropertyValueFactory<>("nombrechambre"));
        nbrBainCol.setCellValueFactory(new PropertyValueFactory<>("nombresalle"));
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prixnuit"));
        capaciteCol.setCellValueFactory(new PropertyValueFactory<>("capacite"));

        boolean deleteColumnExists = false;
        boolean ModifyColumnExists = false;

        for (TableColumn column : tabProp.getColumns()) {
            if (column.getText().equals("Action")) {
                deleteColumnExists = true;
                break;
            }
        }

        if (!deleteColumnExists) {
            TableColumn<Propriete, Void> deleteColumn = new TableColumn<>("Action");
            deleteColumn.setCellFactory(column -> {
                return new TableCell<Propriete, Void>() {
                    private final Button deleteButton = new Button("Delete");

                    {
                        deleteButton.setOnAction(event -> {
                            Propriete c = getTableView().getItems().get(getIndex());
                            ProprieteService cs = new ProprieteService();
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

            tabProp.getColumns().add(deleteColumn);
        }

        if (!ModifyColumnExists) {
            TableColumn<Propriete, Void> modifyColumn = new TableColumn<>("Update");
            modifyColumn.setCellFactory(column -> {
                return new TableCell<Propriete, Void>() {
                    private final Button modifyButton = new Button("Modify");

                    {
                        modifyButton.setOnAction(event -> {
                            Propriete selectedPropriete = getTableView().getItems().get(getIndex());
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifyPropriete.fxml"));
                            Parent root;
                            try {
                                root = loader.load();
                                ModifyProprieteController controller = loader.getController();
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

            tabProp.getColumns().add(modifyColumn);
        }

        // Load voyages from the database
        List<Propriete> list = cs.read();
        System.out.println(list);
        ObservableList<Propriete> observableList = FXCollections.observableArrayList(list);
        tabProp.setItems(observableList);

    }

    public void refreshTable() {
        try {
            proprieteList = new ProprieteService().read();
            tabProp.setItems(FXCollections.observableArrayList(proprieteList));
        } catch (SQLException ex) {
            Logger.getLogger(ListeProprieteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
