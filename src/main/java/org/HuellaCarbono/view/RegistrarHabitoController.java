package org.HuellaCarbono.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.HuellaCarbono.model.entity.Actividad;
import org.HuellaCarbono.model.entity.Habito;
import org.HuellaCarbono.model.entity.HabitoId;
import org.HuellaCarbono.model.entity.Usuario;
import org.HuellaCarbono.model.services.ActividadService;
import org.HuellaCarbono.model.services.HabitoService;
import org.HuellaCarbono.model.services.UsuarioService;
import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class RegistrarHabitoController extends Controller implements Initializable {
    private Integer userId;
    private ActividadService actividadService;
    private HabitoService habitoService;
    private UsuarioService usuarioService;

    @FXML
    private ComboBox<Actividad> actividadComboBox;
    @FXML
    private TextField frecuencia;
    @FXML
    private TextField tipo;
    @FXML
    private DatePicker fecha;

    public RegistrarHabitoController() {
        this.actividadService = new ActividadService();
        this.habitoService = new HabitoService();
        this.usuarioService = new UsuarioService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Actividad> actividades = actividadService.getAllActividades();
        actividadComboBox.getItems().setAll(actividades);
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

    }

    @FXML
    public void registrarHabito() {

    }

    @FXML
    public void goToMainPageHabito() throws IOException {
        MainController.changeScene(Scenes.MainPageHabito, this.userId);
    }
}