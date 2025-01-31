package org.HuellaCarbono.view;

public enum Scenes {
    WelcomePage("view/WelcomePage.fxml"),
    Secondary("view/secondary.fxml");

    private String url;
    Scenes(String url) {
        this.url = url;
    }
    public String getURL() {
        return url;
    }
}
