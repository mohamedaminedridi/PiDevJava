package controllers;

import entities.Propriete;
import entities.Service;
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
import services.ServiceService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceListeController implements Initializable {

    @FXML
    private TableColumn<?, ?> descCol;

    @FXML
    private TableColumn<?, ?> nomCol;

    @FXML
    private TableColumn<?, ?> prixCol;

    @FXML
    private TableView<Service> tabService;

    ServiceService ss = new ServiceService();

    private List<Service> serviceList;


    @FXML
    void Create(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/createService.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene scene = tabService.getScene();
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ListeServices();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void ListeServices () throws SQLException {
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prix"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        boolean deleteColumnExists = false;
        boolean ModifyColumnExists = false;

        for (TableColumn column : tabService.getColumns()) {
            if (column.getText().equals("Action")) {
                deleteColumnExists = true;
                break;
            }
        }

        if (!deleteColumnExists) {
            TableColumn<Service, Void> deleteColumn = new TableColumn<>("Action");
            deleteColumn.setCellFactory(column -> {
                return new TableCell<Service, Void>() {
                    private final Button deleteButton = new Button("Delete");

                    {
                        deleteButton.setOnAction(event -> {
                            Service c = getTableView().getItems().get(getIndex());
                            ServiceService cs = new ServiceService();
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete Service");
                            alert.setHeaderText("Are you sure you want to delete this Service?");
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

            tabService.getColumns().add(deleteColumn);
        }

        if (!ModifyColumnExists) {
            TableColumn<Service, Void> modifyColumn = new TableColumn<>("Update");
            modifyColumn.setCellFactory(column -> {
                return new TableCell<Service, Void>() {
                    private final Button modifyButton = new Button("Modify");

                    {
                        modifyButton.setOnAction(event -> {
                            Service selectedService = getTableView().getItems().get(getIndex());
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifyService.fxml"));
                            Parent root;
                            try {
                                root = loader.load();
                                ModifyServiceController controller = loader.getController();
                                controller.initData(selectedService);
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
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
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

            tabService.getColumns().add(modifyColumn);
        }

        // Load voyages from the database
        List<Service> list = ss.read();
        System.out.println(list);
        ObservableList<Service> observableList = FXCollections.observableArrayList(list);
        tabService.setItems(observableList);


    }

    public void refreshTable() {
        try {
            serviceList = new ServiceService().read();
            tabService.setItems(FXCollections.observableArrayList(serviceList));
        } catch (SQLException ex) {
            Logger.getLogger(ListeProprieteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
