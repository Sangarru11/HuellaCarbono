package org.HuellaCarbono.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.HuellaCarbono.model.entity.Actividad;
import org.HuellaCarbono.model.entity.Huella;
import org.HuellaCarbono.model.entity.Usuario;
import org.HuellaCarbono.model.services.HuellaService;
import org.HuellaCarbono.model.services.UsuarioService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainPageController extends Controller implements Initializable {
    @FXML
    private TableView<Huella> tableView;
    @FXML
    private TableColumn<Huella, String> Actividad;
    @FXML
    private TableColumn<Huella, String> Cantidad;
    @FXML
    private TableColumn<Huella, String> Unidad;
    @FXML
    private TableColumn<Huella, String> Fecha;
    @FXML
    private TableColumn<Huella, String> ImpactoHC;

    private ObservableList<Huella> huellaObservableList;
    private UsuarioService usuarioService;
    private HuellaService huellaService;

    public MainPageController() {
        this.usuarioService = new UsuarioService();
        this.huellaService = new HuellaService();
    }

    @Override
    public Object onOpen(Object input) throws IOException {
        if (input instanceof Integer) {
            Integer userId = (Integer) input;
            Usuario usuario = usuarioService.getUsuarioById(userId);
            if (usuario != null) {
                tableView.setUserData(usuario);
                List<Huella> huellaList = huellaService.getAllHuellas()
                        .stream()
                        .filter(huella -> huella.getIdUsuario().getId().equals(userId))
                        .collect(Collectors.toList());
                this.huellaObservableList = FXCollections.observableArrayList(huellaList);
                tableView.setItems(this.huellaObservableList);
            }
        }
        return input;
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Actividad.setCellValueFactory(cellData -> {
            Actividad actividad = cellData.getValue().getIdActividad();
            return new SimpleStringProperty(actividad != null ? actividad.getNombre() : "");
        });
        Cantidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValor().toString()));
        Unidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUnidad()));
        Fecha.setCellValueFactory(cellData -> {
            LocalDate fecha = cellData.getValue().getFecha();
            return new SimpleStringProperty(fecha != null ? fecha.toString() : "");
        });
        ImpactoHC.setCellValueFactory(cellData -> {
            Huella huella = cellData.getValue();
            Actividad actividad = huella.getIdActividad();
            double impactoHC = huella.getValor() * Double.parseDouble(actividad.getIdCategoria().getFactorEmision());
            return new SimpleStringProperty(String.valueOf(impactoHC));
        });
    }

    @FXML
    public void goToRegistrarHuella() throws IOException {
        Usuario usuario = (Usuario) tableView.getUserData();
        WelcomePageController.changeScene(Scenes.RegistrarHuella, usuario.getId());
    }
}