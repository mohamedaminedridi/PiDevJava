package controllers;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import entities.Reservation;
import entities.Voyage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import services.EmailSender;
import services.ReservationService;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CardController {


    @FXML
    private ImageView ImageVoy;

    @FXML
    private Label descLB;

    @FXML
    private Label titleLB;


    @FXML
    private AnchorPane box;


    Voyage voyage;

    ReservationService rs = new ReservationService();




    @FXML
    void Reserver(ActionEvent event) throws IOException, SQLException {
        // add reservation logique here (user integration)
        Reservation Res = new Reservation(1, voyage.getId(), voyage.getNbr_personne(), String.valueOf(voyage.getPrixtotal()),"en cours");
        generateQRCode(voyage.getId(), String.valueOf(voyage.getNbr_personne()));

        rs.add(Res);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scan.fxml"));
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reservé");
        alert.setHeaderText(null);
        alert.setContentText("Voyage Reservé !");
        alert.getButtonTypes().clear(); // Remove existing button types
        alert.getButtonTypes().add(ButtonType.OK); // Add only OK button type
        EmailSender.sendEmail("didicabiste75321@gmail.com", "idrv qlgo takb glpb", "dridimeding15@gmail.com", "Reservation", "Votre reservation de : " + voyage.getTitle()+" a été effectuée avec succès");

        alert.showAndWait();
        Parent root;
        try {
            root = loader.load();
            ScanController controller = loader.getController();
            controller.initData(voyage.getId());
            Scene scene = titleLB.getScene();
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

    private void generateQRCode(int productId, String productName) {
        String qrCodeText = "Voyage ID: " + productId + "\nNombre de personnes: " + productName;
        String filePath = "QRCode_Voyage_" + productId + ".png";
        int size = 250;
        String fileType = "png";

        Map<EncodeHintType, Object> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, fileType, path);
            System.out.println("QR Code generated successfully for Product ID: " + productId);
        } catch (WriterException | IOException e) {
            System.out.println("Error generating QR code for Product ID " + productId + ": " + e.getMessage());
        }
    }

    private String [] colors = {"B9E5FF","BDB2FE","FB9AA8","FF5056"};


    public void setData (Voyage voy){
        this.voyage = voy;
        titleLB.setText(voy.getTitle());
        descLB.setText(voy.getDescription());
        if (voyage.getImage() != null){
            ImageVoy.setImage(new Image(voyage.getImage()));
        }
        ImageVoy.setImage(new Image(voyage.getImage()));
        box.setStyle("-fx-background-color: " + Color.web(colors[(int)(Math.random()* colors.length)]));
    }




    @FXML
    void goToDetails(ActionEvent event) {
        try {
            Stage stage = (Stage) titleLB.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/voyageDetails.fxml"));
            Parent root = loader.load();
            System.out.println("1");
            VoyageDetailsController controller = loader.getController();
            System.out.println("2");

            controller.setVoyage(voyage);
            System.out.println("3");

            System.out.println(voyage);
            System.out.println("4");

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
