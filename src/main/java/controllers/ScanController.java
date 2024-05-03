package controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ScanController {
    @FXML
    private ImageView QrImage;

    @FXML
    void GoBack(MouseEvent event) {

    }


    void initData(int id){
        System.out.println(id + "#########################################");
       // QrImage.setImage(new Image(String.valueOf(getClass().getResource("/QR_Codes/"+"QRCode_Voyage_" + id +".png"))));
        String imagePath = "/QRCode_Voyage_" + id + ".png";
        Image image = new Image(getClass().getResource(imagePath).toString());
        QrImage.setImage(image);

    }

}
