package org.HuellaCarbono.view;

public enum Scenes {
    WelcomePage("view/WelcomePage.fxml"),
    RegisterPage("view/RegisterPage.fxml"),
    MainPage("view/MainPage.fxml");

    private String url;
    Scenes(String url) {
        this.url = url;
    }
    public String getURL() {
        return url;
    }
}
