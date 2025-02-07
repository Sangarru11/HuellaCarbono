package org.HuellaCarbono.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import org.HuellaCarbono.model.entity.Huella;
import org.HuellaCarbono.model.entity.Categoria;
import org.HuellaCarbono.model.entity.Usuario;
import org.HuellaCarbono.model.services.HuellaService;
import org.HuellaCarbono.model.services.CategoriaService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ComparativaController extends Controller implements Initializable {
    private Integer userId;
    @FXML
    private StackedBarChart<String, Number> DatosUsuarioPrincipal;
    @FXML
    private StackedBarChart<String, Number> DatosUsuarios;

    private HuellaService huellaService = new HuellaService();
    private CategoriaService categoriaService = new CategoriaService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatosUsuarioPrincipal.getXAxis().setLabel(null);
        DatosUsuarioPrincipal.getYAxis().setLabel(null);
        DatosUsuarioPrincipal.setLegendVisible(false);

        DatosUsuarios.getXAxis().setLabel(null);
        DatosUsuarios.getYAxis().setLabel(null);
        DatosUsuarios.setLegendVisible(false);
    }

    @Override
    public Object onOpen(Object input) throws IOException {
        if (input instanceof Integer) {
            this.userId = (Integer) input;
            loadUserData();
            loadAllUsersData();
        }
        return input;
    }

    @Override
    public void onClose(Object output) {
        // Handle any cleanup if necessary
    }

    private void loadUserData() {
        List<Huella> userHuellas = huellaService.getHuellasByUserId(userId);
        List<Huella> transporteHuellas = filterHuellasByCategories(userHuellas, "Transporte");
        List<Huella> energiaHuellas = filterHuellasByCategories(userHuellas, "Energía");
        double transporteAverage = calculateAverage(transporteHuellas);
        double energiaAverage = calculateAverage(energiaHuellas);
        displayData(DatosUsuarioPrincipal, transporteAverage, energiaAverage);
    }

    private void loadAllUsersData() {
        List<Huella> allHuellas = huellaService.getAllHuellas();
        List<Huella> transporteHuellas = filterHuellasByCategories(allHuellas, "Transporte");
        List<Huella> energiaHuellas = filterHuellasByCategories(allHuellas, "Energía");
        double transporteAverage = calculateAverage(transporteHuellas);
        double energiaAverage = calculateAverage(energiaHuellas);
        displayData(DatosUsuarios, transporteAverage, energiaAverage);
    }

    private void displayData(StackedBarChart<String, Number> chart, double transporteAverage, double energiaAverage) {
        XYChart.Series<String, Number> transporteSeries = new XYChart.Series<>();
        transporteSeries.getData().add(new XYChart.Data<>("Transporte", transporteAverage));

        XYChart.Series<String, Number> energiaSeries = new XYChart.Series<>();
        energiaSeries.getData().add(new XYChart.Data<>("Energía", energiaAverage));

        chart.getData().addAll(transporteSeries, energiaSeries);
    }

    private List<Huella> filterHuellasByCategories(List<Huella> huellas, String... categoryNames) {
        List<Categoria> categorias = categoriaService.getCategoriasByNames(categoryNames);
        List<Integer> categoryIds = categorias.stream()
                .map(Categoria::getId)
                .collect(Collectors.toList());
        return huellas.stream()
                .filter(huella -> categoryIds.contains(huella.getIdActividad().getIdCategoria().getId()))
                .collect(Collectors.toList());
    }

    private double calculateAverage(List<Huella> huellas) {
        return huellas.stream()
                .mapToDouble(Huella::getValor)
                .average()
                .orElse(0.0);
    }

    @FXML
    public void goTosHuellaHabito() throws IOException {
        WelcomePageController.changeScene(Scenes.sHuellaHabito, userId);
    }
}