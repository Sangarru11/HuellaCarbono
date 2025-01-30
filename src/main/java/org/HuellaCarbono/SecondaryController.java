package org.HuellaCarbono;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.HuellaCarbono.view.Controller;
import org.HuellaCarbono.view.MainController;
import org.HuellaCarbono.view.Scenes;

public class SecondaryController extends Controller implements Initializable{

    @FXML
    private void switchToPrimary() throws IOException {
        MainController.changeScene(Scenes.Secondary, null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }
}