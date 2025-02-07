package org.HuellaCarbono.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.HuellaCarbono.model.entity.*;
import org.HuellaCarbono.model.services.CategoriaService;
import org.HuellaCarbono.model.services.HuellaService;
import org.HuellaCarbono.model.services.RecomendacionService;
import org.HuellaCarbono.model.services.UsuarioService;
import com.opencsv.CSVWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @FXML
    private TableView<Recomendacion> tableViewRecomendacion;
    @FXML
    private TableColumn<Recomendacion, String> Descripcion;
    @FXML
    private TableColumn<Recomendacion, String> Impacto;

    private ObservableList<Recomendacion> recomendacionObservableList;
    private RecomendacionService recomendacionService;

    public MainPageController() {
        this.usuarioService = new UsuarioService();
        this.huellaService = new HuellaService();
        this.recomendacionService = new RecomendacionService();
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
                this.huellaObservableList.setAll(huellaList);
                tableView.setItems(this.huellaObservableList);
                updateRecommendations();
            }
        }
        return input;
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.huellaObservableList = FXCollections.observableArrayList();

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

        List<Huella> huellaList = huellaService.getAllHuellas();
        huellaList.forEach(huella -> {
            huella.getIdActividad().getIdCategoria().getId();
        });
        this.huellaObservableList.setAll(huellaList);
        tableView.setItems(this.huellaObservableList);

        List<Integer> categoriasPresentes = huellaObservableList.stream()
                .map(huella -> huella.getIdActividad().getIdCategoria().getId())
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

    private void updateRecommendations() {
        List<Integer> categoriasPresentes = huellaObservableList.stream()
                .map(huella -> huella.getIdActividad().getIdCategoria().getId())
                .distinct()
                .collect(Collectors.toList());

        List<Recomendacion> recomendacionesFiltradas = recomendacionService.getRecomendacionesByCategoriaIds(
                categoriasPresentes
        );

        this.recomendacionObservableList.setAll(recomendacionesFiltradas);
        tableViewRecomendacion.setItems(this.recomendacionObservableList);
    }

    @FXML
    public void GeneratePdfRecords() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            List<Huella> huellaList = huellaService.getAllHuellas();
            generatePdf(huellaList, file);
        }
    }

    @FXML
    public void GenerateCsvRecords() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            List<Huella> huellaList = huellaService.getAllHuellas();
            generateCsv(huellaList, file);
        }
    }

    private void generatePdf(List<Huella> huellaList, File file) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(25, 750);
                contentStream.showText("Historial de Huella de Carbono");
                contentStream.endText();

                float margin = 25;
                float yStart = 700;
                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                float yPosition = yStart;
                float rowHeight = 20;
                float cellMargin = 5f;

                contentStream.setLineWidth(1f);
                contentStream.moveTo(margin, yPosition);
                contentStream.lineTo(margin + tableWidth, yPosition);
                contentStream.stroke();
                yPosition -= rowHeight;

                String[] headers = {"Fecha", "Valor", "Actividad", "Recomendaciones"};
                for (String header : headers) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin + cellMargin, yPosition + cellMargin);
                    contentStream.showText(header);
                    contentStream.endText();
                    margin += tableWidth / headers.length;
                }
                margin = 25;
                yPosition -= rowHeight;

                for (Huella huella : huellaList) {
                    String[] data = {
                            huella.getFecha().toString(),
                            huella.getValor().toString(),
                            huella.getIdActividad().getNombre(),
                            getRecomendaciones(huella)
                    };
                    for (int i = 0; i < data.length; i++) {
                        contentStream.beginText();
                        contentStream.newLineAtOffset(margin + cellMargin, yPosition + cellMargin);
                        if (i == 3) {
                            String[] lines = splitTextIntoLines(data[i], 4);
                            for (String line : lines) {
                                contentStream.showText(line);
                                yPosition -= rowHeight;
                                contentStream.newLineAtOffset(0, -rowHeight);
                            }
                        } else {
                            contentStream.showText(data[i]);
                        }
                        contentStream.endText();
                        margin += tableWidth / data.length;
                    }
                    margin = 25;
                    yPosition -= rowHeight;
                }
            }

            document.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] splitTextIntoLines(String text, int maxLength) {
        String[] words = text.split(" ");
        List<String> lines = new ArrayList<>();
        StringBuilder line = new StringBuilder();
        int wordCount = 0;

        for (String word : words) {
            if (wordCount == maxLength) {
                lines.add(line.toString());
                line = new StringBuilder();
                wordCount = 0;
            }
            if (line.length() > 0) {
                line.append(" ");
            }
            line.append(word);
            wordCount++;
        }
        if (line.length() > 0) {
            lines.add(line.toString());
        }
        return lines.toArray(new String[0]);
    }

    private void generateCsv(List<Huella> huellaList, File file) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
            String[] header = {"Fecha", "Valor", "Actividad", "Recomendaciones"};
            writer.writeNext(header);
            for (Huella huella : huellaList) {
                String[] data = {
                        huella.getFecha().toString(),
                        huella.getValor().toString(),
                        huella.getIdActividad().getNombre(),
                        getRecomendaciones(huella)
                };
                writer.writeNext(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getRecomendaciones(Huella huella) {
        List<Recomendacion> recomendaciones = recomendacionService.getRecomendacionesByCategoriaIds(
                List.of(huella.getIdActividad().getIdCategoria().getId())
        );
        return recomendaciones.stream()
                .map(Recomendacion::getDescripcion)
                .collect(Collectors.joining(", "));
    }

    @FXML
    public void goToRegistrarHuella() throws IOException {
        Usuario usuario = (Usuario) tableView.getUserData();
        WelcomePageController.changeScene(Scenes.RegistrarHuella, usuario.getId());
    }
    @FXML
    public void goTosHuellaHabito() throws IOException{
        Usuario usuario = (Usuario) tableView.getUserData();
        WelcomePageController.changeScene(Scenes.sHuellaHabito, usuario.getId());
    }
}