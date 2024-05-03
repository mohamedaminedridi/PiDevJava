package controllers;

import entities.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import services.ReservationService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TreatReservationController implements Initializable {

    Reservation selectedReservation ;
    ReservationService rs = new ReservationService();

    @FXML
    private Label nbrGuestLB;

    @FXML
    private Label priceLB;

    @FXML
    private ComboBox<String> statusCB;

    @FXML
    private Label userLB;

    @FXML
    private Label voyLB;

    @FXML
    void Treat(ActionEvent event) throws SQLException {
        String etat = statusCB.getValue();
        rs.updateStatus(selectedReservation.getId(),etat);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reservationList.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene scene = userLB.getScene();
            if (scene != null) {
                Stage currentStage = (Stage) scene.getWindow();
                currentStage.close();
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(TreatReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    void initData(Reservation res) throws SQLException {
        this.selectedReservation = res;
        nbrGuestLB.setText(String.valueOf(res.getNumberguest()));
        priceLB.setText(String.valueOf(res.getTotalprice()));
        String name = rs.GetUserNameByID(res.getUser_id());
        userLB.setText(name);
        String voyName = rs.GetVoyageTitleByID(res.getVoyage_id());
        voyLB.setText(voyName);
        statusCB.setValue(res.getStatus());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statusCB.getItems().addAll("en cours", "confirmé", "refusé");

    }
}
