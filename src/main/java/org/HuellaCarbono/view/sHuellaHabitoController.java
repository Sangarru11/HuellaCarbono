package org.HuellaCarbono.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class sHuellaHabitoController extends Controller implements Initializable {
    private Integer userId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
    @FXML
    public void goToWelcome() throws IOException {
        WelcomePageController.changeScene(Scenes.WelcomePage, null);
    }
    @FXML
    public void setHuellaImageView() throws IOException {
        MainController.changeScene(Scenes.MainPage, userId);
    }
    @FXML
    public void setHabitoImageView() throws IOException {
        MainController.changeScene(Scenes.MainPageHabito, userId);
    }
}