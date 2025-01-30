package org.HuellaCarbono;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.HuellaCarbono.view.Controller;
import org.HuellaCarbono.view.MainController;
import org.HuellaCarbono.view.Scenes;
import org.HuellaCarbono.view.View;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene;
    public static Stage stage;
    public static Controller currentController;

    @Override
    public void start(Stage stage) throws IOException {
        View view = MainController.loadFXML(Scenes.Primary);
        scene = new Scene(view.scene, 640, 480);
        currentController = view.controller;
        currentController.onOpen(null);
        App.stage = stage;
        stage.setTitle("Cuneta Party");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}