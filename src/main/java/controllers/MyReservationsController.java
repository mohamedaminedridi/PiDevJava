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

public class MyReservationsController implements Initializable {




    @FXML
    private TableColumn<?, ?> nbrGuestCol;

    @FXML
    private TableColumn<?, ?> priceCol;

    @FXML
    private TableColumn<?, ?> statusCol;

    @FXML
    private TableView<Reservation> tabRes;

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
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("totalprice"));
        nbrGuestCol.setCellValueFactory(new PropertyValueFactory<>("numberguest"));
        voyIdCol.setCellValueFactory(new PropertyValueFactory<>("voyage_id"));

        boolean ModifyColumnExists = false;




        List<Reservation> list = rs.read();
        System.out.println(list);
        ObservableList<Reservation> observableList = FXCollections.observableArrayList(list);
        tabRes.setItems(observableList);

    }


}
