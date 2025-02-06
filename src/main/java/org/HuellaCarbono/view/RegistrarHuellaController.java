package org.HuellaCarbono.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.HuellaCarbono.model.entity.Actividad;
import org.HuellaCarbono.model.entity.Categoria;
import org.HuellaCarbono.model.entity.Huella;
import org.HuellaCarbono.model.entity.Usuario;
import org.HuellaCarbono.model.services.ActividadService;
import org.HuellaCarbono.model.services.HuellaService;
import org.HuellaCarbono.model.services.UsuarioService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class RegistrarHuellaController extends Controller implements Initializable {
    private Integer userId;
    private ActividadService actividadService;
    private HuellaService huellaService;
    private UsuarioService usuarioService;

    @FXML
    private ComboBox<Actividad> actividadComboBox;
    @FXML
    private Text unidad;
    @FXML
    private TextField cantidad;
    @FXML
    private DatePicker fecha;

    public RegistrarHuellaController() {
        this.actividadService = new ActividadService();
        this.huellaService = new HuellaService();
        this.usuarioService = new UsuarioService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        actividadComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Categoria categoria = newValue.getIdCategoria();
                unidad.setText(categoria.getUnidad());
            }
        });
    }

    @Override
    public Object onOpen(Object input) throws IOException {
        if (input instanceof Integer) {
            this.userId = (Integer) input;
            List<Actividad> actividades = actividadService.getAllActividades();
            actividadComboBox.getItems().setAll(actividades);
        }
        return input;
    }

    @Override
    public void onClose(Object output) {

    }

    @FXML
    public void registrarHuella() throws IOException {
        Actividad actividad = actividadComboBox.getValue();
        String valor = cantidad.getText();
        String date = fecha.getValue().toString();
        Usuario usuario = usuarioService.getUsuarioById(this.userId);

        if (actividad == null || valor.isEmpty() || date.isEmpty() || usuario == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Campos incompletos");
            alert.setContentText("Por favor, complete todos los campos.");
            alert.showAndWait();
        } else {
            Huella huella = new Huella();
            huella.setIdActividad(actividad);
            huella.setValor(Integer.valueOf(valor));
            huella.setUnidad(unidad.getText());
            huella.setIdUsuario(usuario);
            huella.setFecha(LocalDate.parse(date));

            huellaService.saveHuella(huella);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText("Huella registrada");
            alert.setContentText("La huella ha sido registrada exitosamente.");
            alert.showAndWait();

            returnToMain();
        }
    }

    @FXML
    public void returnToMain() throws IOException {
        MainController.changeScene(Scenes.MainPage, this.userId);
    }
}