package org.HuellaCarbono.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.HuellaCarbono.App;
import org.HuellaCarbono.model.DAO.UsuarioDAO;
import org.HuellaCarbono.model.entity.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomePageController extends Controller implements Initializable {
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    @FXML
    public void Login() throws IOException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Completa todos los campos.");
            alert.show();
            return;
        }

        Usuario user = UsuarioDAO.build().findByName(username);

        if (user != null) {
            if (password.equals(user.getContrasena())) {
                MainController.changeScene(Scenes.MainPage, user.getId()); // Pass user ID
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Contraseña incorrecta.");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Usuario no encontrado.");
            alert.show();
        }
    }

    @FXML
    private void goToRegister() throws IOException {
        changeScene(Scenes.RegisterPage, null);
    }

    public static void changeScene(Scenes scene, Object data) throws IOException {
        View view = MainController.loadFXML(scene);
        Scene _scene = new Scene(view.scene, 600, 695);
        App.currentController = view.controller;
        App.currentController.onOpen(data);
        App.stage.setScene(_scene);
        App.stage.show();
    }
}
