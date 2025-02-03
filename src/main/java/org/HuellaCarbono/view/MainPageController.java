package org.HuellaCarbono.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.HuellaCarbono.model.DAO.ActividadDAO;
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

    @Override
    public void onOpen(Object input) throws IOException {
        if (input instanceof Integer) {
            Integer userId = (Integer) input;
            Usuario usuario = UsuarioDAO.build().findById(userId);
            tableView.setUserData(usuario);
            List<Huella> huellaList = HuellaDAO.build().findByUser(usuario);
            this.huellaObservableList = FXCollections.observableArrayList(huellaList);
            tableView.setItems(this.huellaObservableList);
        }
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Actividad.setCellValueFactory(cellData -> {
            Actividad actividad = ActividadDAO.build().findById(cellData.getValue().getIdActividad().getId());
            return new SimpleStringProperty(actividad != null ? actividad.getNombre() : "");
        });
        Cantidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValor().toString()));
        Unidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUnidad()));
        Fecha.setCellValueFactory(cellData -> {
            LocalDate fecha = cellData.getValue().getFecha();
            return new SimpleStringProperty(fecha != null ? fecha.toString() : ""); // Handle null date
        });
        ImpactoHC.setCellValueFactory(cellData -> {
            Huella huella = cellData.getValue();
            Categoria categoria = huella.getIdActividad().getIdCategoria();
            double impactoHC = huella.getValor() * Double.parseDouble(categoria.getFactorEmision());
            return new SimpleStringProperty(String.valueOf(impactoHC));
        });
    }

    @FXML
    public void goToRegistrarHuella() throws IOException {
        Usuario usuario = (Usuario) tableView.getUserData();
            WelcomePageController.changeScene(Scenes.RegistrarHuella, usuario.getId());

    }
}