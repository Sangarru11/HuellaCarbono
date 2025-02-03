package org.HuellaCarbono.view;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.HuellaCarbono.model.DAO.ActividadDAO;
import org.HuellaCarbono.model.DAO.CategoriaDAO;
import org.HuellaCarbono.model.DAO.HuellaDAO;
import org.HuellaCarbono.model.DAO.UsuarioDAO;
import org.HuellaCarbono.model.entity.Actividad;
import org.HuellaCarbono.model.entity.Categoria;
import org.HuellaCarbono.model.entity.Huella;
import org.HuellaCarbono.model.entity.Usuario;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class RegistrarHuellaController extends Controller implements Initializable {
    private Integer userId;
    @FXML
    private ComboBox<Actividad> actividadComboBox;
    @FXML
    private Text unidad;
    @FXML
    private TextField cantidad;
    @FXML
    private DatePicker fecha;
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
    public void onOpen(Object input) throws IOException {
        if (input instanceof Integer) {
            this.userId = (Integer) input;
            List<Actividad> actividades = ActividadDAO.build().findAll();
            actividadComboBox.getItems().setAll(actividades);
        }
    }

    @Override
    public void onClose(Object output) {
        // Lógica para cerrar la vista si es necesario
    }

    @FXML
    public void registrarHuella() {
        Actividad actividad = actividadComboBox.getValue();
        String valor = cantidad.getText();
        String date = fecha.getValue().toString();
        Usuario usuario = UsuarioDAO.build().findById(this.userId);

        Huella huella = new Huella();
        huella.setIdActividad(actividad);
        huella.setValor(Integer.valueOf(valor));
        huella.setUnidad(unidad.getText());
        huella.setIdUsuario(usuario);
        huella.setFecha(LocalDate.parse(date));

        HuellaDAO.build().save(huella);
    }
    @FXML
    public void returnToMain() throws IOException {
        MainController.changeScene(Scenes.MainPage, this.userId);
    }
}