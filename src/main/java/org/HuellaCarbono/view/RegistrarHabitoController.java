package org.HuellaCarbono.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    private ComboBox<String> tipo;
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

        tipo.getItems().addAll("Semanal", "Diaria", "Anuel");
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
    public void registrarHabito() throws IOException {
        Actividad actividad = actividadComboBox.getValue();
        String freq = frecuencia.getText();
        String tipoHabito = tipo.getValue();
        LocalDate date = fecha.getValue();
        Usuario usuario = usuarioService.getUsuarioById(this.userId);

        if (actividad == null || usuario == null || date == null || freq.isEmpty() || tipoHabito.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Datos faltantes");
            alert.setContentText("Por favor, complete todos los campos.");
            alert.showAndWait();
        } else {
            HabitoId habitoId = new HabitoId();
            habitoId.setIdUsuario(usuario.getId());
            habitoId.setIdActividad(actividad.getId());

            Habito existingHabito = habitoService.getHabitoById(habitoId);
            if (existingHabito != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Hábito duplicado");
                alert.setContentText("Este hábito ya está registrado.");
                alert.showAndWait();
            } else {
                Habito habito = new Habito();
                habito.setIdUsuario(usuario);
                habito.setIdActividad(actividad);
                habito.setFecuencia(freq);
                habito.setTipo(tipoHabito);
                habito.setUltimaFecha(date);
                habito.setId(habitoId);

                habitoService.saveHabito(habito);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Éxito");
                alert.setHeaderText("Hábito registrado");
                alert.setContentText("El hábito ha sido registrado exitosamente.");
                alert.showAndWait();

                goToMainPageHabito();
            }
        }
    }

    @FXML
    public void goToMainPageHabito() throws IOException {
        MainController.changeScene(Scenes.MainPageHabito, this.userId);
    }
}