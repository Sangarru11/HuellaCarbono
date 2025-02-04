package org.HuellaCarbono.view;

public enum Scenes {
    WelcomePage("view/WelcomePage.fxml"),
    RegisterPage("view/RegisterPage.fxml"),
    sHuellaHabito("view/sHuellaHabito.fxml"),
    MainPage("view/MainPage.fxml"),
    RegistrarHuella("view/RegistrarHuella.fxml"),
    RegistrarHabito("view/RegistrarHabito.fxml"),
    MainPageHabito("view/MainPageHabito.fxml");

    private String url;
    Scenes(String url) {
        this.url = url;
    }
    public String getURL() {
        return url;
    }
}
