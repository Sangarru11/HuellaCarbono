package org.HuellaCarbono.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class sHuellaHabitoController extends Controller implements Initializable {
    @FXML
    private ImageView huellaImageView;
    @FXML
    private ImageView habitoImageView;

    private Integer userId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        huellaImageView.setOnMouseClicked(event -> {
            try {
                MainController.changeScene(Scenes.MainPage, userId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        habitoImageView.setOnMouseClicked(event -> {
            try {
                MainController.changeScene(Scenes.MainPageHabito, userId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public Object onOpen(Object input) throws IOException {
        if (input instanceof Integer) {
            this.userId = (Integer) input;
        }
        return input;
    }

    @Override
    public void onClose(Object output) {
        // Logic to close the view if necessary
    }
}
