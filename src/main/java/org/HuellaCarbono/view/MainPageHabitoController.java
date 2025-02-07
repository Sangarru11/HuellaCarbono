package org.HuellaCarbono.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.HuellaCarbono.model.entity.*;
import org.HuellaCarbono.model.services.CategoriaService;
import org.HuellaCarbono.model.services.HabitoService;
import org.HuellaCarbono.model.services.RecomendacionService;
import org.HuellaCarbono.model.services.UsuarioService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainPageHabitoController extends Controller implements Initializable {
    @FXML
    private TableView<Habito> tableView;
    @FXML
    private TableColumn<Habito, String> Actividad;
    @FXML
    private TableColumn<Habito, String> Frecuencia;
    @FXML
    private TableColumn<Habito, String> Tipo;
    @FXML
    private TableColumn<Habito, String> Fecha;

    private ObservableList<Habito> habitoObservableList;
    private UsuarioService usuarioService;
    private HabitoService habitoService;

    @FXML
    private TableView<Recomendacion> tableViewRecomendacion;
    @FXML
    private TableColumn<Recomendacion, String> Descripcion;
    @FXML
    private TableColumn<Recomendacion, String> Impacto;

    private ObservableList<Recomendacion> recomendacionObservableList;
    private RecomendacionService recomendacionService;

    public MainPageHabitoController() {
        this.usuarioService = new UsuarioService();
        this.habitoService = new HabitoService();
        this.recomendacionService = new RecomendacionService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Actividad.setCellValueFactory(cellData -> {
            Actividad actividad = cellData.getValue().getIdActividad();
            return new SimpleStringProperty(actividad != null ? actividad.getNombre() : "");
        });
        Frecuencia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecuencia()));
        Tipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo()));
        Fecha.setCellValueFactory(cellData -> {
            LocalDate fecha = cellData.getValue().getUltimaFecha();
            return new SimpleStringProperty(fecha != null ? fecha.toString() : "");
        });

        List<Habito> habitoList = habitoService.getAllHabitos();
        habitoList.forEach(huella -> {
            huella.getIdActividad().getIdCategoria().getId();
        });

        this.habitoObservableList = FXCollections.observableArrayList(habitoList);
        tableView.setItems(this.habitoObservableList);

        List<Integer> categoriasPresentes = habitoObservableList.stream()
                .map(habito -> habito.getIdActividad().getIdCategoria().getId())
                .distinct()
                .collect(Collectors.toList());

        CategoriaService categoriaService = new CategoriaService();
        List<Categoria> categorias = categoriaService.getAllCategorias();
        List<Recomendacion> recomendacionesFiltradas = recomendacionService.getRecomendacionesByCategoriaIds(
                categorias.stream()
                        .filter(categoria -> categoriasPresentes.contains(categoria.getId()))
                        .map(Categoria::getId)
                        .collect(Collectors.toList())
        );

        Descripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
        Impacto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImpactoEstimado()));
        this.recomendacionObservableList = FXCollections.observableArrayList(recomendacionesFiltradas);
        tableViewRecomendacion.setItems(this.recomendacionObservableList);
        updateRecommendations();
    }

    @Override
    public Object onOpen(Object input) throws IOException {
        if (input instanceof Integer) {
            Integer userId = (Integer) input;
            Usuario usuario = usuarioService.getUsuarioById(userId);
            if (usuario != null) {
                tableView.setUserData(usuario);
                List<Habito> habitoList = habitoService.getAllHabitos()
                        .stream()
                        .filter(habito -> habito.getIdUsuario().getId().equals(userId))
                        .collect(Collectors.toList());
                this.habitoObservableList = FXCollections.observableArrayList(habitoList);
                tableView.setItems(this.habitoObservableList);
                updateRecommendations();
            }
        }
        return input;
    }

    private void updateRecommendations() {
        List<Integer> categoriasPresentes = habitoObservableList.stream()
                .map(habito -> habito.getIdActividad().getIdCategoria().getId())
                .distinct()
                .collect(Collectors.toList());

        List<Recomendacion> recomendacionesFiltradas = recomendacionService.getRecomendacionesByCategoriaIds(
                categoriasPresentes
        );

        this.recomendacionObservableList.setAll(recomendacionesFiltradas);
        tableViewRecomendacion.setItems(this.recomendacionObservableList);
    }

    @Override
    public void onClose(Object output) {

    }
    @FXML
    public void goToRegistrarHabito() throws IOException{
        Usuario usuario = (Usuario) tableView.getUserData();
        WelcomePageController.changeScene(Scenes.RegistrarHabito, usuario.getId());
    }
    @FXML
    public void goTosHuellaHabito() throws IOException{
        Usuario usuario = (Usuario) tableView.getUserData();
        WelcomePageController.changeScene(Scenes.sHuellaHabito, usuario.getId());
    }
}