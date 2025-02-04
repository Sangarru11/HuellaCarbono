package org.HuellaCarbono.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.HuellaCarbono.App;
import org.HuellaCarbono.model.entity.Usuario;
import org.HuellaCarbono.model.services.UsuarioService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RegisterPageController extends Controller implements Initializable {
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtEmail;

    private UsuarioService usuarioService;

    public RegisterPageController() {
        this.usuarioService = new UsuarioService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public Object onOpen(Object input) throws IOException {
        return input;
    }

    @Override
    public void onClose(Object output) {

    }

    @FXML
    public void Register() throws IOException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String email = txtEmail.getText();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Completa todos los campos.");
            alert.show();
            return;
        }

        Usuario user = usuarioService.getUsuarioByUsername(username);

        if (user != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("El usuario ya existe.");
            alert.show();
        } else {
            Usuario newUser = new Usuario();
            newUser.setNombre(username);
            newUser.setContrasena(password);
            newUser.setEmail(email);
            newUser.setFechaRegistro(LocalDate.now());
            usuarioService.saveUsuario(newUser);
            changeScene(Scenes.WelcomePage, null);
        }
    }

    @FXML
    private void goToWelcome() throws IOException {
        changeScene(Scenes.WelcomePage, null);
    }

    public static void changeScene(Scenes scene, Object data) throws IOException {
        View view = MainController.loadFXML(scene);
        Scene _scene = new Scene(view.scene, 600, 617);
        App.currentController = view.controller;
        App.currentController.onOpen(data);
        App.stage.setScene(_scene);
        App.stage.show();
    }
}