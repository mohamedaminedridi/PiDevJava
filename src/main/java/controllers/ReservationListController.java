package controllers;

import entities.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.ReservationService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationListController implements Initializable {

    @FXML
    private TableColumn<?, ?> nbrGuestCol;

    @FXML
    private TableColumn<?, ?> priceCol;

    @FXML
    private TableColumn<?, ?> statusCol;

    @FXML
    private TableView<Reservation> tabRes;

    @FXML
    private TableColumn<?, ?> userCol;

    @FXML
    private TableColumn<?, ?> voyIdCol;

    ReservationService rs = new ReservationService();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ListeReservations();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void ListeReservations() throws SQLException {
        userCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("totalprice"));
        nbrGuestCol.setCellValueFactory(new PropertyValueFactory<>("numberguest"));
        voyIdCol.setCellValueFactory(new PropertyValueFactory<>("voyage_id"));

        boolean ModifyColumnExists = false;


        if (!ModifyColumnExists) {
            TableColumn<Reservation, Void> modifyColumn = new TableColumn<>("Treat");
            modifyColumn.setCellFactory(column -> {
                return new TableCell<Reservation, Void>() {
                    private final Button modifyButton = new Button("Modify");

                    {
                        modifyButton.setOnAction(event -> {
                            Reservation selectedReservation = getTableView().getItems().get(getIndex());
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/treatReservation.fxml"));
                            Parent root;
                            try {
                                root = loader.load();
                                TreatReservationController controller = loader.getController();
                                controller.initData(selectedReservation);
                                Scene scene = modifyButton.getScene();
                                if (scene != null) {
                                    Stage currentStage = (Stage) scene.getWindow();
                                    currentStage.close();
                                }
                                Stage stage = new Stage();
                                stage.setScene(new Scene(root));
                                stage.show();

                            } catch (IOException ex) {
                                Logger.getLogger(ReservationListController.class.getName()).log(Level.SEVERE, null, ex);
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

            tabRes.getColumns().add(modifyColumn);
        }

        List<Reservation> list = rs.read();
        System.out.println(list);
        ObservableList<Reservation> observableList = FXCollections.observableArrayList(list);
        tabRes.setItems(observableList);

    }
}
